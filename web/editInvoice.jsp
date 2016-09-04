<%-- 
    Document   : editInvoice
    Created on : 08 20, 16, 5:17:00 PM
    Author     : user
--%>

<<<<<<< HEAD
<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    invoiceBean invoice = (invoiceBean)request.getAttribute("invoice");
%>
=======
<%@page contentType="text/html" pageEncoding="UTF-8"%>
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Invoice</title>
    </head>
    <body>
        <h1>This is the Edit Invoice page!</h1>
<<<<<<< HEAD
        
        <br><br><br>
        <!--show the invoice details so users can have an easier time-->
        <h5>Invoice Details</h5>
        
        <form action="invoice.edit" method="post">
            <input type="hidden" value="${invoice.getInvoiceID()}" name="invoiceIDInput"><p>Invoice ID: ${invoice.getInvoiceID()}</p>
            <input type="hidden" value="${invoice.getPRCID()}" name="PRCIDInput"><p>PRC ID: ${invoice.getPRCID()}</p>
            <input type="hidden" value="${invoice.getClinicID()}" name="clinicIDInput"><p>Clinic ID: ${invoice.getClinicID()}</p>
            <input type="hidden" value="${invoice.getInvoiceDate()}" name="invoiceDateInput"><p>Invoice Date Created: ${invoice.getInvoiceDate()}</p>
            Delivery Date:<br>
            <c:choose>
                <c:when test="${invoice.getStatus() eq 'In Progress'}">
                    <br>
                    From: ${invoice.getDeliveryDate()}<br>
                    To: <input type="text" name="deliveryDateInput" value="${invoice.getDeliveryDate()}"><br>
                </c:when>
                <c:when test="${invoice.getStatus() ne 'In Progress'}">
                    <input type="hidden" value="${invoice.getDeliveryDate()}" name="deliveryDateInput">${invoice.getDeliveryDate()}<br>
                </c:when>
            </c:choose>
            <input type="hidden" value="${invoice.getAdditionalAccessories()}" name="addAccInput"><p>Additional Accessories: ${invoice.getAdditionalAccessories()}</p>
            Terms of Payment:
            <c:choose>
                <c:when test="${invoice.getStatus() eq 'In Progress'}">
                    <br>
                    From: ${invoice.getTermsOfPayment()}<br>
                    To: <input type="text" name="topInput" value="${invoice.getTermsOfPayment()}"><br><br>
                </c:when>
                <c:when test="${invoice.getStatus() ne 'In Progress'}">
                    <input type="hidden" value="${invoice.getTermsOfPayment()}" name="topInput">${invoice.getTermsOfPayment()}<br><br>
                </c:when>
            </c:choose>      
            Payment Due Date:        
            <c:choose>
                <c:when test="${invoice.getDatePaid() eq ''}">
                    <br>
                    From: ${invoice.getPaymentDueDate()}<br>
                    To: <input type="text" name="paymentDueDateInput" value="${invoice.getPaymentDueDate()}"><br><br>
                </c:when>
                <c:when test="${invoice.getDatePaid() ne ''}">
                    <input type="hidden" value="${invoice.getPaymentDueDate()}" name="paymentDueDateInput">${invoice.getPaymentDueDate()}<br><br>
                </c:when>
            </c:choose>
            Date Paid:
            <c:choose>
                <c:when test="${invoice.getDatePaid() eq ''}">
                    <br>
                    From: ${invoice.getDatePaid()}<br>
                    To: <input type="text" name="datePaidInput" value="${invoice.getDatePaid()}"><br><br>
                </c:when>
                <c:when test="${invoice.getDatePaid() ne ''}">
                    <input type="hidden" value="${invoice.getDatePaid()}" name="datePaidInput">${invoice.getDatePaid()}<br><br>
                </c:when>
            </c:choose>
            Status:
            <c:choose>
                <c:when test="${invoice.getStatus() ne 'Completed'}">
                    <br>
                    From: ${invoice.getStatus()}<br>
                    To: 
                    <select name="statusInput">
                        <option value="In Progress">In Progress</option>
                        <option value="Completed">Completed</option>
                        <option value="Cancelled">Cancelled</option>
                    </select><br>
                </c:when>
                <c:when test="${invoice.getStatus() eq 'Completed'}">
                    <input type="hidden" value="${invoice.getStatus()}" name="statusInput">${invoice.getStatus()}<br>
                </c:when>
            </c:choose>
            
            <p>Overdue Fee: ${invoice.getOverdueFee()}</p>
            <br><input type="submit" value="Save Changes">
        </form>
        
        
        <!-- Invoice Items, for user friendliness-->
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
        <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=no&invID=<c:out value="${invoice.getInvoiceID()}"/>">Return to Invoice details</a>
        <br><br>
        <a href="Servlets.getInvoiceServlet">Return to list of Invoices</a>
        <br><br>
=======

>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
