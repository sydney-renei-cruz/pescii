<%-- 
    Document   : addCustomer
    Created on : 08 20, 16, 1:14:04 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Add Customer</title>
    </head>
    <body>
        <h1>This is the Add Customer page!</h1>
        
        
        <form action="Servlets.addCustomerServlet" method="post">
            Enter Customer PRCID:<input type="text" name="customerIDInput"><br>
            Enter Customer Name:<input type="text" name="customerNameInput"><br>
            Enter Customer Mobile Number:<input type="text" name="customerMobileNumberInput"><br>
            Enter Customer Telephone Number:<input type="text" name="customerTelephoneNumberInput"><br>
            Enter Clinic Name:<input type="text" name="clinicNameInput"><br>
            Enter Clinic Address:<input type="text" name="clinicAddressInput"><br>
            Enter Clinic Phone Number:<input type="text" name="clinicPhoneNumInput"><br>
            <input type="submit" value="Add">
        </form>
        
        
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
