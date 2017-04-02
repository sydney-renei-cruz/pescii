<%-- 
    Document   : invoiceDetails
    Created on : 08 25, 16, 4:50:42 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    invoiceBean invoice = (invoiceBean)request.getAttribute("invoice");
    ArrayList<invoiceItemBean> invitemsList = (ArrayList<invoiceItemBean>)request.getAttribute("invitemsList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>This is the Invoice Details Page!</h1>
        <br><br><br>
        <!--Invoice-->
        <h5>Invoice Details</h5>
        
        <p>Invoice ID: ${invoice.getInvoiceID()}</p>
        <p>Invoice Name: ${invoice.getInvoiceName()}</p>
        <p>Customer PRCID: ${invoice.getPRCID()}</p>
        <p>Customer Name: ${invoice.getCustomerName()}</p>
        <p>Clinic Name: ${invoice.getClinicName()}</p>
        <p>Province: ${invoice.getProvinceName()}</p>
        <p>Invoice Date: ${invoice.getDateCreated()}</p>
        <p>Delivery Date: ${invoice.getDeliveryDate()}</p>
        <p>Date Delivered: ${invoice.getDateDelivered()}</p>
        <p>Terms Of Payment: ${invoice.getTermsOfPayment()}</p>
        <p>Date Paid: ${invoice.getDatePaid()}</p>
        <p>Amount Due: ${invoice.getAmountDue()}</p>
        <p>Discount: ${invoice.getDiscount()}</p>
        <p>Amount Paid: ${invoice.getAmountPaid()}</p>
        <p>Date Closed: ${invoice.getDateClosed()}</p>
        <p>Status: ${invoice.getStatusName()}</p>
        <p>Overdue Fee: ${invoice.getOverdueFee()}</p>
        <p>Last Editted By: ${invoice.getLastEdittedBy()}</p>
        
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
                <td>${invitem.getTotalCost()}</td>
            </tr>
        </c:forEach>
        </table>
        <br><br>
        <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${invoice.getInvoiceID()}"/>">Edit Invoice</a>
        <br><br>
        <a href="Servlets.getInvoiceServlet">Return to list of Invoices</a>
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
