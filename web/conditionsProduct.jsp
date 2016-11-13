<%-- 
    Document   : conditionsProduct
    Created on : 11 13, 16, 12:43:47 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Product</title>
    </head>
    <body>
        <h1>This is the Conditions Product page!</h1>
        
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="product">
            Product Class:<br>
                <input type="checkbox" name="productClassInput" value="Dental Unit">Dental Units<br>
                <input type="checkbox" name="productClassInput" value="Impression Material">Impression Materials<br>
                <input type="checkbox" name="productClassInput" value="Raw Material">Raw Materials<br>
                <br>
            Low Stock:<br>
                <input type="radio" name="lowStockInput" value="yes">Yes<br>
                <input type="radio" name="lowStockInput" value="no">No<br>
                <input type="radio" name="lowStockInput" value="both">Both<br>
                <br>
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
