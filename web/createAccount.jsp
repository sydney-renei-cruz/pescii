<%-- 
    Document   : createAccount
    Created on : 08 20, 16, 5:24:42 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Create Account</title>
    </head>
    <body>
        <h1>This is the Create Account page!</h1>
        
        <form action="Servlets.createAccountServlet" method="post">
            Enter username:<input type="text" name="usernameInput"><br>
            Enter password:<input type="text" name="passwordInput"><br>
            Enter account type:<input type="text" name="accTypeInput"><br>
            <input type="submit" value="Create">
        </form>
        
        
            <a href="homePage.jsp">Return to Home</a>
            <br><br>
            <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
