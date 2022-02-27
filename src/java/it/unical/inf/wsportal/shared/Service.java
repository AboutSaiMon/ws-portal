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
 * Describe a web service and its attributes.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class Service {

    private String url;
    private String wsdlUrl;
    private String name;
    private String packageName;
    private String description;
    private ArrayList<Operation> operations;

    /**
     * Creates an empty service.
     */
    public Service() {
        url = "";
        wsdlUrl = "";
        name = "";
        packageName = "";
        description = "";
        operations = new ArrayList<Operation>();
    }

    /**
     * Sets the service URL.
     * 
     * @param url the URL of the service.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the service URL.
     * 
     * @return the URL of the service
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the WSDL file URL.
     * 
     * @param wsdlUrl the URL of the WSDL file
     */
    public void setWsdlUrl(String wsdlUrl) {
        this.wsdlUrl = wsdlUrl;
    }

    /**
     * Gets the WSDL file URL.
     *
     * @return the URL of the WSDL file
     */
    public String getWsdlUrl() {
        return wsdlUrl;
    }

    /**
     * Sets the service name.
     *
     * @param name the name of the service
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the service name.
     *
     * @return the name of the service
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the package name.
     * 
     * @param packageName the name of the package where the related client library is stored
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Gets the package name.
     * 
     * @return the name of the package where the related client library is stored
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets the description of the web service.
     * 
     * @param description a text that describe the web service
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the web service.
     *
     * @return a text that describe the web service
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the list of service operations.
     *
     * @return a list of operations
     */
    public ArrayList<Operation> getOperations() {
        return operations;
    }

    /**
     * Gets an operation at the given index.
     * 
     * @param index the index of the list
     * @return an operation of this list
     */
    public Operation getOperation(int index) {
        return operations.get(index);
    }

    /**
     * Gets the size of the list.
     *
     * @return an integer that describe the size of the list
     */
    public int getOperationsNumber() {
        return operations.size();
    }

    /**
     * Adds an operation to the web service.
     *
     * @param operation a web service operation
     */
    public void addOperation(Operation operation) {
        operations.add(operation);
    }
}
