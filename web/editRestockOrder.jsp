<%-- 
    Document   : editRestockOrder
    Created on : 08 20, 16, 1:16:09 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    restockOrderBean restockOrder = (restockOrderBean)request.getAttribute("restockOrder");
    productBean product = (productBean)request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit RO</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
            function init() {
                calendar.set("date1");
            }
        </script>
    </head>
    <body onload="init()">
        <h1>This is the Edit Restock Order page!</h1>
          <form action="restock.edit" method="post">
                Restock Order ID: <input type="hidden" value="${restockOrder.getRestockOrderID()}" name="restockOrderIDInput">${restockOrder.getRestockOrderID()}<br>
                Restock Order Name: <input type="text" value="${restockOrder.getRestockOrderName()}" name="newRONameInput"><br>
                Product Ordered: <input type="hidden" value="${restockOrder.getProductID()}" name="productIDInput">${restockOrder.getProductName()}<br>
                Number of Pieces Ordered: <input type="text" value="${restockOrder.getNumberOfPiecesOrdered()}" name="numberOfPiecesOrderedInput"><br>
                Number of Pieces Received: <input type="text" value="${restockOrder.getNumberOfPiecesReceived()}" name="numberOfPiecesReceivedInput"><br>
                <c:set var="totalPrice" value="${restockOrder.getRestockPrice() * restockOrder.getNumberOfPiecesOrdered()}"/>
                <p>Original Price: <fmt:formatNumber pattern="0.00" value="${totalPrice}" type="number"/></p>
                Discount: ${restockOrder.getDiscount()}<br>
                <c:set var="totalPrice" value="${restockOrder.getRestockPrice() * restockOrder.getNumberOfPiecesOrdered() - restockOrder.getDiscount()}"/>
                <p>Discounted Price: <fmt:formatNumber pattern="0.00" value="${totalPrice}" type="number"/></p>
                Amount Paid:<input type="text" value="${restockOrder.getAmountPaid()}" name="amountPaidInput"><br>
                Supplier: ${restockOrder.getSupplierName()}<br>
                Purpose: <input type="text" value="${restockOrder.getPurpose()}" name="purposeInput"><br>
                Delivery Due Date: ${restockOrder.getRODateDue()}<br>
                Date Delivered: <input type="text" value="${restockOrder.getRODateDelivered()}" name="roDateDeliveredInput" id="date1"><br>
                
                <br><input type="submit" value="Save Changes">
                
        </form>
            
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
