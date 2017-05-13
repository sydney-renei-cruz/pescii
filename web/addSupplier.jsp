<%-- 
    Document   : addSupplier
    Created on : 12 23, 16, 12:44:34 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PECII Add Supplier</title>
    </head>
    <body>
         <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
       
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Add Supplier</legend>
                        
        <!--this is the error message-->
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        <div class="mui-col-md-8 mui-col-md-offset-2">    
         <form action="supplier.add" method="post">
             <div class="mui-col-md-12">
                <div class="mui-textfield mui-textfield--float-label">
                <input type="text" name="supplierNameInput" id="supplierNameInput"required>
                <label for="supplierNameInput">Supplier Name</label>
                </div>
            </div>
             <br>
             <div class="mui-col-md-12">
                <div class="mui-textfield mui-textfield--float-label">
                <input type="text" name="supplierAddressInput" id="supplierAddressInput"required>
                <label for="supplierAddressInput">Supplier Address</label>
                </div>
            </div>
             <br>
             <div class="mui-col-md-12">
                <div class="mui-textfield mui-textfield--float-label">
                <input type="text" name="supplierContactNumberInput" id="supplierContactNumberInput"required>
                <label for="supplierContactNumberInput">Supplier Contact Number</label>
                </div>
            </div>
             <br><br><br>
          
            Enter Product Class: <br><select name="productClassInput">
                <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProductClassID()}">${pro.getProductClassName()}</option>
                </c:forEach>
            </select><br>
            <br><br>
            <button type="submit" class="mui-btn mui-btn--raised">Add Supplier</button>
            
        </form>
        <br><br>
        </div>
                    </div>
                </div>
        <center>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
        </center>
    </body>
</html>
