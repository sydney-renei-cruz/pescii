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
            <%=account.getPassword()%>
            <input type="hidden" value="${account.getPassword()}" name="oldPassword">
            Password: <input type="password" name="newPasswordInput"><br>
            Enter password again:<input type="password" name="newPasswordInput2"><br>
            User Name: <input type="text" value="${account.getUserName()}" name="userNameInput"><br>
            Account Type:<br>
            From: <c:if test="${account.getAccountType() == '1'}">CEO<br></c:if>
                  <c:if test="${account.getAccountType() == '2'}">Secretary<br></c:if>
                  <c:if test="${account.getAccountType() == '3'}">Accountant<br></c:if>
                  <c:if test="${account.getAccountType() == '4'}">Inventory Manager<br></c:if>
                  <c:if test="${account.getAccountType() == '5'}">Auditor<br></c:if>
            To:
            <select name="accountTypeInput">
                <option value="3">Accountant</option>
                <option value="5">Auditor</option>
                <option value="1">CEO</option>
                <option value="4">Inventory Manager</option>
                <option value="2">Secretary</option>
            </select><br>
            Account Status:<br>
            From: <c:if test="${account.getAccountStatus() == '1'}">Activated<br></c:if>
                  <c:if test="${account.getAccountStatus() == '2'}">Deactivated<br></c:if>
            To:
            <select name="accountStatusInput">
                <option value="1">Activated</option>
                <option value="2">Deactivated</option>
            </select><br>
            
            
            <br><input type="submit" value="Save Changes">
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
