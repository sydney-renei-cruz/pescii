<%-- 
    Document   : addInvoice
    Created on : 08 20, 16, 11:27:31 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList, java.util.LinkedList,java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <div id="content-wrapper">
        
        <c:set var="customer" value="${requestScope.customer}"/>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        
         <div class="mui-textfield mui-textfield--float-label">
                   
                    </div>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            
            
         <legend class="mui--text-center mui--text-display3">Add Invoice</legend>
            <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
                <p>${errorMessage}</p><br><br>
            </c:if>
            <table  class="mui-table mui--text-center" id="customer-table" >
                 <thead>
                    <tr>
                        <th>PRC ID</th>
                        <th>Customer Name</th>
                        <th>Mobile #</th>
                        <th>Telephone #</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${customer.getPRCID()}</td>
                        <td>${customer.getCustomerLastName()}, ${customer.getCustomerFirstName()}</td>
                        <td>${customer.getCustomerMobileNumber()}</td>
                        <td>${customer.getCustomerTelephoneNumber()}</td>
                    </tr>
                </tbody>
            </table>
            
            <br><br><br>
        
        <table class="mui-table mui--text-center" id="customer-table" >
                 <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                    </tr>
                 </thead>
                 <tbody>
                <c:choose>
                    <c:when test="${sessionScope.cart != null}">
                        <c:set var="cartSize" value="${sessionScope.cart.size()}"/>
                        <c:set var="cart" value="${sessionScope.cart}"/>
                        <c:set var="quantity" value="${sessionScope.quantity}"/>
                        <c:set var="totalPrices" value="${sessionScope.totalPrices}"/>
                        <c:if test="${cartSize > 0}">
                        <c:forEach items="${cart}" var="prods" begin="0" step="1" varStatus="loop">
                            <tr>
                                <td><c:out value="${prods.getProductName()}"/></td>
                                <td><fmt:formatNumber pattern="0.00" value="${prods.getProductPrice()}" type="number"/></td>
                                <td><c:out value="${quantity[loop.index]}"/></td>
                                <td><fmt:formatNumber pattern="0.00" value="${totalPrices[loop.index]}" type="number"/></td>
                            </tr>
                        </c:forEach>
                        </c:if>
                    </c:when>

                    <c:when test="${sessionScope.cart == null}">
                       <p>the size is 0</p>
                    </c:when>
                </c:choose>
        </tbody>
                
            </table>
            
            <br><br><br>
      </div>
       <div id="content-wrapper">
        <form action="invoice.add" method="POST"class="mui-form" id="add-customer-form">
            
            Enter Invoice Name: <br> 
            <input type="text" name="invoiceNameInput" maxlength="255" required><br>
            Select Clinic Address: <br> 
            <select name="chosenClinic"><br> 
                <c:forEach items="${clinicsList}" var="clin" begin="0" step="1">
                    <option value="${clin.getClinicID()}">${clin.getClinicAddress()}</option>
                </c:forEach>
            </select>
            
            <br><br>
            <input type="hidden" name="customerIDInput" value="${customer.getCustomerID()}">
            
            Enter Delivery Date: <br> <input type="text" name="deliveryDateInput" id="date1" required><br>
            Terms of Payment:<br>  <select name="topInput">
                <option value="Cash">Cash</option>
                <option value="Card">Card</option>
                <option value="Cheque">Cheque</option>
            </select><br>
            Payment Due Date:<br>  <input type="text" name="paymentDueDateInput" id="date2" required><br>
            
            Amount Due: <br> <input type="text" name="amountDueInput" value="0" required><br>
            Discount:<br>  <input type="text" name="discountInput" value="0"><br>
            
            <c:if test="${accountType eq '3' || accountType eq '1'}">
                <input type='hidden' value='2' name='statusInput'>
                Amount Paid: <br><input type="text" name="amountPaidInput" value="0"><br>
                Date Paid: <br><input type="text" name="datePaidInput" id="date3"><br>
            </c:if>
            <c:if test="${accountType eq '6'}">
                <input type='hidden' value='2' name='statusInput'>
                <input type="hidden" name="amountPaidInput" value="0">
                <input type="hidden" name="datePaidInput" value="">
            </c:if>
            
            Sales Representative: <br> <input type="hidden" name="salesRepIDInput" value="${customer.getSalesRepID()}">${customer.getSalesRep()}<br>
           
            <button type="submit"  value="Add Invoice" class="mui-btn mui-btn--raised">Submit</button>
        </form>
        </div>    
         <div id="content-wrapper">   
        <br><br>
        <a href="invoice.add?cancel=yes">Cancel Invoice</a>
        <br><br>
        <a href="Servlets.getCustomerServlet">Change Customer</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
         </div>
    </body>
</html>
