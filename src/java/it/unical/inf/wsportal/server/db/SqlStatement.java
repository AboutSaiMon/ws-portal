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

import it.unical.inf.wsportal.shared.Service;
import it.unical.inf.wsportal.server.util.Hash;
import it.unical.inf.wsportal.server.util.SqlCleaner;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.naming.NamingException;

/**
 * 
 * @author Carmine Dodaro {carminedodaro@gmail.com}, Simone Spaccarotella {spa.simone@gmail.com}
 */
public class SqlStatement {

    private DataSourceConnection dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    /**
     * Create an object that performs SQL queries and data manipulations.
     */
    public SqlStatement() {
        try {
            dataSource = new DataSourceConnection();
            connection = dataSource.getDatabaseConnection();
        } catch (SQLException ex) {
        } catch (NamingException ex) {
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Log the user into the system.
     * 
     * @param username the name of the user
     * @param password the pass key of the user
     * @return true if the user is valid
     */
    public boolean logIn(String username, String password) {
        boolean result = false;
        try {
            username = username.toLowerCase();
            username = SqlCleaner.clean(username);
            statement = connection.prepareStatement("select * from user where username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                String dbSalt = resultSet.getString("salt");
                String cryptoPassword = Hash.sha1(username + password + dbSalt);
                if (dbPassword.equals(cryptoPassword)) {
                    result = true;
                }
            }
        } catch (NoSuchAlgorithmException ex) {
        } catch (UnsupportedEncodingException ex) {
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return result;
    }

    /**
     * Sign the user up into the system.
     *
     * @param username the username
     * @param password the pass key
     * @param name the name of the user
     * @param surname the surname of the user
     * @return true if there isn't another user with the same name
     */
    public boolean signUp(String username, String password, String name, String surname) {
        boolean result = false;
        username = username.toLowerCase();
        // check if the username doesn't have strange character
        if (SqlCleaner.isClean(username)) {
            try {
                // generate the random string
                String salt = generateSalt();
                // rigenerate the hashed password
                password = Hash.sha1(username + password + salt);
                // prepare the statement
                statement = connection.prepareStatement("insert into user values(?,?,?,?,?)");
                // set the parameters
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, name);
                statement.setString(4, surname);
                statement.setString(5, salt);
                // execute the update - if a tuple with the same key exists, this statement
                // will throws an SQLException and the result will be false
                statement.executeUpdate();
                result = true;
            } catch (NoSuchAlgorithmException ex) {
            } catch (UnsupportedEncodingException ex) {
            } catch (SQLException ex) {
            } finally {
                closeResources();
            }
        }
        return result;
    }

    /**
     * Add a new service into the DB.
     *
     * @param serviceUrl the URL where the web service is stored
     * @param serviceName the name of the web service
     * @param description a short description of what the web service does
     * @param packageName the name of the auto generated package
     * @return true if there isn't another web service with the same URL
     */
    public boolean addService(Service service) {
        boolean result = false;
        // clean the variable in order to prevents the SQL injection
        String url = SqlCleaner.cleanerSQLInjection(service.getUrl());
        String wsdlUrl = SqlCleaner.cleanerSQLInjection(service.getWsdlUrl());
        String name = SqlCleaner.cleanerSQLInjection(service.getName());
        String packageName = SqlCleaner.cleanerSQLInjection(service.getPackageName());
        String description = SqlCleaner.cleanerSQLInjection(service.getDescription());
        try {
            // prepare the statement
            statement = connection.prepareStatement("insert into service values(?,?,?,?,?)");
            // set the parameters
            statement.setString(1, url);
            statement.setString(2, wsdlUrl);
            statement.setString(3, name);
            statement.setString(4, packageName);
            statement.setString(5, description);
            // execute the update - if a tuple with the same key exists, this statement
            // will throws an SQLException and the result will be false
            statement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return result;
    }

    /**
     * Remove the web service from the DB.
     *
     * @param serviceUrl the URL of the web service.
     * @return return true if the web service with the specified <code>serviceUrl</code>
     * is found
     */
    public boolean deleteService(String serviceUrl) {
        boolean result = false;
        serviceUrl = SqlCleaner.cleanerSQLInjection(serviceUrl);
        try {
            // prepare the statement
            statement = connection.prepareStatement("delete from service where service_url=?");
            // execute the update
            statement.setString(1, serviceUrl);
            int row = statement.executeUpdate();
            if (row == 1) {
                result = true;
            }
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return result;
    }

    /**
     * Get a list of all the services stored into the DB.
     *
     * @return the list of all the web services
     */
    public ArrayList<Service> getAllServices() {
        ArrayList<Service> list = new ArrayList<Service>();
        try {
            statement = connection.prepareStatement("select * from service");
            resultSet = statement.executeQuery();
            Service service = null;
            while (resultSet.next()) {
                service = new Service();
                service.setUrl(resultSet.getString("url"));
                service.setWsdlUrl("wsdlUrl");
                service.setName(resultSet.getString("name"));
                service.setPackageName(resultSet.getString("packageName"));
                service.setDescription(resultSet.getString("description"));
                list.add(service);
            }
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Service> getAllUserServices(String user) {
        ArrayList<Service> list = new ArrayList<Service>();
        try {
            String query = "select S.url, S.wsdlUrl, S.name, S.packageName, S.description "
                    + "from service S, user_service US "
                    + "where S.url = US.service_url and US.username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, user);
            resultSet = statement.executeQuery();
            Service service = null;
            while (resultSet.next()) {
                service = new Service();
                service.setUrl(resultSet.getString("url"));
                service.setWsdlUrl(resultSet.getString("wsdlUrl"));
                service.setName(resultSet.getString("name"));
                service.setPackageName(resultSet.getString("packageName"));
                service.setDescription(resultSet.getString("description"));
                list.add(service);
            }
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     * Get the service with the specified <code>serviceUrl</code>.
     *
     * @param url the URL of the web service
     * @return the web service
     */
    public Service getService(String url) {
        url = SqlCleaner.cleanerSQLInjection(url);
        Service service = null;
        try {
            statement = connection.prepareStatement("select * from service where url = ?");
            statement.setString(1, url);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                service = new Service();
                service.setUrl(url);
                service.setWsdlUrl(resultSet.getString("wsdlUrl"));
                service.setName(resultSet.getString("name"));
                service.setPackageName(resultSet.getString("packageName"));
                service.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return service;
    }

    /**
     * Add to the user with the <code>username</code> the web service with the
     * specified <code>serviceUrl</code>.
     *
     * @param username the name of the user
     * @param serviceUrl the URL of the web service
     * @return true if the user doesn't have this web service yet.
     */
    public boolean addUserService(String username, String serviceUrl) {
        boolean result = false;
        username = SqlCleaner.clean(username);
        serviceUrl = SqlCleaner.cleanerSQLInjection(serviceUrl);
        try {
            statement = connection.prepareStatement("insert into user_service values(?,?)");
            statement.setString(1, username);
            statement.setString(2, serviceUrl);
            statement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return result;
    }

    /**
     * Remove from the user with the <code>username</code> the web service with the
     * specified <code>serviceUrl</code>.
     *
     * @param username the name of the user
     * @param serviceUrl the URL of the web service
     * @return true if the user has this web service.
     */
    public boolean deleteUserService(String username, String serviceUrl) {
        boolean result = false;
        username = SqlCleaner.clean(username);
        serviceUrl = SqlCleaner.cleanerSQLInjection(serviceUrl);
        try {
            statement = connection.prepareStatement("delete from user_service where username = ? and service_url = ?");
            statement.setString(1, username);
            statement.setString(2, serviceUrl);
            int row = statement.executeUpdate();
            if (row == 1) {
                result = true;
            }
        } catch (SQLException ex) {
        } finally {
            closeResources();
        }
        return result;
    }

    /**
     * Generate a random string in order to add noise into the password digest.
     * 
     * @return a random string
     */
    private String generateSalt() {
        Random r = new Random();
        int salt = r.nextInt();
        if (salt < 0) {
            salt = salt * (-1);
        }
        salt = salt + 10000000;
        return String.valueOf(salt);
    }

    /**
     * Close the resources.
     */
    private void closeResources() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
            } finally {
                resultSet = null;
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
            } finally {
                statement = null;
            }
        }
    }

    /**
     * Release the connection.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            } finally {
                connection = null;
            }
        }
    }
}
