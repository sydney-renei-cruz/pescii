<%-- 
    Document   : addSalesRep
    Created on : 11 1, 16, 10:32:03 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
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
        <title>PESCII Add Sales Rep</title>
    </head>
    <body>
         <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Add Sales Representative</legend>
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
            
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="accountsList" value="${requestScope.accountsList}"/>
        <c:set var="listSize" value="${accountsList.size()}"/>
            
        
        <div class="mui-col-md-8 mui-col-md-offset-2">   
        <form action="salesrep.add" method="post"class="mui-form" id="add-customer-form">
            <div class="mui-col-md-12">
                            <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="salesrepFirstNameInput" id="customerFirstNameInput"required>
                                    <label for="salesrepFirstNameInput">First Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="salesrepLastNameInput" id="customerLastNameInput"required>
                                    <label for="salesrepLastNameInput">Last Name</label>
                                </div>
                            </div>
            
            <div class="mui-col-md-12">
                <div class="mui-textfield mui-textfield--float-label">
                    <input type="text" name="salesrepMNInput" id="customerMobileNumberInput">
                    <label for="salesrepMNInput">Mobile Number</label>
                </div>
            </div>
            
            <div class="mui-col-md-12">
                <div class="mui-textfield mui-textfield--float-label">
                    <input type="text" name="salesrepAddressInput" id="customerMobileNumberInput">
                    <label for="salesrepAddressInput">Address</label>
                </div>
            </div>
            
            
             <c:if test="${listSize > 0}">
                     <table class="mui-table mui--text-center" id="customer-table">
                     <thead>
                        <tr>
                            <th></th>
                            <th>User Name</th>
                            <th>Account Type</th>
                            <th>Account Status</th>
                            <th>Date Created</th>
                        </tr>
                     </thead>
                     <tbody>
                    <c:forEach items="${accountsList}" var="acc" begin="0" step="1" varStatus="status">
                        <tr>
                                <td><input type="radio" name="selectedAccount" value="${acc.getAccountID()}"></td>
                                <td>${acc.getUserName()}</td>
                                <td>${acc.getAccountType()}</td>
                                <td>${acc.getAccountStatus()}</td>
                                <td>${acc.getDateCreated()}</td>
                        </tr>

                    </c:forEach>
                     </tbody>
                    </table>
                </c:if>
        <div class="text-center">
            <div id="error-msg">${error_msg}</div>
            <c:if test="${listSize eq 0}">
            <p> 0 accounts found.</p>
        </c:if>
         </div>
            
            
            
            
            
            <button type="submit" value="Save Sales Rep" class="mui-btn mui-btn--raised">Save</button>
            
        </form>
        
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
        
    </body>
</html>
