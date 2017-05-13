<%-- 
    Document   : getSupplier
    Created on : 01 4, 17, 3:29:21 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Supplier</title>
    </head>
    <body>
       
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="cartType" value="${sessionScope.cartType}"/>
        <c:set var="suppliersList" value="${requestScope.suppliersList}"/>
        <c:set var="listSize" value="${suppliersList.size()}"/>
        
            
        <div class="mui-textfield mui-textfield--float-label">
                   
                    </div>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            
            
        <legend class="mui--text-center mui--text-display3">Suppliers</legend> 
         
        <c:if test="${listSize > 0}">
            <table class="mui-table mui--text-center" >
            <thead>    
                <tr>
                    <th>Supplier ID</th>
                    <th>Supplier Name</th>
                    <th>Address</th>
                    <th>Contact Number</th>
                    <th>Product Class</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${suppliersList}" var="sr" begin="0" step="1" varStatus="status">
                <tr>
                        <td>${sr.getSupplierID()}</td>
                        <td><a href="supplier.getDetails?suppID=<c:out value="${sr.getSupplierID()}"/>">${sr.getSupplierName()}</a></td>
                        <td>${sr.getSupplierAddress()}</td>
                        <td>${sr.getSupplierContactNumber()}</td>
                        <td>${sr.getProductClassName()}</td>
                        <td><a href="Servlets.getProductServlet?forOther=restock&suppID=<c:out value="${sr.getSupplierID()}"/>">Select for RO</a></td>
                        <td><a href="supplier.getDetails?forEdit=yes&suppID=<c:out value="${sr.getSupplierID()}"/>">Edit</a></td>
                </tr>

            </c:forEach>
            </tbody>
            </table>
        </c:if>
        </div>
        
        <div class="text-center">
            <div id="error-msg">${error_msg}</div>
            <c:if test="${listSize eq 0}">
                <p> 0 suppliers found.</p>
            </c:if>
        </div>
    </div>
            
       
        <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">
        <a href="product.getProductClass?search=yes&searchWhat=supp">Search Supplier</a>
        <br>
        <c:if test="${cartType eq 'restock'}">
            <a href="Servlets.createRestockOrderServlet?cancel=yes">Cancel Restock Order</a>
        </c:if>
        <br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
    </div>  
                  </div>
            </div>
        </footer>      
        
    </body>
</html>
