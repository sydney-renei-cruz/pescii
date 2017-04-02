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
        <title>PECII Edit Product</title>
    </head>
    <body>
        <h1>This is the Add Supplier page!</h1>
        
        <!--this is the error message-->
        <c:if test="${message ne '' || message ne null || message ne 'null'}">
            <p>${message}</p><br><br>
        </c:if>
            
         <form action="supplier.add" method="post">
            Enter Supplier Name:<input type="text" name="supplierNameInput" maxlength="100" required><br>
            Enter Supplier Address: <input type="text" name="supplierAddressInput" maxlength="255" required><br>
            Enter Supplier Contact Number:<input type="text" name="supplierContactNumberInput" maxlength="12" required><br>
            Enter Product Class: <select name="productClassInput">
                <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProductClassID()}">${pro.getProductClassName()}</option>
                </c:forEach>
            </select><br>
            
            <input type="submit" value="Add Supplier">
        </form>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
