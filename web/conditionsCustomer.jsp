<%-- 
    Document   : conditionsCustomer
    Created on : 12 27, 16, 7:23:19 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<salesRepBean> salesRepList = (ArrayList<salesRepBean>)request.getAttribute("salesRepsList");
    ArrayList<provinceBean> provinceList = (ArrayList<provinceBean>)request.getAttribute("provList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Customer</title>
    </head>
    <body>
         <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        <c:set var="salesRepList" value="${requestScope.salesRepsList}"/>
        <c:set var="provinceList" value="${requestScope.provList}"/>
        
        <!--this is the error message-->
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Search Customer</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
           <div class="mui-col-md-12 mui--text-center">
                <div id="success-msg">${success_msg}</div> 
            <p>${errorMessage}</p><br><br>
           </div>
        </c:if>
         </div>
                    <div class="mui-col-md-8 mui-col-md-offset-2">
        <h4>Set the conditions of your search</h4>
        <form action="new.get"  class="mui-form">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="customer">
            <b>Search by Customer Name:</b><br>
            Last Name: 
                            <br>
            <input type="text" name="searchCustomerLastNameInput" maxlength="100"><br>
              
            First Name:<br> <input type="text" name="searchCustomerFirstNameInput" maxlength="100"><br>
            <br>
            Search by PRCID:
            <br>
            <input type="text" name="searchPRCIDInput" maxlength="50"><br>
            
            Search by Clinic Name: 
            <br><input type="text" name="searchClinicNameInput" maxlength="255"><br>
            
            <br><br>
            <b>Search by Sales Representative:</b><br>
            Last Name:<br> <input type="text" name="searchSalesRepLastNameInput" maxlength="100"><br>
            First Name:<br> <input type="text" name="searchSalesRepFirstNameInput" maxlength="100"><br>
            <br>
            
            
            <br><br>
            Search by Province:<br><select name="searchProvinceInput">
                    <option value="all">All</option>
                    <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                    </c:forEach>    
            </select><br><br>
                
            <button type="submit" class="mui-btn mui-btn--raised" value="Search">Search</button>
        </form>
        
        
        <br><br>
        <a href="Servlets.getCustomerServlet">View All Customers</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>