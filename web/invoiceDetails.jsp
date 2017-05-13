<%-- 
    Document   : invoiceDetails
    Created on : 08 25, 16, 4:50:42 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">View Cart for Restock Order</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="invoice" value="${requestScope.invoice}"/>
        <c:set var="invitemsList" value="${requestScope.invitemsList}"/>
        
        <br>
        <!--Invoice-->
        <h5>Invoice Details</h5>
        
        <p>Invoice ID: ${invoice.getInvoiceID()}</p>
        <p>Invoice Name: ${invoice.getInvoiceName()}</p>
        <p>Customer PRCID: ${invoice.getPRCID()}</p>
        <p>Customer Name: <a href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=<c:out value="${invoice.getCustomerID()}"/>">${invoice.getCustomerName()}</a></p>
        <p>Clinic Name: ${invoice.getClinicName()}</p>
        <p>Province: ${invoice.getProvinceName()}</p>
        <p>Invoice Date: ${invoice.getDateCreated()}</p>
        <p>Delivery Date: ${invoice.getDeliveryDate()}</p>
        <p>Date Delivered: ${invoice.getDateDelivered()}</p>
        <p>Terms Of Payment: ${invoice.getTermsOfPayment()}</p>
        <p>Date Paid: ${invoice.getDatePaid()}</p>
        <p>Amount Due: <fmt:formatNumber pattern="0.00" value="${invoice.getAmountDue()}" type="number"/></p>
        <p>Discount: <fmt:formatNumber pattern="0.00" value="${invoice.getDiscount()}" type="number"/></p>
        <p>Amount Paid: <fmt:formatNumber pattern="0.00" value="${invoice.getAmountPaid()}" type="number"/></p>
        <p>Date Closed: ${invoice.getDateClosed()}</p>
        <p>Status: ${invoice.getStatusName()}</p>
        <p>Overdue Fee: <fmt:formatNumber pattern="0.00" value="${invoice.getOverdueFee()}" type="number"/></p>
        <p>Sales Representative: ${invoice.getSalesRepName()}</p>
        <p>Last Edited By: ${invoice.getLastEdittedBy()}</p>
        
        <!-- Invoice Items-->
        <br><br><br>
        <h5>Invoice Items</h5>
        <table border="1">
            <tr>
                <th>Invoice Item ID</th>
                <th>Product Name</th>
                <th>Quantity Purchased</th>
                <th>Total Cost</th>
            </tr>
        
        <c:forEach items="${invitemsList}" var="invitem" begin="0" step="1">
            <tr>
                <td>${invitem.getInvoiceItemID()}</td>
                <td><a href="product.getDetails?prodID=<c:out value="${invitem.getProductID()}"/>">${invitem.getProductName()}</a></td>
                <td>${invitem.getQuantityPurchased()}</td>
                <td><fmt:formatNumber pattern="0.00" value="${invitem.getTotalCost()}" type="number"/></td>
            </tr>
        </c:forEach>
        </table>
        <br><br>
        <c:if test="${accountType eq '4' || accountType eq '1' || accountType eq '3' || accountType eq '6'}">
            <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${invoice.getInvoiceID()}"/>">Edit Invoice</a>
        </c:if>
        <br><br>
        <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a><br>
        <br><br>
        <a href="Servlets.getInvoiceServlet">Go to list of Invoices</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
