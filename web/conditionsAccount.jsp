<%-- 
    Document   : conditionsAccount
    Created on : 01 5, 17, 4:11:34 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<accountStatusBean> accountStatusList = (ArrayList<accountStatusBean>)request.getAttribute("accountStatusesList");
    ArrayList<accountTypeBean> accountTypes = (ArrayList<accountTypeBean>)request.getAttribute("atypeList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Search Account</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
            function init() {
                calendar.set("date1");
                calendar.set("date2");
            }
        </script>
    </head>
    <body onload="init()">
         <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Search Account</legend>
        <h4>Set the conditions of your search</h4>
        
        <c:set var="accountStatusList" value="${requestScope.accountStatusesList}"/>
        <c:set var="accountTypes" value="${requestScope.atypeList}"/>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="account">
            
            Search by User Name:<br><input type="text" name="userNameInput" maxlength="30">
            <br><br>
            Search by Account Status:<br>
                <c:forEach items="${accountStatusList}" var="astat" begin="0" step="1">
                    <input type="checkbox" name="accountStatusInput" value="${astat.getAccountStatusName()}">${astat.getAccountStatusName()}<br>
                </c:forEach>
                <br>
            <br><br>
            Search by Account Type<br>
                <c:forEach items="${accountTypes}" var="atype" begin="0" step="1">
                    <input type="checkbox" name="accountTypeInput" value="${atype.getAccountTypeName()}">${atype.getAccountTypeName()}<br>
                </c:forEach>    
                <br>
            <br><br>
            <b>Search by Date Created</b>
            <br><br>
            From:<br><input type="text" name="fromDate" id="date1" maxlength="10"><br>
            To:<br><input type="text" name="toDate" id="date2" maxlength=10><br>
            <br><br> 
                
            <input type="submit" value="Search">    
        </form>
        
        <br><br>
        <a href="account.get">View All Accounts</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
