<%-- 
    Document   : editProduct
    Created on : 08 20, 16, 5:25:45 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    productBean product = (productBean)request.getAttribute("pbean");
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    ArrayList<supplierBean> suppList = (ArrayList<supplierBean>)request.getAttribute("suppliersList");
    String message = ""+request.getAttribute("message");
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
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
            
<div class="form">
    <form action="product.edit" method="post">
            <input type="hidden" value="${product.getProductID()}" name="productIDInput">
            Product Name<input type="hidden" value="${product.getProductName()}" name="productNameInput"><br>
            Product Description <textarea name="productDescriptionInput" rows="5" cols="50">${product.getProductDescription()}</textarea><br>
            Product Price: <input type="text" value="${product.getProductPrice()}" name="productPriceInput"><br>
            Restock Price: <input type="text" value="${product.getRestockPrice()}" name="restockPriceInput"><br>
            Low Stock Level <input type="text" value="${product.getLowStock()}" name="lowStockInput"><br>
            Brand: <input type="text" value="${product.getBrand()}" name="brandInput" maxlength="50"><br>
            Color: <input type="text" value="${product.getColor()}" name="colorInput" maxlength="20"><br><br>
            <b>Product Class</b><br>
            From: ${product.getProductClassName()}<br>
            To: <select name="productClassInput">
                <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProductClassID()}">${pro.getProductClassName()}</option>
                </c:forEach>
            </select><br><br>
            <b>Supplier</b><br>
            From: ${product.getSupplierName()}<br>
            To: <select name="supplierInput">
                <c:forEach items="<%=suppList%>" var="sup" begin="0" step="1">
                        <option value="${sup.getSupplierID()}">${sup.getSupplierName()}</option>
                </c:forEach>
            </select><br><br>
                        
            
       
        
            <br><input type="submit" value="Save Changes">
        </form>

        <br><br>
        <a href="product.getDetails?prodID=<c:out value="${product.getProductID()}"/>">Return to Product details</a>
        <br><br>
        <a href="Servlets.getProductServlet">Go to Products list</a>
        <br><br>
        <a href="product.getProductClass?search=yes&searchWhat=prod">Custom View Product</a>
        <br><br>
        <c:choose>
            <c:when test="${accountType eq 3}">
                <a href="notif.get?forWhat=invoice">Return to Home</a>
            </c:when>
            <c:when test="${(accountType eq 4) || (accountType eq 5)} ">
                <a href="notif.get?forWhat=restock">Return to Home</a>
            </c:when>
            <c:when test="${accountType eq 1}">
                <a href="notif.get?forWhat=both">Return to Home</a>
            </c:when>
            <c:when test="${(accountType ne 3) || (accountType ne 4) || (accountType ne 5) || (accountType ne 1)}">
                <a href="homePage.jsp">Return to Home</a>
            </c:when>
        </c:choose>
        <br>
        <a href="Servlets.logoutServlet">logout</a>
</div>
    </body>
</html>

