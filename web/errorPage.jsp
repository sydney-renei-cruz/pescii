<%-- 
    Document   : errorPage
    Created on : 04 29, 17, 4:43:45 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList, java.util.LinkedList,java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Error</title>
    </head>
    <body>
        <h1>This is the Error Page!</h1>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <h3>${errorMessage}</h3>
            
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
