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
package it.unical.inf.wsportal.shared;

/**
 * This interface contains constants used by both the client and the server.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public interface SharedStuff {

    // used by the bridge
    public static final String BRIDGE_GET_USERNAME = "getUsername";
    public static final String BRIDGE_GET_TREE_STORE = "getTreeStore";
    // used by the form
    public static final String FORM_URL = "url";
    public static final String FORM_FILE = "file";
    public static final String FORM_FIELD = "field";
    // used by the MessageEncoder/MessageDecoder classes
    public static final String RESULT = "result";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String PACKAGE = "package";
    public static final String DESCRIPTION = "description";
    public static final String OPERATIONS = "operations";
    public static final String OPERATION = "operation";
    public static final String PARAM = "param";
    public static final String TYPE = "type";
    public static final String ID = "id";
    public static final String SIZE = "size";
    public static final String RETURN_TYPE = "return-type";
    public static final String SERVICE = "service";
    public static final String MESSAGE = "message";
    // used by the XMLSerializer
    public static final String ENVELOPE = "envelope";
    public static final String SERVICE_URL = "service-url";
    public static final String METHOD_NAME = "method-name";
    public static final String METHOD_PARAMETER = "method-parameter";
    public static final String VALUE = "value";
}
