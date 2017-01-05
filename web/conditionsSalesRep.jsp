<%-- 
    Document   : conditionsSalesRep
    Created on : 01 5, 17, 3:20:58 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Search Sales Rep</title>
    </head>
    <body>
        <h1>This is the Conditions Sales Rep page!</h1>
        
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="salesRep">
            
            Search by First Name:<input type="text" name="searchSalesRepFirstNameInput">
            <br><br>
            Search by Last Name:<input type="text" name="searchSalesRepLastNameInput">
            <br><br>
            <b>Search by Date Created</b>
            <br><br>
            From:<input type="text" name="fromDate"><br>
            To:<input type="text" name="toDate"><br>
            <br><br>    
                
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
