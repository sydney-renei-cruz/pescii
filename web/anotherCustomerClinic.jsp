<%-- 
    Document   : anotherCustomerClinic
    Created on : 04 30, 17, 1:18:18 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    String message = ""+request.getAttribute("message");
    //String customerID = ""+request.getAttribute("custID");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Customer Data Saved</title>
    </head>
    <body>
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="customerID" value="${requestScope.custID}"/>   
        <a href="salesrep.get?whatFor=addCustomer">Add a new Customer</a><br>
        <c:if test="${customerID ne '' && customerID ne null}">
            <a href="Servlets.viewCustomerDetailsServlet?editWhat=cust&forEdit=yes&custID=<c:out value="${customerID}"/>">Edit created Customer</a><br>
            <a href="province.get?whatFor=addClinic&custID=<c:out value="${customerID}"/>">Add another Clinic to created Customer</a><br>
            <a href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=<c:out value="${customerID}"/>">View Customer Details</a><br><br>
        </c:if>
        <a href="Servlets.getCustomerServlet">Edit another Customer</a><br>
        <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a><br>
        <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
