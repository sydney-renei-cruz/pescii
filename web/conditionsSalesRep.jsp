<%-- 
    Document   : conditionsSalesRep
    Created on : 01 5, 17, 3:20:58 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Search Sales Rep</title>
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
        <h1>This is the Conditions Sales Rep page!</h1>
        
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="salesRep">
            
            Search by First Name:<input type="text" name="searchSalesRepFirstNameInput" maxlength="100">
            <br><br>
            Search by Last Name:<input type="text" name="searchSalesRepLastNameInput" maxlength="100">
            <br><br>
            <b>Search by Date Created</b>
            <br><br>
            From:<input type="text" name="fromDate" id="date1" maxlength="10"><br>
            To:<input type="text" name="toDate" id="date2" maxlength="10"><br>
            <br><br>    
                
            <input type="submit" value="Search">    
        </form>
        
        
        <br><br>
        <a href="salesrep.get">View all Sales Reps</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
