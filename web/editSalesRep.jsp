<%-- 
    Document   : editSalesRep
    Created on : 11 1, 16, 3:15:17 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    salesRepBean salesRep = (salesRepBean)request.getAttribute("srbean");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Sales Rep</title>
    </head>
    <body>
        <h1>This is the Edit Sales Rep Page!</h1>
        
        <form action="salesrep.edit" method="post">
                Sales Rep ID: <input type="hidden" value="<%=salesRep.getSalesRepID()%>" name="srID"><%=salesRep.getSalesRepID()%><br>
                Last Name: <input type="text" value="<%=salesRep.getSalesRepLastName()%>" name="newSalesRepLastNameInput"><br>
                First Name: <input type="text" value="<%=salesRep.getSalesRepFirstName()%>" name="newSalesRepFirstNameInput"><br>
                Mobile Number: <input type="text" value="<%=salesRep.getSalesRepMobileNumber()%>" name="newSalesRepMNInput"><br>
                Address: <input type="text" value="<%=salesRep.getSalesRepAddress()%>" name="newSalesRepAddressInput"><br>
                
                <br><input type="submit" value="Save Changes">
        </form>
            
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
