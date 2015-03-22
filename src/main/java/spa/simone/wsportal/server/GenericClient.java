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
package spa.simone.wsportal.server;

import it.unical.inf.wsportal.client.MethodEnvelope;
import it.unical.inf.wsportal.client.TypeValue;
import it.unical.inf.wsportal.shared.Operation;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class GenericClient {

    /**
     * Get the class object of the given package, if the file name matches
     * with the given regular expression <code>regex</code>.
     * 
     * @param wsdlFile the URL/path of the WSDL file.
     * @return the Class object
     */
    private Class getClassByWsdl(String wsdlFile) {
        WsdlInfoRetreiver info = null;
        try {
            info = new WsdlInfoRetreiver(wsdlFile);
        } catch (URISyntaxException ex) {
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        String packageName = FoldersNFiles.GENERATED_SERVICES + "." + info.getPackageName();
        String serviceName = getNormalizedName(info.getServiceName());
        Class serviceClass = null;
        String className = null;
        try {
            className = packageName + "." + serviceName + "_PortType";
            serviceClass = Class.forName(className);
        } catch (ClassNotFoundException ex1) {
            try {
                className = packageName + "." + serviceName;
                serviceClass = Class.forName(className);
            } catch (ClassNotFoundException ex2) {
            }
        }
        return serviceClass;
    }

    /**
     * 
     * @param packageName
     * @return
     */
    private Class getClassByPackage(String packageName, String regex) {
        // create the string that describe the folder where the classes are located
        StringBuilder builder = new StringBuilder(FoldersNFiles.getClassesPath());
        builder.append(File.separator);
        builder.append(packageName.replaceAll("[.]", File.separator));
        String folder = builder.toString();
        // handle the directory
        File serviceFolder = new File(folder);
        // get a list of files
        String[] files = serviceFolder.list();
        Class serviceClass = null;
        boolean found = false;
        // search the xxxPortType.class file
        for (String file : files) {
            // if it does exist
            if (file.endsWith(regex)) {
                try {
                    // load this class
                    serviceClass = Class.forName(packageName + "." + file.replaceAll(".class", ""));
                } catch (ClassNotFoundException ex) {
                } finally {
                    // set the flag found to true and exit from the cycle.
                    found = true;
                    break;
                }
            }
        }
        // if the xxxPortType.class file it wasn't find
        if (!found) {
            // search the xxxService.class file
            for (String file : files) {
                // if it does exist
                if (file.endsWith("Service.class")) {
                    try {
                        // load this class
                        serviceClass = Class.forName(packageName + "." + file.replaceAll("Service.class", ""));
                    } catch (ClassNotFoundException ex) {
                    }
                }
            }
        }
        return serviceClass;
    }

    public String invokeMethod(String packageName, MethodEnvelope envelope, String portName) throws IOException {
        Object result = "nothing";
        try {
            // locator class
            Class locatorClass = getClassByPackage(packageName, "Locator.class");
            // locator object
            Object locator = locatorClass.newInstance();
            Method getService = locatorClass.getMethod("get" + portName, new Class[]{});
            Object service = getService.invoke(locator, new Object[]{});
            Class serviceClass = Class.forName(service.getClass().getCanonicalName());
            ArrayList<TypeValue> parameters = envelope.getParameters();
            Class[] formalParameters = new Class[parameters.size()];
            Object[] actualParameters = new Object[parameters.size()];
            for (int i = 0; i < formalParameters.length; i++) {
                formalParameters[i] = Class.forName(parameters.get(i).getType());
                actualParameters[i] = parameters.get(i).getValue();
            }
            Method method = serviceClass.getMethod(envelope.getMethodName(), formalParameters);
            result = method.invoke(service, actualParameters);
        } catch (ClassNotFoundException ex) {
            result = "Class not found";
        } catch (IllegalArgumentException ex) {
            result = "Illegal argument";
        } catch (InvocationTargetException ex) {
            result = "Invocation Target exception";
        } catch (InstantiationException ex) {
            result = "Instantiation exception";
        } catch (IllegalAccessException ex) {
            result = "Illegal acces";
        } catch (NoSuchMethodException ex) {
            result = "No such method";
        } catch (SecurityException ex) {
            result = "Security exception";
        }
        return result.toString();
    }

    /**
     *
     * @param input
     * @return
     */
    private boolean isValidUrl(String input) {
        boolean isValid;
        try {
            URL url = new URL(input);
            isValid = true;
        } catch (MalformedURLException ex) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Get the service name without the last string "Service"
     * @param serviceName
     * @return
     */
    private String getNormalizedName(String serviceName) {
        StringBuilder builder = new StringBuilder(serviceName);
        builder.reverse();
        builder = new StringBuilder(builder.substring(7, builder.length()));
        return builder.reverse().toString();
    }

    /**
     * Get all the public service methods. This method accept a string that's
     * the package name where the service classes are stored.
     * 
     * @param packageName the package of the service
     * @return a list of the web service operations
     */
    public ArrayList<Operation> allServiceMethods(String packageName) {
        Class service = getClassByPackage(packageName, "PortType.class");
        // init the list
        ArrayList<Operation> list = new ArrayList<Operation>();
        // if isn't null
        if (service != null) {
            // gets all its methods
            Method[] methods = service.getDeclaredMethods();
            Operation operation = null;
            for (Method method : methods) {
                // if the method is public and abstract
                if (method.getModifiers() == (Modifier.PUBLIC | Modifier.ABSTRACT)) {
                    // create a new operation instance
                    operation = new Operation();
                    // get its input parameters
                    Class[] parameters = method.getParameterTypes();
                    // set the name
                    operation.setName(method.getName());
                    // set the return type
                    operation.setReturnType(method.getReturnType().getName());
                    // set the input parameters if any
                    for (Class parameter : parameters) {
                        // adds the parameter type
                        operation.addParameter(parameter.getName());
                    }
                    // add the operation to the list
                    list.add(operation);
                }
            }
        }
        return list;
    }
}
