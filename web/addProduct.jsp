<%-- 
    Document   : addProduct
    Created on : 08 20, 16, 1:15:17 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    ArrayList<supplierBean> suppList = (ArrayList<supplierBean>)request.getAttribute("suppliersList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Add Product</title>
    </head>
    <body>
        <h1>This is the Add Product page!</h1>
         <form action="Servlets.addProductServlet" method="post">
            <!-- Product ID self generated -->
            Enter Product Name:<input type="text" name="productNameInput"><br>
            Enter Product Description: <input type="text" name="productDescInput"><br>
            Enter Product Price:<input type="text" name="productPriceInput"><br>
            Enter Restock Price:<input type="text" name="restockPriceInput"><br>
            <!--you don't put Stocks Remaining because it's 0 by default.
                    - it'll be filled if a Restock Order is completed-->
            Enter Low Stock Level:<input type="text" name="lowStockInput"><br>
            Enter Brand: <input type="text" name="brandInput"><br>
            Enter Product Class: <select name="productClassInput">
                <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProductClassID()}">${pro.getProductClassName()}</option>
                </c:forEach>
            </select><br>
            Enter Color: <input type="text" name="colorInput"><br>
            Enter Supplier: <select name="supplierInput">
                <c:forEach items="<%=suppList%>" var="sup" begin="0" step="1">
                        <option value="${sup.getSupplierID()}">${sup.getSupplierName()}</option>
                </c:forEach>
            </select><br><br>
            <input type="submit" value="Add Product">
        </form>
        <br><br>
            
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
