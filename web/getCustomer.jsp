<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<customerBean> customersList = (ArrayList<customerBean>)request.getAttribute("customersList");
    String addInvoice = ""+request.getAttribute("addInvoice");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Get Customer</title>
    </head>
    <body>
        <h1>This is the Get Customer page!</h1>
        <c:if test="${addInvoice eq 'yes'}">
            <h3>Select the customer making the invoice.</h3>
        </c:if>
        <table border="1">
            <tr>
                <th>PRC ID</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Mobile #</th>
                <th>Telephone #</th>
            </tr>
        
        <c:forEach items="${customersList}" var="cust" begin="0" step="1" varStatus="status">
            <tr>
                    <td>${cust.getPRCID()}</td>
                    <td><a href="Servlets.viewCustomerDetailsServlet?custID=<c:out value="${cust.getCustomerID()}"/>">${cust.getCustomerLastName()}</a></td>
                    <td>${cust.getCustomerFirstName()}</td>
                    <td>${cust.getCustomerMobileNumber()}</td>
                    <td>${cust.getCustomerTelephoneNumber()}</td>
                    <td><a href="Servlets.viewCustomerDetailsServlet?editWhat=cust&forEdit=yes&custID=<c:out value="${cust.getCustomerID()}"/>">Edit</a></td>
            </tr>
        </c:forEach>
        </table>
        
        
        <br><br>
        <c:if test="${addInvoice eq 'yes'}">
            <a href="viewCart.jsp">View Cart</a>
            <br><br>
            <a href="invoice.add?cancel=yes">Cancel Invoice</a>
            <br><br>
        </c:if>
        <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a>
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
