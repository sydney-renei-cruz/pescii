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
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="supplierID" value="${requestScope.suppID}"/>   
        <a href="product.getProductClass?addSupp=yes">Add Supplier</a><br>
        <a href="supplier.getDetails?forEdit=yes&suppID=<c:out value="${supplierID}"/>">Edit created Supplier</a><br>
        <a href="supplier.getDetails?suppID=<c:out value="${supplierID}"/>">View Supplier Details</a><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
