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
package it.unical.inf.wsportal.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.axis.wsdl.toJava.Emitter;

/**
 * Create a web service library, according to a WSDL file. This library can be used
 * from a generic client.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class Generator {

    // the generator of the source files
    private Emitter emitter;
    // the module that compile the sources file
    private Compiler compiler;
    // the wsdl file
    private String wsdlFile;

    /**
     * Create a new generator, pointing to the URL of the WSDL file.
     * @param wsdl the URL of the WSDL file
     */
    public Generator(String wsdl) {
        emitter = new Emitter();
        setJavaFilesFolder("");
        compiler = new Compiler();
        this.wsdlFile = wsdl;
    }

    /**
     * Get the package name.
     * 
     * @return the name of the auto generated package.
     */
    public String getPackageName() {
        return emitter.getPackageName();
    }

    /**
     * Says if the emitter package name is invalid or not.
     * @return true if the emitter package name is invalid.
     */
    private boolean isInvalidPackage() {
        return isInvalidPackageName(emitter.getPackageName());
    }

    /**
     * Says if the package name is invalid or not.
     * @param packageName the name of the package
     * @return true if the package name is invalid
     */
    public boolean isInvalidPackageName(String packageName) {
        if (packageName != null) {
            if (packageName.startsWith(".") || packageName.endsWith(".") || packageName.equals("")) {
                return true;
            }
            String[] split = packageName.split("[.]");
            for (String s : split) {
                if (isJavaKeyword(s) || containsInvalidChar(s)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Says if the <code>input</code> string is a Java reserved word.
     * @param input the string that has to be checked
     * @return true if the string is a Java keyword, such as:
     *      <p>
     *          double, float, long, int, short, byte, char, boolean,
     *          void, enum, true, false, null, if, else, switch, case,
     *          default, for, while, do, break, continue, return, goto,
     *          try, catch, finally, throws, throw, public, protected,
     *          private, static, final, native, const, volatile, transient,
     *          synchronized, package, abstract, class, interface, super,
     *          this, new, extends, implements, import, instanceof, strictfp,
     *          assert
     *      </p>
     */
    private boolean isJavaKeyword(String input) {
        if (input.equals("double")
                || input.equals("float")
                || input.equals("long")
                || input.equals("int")
                || input.equals("short")
                || input.equals("byte")
                || input.equals("char")
                || input.equals("boolean")
                || input.equals("void")
                || input.equals("enum")
                || input.equals("true")
                || input.equals("false")
                || input.equals("null")
                || input.equals("if")
                || input.equals("else")
                || input.equals("switch")
                || input.equals("case")
                || input.equals("default")
                || input.equals("for")
                || input.equals("while")
                || input.equals("do")
                || input.equals("break")
                || input.equals("continue")
                || input.equals("return")
                || input.equals("goto")
                || input.equals("try")
                || input.equals("catch")
                || input.equals("finally")
                || input.equals("throws")
                || input.equals("throw")
                || input.equals("public")
                || input.equals("protected")
                || input.equals("private")
                || input.equals("static")
                || input.equals("final")
                || input.equals("native")
                || input.equals("const")
                || input.equals("volatile")
                || input.equals("transient")
                || input.equals("synchronized")
                || input.equals("package")
                || input.equals("abstract")
                || input.equals("class")
                || input.equals("interface")
                || input.equals("super")
                || input.equals("this")
                || input.equals("new")
                || input.equals("extends")
                || input.equals("implements")
                || input.equals("import")
                || input.equals("instanceof")
                || input.equals("strictfp")
                || input.equals("assert")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Says if the <code>input</code> string contains invalid chars.
     * Only alphabetic chars and digit are allowed.
     * @param input the string that has to be checked
     * @return true if the string contains invalid chars
     */
    private boolean containsInvalidChar(String input) {
        if (input.matches("\\d.*") || (input.matches("\\W") && !input.equals("_")) || input.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates the java library that uses the WS described by the WSDL file.
     * @throws Exception
     */
    public void run() throws Exception {
        if (isInvalidPackage()) {
            generatePackageName(null);
        }
        emitter.run(wsdlFile);
        compiler.setSourceDir(emitter);
        compiler.compile();
    }

    /**
     * Add a root package to the hierarchy.
     * @param subpackage the root package
     */
    public void addRootPackage(String subpackage) {
        generatePackageName(subpackage);
    }

    /**
     * Generate the package name according to the URL of the WSDL file. If
     * <code>subPackage</code> is not null, it adds a root package to the hierarchy.
     * @param subPackage the root package.
     */
    private void generatePackageName(String subPackage) {
        /*
         * The package name is a string composed of two parts: the first portion of the string
         * is the host name backwards without the ".", replaced with the "_".
         * Whereas the second portion of it is the absolute file path
         * without the "/", replaced by the "_".
         */
        // with the URL class we can get the host name and the file path easily
        URL url = null;
        try {
            // retrieve the url of the service in order to create a valid
            // package name
            WsdlInfoRetreiver info = new WsdlInfoRetreiver(wsdlFile);
            url = new URI(info.getServiceUrl()).toURL();
            // if wsdlURL is a valid url then continue the generation
            // This is the string builder that store the package name
            StringBuilder packageName = new StringBuilder();
            // gets the host name
            String temp = url.getHost();
            // splits the string by the full stop
            String[] split = temp.split("[.]");
            // builds the package name with the backward splitted token
            for (int i = split.length - 1; i >= 0; i--) {
                packageName.append(split[i] + "_");
            }
            // gets the file path
            temp = url.getPath();
            // delete the first "/" char (because the absolute path begin with the slash)
            temp = temp.replaceFirst("/", "");
            // replace the remaining "/" with the "_"
            temp = temp.replaceAll("/", "_");
            // replace all the "." with the "_"
            temp = temp.replaceAll("[.]", "_");
            // append the last portion of the string to the builder
            packageName.append(temp);
            // sets the package name
            if (!isInvalidPackageName(subPackage)) {
                emitter.setPackageName(subPackage.toLowerCase() + "." + packageName.toString().toLowerCase());
            } else {
                emitter.setPackageName(packageName.toString().toLowerCase());
            }
        } catch (URISyntaxException ex) {
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
    }

    /**
     * Set the directory name where the ".java" files must be generated.
     * @param folderName the name of the folder
     */
    public void setJavaFilesFolder(String folderName) {
        emitter.setOutputDir(new File(folderName).getAbsolutePath());
    }

    /**
     * Get the absolute path of the directory where the ".java" files are stored.
     * @return the absolute folder path
     */
    public String getJavaFilesFolder() {
        return emitter.getOutputDir();
    }

    /**
     * Set the directory name where the ".class" files must be generated.
     * @param folderName the name of the folder
     */
    public void setClassesFolder(String folderName) {
        File folder = new File(folderName);
        folder.mkdirs();
        compiler.setOutputDir(folder.getAbsolutePath());
    }

    /**
     * Get the absolute path of the directory where the ".class" files are stored.
     * @return the absolute folder path
     */
    public String getClassesFolder() {
        return compiler.getOutputDir();
    }

    /**
     * Set the directory name where the ".jar" files are stored.
     * @param folderName the name of the library folder
     */
    public void setLibraryFolder(String folderName) {
        File folder = new File(folderName);
        folder.mkdirs();
        compiler.setLibDir(folder.getAbsolutePath());
    }

    /**
     * Get the absolute path of the directory where the ".jar" files are stored.
     * @return the absolute folder path
     */
    public String getLibraryFolder() {
        return compiler.getLibDir();
    }

    /**
     * Set the name of the working directory where the "javac" options files are stored.
     * @param folderName the name of the "javac" folder
     */
    public void setWorkingFolder(String folderName) {
        File folder = new File(folderName);
        folder.mkdirs();
        compiler.setWorkingDir(folder.getAbsolutePath());
    }

    /**
     * Get the absolute path of the working directory where the "javac" files are stored.
     * @return the absolute folder path
     */
    public String getWorkingFolder() {
        return compiler.getWorkingDir();
    }

    /**
     * Clean the source directory
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws InterruptedException
     */
    public void clean() throws IOException, FileNotFoundException, InterruptedException {
        compiler.cleanSources(emitter.getOutputDir());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        WsdlInfoRetreiver info = null;
        String toString = null;
        try {
            info = new WsdlInfoRetreiver(wsdlFile);
            toString = info.getServiceName() + "<>" + info.getServiceUrl() + "<>" + emitter.getPackageName();
        } catch (URISyntaxException ex) {
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        return toString;
    }
}
