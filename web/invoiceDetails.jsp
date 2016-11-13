<%-- 
    Document   : invoiceDetails
    Created on : 08 25, 16, 4:50:42 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
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
        <table border="1">
            <tr>
                <th>Invoice ID</th>
                <th>Invoice Name</th>
                <th>PRC ID</th>
                <th>Clinic ID</th>
                <th>Invoice Date</th>
                <th>Delivery Date</th>
                <th>Additional Accessories</th>
                <th>Terms Of Payment</th>
                <th>Date Paid</th>
                <th>Date Closed</th>
                <th>Status</th>
                <th>Overdue Fee</th>
            </tr>
        
        <tr>
                <td>${invoice.getInvoiceID()}</td>
                <td>${invoice.getInvoiceName()}</td>
                <td>${invoice.getPRCID()}</td>
                <td>${invoice.getClinicID()}</td>
                <td>${invoice.getInvoiceDate()}</td>
                <td>${invoice.getDeliveryDate()}</td>
                <td>${invoice.getAdditionalAccessories()}</td>
                <td>${invoice.getTermsOfPayment()}</td>
                <td>${invoice.getDatePaid()}</td>
                <td>${invoice.getDateClosed()}</td>
                <td>${invoice.getStatus()}</td>
                <td>${invoice.getOverdueFee()}</td>
            </tr>
        </table>
        
        <!-- Invoice Items-->
        <br><br><br>
        <h5>Invoice Items</h5>
        <table border="1">
            <tr>
                <th>Invoice Item ID</th>
                <th>Product ID</th>
                <th>Quantity Purchased</th>
            </tr>
        
        <c:forEach items="${invitemsList}" var="invitem" begin="0" step="1">
            <tr>
                <td>${invitem.getInvoiceItemID()}</td>
                <td>${invitem.getProductID()}</td>
                <td>${invitem.getQuantityPurchased()}</td>
            </tr>
        </c:forEach>
        </table>
        <br><br>
        <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${invoice.getInvoiceID()}"/>">Edit Invoice</a>
        <br><br>
        <a href="Servlets.getInvoiceServlet">Return to list of Invoices</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
