<%-- 
    Document   : viewCart
    Created on : 09 3, 16, 2:55:34 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.LinkedList,java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    /*<String> cart = new LinkedList<String>();
    if(session.getAttribute("cart")!=null){cart = (LinkedList<String>)session.getAttribute("cart");}
    int cartSize=cart.size();*/
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII view cart</title>
    </head>
    <body>
        <h1>This is the View Cart page!</h1>
        
        <c:choose>
            <c:when test="${sessionScope.cart != null}">
                <c:set var="cartSize" value="${sessionScope.cart.size()}"/>
                <c:set var="cart" value="${sessionScope.cart}"/>
                <c:set var="prodNames" value="${sessionScope.prodNames}"/>
                <p>the size is <c:out value="${cartSize}"/></p>
                
                <!--Now make the table-->
                <form action="addToCart">
                    <input type="hidden" name="gotQuantity" value="yes">
                    <c:forEach items="${prodNames}" var="prods" begin="0" step="1">
                        ${prods} <input type="text" name="${prods}"><br>
                    </c:forEach>
                        <br>
                        <input type="submit" value="Select Customer">
                </form>
                
                
            </c:when>

            <c:when test="${sessionScope.cart == null}">
                <%LinkedList<String> emptyCart = new LinkedList<String>();%>
                <c:set var="cartSize" value="0"/>
                <p>the size is 0</p>
            </c:when>
        </c:choose>


        <br><br>
        <a href="Servlets.getProductServlet?forInvoice=yes">Return to Products List</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
