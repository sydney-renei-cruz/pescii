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
         <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <div id="content-wrapper">

            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
                        <legend class="mui--text-center mui--text-display3">Search Restock Order</legend>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        
                        <c:if test="${success_msg != null}">
                            <div class="mui-col-md-12 mui--text-center">
                                <div id="success-msg">${success_msg}</div>
                                <c:remove var="success_msg" scope="session"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                       <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <h4>Set the conditions of your search</h4><br>
        
        <p><b>Search</b></p><br>
        <form action="new.get" class="mui-form">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="restockOrder">
            Search by Restock Order Name:<br><input type="text" name="searchNameInput" maxlength="255">
            <br><br>
            Search by Supplier:<br><input type="text" name="searchSupplierInput" maxlength="255">
            <br><br>
            Search by Date:<br><select name="searchDateInput">
                    <br><option value="RODateDue">Expected Arrival Date</option>
                    <br><option value="RODateDelivered">Date Received</option>
                    <br><option value="RestockOrder.dateCreated">Date Created</option>
            </select><br><br>
            From:<br><input type="text" name="fromDate" id="date1" maxlength="10"><br>
            To:<br><input type="text" name="toDate" id="date2" maxlength="10"><br><br>
            
            <c:set var="rostatList" value="${requestScope.roStatList}"/> 
            Search by Status:<br>
                <c:forEach items="${rostatList}" var="rStatList" begin="0" step="1">
                    <input type="checkbox" name="searchStatusInput" value="${rStatList.getStatusName()}">${rStatList.getStatusName()}
                </c:forEach>
            <br><br>
             <button type="submit" class="mui-btn mui-btn--raised" value="Search">Search</button>
             <br><br><br>
        </form>
        
        <form action="new.get" class="mui-form">
            <input type="hidden" name="whatFor" value="restockOrder">
            
            <b>Shortcuts</b><br>
                <input type="radio" name="getWhat" value="new">View New Restock Orders<br>
                <input type="radio" name="getWhat" value="completed">View Recently Completed Restock Orders<br>
                <input type="radio" name="getWhat" value="close">View Restock Orders Arriving Soon<br>
                <br>
             <button type="submit" class="mui-btn mui-btn--raised" value="Get">Get</button>
   
        </form>
                    </div>
                    
        
        
        
        
        <br><br>
        <a href="restockOrder.get">View All Restock Orders</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
