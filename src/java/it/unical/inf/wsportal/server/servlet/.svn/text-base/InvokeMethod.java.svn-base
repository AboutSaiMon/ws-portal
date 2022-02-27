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

import it.unical.inf.wsportal.client.MethodEnvelope;
import it.unical.inf.wsportal.server.GenericClient;
import it.unical.inf.wsportal.server.WsdlInfoRetreiver;
import it.unical.inf.wsportal.server.db.SqlStatement;
import it.unical.inf.wsportal.shared.Service;
import it.unical.inf.wsportal.shared.SharedStuff;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class InvokeMethod extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException, MalformedURLException, IOException {
        MethodEnvelope envelope = deserialize(request.getParameter("xml"));
        // retrieve the service from the db
        Service service = getService(envelope.getServiceUrl());
        // get the port name used to invoke the service;
        String portName = getServicePortName(service.getWsdlUrl());
        GenericClient client = new GenericClient();
        //String result = client.invokeMethod(service.getPackageName(), envelope, portName);
        String result = client.invokeMethod(service.getPackageName(), envelope, portName);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.close();
    }

    private MethodEnvelope deserialize(String xml) throws UnsupportedEncodingException, IOException {
        // decodes the result
        xml = URLDecoder.decode(xml, "UTF-8");
        // creates a new parser
        Tidy parser = new Tidy();
        parser.setXmlTags(true);
        // transforms the xml file in a DOM object
        Document dom = parser.parseDOM(new ByteArrayInputStream(xml.getBytes()), null);
        // retrieves the service url element from the DOM
        Node serviceUrlNode = dom.getElementsByTagName(SharedStuff.SERVICE_URL).item(0);
        String serviceUrl = serviceUrlNode.getFirstChild().getNodeValue();
        // retrieves the method name element from the DOM
        Node methodNameNode = dom.getElementsByTagName(SharedStuff.METHOD_NAME).item(0);
        String methodName = methodNameNode.getFirstChild().getNodeValue();
        // create a new object that wrap these informations
        MethodEnvelope envelope = new MethodEnvelope();
        // sets the attributes
        envelope.setServiceUrl(serviceUrl);
        envelope.setMethodName(methodName);
        // get the parameters
        NodeList parameters = dom.getElementsByTagName(SharedStuff.METHOD_PARAMETER);
        if (parameters != null) {
            for (int i = 0; i < parameters.getLength(); i++) {
                NodeList children = parameters.item(i).getChildNodes();
                String type = children.item(0).getFirstChild().getNodeValue();
                String value = children.item(1).getFirstChild().getNodeValue();
                // adds a new parameter
                envelope.addParameter(type, value);
            }
        }
        return envelope;
    }

    private Service getService(String url) {
        SqlStatement statement = new SqlStatement();
        Service service = statement.getService(url);
        statement.close();
        return service;
    }

    private String getServicePortName(String wsdlUrl) throws MalformedURLException, IOException {
        WsdlInfoRetreiver info = null;
        try {
            info = new WsdlInfoRetreiver(wsdlUrl);
        } catch (URISyntaxException ex) {
        }
        return info.getPortName();
    }
}
