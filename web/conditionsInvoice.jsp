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
        <h1>This is the Conditions Invoice page!</h1>
        
        
        <h4>Set the conditions of your search</h4>
        
        <p><b>Search</b></p>
        <form action="new.get">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="invoice">
            Search by Invoice Name:<input type="text" name="searchNameInput" maxlength="255">
            <br><br>
            Search by Status:
                    <c:forEach items="${invStatList}" var="invStat" begin="0" step="1">
                        <input type="checkbox" name="searchStatusInput" value="${invStat.getStatusName()}">${invStat.getStatusName()}
                    </c:forEach>
                    
            <br><br>
            <b>Search by Customer Name:</b><br>
            Last Name: <input type="text" name="searchCustomerLastNameInput" maxlength="100"><br>
            First Name: <input type="text" name="searchCustomerFirstNameInput" maxlength="100"><br>
            <br>
            Search by Province:<select name="searchProvinceInput">
                    <option value="all">All</option>
                    <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                    </c:forEach>    
                </select>
            <br><br>
            Search by Date:<select name="searchDateInput">
                    <option value="Invoice.invoiceDate">Invoice Date</option>
                    <option value="Invoice.deliveryDate">Delivery Date</option>
                    <option value="Invoice.paymentDueDate">Payment Deadline</option>
                    <option value="Invoice.datePaid">Date Paid</option>
                    <option value="Invoice.dateClosed">Close Date</option>
                    <option value="Invoice.dateDelivered">Date Delivered</option>
                    <option value="Invoice.dateCreated">Date Created</option>
            </select>
            <br><br>
            From:<input type="text" name="fromDate" id="date1" maxlength="10"><br>
            To:<input type="text" name="toDate" id="date2" maxlength="10"><br><br>
            
            <input type="submit" value="Search"><br><br><br>
        </form>
        
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="invoice">
            
            <b>Shortcuts</b><br>
                <input type="radio" name="getWhat" value="new">View New Invoices<br>
                <input type="radio" name="getWhat" value="validated">View Incomplete Invoices<br>
                <input type="radio" name="getWhat" value="close">View Invoices near payment deadlines<br>
                <input type="radio" name="getWhat" value="overduePay">View Invoices with Overdue Payments<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        
        
        <br><br>
        <a href="Servlets.getInvoiceServlet">View All Invoices</a>
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
