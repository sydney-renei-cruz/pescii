<%-- 
    Document   : addInvoice
    Created on : 08 20, 16, 11:27:31 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList, java.util.LinkedList,java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    customerBean customer = (customerBean)request.getAttribute("customer");
    ArrayList<clinicBean> clinicsList = (ArrayList<clinicBean>)request.getAttribute("clinicsList");
    ArrayList<invoiceStatusBean> invStatList = (ArrayList<invoiceStatusBean>)request.getAttribute("invStatList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Invoice</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
            function init() {
                calendar.set("date1");
                calendar.set("date2");
                calendar.set("date3");
            }
        </script>
    </head>
    <body onload="init()">
        <h1>This is the Add Invoice page!</h1>
        
        <!--this is the error message-->
        <c:if test="${message ne '' || message ne null || message ne 'null'}">
            <p>${message}</p><br><br>
        </c:if>
            
            <table border="1">
                <tr>
                    <th>PRC ID</th>
                    <th>Customer Name</th>
                    <th>Mobile #</th>
                    <th>Telephone #</th>
                </tr>

                <tr>
                        <td>${customer.getPRCID()}</td>
                        <td>${customer.getCustomerLastName()}, ${customer.getCustomerFirstName()}</td>
                        <td>${customer.getCustomerMobileNumber()}</td>
                        <td>${customer.getCustomerTelephoneNumber()}</td>
                </tr>
            </table>
            
            <br><br><br>
        
        <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total Price</th>
                </tr>
                <c:choose>
                    <c:when test="${sessionScope.cart != null}">
                        <c:set var="cartSize" value="${sessionScope.cart.size()}"/>
                        <c:set var="cart" value="${sessionScope.cart}"/>
                        <c:set var="prodNames" value="${sessionScope.prodNames}"/>
                        <c:set var="prodPrices" value="${sessionScope.prodPrices}"/>
                        <c:set var="quantity" value="${sessionScope.quantity}"/>
                        <c:set var="totalPrices" value="${sessionScope.totalPrices}"/>

                        <c:forEach items="${cart}" var="prods" begin="0" step="1" varStatus="loop">
                            <tr>
                                <td><c:out value="${prodNames[loop.index]}"/></td>
                                <td><c:out value="${prodPrices[loop.index]}"/></td>
                                <td><c:out value="${quantity[loop.index]}"/></td>
                                <td><c:out value="${totalPrices[loop.index]}"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>

                    <c:when test="${sessionScope.cart == null}">
                        <%LinkedList<String> emptyCart = new LinkedList<String>();%>
                        <c:set var="cartSize" value="0"/>
                        <p>the size is 0</p>
                    </c:when>
                </c:choose>
                
                
            </table>
            
            <br><br><br>
        
        <form action="invoice.add" method="POST">
            
            Enter Invoice Name: <input type="text" name="invoiceNameInput" maxlength="255" required><br>
            Select Clinic: <select name="chosenClinic">
                <c:forEach items="${clinicsList}" var="clin" begin="0" step="1">
                    <option value="${clin.getClinicID()}">${clin.getClinicAddress()}</option>
                </c:forEach>
            </select>
            
            <br><br>
            <input type="hidden" name="customerIDInput" value="${customer.getCustomerID()}">
            
            Enter Delivery Date: <input type="text" name="deliveryDateInput" id="date1" required><br>
            Terms of Payment: <input type="text" name="topInput" maxlength="20" required><br>
            Payment Due Date: <input type="text" name="paymentDueDateInput" id="date2" required><br>
            Date Paid: <input type="text" name="datePaidInput" id="date3"><br>
            Status: <select name="statusInput">
                <c:forEach items="${invStatList}" var="invStat" begin="0" step="1">
                    <option value="${invStat.getStatusID()}">${invStat.getStatusName()}</option>
                </c:forEach>
            </select><br>
            Amount Due: <input type="text" name="amountDueInput" value="0" required><br>
            Discount: <input type="text" name="discountInput" value="0"><br>
            Amount Paid: <input type="text" name="amountPaidInput" value="0"><br>
            
            <input type="submit" value="Add Invoice">
        </form>
        <br><br>
        <a href="invoice.add?cancel=yes">Cancel Invoice</a>
        <br><br>
        <a href="Servlets.getCustomerServlet">Change Customer</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
