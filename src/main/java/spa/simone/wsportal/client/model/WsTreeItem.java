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
package it.unical.inf.wsportal.client.model;

import com.extjs.gxt.ui.client.data.BaseModelData;
import it.unical.inf.wsportal.client.util.CSSIconImage;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class WsTreeItem extends BaseModelData {

    private int type;
    private static int classCounter = 0;
    public static final int SERVICE = 1;
    public static final int OPERATION = 2;

    /**
     *
     */
    public WsTreeItem() {
        this(null);
    }

    /**
     *
     * @param name
     */
    public WsTreeItem(String name) {
        this(name, 0);
    }

    /**
     *
     * @param name
     * @param type
     */
    public WsTreeItem(String name, int type) {
        super();
        classCounter++;
        setId(name.toLowerCase() + "-" + classCounter);
        setName(name);
        setType(type);
    }

    /**
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
        if (type == SERVICE) {
            setIcon(CSSIconImage.SERVICE_CLOSED);
        } else if (type == OPERATION) {
            setIcon(CSSIconImage.OPERATION);
        } else {
            setIcon("");
        }
    }

    /**
     *
     * @return
     */
    public int getType() {
        return this.type;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        set("name", name);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return get("name");
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        set("id", id);
    }

    /**
     *
     * @return
     */
    public String getId() {
        return get("id");
    }

    /**
     *
     * @param icon
     */
    public void setIcon(String icon) {
        set("icon", icon);
    }

    /**
     * 
     * @return
     */
    public String getIcon() {
        return get("icon");
    }
}
