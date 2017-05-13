<%-- 
    Document   : getAccount
    Created on : 09 10, 16, 12:40:05 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Account List</title>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="accountsList" value="${requestScope.accountsList}"/>
        <c:set var="listSize" value="${accountsList.size()}"/>
        
        <div class="mui-textfield mui-textfield--float-label">
                   
                    </div>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            
            
         <legend class="mui--text-center mui--text-display3">Accounts</legend>
        <c:if test="${listSize > 0}">
             <table class="mui-table mui--text-center" id="customer-table">
             <thead>
                <tr>
                    <th>Account ID</th>
                    <th>User Name</th>
                    <th>Account Type</th>
                    <th>Account Status</th>
                    <th>Date Created</th>
                </tr>
             </thead>
             <tbody>
            <c:forEach items="${accountsList}" var="acc" begin="0" step="1" varStatus="status">
                <tr>
                        <td>${acc.getAccountID()}</td>
                        <td>${acc.getUserName()}</td>
                        <td>${acc.getAccountType()}</td>
                        <td>${acc.getAccountStatus()}</td>
                        <td>${acc.getDateCreated()}</td>
                        <td><a href="account.getDetails?accID=<c:out value="${acc.getAccountID()}"/>">Edit</a></td>
                </tr>

            </c:forEach>
             </tbody>
            </table>
        </c:if>
</div>
        <div class="text-center">
            <div id="error-msg">${error_msg}</div>
            <c:if test="${listSize eq 0}">
            <p> 0 accounts found.</p>
        </c:if>
         </div>
        </div>    
            
       
       <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">  
        <a href="account.getTypeStatus?forSearch=yes">Search Account</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
      
                  </div>
            </div>
        </footer> 
    </body>
</html>
