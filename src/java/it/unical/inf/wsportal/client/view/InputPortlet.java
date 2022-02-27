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

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import it.unical.inf.wsportal.client.controller.Controller;
import it.unical.inf.wsportal.client.util.ComponentID;
import java.util.ArrayList;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class InputPortlet extends Portlet {

    private static int instance = 0;
    private ColumnModel columnModel;
    private ColumnConfig typeColumn;
    private ColumnConfig paramColumn;
    private CheckColumnConfig hideColumn;
    private InputGrid grid;
    private ListStore<BaseModelData> listStore;
    private String serviceUrl;

    /**
     * 
     */
    public InputPortlet(String heading) {
        super();
        instance++;
        setID(ComponentID.INPUT_PORTLET + "-" + instance);
        setProperties(heading);
        setComponents();
    }

    public void setServiceUrl(String url) {
        serviceUrl = url;
    }

    public String getServiceUrl() {
        return serviceUrl;
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
     * @param heading
     */
    private void setProperties(String heading) {
        setHeading(heading);
        setCollapsible(true);
        setAnimCollapse(true);
        setHeight(200);
        setLayout(new FitLayout());
    }

    /**
     * 
     */
    private void setComponents() {
        ArrayList<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        typeColumn = new ColumnConfig();
        typeColumn.setId("type");
        typeColumn.setHeader("Input type");
        typeColumn.setWidth(100);
        typeColumn.setSortable(false);
        configs.add(typeColumn);

        paramColumn = new ColumnConfig();
        paramColumn.setId("param");
        paramColumn.setHeader("Input value");
        paramColumn.setWidth(100);
        TextField<Object> textField = new TextField<Object>();
        paramColumn.setEditor(new CellEditor(textField));
        paramColumn.setSortable(false);
        configs.add(paramColumn);


        hideColumn = new CheckColumnConfig();
        hideColumn.setId("hide");
        hideColumn.setHeader("Hide input");
        hideColumn.setWidth(100);
        CheckBox checkBox = new CheckBox();
        checkBox.addListener(Events.OnChange, Controller.getInstance());
        hideColumn.setEditor(new CellEditor(checkBox));
        hideColumn.setSortable(false);
        configs.add(hideColumn);

        columnModel = new ColumnModel(configs);
        listStore = new ListStore<BaseModelData>();

        grid = new InputGrid(listStore, columnModel);
        add(grid);
        
        CloseButton close = new CloseButton("x-tool-close", this);
        getHeader().addTool(close);
    }

    /**
     * 
     * @param type
     * @param param
     */
    public void addParameter(String type, String param) {
        BaseModelData parameter = new BaseModelData();
        parameter.set("id", param);
        parameter.set("type", type);
        parameter.set("param", param);
        parameter.set("hide", false);
        listStore.add(parameter);
    }

    public ColumnModel getColumnModel() {
        return columnModel;
    }

    public ListStore<BaseModelData> getListStore() {
        return listStore;
    }

    public ColumnConfig getTypeColumn() {
        return typeColumn;
    }

    public ColumnConfig getParamColumn() {
        return paramColumn;
    }

    public CheckColumnConfig getHideColumn() {
        return hideColumn;
    }

    public InputGrid getInputGrid() {
        return grid;
    }
}
