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
        <table border="1">
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Description</th>
                <th>Product Price</th>
                <th>Restock Price</th>
                <th>Stocks Remaining</th>
                <th>Low Stock</th>
                <th>Brand</th>
                <th>Product</th>
                <th>Color</th>
            </tr>
        
            <tr>
                    <td>${product.getProductID()}</td>
                    <td>${product.getProductName()}</td>
                    <td>${product.getProductDescription()}</td>
                    <td>${product.getProductPrice()}</td>
                    <td>${product.getRestockPrice()}</td>
                    <td>${product.getStocksRemaining()}</td>
                    <td>${product.getLowStock()}</td>
                    <td>${product.getBrand()}</td>
                    <td>${product.getProductClass()}</td>
                    <td>${product.getColor()}</td>
                    
                    
                    
            </tr>
            
        </table>
        <br><br><br>
        <c:if test="${forInvoice eq 'yes'}">
            <a href="addToCart?prodName=<c:out value="${product.getProductName()}"/>&prodID=<c:out value="${product.getProductID()}"/>">ADD</a><br><br>
        </c:if>
        <c:if test="${forInvoice eq 'yes'}">
            <br><br>
            <a href="viewCart.jsp">View Cart</a> to add your invoice.
        </c:if>
        <br><br>
        
        <c:choose>
            <c:when test="${forInvoice ne 'yes'}">
                <a href="Servlets.getProductServlet">Go to Products list</a>
            </c:when>
            <c:when test="${forInvoice eq 'yes'}">
                <a href="Servlets.getProductServlet?forInvoice=yes">Return to Products list</a>
            </c:when>
        </c:choose>
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
