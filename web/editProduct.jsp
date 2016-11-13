<%-- 
    Document   : editProduct
    Created on : 08 20, 16, 5:25:45 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            Product Name<input type="hidden" value="${product.getProductName()}" name="productName"><br>
            Product Description <input type="text" value="${product.getProductDescription()}" name="productDescription"><br>
            Product Price: <input type="text" value="${product.getProductPrice()}" name="productPrice"><br>
            Restock Price: <input type="text" value="${product.getProductRestockPrice()}" name="restockPrice"><br>
            Low Stock Level <input type="text" value="${product.getLowStockLevel()}" name="lowStockLevel"><br>
            Brand: <input type="text" value="${product.getBrand()}" name="brand"><br>
            Product Class: <input type="text" value="${product.getProductClass()}" name="productClass"><br>
            Color: <input type="text" value="${product.getColor()}" name="color"><br>            
            
           
        <div class="field-wrap">
            <label>
            Product Class<span class="req">*</span>
            </label>
            <select name="productClassInput">
                <option value="Dental Unit">Dental Unit</option>
                <option value="Impression Material">Impression Material</option>
                <option value="Raw Material">Raw Material</option>
            </select>
        </div> 
       
        
            <br><input type="submit" value="Save Changes">
        </form>

        <br>
        <button class="button button-block" type="submit" href="homePage.jsp">Return to Home</button>
        <br>
        <button class="button button-block" type="submit" href="Servlets.logoutServlet">logout</button>
</div>
    </body>
</html>

