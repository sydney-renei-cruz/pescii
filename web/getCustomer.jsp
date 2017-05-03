<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Get Customer</title>
    </head>
    <body>
        <h1>This is the Get Customer page!</h1>
        
        <c:set var="customersList" value="${requestScope.customersList}"/>
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="addInvoice" value="${requestScope.addInvoice}"/>
        <c:set var="listSize" value="${customersList.size()}"/>
        <c:if test="${addInvoice eq 'yes'}">
            <h3>Select the customer making the invoice.</h3>
        </c:if>
        <c:if test="${listSize > 0}">
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
                        <td><a href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=<c:out value="${cust.getCustomerID()}"/>">${cust.getCustomerLastName()}</a></td>
                        <td>${cust.getCustomerFirstName()}</td>
                        <td>${cust.getCustomerMobileNumber()}</td>
                        <td>${cust.getCustomerTelephoneNumber()}</td>
                        <c:if test="${accountType eq '2' || accountType eq '1'}">
                            <td><a href="Servlets.viewCustomerDetailsServlet?editWhat=cust&forEdit=yes&custID=<c:out value="${cust.getCustomerID()}"/>">Edit</a></td>
                        </c:if>
                </tr>
            </c:forEach>
            </table>
        </c:if>
            
        <c:if test="${listSize eq 0}">
            <p> 0 customers found.</p>
        </c:if>
        
        <br><br>
        <c:if test="${addInvoice eq 'yes'}">
            <a href="viewCart.jsp">View Cart</a>
            <br><br>
            <a href="invoice.add?cancel=yes">Cancel Invoice</a>
            <br><br>
        </c:if>
        <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
