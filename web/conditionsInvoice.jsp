<%-- 
    Document   : conditionsInvoice
    Created on : 11 13, 16, 2:24:50 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<provinceBean> provList = (ArrayList<provinceBean>)request.getAttribute("provList");
    ArrayList<invoiceStatusBean> invStatList = (ArrayList<invoiceStatusBean>)request.getAttribute("invStatList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Invoice</title>
    </head>
    <body>
        <h1>This is the Conditions Invoice page!</h1>
        
        
        <h4>Set the conditions of your search</h4>
        
        <p><b>Search</b></p>
        <form action="new.get">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="invoice">
            Search by Invoice Name:<input type="text" name="searchNameInput">
            <br><br>
            Search by Status:
                    <!--<option value="All">All</option>
                    <option value="In Progress">In Progress</option>
                    <option value="Completed">Completed</option>
                    <option value="Cancelled">Cancelled</option>-->
                    <c:forEach items="${invStatList}" var="invStat" begin="0" step="1">
                        <input type="checkbox" name="searchStatusInput" value="${invStat.getStatusName()}">${invStat.getStatusName()}
                    </c:forEach>
                    
            <br><br>
            <b>Search by Customer Name:</b><br>
            Last Name: <input type="text" name="searchCustomerLastNameInput"><br>
            First Name: <input type="text" name="searchCustomerFirstNameInput"><br>
            <br>
            Search by Province:<select name="searchProvinceInput">
                    <option value="all">All</option>
                    <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option><br>
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
            From:<input type="text" name="fromDate"><br>
            To:<input type="text" name="toDate"><br><br>
            
            <input type="submit" value="Search"><br><br><br>
        </form>
        
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="invoice">
            
            <b>Shortcuts</b><br>
                <input type="radio" name="getWhat" value="new">New Invoice<br>
                <input type="radio" name="getWhat" value="validated">Validated Invoices<br>
                <input type="radio" name="getWhat" value="close">Invoices near payment deadlines<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

        
        
    </body>
</html>
