<%-- 
    Document   : supplierDetails
    Created on : 01 4, 17, 4:25:39 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    supplierBean supb = (supplierBean)request.getAttribute("supplier");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Supplier Details</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        
        <c:set var="supb" value="${requestScope.supplier}"/>
        
        <br><br>
        <p>Name: ${supb.getSupplierName()}</p>
        <p>Address: ${supb.getSupplierAddress()}</p>
        <p>Contact Number: ${supb.getSupplierContactNumber()}</p>
        <p>Product Class: ${supb.getProductClassName()}</p>
        <p>Date Created: ${supb.getDateCreated()}</p>
        <p>Last Edited By: ${supb.getLastEdittedBy()}</p>
        
        <br><br><br>
        <a href="supplier.getDetails?forEdit=yes&suppID=<c:out value="${supb.getSupplierID()}"/>">Edit</a>
        <br><br>
        <a href="Servlets.getProductServlet?forOther=restock&suppID=<c:out value="${supb.getSupplierID()}"/>">Select for RO</a>
        <br><br>
        <a href="supplier.get?viewSupp=yes">Go to Suppliers List</a>
        <br><br>
        <a href="product.getProductClass?search=yes&searchWhat=supp">Search Supplier</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
