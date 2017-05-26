<%-- 
    Document   : editInvoice
    Created on : 08 20, 16, 5:17:00 PM
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
       <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Edit Invoice</legend>
                        <div class="mui-col-md-12 mui--text-center">
                            <c:if test="${success_msg != null}">
                                    <div id="success-msg">${success_msg}</div>
                                    <c:remove var="success_msg" scope="session"/>
                            </c:if>
                        </div>
                    </div>
                    <div class="mui-col-md-8 mui-col-md-offset-2">
         <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="invoice" value="${requestScope.invoice}"/>
        <c:set var="invitemsList" value="${requestScope.invitemsList}"/>
        <c:set var="invStatList" value="${requestScope.invStatList}"/>
        
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <br>
        <!--show the invoice details so users can have an easier time-->
        <h5>Invoice Details</h5>
        
        <form action="invoice.edit" method="post">
            <input type="hidden" value="${invoice.getInvoiceID()}" name="invoiceIDInput">
            <p>Invoice Name: <input type="text" value="${invoice.getInvoiceName()}" name="newInvoiceNameInput" maxlength="255" required><br>
            <p>Customer Name: <input type="hidden" value="${invoice.getPRCID()}" name="PRCIDInput">${invoice.getCustomerName()}</p>
            <p>Clinic Name: <input type="hidden" value="${invoice.getClinicID()}" name="clinicIDInput">${invoice.getClinicName()}</p>
            <p><input type="hidden" value="${invoice.getInvoiceDate()}" name="invoiceDateInput">Invoice Date Created: ${invoice.getInvoiceDate()}</p>
            <b>Delivery Due Date:</b><br>
            <c:choose>
                <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                    From: ${invoice.getDeliveryDate()}<br>
                    To: <input type="text" name="deliveryDateInput" value="${invoice.getDeliveryDate()}" id="date1" maxlength="10" required><br>
                </c:when>
                <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                    <input type="hidden" value="${invoice.getDeliveryDate()}" name="deliveryDateInput">${invoice.getDeliveryDate()}<br>
                </c:when>
            </c:choose>
            <br>
            
            <b>Terms of Payment:</b><br>
            <c:if test="${accountType ne '2'}">
                <c:choose>
                    <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                        From: ${invoice.getTermsOfPayment()}<br>
                        To: <select name="topInput">
                                <option value="Cash">Cash</option>
                                <option value="Card">Card</option>
                                <option value="Cheque">Cheque</option>
                            </select>
                        <br><br>
                    </c:when>
                    <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                        <input type="hidden" value="${invoice.getTermsOfPayment()}" name="topInput">${invoice.getTermsOfPayment()}<br><br>
                    </c:when>
                </c:choose>
            </c:if>
            <c:if test="${accountType eq '2'}">
                <input type="hidden" value="${invoice.getTermsOfPayment()}" name="topInput">${invoice.getTermsOfPayment()}<br><br>
            </c:if>
                    
            <b>Payment Due Date:</b><br>        
            <c:choose>
                <c:when test="${invoice.getDatePaid() eq '0000-00-00' || invoice.getDatePaid() eq null}">
                    From: ${invoice.getPaymentDueDate()}<br>
                    To: <input type="text" name="paymentDueDateInput" value="${invoice.getPaymentDueDate()}" id="date2" maxlength="10" required><br><br>
                </c:when>
                <c:when test="${invoice.getDatePaid() ne '0000-00-00'}">
                    <input type="hidden" value="${invoice.getPaymentDueDate()}" name="paymentDueDateInput">${invoice.getPaymentDueDate()}<br><br>
                </c:when>
            </c:choose>
                    
            
            <b>Date Paid:</b>
            <c:if test="${accountType eq '3' || accountType eq '1'}">        
                <c:choose>
                    <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                        <br>
                        From: ${invoice.getDatePaid()}<br>
                        To: <input type="text" name="datePaidInput" value="${invoice.getDatePaid()}" id="date3" maxlength="10"><br><br>
                    </c:when>
                    <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                        <input type="hidden" value="${invoice.getDatePaid()}" name="datePaidInput">${invoice.getDatePaid()}<br><br>
                    </c:when>
                </c:choose>
            </c:if>
            <c:if test="${accountType eq '6' || accountType eq '2'}">
                <input type="hidden" value="${invoice.getDatePaid()}" name="datePaidInput">${invoice.getDatePaid()}<br><br>
            </c:if>  
                    
            <b>Status:</b>
            <c:if test="${accountType eq '3' || accountType eq '1' || accountType eq '2'}">
                <c:choose>
                    <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                        <br>
                        From: ${invoice.getStatusName()}<br>
                        To: 
                        <select name="statusInput">
                            <c:forEach items="${invStatList}" var="invStat" begin="0" step="1">
                            <option value="${invStat.getStatusID()}">${invStat.getStatusName()}</option>
                            </c:forEach>
                        </select><br>
                    </c:when>
                    <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                        <input type="hidden" value="${invoice.getStatusID()}" name="statusInput">${invoice.getStatusName()}<br>
                    </c:when>
                </c:choose>
            </c:if>
            <c:if test="${accountType eq '6'}">
                <input type="hidden" value="${invoice.getStatusID()}" name="statusInput">${invoice.getStatusName()}<br>
            </c:if>            
                        
            <br>
            Amount Due:
            <c:if test="${accountType ne '2'}">
                <c:choose>
                    <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                        <input type="text" value="${invoice.getAmountDue()}" name="amountDueInput" required><br>
                    </c:when>
                    <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                        <input type="hidden" value="${invoice.getAmountDue()}" name="amountDueInput">
                        <fmt:formatNumber pattern="0.00" value="${invoice.getAmountDue()}" type="number"/><br>
                    </c:when>
                </c:choose>
            </c:if>   
            <c:if test="${accountType eq '2'}">
                <input type="hidden" value="${invoice.getAmountDue()}" name="amountDueInput">
                <fmt:formatNumber pattern="0.00" value="${invoice.getAmountDue()}" type="number"/><br>
            </c:if>
                        
            Discount:
            <c:if test="${accountType ne '2'}">
            <c:choose>
                <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                    <input type="text" value="${invoice.getDiscount()}" name="discountInput" required><br>
                </c:when>
                <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                    <input type="hidden" value="${invoice.getDiscount()}" name="discountInput">
                    <fmt:formatNumber pattern="0.00" value="${invoice.getDiscount()}" type="number"/><br>
                </c:when>
            </c:choose>
            </c:if>
            <c:if test="${accountType eq '2'}">
                <input type="hidden" value="${invoice.getDiscount()}" name="discountInput">
                <fmt:formatNumber pattern="0.00" value="${invoice.getDiscount()}" type="number"/><br>
            </c:if>
                   
            Amount Paid:
            <c:if test="${accountType eq '3' || accountType eq '1'}">
                <c:choose>
                    <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                        <input type="text" value="${invoice.getAmountPaid()}" name="amountPaidInput" required><br>
                    </c:when>
                    <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                        <input type="hidden" value="${invoice.getAmountPaid()}" name="amountPaidInput">
                        <fmt:formatNumber pattern="0.00" value="${invoice.getAmountPaid()}" type="number"/><br>
                    </c:when>
                </c:choose>
            </c:if>     
            <c:if test="${accountType eq '6' || accountType eq '2'}">
                <input type="hidden" value="${invoice.getAmountPaid()}" name="amountPaidInput">
                <fmt:formatNumber pattern="0.00" value="${invoice.getAmountPaid()}" type="number"/><br>
            </c:if>
                    
            Date Delivered:
            <c:if test="${accountType eq '3' || accountType eq '1' || accountType eq '2'}">
            <c:choose>
                <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                    <input type="text" value="${invoice.getDateDelivered()}" name="dateDeliveredInput" id="date4" maxlength="10"><br>
                </c:when>
                <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                    <input type="hidden" value="${invoice.getDateDelivered()}" name="dateDeliveredInput">${invoice.getDateDelivered()}<br>
                </c:when>
            </c:choose>
            </c:if>
            <c:if test="${accountType eq '6'}">
                <input type="hidden" value="${invoice.getDateDelivered()}" name="dateDeliveredInput">${invoice.getDateDelivered()}<br>
            </c:if>        
                    
                    
            Overdue Fee:
            <c:if test="${accountType eq '3' || accountType eq '1'}">
                <c:choose>
                    <c:when test="${invoice.getStatusName() eq 'In Progress'}">
                        <input type="text" value="${invoice.getOverdueFee()}" name="overdueFeeInput" required><br>
                    </c:when>
                    <c:when test="${invoice.getStatusName() ne 'In Progress'}">
                        <input type="hidden" value="${invoice.getOverdueFee()}" name="overdueFeeInput">
                        <fmt:formatNumber pattern="0.00" value="${invoice.getOverdueFee()}" type="number"/><br>
                    </c:when>
                </c:choose>
            </c:if>
            <c:if test="${accountType ne '3' && accountType ne '1'}">
                <input type="hidden" value="${invoice.getOverdueFee()}" name="overdueFeeInput">
                <fmt:formatNumber pattern="0.00" value="${invoice.getOverdueFee()}" type="number"/><br>
            </c:if>
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
        <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=no&invID=<c:out value="${invoice.getInvoiceID()}"/>">Go to Invoice details</a>
        <br><br>
        <a href="Servlets.getInvoiceServlet">Go to list of Invoices</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
