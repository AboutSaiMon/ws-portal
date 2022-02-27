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

import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import it.unical.inf.wsportal.client.WSPortal;
import it.unical.inf.wsportal.client.util.ComponentID;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class OutputTabItem extends TabItem {

    private PortletContainer portal;

    public OutputTabItem() {
        super("Output");
        setID(ComponentID.OUTPUT_TAB_ITEM);
        setLayout(new FitLayout());
        portal = WSPortal.getOutputPortletContainer();
        add(portal);
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
}
