<%-- 
    Document   : getRestockOrder
    Created on : 11 13, 16, 1:38:29 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
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
                <th>Restock Order Name</th>
                <th>Date Due</th>
                <th>Date Delivered</th>
                <th>Amount Paid</th>
                <th>Discount</th>
                <th>Date Paid</th>
                <th>Date Created</th>
            </tr>
        
        <c:forEach items="${restocksList}" var="ro" begin="0" step="1" varStatus="status">
            <tr>
                <td><a href="restockOrder.getDetails?restockID=<c:out value="${ro.getRestockOrderID()}"/>">${ro.getRestockOrderName()}</a></td>
                <td>${ro.getRODateDue()}</td>
                <td>${ro.getRODateDelivered()}</td>
                <td>${ro.getAmountPaid()}</td>
                <td>${ro.getDiscount()}</td>
                <td>${ro.getDatePaid()}</td>
                <td>${ro.getDateCreated()}</td>
                <td><a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit</a></td>
            </tr>
            
        </c:forEach>
        </table>
        
        
        <br><br>
        <a href="restockOrder.getStatus">Search RO</a><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
