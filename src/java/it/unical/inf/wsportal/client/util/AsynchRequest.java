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

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class AsynchRequest extends RequestBuilder {

    /**
     * Create an object that performs asynchronous call to the server.
     * 
     * @param method the HTTP method: GET or POST
     * @param url the URL of the server side component
     * @param params the query string of the URL
     */
    public AsynchRequest(AsynchRequest.Method method, String url, String params) {
        super(method, URL.encode(url));
        if (method.toString().equals(POST.toString())) {
            setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            if (params != null) {
                setHeader("Content-length", String.valueOf(params.length()));
            } else {
                setHeader("Content-length", "0");
            }
            setHeader("Connection", "close");
        }
        setRequestData(params);
    }
}
