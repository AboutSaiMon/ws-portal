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

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import it.unical.inf.wsportal.client.controller.Controller;
import it.unical.inf.wsportal.client.model.WsTreeItem;
import it.unical.inf.wsportal.client.util.ComponentID;
import java.util.List;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class WsTree extends TreePanel<ModelData> {

    /**
     * 
     */
    public WsTree() {
        this(new TreeStore<ModelData>());
    }

    /**
     * 
     * @param store
     */
    public WsTree(TreeStore<ModelData> store) {
        super(store);
        init();
    }

    /**
     * 
     */
    private void init() {
        setID(ComponentID.WS_TREE);
        setIconProvider(new ModelIconProvider<ModelData>() {

            public AbstractImagePrototype getIcon(ModelData model) {
                if (model.get("icon") != null) {
                    return IconHelper.createStyle((String) model.get("icon"));
                } else {
                    return null;
                }
            }
        });
        setDisplayProperty("name");
        addListener(Events.OnDoubleClick, Controller.getInstance());
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
     * @param store
     */
    public void setStore(TreeStore<ModelData> store) {
        this.store = store;
    }

    /**
     * 
     * @return
     */
    public TreeStore<ModelData> getStore() {
        return this.store;
    }

    /**
     *
     * @param service
     */
    public void addService(WsTreeItem service) {
        store.add(service, true);
        store.sort("name", SortDir.ASC);
    }

    /**
     *
     * @param serviceName
     */
    public void addService(String serviceName) {
        addService(new WsTreeItem(serviceName, WsTreeItem.SERVICE));
    }

    /**
     *
     * @param serviceName
     * @param operationName
     */
    public void addOperation(String serviceName, String operationName) {
        WsTreeItem service = (WsTreeItem) store.findModel("name", serviceName);
        addOperation(service, new WsTreeItem(operationName, WsTreeItem.OPERATION));
    }

    /**
     *
     * @param service
     * @param operation
     */
    public void addOperation(WsTreeItem service, WsTreeItem operation) {
        store.add(service, operation, false);
        store.sort("name", SortDir.ASC);
    }

    /**
     *
     * @param service
     * @param operations
     */
    public void addOperations(WsTreeItem service, List<ModelData> operations) {
        store.add(service, operations, false);
        store.sort("name", SortDir.ASC);
    }

    /**
     * 
     * @return
     */
    public WsTreeItem getSelectedItem() {
        return (WsTreeItem) getSelectionModel().getSelectedItem();
    }

    /**
     * 
     * @param item
     * @return
     */
    public WsTreeItem getServiceOf(WsTreeItem item) {
        return (WsTreeItem) store.getParent(item);
    }
}
