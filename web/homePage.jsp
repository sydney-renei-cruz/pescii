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
<<<<<<< HEAD
                - once an invoice is created, there are some fields that can no longer be edited.
                    - see document to know what these are
        -->
        <c:if test="${accountType == 'Accountant'}">
            <a href="Servlets.getProductServlet?forInvoice=yes">Add Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">View Invoice</a><br><br>
=======
                - once an invoice is created, there are some fields that can no longer be editted.
                    - see document to know what these are
        -->
        <c:if test="${accountType == 'Accountant'}">
            <a href="addInvoice.jsp">Add Invoice</a><br>
            <a href="editInvoice.jsp">Edit Invoice</a><br>
            <a href="getInvoice.jsp">View Invoice</a><br><br>
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
            <a href="editAccount.jsp">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Secretary section
                - the Secretary handles customer information
                - the Secretary can create Accounts, but can only change the account type when editting
        -->    
        <c:if test="${accountType == 'Secretary'}">
            <a href="addCustomer.jsp">Add Customer</a><br>
            <a href="editCustomer.jsp">Edit Customer</a><br>
            <a href="Servlets.getCustomerServlet">View Customer</a><br><br>
            
            <a href="createAccount.jsp">Create Account</a><br>
            <a href="editAccount.jsp">Edit Account</a><br><br>
        </c:if>    
        
        <!--this is the Inventory Manager Section
                - the IM manages product information
                    - check document for which fields IM can edit
                - he can also create and edit Restock Orders, but only one half of the RO
                    - check document for which fields IM can edit
        -->
        <c:if test="${accountType == 'Inventory Manager'}">
            <a href="addProduct.jsp">Add Product</a><br>
            <a href="editProduct.jsp">Edit Product</a><br><br>
            
            <a href="createRestockOrder.jsp">Add Restock Order</a><br>
            <a href="editRestockOrder.jsp">Edit Restock Order</a><br>
            <a href="getRestockOrder.jsp">View Restock Order</a><br><br>
            
            <a href="editAccount.jsp">Edit Account</a><br><br>
        </c:if>
        
        <!--this is the Auditor section
                - the Auditor can edit the other half of the Restock Order
                    - again, check the document to see which fields the Auditor can edit
        -->
        <c:if test="${accountType == 'Auditor'}">
            <a href="editRestockOrder.jsp">Edit Restock Order</a><br>
            <a href="getRestockOrder.jsp">View Restock Order</a><br>
            <a href="editAccount.jsp">Edit Account</a><br><br>
        </c:if>  
        
        <!--this is the CEO section
                - the CEO can do everything
        -->
        <c:if test="${accountType == 'CEO'}">
<<<<<<< HEAD
            <a href="Servlets.getProductServlet?forInvoice=yes">Add Invoice</a><br>
            <a href="Servlets.getInvoiceServlet">Edit Invoice</a><br>
=======
            <a href="addInvoice.jsp">Add Invoice</a><br>
            <a href="editInvoice.jsp">Edit Invoice</a><br>
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
            <a href="Servlets.getInvoiceServlet">View Invoice</a><br><br>
            
            <a href="addCustomer.jsp">Add Customer</a><br>
            <a href="editCustomer.jsp">Edit Customer</a><br>
            <a href="Servlets.getCustomerServlet">View Customer</a><br><br>
            
            <a href="createAccount.jsp">Create Account</a><br>
            <a href="editAccount.jsp">Edit Account</a><br><br>
            
            <a href="addProduct.jsp">Add Product</a><br>
            <a href="editProduct.jsp">Edit Product</a><br>
            <a href="createRestockOrder.jsp">Add Restock Order</a><br>
            <a href="editRestockOrder.jsp">Edit Restock Order</a><br>
            <a href="getRestockOrder.jsp">View Restock Order</a><br><br>
        </c:if>
        
<<<<<<< HEAD
        <a href="Servlets.getProductServlet">View Product</a><br>
=======
        <a href="getProduct.jsp">View Product</a><br>
>>>>>>> 6c76f4a4ff7cd215e69507498bd92d6a2cd82aeb
        
    </body>
</html>
