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
package it.unical.inf.wsportal.server.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Establishes a connection to the data base, using an available connection from
 * the connection pool.
 *
 * @author Carmine Dodaro {carminedodaro@gmail.com}, Simone Spaccarotella {spa.simone@gmail.com}
 */
public class DataSourceConnection {

    private DataSource dataSource;

    /**
     * 
     * @throws NamingException
     */
    public DataSourceConnection() throws NamingException {
        InitialContext initialContext = new InitialContext();
        dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/wsportal");
    }

    /**
     * Get the connection to the database.
     * 
     * @return a java.sql.Connection object
     * @throws SQLException
     */
    public Connection getDatabaseConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
