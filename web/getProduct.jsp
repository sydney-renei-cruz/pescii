<%-- 
<<<<<<< HEAD
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
%>
=======
    Document   : getProduct
    Created on : 08 20, 16, 5:26:29 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Get Product</title>
    </head>
    <body>
<<<<<<< HEAD
        <h1>This is the Get Product page!</h1>
        <table border="1">
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Description</th>
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
                    <td>${prod.getProductName()}</td>
                    <td>${prod.getProductDescription()}</td>
                    <td>${prod.getProductPrice()}</td>
                    <td>${prod.getRestockPrice()}</td>
                    <td>${prod.getStocksRemaining()}</td>
                    <td>${prod.getLowStock()}</td>
                    <td>${prod.getBrand()}</td>
                    <td>${prod.getProductClass()}</td>
                    <td>${prod.getColor()}</td>
                    
                    <c:if test="${forInvoice eq 'yes'}">
                        <td><a href="addToCart?prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>">ADD</a></td>
                    </c:if>
                    
            </tr>
            
        </c:forEach>
        </table>
        
        <c:if test="${forInvoice eq 'yes'}">
            <br><br>
            <a href="viewCart.jsp">View Cart</a> to add your invoice.
        </c:if>
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
=======
        <h1>This is the Get Product Page!</h1>
        
            <a href="homePage.jsp">Return to Home</a>
            <br><br>
            <a href="Servlets.logoutServlet">logout</a>
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
        
    </body>
</html>
