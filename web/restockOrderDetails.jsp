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
    String accountType = ""+session.getAttribute("accountType");
    restockOrderBean restockOrder = (restockOrderBean)request.getAttribute("restockOrder");
    ArrayList<restockOrderItemBean> roitemsList = (ArrayList<restockOrderItemBean>)request.getAttribute("roitemsList");
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
        <p>Restock Order Name: ${restockOrder.getRestockOrderName()}</p>
        <p>Total Price: <fmt:formatNumber pattern="0.00" value="${totalPrice}" type="number"/></p>
        <p>Amount Paid: ${restockOrder.getAmountPaid()}</p>
        <p>Discount: ${restockOrder.getDiscount()}</p>
        <p>Date Due: ${restockOrder.getRODateDue()}</p>
        <p>Date Received: ${restockOrder.getRODateDelivered()}</p>
        <p>Purpose: ${restockOrder.getPurpose()}</p>
        <p>Date Created: ${restockOrder.getDateCreated()}</p>
        <p>Last Editted By: ${restockOrder.getLastEdittedBy()}</p>
        
        
        <!--Restock Order Items-->
        <br><br><br>
        <h5>Restock Order Items</h5>
        <table border="1">
            <tr>
                <th>Product Name</th>
                <th>Supplier</th>
                <th>Quantity Purchased</th>
                <th>Quantity Received</th>
                <th>Total Cost</th>
            </tr>
        
        <c:forEach items="${roitemsList}" var="roitem" begin="0" step="1">
            <tr>
                <td><a href="product.getDetails?prodID=<c:out value="${roitem.getProductID()}"/>">${roitem.getProductName()}</a></td>
                <td>${roitem.getSupplierName()}</td>
                <td>${roitem.getQuantityPurchased()}</td>
                <td>${roitem.getQuantityReceived()}</td>
                <td>${roitem.getTotalCost()}</td>
            </tr>
        </c:forEach>
        </table>
        
        <br><br>
        <a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${restockOrder.getRestockOrderID()}"/>">Edit</a>
        
        <br><br>
        <a href="product.getProductClass?search=yes&searchWhat=ro">Custom View RO</a><br>
        <br>
        <a href="restockOrder.get">Return to RO list</a>
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
