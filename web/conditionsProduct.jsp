<%-- 
    Document   : conditionsProduct
    Created on : 11 13, 16, 12:43:47 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    ArrayList<supplierBean> suppList = (ArrayList<supplierBean>)request.getAttribute("suppliersList");
    String forOther = ""+request.getAttribute("forOther");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Product</title>
    </head>
    <body>
        <h1>This is the Conditions Product page!</h1>
        
        <h4>Set the conditions of your search</h4>
        
        <form action="new.get">
            <input type="hidden" name="whatFor" value="product">
            <c:if test="${forOther eq 'invoice'}">
                <input type="hidden" name="forOther" value="invoice">
            </c:if>
            <c:if test="${forOther eq 'restock'}">
                <input type="hidden" name="forOther" value="restock">
            </c:if>
            
            Search by Name:<input type="text" name="searchNameInput">
            <br><br>
            Search by Brand:<input type="text" name="searchBrandInput">
            <br><br>
            Product Class:<br>
                
                <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                    <input type="checkbox" name="productClassInput" value="${pro.getProductClassName()}">${pro.getProductClassName()}<br>
                </c:forEach>
                <br>
            Low Stock:<br>
                <input type="radio" name="lowStockInput" value="yes">Yes<br>
                <input type="radio" name="lowStockInput" value="no">No<br>
                <input type="radio" name="lowStockInput" value="cancel">Both<br>
                <br><br>
                
            Search by Supplier:<select name="searchSupplierInput">
                <option value="All">All</option>
                <c:forEach items="<%=suppList%>" var="sup" begin="0" step="1">
                        <option value="${sup.getSupplierName()}">${sup.getSupplierName()}</option>
                </c:forEach>
            </select><br><br>
            <br><br>    
                
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
