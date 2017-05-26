<%-- 
    Document   : restockOrderDetails
    Created on : 09 9, 16, 4:59:39 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII RO Details</title>
    </head>
    <body>
       <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Restock Order Details</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
         <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="restockOrder" value="${requestScope.restockOrder}"/>
        <c:set var="roitemsList" value="${requestScope.roitemsList}"/>
        
        <br><br>
        <p>Restock Order Name: ${restockOrder.getRestockOrderName()}</p>
        <p>Supplier: ${restockOrder.getSupplierName()}</p>
        <p>Total Price: <fmt:formatNumber pattern="0.00" value="${restockOrder.getRestockPrice()}" type="number"/></p>
        <p>Amount Paid: <fmt:formatNumber pattern="0.00" value="${restockOrder.getAmountPaid()}" type="number"/></p>
        <p>Discount: <fmt:formatNumber pattern="0.00" value="${restockOrder.getDiscount()}" type="number"/></p>
        <p>Date Due: ${restockOrder.getRODateDue()}</p>
        <p>Date Received: ${restockOrder.getRODateDelivered()}</p>
        <p>Status: ${restockOrder.getStatusName()}</p>
        <p>Purpose: ${restockOrder.getPurpose()}</p>
        <p>Date Created: ${restockOrder.getDateCreated()}</p>
        <p>Last Edited By: ${restockOrder.getLastEdittedBy()}</p>
        
        
        <!--Restock Order Items-->
        <br><br><br>
        <h5>Restock Order Items</h5>
        <table border="1">
            <tr>
                <th>Product Name</th>
                <th>Quantity Purchased</th>
                <th>Quantity Received</th>
                <th>Total Cost</th>
            </tr>
        
        <c:forEach items="${roitemsList}" var="roitem" begin="0" step="1">
            <tr>
                <td><a href="product.getDetails?prodID=<c:out value="${roitem.getProductID()}"/>">${roitem.getProductName()}</a></td>
                <td>${roitem.getQuantityPurchased()}</td>
                <td>${roitem.getQuantityReceived()}</td>
                <td><fmt:formatNumber pattern="0.00" value="${roitem.getTotalCost()}" type="number"/></td>
            </tr>
        </c:forEach>
        </table>
        
        <br><br>
        <c:if test="${accountType eq '1' || accountType eq '4' || accountType eq '5'}">
        <a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${restockOrder.getRestockOrderID()}"/>">Edit</a>
        </c:if>
        <br><br>
        <a href="restockOrder.getStatus">Search RO</a><br>
        <br>
        <a href="restockOrder.get">Go to RO list</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
