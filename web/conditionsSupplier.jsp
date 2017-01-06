<%-- 
    Document   : conditionsSupplier
    Created on : 01 6, 17, 6:14:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
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
            
            Search by Supplier Name:<input type="text" name="searchSupplierNameInput">
            <br><br>
            Search by Product Class:<br>
               <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                    <input type="checkbox" name="productClassInput" value="${pro.getProductClassName()}">${pro.getProductClassName()}<br>
               </c:forEach>
            <br>
            <b>Search by Date Created</b>
            <br><br>
            From:<input type="text" name="fromDate" id="date1"><br>
            To:<input type="text" name="toDate" id="date2"><br>
            <br><br> 
                
            <input type="submit" value="Get">    
        </form>
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
