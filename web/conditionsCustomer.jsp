<%-- 
    Document   : conditionsCustomer
    Created on : 12 27, 16, 7:23:19 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<salesRepBean> salesRepList = (ArrayList<salesRepBean>)request.getAttribute("salesRepsList");
    ArrayList<provinceBean> provinceList = (ArrayList<provinceBean>)request.getAttribute("provList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Customer</title>
    </head>
    <body>
        <h1>This is the Conditions Customer page!</h1>
        
        <h4>Set the conditions of your search</h4>
        <form action="new.get">
            <input type="hidden" name="getWhat" value="customSearch">
            <input type="hidden" name="whatFor" value="customer">
            <b>Search by Customer Name:</b><br>
            Last Name: <input type="text" name="searchCustomerLastNameInput"><br>
            First Name: <input type="text" name="searchCustomerFirstNameInput"><br>
            <br>
            Search by PRCID: <input type="text" name="searchPRCIDInput"><br>
            
            Search by Sales Representative:<select name="searchSalesRepInput">
                <option value="All">All</option>
                <c:forEach items="<%=salesRepList%>" var="sr" begin="0" step="1">
                    <option value="${sr.getSalesRepID()}">${sr.getSalesRepLastName()}, ${sr.getSalesRepFirstName()}</option>
                </c:forEach>
            </select>
            <br><br>
            Search by Province:<select name="searchProvinceInput">
                    <option value="all">All</option>
                    <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                    </c:forEach>    
            </select><br><br>
                
            <input type="submit" value="Get">    
        </form>
        
        
        
        
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>