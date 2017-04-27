<%-- 
    Document   : conditionsSupplier
    Created on : 01 6, 17, 6:14:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
%>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Search Supplier</title>
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
        <h1>This is the Conditions Supplier page!</h1>
        
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="supplier">
            
            Search by Supplier Name:<input type="text" name="searchSupplierNameInput" maxlength="100">
            <br><br>
            Search by Product Class:<br>
               <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                    <input type="checkbox" name="productClassInput" value="${pro.getProductClassName()}">${pro.getProductClassName()}<br>
               </c:forEach>
            <br>
            <b>Search by Date Created</b>
            <br><br>
            From:<input type="text" name="fromDate" id="date1" maxlength="10"><br>
            To:<input type="text" name="toDate" id="date2" maxlength="10"><br>
            <br><br> 
                
            <input type="submit" value="Get">    
        </form>
        
        <br><br>
        <a href="supplier.get?viewSupp=yes">View All Suppliers</a>
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
