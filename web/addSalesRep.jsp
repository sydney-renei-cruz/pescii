<%-- 
    Document   : addSalesRep
    Created on : 11 1, 16, 10:32:03 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
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
        <title>PESCII Add Sales Rep</title>
    </head>
    <body>
        <h1>This is the Add Sales Rep Page!</h1>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
            
        <form action="salesrep.add" method="post">
            First name:<input type="text" name="salesrepFirstNameInput"  maxlength="100" required><br>
            Last name:<input type="text" name="salesrepLastNameInput" maxlength="100" required><br>
            Enter Sales Rep mobile number:<input type="text" name="salesrepMNInput" maxlength="12"><br>
            Enter Sales Rep Address:<input type="text" name="salesrepAddressInput" maxlength="255" required><br>
            <input type="submit" value="Save Sales Rep"><br>
        </form>
        
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
