<%-- 
    Document   : homePage
    Created on : 08 16, 16, 9:12:22 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String state = ""+session.getAttribute("state");
    String userName = ""+session.getAttribute("userName");
    String message = ""+request.getAttribute("message");
    String accountType = ""+session.getAttribute("accountType");
    
    ArrayList<restockOrderBean> restocksList = (ArrayList<restockOrderBean>)request.getAttribute("restocksList");
    ArrayList<invoiceBean> invoiceList = (ArrayList<invoiceBean>)request.getAttribute("invoiceList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Homepage</title>
    </head>
    <body>
        <h1>This is the homePage!</h1>
        
        <c:if test="${userName == ''}">
            <p>Welcome, ${userName}!</p><br>
            <p>Your account type is ${accountType}.</p><br>     <!-- this is to be removed later on-->
        </c:if>
        
        
        <!--THIS PART OF THE CODE CHECKS IF IT SHOULD DISPLAY THE LOG IN OR LOGOUT LINK-->
        <c:if test="${state == 'logged in'}">
            <a href="Servlets.logoutServlet">log out</a><br><br>
        </c:if>
            
        <c:if test="${state ne 'logged in'}">
            <a href="logIn.jsp">log in</a><br><br>
        </c:if>
        
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
        
            
        <!--THIS PART DISPLAYS LINKS BASED ON THE USER'S ACCOUNT TYPE-->
        
        <!--this is the Accountant section
                - the Accountant manages invoice information
                - once an invoice is created, there are some fields that can no longer be edited.
                    - see document to know what these are
        -->
        <c:if test="${accountType == '3'}">
            <a href="Servlets.getProductServlet?forOther=invoice">Create Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
            <a href="unfinished.get?getTable=invoice">Unfinished Invoices</a><br>
            <a href="province.get?whatFor=conditionsInvoice">Custom View Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br>
            <a href="salesrep.get?whatFor=searchCustomer">Custom View Customers</a><br><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Secretary section
                - the Secretary handles customer information
                - the Secretary can create Accounts, but can only change the account type when editting
        -->    
        <c:if test="${accountType == '2'}">
            <a href="salesrep.get?whatFor=addCustomer">Add Customer</a><br>
            <a href="Servlets.getCustomerServlet">Edit Customer</a><br>
            <a href="new.get?whatFor=customer&getWhat=overdue">Customers With Overdue Fees</a><br>
            <a href="salesrep.get?whatFor=searchCustomer">Custom View Customers</a><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
            
            <a href="unfinished.get?getTable=invoice">Unfinished Invoices</a><br>
            <a href="conditionsInvoice.jsp">Custom View Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            
            <a href="addSalesRep.jsp">Add Sales Rep</a><br>
            <a href="salesrep.get">Edit Sales Rep</a><br>
            <a href="salesrep.get">View Sales Rep</a><br><br>
            
            <a href="unfinished.get?getTable=ro">Unfinished RO</a><br>
            <a href="conditionsRestockOrder.jsp">Custom View RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            
            
            <a href="createAccount.jsp">Create Account</a><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>    
        
        <!--this is the Inventory Manager Section
                - the IM manages product information
                    - check document for which fields IM can edit
                - he can also create and edit Restock Orders, but only one half of the RO
                    - check document for which fields IM can edit
        -->
        <c:if test="${accountType == '4'}">
            <a href="product.getProductClass">Add Product</a><br>
            <a href="Servlets.getProductServlet">Edit Product</a><br>
            
            <a href="unfinished.get?getTable=ro">Unfinished RO</a><br>
            <a href="product.getProductClass?search=yes&searchWhat=ro">Custom View RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            
            <a href="account.get">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Auditor section
                - the Auditor can edit the other half of the Restock Order
                    - again, check the document to see which fields the Auditor can edit
        -->
        <c:if test="${accountType == '5'}">
            <!--<a href="Servlets.getProductServlet?forOther=restock">Add Restock Order</a><br>-->
            <a href="restockOrder.get">Edit Restock Order</a><br>
            <a href="unfinished.get?getTable=ro">Unfinished RO</a><br>
            <a href="conditionsRestockOrder.jsp">Custom View RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>  
        
        <!--this is the CEO section
                - the CEO can do everything
        -->
        <c:if test="${accountType == '1'}">
            <a href="Servlets.getProductServlet?forOther=invoice">Create Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
            <a href="unfinished.get?getTable=invoice">Unfinished Invoices</a><br>
            <a href="province.get?whatFor=conditionsInvoice">Custom View Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            
            <a href="salesrep.get?whatFor=addCustomer">Add Customer</a><br>
            <a href="Servlets.getCustomerServlet">Edit Customer</a><br>
            <a href="new.get?whatFor=customer">Customers With Overdue Fees</a><br>
            <a href="salesrep.get?whatFor=searchCustomer">Custom View Customers</a><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
            <a href="addSalesRep.jsp">Add Sales Rep</a><br>
            <a href="salesrep.get">Edit Sales Rep</a><br>
            <a href="conditionsSalesRep.jsp">Custom View Sales Rep</a><br>
            <a href="salesrep.get">View Sales Reps</a><br><br>
            <a href="account.getTypeStatus">Create Account</a><br>
            <a href="account.get">View All Accounts</a><br>
            <a href="account.getTypeStatus?forSearch=yes">Custom View Account</a><br><br>
            
            <a href="product.getProductClass?addSupp=yes">Add Supplier</a><br>
            <a href="supplier.get?viewSupp=yes">Edit Supplier</a><br>
            <a href="product.getProductClass?search=yes&searchWhat=supp">Custom View Supplier</a><br>
            <a href="supplier.get?viewSupp=yes">View All Suppliers</a><br><br>
            
            <a href="product.getProductClass">Add Product</a><br>
            <a href="Servlets.getProductServlet">Edit Product</a><br>
            <a href="Servlets.getProductServlet?forOther=restock">Add Restock Order</a><br>
            <a href="restockOrder.get">Edit Restock Order</a><br>
            <a href="unfinished.get?getTable=ro">Unfinished RO</a><br>
            <a href="product.getProductClass?search=yes&searchWhat=ro">Custom View RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            
            
            <!--Notification section-->
            <!--Invoice notifs for those with near (within 7 days) payment or delivery deadlines-->
            <c:if test="${invoiceList.size() eq 0}">
                <p>You have <b>${invoiceList.size()} invoice/s</b> with deadlines within the next 7 days</p>
            </c:if>
            <c:if test="${invoiceList.size() ne 0}">
                <p>You have <b>${invoiceList.size()} invoice/s</b> with deadlines within the next 7 days</p>
                <table border="1">
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
                            <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${inv.getInvoiceID()}"/>">Edit Invoice</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
                
            <!--RestockOrder notifs for those with near (within 7 days) delivery deadlines-->
            
            
            <c:if test="${restocksList.size() eq 0}">
                <p>You have <b>${restocksList.size()} RO/s</b> with deadlines within the next 7 days</p>
            </c:if>
            <c:if test="${restocksList.size() ne 0}">
                <p>You have <b>${restocksList.size()} RO/s</b> with deadlines within the next 7 days</p>
                <table border="1">
                    <tr>
                        <th>Restock Order ID</th>
                        <th>Restock Order Name</th>
                        <th>Product Name</th>
                        <th>Restock Price</th>
                        <th>Pieces Ordered</th>
                        <th>Pieces Received</th>
                        <th>Supplier</th>
                        <th>Date Due</th>
                        <th>Date Delivered</th>
                    </tr>

                    <c:forEach items="${restocksList}" var="ro" begin="0" step="1" varStatus="status">
                        <tr>
                            <td>${ro.getRestockOrderID()}</td>
                            <td><a href="restockOrder.getDetails?restockID=<c:out value="${ro.getRestockOrderID()}"/>">${ro.getRestockOrderName()}</td>
                            <td>${ro.getProductName()}</td>
                            <td>${ro.getRestockPrice()}</td>
                            <td>${ro.getNumberOfPiecesOrdered()}</td>
                            <td>${ro.getNumberOfPiecesReceived()}</td>
                            <td>${ro.getSupplierName()}</td>
                            <td>${ro.getRODateDue()}</td>
                            <td>${ro.getRODateDelivered()}</td>
                            <td><a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            
            
            
        </c:if>
        
        <a href="product.getProductClass?search=yes&searchWhat=prod">Custom View Product</a><br>
        <a href="Servlets.getProductServlet">View Product</a><br>
        
    </body>
</html>
