<%-- 
    Document   : conditionsRestockOrder
    Created on : 11 13, 16, 1:30:10 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    ArrayList<supplierBean> suppList = (ArrayList<supplierBean>)request.getAttribute("suppliersList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View RO</title>
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
        <h1>This is the Conditions Restock Order page!</h1>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <h4>Set the conditions of your search</h4>
        
        <p><b>Search</b></p>
        <form action="new.get">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="restockOrder">
            Search by Restock Order Name:<input type="text" name="searchNameInput" maxlength="255">
            <br><br>
            Search by Supplier:<input type="text" name="searchSupplierInput" maxlength="255">
            <br><br>
            Search by Date:<select name="searchDateInput">
                    <option value="RODateDue">Expected Arrival Date</option>
                    <option value="RODateDelivered">Date Received</option>
                    <option value="RestockOrder.dateCreated">Date Created</option>
            </select><br><br>
            From:<input type="text" name="fromDate" id="date1" maxlength="10"><br>
            To:<input type="text" name="toDate" id="date2" maxlength="10"><br><br>
            
            <c:set var="rostatList" value="${requestScope.roStatList}"/> 
            Search by Status:
                <c:forEach items="${rostatList}" var="rStatList" begin="0" step="1">
                    <input type="checkbox" name="searchStatusInput" value="${rStatList.getStatusName()}">${rStatList.getStatusName()}
                </c:forEach>
            <br><br>
            <input type="submit" value="Search"><br><br><br>
        </form>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="restockOrder">
            
            <b>Shortcuts</b><br>
                <input type="radio" name="getWhat" value="new">View New Restock Orders<br>
                <input type="radio" name="getWhat" value="completed">View Recently Completed Restock Orders<br>
                <input type="radio" name="getWhat" value="close">View Restock Orders Arriving Soon<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        
        
        <br><br>
        <a href="restockOrder.get">View All Restock Orders</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
