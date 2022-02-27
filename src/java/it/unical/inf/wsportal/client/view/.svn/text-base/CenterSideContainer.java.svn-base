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

import com.extjs.gxt.ui.client.widget.TabPanel;
import it.unical.inf.wsportal.client.util.ComponentID;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class CenterSideContainer extends TabPanel {

    private InputTabItem inputTab;
    private OutputTabItem outputTab;

    /**
     *
     */
    public CenterSideContainer() {
        super();
        setID(ComponentID.CENTER_SIDE_CONTAINER);
        setProperties();
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
     * Sets the properties of this container
     */
    private void setProperties() {
        setTabScroll(true);
        setAnimScroll(true);
        setCloseContextMenu(true);
    }

    /**
     * Sets the components of this container.
     */
    private void setComponents() {
        inputTab = new InputTabItem();
        inputTab.setTabIndex(0);
        add(inputTab);

        outputTab = new OutputTabItem();
        outputTab.setTabIndex(1);
        add(outputTab);
    }
}
