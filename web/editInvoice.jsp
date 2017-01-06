<%-- 
    Document   : editInvoice
    Created on : 08 20, 16, 5:17:00 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    invoiceBean invoice = (invoiceBean)request.getAttribute("invoice");
    ArrayList<invoiceStatusBean> invStatList = (ArrayList<invoiceStatusBean>)request.getAttribute("invStatList");
    ArrayList<invoiceItemBean> invitemsList = (ArrayList<invoiceItemBean>)request.getAttribute("invitemsList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Invoice</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
            function init() {
                calendar.set("date1");
                calendar.set("date2");
                calendar.set("date3");
                calendar.set("date4");
            }
        </script>
    </head>
    <body onload="init()">
        <h1>This is the Edit Invoice page!</h1>
        
        <br><br><br>
        <!--show the invoice details so users can have an easier time-->
        <h5>Invoice Details</h5>
        
        <form action="invoice.edit" method="post">
            <p>Invoice ID: <input type="hidden" value="${invoice.getInvoiceID()}" name="invoiceIDInput">${invoice.getInvoiceID()}</p>
            <p>Invoice Name: <input type="text" value="${invoice.getInvoiceName()}" name="newInvoiceNameInput"><br>
            <p>Customer Name: <input type="hidden" value="${invoice.getPRCID()}" name="PRCIDInput">${invoice.getCustomerName()}</p>
            <p>Clinic Name: <input type="hidden" value="${invoice.getClinicID()}" name="clinicIDInput">${invoice.getClinicName()}</p>
            <p><input type="hidden" value="${invoice.getInvoiceDate()}" name="invoiceDateInput">Invoice Date Created: ${invoice.getInvoiceDate()}</p>
            Delivery Due Date:<br>
            <c:choose>
                <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                    <br>
                    From: ${invoice.getDeliveryDate()}<br>
                    To: <input type="text" name="deliveryDateInput" value="${invoice.getDeliveryDate()}" id="date1"><br>
                </c:when>
                <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                    <input type="hidden" value="${invoice.getDeliveryDate()}" name="deliveryDateInput">${invoice.getDeliveryDate()}<br>
                </c:when>
            </c:choose>
            Terms of Payment:
            <c:choose>
                <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                    <br>
                    From: ${invoice.getTermsOfPayment()}<br>
                    To: <input type="text" name="topInput" value="${invoice.getTermsOfPayment()}"><br><br>
                </c:when>
                <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                    <input type="hidden" value="${invoice.getTermsOfPayment()}" name="topInput">${invoice.getTermsOfPayment()}<br><br>
                </c:when>
            </c:choose>      
            Payment Due Date:        
            <c:choose>
                <c:when test="${invoice.getDatePaid() eq '0000-00-00'}">
                    <br>
                    From: ${invoice.getPaymentDueDate()}<br>
                    To: <input type="text" name="paymentDueDateInput" value="${invoice.getPaymentDueDate()}" id="date2"><br><br>
                </c:when>
                <c:when test="${invoice.getDatePaid() ne '0000-00-00'}">
                    <input type="hidden" value="${invoice.getPaymentDueDate()}" name="paymentDueDateInput">${invoice.getPaymentDueDate()}<br><br>
                </c:when>
            </c:choose>
            <b>Date Paid:</b>
            <c:choose>
                <c:when test="${invoice.getDatePaid() eq null}">
                    <br>
                    From: ${invoice.getDatePaid()}<br>
                    To: <input type="text" name="datePaidInput" value="${invoice.getDatePaid()}" id="date3"><br><br>
                </c:when>
                <c:when test="${invoice.getDatePaid() ne null}">
                    <input type="hidden" value="${invoice.getDatePaid()}" name="datePaidInput">${invoice.getDatePaid()}<br><br>
                </c:when>
            </c:choose>
            <b>Status:</b>
            <c:choose>
                <c:when test="${invoice.getStatusName() ne 'Completed'}">
                    <br>
                    From: ${invoice.getStatusName()}<br>
                    To: 
                    <select name="statusInput">
                        <c:forEach items="${invStatList}" var="invStat" begin="0" step="1">
                        <option value="${invStat.getStatusID()}">${invStat.getStatusName()}</option>
                        </c:forEach>
                    </select><br>
                </c:when>
                <c:when test="${invoice.getStatusName() eq 'Completed'}">
                    <input type="hidden" value="${invoice.getStatusID()}" name="statusInput">${invoice.getStatusName()}<br>
                </c:when>
            </c:choose>
            
            Amount Due:
            <c:choose>
                <c:when test="${invoice.getStatusName() ne 'Completed'}">
                    <input type="text" value="${invoice.getAmountDue()}" name="amountDueInput"><br>
                </c:when>
                <c:when test="${invoice.getStatusName() eq 'Completed'}">
                    <input type="hidden" value="${invoice.getAmountDue()}" name="amountDueInput">${invoice.getAmountDue()}<br>
                </c:when>
            </c:choose>
                    
            Discount:
            <c:choose>
                <c:when test="${invoice.getStatusName() ne 'Completed'}">
                    <input type="text" value="${invoice.getDiscount()}" name="discountInput"><br>
                </c:when>
                <c:when test="${invoice.getStatusName() eq 'CompletstatusInputed'}">
                    <input type="hidden" value="${invoice.getDiscount()}" name="discountInput">${invoice.getDiscount()}<br>
                </c:when>
            </c:choose>
                    
            Amount Paid:
            <c:choose>
                <c:when test="${invoice.getStatusName() ne 'Completed'}">
                    <input type="text" value="${invoice.getAmountPaid()}" name="amountPaidInput"><br>
                </c:when>
                <c:when test="${invoice.getStatusName() eq 'Completed'}">
                    <input type="hidden" value="${invoice.getAmountPaid()}" name="amountPaidInput">${invoice.getAmountPaid()}<br>
                </c:when>
            </c:choose>
                    
            Date Delivered:
            <c:choose>
                <c:when test="${invoice.getStatusName() ne 'Completed'}">
                    <input type="text" value="${invoice.getDateDelivered()}" name="dateDeliveredInput" id="date4"><br>
                </c:when>
                <c:when test="${invoice.getStatusName() eq 'Completed'}">
                    <input type="hidden" value="${invoice.getDateDelivered()}" name="dateDeliveredInput">${invoice.getDateDelivered()}<br>
                </c:when>
            </c:choose>
                    
                    
                    
            <p>Overdue Fee: ${invoice.getOverdueFee()}</p>
            <br><input type="submit" value="Save Changes">
        </form>
        
        
        <!-- Display Invoice Items, for user friendliness-->
        <br><br><br>
        <h5>Invoice Items</h5>
        <table border="1">
            <tr>
                <th>Invoice Item ID</th>
                <th>Product Name</th>
                <th>Quantity Purchased</th>
            </tr>
        
        <c:forEach items="${invitemsList}" var="invitem" begin="0" step="1">
            <tr>
                <td>${invitem.getInvoiceItemID()}</td>
                <td>${invitem.getProductName()}</td>
                <td>${invitem.getQuantityPurchased()}</td>
            </tr>
        </c:forEach>
        </table>
        
        
        <br><br>
        <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=no&invID=<c:out value="${invoice.getInvoiceID()}"/>">Return to Invoice details</a>
        <br><br>
        <a href="Servlets.getInvoiceServlet">Return to list of Invoices</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
