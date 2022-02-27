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

import it.unical.inf.wsportal.server.GenericClient;
import it.unical.inf.wsportal.server.util.MessageEncoder;
import it.unical.inf.wsportal.shared.Service;
import it.unical.inf.wsportal.server.db.SqlStatement;
import it.unical.inf.wsportal.shared.Operation;
import it.unical.inf.wsportal.shared.SharedStuff;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class Bridge extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        String username = getUsername(request, response);
        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        if (method.equals(SharedStuff.BRIDGE_GET_USERNAME)) {
            response.setContentType("text/plain");
            out = response.getWriter();
            out.println(username);
        } else if (method.equals(SharedStuff.BRIDGE_GET_TREE_STORE)) {
            response.setContentType("text/xml");
            out = response.getWriter();
            getTreeStore(username, out);
        }
        out.close();
    }

    private String getUsername(HttpServletRequest request, HttpServletResponse response) {
        return (String) request.getSession().getAttribute("username");
    }

    private void getTreeStore(String username, PrintWriter out) {
        SqlStatement statement = new SqlStatement();
        ArrayList<Service> services = statement.getAllUserServices(username);
        for (Service service : services) {
            GenericClient client = new GenericClient();
            ArrayList<Operation> operations = client.allServiceMethods(service.getPackageName());
            for (Operation operation : operations) {
                service.addOperation(operation);
            }
        }
        MessageEncoder encoder = new MessageEncoder(out);
        encoder.encode(services);
    }
}
