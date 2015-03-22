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
package it.unical.inf.wsportal.client.controller;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import it.unical.inf.wsportal.client.MethodEnvelope;
import it.unical.inf.wsportal.client.WSPortal;
import it.unical.inf.wsportal.client.XMLSerializer;
import it.unical.inf.wsportal.client.callback.InvokeMethod;
import it.unical.inf.wsportal.client.callback.ShowWsdlDialog;
import it.unical.inf.wsportal.client.model.WsTreeItem;
import it.unical.inf.wsportal.client.util.AsynchRequest;
import it.unical.inf.wsportal.client.util.ComponentID;
import it.unical.inf.wsportal.client.util.MessageDecoder;
import it.unical.inf.wsportal.client.util.Toolkit;
import it.unical.inf.wsportal.client.view.CloseButton;
import it.unical.inf.wsportal.client.view.InputPortlet;
import it.unical.inf.wsportal.client.view.InvokeButton;
import it.unical.inf.wsportal.client.view.WsTree;
import it.unical.inf.wsportal.shared.Operation;
import it.unical.inf.wsportal.shared.Service;
import java.util.ArrayList;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class Controller implements Listener<ComponentEvent> {

    private static Controller controller;

    @Override
    public void handleEvent(ComponentEvent event) {
        String id = event.getComponent().getId();
        int eventCode = event.getType().getEventCode();

        if (id.startsWith(ComponentID.INVOKE_BUTTON)) {
            invokeMethod(event.getComponent());
        } else if (id.equals(ComponentID.ADD_WSDL_BUTTON)) {
            showLoadWsdlDialog();
        } else if (eventCode == Events.OnDoubleClick.getEventCode()) {
            showInputPortlet();
        }
    }

    /**
     * Adds the Portlet to the Portal and show it
     */
    private void showInputPortlet() {
        WsTree tree = WSPortal.getWsTree();
        WsTreeItem item = tree.getSelectedItem();
        if (item.getType() == WsTreeItem.OPERATION) {
            // create a new portlet
            InputPortlet portlet = new InputPortlet(item.getName());
            // get the service url from the service item id
            String url = tree.getServiceOf(item).getId();
            // set the service url to the portlet
            portlet.setServiceUrl(url);
            // get the item id
            String id = item.getId();
            // if the id is equals to void, it means that the operation doesn't
            // accept any parameter, otherwise
            if (!id.equals("void")) {
                // split the string by ":"
                String[] split = id.split(":");
                // and for each element
                for (int i = 0; i < split.length; i++) {
                    // get the input parameter and set it to the portlet input text field
                    portlet.addParameter(split[i], "param" + i);
                }
            }
            portlet.setButtonAlign(HorizontalAlignment.CENTER);
            portlet.addButton(new InvokeButton());
            WSPortal.getInputPortletContainer().add(portlet);
        }
    }

    /**
     * Shows the dialog that allow the user to type the URL of the WSDL file
     * and add the new web service.
     */
    private void showLoadWsdlDialog() {
        MessageBox messageBox = MessageBox.prompt("Load new Service", "WSDL url");
        messageBox.addCallback(new ShowWsdlDialog());
    }

    /**
     * Passes the method definition to the server side. The server invokes the method
     * and sends back the result as a string.
     */
    private void invokeMethod(Component component) {
        InputPortlet portlet = (InputPortlet) WSPortal.getInputPortletContainer().getPortlet(component);
        ListStore<BaseModelData> store = portlet.getInputGrid().getStore();
        MethodEnvelope envelope = new MethodEnvelope();
        envelope.setServiceUrl(portlet.getServiceUrl());
        envelope.setMethodName(portlet.getHeading());
        for (int i = 0; i < store.getCount(); i++) {
            String type = store.getAt(i).get("type").toString();
            Object value = store.getAt(i).get("param");
            envelope.addParameter(type, value);
        }
        XMLSerializer serializer = new XMLSerializer(envelope);
        String params = "xml=" + URL.encode(serializer.serialize());
        AsynchRequest request = new AsynchRequest(AsynchRequest.POST, "../invokeMethod", params);
        request.setCallback(new InvokeMethod(portlet.getHeading()));
        try {
            request.send();
        } catch (RequestException ex) {
            Toolkit.showException(ex);
        }
    }

    /**
     * Add the service to the tree on the left side panel.
     * 
     * @param xml the XML file that describe the service to add
     */
    public static void addWsdl(String xml) {
        // wrap the xml file
        MessageDecoder decoder = new MessageDecoder(xml);
        // if is a service object
        if (decoder.isService()) {
            // deserialize the xml file in a lis of objects, if any
            ArrayList<Service> services = decoder.getServices();
            // handle the web service tree
            WsTree tree = WSPortal.getWsTree();
            // for each Service
            for (Service service : services) {
                // create a tree item object
                WsTreeItem serviceItem = new WsTreeItem(service.getName(), WsTreeItem.SERVICE);
                serviceItem.setId(service.getUrl());
                tree.addService(serviceItem);
                // gets the service operations
                ArrayList<Operation> operations = service.getOperations();
                // for each operation, if any
                for (Operation operation : operations) {
                    // create an operation item for the tree
                    WsTreeItem operationItem = new WsTreeItem(operation.getName(), WsTreeItem.OPERATION);
                    // gets its parameter
                    ArrayList<String> parameters = operation.getParameters();
                    // if the operation has more than zero elements
                    if (parameters.size() > 0) {
                        // add its parameter separated by a ":"
                        StringBuilder buffer = new StringBuilder();
                        for (String parameter : parameters) {
                            buffer.append(parameter).append(":");
                        }
                        // delete the last ":"
                        buffer.deleteCharAt(buffer.lastIndexOf(":"));
                        // set the id
                        operationItem.setId(buffer.toString());
                    } else {
                        // set the id with "void"
                        operationItem.setId("void");
                    }
                    tree.addOperation(serviceItem, operationItem);
                }
            }
        } else {
            // show a message. The user already has the service.
            Toolkit.showInfo(decoder.getMessage());
        }
    }

    /**
     * Gets the instance of this singleton class.
     *
     * @return the Controller instance
     */
    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }
}
