<%-- 
    Document   : anotherRestockOrder
    Created on : 04 30, 17, 6:35:27 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String message = ""+request.getAttribute("message");
    String suppID = ""+session.getAttribute("suppID");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCI RO Data Saved</title>
    </head>
    <body>
          <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Restock Order</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="restockOrderID" value="${requestScope.restockID}"/>   
        <c:choose>
                <c:when test="${(suppID eq null || suppID eq 'null' || suppID eq '')}">
                    <a href="supplier.get?viewSupp=yes&forRestock=yes">Create a new Restock Order</a><br>      
                </c:when>
                <c:when test="${cartType eq 'restock' && (suppID ne null && suppID ne 'null' && suppID ne '')}">
                    <a href="Servlets.getProductServlet?forOther=restock">Create a new Restock Order</a><br>      
                </c:when>
        </c:choose>
        <c:if test="${restockOrderID ne '' && restockOrderID ne null}">             
            <a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${restockOrderID}"/>">Edit Restock Order</a><br>
            <a href="restockOrder.getDetails?restockID=<c:out value="${restockOrderID}"/>">View Restock Order Details</a><br><br>
        </c:if>
        <a href="restockOrder.get">Edit another Restock Order</a><br>
        <a href="unfinished.get?getTable=ro">View Unfinished RO</a><br>
        <a href="restockOrder.getStatus">Search RO</a><br>
        <a href="restockOrder.get">View All Restock Orders</a><br><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
