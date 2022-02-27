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
package it.unical.inf.wsportal.client.util;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import static it.unical.inf.wsportal.shared.SharedStuff.*;
import it.unical.inf.wsportal.shared.Operation;
import it.unical.inf.wsportal.shared.Service;
import java.util.ArrayList;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class MessageDecoder {

    private Document document;
    private Node result;
    private boolean isService;

    public MessageDecoder(String xml) {
        // gets the DOM from the xml file
        document = XMLParser.parse(xml);
        // gets the root element
        result = document.getElementsByTagName(RESULT).item(0);
        // gets the type attribute of the root element
        Node type = result.getAttributes().getNamedItem(TYPE);
        if (type.getNodeValue().equals(SERVICE)) {
            isService = true;
        } else {
            isService = false;
        }
    }

    public boolean isService() {
        return isService;
    }

    public String getMessage() {
        return result.getChildNodes().item(0).getNodeValue();
    }

    public ArrayList<Service> getServices() {
        ArrayList<Service> list = new ArrayList<Service>();
        NodeList services = document.getElementsByTagName(SERVICE);
        // for each service, if at least one
        for (int i = 0; i < services.getLength(); i++) {
            // create a new service object
            Service s = new Service();
            // gets the next service
            Node service = services.item(i);
            // gets the service attributes (eg. name, url, package, description)
            NodeList serviceFields = service.getChildNodes();
            // set the attributes into the service object
            s.setName(serviceFields.item(1).getFirstChild().getNodeValue());
            s.setUrl(serviceFields.item(3).getFirstChild().getNodeValue());
            s.setPackageName(serviceFields.item(5).getFirstChild().getNodeValue());
            s.setDescription(serviceFields.item(7).getFirstChild().getNodeValue());
            // gets the service operations
            NodeList operations = serviceFields.item(9).getChildNodes();
            for (int j = 0; j < operations.getLength(); j++) {
                // gets the next operation
                Node operation = operations.item(j);
                // avoids the #text element
                if (operation.getNodeType() != 3) {
                    // create a new operation object
                    Operation o = new Operation();
                    // gets the attribute of the operation node
                    NamedNodeMap attributes = operation.getAttributes();
                    // set the name and the return type
                    o.setName(attributes.getNamedItem(NAME).getNodeValue());
                    o.setReturnType(attributes.getNamedItem(RETURN_TYPE).getNodeValue());
                    // gets its input parameters
                    NodeList params = operation.getChildNodes();
                    // for each input parameter
                    for (int k = 0; k < params.getLength(); k++) {
                        // gets the item
                        Node param = params.item(k);
                        // avoids the #text element
                        if (param.getNodeType() != 3) {
                            o.addParameter(param.getFirstChild().getNodeValue());
                        }
                    }
                    s.addOperation(o);
                }
            }
            list.add(s);
        }
        return list;
    }
}
