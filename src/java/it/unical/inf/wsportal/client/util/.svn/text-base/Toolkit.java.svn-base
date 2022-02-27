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

import com.extjs.gxt.ui.client.widget.MessageBox;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class Toolkit {

    /**
     *
     * @param url
     * @return
     */
    public static boolean isValidUrl(String url) {
        return true;
    }

    /**
     *
     * @param ex
     */
    public static void showException(Exception ex) {
        MessageBox.alert("Exception", ex.getMessage(), null).setIcon(MessageBox.ERROR);
    }

    /**
     *
     * @param message
     */
    public static void showWarning(String message) {
        MessageBox.alert("Warning", message, null);
    }

    /**
     *
     * @param ex
     */
    public static void showServerError(Throwable ex) {
        MessageBox.alert("Server Error", ex.getMessage(), null).setIcon(MessageBox.ERROR);
    }

    /**
     *
     * @param message
     */
    public static void showInfo(String message) {
        MessageBox.info("Info", message, null);
    }
}
