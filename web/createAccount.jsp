<%-- 
    Document   : createAccount
    Created on : 08 20, 16, 5:24:42 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    String message = ""+request.getAttribute("message");
    ArrayList<accountStatusBean> accountStatusList = (ArrayList<accountStatusBean>)request.getAttribute("accountStatusesList");
    ArrayList<accountTypeBean> accountTypes = (ArrayList<accountTypeBean>)request.getAttribute("atypeList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Create Account</title>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <h1>This is the Create Account page!</h1>
        
        <c:set var="accountTypes" value="${requestScope.atypeList}"/>
        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Create Account</legend>
                        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
                            <p>${errorMessage}</p><br><br>
                        </c:if>
        <form action="Servlets.createAccountServlet" method="post"class="mui-form" id="add-customer-form"required>
            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="usernameInput" id="customerIDInput" required>
                                    <label for="usernameInput">Username</label>
                                </div>
                            </div>
            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="password" name="passwordInput" id="customerIDInput"required>
                                    <label for="passwordInput">Password</label>
                                </div>
                            </div>
            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="password" name="password2Input" id="customerIDInput"required>
                                    <label for="password2Input">Confirm Password</label>
                                </div>
                            </div> 
           
            
            Select Account Type <br><select name="accTypeInput">
                <c:forEach items="<%=accountTypes%>" var="atype" begin="0" step="1">
                        <option value="${atype.getAccountTypeID()}">${atype.getAccountTypeName()}</option>
                </c:forEach>       
            </select><br><br>
             <button type="submit"  value="Create" class="mui-btn mui-btn--raised">Create account</button>
             
        </form>
        
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
        
    </body>
</html>
