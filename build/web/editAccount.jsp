<%-- 
    Document   : editAccount
    Created on : 09 9, 16, 4:02:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    accountBean account = (accountBean)request.getAttribute("account");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Account</title>
    </head>
    <body>
        <h1>This is the Edit Account Page!</h1>
        
        <form action="account.edit" method="post">
            Account ID: <input type="hidden" value="${account.getAccountID()}" name="accountIDInput"><br>
            <input type="hidden" value="${account.getPassword()}" name="oldPassword">
            Password: <input type="password" name="newPasswordInput"><br>
            User Name: <input type="text" value="${account.getUserName()}" name="userNameInput"><br>
            Account Type:<br>
            From: ${account.getAccountType()}<br>
            To:
            <select name="accountTypeInput">
                <option value="Accountant">Accountant</option>
                <option value="Auditor">Auditor</option>
                <option value="CEO">CEO</option>
                <option value="Inventory Manager">Inventory Manager</option>
                <option value="Secretary">Secretary</option>
            </select><br>
            Account Status:<br>
            From: ${account.getAccountStatus()}<br>
            To:
            <select name="accountStatusInput">
                <option value="Activated">Activated</option>
                <option value="Deactivated">Deactivated</option>
            </select><br>
            
            
            <br><input type="submit" value="Save Changes">
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
