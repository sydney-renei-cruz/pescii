<%-- 
    Document   : createRestockOrder
    Created on : 08 20, 16, 5:27:31 PM
    Author     : user
--%>
<%@page import="Beans.*,java.util.ArrayList, java.util.LinkedList,java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String accountType = ""+session.getAttribute("accountType");
    //productBean pbean = (productBean)request.getAttribute("product");
    ArrayList<restockOrderStatusBean> rosList = (ArrayList<restockOrderStatusBean>)request.getAttribute("roStatList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Create RO</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
            function init() {
                calendar.set("date1");
            }
        </script>
    </head>
    <body onload="init()">
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3"> Create Restock Order</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
       
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Restock Price</th>
                    <th>Quantity Ordered</th>
                    <th>Total Price</th>
                </tr>
                <c:choose>
                    <c:when test="${sessionScope.ROcart != null}">
                        <c:set var="cartSize" value="${sessionScope.ROcart.size()}"/>
                        <c:set var="cart" value="${sessionScope.ROcart}"/>
                        <c:set var="prodNames" value="${sessionScope.ROprodNames}"/>
                        <c:set var="prodPrices" value="${sessionScope.ROprodPrices}"/>
                        <c:set var="quantity" value="${sessionScope.ROquantity}"/>
                        <c:set var="totalPrices" value="${sessionScope.ROtotalPrices}"/>
                        <c:set var="total" value="0"/>

                        <c:forEach items="${cart}" var="prods" begin="0" step="1" varStatus="loop">
                            <tr>
                                <td><c:out value="${prodNames[loop.index]}"/></td>
                                <td><fmt:formatNumber pattern="0.00" value="${prodPrices[loop.index]}" type="number"/></td>
                                <td><c:out value="${quantity[loop.index]}"/></td>
                                <td><fmt:formatNumber pattern="0.00" value="${totalPrices[loop.index]}" type="number"/></td>
                                <c:set var="total" value = "${total+totalPrices[loop.index]}"/>
                            </tr>
                        </c:forEach>
                    </c:when>

                    <c:when test="${sessionScope.ROcart == null}">
                        <p>0 products selected</p>
                    </c:when>
                </c:choose>
                
                
            </table>
            
        <br><br><br>    
        <c:set var="rostatList" value="${requestScope.roStatList}"/>    
        <form action="Servlets.createRestockOrderServlet" method="post">
            Enter RO Name: <input type="text" name="RONameInput" maxlength="255" required><br>
            Enter Purpose:<br><textarea name="purposeInput" rows="5" cols="50"></textarea><br>
            Enter Date Due:<input type="text" name="dateDueInput" id="date1" maxlength="10" required><br>
            Total due: <c:out value="${total}"/><br>
            Enter discount:<input type="text" name="discountInput" value="0"><br>
            <input type="Submit" value="Create Restock Order">
        </form>
        
        <br><br>
        <a href="viewROCart.jsp">Return to Cart</a>
        <br><br>
        <a href="Servlets.createRestockOrderServlet?cancel=yes">Cancel Restock Order</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
