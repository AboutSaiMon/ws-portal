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

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public abstract class FoldersNFiles {

    private static String rootPath = null;
    private static final String CLASSES_FOLDER = "WEB-INF/classes/";
    private static final String LIB_FOLDER = "WEB-INF/lib/";
    private static final String SRC_FOLDER = "WEB-INF/src/";
    private static final String JAVAC_FOLDER = "WEB-INF/javac/";
    /**
     *
     */
    public static final String GENERATED_SERVICES = "webservices";

    /**
     * Set the absolute root path of the web application.
     * @param rootPath the absolute root path
     */
    public static void setRootPath(String rootPath) {
        FoldersNFiles.rootPath = rootPath;
    }

    /**
     * Get the absolute root path of the web application.
     * @return the absolute path
     */
    public static String getRootPath() {
        return rootPath;
    }

    /**
     * Gets the absolute path of the "<code>classes</code>" folder,
     * if and only if the root path is not null, otherwise it returns null.
     * @return the absolute path of the "<code>classes</code>" folder, or null
     */
    public static String getClassesPath() {
        return getAbsolute(CLASSES_FOLDER);
    }

    /**
     * Gets the absolute path of the "<code>lib</code>" folder,
     * if and only if the root path is not null, otherwise it returns null.
     * @return the absolute path of the "<code>lib</code>" folder, or null
     */
    public static String getLibPath() {
        return getAbsolute(LIB_FOLDER);
    }

    /**
     * Gets the absolute path of the "<code>src</code>" folder,
     * if and only if the root path is not null, otherwise it returns null.
     * The first time this method is invoked, the <code>src</code> folder
     * is created.
     * @return the absolute path of the "<code>src</code>" folder
     */
    public static String getSrcPath() {
        return getAbsolute(SRC_FOLDER);
    }

    /**
     * Gets the absolute path of the "<code>javac</code>" folder,
     * if and only if the root path is not null, otherwise it returns null.
     * The first time this method is invoked, the <code>javac</code> folder
     * is created.
     * @return the absolute path of the "<code>javac</code>" folder
     */
    public static String getJavacPath() {
        return getAbsolute(JAVAC_FOLDER);
    }

    /**
     * Gets the absolute path of a folder/file.
     * @param name the name of the folder/file
     * @return the absolute path
     */
    private static String getAbsolute(String name) {
        // return the absolute path of the "folderName",
        // if and only if the root path is not null
        if (rootPath != null) {
            return rootPath + name;
        } else {
            return null;
        }
    }
}
