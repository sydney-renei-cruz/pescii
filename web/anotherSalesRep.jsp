<%-- 
    Document   : anotherSalesRep
    Created on : 04 30, 17, 5:26:18 PM
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
        <title>PESCII SalesRep Data Saved</title>
    </head>
    <body>
          <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Sales Representativex</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="salesRepID" value="${requestScope.srID}"/>   
        <a href="addSalesRep.jsp">Add another Sales Rep</a><br>
        <c:if test="${salesRepID ne '' && salesRepID ne null}">
            <a href="salesrep.getDetails?srID=<c:out value="${salesRepID}"/>">Edit created Sales Rep</a><br><br>
        </c:if>
        <a href="salesrep.get">Edit another Sales Rep</a><br>
            <a href="conditionsSalesRep.jsp">Search Sales Reps</a><br>
            <a href="salesrep.get">View All Sales Reps</a><br><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
