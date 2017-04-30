<%-- 
    Document   : editClinic
    Created on : 12 4, 16, 5:24:53 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    clinicBean clinic = (clinicBean)request.getAttribute("clinic");
    ArrayList<provinceBean> provList = (ArrayList<provinceBean>)request.getAttribute("provList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Clinic</title>
    </head>
    <body>
        <h1>This is the Edit Clinic Page!</h1>
        
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        
        Customer Name: ${clinic.getCustomerLastName()}, ${clinic.getCustomerFirstName()}<br>
        
        <c:set var="customerID" value="${requestScope.custID}"/>
        <form action="customer.editClinic" method="post">
                <input type="hidden" value="${clinic.getClinicID()}" name="clinID">
                <input type='hidden' value='${customerID}' name='custID'>
                PRCID: ${clinic.getPRCID()}<br>
                Clinic Name: <input type="text" value="${clinic.getClinicName()}" name="clinicNameInput" maxlength="255"><br>
                Clinic Address: <input type="text" value="${clinic.getClinicAddress()}" name="clinicAddressInput" maxlength="255"><br>
                Clinic Phone Number: <input type="text" value="${clinic.getClinicPhoneNumber()}" name="clinicPhoneNumberInput" maxlength="15"><br>
                <b>Province</b><br>
                From: ${clinic.getProvinceName()}<br>
                To:<select name="chosenProvince">
                    <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                    </c:forEach>
                </select><br><br>
                <br><input type="submit" value="Save Changes">
                
        </form>
        
        <br><br>
        <a href="Servlets.viewCustomerDetailsServlet?custID=<c:out value="${clinic.getCustomerID()}"/>">Go to Customer Details</a>
        <br><br>
        <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a>
        <br><br>
        <a href="notif.get?forWhat=invoice">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>