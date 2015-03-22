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

import java.util.ArrayList;

/**
 * Describes a web service operation with its parameters.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class Operation {

    private String name;
    private String returnType;
    private ArrayList<String> parameters;

    /**
     * Creates a new operation.
     */
    public Operation() {
        returnType = "";
        parameters = new ArrayList<String>();
    }

    /**
     * Gets its parameters list.
     * 
     * @return a list of input parameters
     */
    public ArrayList<String> getParameters() {
        return parameters;
    }

    /**
     * Gets the parameter at the given index.
     * 
     * @param index the index of the list
     * @return an input parameter
     */
    public String getParameter(int index) {
        return parameters.get(index);
    }

    /**
     * Gets the size of the list.
     * 
     * @return an integer number that's the size of the list
     */
    public int getParameterNumber() {
        return parameters.size();
    }

    /**
     * Adds an input parameter to the operation.
     * 
     * @param parameterType the input parameter
     */
    public void addParameter(String parameterType) {
        parameters.add(parameterType);
    }

    /**
     * Gets the parameter name.
     * 
     * @return the name of the parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the parameter name.
     *
     * @param name the name of the parameter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the operation return type.
     *
     * @return the object type that this operation returns
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Sets the operation return type.
     * 
     * @param returnType the object type that this operation returns
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
