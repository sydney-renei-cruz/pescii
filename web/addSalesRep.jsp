<%-- 
    Document   : addSalesRep
    Created on : 11 1, 16, 10:32:03 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Add Sales Rep</title>
    </head>
    <body>
        <h1>This is the Add Sales Rep Page!</h1>
        <form action="salesrep.add" method="post">
            Enter Sales Rep name:<input type="text" name="salesrepNameInput"><br>
            Enter Sales Rep mobile number:<input type="text" name="salesrepMNInput"><br>
            Enter Sales Rep Address:<input type="text" name="salesrepAddressInput"><br>
            <input type="submit" value="Save Sales Rep"><br>
        </form>
        
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
