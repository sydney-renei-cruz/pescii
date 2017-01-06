<%-- 
    Document   : conditionsRestockOrder
    Created on : 11 13, 16, 1:30:10 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
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
        
        <h4>Set the conditions of your search</h4>
        
        <p><b>Search</b></p>
        <form action="new.get">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="restockOrder">
            Search by Restock Order Name:<input type="text" name="searchNameInput">
            <br><br>
            Search by Supplier:<select name="searchSupplierInput">
                <option value="All">All</option>
                <c:forEach items="<%=suppList%>" var="sup" begin="0" step="1">
                        <option value="${sup.getSupplierName()}">${sup.getSupplierName()}</option>
                </c:forEach>
            </select>
            <br><br>
            Search by Product Name:<input type="text" name="searchProductNameInput">
            <br><br>
            Product Class:<br>
               <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                    <input type="checkbox" name="productClassInput" value="${pro.getProductClassName()}">${pro.getProductClassName()}<br>
               </c:forEach>
            <br>
            Search by Date:<select name="searchDateInput">
                    <option value="RODateDue">Expected Arrival Date</option>
                    <option value="RODateDelivered">Date Received</option>
                    <option value="RestockOrder.dateCreated">Date Created</option>
            </select><br><br>
            From:<input type="text" name="fromDate" id="date1"><br>
            To:<input type="text" name="toDate" id="date2"><br><br>
            
            <input type="submit" value="Search"><br><br><br>
        </form>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="restockOrder">
            
            <b>Shortcuts</b><br>
                <input type="radio" name="getWhat" value="new">New Restock Orders<br>
                <input type="radio" name="getWhat" value="completed">Recently completed Restock Orders<br>
                <input type="radio" name="getWhat" value="close">Restock Orders near deadlines<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
