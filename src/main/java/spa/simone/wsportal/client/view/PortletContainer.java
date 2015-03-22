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

import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import it.unical.inf.wsportal.client.util.ComponentID;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class PortletContainer extends Portal {

    private static int instance = 0;
    private int counter = -1;
    private static final int COLUMNS = 3;
    private Map<String, Portlet> map;

    /**
     * 
     */
    public PortletContainer() {
        super(COLUMNS);
        setID(ComponentID.PORTLET_CONTAINER + "-" + (++instance));
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
     * 
     */
    private void setProperties() {
        setBorders(true);
        setColumnWidth(0, .33);
        setColumnWidth(1, .33);
        setColumnWidth(2, .33);
    }

    private void setComponents() {
        map = new HashMap<String, Portlet>();
    }

    public void add(Portlet portlet) {
        counter = (counter + 1) % 3;
        super.add(portlet, counter);
        map.put(portlet.getId(), portlet);
        portlet.el().fadeIn(FxConfig.NONE);
    }

    private Portlet get(String id) {
        return map.get(id);
    }

    public Portlet getPortlet(Component component) {
        String id = component.getId().split("-")[1];
        return get(ComponentID.INPUT_PORTLET + "-" + id);
    }
}
