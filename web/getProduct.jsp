<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<productBean> productsList = (ArrayList<productBean>)request.getAttribute("productsList");
    String forInvoice = "" + request.getAttribute("forInvoice");
    String forRestock = "" + request.getAttribute("forRestock");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Product</title>
    </head>
    <body>
        <h1>This is the Get Product page!</h1>
        <table border="1">
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Description</th>
                <th>Supplier</th>
                <th>Product Price</th>
                <th>Restock Price</th>
                <th>Stocks Remaining</th>
                <th>Low Stock</th>
                <th>Brand</th>
                <th>Product</th>
                <th>Color</th>
            </tr>
        
        <c:forEach items="${productsList}" var="prod" begin="0" step="1" varStatus="status">
            <tr>
                    <td>${prod.getProductID()}</td>
                    <c:choose>
                        <c:when test="${forInvoice ne 'yes'}">
                            <td><a href="product.getDetails?prodID=<c:out value="${prod.getProductID()}"/>">${prod.getProductName()}</a></td>
                        </c:when>
                        <c:when test="${forInvoice eq 'yes'}">
                            <td><a href="product.getDetails?forInvoice=yes&prodID=<c:out value="${prod.getProductID()}"/>">${prod.getProductName()}</a></td>
                        </c:when>
                    </c:choose>
                    
                    
                    <td>${prod.getProductDescription()}</td>
                    <td>${prod.getSupplierName()}</td>
                    <td>${prod.getProductPrice()}</td>
                    <td>${prod.getRestockPrice()}</td>
                    <td>${prod.getStocksRemaining()}</td>
                    <td>${prod.getLowStock()}</td>
                    <td>${prod.getBrand()}</td>
                    <td>${prod.getProductClassName()}</td>
                    <td>${prod.getColor()}</td>
                    
                    <c:if test="${forInvoice eq 'yes'}">
                        <td><a href="addToCart?prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>&prodPrice=<c:out value="${prod.getProductPrice()}"/>">ADD to Cart</a></td>
                    </c:if>
                    <c:if test="${forRestock eq 'yes'}">
                      <td><a href="product.getDetails?forRestock=yes&prodID=<c:out value="${prod.getProductID()}"/>">ADD to RO</a></td>
                    </c:if>    
                    
                      <td><a href="product.getDetails?forEdit=yes&prodID=<c:out value="${prod.getProductID()}"/>">EDIT</a></td>
                    
            </tr>
            
        </c:forEach>
        </table>
        
        <br><br>
        
        <c:choose>
            <c:when test="${forInvoice eq 'yes'}">
                <a href="viewCart.jsp">View Cart</a> to add your invoice.<br><br>
                <a href="invoice.add?cancel=yes">Cancel Invoice</a><br><br>
                <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=invoice">Custom View Product</a>
            </c:when>
                
            <c:when test="${forRestock eq 'yes'}">
                <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=restock">Custom View Product</a>
            </c:when>    
            
            <c:when test="${forInvoice ne 'yes' && forRestock ne 'yes'}">
                <a href="product.getProductClass?search=yes&searchWhat=prod">Custom View Product</a>
            </c:when>
        </c:choose>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
