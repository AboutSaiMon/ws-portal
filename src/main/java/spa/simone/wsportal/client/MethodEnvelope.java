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
package it.unical.inf.wsportal.client;

import java.util.ArrayList;

/**
 * Is a Bean that describe the information about a method.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class MethodEnvelope {

    private String serviceUrl;
    private String methodName;
    private ArrayList<TypeValue> list;

    /**
     * Create a new envelope for the method.
     */
    public MethodEnvelope() {
        list = new ArrayList<TypeValue>();
    }

    /**
     * Set the URL of the service.
     * 
     * @param serviceUrl the URL of the service
     */
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * Gets the URL of the service.
     * 
     * @return the URL of the service.
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * Sets the name of the method.
     *
     * @param methodName the method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets the name of the method.
     * 
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Adds a new parameter to the method.
     * 
     * @param type the parameter type
     * @param value the parameter value typed by the user.
     */
    public void addParameter(String type, Object value) {
        list.add(new TypeValue(type, value));
    }

    /**
     * Gets the parameter at the given index.
     * 
     * @param index the index of the list of parameters.
     * @return an object that describe the couple [type, value]
     */
    public TypeValue getParameter(Integer index) {
        return list.get(index);
    }

    /**
     * Gets the parameters list.
     *
     * @return a list of parameters
     */
    public ArrayList<TypeValue> getParameters() {
        return list;
    }
}
