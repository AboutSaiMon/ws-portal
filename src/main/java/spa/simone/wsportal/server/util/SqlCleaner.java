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
package spa.simone.wsportal.server.util;

import java.util.regex.Pattern;

/**
 * 
 * @author Carmine Dodaro {carminedodaro@gmail.com}, Simone Spaccarotella {spa.simone@gmail.com}
 */
public class SqlCleaner {

    /**
     * Clean the SQL statement.
     * 
     * @param query the SQL statement
     * @return the cleaned SQL string
     */
    public static String clean(String query) {
        String cleanedQuery = query;
        cleanedQuery = cleanedQuery.replaceAll("[^\\d\\w]+", "");
        return cleanedQuery;
    }

    /**
     * Says whether the SQL statement is clean or not.
     * 
     * @param text the text to clean
     * @return true if the text doesn't contain invalid SQL character
     */
    public static boolean isClean(String text) {
        return Pattern.matches("[\\d\\w]+", text);
    }

    /**
     * Escape the "'" character.
     *
     * @param query the SQL statement
     * @return the SQL string escaped
     */
    public static String cleanerSQLInjection(String query) {
        String cleanedQuery = query;
        cleanedQuery = cleanedQuery.replaceAll("'", "`");
        return cleanedQuery;
    }
}
