<%-- 
    Document   : editProduct
    Created on : 08 20, 16, 5:25:45 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    productBean product = (productBean)request.getAttribute("pbean");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Product</title>
        <link rel="stylesheet" href="css/reset.css">
        <link rel="stylesheet" href="css/normalize.css">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <h1>EDIT PRODUCT PAGE</h1>
        
<div class="form">
    <form action="product.edit" method="post">
            <input type="hidden" value="${product.getProductID()}" name="productIDInput">
            Product Name<input type="hidden" value="${product.getProductName()}" name="productNameInput"><br>
            Product Description <input type="text" value="${product.getProductDescription()}" name="productDescriptionInput"><br>
            Product Price: <input type="text" value="${product.getProductPrice()}" name="productPriceInput"><br>
            Restock Price: <input type="text" value="${product.getRestockPrice()}" name="restockPriceInput"><br>
            Low Stock Level <input type="text" value="${product.getLowStock()}" name="lowStockInput"><br>
            Brand: <input type="text" value="${product.getBrand()}" name="brandInput"><br>
            <b>Product Class</b>
            From: ${product.getProductClass()}
            To: <select name="productClassInput">
                <option value="Dental Unit">Dental Unit</option>
                <option value="Impression Material">Impression Material</option>
                <option value="Raw Material">Raw Material</option>
            </select><br><br>
            
            Color: <input type="text" value="${product.getColor()}" name="colorInput"><br>            
            
       
        
            <br><input type="submit" value="Save Changes">
        </form>

        <br>
        <button class="button button-block" type="submit" href="homePage.jsp">Return to Home</button>
        <br>
        <button class="button button-block" type="submit" href="Servlets.logoutServlet">logout</button>
</div>
    </body>
</html>

