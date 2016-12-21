<%-- 
    Document   : createAccount
    Created on : 08 20, 16, 5:24:42 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
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
        <h1>This is the Create Account page!</h1>
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
        <%=accountTypes.get(0).getAccountTypeName()%>
        <form action="Servlets.createAccountServlet" method="post">
            Enter username:<input type="text" name="usernameInput"><br>
            Enter password:<input type="password" name="passwordInput"><br>
            Enter password again:<input type="password" name="password2Input"><br>
            Select Account Type:<select name="accTypeInput">
                <c:forEach items="<%=accountTypes%>" var="atype" begin="0" step="1">
                        <option value="${atype.getAccountTypeID()}">${atype.getAccountTypeName()}</option>
                </c:forEach>       
            </select><br><br>
            <input type="submit" value="Create">
        </form>
        
        
            <a href="homePage.jsp">Return to Home</a>
            <br><br>
            <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
