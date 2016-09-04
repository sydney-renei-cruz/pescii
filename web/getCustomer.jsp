<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<customerBean> customersList = (ArrayList<customerBean>)request.getAttribute("customersList");
<<<<<<< HEAD
    String addInvoice = ""+request.getAttribute("addInvoice");
=======
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Get Customer</title>
    </head>
    <body>
        <h1>This is the Get Customer page!</h1>
<<<<<<< HEAD
        <c:if test="${addInvoice != ''}">
            <h3>Select the customer making the invoice.</h3>
        </c:if>
=======
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
        <table border="1">
            <tr>
                <th>PRC ID</th>
                <th>Customer Name</th>
                <th>Mobile #</th>
                <th>Telephone #</th>
            </tr>
        
        <c:forEach items="${customersList}" var="cust" begin="0" step="1" varStatus="status">
            <tr>
                    <td>${cust.getPRCID()}</td>
                    <td><a href="Servlets.viewCustomerDetailsServlet?custID=<c:out value="${cust.getPRCID()}"/>">${cust.getCustomerName()}</a></td>
                    <td>${cust.getCustomerMobileNumber()}</td>
                    <td>${cust.getCustomerTelephoneNumber()}</td>
            </tr>
        </c:forEach>
        </table>
        
        
        
        
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
