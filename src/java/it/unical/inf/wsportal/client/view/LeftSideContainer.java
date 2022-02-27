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

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import it.unical.inf.wsportal.client.util.ComponentID;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class LeftSideContainer extends ContentPanel {

    private RowLayout layout;
    /* the layout constrains */
    private RowData wsPanelConstraint;
    private RowData addWsdlPanelConstraint;
    private WsPanel webServicesPanel;
    private AddWsdlPanel addWsdlPanel;

    /**
     * 
     */
    public LeftSideContainer() {
        super();
        setID(ComponentID.LEFT_SIDE_CONTAINER);
        setProperties();
        setLayout();
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
     * Sets the properties of this container.
     */
    private void setProperties() {
        setHeading("Web Services Panel");
    }

    /**
     * Sets the row layout.
     */
    private void setLayout() {
        layout = new RowLayout(Orientation.VERTICAL);
        setLayout(layout);
        wsPanelConstraint = new RowData(1, 0.9, new Margins(1));
        addWsdlPanelConstraint = new RowData(1, 0.1, new Margins(1));
    }

    /**
     * Sets the components of this container.
     */
    private void setComponents() {
        webServicesPanel = new WsPanel();
        add(webServicesPanel, wsPanelConstraint);
        addWsdlPanel = new AddWsdlPanel();
        add(addWsdlPanel, addWsdlPanelConstraint);
    }
}
