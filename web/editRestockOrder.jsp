<%-- 
    Document   : editRestockOrder
    Created on : 08 20, 16, 1:16:09 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    restockOrderBean restockOrder = (restockOrderBean)request.getAttribute("restockOrder");
    productBean product = (productBean)request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit RO</title>
    </head>
    <body>
        <h1>This is the Edit Restock Order page!</h1>
          <form action="restock.edit" method="post">
                Restock Order ID: <input type="hidden" value="${restockOrder.getRestockOrderID()}" name="restockOrderIDInput">${restockOrder.getRestockOrderID()}<br>
                Restock Order Name: <input type="text" value="${restockOrder.getRestockOrderName()}" name="newRONameInput"><br>
                Product Ordered: <input type="hidden" value="${restockOrder.getProductID()}" name="productIDInput">${restockOrder.getProductName()}<br>
                Number of Pieces Ordered: <input type="text" value="${restockOrder.getNumberOfPiecesOrdered()}" name="numberOfPiecesOrderedInput"><br>
                Number of Pieces Received: <input type="text" value="${restockOrder.getNumberOfPiecesReceived()}" name="numberOfPiecesReceivedInput"><br>
                Supplier: <input type="text" value="${restockOrder.getSupplier()}" name="supplierInput"><br>
                Purpose: <input type="text" value="${restockOrder.getPurpose()}" name="purposeInput"><br>
                Restock Order Date Delivered: <input type="text" value="${restockOrder.getRODateDelivered()}" name="roDateDeliveredInput"><br>
                
                <p>Product Name: ${product.getProductName()}</p>
                <p>Restock Price: ${product.getRestockPrice()}</p>
                
                <br><input type="submit" value="Save Changes">
                
        </form>
            
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
