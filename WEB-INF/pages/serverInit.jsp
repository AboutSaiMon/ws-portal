<%-- 
    Document   : serverInit
    Created on : 05-Jun-2010, 12:24:57
    Author     : Simone Spaccarotella <spa.simone@gmail.com>
--%>

<%@page import="it.unical.inf.wsportal.server.GenericClient"%>
<%@page import="it.unical.inf.wsportal.server.FoldersNFiles"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Server initialization</title>
    </head>
    <body>
        <%
            
        %>
        <h1>WS Portal Configuration Status</h1>
        <p>
            Congratulation, the environment variables have been set.
        </p>
        <ul>
            <li><b>Root path:</b> <%=FoldersNFiles.getRootPath()%></li>
            <li><b>Class:</b> <%=FoldersNFiles.getClassesPath()%></li>
            <li><b>Javac:</b> <%=FoldersNFiles.getJavacPath()%></li>
            <li><b>Lib:</b> <%=FoldersNFiles.getLibPath()%></li>
            <li><b>Src:</b> <%=FoldersNFiles.getSrcPath()%></li>
        </ul>
        go back to the <a href="home.jsp">home page</a>
    </body>
</html>