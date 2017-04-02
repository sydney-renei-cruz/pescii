<%-- 
    Document   : index
    Created on : 08 15, 16, 3:00:01 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String message = ""+request.getAttribute("message");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome!</title>
          <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <h1>This is the log in page!</h1>
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
            <div class="login">
            <div class="login-triangle"></div>
            <h2 class="login-header">Log In</h2>
        <form action="Servlets.logInServlet" method="post" class="login-container">
            <p>Enter username:<input type="text" name="usernameInput" maxlength="30"></p><br>
            <p>Enter password:<input type="password" name="passwordInput" maxlength="255"></p><br>
            <input type="submit" value="Log in" >
        </form>    
    </div>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    </body>
</html>
