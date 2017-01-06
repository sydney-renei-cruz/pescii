<%-- 
    Document   : getSupplier
    Created on : 01 4, 17, 3:29:21 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<supplierBean> suppliersList = (ArrayList<supplierBean>)request.getAttribute("suppliersList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Supplier</title>
    </head>
    <body>
        <h1>This is the Get Supplier Page!</h1>
        
        <table border="1">
            <tr>
                <th>Supplier ID</th>
                <th>Supplier Name</th>
                <th>Address</th>
                <th>Contact Number</th>
                <th>Product Class</th>
            </tr>
        
        <c:forEach items="${suppliersList}" var="sr" begin="0" step="1" varStatus="status">
            <tr>
                    <td>${sr.getSupplierID()}</td>
                    <td><a href="supplier.getDetails?suppID=<c:out value="${sr.getSupplierID()}"/>">${sr.getSupplierName()}</a></td>
                    <td>${sr.getSupplierAddress()}</td>
                    <td>${sr.getSupplierContactNumber()}</td>
                    <td>${sr.getProductClassName()}</td>
                    <td><a href="supplier.getDetails?forEdit=yes&suppID=<c:out value="${sr.getSupplierID()}"/>">Edit</a></td>
            </tr>
            
        </c:forEach>
        </table>
        <br><br>
        <a href="product.getProductClass?search=yes&searchWhat=supp">Custom View Supplier</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>