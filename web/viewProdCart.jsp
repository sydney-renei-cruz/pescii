<%-- 
    Document   : viewProdCart
    Created on : 05 2, 17, 3:49:16 PM
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
        <title>JSP Page</title>
    </head>
    <body>
       <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">View Cart</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <c:choose>
            <c:when test="${sessionScope.prodCart != null}">
                <c:set var="prodCartSize" value="${sessionScope.prodCart.size()}"/>
                <c:set var="prodCart" value="${sessionScope.prodCart}"/>
               <h4>Products selected: <c:out value="${prodCartSize}"/></h4>
                <c:if test="${prodCartSize>0}">
                <p>Please set the level in which the following product/s will be considered low in stock.</p>
                
                <form action="product.setLowstockLevel" method="post">
                    Replenish products when stock reaches: <input type="text" name="newLowstockLevel" required>
                    <input type="submit" value="Set"><br>
                </form>
                <br><br>
                <table border="1">
                    <tr>
                        <th></th>
                        <th>Product Name</th>
                        <th>Supplier</th>
                        <th>Restock Price</th>
                        <th>Low Stock</th>
                        <th>Brand</th>
                        <th>Product Class</th>
                        <th>Color</th>
                    </tr>

                    <c:forEach items="${prodCart}" var="prod" begin="0" step="1" varStatus="status">
                    <tr>
                        <td><a href="removeFromProdCart?prodID=<c:out value="${prod.getProductID()}"/>">REMOVE</a></td>
                        <td>${prod.getProductName()}</td>
                        <td>${prod.getSupplierName()}</td>
                        <td>${prod.getRestockPrice()}</td>
                        <td>${prod.getLowStock()}</td>
                        <td>${prod.getBrand()}</td>
                        <td>${prod.getProductClassName()}</td>
                        <td>${prod.getColor()}</td>
                    </tr>

                    </c:forEach>
                </table>
                </c:if>
            </c:when>

            <c:when test="${sessionScope.prodCart == null}">
                <c:set var="cartSize" value="0"/>
                <p>0 products selected</p>
            </c:when>
        </c:choose>


        <br><br>
        <a href="product.setLowstockLevel?cancel=yes">Cancel Low Stock Setting</a>
        <br><br>
        <a href="Servlets.getProductServlet">Go to Products List</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
