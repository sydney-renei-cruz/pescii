<%-- 
    Document   : getInvoice
    Created on : 08 20, 16, 5:19:06 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<invoiceBean> invoiceList = (ArrayList<invoiceBean>)request.getAttribute("invoiceList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Invoice</title>
    </head>
    <body>
        <h1>This is the View Invoice page!</h1>
        <table border="1">
            <tr>
                <th>Invoice ID</th>
                <th>Invoice Name</th>
                <th>PRC ID</th>
                <th>Clinic ID</th>
                <th>Invoice Date</th>
                <th>Payment Due Date</th>
                
            </tr>
        
        <c:forEach items="${invoiceList}" var="inv" begin="0" step="1" varStatus="status">
            <tr>
                <td>${inv.getInvoiceID()}</td>
                <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=no&invID=<c:out value="${inv.getInvoiceID()}"/>">${inv.getInvoiceName()}</td>
                <td>${inv.getPRCID()}</td>
                <td>${inv.getClinicID()}</td>
                <td>${inv.getInvoiceDate()}</td>
                <td>${inv.getPaymentDueDate()}</td>
                <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${inv.getInvoiceID()}"/>">Edit Invoice</a></td>
            </tr>
        </c:forEach>
        </table>
        


        
            <a href="homePage.jsp">Return to Home</a>
            <br><br>
            <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
