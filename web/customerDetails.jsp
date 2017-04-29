<%-- 
    Document   : customerDetails
    Created on : 08 23, 16, 5:29:24 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    customerBean customer = (customerBean)request.getAttribute("customer");
    ArrayList<clinicBean> clinicsList = (ArrayList<clinicBean>)request.getAttribute("clinicsList");
    ArrayList<invoiceBean> invoicesList = (ArrayList<invoiceBean>)request.getAttribute("invoicesList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Customer Details</title>
    </head>
    <body>
        <h1>This is the Customer Details page!</h1>
        <br><br><br>
        <!--this is the table for the customer details-->
        <h5>Customer Details</h5>
        
        <p>PRC ID: ${customer.getPRCID()}</p>
        <p>Last Name: ${customer.getCustomerLastName()}</p>
        <p>First Name: ${customer.getCustomerFirstName()}</p>
        <p>Mobile Number: ${customer.getCustomerMobileNumber()}</p>
        <p>Telephone Number: ${customer.getCustomerTelephoneNumber()}</p>
        <p>Sales Rep:${customer.getSalesRep()}</p>
        <p>Sales Rep ID:${customer.getSalesRepID()}</p>
        
            
        <br><br>
        <a href="Servlets.viewCustomerDetailsServlet?forEdit=yes&custID=<c:out value="${customer.getCustomerID()}"/>">Edit</a>    
        <br><br>
        
        <!-- this is the table for the clinics-->
        <h5>Clinics</h5>
        <table border="1">
            <tr>
                <th>Clinic ID</th>
                <th>Clinic Name</th>
                <th>Clinic Address</th>
                <th>Province</th>
                <th>Clinic Phone #</th>
            </tr>
        
        <c:forEach items="${clinicsList}" var="clin" begin="0" step="1">
            <tr>
                <td>${clin.getClinicID()}</td>
                <td>${clin.getClinicName()}</td>
                <td>${clin.getClinicAddress()}</td>
                <td>${clin.getProvinceName()}</td>
                <td>${clin.getClinicPhoneNumber()}</td>
                <td><a href="customer.getClinic?clinID=<c:out value="${clin.getClinicID()}"/>">Edit</a></td>
            </tr>
        </c:forEach>
        </table>
        <br><br>
        <form action="province.get">
            <input type="hidden" value="addClinic" name="whatFor">
            <input type="hidden" value="${customer.getCustomerID()}" name="custID">
            <input type="submit" value="Add Clinic">
        </form>
        <br><br>
         <!-- this table is for the invoices-->
        <h5>Invoices</h5>
        <table border="1">
            <tr>
                <th>Invoice ID</th>
                <th>Invoice Name</th>
                <th>Clinic Name</th>
                <th>Payment Due Date</th>
                <th>Date Paid</th>
                <th>Status</th>
            </tr>
        
        <c:forEach items="${invoicesList}" var="inv" begin="0" step="1">
            <tr>
                <td>${inv.getInvoiceID()}</td>
                <td><a href="Servlets.viewInvoiceDetailsServlet?invID=<c:out value="${inv.getInvoiceID()}"/>">${inv.getInvoiceName()}</a></td>
                <td>${inv.getClinicName()}</td>
                <td>${inv.getPaymentDueDate()}</td>
                <td>${inv.getDatePaid()}</td>
                <td>${inv.getStatusName()}</td>
                <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${inv.getInvoiceID()}"/>">Edit Invoice</a></td>
            </tr>
        </c:forEach>
        </table>
        
        <br><br>
        <a href="Servlets.getCustomerServlet">Return to list of customers</a>
        <br><br>
        <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a>
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
