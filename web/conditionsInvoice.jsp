<%-- 
    Document   : conditionsInvoice
    Created on : 11 13, 16, 2:24:50 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<provinceBean> provList = (ArrayList<provinceBean>)request.getAttribute("provList");
    ArrayList<invoiceStatusBean> invStatList = (ArrayList<invoiceStatusBean>)request.getAttribute("invStatList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Invoice</title>
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
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Search for Invoice</legend>
                        <c:if test="${success_msg != null}">
                            <div class="mui-col-md-12 mui--text-center">
                                <div id="success-msg">${success_msg}</div>
                                <c:remove var="success_msg" scope="session"/>
                            </div>
                        </c:if>
                    </div>
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        <div class="mui-col-md-8 mui-col-md-offset-2">
            
        <h4>Set the conditions of your search</h4>
        
        <p><b>Search</b></p>
        <form action="new.get" class="mui-form" id="add-product-form">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="invoice">
            Search by Invoice Name:<br><input type="text" name="searchNameInput" maxlength="255">
            <br><br>
            Search by Status:<br>
                    <c:forEach items="${invStatList}" var="invStat" begin="0" step="1">
                        <input type="checkbox" name="searchStatusInput" value="${invStat.getStatusName()}">${invStat.getStatusName()}
                    </c:forEach>
                    
            <br><br>
            <b>Search by Customer Name:</b><br>
            Last Name:<br> <input type="text" name="searchCustomerLastNameInput" maxlength="100"><br>
            First Name:<br> <input type="text" name="searchCustomerFirstNameInput" maxlength="100"><br>
            <br>
            Search by Province:<br><select name="searchProvinceInput">
                    <option value="all">All</option>
                    <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                    </c:forEach>    
                </select>
            <br><br>
            Search by Date:<br><select name="searchDateInput">
                    <option value="Invoice.invoiceDate">Invoice Date</option>
                    <option value="Invoice.deliveryDate">Delivery Date</option>
                    <option value="Invoice.paymentDueDate">Payment Deadline</option>
                    <option value="Invoice.datePaid">Date Paid</option>
                    <option value="Invoice.dateClosed">Close Date</option>
                    <option value="Invoice.dateDelivered">Date Delivered</option>
                    <option value="Invoice.dateCreated">Date Created</option>
            </select>
            <br><br>
            From:<br><input type="text" name="fromDate" id="date1" maxlength="10"><br>
            <br><input type="text" name="toDate" id="date2" maxlength="10"><br><br>
            
            <br><br>
            <b>Search by Sales Representative:</b><br>
            Last Name: <br><input type="text" name="searchSalesRepLastNameInput" maxlength="100"><br>
            First Name:<br> <input type="text" name="searchSalesRepFirstNameInput" maxlength="100"><br>
            <br>
            
            
           <button type="submit" class="mui-btn mui-btn--raised"  value="Search">Search</button>
                            </div>
                        </form>
                    </div>
               
         
        
        <div class="mui-col-md-8 mui-col-md-offset-2">
        <form action="new.get"class="mui-form" id="add-product-form">
            <input type="hidden" name="whatFor" value="invoice">
            
            <b>Shortcuts</b><br>
                <input type="radio" name="getWhat" value="new">View New Invoices<br>
                <input type="radio" name="getWhat" value="unpaid">View Unpaid Invoices<br>
                <input type="radio" name="getWhat" value="validated">View validated Invoices<br>
                <input type="radio" name="getWhat" value="undelivered">View Undelivered Invoices<br>
                <input type="radio" name="getWhat" value="close">View Invoices near payment deadlines<br>
                <input type="radio" name="getWhat" value="overduePay">View Invoices with Overdue Payments<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        </div>
        
        
         <div class="mui-col-md-8 mui-col-md-offset-2">
        <br><br>
        <a href="Servlets.getInvoiceServlet">View All Invoices</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
<br><br><br><br><br><br>
        </div>
        
    </body>
</html>
