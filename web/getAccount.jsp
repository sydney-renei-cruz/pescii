<%-- 
    Document   : getAccount
    Created on : 09 10, 16, 12:40:05 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<accountBean> accountsList = (ArrayList<accountBean>)request.getAttribute("accountsList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Account List</title>
    </head>
    <body>
        <h1>This is the Accounts list!</h1>
        
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
                    <td><a href="account.getDetails?accID=<c:out value="${acc.getAccountID()}"/>">Edit</td>
            </tr>
            
        </c:forEach>
        </table>
        <br><br>
        
        <a href="account.getTypeStatus?forSearch=yes">Custom View Account</a>
        <br><br>
        <c:choose>
            <c:when test="${accountType eq 3}">
                <a href="notif.get?forWhat=invoice">Return to Home</a>
            </c:when>
            <c:when test="${(accountType eq 4) || (accountType eq 5)} ">
                <a href="notif.get?forWhat=restock">Return to Home</a>
            </c:when>
            <c:when test="${accountType eq 1}">
                <a href="notif.get?forWhat=both">Return to Home</a>
            </c:when>
            <c:when test="${(accountType ne 3) || (accountType ne 4) || (accountType ne 5) || (accountType ne 1)}">
                <a href="homePage.jsp">Return to Home</a>
            </c:when>
        </c:choose>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    
    </body>
</html>
