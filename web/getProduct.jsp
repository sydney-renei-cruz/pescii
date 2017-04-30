<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<productBean> productsList = (ArrayList<productBean>)request.getAttribute("productsList");
    String forInvoice = "" + request.getAttribute("forInvoice");
    String forRestock = "" + request.getAttribute("forRestock");
    String cartType = "" + session.getAttribute("cartType");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Product</title>
    </head>
    <body>
        <h1>This is the Get Product page!</h1>
        ${cartType}
        <table border="1">
            <tr>
                <th>Product Name</th>
                <th>Supplier</th>
                <th>Product Price</th>
                <th>Restock Price</th>
                <th>Stocks Remaining</th>
                <th>Low Stock</th>
                <th>Brand</th>
                <th>Product Class</th>
                <th>Color</th>
            </tr>
        
        <c:forEach items="${productsList}" var="prod" begin="0" step="1" varStatus="status">
            <tr>
                    <td><a href="product.getDetails?prodID=<c:out value="${prod.getProductID()}"/>">${prod.getProductName()}</a></td>
                    <td>${prod.getSupplierName()}</td>
                    <td>${prod.getProductPrice()}</td>
                    <td>${prod.getRestockPrice()}</td>
                    <td>${prod.getStocksRemaining()}</td>
                    <td>${prod.getLowStock()}</td>
                    <td>${prod.getBrand()}</td>
                    <td>${prod.getProductClassName()}</td>
                    <td>${prod.getColor()}</td>
                    
                    <c:if test="${cartType eq 'invoice'}">
                        <td><a href="addToCart?prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>&prodPrice=<c:out value="${prod.getProductPrice()}"/>">ADD to Invoice List</a></td>
                    </c:if>
                    <c:if test="${cartType eq 'restock'}">
                        <td><a href="addToROCart?prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>&prodPrice=<c:out value="${prod.getRestockPrice()}"/>&suppID=<c:out value="${prod.getSupplierID()}"/>&suppName=<c:out value="${prod.getSupplierName()}"/>">ADD to RO</a></td>
                    </c:if>    
                    <c:if test="${accountType eq '4' || accountType eq '1'}">
                      <td><a href="product.getDetails?forEdit=yes&prodID=<c:out value="${prod.getProductID()}"/>">EDIT</a></td>
                    </c:if>
                    
            </tr>
            
        </c:forEach>
        </table>
        
        <br><br>
        
        <c:choose>
            <c:when test="${cartType eq 'invoice'}">
                <a href="viewCart.jsp">View Cart</a> to add your invoice.<br><br>
                <a href="invoice.add?cancel=yes">Cancel Invoice</a><br><br>
                <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=invoice">Search Product</a>
            </c:when>
                
            <c:when test="${cartType eq 'restock'}">
                <a href="viewROCart.jsp">View Cart</a> to add your Restock Order.<br><br>
                <a href="Servlets.createRestockOrderServlet?cancel=yes">Cancel Restock Order</a><br><br>
                <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=restock">Search Product</a>
            </c:when>    
            
            <c:when test="${forInvoice ne 'yes' && forRestock ne 'yes'}">
                <a href="product.getProductClass?search=yes&searchWhat=prod">Search Product</a>
            </c:when>
        </c:choose>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <!--THIS PART OF THE CODE CHECKS IF IT SHOULD DISPLAY THE LOG IN OR LOGOUT LINK-->
        <c:if test="${state == 'logged in'}">
            <a href="Servlets.logoutServlet">log out</a><br><br>
        </c:if>
            
        <c:if test="${state ne 'logged in'}">
            <a href="logIn.jsp">log in</a><br><br>
        </c:if>
        
    </body>
</html>
