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
        <a href="product.getProductClass?search=yes&searchWhat=supp">Custom View Supplier</a>
        <br><br>
        <c:choose>
            <c:when test="${accountType eq 3}">
                <a href="notif.get?forWhat=invoice">Return to Home</a>
            </c:when>
            <c:when test="${(accountType eq 4) || (accountType eq 5)} ">
                <a href="notif.get?forWhat=restock">Return to Home</a>
            </c:when>
            <c:when test="${accountType eq 1}">
                <a href="notif.get?forWhat=both">Return to Home</a>
            </c:when>
            <c:when test="${(accountType ne 3) || (accountType ne 4) || (accountType ne 5) || (accountType ne 1)}">
                <a href="homePage.jsp">Return to Home</a>
            </c:when>
        </c:choose>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
