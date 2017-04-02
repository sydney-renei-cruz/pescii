<%-- 
    Document   : editSalesRep
    Created on : 11 1, 16, 3:15:17 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    salesRepBean salesRep = (salesRepBean)request.getAttribute("srbean");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Sales Rep</title>
    </head>
    <body>
        <h1>This is the Edit Sales Rep Page!</h1>
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
        
        
        <form action="salesrep.edit" method="post">
                Sales Rep ID: <input type="hidden" value="<%=salesRep.getSalesRepID()%>" name="srID"><%=salesRep.getSalesRepID()%><br>
                Last Name: <input type="text" value="<%=salesRep.getSalesRepLastName()%>" name="newSalesRepLastNameInput" maxlength="100"><br>
                First Name: <input type="text" value="<%=salesRep.getSalesRepFirstName()%>" name="newSalesRepFirstNameInput" maxlength="100"><br>
                Mobile Number: <input type="text" value="<%=salesRep.getSalesRepMobileNumber()%>" name="newSalesRepMNInput" maxlength="12"><br>
                Address: <input type="text" value="<%=salesRep.getSalesRepAddress()%>" name="newSalesRepAddressInput" maxlength="255"><br>
                
                <br><input type="submit" value="Save Changes">
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
