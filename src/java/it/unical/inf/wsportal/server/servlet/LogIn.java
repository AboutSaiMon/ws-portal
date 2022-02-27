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
package it.unical.inf.wsportal.server.servlet;

import it.unical.inf.wsportal.server.db.SqlStatement;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carmine Dodaro {carminedodaro@gmail.com}, Simone Spaccarotella {spa.simone@gmail.com}
 */
public class LogIn extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        String password = null;
        // if login is called by the servlet SignUp, then gets the username and password
        // parameters from the attribute of the request object
        if (request.getAttribute("redirect") != null) {
            username = (String) request.getAttribute("username");
            password = (String) request.getAttribute("password");
            // otherwise this servlet is called directly by an user, so it gets
            // the username and password parameter from the query string
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }
        SqlStatement statement = new SqlStatement();
        // performs the login
        boolean loggedIn = statement.logIn(username, password);
        statement.close();
        // if the user is valid, create the session
        if (loggedIn) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("x", "1");
            request.getRequestDispatcher("home.jsp").include(request, response);
        }
    }
}
