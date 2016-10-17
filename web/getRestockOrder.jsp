<%-- 
    Document   : getRestockOrder
    Created on : 08 20, 16, 5:28:16 PM
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
        <h1>This is the View Restock Order Page!</h1>
        
        <table border="1">
            <tr>
                <th>Restock Order ID</th>
                <th>Supplier</th>
                <th>Date Due</th>
                <th>Date Delivered</th>
                
            </tr>
        
        <c:forEach items="${restocksList}" var="ro" begin="0" step="1" varStatus="status">
            <tr>
                <td><a href="restockOrder.getDetails?editRestock=no&restockID=<c:out value="${ro.getRestockOrderID()}"/>">${ro.getRestockOrderID()}</a></td>
                <td>${ro.getSupplier()}</td>
                <td>${ro.getRODateDue()}</td>
                <td>${ro.getRODateDelivered()}</td>
                <td><a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit RO</a></td>
            </tr>
        </c:forEach>
        </table>

        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
