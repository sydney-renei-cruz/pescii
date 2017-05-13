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
         <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <h1>This is the Conditions Supplier page!</h1>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    
                        <legend class="mui--text-center mui--text-display3">Search Supplier</legend>
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="supplier">
         
                <div class="mui-textfield mui-textfield--float-label">
                    <input type="text" name="searchSupplierNameInput" id="clinicNameInput">
                    <label for="searchSupplierNameInput"> Search by Supplier Name</label>
                </div>
           
            <br><br>
            Search by Product Class:<br>
               <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                    <input type="checkbox" name="productClassInput" value="${pro.getProductClassName()}">${pro.getProductClassName()}<br>
               </c:forEach>
            <br>
            <b>Search by Date Created</b>
            <br><br>
            
          
            
            From:
            <br><input type="text" name="fromDate" id="date1" maxlength="10"><br>
            To:
            <br><input type="text" name="toDate" id="date2" maxlength="10"><br>
            <br><br> 
              <button type="submit" class="mui-btn mui-btn--raised" value="Search">Search</button>
            
        </form>
        
        <br><br>
        <a href="supplier.get?viewSupp=yes">View All Suppliers</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
        
        
    </body>
</html>
