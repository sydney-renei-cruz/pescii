<%-- 
    Document   : customerDetails
    Created on : 08 23, 16, 5:29:24 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
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
        
        
        <table border="1">
            <tr>
                <th>PRC ID</th>
                <th>Customer Name</th>
                <th>Mobile #</th>
                <th>Telephone #</th>
            </tr>
        
            <tr>
                <td>${customer.getPRCID()}</td>
                <td>${customer.getCustomerName()}</td>
                <td>${customer.getCustomerMobileNumber()}</td>
                <td>${customer.getCustomerTelephoneNumber()}</td>
            </tr>
        </table>
            
        <br><br>
            
            
        <!-- this is the table for the clinics-->
        <h5>Clinics</h5>
        <table border="1">
            <tr>
                <th>Clinic ID</th>
                <th>Clinic Name</th>
                <th>Clinic Address</th>
                <th>Clinic Phone #</th>
            </tr>
        
        <c:forEach items="${clinicsList}" var="clin" begin="0" step="1">
            <tr>
                <td>${clin.getClinicID()}</td>
                <td>${clin.getClinicName()}</td>
                <td>${clin.getClinicAddress()}</td>
                <td>${clin.getClinicPhoneNumber()}</td>
            </tr>
        </c:forEach>
        </table>
        
        <br><br>
         <!-- this table is for the invoices-->
        <h5>Invoices</h5>
        <table border="1">
            <tr>
                <th>Invoice ID</th>
                <th>Clinic ID</th>
                <th>Payment Due Date</th>
                <th>Date Paid</th>
                <th>Status</th>
            </tr>
        
        <c:forEach items="${invoicesList}" var="inv" begin="0" step="1">
            <tr>
                <td><a href="Servlets.viewInvoiceDetailsServlet?invID=<c:out value="${inv.getInvoiceID()}"/>">${inv.getInvoiceID()}</a></td>
                <td>${inv.getClinicID()}</td>
                <td>${inv.getPaymentDueDate()}</td>
                <td>${inv.getDatePaid()}</td>
                <td>${inv.getStatus()}</td>
                <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${inv.getInvoiceID()}"/>">Edit Invoice</a></td>
            </tr>
        </c:forEach>
        </table>
        
        <br><br>
        <a href="Servlets.getCustomerServlet">Return to Customer List</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
