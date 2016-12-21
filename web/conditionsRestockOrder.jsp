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
        
        <p><b>Search</b></p>
        <form action="new.get">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="restockOrder">
            Search by Name:<input type="text" name="searchNameInput">
            <br><br>
            Search by Supplier:<input type="text" name="searchSupplierInput">
            <br><br>
            Search by Product Name:<input type="text" name="searchProductNameInput">
            <br><br>
            Search by Date:<select name="searchDateInput">
                    <option value="RODateDue">Expected Arrival Date</option>
                    <option value="RODateDelivered">Date Received</option>
                    <option value="RestockOrder.dateCreated">Date Created</option>
            </select><br><br>
            From:<input type="text" name="fromDate"><br>
            To:<input type="text" name="toDate"><br><br>
            
            <input type="submit" value="Search"><br><br><br>
        </form>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="restockOrder">
            
            <b>Shortcuts</b><br>
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
