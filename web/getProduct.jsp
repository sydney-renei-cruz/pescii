<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Product</title>
    </head>
    <body>
        <h1>This is the Get Product page!</h1>
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="productsList" value="${requestScope.productsList}"/>
        <c:set var="listSize" value="${productsList.size()}"/>
        <c:set var="forInvoice" value="${requestScope.forInvoice}"/>
        <c:set var="forRestock" value="${requestScope.forRestock}"/>
        <c:set var="forLowstock" value="${requestScope.forLowstock}"/>
        <c:set var="cartType" value="${sessionScope.cartType}"/>
        <c:if test="${cartType eq 'invoice'}">
            <p>Select Products for Invoice</p>
        </c:if>
        <c:if test="${cartType eq 'restock'}">
            <p>Select Products for Invoice</p>
        </c:if>
        <c:if test="${cartType eq 'lowstockLevel'}">
            <p>Select Products for Invoice</p>
        </c:if>
            
        <c:if test="${listSize > 0}">
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
                        <td><fmt:formatNumber pattern="0.00" value="${prod.getProductPrice()}" type="number"/></td>
                        <td><fmt:formatNumber pattern="0.00" value="${prod.getRestockPrice()}" type="number"/></td>
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
                        <c:if test="${cartType eq 'lowstockLevel'}">
                            <td><a href="addToProdCart?prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>&prodPrice=<c:out value="${prod.getRestockPrice()}"/>&suppID=<c:out value="${prod.getSupplierID()}"/>&suppName=<c:out value="${prod.getSupplierName()}"/>&brand=<c:out value="${prod.getBrand()}"/>&prodClass=<c:out value="${prod.getProductClassName()}"/>&color=<c:out value="${prod.getColor()}"/>&lowStock=<c:out value="${prod.getLowStock()}"/>">Select</a></td>
                        </c:if>
                        <c:if test="${accountType eq '4' || accountType eq '1'}">
                          <td><a href="product.getDetails?forEdit=yes&prodID=<c:out value="${prod.getProductID()}"/>">EDIT</a></td>
                        </c:if>

                </tr>

            </c:forEach>
            </table>
        </c:if>
            
        <c:if test="${listSize eq 0}">
            <p> 0 products found.</p>
        </c:if>
        <br><br>
        
        <c:choose>
            <c:when test="${cartType eq 'invoice'}">
                <a href="viewCart.jsp">View Cart</a> to add your invoice<br><br>
                <a href="invoice.add?cancel=yes">Cancel Invoice</a><br><br>
                <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=invoice">Search Product</a>
            </c:when>
                
            <c:when test="${cartType eq 'restock'}">
                <a href="viewROCart.jsp">View Cart</a> to add your Restock Order<br><br>
                <a href="supplier.get?viewSupp=yes&forRestock=yes">Change Supplier</a><br>
                <a href="Servlets.createRestockOrderServlet?cancel=yes">Cancel Restock Order</a><br><br>
                <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=restock">Search Product</a>
            </c:when>
                
            <c:when test="${cartType eq 'lowstockLevel'}">
                <a href="viewProdCart.jsp">View selected Products</a><br><br>
                <a href="product.setLowstockLevel?cancel=yes">Cancel Low Stock Setting</a><br><br>
                <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=lowstockLevel">Search Product</a>
            </c:when>        
            
            <c:when test="${forInvoice ne 'yes' && forRestock ne 'yes' && forLowstock ne 'yes'}">
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
