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
        <h1>This is the Accounts list!</h1>
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="accountsList" value="${requestScope.accountsList}"/>
        <c:set var="listSize" value="${accountsList.size()}"/>
        
        <c:if test="${listSize > 0}">
            <table border="1">
                <tr>
                    <th>Account ID</th>
                    <th>User Name</th>
                    <th>Account Type</th>
                    <th>Account Status</th>
                    <th>Date Created</th>
                </tr>

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
            </table>
        </c:if>
        
        <c:if test="${listSize eq 0}">
            <p> 0 accounts found.</p>
        </c:if>
        
        <br><br>
        
        <a href="account.getTypeStatus?forSearch=yes">Search Account</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    
    </body>
</html>
