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
package it.unical.inf.wsportal.client;

import com.extjs.gxt.ui.client.event.Events;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import it.unical.inf.wsportal.client.controller.Controller;
import it.unical.inf.wsportal.client.util.Session;
import it.unical.inf.wsportal.client.view.AddWsdlButton;
import it.unical.inf.wsportal.client.view.PortletContainer;
import it.unical.inf.wsportal.client.view.WsPortalViewport;
import it.unical.inf.wsportal.client.view.WsTree;

/**
 * This is the entry point of the application. This class loads the GWT module, and
 * instantiates the GUI.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class WSPortal implements EntryPoint {

    private WsPortalViewport wsportalViewport;
    private static WsTree wsTree;
    private static AddWsdlButton button;
    private static PortletContainer inputPortletContainer;
    private static PortletContainer outputPortletContainer;

    /**
     * Gets the tree view that shows the web services.
     * 
     * @return a Tree of web services
     */
    public static WsTree getWsTree() {
        if (wsTree == null) {
            wsTree = new WsTree();
            wsTree.addListener(Events.OnDoubleClick, Controller.getInstance());
            Session.updateTreeStore();
        }
        return wsTree;
    }

    /**
     * Gets the button that allow the user to add a nwe WSDL file.
     * 
     * @return the AddWsdlButton
     */
    public static AddWsdlButton getAddWsdlButton() {
        if (button == null) {
            button = new AddWsdlButton();
        }
        return button;
    }

    /**
     * Gets the portal that contains the input portlets.
     * 
     * @return the Portal
     */
    public static PortletContainer getInputPortletContainer() {
        if (inputPortletContainer == null) {
            inputPortletContainer = new PortletContainer();
        }
        return inputPortletContainer;
    }

    /**
     * Gets the portal that contains the output portelts.
     * 
     * @return the Portal
     */
    public static PortletContainer getOutputPortletContainer() {
        if (outputPortletContainer == null) {
            outputPortletContainer = new PortletContainer();
        }
        return outputPortletContainer;
    }

    /**
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        // instantiates the viewport
        wsportalViewport = new WsPortalViewport();
        // add the viewport into the web page
        RootPanel.get("wsportal").add(wsportalViewport);
    }
}
