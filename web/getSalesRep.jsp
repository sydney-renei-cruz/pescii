<%-- 
    Document   : getSalesRep
    Created on : 11 1, 16, 11:55:00 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<salesRepBean> salesRepsList = (ArrayList<salesRepBean>)request.getAttribute("salesRepsList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Sales Rep</title>
    </head>
    <body>
        <h1>This is the Get Sales Rep Page!</h1>
        
        <table border="1">
            <tr>
                <th>Sales Rep ID</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Mobile Number</th>
                <th>Address</th>
            </tr>
        
        <c:forEach items="${salesRepsList}" var="sr" begin="0" step="1" varStatus="status">
            <tr>
                    <td>${sr.getSalesRepID()}</td>
                    <td>${sr.getSalesRepLastName()}</td>
                    <td>${sr.getSalesRepFirstName()}</td>
                    <td>${sr.getSalesRepMobileNumber()}</td>
                    <td>${sr.getSalesRepAddress()}</td>
                    <td><a href="salesrep.getDetails?srID=<c:out value="${sr.getSalesRepID()}"/>">Edit</td>
            </tr>
            
        </c:forEach>
        </table>
        <br><br>
        <a href="conditionsSalesRep.jsp">Custom View Sales Rep</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
