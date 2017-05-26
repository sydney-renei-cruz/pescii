<%-- 
    Document   : editAccount
    Created on : 09 9, 16, 4:02:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    accountBean account = (accountBean)request.getAttribute("account");
    ArrayList<accountStatusBean> accountStatusList = (ArrayList<accountStatusBean>)request.getAttribute("accountStatusesList");
    ArrayList<accountTypeBean> accountTypes = (ArrayList<accountTypeBean>)request.getAttribute("atypeList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Account</title>
    </head>
    <body>
       <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Edit Account</legend>
        
        <c:set var="accountStatusList" value="${requestScope.accountStatusesList}"/>
        <c:set var="accountTypes" value="${requestScope.atypeList}"/>
        <c:set var="account" value="${requestScope.account}"/>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <form action="account.edit" method="post">
            <input type="hidden" value="${account.getAccountID()}" name="accountIDInput"><br>
            <input type="hidden" value="${account.getPassword()}" name="oldPassword">
            User Name: <input type="text" value="${account.getUserName()}" name="userNameInput" maxlength="30" required><br>
            Password: <input type="password" name="newPasswordInput" maxlength="255"><br>
            Enter password again:<input type="password" name="newPasswordInput2" maxlength="255"><br><br>
            <b>Account Type</b><br>
            From:${account.getAccountType()}<br>
            To:
            <select name="accountTypeInput">
                <c:forEach items="${accountTypes}" var="atype" begin="0" step="1">
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
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
