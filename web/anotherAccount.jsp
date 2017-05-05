<%-- 
    Document   : anotherAccount
    Created on : 04 30, 17, 5:57:23 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="accountID" value="${requestScope.accID}"/>   
        <a href="account.getTypeStatus">Create a new Account</a><br>
        <c:if test="${accountID ne '' && accountID ne null}">
            <a href="account.getDetails?accID=<c:out value="${accountID}"/>">Edit created Account</a><br><br>
        </c:if>
        <a href="account.get">Edit another Account</a><br>
        <a href="account.getTypeStatus?forSearch=yes">Search Accounts</a><br>
        <a href="account.get">View All Accounts</a><br><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
