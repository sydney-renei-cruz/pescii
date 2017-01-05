<%-- 
    Document   : supplierDetails
    Created on : 01 4, 17, 4:25:39 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    supplierBean supb = (supplierBean)request.getAttribute("supplier");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Supplier Details</h1>
        
        <br><br>
        <p>Supplier ID: <%=supb.getSupplierID()%></p>
        <p>Name: <%=supb.getSupplierName()%></p>
        <p>Address: <%=supb.getSupplierAddress()%></p>
        <p>Contact Number: <%=supb.getSupplierContactNumber()%></p>
        <p>Product Class: <%=supb.getProductClassName()%></p>
        <p>Date Created: <%=supb.getDateCreated()%></p>
        <p>Last Editted By: <%=supb.getLastEdittedBy()%></p>
        
        <br><br><br>
        
        
        <br><br>
        <a href="supplier.get?viewSupp=yes">Return to Suppliers List</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
