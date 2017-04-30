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
            <input type="hidden" value="${restockOrder.getRestockOrderID()}" name="restockOrderIDInput">

            <c:choose>
                <c:when test="${accountType eq '4' || accountType eq '1'}">
                    Restock Order Name: <input type="text" value="${restockOrder.getRestockOrderName()}" name="newRONameInput" maxlength="255" required><br>
                    Purpose: <br><textarea name="purposeInput" rows="5" cols="50">${restockOrder.getPurpose()}</textarea><br>
                </c:when>
                <c:when test="${accountType eq '5'}">
                    Restock Order Name: <input type="hidden" value="${restockOrder.getRestockOrderName()}" name="newRONameInput">${restockOrder.getRestockOrderName()}<br>
                    Purpose: <br><input type="hidden" name="purposeInput" value="${restockOrder.getPurpose()}">${restockOrder.getPurpose()}<br>
                </c:when>
            </c:choose>
                    
                    
            <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Pieces Ordered</th>
                    <th>Pieces Received</th>
                </tr>

                <c:forEach items="${roitemsList}" var="ro" begin="0" step="1" varStatus="status">
                    <tr>
                        <td><a href="restockOrder.getDetails?restockID=<c:out value="${ro.getProductID()}"/>">${ro.getProductName()}</a></td>
                        <c:choose>
                            <c:when test="${restockOrder.getStatusName() eq 'In Progress'}">
                                <c:choose>
                                    <c:when test="${accountType eq '4'}">
                                        <td><input type="text" name="QO" value="${ro.getQuantityPurchased()}"></td>
                                        <td><input type="hidden" name="QR" value="${ro.getQuantityReceived()}">${ro.getQuantityReceived()}</td>
                                    </c:when>
                                    <c:when test="${accountType eq '5'}">
                                        <td><input type="hidden" name="QO" value="${ro.getQuantityPurchased()}">${ro.getQuantityPurchased()}</td>
                                        <td><input type="text" name="QR" value="${ro.getQuantityReceived()}"></td>
                                    </c:when>
                                    <c:when test="${accountType eq '1'}">
                                        <td><input type="text" name="QO" value="${ro.getQuantityPurchased()}"></td>
                                        <td><input type="text" name="QR" value="${ro.getQuantityReceived()}"></td>
                                    </c:when>
                                </c:choose>
                            </c:when>
                                        
                            <c:when test="${restockOrder.getStatusName() ne 'In Progress'}">
                                <td><input type="hidden" name="QO" value="${ro.getQuantityPurchased()}">${ro.getQuantityPurchased()}</td>
                                <td><input type="hidden" name="QR" value="${ro.getQuantityReceived()}">${ro.getQuantityReceived()}</td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
            <c:forEach items="${roitemsList}" var="ro" begin="0" step="1" varStatus="status">
                <input type="hidden" value="${ro.getRestockOrderItemID()}" name="ROIID">
                <input type="hidden" value="${ro.getProductID()}" name="pid">
            </c:forEach>
            <input type="hidden" value="${roitemsList.size()}" name="roitems">
            
        

            Original Price: ${restockOrder.getRestockPrice()}<br>
            <c:choose>
                <c:when test="${restockOrder.getStatusName() eq 'In Progress'}">
                    <c:choose>
                        <c:when test="${accountType eq '4'}">
                            Discount: <input type="text" value="${restockOrder.getDiscount()}" name="discountInput" required><br>
                            Amount Paid:<input type="text" value="${restockOrder.getAmountPaid()}" name="amountPaidInput" required><br>
                            Delivery Due Date: <input type="text" value="${restockOrder.getRODateDue()}" name="roDeliveryDueDateInput" id="date1" maxlength="10" required><br>
                            Date Delivered: <input type="hidden" value="${restockOrder.getRODateDelivered()}" name="roDateDeliveredInput">${restockOrder.getRODateDelivered()}<br>
                        </c:when>
                        <c:when test="${accountType eq '5'}">
                            Discount: <input type="hidden" value="${restockOrder.getDiscount()}" name="discountInput">${restockOrder.getDiscount()}<br>
                            Amount Paid:<input type="hidden" value="${restockOrder.getAmountPaid()}" name="amountPaidInput">${restockOrder.getAmountPaid()}<br>
                            Delivery Due Date: <input type="hidden" value="${restockOrder.getRODateDue()}" name="roDeliveryDueDateInput">${restockOrder.getRODateDue()}<br>
                            Date Delivered: <input type="text" value="${restockOrder.getRODateDelivered()}" name="roDateDeliveredInput" id="date2" maxlength="10" required><br>
                        </c:when>
                        <c:when test="${accountType eq '1'}">
                            Discount: <input type="text" value="${restockOrder.getDiscount()}" name="discountInput" required><br>
                            Amount Paid:<input type="text" value="${restockOrder.getAmountPaid()}" name="amountPaidInput" required><br>
                            Delivery Due Date: <input type="text" value="${restockOrder.getRODateDue()}" name="roDeliveryDueDateInput" id="date1" maxlength="10" required><br>
                            Date Delivered: <input type="text" value="${restockOrder.getRODateDelivered()}" name="roDateDeliveredInput" id="date2" maxlength="10" required><br>
                        </c:when>
                    </c:choose>
                </c:when>
                
                <c:when test="${restockOrder.getStatusName() ne 'In Progress'}">
                    Discount: <input type="hidden" value="${restockOrder.getDiscount()}" name="discountInput">${restockOrder.getDiscount()}<br>
                    Amount Paid:<input type="hidden" value="${restockOrder.getAmountPaid()}" name="amountPaidInput">${restockOrder.getAmountPaid()}<br>
                    Delivery Due Date: <input type="hidden" value="${restockOrder.getRODateDue()}" name="roDeliveryDueDateInput">${restockOrder.getRODateDue()}<br>
                    Date Delivered: <input type="hidden" value="${restockOrder.getRODateDelivered()}" name="roDateDeliveredInput">${restockOrder.getRODateDelivered()}<br>
                </c:when>
                            
            </c:choose>
            
            <c:set var="rostatList" value="${requestScope.roStatList}"/>  
            <b>Status:</b>
                <c:choose>
                    <c:when test="${restockOrder.getStatusName() ne 'Completed'}">
                        <br>
                        From: ${restockOrder.getStatusName()}<br>
                        To: 
                        <select name="statusInput">
                             <c:forEach items="${roStatList}" var="roStat" begin="0" step="1">
                                <option value="${roStat.getStatusID()}">${roStat.getStatusName()}</option>
                            </c:forEach>
                        </select><br>
                    </c:when>
                    <c:when test="${restockOrder.getStatusName() eq 'Completed'}">
                        <input type="hidden" value="${restockOrder.getStatusID()}" name="statusInput">${restockOrder.getStatusName()}<br>
                    </c:when>
                </c:choose>
            <br>
            <input type="submit" value="Save Changes">
        </form> 
        
        <br><br>
        <a href="restockOrder.getDetails?restockID=<c:out value="${restockOrder.getRestockOrderID()}"/>">Return to RO Details</a>
        <br><br>
        <a href="restockOrder.get">Return to RO list</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
