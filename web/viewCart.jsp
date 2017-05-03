<%-- 
    Document   : viewCart
    Created on : 09 3, 16, 2:55:34 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.LinkedList,java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII view cart</title>
    </head>
    <body>
        <h1>This is the View Cart page!</h1>
        
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
            
        <c:choose>
            <c:when test="${sessionScope.cart != null}">
                <c:set var="cartSize" value="${sessionScope.cart.size()}"/>
                <c:set var="cart" value="${sessionScope.cart}"/>
                <c:set var="quantity" value="${sessionScope.quantity}"/>
                <h4>the size is <c:out value="${cartSize}"/></h4>
                <p>Please enter the quantity you wish to purchase.</p>
                
                <!--Now make the table-->
                <form action="addToCart">
                    <input type="hidden" name="gotQuantity" value="yes">
                    <c:forEach items="${cart}" var="prods" begin="0" step="1" varStatus="loop">
                        <a href="removeFromCart?prodName=<c:out value="${prods.getProductName()}"/>">REMOVE</a>
                        ${prods.getProductName()}: <input type="text" name="${prods.getProductName()}" value="${quantity[loop.index]}"><br>
                    </c:forEach>
                        <br>
                        <input type="submit" value="Select Customer">
                </form>
                
                
            </c:when>

            <c:when test="${sessionScope.cart == null}">
                 <p>0 products selected</p>
            </c:when>
        </c:choose>


        <br><br>
        <a href="invoice.add?cancel=yes">Cancel Invoice</a>
        <br><br>
        <a href="Servlets.getProductServlet?forOther=invoice">Go to Products List</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
