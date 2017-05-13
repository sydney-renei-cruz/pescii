<%-- 
    Document   : anotherSupplier
    Created on : 04 30, 17, 5:47:28 PM
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
        <title>PESCII Supplier Data Saved</title>
    </head>
    <body>
          <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Supplier</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="supplierID" value="${requestScope.suppID}"/>   
        <a href="product.getProductClass?addSupp=yes">Add Supplier</a><br>
        <c:if test="${supplierID ne '' && supplierID ne null}">
            <a href="supplier.getDetails?forEdit=yes&suppID=<c:out value="${supplierID}"/>">Edit created Supplier</a><br>
            <a href="supplier.getDetails?suppID=<c:out value="${supplierID}"/>">View Supplier Details</a><br><br>
        </c:if>
        <a href="supplier.get?viewSupp=yes">Edit another Supplier</a><br>
        <a href="product.getProductClass?search=yes&searchWhat=supp">Search Supplier</a><br>
        <a href="supplier.get?viewSupp=yes">View All Suppliers</a><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
