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
package it.unical.inf.wsportal.client.util;

import com.extjs.gxt.ui.client.widget.Label;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import it.unical.inf.wsportal.client.callback.UpdateTreeStore;
import it.unical.inf.wsportal.client.callback.GetUsername;
import it.unical.inf.wsportal.shared.SharedStuff;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class Session {

    /**
     *
     * @param label
     */
    public static void setUsername(Label label) {
        String params = "method=" + SharedStuff.BRIDGE_GET_USERNAME;
        AsynchRequest request = new AsynchRequest(RequestBuilder.POST, "../bridge", params);
        request.setCallback(new GetUsername(label));
        try {
            request.send();
        } catch (RequestException ex) {
            Toolkit.showException(ex);
        }
    }

    /**
     *
     * @param wsTree
     */
    public static void updateTreeStore() {
        String params = "method=" + SharedStuff.BRIDGE_GET_TREE_STORE;
        AsynchRequest async = new AsynchRequest(AsynchRequest.POST, "../bridge", params);
        async.setCallback(new UpdateTreeStore());
        try {
            async.send();
        } catch (RequestException ex) {
            Toolkit.showException(ex);
        }
    }
}
