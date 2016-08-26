<%-- 
    Document   : createRestockOrder
    Created on : 08 20, 16, 5:27:31 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Create RO</title>
    </head>
    <body>
        <h1>This is the Create Restock Order page!</h1>
        <form action="Servlets.createRestockOrderServlet" method="post">
            Enter Restock Date Created:<input type="text" name="restockDateCreatedInput"><br>
            Enter Restock Arrive Date:<input type="text" name="restockArriveDateInput"><br>
            Enter Restock Completed Date:<input type="text" name="restockCompletedDateInput"><br>
            Enter Restock Cost:<input type="text" name="restockCost"><br>
            Enter Supplier:<input type="text" name="supplier"><br>
            <input type="Submit" value="Create Restock Order">
        </form>
        
        
            <a href="homePage.jsp">Return to Home</a>
            <br><br>
            <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
