/*
 * Copyright © 2010
 *
 * This file is part of "WS Portal" web application.
 *
 * WS Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WS Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WS Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.unical.inf.wsportal.server.servlet;

import com.oreilly.servlet.MultipartRequest;
import it.unical.inf.wsportal.server.FoldersNFiles;
import it.unical.inf.wsportal.server.Generator;
import it.unical.inf.wsportal.server.GenericClient;
import it.unical.inf.wsportal.server.util.MessageEncoder;
import it.unical.inf.wsportal.server.WsdlInfoRetreiver;
import it.unical.inf.wsportal.shared.Service;
import it.unical.inf.wsportal.server.db.SqlStatement;
import it.unical.inf.wsportal.shared.Operation;
import it.unical.inf.wsportal.shared.SharedStuff;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carmine Dodaro {carminedodaro@gmail.com}, Simone Spaccarotella {spa.simone@gmail.com}
 */
public class WSLibGenerator extends HttpServlet {

    private Service service;
    private HttpServletRequest request;
    private static final String URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String MULTIPART = "multipart/form-data";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
        this.request = request;
        String wsdlFile = getWsdlFile();
        if (isNotGeneratedYet(wsdlFile)) {
            generateLib(wsdlFile);
        }
        boolean addedToTheUser = addServiceToTheUser();
        String result = null;
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        MessageEncoder encoder = new MessageEncoder(response.getWriter());
        if (!addedToTheUser) {
            encoder.encode("The user already has the " + service.getName());
        } else {
            addServiceOperations();
            encoder.encode(service);
        }
    }

    /**
     * Get the path where the WSDL file is located (URL or local path).
     * 
     * @return the location of the WSDL file
     * @throws IOException
     */
    private String getWsdlFile() throws IOException {
        String contentType = request.getContentType();
        String wsdlFile = null;
        if (contentType.startsWith(URL_ENCODED)) {
            wsdlFile = request.getParameter(SharedStuff.FORM_URL);
        } else if (contentType.startsWith(MULTIPART)) {
            // get the username in order to use its working directory
            String user = (String) request.getSession().getAttribute("username");
            // create the user working directory if doesn't exist
            String src = FoldersNFiles.getSrcPath() + user + File.separator;
            new File(src).mkdirs();
            // handle the post request
            MultipartRequest multipart = new MultipartRequest(request, src);
            // get the type of field that was used in order to post the WSDL file
            String field = multipart.getParameter(SharedStuff.FORM_FIELD);
            // if the wsdl file is identified by an url
            if (field.equals(SharedStuff.FORM_URL)) {
                // gets the url
                wsdlFile = multipart.getParameter(field);
                // else if is a local file
            } else if (field.equals(SharedStuff.FORM_FILE)) {
                // gets the absolute path
                wsdlFile = multipart.getFile(field).getAbsolutePath();
            }
        }
        return wsdlFile;
    }

    /**
     * Says whether the client library is already generated or not.
     *
     * @param wsdlFile the WSDL file path/URL
     * @return true if the library was already generated
     */
    private boolean isNotGeneratedYet(String wsdlFile) {
        WsdlInfoRetreiver info = null;
        try {
            info = new WsdlInfoRetreiver(wsdlFile);
        } catch (URISyntaxException ex) {
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        service = new Service();
        service.setUrl(info.getServiceUrl());
        service.setName(info.getServiceName());
        service.setWsdlUrl(wsdlFile);
        service.setPackageName(FoldersNFiles.GENERATED_SERVICES + "." + info.getPackageName());
        service.setDescription("no description");
        SqlStatement statement = new SqlStatement();
        boolean added = statement.addService(service);
        statement.close();
        return added;
    }

    /**
     * Generate the client library, in order to use the web service.
     *
     * @param wsdlFile the path/URL of the WSDL file
     */
    private void generateLib(String wsdlFile) throws IOException {
        HttpSession session = request.getSession();
        // get the username
        String user = (String) session.getAttribute("username");
        Generator generator = new Generator(wsdlFile);
        // set the directory of the source files (WEB-INF/src/$USER)
        generator.setJavaFilesFolder(FoldersNFiles.getSrcPath() + File.separator + user);
        // set the root package (generatedServices)
        generator.addRootPackage(FoldersNFiles.GENERATED_SERVICES);
        // set the directory of the library (WEB-INF/lib)
        generator.setLibraryFolder(FoldersNFiles.getLibPath());
        // set the directory of the classes (WEB-INF/classes/)
        generator.setClassesFolder(FoldersNFiles.getClassesPath());
        // set the working directory of javac (WEB-INF/javac/$USER)
        generator.setWorkingFolder(FoldersNFiles.getJavacPath() + user);
        try {
            // run the generator
            generator.run();
        } catch (Exception ex) {
        }
        try {
            generator.clean();
        } catch (FileNotFoundException ex) {
        } catch (InterruptedException ex) {
        } catch (IOException ex) {
        }
    }

    /**
     * Add the service to the user table.
     *
     * @return true if the service was added to the user.
     */
    private boolean addServiceToTheUser() {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        SqlStatement statement = new SqlStatement();
        boolean added = statement.addUserService(username, service.getUrl());
        statement.close();
        return added;
    }

    /**
     * Retrieves the operations of this service and add them to it.
     */
    private void addServiceOperations() {
        GenericClient client = new GenericClient();
        ArrayList<Operation> operations = client.allServiceMethods(service.getPackageName());
        for (Operation o : operations) {
            service.addOperation(o);
        }
    }
}
