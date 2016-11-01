<%-- 
    Document   : createAccount
    Created on : 08 20, 16, 5:24:42 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Create Account</title>
    </head>
    <body>
        <h1>This is the Create Account page!</h1>
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
        
        <form action="Servlets.createAccountServlet" method="post">
            Enter username:<input type="text" name="usernameInput"><br>
            Enter password:<input type="password" name="passwordInput"><br>
            Enter password again:<input type="password" name="password2Input"><br>
            Enter Account Type:<select name="accTypeInput">
                <option value="3">Accountant</option>
                <option value="5">Auditor</option>
                <option value="1">CEO</option>
                <option value="4">Inventory Manager</option>
                <option value="2">Secretary</option>
            </select><br><br>
            <input type="submit" value="Create">
        </form>
        
        
            <a href="homePage.jsp">Return to Home</a>
            <br><br>
            <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
