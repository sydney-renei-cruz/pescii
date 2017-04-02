<%-- 
    Document   : editAccount
    Created on : 09 9, 16, 4:02:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    accountBean account = (accountBean)request.getAttribute("account");
    ArrayList<accountStatusBean> accountStatusList = (ArrayList<accountStatusBean>)request.getAttribute("accountStatusesList");
    ArrayList<accountTypeBean> accountTypes = (ArrayList<accountTypeBean>)request.getAttribute("atypeList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Account</title>
    </head>
    <body>
        <h1>This is the Edit Account Page!</h1>
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
        
        <form action="account.edit" method="post">
            <input type="hidden" value="${account.getAccountID()}" name="accountIDInput"><br>
            <input type="hidden" value="${account.getPassword()}" name="oldPassword">
            Password: <input type="password" name="newPasswordInput" maxlength="255"><br>
            Enter password again:<input type="password" name="newPasswordInput2" maxlength="255"><br>
            User Name: <input type="text" value="${account.getUserName()}" name="userNameInput" maxlength="30"><br><br>
            <b>Account Type</b><br>
            From:${account.getAccountType()}<br>
            To:
            <select name="accountTypeInput">
                <c:forEach items="<%=accountTypes%>" var="atype" begin="0" step="1">
                        <option value="${atype.getAccountTypeID()}">${atype.getAccountTypeName()}</option>
                </c:forEach>    
            </select><br><br>
            <b>Account Status</b><br>
            From: ${account.getAccountStatus()}<br>
            To:
            <select name="accountStatusInput">
                <c:forEach items="<%=accountStatusList%>" var="astat" begin="0" step="1">
                        <option value="${astat.getAccountStatusID()}">${astat.getAccountStatusName()}</option>
                </c:forEach>    
            </select><br>
            
            
            <br><input type="submit" value="Save Changes">
        </form>
        
        
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
