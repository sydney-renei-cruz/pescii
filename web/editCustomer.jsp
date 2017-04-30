<%-- 
    Document   : editCustomer
    Created on : 08 20, 16, 5:20:13 PM
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
    ArrayList<salesRepBean> salesRepList = (ArrayList<salesRepBean>)request.getAttribute("salesRepList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Customer</title>
    </head>
    <body>
        <h1>This is the Edit Customer page!</h1>
        
        <c:set var="errorMessage" value="${message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <form action="customer.edit" method="post">
            <input type="hidden" value="${customer.getCustomerID()}" name="customerIDInput">
            PRCID: <input type="hidden" value="${customer.getPRCID()}" name="PRCIDInput">${customer.getPRCID()}<br>
            Last Name: <input type="text" value="${customer.getCustomerLastName()}" name="customerLastNameInput" maxlength="100"><br>
            First Name: <input type="text" value="${customer.getCustomerFirstName()}" name="customerFirstNameInput" maxlength="100"><br>
            Mobile Number: <input type="text" value="${customer.getCustomerMobileNumber()}" name="customerMobileNumberInput" maxlength="20"><br>
            Telephone Number: <input type="text" value="${customer.getCustomerTelephoneNumber()}" name="customerTelNumInput" maxlength="15"><br><br>
            <b>Sales Representative</b><br>
            From: ${customer.getSalesRep()}<br>
            To:<select name="chosenSalesRep">
                <c:forEach items="${salesRepList}" var="sr" begin="0" step="1">
                    <option value="${sr.getSalesRepID()}">${sr.getSalesRepLastName()} ${sr.getSalesRepFirstName()}</option>
                </c:forEach>
            </select><br><br>
            <br><input type="submit" value="Save Changes"><br>
        </form>
        <br><br>
        <c:if test="${accountType eq '2' || accountType eq '1'}">
            <form action="province.get">
                <input type="hidden" value="addClinic" name="whatFor">
                <input type="hidden" value="${customer.getCustomerID()}" name="custID">
                <input type="submit" value="Add Clinic">
            </form>
        </c:if>
        <br><br>
        <a href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=<c:out value="${customer.getCustomerID()}"/>">Go to Customer Details</a>
        <br><br>
        <a href="Servlets.getCustomerServlet">Go to list of customers</a>
        <br><br>
        <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
