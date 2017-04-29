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
    String accountType = ""+session.getAttribute("accountType");
    restockOrderBean restockOrder = (restockOrderBean)request.getAttribute("restockOrder");
    //productBean product = (productBean)request.getAttribute("product");
    ArrayList<restockOrderItemBean> roitemsList = (ArrayList<restockOrderItemBean>)request.getAttribute("roitemsList");
    ArrayList<restockOrderStatusBean> ROStatList = (ArrayList<restockOrderStatusBean>)request.getAttribute("roStatList");
    String message = ""+request.getAttribute("message");
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
                calendar.set("date2");
            }
        </script>
    </head>
    <body onload="init()">
        <h1>This is the Edit Restock Order page!</h1>
        <c:if test="${message ne '' || message ne null || message ne 'null'}">
            <p>${message}</p><br><br>
        </c:if>
        
        

        <form action="restock.edit" method="post">
            ROID: ${restockOrder.getRestockOrderID()}<input type="hidden" value="${restockOrder.getRestockOrderID()}" name="restockOrderIDInput">

            Restock Order Name: <input type="text" value="${restockOrder.getRestockOrderName()}" name="newRONameInput" maxlength="255"><br>
            Purpose: <br><textarea name="purposeInput" rows="5" cols="50">${restockOrder.getPurpose()}</textarea><br>
            
            <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Pieces Ordered</th>
                    <th>Pieces Received</th>
                    <th>Supplier</th>
                </tr>

                <c:forEach items="${roitemsList}" var="ro" begin="0" step="1" varStatus="status">
                    <tr>
                        <td><a href="restockOrder.getDetails?restockID=<c:out value="${ro.getProductID()}"/>">${ro.getProductName()}</a></td>
                        <td><input type="text" name="QO" value="${ro.getQuantityPurchased()}"></td>
                        <td><input type="text" name="QR" value="${ro.getQuantityReceived()}"></td>
                        <td>${ro.getSupplierName()}</td>
                    </tr>
                </c:forEach>
            </table>
            <c:forEach items="${roitemsList}" var="ro" begin="0" step="1" varStatus="status">
                <input type="hidden" value="${ro.getRestockOrderItemID()}" name="ROIID">
                <input type="hidden" value="${ro.getProductID()}" name="pid">
            </c:forEach>
            <input type="hidden" value="${roitemsList.size()}" name="roitems">
            
        


        Discount: <input type="text" value="${restockOrder.getDiscount()}" name="discountInput"><br>
        Delivery Due Date: <input type="text" value="${restockOrder.getRODateDue()}" name="roDeliveryDueDateInput" id="date1" maxlength="10"><br>
        Amount Paid:<input type="text" value="${restockOrder.getAmountPaid()}" name="amountPaidInput"><br>
        Date Delivered: <input type="text" value="${restockOrder.getRODateDelivered()}" name="roDateDeliveredInput" id="date2" maxlength="10"><br>
        Status: <select name="statusInput">
                <c:forEach items="${ROStatList}" var="roStat" begin="0" step="1">
                    <option value="${roStat.getStatusID()}">${roStat.getStatusName()}</option>
                </c:forEach>
       </select><br>
        <br>
        <input type="submit" value="Save Changes">
        </form> 
        
        <br><br>
        <a href="restockOrder.getDetails?restockID=<c:out value="${restockOrder.getRestockOrderID()}"/>">Return to RO Details</a>
        <br><br>
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
