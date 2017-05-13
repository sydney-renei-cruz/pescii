<%-- 
    Document   : conditionsProduct
    Created on : 11 13, 16, 12:43:47 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    ArrayList<supplierBean> suppList = (ArrayList<supplierBean>)request.getAttribute("suppliersList");
    String forOther = ""+request.getAttribute("forOther");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Product</title>
    </head>
    <body>
       <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Search Product</legend>
        <h4>Set the conditions of your search</h4>
        <c:set var="suppList" value="${requestScope.suppliersList}"/>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
     
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="product">
            <c:if test="${forOther eq 'invoice'}">
                <input type="hidden" name="forOther" value="invoice">
            </c:if>
            <c:if test="${forOther eq 'restock'}">
                <input type="hidden" name="forOther" value="restock">
            </c:if>
            
            Search by Name:<br><br><input type="text" name="searchNameInput"  maxlength="255">
            <br><br>
            Search by Brand:<br><input type="text" name="searchBrandInput"  maxlength="50">
            <br><br>
            Product Class:<br>
                
                <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                    <input type="checkbox" name="productClassInput" value="${pro.getProductClassName()}">${pro.getProductClassName()}<br>
                </c:forEach>
                <br>
            Low Stock:<br>
                <input type="radio" name="lowStockInput" value="yes">Yes<br>
                <input type="radio" name="lowStockInput" value="no">No<br>
                <input type="radio" name="lowStockInput" value="cancel">Both<br>
                <br><br>
                
            Search by Supplier:<br><select name="searchSupplierInput">
                <option value="All">All</option>
                <c:forEach items="${suppList}" var="sup" begin="0" step="1">
                        <option value="${sup.getSupplierName()}">${sup.getSupplierName()}</option>
                </c:forEach>
            </select><br><br>
            <br><br>    
                
            <input type="submit" value="Search">    
        </form>
        
        
        <br><br>
        <a href="Servlets.getProductServlet">View All Products</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <!--THIS PART OF THE CODE CHECKS IF IT SHOULD DISPLAY THE LOG IN OR LOGOUT LINK-->
        <c:if test="${state == 'logged in'}">
            <a href="Servlets.logoutServlet">log out</a><br><br>
        </c:if>
            
        <c:if test="${state ne 'logged in'}">
            <a href="logIn.jsp">log in</a><br><br>
        </c:if>
        
        
    </body>
</html>
