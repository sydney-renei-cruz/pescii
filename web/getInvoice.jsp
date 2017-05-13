<%-- 
    Document   : getInvoice
    Created on : 08 20, 16, 5:19:06 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Invoice</title>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <h1>View Invoice</h1>
       
            
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="invoiceList" value="${requestScope.invoiceList}"/>
        <c:set var="listSize" value="${invoiceList.size()}"/>
       
        
        
        <div class="mui-textfield mui-textfield--float-label">
                   
                    </div>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            
            
         <legend class="mui--text-center mui--text-display3">Invoices</legend>
         <div class="text-center">
            <div id="error-msg">${error_msg}</div>
        <c:if test="${listSize eq 0}">
            <p> 0 invoices found.</p>
        </c:if>
        </div>
        
        <c:if test="${listSize > 0}">
            <table class="mui-table mui--text-center" id="customer-table">
             <thead>    
                 <tr>
                    <th>Invoice ID</th>
                    <th>Invoice Name</th>
                    <th>Customer Name</th>
                    <th>Clinic Name</th>
                    <th>Province</th>
                    <th>Status</th>
                    <th>Invoice Date</th>
                    <th>Payment Due Date</th>
                    <th>Date Paid</th>
                    <th>Delivery Date</th>
                </tr>
</thead>
             <tbody>
            <c:forEach items="${invoiceList}" var="inv" begin="0" step="1" varStatus="status">
                <tr>
                    <td>${inv.getInvoiceID()}</td>
                    <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=no&invID=<c:out value="${inv.getInvoiceID()}"/>">${inv.getInvoiceName()}</td>
                    <td>${inv.getCustomerName()}</td>
                    <td>${inv.getClinicName()}</td>
                    <td>${inv.getProvinceName()}</td>
                    <td>${inv.getStatusName()}</td>
                    <td>${inv.getInvoiceDate()}</td>
                    <td>${inv.getPaymentDueDate()}</td>
                    <td>${inv.getDatePaid()}</td>
                    <td>${inv.getDeliveryDate()}</td>
                    <c:if test="${accountType eq '2' || accountType eq '1' || accountType eq '3' || accountType eq '6'}">
                        <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${inv.getInvoiceID()}"/>">Edit Invoice</a></td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
            </table>
        </c:if>
        </div>
        
        </div>    
            
            
<footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center"> 
        <br><br>
        <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">Logout</a>
         </div>
            </div>
        </footer> 
    </body>
</html>
