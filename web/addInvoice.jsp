<%-- 
    Document   : addInvoice
    Created on : 08 20, 16, 11:27:31 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    customerBean customer = (customerBean)request.getAttribute("customer");
    ArrayList<clinicBean> clinicsList = (ArrayList<clinicBean>)request.getAttribute("clinicsList");
    ArrayList<invoiceStatusBean> invStatList = (ArrayList<invoiceStatusBean>)request.getAttribute("invStatList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Invoice</title>
    </head>
    <body>
        <h1>This is the Add Invoice page!</h1>
        
            <table border="1">
                <tr>
                    <th>PRC ID</th>
                    <th>Customer Name</th>
                    <th>Mobile #</th>
                    <th>Telephone #</th>
                </tr>

                <tr>
                        <td>${customer.getPRCID()}</td>
                        <td>${customer.getCustomerLastName()}, ${customer.getCustomerFirstName()}</td>
                        <td>${customer.getCustomerMobileNumber()}</td>
                        <td>${customer.getCustomerTelephoneNumber()}</td>
                </tr>
            </table>
            
            <br><br><br>
            
        
        <form action="invoice.add" method="POST">
            
            Enter Invoice Name: <input type="text" name="invoiceNameInput"><br>
            Select Clinic: <select name="chosenClinic">
                <c:forEach items="${clinicsList}" var="clin" begin="0" step="1">
                    <option value="${clin.getClinicID()}">${clin.getClinicAddress()}</option>
                </c:forEach>
            </select>
            
            <br><br>
            <input type="hidden" name="customerIDInput" value="${customer.getCustomerID()}">
            
            Enter Delivery Date: <input type="text" name="deliveryDateInput"><br>
            Additional Accessories: <input type="text" name="addAccInput"><br>
            Terms of Payment: <input type="text" name="topInput"><br>
            Payment Due Date: <input type="text" name="paymentDueDateInput"><br>
            Date Paid: <input type="text" name="datePaidInput"><br>
            Status: <select name="statusInput">
                <c:forEach items="${invStatList}" var="invStat" begin="0" step="1">
                    <option value="${invStat.getStatusID()}">${invStat.getStatusName()}</option>
                </c:forEach>
            </select><br>
            Amount Due: <input type="text" name="amountDueInput"><br>
            Discount: <input type="text" name="DiscountInput"><br>
            
            <input type="submit" value="Add Invoice">
        </form>
        <br><br>
        <a href="Servlets.getCustomerServlet">Return to Customer List</a>
        <br><br>
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>

    </body>
</html>
