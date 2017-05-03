<%-- 
    Document   : getInvoice
    Created on : 08 20, 16, 5:19:06 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Invoice</title>
    </head>
    <body>
        <h1>This is the View Invoice page!</h1>
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="invoiceList" value="${requestScope.invoiceList}"/>
        <c:set var="listSize" value="${invoiceList.size()}"/>
        
        <c:if test="${listSize > 0}">
            <table border="1">
                <tr>
                    <th>Invoice ID</th>
                    <th>Invoice Name</th>
                    <th>Customer Name</th>
                    <th>Clinic Name</th>
                    <th>Province</th>
                    <th>Status</th>
                    <th>Invoice Date</th>
                    <th>Payment Due Date</th>
                    <th>Date Paid</th>
                    <th>Delivery Date</th>
                </tr>

            <c:forEach items="${invoiceList}" var="inv" begin="0" step="1" varStatus="status">
                <tr>
                    <td>${inv.getInvoiceID()}</td>
                    <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=no&invID=<c:out value="${inv.getInvoiceID()}"/>">${inv.getInvoiceName()}</td>
                    <td>${inv.getCustomerName()}</td>
                    <td>${inv.getClinicName()}</td>
                    <td>${inv.getProvinceName()}</td>
                    <td>${inv.getStatusName()}</td>
                    <td>${inv.getInvoiceDate()}</td>
                    <td>${inv.getPaymentDueDate()}</td>
                    <td>${inv.getDatePaid()}</td>
                    <td>${inv.getDeliveryDate()}</td>
                    <c:if test="${accountType eq '2' || accountType eq '1' || accountType eq '3' || accountType eq '6'}">
                        <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${inv.getInvoiceID()}"/>">Edit Invoice</a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </table>
        </c:if>
        
        <c:if test="${listSize eq 0}">
            <p> 0 invoices found.</p>
        </c:if>

        <br><br>
        <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
