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
package it.unical.inf.wsportal.client.view;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import it.unical.inf.wsportal.client.util.ComponentID;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class WsPortalViewport extends Viewport {

    /* ***** layout objects ***** */
    // layout
    private BorderLayout layout;
    // layout constraints
    private BorderLayoutData northConstraint;
    private BorderLayoutData westConstraint;
    private BorderLayoutData centerConstraint;

    /* ***** components ***** */
    // is the panel placed at the top of the viewport
    private NorthSideContainer barPanel;
    // contains hidden panels that show the added services
    private LeftSideContainer servicePanelContainer;
    // contains tabs that serve to interact with the web services
    private CenterSideContainer serviceInteractionContainer;

    /* *****  ***** */
    /**
     * Creates a new custom viewport. It will be the
     * content panel of the portal.
     */
    public WsPortalViewport() {
        super();
        setID(ComponentID.WS_PORTAL_VIEWPORT);
        setLayout();
        setLayoutConstraints();
        setComponents();
    }

    /**
     * Sets the id and the item id.
     *
     * @param id a string which represent the component univocally.
     */
    private void setID(String id) {
        setId(id);
        setItemId(id);
    }

    /**
     * Sets the border layout.
     */
    private void setLayout() {
        layout = new BorderLayout();
        setLayout(layout);
    }

    /**
     * Set the constraints for each side of the
     * border layout.
     */
    private void setLayoutConstraints() {
        // 5% of the area
        northConstraint = new BorderLayoutData(LayoutRegion.NORTH, 0.05F);
        // 20% of the area and a floating size from 180px to 400px
        westConstraint = new BorderLayoutData(LayoutRegion.WEST, 0.2F, 180, 600);
        westConstraint.setCollapsible(true);
        westConstraint.setSplit(true);
        // the rest of the area
        centerConstraint = new BorderLayoutData(LayoutRegion.CENTER);
    }

    /**
     * Sets the components of this container.
     */
    private void setComponents() {
        barPanel = new NorthSideContainer();
        add(barPanel, northConstraint);
        servicePanelContainer = new LeftSideContainer();
        add(servicePanelContainer, westConstraint);
        serviceInteractionContainer = new CenterSideContainer();
        add(serviceInteractionContainer, centerConstraint);
    }
}
