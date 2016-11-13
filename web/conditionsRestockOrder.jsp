<%-- 
    Document   : conditionsRestockOrder
    Created on : 11 13, 16, 1:30:10 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View RO</title>
    </head>
    <body>
        <h1>This is the Conditions Restock Order page!</h1>
        
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="restockOrder">
            
            Conditions:<br>
                <input type="radio" name="getWhat" value="new">New Restock Orders<br>
                <input type="radio" name="getWhat" value="completed">Recently completed Restock Orders<br>
                <input type="radio" name="getWhat" value="close">Restock Orders near deadlines<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
