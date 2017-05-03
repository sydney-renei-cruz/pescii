<%-- 
    Document   : homePage
    Created on : 08 16, 16, 9:12:22 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Homepage</title>
    </head>
    <body>
        <h1>This is the homePage!</h1>
         <c:set var="state" value="${sessionScope.state}"/>
         <c:set var="userName" value="${sessionScope.userName}"/>
         <c:set var="message" value="${sessionScope.message}"/>
         <c:set var="accountType" value="${sessionScope.accountType}"/>
         <c:set var="suppID" value="${sessionScope.suppID}"/>
        <c:set var="restocksList" value="${requestScope.restocksList}"/>
        <c:set var="invoiceList" value="${requestScope.invoiceList}"/>
        <c:set var="productsList" value="${productsList}"/>
        
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
        
        <!--this is the Sales Representative section
                - the SalesRep manages invoice information, but can't input the payment fields.
                - once an invoice is created, there are some fields that can no longer be edited.
                    - see document to know what these are
        -->
        <c:if test="${accountType == '6'}">
            <a href="Servlets.getProductServlet?forOther=invoice">Create Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
            <a href="unfinished.get?getTable=invoice">View Unfinished Invoices</a><br>
            <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>
        
        
        <!--this is the Accountant section
                - the Accountant manages invoice information
                - once an invoice is created, there are some fields that can no longer be edited.
                    - see document to know what these are
        -->
        <c:if test="${accountType == '3'}">
            <a href="Servlets.getProductServlet?forOther=invoice">Create Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
            <a href="unfinished.get?getTable=invoice">View Unfinished Invoices</a><br>
            <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Secretary section
                - the Secretary handles customer information
                - the Secretary records if an Invoice has been delivered or not
                - the Secretary manages Sales Reps and Accounts
        -->    
        <c:if test="${accountType == '2'}">
            <a href="salesrep.get?whatFor=addCustomer">Add Customer</a><br>
            <a href="Servlets.getCustomerServlet">Edit Customer</a><br>
            <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
            
            <a href="unfinished.get?getTable=invoice">View Unfinished Invoices</a><br>
            <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            
            <a href="addSalesRep.jsp">Add Sales Rep</a><br>
            <a href="salesrep.get">Edit Sales Rep</a><br>
            <a href="conditionsSalesRep.jsp">Search Sales Reps</a><br>
            <a href="salesrep.get">View All Sales Reps</a><br><br>
            
            <a href="unfinished.get?getTable=ro">View Unfinished RO</a><br>
            <a href="conditionsRestockOrder.jsp">Search RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            
            
            <a href="account.getTypeStatus">Create Account</a><br>
            <a href="account.get">Edit Account</a><br>
            <a href="account.getTypeStatus?forSearch=yes">Search Accounts</a><br>
            <a href="account.get">View All Accounts</a><br><br>
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
            
            <c:choose>
                <c:when test="${(suppID eq null || suppID eq 'null' || suppID eq '')}">
                    <a href="supplier.get?viewSupp=yes&forRestock=yes">Create Restock Order</a><br>      
                </c:when>
                <c:when test="${cartType eq 'restock' && (suppID ne null && suppID ne 'null' && suppID ne '')}">
                    <a href="Servlets.getProductServlet?forOther=restock">Create Restock Order</a><br>      
                </c:when>
            </c:choose>
            <a href="restockOrder.get">Edit Restock Order</a><br>
            <a href="unfinished.get?getTable=ro">View Unfinished RO</a><br>
            <a href="restockOrder.getStatus">Search RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            
            <a href="product.getProductClass?addSupp=yes">Add Supplier</a><br>
            <a href="supplier.get?viewSupp=yes">Edit Supplier</a><br>
            <a href="product.getProductClass?search=yes&searchWhat=supp">Search Supplier</a><br>
            <a href="supplier.get?viewSupp=yes">View All Suppliers</a><br><br>
            
            <a href="account.get">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Auditor section
                - the Auditor can edit the other half of the Restock Order
                    - again, check the document to see which fields the Auditor can edit
        -->
        <c:if test="${accountType == '5'}">
            <!--<a href="Servlets.getProductServlet?forOther=restock">Add Restock Order</a><br>-->
            <a href="restockOrder.get">Complete Restock Order</a><br>
            <a href="unfinished.get?getTable=ro">Unfinished RO</a><br>
            <a href="conditionsRestockOrder.jsp">Search RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>  
        
        <!--this is the CEO section
                - the CEO can do everything
        -->
        <c:if test="${accountType == '1'}">
            <a href="Servlets.getProductServlet?forOther=invoice">Create Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
            <a href="unfinished.get?getTable=invoice">View Unfinished Invoices</a><br>
            <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            
            <a href="salesrep.get?whatFor=addCustomer">Add Customer</a><br>
            <a href="Servlets.getCustomerServlet">Edit Customer</a><br>
            <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
            <a href="addSalesRep.jsp">Add Sales Rep</a><br>
            <a href="salesrep.get">Edit Sales Rep</a><br>
            <a href="conditionsSalesRep.jsp">Search Sales Reps</a><br>
            <a href="salesrep.get">View All Sales Reps</a><br><br>
            <a href="account.getTypeStatus">Create Account</a><br>
            <a href="account.get">Edit Account</a><br>
            <a href="account.getTypeStatus?forSearch=yes">Search Accounts</a><br>
            <a href="account.get">View All Accounts</a><br><br>
            
            <a href="product.getProductClass?addSupp=yes">Add Supplier</a><br>
            <a href="supplier.get?viewSupp=yes">Edit Supplier</a><br>
            <a href="product.getProductClass?search=yes&searchWhat=supp">Search Supplier</a><br>
            <a href="supplier.get?viewSupp=yes">View All Suppliers</a><br><br>
            
            <a href="product.getProductClass">Add Product</a><br>
            <a href="Servlets.getProductServlet">Edit Product</a><br>
            <a href="Servlets.getProductServlet?forOther=lowstockLevel">Set Low stock level</a><br>
            <c:choose>
                <c:when test="${(suppID eq null || suppID eq 'null' || suppID eq '')}">
                    <a href="supplier.get?viewSupp=yes&forRestock=yes">Create Restock Order</a><br>      
                </c:when>
                <c:when test="${cartType eq 'restock' && (suppID ne null && suppID ne 'null' && suppID ne '')}">
                    <a href="Servlets.getProductServlet?forOther=restock">Create Restock Order</a><br>      
                </c:when>
            </c:choose>
            <a href="restockOrder.get">Edit Restock Order</a><br>
            <a href="unfinished.get?getTable=ro">View Unfinished RO</a><br>
            <a href="restockOrder.getStatus">Search RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            
            
        </c:if>
        
        <!--Notification section-->
        <c:if test="${accountType == '3' || accountType == '2' || accountType == '1'}">
            
                <!--Invoice notifs for those with near (within 7 days) payment or delivery deadlines-->
                <c:if test="${invoiceList.size() eq 0}">
                    <p>You have <b>${invoiceList.size()} Invoices</b> with deadlines within the next 7 days</p>
                </c:if>
                <c:if test="${invoiceList.size() ne 0}">
                    <p>You have <b>${invoiceList.size()} Invoice/s</b> with deadlines within the next 7 days</p>
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
        </c:if>    
                    
        <br><br>            
        <!--RestockOrder notifs for those with near (within 7 days) delivery deadlines-->
        <c:if test="${accountType == '4' || accountType == '5' || accountType == '2' || accountType == '1'}">    
            <c:if test="${restocksList.size() eq 0}">
                <p>You have <b>${restocksList.size()} ROs</b> with deadlines within the next 7 days</p>
            </c:if>
            <c:if test="${restocksList.size() ne 0}">
                <p>You have <b>${restocksList.size()} RO/s</b> with deadlines within the next 7 days</p>
                <table border="1">
                <tr>
                    <th>Restock Order Name</th>
                    <th>Date Due</th>
                    <th>Date Delivered</th>
                    <th>Amount Paid</th>
                    <th>Discount</th>
                    <th>Date Paid</th>
                    <th>Date Created</th>
                </tr>

                <c:forEach items="${restocksList}" var="ro" begin="0" step="1" varStatus="status">
                    <tr>
                        <td><a href="restockOrder.getDetails?restockID=<c:out value="${ro.getRestockOrderID()}"/>">${ro.getRestockOrderName()}</a></td>
                        <td>${ro.getRODateDue()}</td>
                        <td>${ro.getRODateDelivered()}</td>
                        <td>${ro.getDiscount()}</td>
                        <td>${ro.getDatePaid()}</td>
                        <td>${ro.getDateCreated()}</td>
                        <td><a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit</td>
                    </tr>
                </c:forEach>
                </table>
            </c:if>
            <br><br>    
            <c:if test="${productsList.size() eq 0}">
                <p>You have <b>${productsList.size()} Products</b> with low stocks.</p>
            </c:if>
            <c:if test="${productsList.size() ne 0}">
                <p>You have <b>${restocksList.size()} RO/s</b> with deadlines within the next 7 days</p>
                <table border="1">
                    <tr>
                        <th>Product Name</th>
                        <th>Supplier</th>
                        <th>Product Price</th>
                        <th>Restock Price</th>
                        <th>Stocks Remaining</th>
                        <th>Low Stock</th>
                        <th>Brand</th>
                        <th>Product Class</th>
                        <th>Color</th>
                    </tr>

                    <c:forEach items="${productsList}" var="prod" begin="0" step="1" varStatus="status">
                    <tr>
                        <td><a href="product.getDetails?prodID=<c:out value="${prod.getProductID()}"/>">${prod.getProductName()}</a></td>
                        <td>${prod.getSupplierName()}</td>
                        <td>${prod.getProductPrice()}</td>
                        <td>${prod.getRestockPrice()}</td>
                        <td>${prod.getStocksRemaining()}</td>
                        <td>${prod.getLowStock()}</td>
                        <td>${prod.getBrand()}</td>
                        <td>${prod.getProductClassName()}</td>
                        <td>${prod.getColor()}</td>

                        <c:if test="${accountType eq '4' || accountType eq '1'}">
                            <td><a href="product.getDetails?forEdit=yes&prodID=<c:out value="${prod.getProductID()}"/>">EDIT</a></td>
                        </c:if>
                            
                        <c:if test="${accountType eq '4' || accountType eq '1'}">
                            <td><a href="addToROCart?getQuantity=yes&prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>&prodPrice=<c:out value="${prod.getRestockPrice()}"/>&suppID=<c:out value="${prod.getSupplierID()}"/>&suppName=<c:out value="${prod.getSupplierName()}"/>">Create RO</a></td>
                        </c:if>    

                    </tr>

                </c:forEach>
                </table>
                <br><br>
            </c:if>        
            
        </c:if>
        
        <a href="product.getProductClass?search=yes&searchWhat=prod">Search Product</a><br>
        <a href="Servlets.getProductServlet">View all Products</a><br>
        
    </body>
</html>
