<%-- 
    Document   : getRestockOrder
    Created on : 11 13, 16, 1:38:29 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<restockOrderBean> restocksList = (ArrayList<restockOrderBean>)request.getAttribute("restocksList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View RO</title>
    </head>
    <body>
        <h1>This is the Get Restock Order page!</h1>
        
        <table border="1">
            <tr>
                <th>Restock Order ID</th>
                <th>Restock Order Name</th>
                <th>Product Name</th>
                <th>Restock Price</th>
                <th>Pieces Ordered</th>
                <th>Pieces Received</th>
                <th>Supplier</th>
                <th>Date Due</th>
                <th>Date Delivered</th>
            </tr>
        
        <c:forEach items="${restocksList}" var="ro" begin="0" step="1" varStatus="status">
            <tr>
                <td>${ro.getRestockOrderID()}</td>
                <td><a href="restockOrder.getDetails?restockID=<c:out value="${ro.getRestockOrderID()}"/>">${ro.getRestockOrderName()}</td>
                <td>${ro.getProductName()}</td>
                <td>${ro.getRestockPrice()}</td>
                <td>${ro.getNumberOfPiecesOrdered()}</td>
                <td>${ro.getNumberOfPiecesReceived()}</td>
                <td>${ro.getSupplier()}</td>
                <td>${ro.getRODateDue()}</td>
                <td>${ro.getRODateDelivered()}</td>
                <td><a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit</td>
            </tr>
            
        </c:forEach>
        </table>
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
