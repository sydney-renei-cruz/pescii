<%-- 
    Document   : createRestockOrder
    Created on : 08 20, 16, 5:27:31 PM
    Author     : user
--%>
<%@page import="Beans.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    productBean pbean = (productBean)request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Create RO</title>
    </head>
    <body>
        <h1>This is the Create Restock Order page!</h1>
        <form action="Servlets.createRestockOrderServlet" method="post">
            Enter RO Name: <input type="text" name="RONameInput"><br>
            Product ID: <%=pbean.getProductID()%><input type="hidden" value="<%=pbean.getProductID()%>" name="pid"><br>
            Product Name: <%=pbean.getProductName()%><br>
            Enter Quantity Ordered:<input type="text" name="piecesOrderedInput"><br>
            Enter Purpose:<input type="text" name="purposeInput"><br>
            Enter Date Due:<input type="text" name="dateDueInput"><br>
            Enter Supplier:<input type="text" name="supplierInput"><br>
            <input type="Submit" value="Create Restock Order">
        </form>
        
        
            <a href="homePage.jsp">Return to Home</a>
            <br><br>
            <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
