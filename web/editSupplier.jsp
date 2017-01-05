<%-- 
    Document   : editSupplier
    Created on : 01 4, 17, 3:47:31 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    supplierBean supb = (supplierBean)request.getAttribute("supplier");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Supplier</title>
    </head>
    <body>
        <h1>This is the Edit Supplier page!</h1>
         <form action="supplier.edit" method="post">
             <%=supb.getSupplierID()%>
            Supplier ID: <%=supb.getSupplierID()%><input type="hidden" value="<%=supb.getSupplierID()%>" name="supplierIDInput"><br>
            Enter Supplier Name:<input type="text" name="supplierNameInput" value="<%=supb.getSupplierName()%>"><br>
            Enter Supplier Address: <input type="text" name="supplierAddressInput" value="<%=supb.getSupplierAddress()%>"><br>
            Enter Supplier Contact Number:<input type="text" name="supplierContactNumberInput" value="<%=supb.getSupplierContactNumber()%>"><br>
            <b>Product Class</b><br>
            From: <%=supb.getProductClassName()%><br>
            To: <select name="productClassInput">
                <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProductClassID()}">${pro.getProductClassName()}</option>
                </c:forEach>
            </select><br><br>
            
            <input type="submit" value="Save Changes">
        </form>
        <br><br>
        
        <a href="supplier.get?viewSupp=yes">Return to Suppliers List</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
