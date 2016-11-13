<%-- 
    Document   : conditionsInvoice
    Created on : 11 13, 16, 2:24:50 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Invoice</title>
    </head>
    <body>
        <h1>This is the Conditions Invoice page!</h1>
        
        
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="invoice">
            
            Conditions:<br>
                <input type="radio" name="getWhat" value="new">New Invoice<br>
                <input type="radio" name="getWhat" value="validated">Validated Invoices<br>
                <input type="radio" name="getWhat" value="close">Invoices near payment deadlines<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

        
        
    </body>
</html>
