<%-- 
    Document   : createRestockOrder
    Created on : 08 20, 16, 5:27:31 PM
    Author     : user
--%>
<%@page import="Beans.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    productBean pbean = (productBean)request.getAttribute("product");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Create RO</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
            function init() {
                calendar.set("date1");
            }
        </script>
    </head>
    <body onload="init()">
        <h1>This is the Create Restock Order page!</h1>
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
        
        <form action="Servlets.createRestockOrderServlet" method="post">
            Enter RO Name: <input type="text" name="RONameInput" maxlength="255" required><br>
            Product ID: <%=pbean.getProductID()%><input type="hidden" value="<%=pbean.getProductID()%>" name="pid"><br>
            Product Name: <%=pbean.getProductName()%><br>
            Supplier: <%=pbean.getSupplierName()%><input type="hidden" value="<%=pbean.getSupplierID()%>" name="supplierIDInput"><br>
            Enter Quantity Ordered:<input type="text" name="piecesOrderedInput" required><br>
            Enter Purpose:<br><textarea name="purposeInput" rows="5" cols="50"></textarea><br>
            Enter Date Due:<input type="text" name="dateDueInput" id="date1" maxlength="10" required><br>
            Enter discount:<input type="text" name="discountInput" value="0"><br>
            <input type="Submit" value="Create Restock Order">
        </form>
        
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
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
