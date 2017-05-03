<%-- 
    Document   : viewROCart
    Created on : 04 28, 17, 6:06:12 PM
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
        <title>PESCII view RO Cart</title>
    </head>
    <body>
        <h1>This is the View RO Cart page!</h1>
        
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <c:choose>
            <c:when test="${sessionScope.ROcart != null}">
                <c:set var="cartSize" value="${sessionScope.ROcart.size()}"/>
                <c:set var="cart" value="${sessionScope.ROcart}"/>
                <c:set var="prodNames" value="${sessionScope.ROprodNames}"/>
                <c:set var="quantity" value="${sessionScope.ROquantity}"/>
                <h4>the size is <c:out value="${cartSize}"/></h4>
                <p>Please enter the quantity you wish to purchase.</p>
                
                <!--Now make the table-->
                <form action="addToROCart">
                    <input type="hidden" name="gotQuantity" value="yes">
                    <c:forEach items="${prodNames}" var="prods" begin="0" step="1" varStatus="loop">
                        <a href="removeFromROCart?prodName=<c:out value="${prods}"/>">REMOVE</a>
                        ${prods}: <input type="text" name="${prods}" value="${quantity[loop.index]}"><br>
                    </c:forEach>
                        <br>
                        <input type="submit" value="Fill out RO form">
                </form>
                
                
            </c:when>

            <c:when test="${sessionScope.ROcart == null}">
                <%LinkedList<String> emptyCart = new LinkedList<String>();%>
                <c:set var="cartSize" value="0"/>
                <p>0 products selected</p>
            </c:when>
        </c:choose>


        <br><br>
        <a href="Servlets.createRestockOrderServlet?cancel=yes">Cancel Restock Order</a>
        <br><br>
        <a href="Servlets.getProductServlet?forOther=restock">Go to Products List</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
