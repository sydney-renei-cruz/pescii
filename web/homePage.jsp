<%-- 
    Document   : homePage
    Created on : 08 16, 16, 9:12:22 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String state = ""+session.getAttribute("state");
    String userName = ""+session.getAttribute("userName");
    String message = ""+request.getAttribute("message");
    String accountType = ""+session.getAttribute("accountType");
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
            <a href="new.get?whatFor=invoice">New Invoices</a><br>
            <a href="new.get?whatFor=invoice&validated=yes">Validated Invoices</a><br>
            <a href="new.get?whatFor=invoice&close=yes">Invoices Near Deadlines</a><br>
            <a href="unfinished.get?getTable=invoice">View Unfinished Invoices</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            <a href="Servlets.getCustomerServlet">View Customer</a><br><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Secretary section
                - the Secretary handles customer information
                - the Secretary can create Accounts, but can only change the account type when editting
        -->    
        <c:if test="${accountType == '2'}">
            <a href="Servlets.getCustomerServlet?forAdd=yes">Add Customer</a><br>
            <a href="Servlets.getCustomerServlet">Edit Customer</a><br>
            <a href="Servlets.getCustomerServlet">View Customer</a><br><br>
            
            <a href="addSalesRep.jsp">Add Sales Rep</a><br>
            <a href="salesrep.get">View Sales Rep</a><br><br>
            
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
            <a href="addProduct.jsp">Add Product</a><br>
            <a href="editProduct.jsp">Edit Product</a><br><br>
            <a href="new.get?whatFor=restockOrder&new=yes">New Restock Orders</a><br>
            <a href="new.get?whatFor=restockOrder&completed=yes">Recently Completed RO</a><br>
            <a href="new.get?whatFor=restockOrder&close=yes">RO Near Deadlines</a><br>
            <a href="unfinished.get?getTable=ro">View Unfinished RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            
            <a href="account.get">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Auditor section
                - the Auditor can edit the other half of the Restock Order
                    - again, check the document to see which fields the Auditor can edit
        -->
        <c:if test="${accountType == '5'}">
            <a href="Servlets.getProductServlet?forOther=restock">Add Restock Order</a><br>
            <a href="restockOrder.get">Edit Restock Order</a><br>
            <a href="new.get?whatFor=restockOrder&new=yes">New Restock Orders</a><br>
            <a href="new.get?whatFor=restockOrder&completed=yes">Recently Completed RO</a><br>
            <a href="new.get?whatFor=restockOrder&close=yes">RO Near Deadlines</a><br>
            <a href="unfinished.get?getTable=ro">View Unfinished RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
            <a href="account.get">Edit Account</a><br><br>
        </c:if>  
        
        <!--this is the CEO section
                - the CEO can do everything
        -->
        <c:if test="${accountType == '1'}">
            <a href="Servlets.getProductServlet?forOther=invoice">Create Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
            <a href="new.get?whatFor=invoice">New Invoices</a><br>
            <a href="new.get?whatFor=invoice&validated=yes">Validated Invoices</a><br>
            <a href="new.get?whatFor=invoice&close=yes">Invoices Near Payment Deadlines</a><br>
            <a href="unfinished.get?getTable=invoice">Unfinished Invoices</a><br>
            <a href="Servlets.getInvoiceServlet">View All Invoices</a><br><br>
            
            <a href="Servlets.getCustomerServlet?forAdd=yes">Add Customer</a><br>
            <a href="Servlets.getCustomerServlet">Edit Customer</a><br>
            <a href="new.get?whatFor=customer">Customers With Overdue Fees</a><br>
            <a href="Servlets.getCustomerServlet">View All Customers</a><br><br>
            <a href="addSalesRep.jsp">Add Sales Rep</a><br>
            <a href="salesrep.get">View Sales Rep</a><br>
            <a href="createAccount.jsp">Create Account</a><br>
            <a href="account.get">Edit Account</a><br><br>
            
            <a href="addProduct.jsp">Add Product</a><br>
            <a href="editProduct.jsp">Edit Product</a><br>
            <a href="Servlets.getProductServlet?forOther=restock">Add Restock Order</a><br>
            <a href="restockOrder.get">Edit Restock Order</a><br>
            <a href="new.get?whatFor=restockOrder&new=yes">New Restock Orders</a><br>
            <a href="new.get?whatFor=restockOrder&completed=yes">Recently Completed RO</a><br>
            <a href="new.get?whatFor=restockOrder&close=yes">RO Near Deadlines</a><br>
            <a href="unfinished.get?getTable=ro">Unfinished RO</a><br>
            <a href="restockOrder.get">View All Restock Orders</a><br><br>
        </c:if>
        
        <a href="Servlets.getProductServlet">View Product</a><br>
        
    </body>
</html>
