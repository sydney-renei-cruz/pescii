<%-- 
    Document   : anotherProduct
    Created on : 04 30, 17, 5:38:15 PM
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
        <title>PESCII Product Data Saved</title>
    </head>
    <body>
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="productID" value="${requestScope.prodID}"/>   
        <a href="product.getProductClass">Add a new Product</a><br>
        <c:if test="${productID ne '' && productID ne null}">
            <a href="product.getDetails?forEdit=yes&prodID=<c:out value="${productID}"/>">Edit created Product</a><br>
            <a href="product.getDetails?prodID=<c:out value="${productID}"/>">View Product Details</a><br><br>
        </c:if>
        <a href="Servlets.getProductServlet">Edit another Product</a><br>
        <a href="product.getProductClass?search=yes&searchWhat=prod">Search Product</a><br>
        <a href="Servlets.getProductServlet">View all Products</a><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
