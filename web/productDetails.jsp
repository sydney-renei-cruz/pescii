<%-- 
    Document   : productDetails
    Created on : 09 4, 16, 11:52:38 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
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
        <h1>This is the Product Details Page!</h1>
        <p>Product ID: ${product.getProductID()}</p>
        <p>Product Name: ${product.getProductName()}</p>
        <p>Product Description: ${product.getProductDescription()}</p>
        <p>Supplier: ${product.getSupplierName()}</p>
        <p>Product Price:${product.getProductPrice()}</p>
        <p>Restock Price: ${product.getRestockPrice()}</p>
        <p>Stocks Remaining: ${product.getStocksRemaining()}</p>
        <p>Low Stock: ${product.getLowStock()}</p>
        <p>Brand: ${product.getBrand()}</p>
        <p>Product Class: ${product.getProductClassName()}</p>
        <p>Color: ${product.getColor()}</p>
        <p>Date Created: ${product.getDateCreated()}</p>
        <p>Last Editted By: ${product.getLastEdittedBy()}</p>
        
        <br><br><br>
        <c:if test="${forInvoice eq 'yes'}">
            <a href="addToCart?prodName=<c:out value="${product.getProductName()}"/>&prodID=<c:out value="${product.getProductID()}"/>">ADD</a><br><br>
        </c:if>
        <c:if test="${forInvoice eq 'yes'}">
            <br><br>
            <a href="viewCart.jsp">View Cart</a> to add your invoice.<br><br>
        </c:if>
        
        
        <c:choose>
            <c:when test="${forInvoice ne 'yes'}">
                <a href="Servlets.getProductServlet">Go to Products list</a><br>
                <a href="product.getDetails?forEdit=yes&prodID=<c:out value="${product.getProductID()}"/>">Edit Product</a>
            </c:when>
            <c:when test="${forInvoice eq 'yes'}">
                <a href="Servlets.getProductServlet?forInvoice=yes">Return to Products list</a><br>
                <a href="invoice.add?cancel=yes">Cancel Invoice</a><br>
            </c:when>
        </c:choose>
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
