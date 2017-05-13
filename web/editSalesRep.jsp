<%-- 
    Document   : editSalesRep
    Created on : 11 1, 16, 3:15:17 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    salesRepBean salesRep = (salesRepBean)request.getAttribute("srbean");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Sales Rep</title>
    </head>
    <body>
        <h1>This is the Edit Sales Rep Page!</h1>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Edit Sales Representative Information</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        <c:set var="salesRep" value="${requestScope.srbean}"/>
        
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        
        <form action="salesrep.edit" method="post">
                <input type="hidden" value="${salesRep.getSalesRepID()}" name="srID">
                Last Name: <input type="text" value="${salesRep.getSalesRepLastName()}" name="newSalesRepLastNameInput" maxlength="100"><br>
                First Name: <input type="text" value="${salesRep.getSalesRepFirstName()}" name="newSalesRepFirstNameInput" maxlength="100"><br>
                Mobile Number: <input type="text" value="${salesRep.getSalesRepMobileNumber()}" name="newSalesRepMNInput" maxlength="12"><br>
                Address: <input type="text" value="${salesRep.getSalesRepAddress()}" name="newSalesRepAddressInput" maxlength="255"><br>
                
                <br><input type="submit" value="Save Changes">
        </form>
            
        <br><br>
        <a href="notif.get?">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
