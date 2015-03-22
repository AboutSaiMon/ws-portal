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
package spa.simone.wsportal.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class WsdlInfoRetreiver {

    private String wsdlFile;
    private Document document;
    private static final String WSDL_NS = "http://schemas.xmlsoap.org/wsdl/";
    private static final String SOAP_NS = "http://schemas.xmlsoap.org/wsdl/soap/";

    /**
     *
     * @param wsdlFile
     * @throws URISyntaxException
     * @throws MalformedURLException
     * @throws IOException
     */
    public WsdlInfoRetreiver(String wsdlFile) throws URISyntaxException, MalformedURLException, IOException {
        this.wsdlFile = wsdlFile;
        generateDOM();
    }

    /**
     * 
     * @return
     */
    public String getServiceUrl() {
        String soapPrefix = getSoapPrefix();
        return findServiceUrl(soapPrefix);
    }

    /**
     * 
     * @return
     */
    public String getServiceName() {
        String wsdlPrefix = getWsdlPrefix();
        return findServiceName(wsdlPrefix);
    }

    /**
     * 
     * @return
     */
    public String getPortName() {
        String soapPrefix = getSoapPrefix();
        return findPortName(soapPrefix);
    }

    /**
     * Get the package name of the WSDL file.
     *
     * @return the package name
     */
    public String getPackageName() {
        /*
         * The package name is a string composed of two parts: the first portion of the string
         * is the host name backwards without the ".", replaced with the "_".
         * Whereas the second portion of it is the absolute file path
         * without the "/", replaced by the "_".
         */

        // with the URL class we can get the host name and the file path easily
        URL url = null;
        // this is the string builder that store the package name
        StringBuilder packageName = new StringBuilder();
        try {
            // retrieve the url of the service in order to create a valid
            // package name
            url = new URI(getServiceUrl()).toURL();

            // gets the host name
            String temp = url.getHost();
            // splits the string by the full stop
            String[] split = temp.split("[.]");
            // builds the package name with the backward splitted token
            for (int i = split.length - 1; i >= 0; i--) {
                packageName.append(split[i] + "_");
            }
            // gets the file path
            temp = url.getPath();
            // delete the first "/" char (because the absolute path begin with the slash)
            temp = temp.replaceFirst("/", "");
            // replace the remaining "/" with the "_"
            temp = temp.replaceAll("/", "_");
            // replace all the "." with the "_"
            temp = temp.replaceAll("[.]", "_");
            // append the last portion of the string to the builder
            packageName.append(temp);
        } catch (URISyntaxException ex) {
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        return packageName.toString().toLowerCase();
    }

    private InputStream getInputStream() throws URISyntaxException, MalformedURLException, IOException {
        InputStream inStream = null;
        try {
            URL url = new URI(wsdlFile).toURL();
            URLConnection connection = url.openConnection();
            connection.connect();
            inStream = connection.getInputStream();
        } catch (IllegalArgumentException ex) {
            inStream = new FileInputStream(wsdlFile);
        }
        return inStream;
    }

    private void generateDOM() throws URISyntaxException, MalformedURLException, IOException {
        Tidy parser = new Tidy();
        parser.setXmlTags(true);
        InputStream inStream = getInputStream();
        document = parser.parseDOM(inStream, null);
        inStream.close();
    }

    private String getWsdlPrefix() {
        String wsdlPrefix = null;
        Element element = document.getDocumentElement();
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            attributes.item(i).getNodeValue();
            if (attributes.item(i).getNodeValue().equals(WSDL_NS)) {
                wsdlPrefix = attributes.item(i).getNodeName().split(":")[1];
                break;
            }
        }
        return wsdlPrefix;
    }

    private String getSoapPrefix() {
        String soapPrefix = null;
        Element element = document.getDocumentElement();
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            attributes.item(i).getNodeValue();
            if (attributes.item(i).getNodeValue().equals(SOAP_NS)) {
                soapPrefix = attributes.item(i).getNodeName().split(":")[1];
                break;
            }
        }
        return soapPrefix;
    }

    private String findServiceName(String wsdlPrefix) {
        NodeList list = document.getElementsByTagName(wsdlPrefix + ":service");
        NamedNodeMap attributes = list.item(0).getAttributes();
        Node name = attributes.getNamedItem("name");
        return name.getNodeValue();
    }

    private String findServiceUrl(String soapPrefix) {
        NodeList list = document.getElementsByTagName(soapPrefix + ":address");
        NamedNodeMap attributes = list.item(0).getAttributes();
        Node location = attributes.getNamedItem("location");
        return location.getNodeValue();
    }

    private String findPortName(String soapPrefix) {
        Node node = document.getElementsByTagName(soapPrefix + ":address").item(0);
        node = node.getParentNode();
        NamedNodeMap attributes = node.getAttributes();
        Node location = attributes.getNamedItem("name");
        return location.getNodeValue();
    }
}
