<%-- 
    Document   : productDetails
    Created on : 09 4, 16, 11:52:38 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String accountType = ""+session.getAttribute("accountType");
    productBean product = (productBean)request.getAttribute("product");
    String forInvoice = "" + request.getAttribute("forInvoice");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Product Details</title>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Product Details</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
            <p>Product ID: ${product.getProductID()}</p>
        <p>Product Name: ${product.getProductName()}</p>
        <p>Product Description: ${product.getProductDescription()}</p>
        <p>Supplier: ${product.getSupplierName()}</p>
        <p>Product Price:<fmt:formatNumber pattern="0.00" value="${product.getProductPrice()}" type="number"/></p>
        <p>Restock Price: <fmt:formatNumber pattern="0.00" value="${product.getRestockPrice()}" type="number"/></p>
        <p>Stocks Remaining: ${product.getStocksRemaining()}</p>
        <p>Low Stock: ${product.getLowStock()}</p>
        <p>Brand: ${product.getBrand()}</p>
        <p>Product Class: ${product.getProductClassName()}</p>
        <p>Color: ${product.getColor()}</p>
        <p>Date Created: ${product.getDateCreated()}</p>
        <p>Last Edited By: ${product.getLastEdittedBy()}</p>
        
        <br><br><br>
        <c:if test="${forInvoice eq 'yes'}">
            <a href="addToCart?prodName=<c:out value="${product.getProductName()}"/>&prodID=<c:out value="${product.getProductID()}"/>&prodPrice=<c:out value="${product.getProductPrice()}"/>">ADD to Invoice</a><br><br>
        </c:if>
        <c:if test="${forInvoice eq 'yes'}">
            <br><br>
            <a href="viewCart.jsp">View Cart</a> to add your invoice
            <br><br>
        </c:if>
        
        <!--insert "add to RO link here!!!!!-->
        <c:set var="cartType" value="${sessionScope.cartType}"/>
        <c:if test="${cartType eq 'restock'}">
            <br>
            <a href="addToROCart?prodName=<c:out value="${product.getProductName()}"/>&prodID=<c:out value="${product.getProductID()}"/>&prodPrice=<c:out value="${product.getRestockPrice()}"/>">ADD to RO</a>
            <br><br>
        </c:if>
        
        
        <c:choose>
            <c:when test="${forInvoice ne 'yes'}">
                <c:if test="${accountType eq '4' || accountType eq '1'}">
                    <a href="product.getDetails?forEdit=yes&prodID=<c:out value="${product.getProductID()}"/>">Edit</a><br><br>
                    <br><br>
                </c:if>
                <a href="Servlets.getProductServlet">Go to Products list</a><br><br>
                <a href="product.getProductClass?search=yes&searchWhat=prod">Search Product</a><br>
               
            </c:when>
            <c:when test="${cartType eq 'invoice'}">
                <a href="Servlets.getProductServlet?forInvoice=yes">Go to Products list</a><br>
                <a href="invoice.add?cancel=yes">Cancel Invoice</a><br>
            </c:when>
        </c:choose>
        
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
