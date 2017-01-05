<%-- 
    Document   : restockOrderDetails
    Created on : 09 9, 16, 4:59:39 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    //productBean product = (productBean)request.getAttribute("product");
    restockOrderBean restockOrder = (restockOrderBean)request.getAttribute("restockOrder");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII RO Details</title>
    </head>
    <body>
        <h1>This is the Restock Order Details Page!</h1>
        
        <br><br>
        <p>Restock Order ID: ${restockOrder.getRestockOrderID()}</p>
        <p>Restock Order Name: ${restockOrder.getRestockOrderName()}</p>
        <p>Product Ordered: <a href="product.getDetails?forInvoice=no&prodID=<c:out value="${restockOrder.getProductID()}"/>">${restockOrder.getProductName()}</a></p>
        <p>Unit Price: ${restockOrder.getRestockPrice()}</p>
        <p>Quantity Ordered: ${restockOrder.getNumberOfPiecesOrdered()}</p>
        <c:set var="totalPrice" value="${restockOrder.getRestockPrice() * restockOrder.getNumberOfPiecesOrdered()}"/>
        <p>Total Price: <fmt:formatNumber pattern="0.00" value="${totalPrice}" type="number"/></p>
        <p>Quantity Received: ${restockOrder.getNumberOfPiecesReceived()}</p>
        <p>Amount Paid: ${restockOrder.getAmountPaid()}</p>
        <p>Discount: ${restockOrder.getDiscount()}</p>
        <p>Supplier: ${restockOrder.getSupplierName()}</p>
        <p>Date Due: ${restockOrder.getRODateDue()}</p>
        <p>Date Received: ${restockOrder.getRODateDelivered()}</p>
        <p>Purpose: ${restockOrder.getPurpose()}</p>
        <p>Date Created: ${restockOrder.getDateCreated()}</p>
        <p>Last Editted By: ${restockOrder.getLastEdittedBy()}</p>
        
        <br><br><br>
        
        <a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit</a>
        
        <br><br>
        <a href="product.getProductClass?search=yes&searchWhat=ro">Custom View RO</a><br>
        <a href="restockOrder.get">Return to RO list</a><br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
