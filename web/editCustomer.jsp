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
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
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
        <a href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=<c:out value="${customer.getCustomerID()}"/>">Return to Customer Details</a>
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
