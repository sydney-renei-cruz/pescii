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
    ArrayList<accountStatusBean> accountStatusList = (ArrayList<accountStatusBean>)request.getAttribute("accountStatusesList");
    ArrayList<accountTypeBean> accountTypes = (ArrayList<accountTypeBean>)request.getAttribute("atypeList");
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
            Enter password again:<input type="password" name="newPasswordInput2"><br>
            User Name: <input type="text" value="${account.getUserName()}" name="userNameInput"><br><br>
            <b>Account Type</b><br>
            From:${account.getAccountType()}<br>
            To:
            <select name="accountTypeInput">
                <c:forEach items="<%=accountTypes%>" var="atype" begin="0" step="1">
                        <option value="${atype.getAccountTypeID()}">${atype.getAccountTypeName()}</option>
                </c:forEach>    
            </select><br><br>
            <b>Account Status</b><br>
            From: ${account.getAccountStatus()}<br>
            To:
            <select name="accountStatusInput">
                <c:forEach items="<%=accountStatusList%>" var="astat" begin="0" step="1">
                        <option value="${astat.getAccountStatusID()}">${astat.getAccountStatusName()}</option>
                </c:forEach>    
            </select><br>
            
            
            <br><input type="submit" value="Save Changes">
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
