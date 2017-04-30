<%-- 
    Document   : addClinic
    Created on : 09 12, 16, 11:28:52 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    String customerID = ""+request.getParameter("custID");
    if(request.getParameter("custID")==null){customerID = ""+request.getAttribute("custID");}
    ArrayList<provinceBean> provList = (ArrayList<provinceBean>)request.getAttribute("provList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Add Clinic</title>
    </head>
    <body>
        <h1>This is the Add Clinic Page!</h1>
        
        <c:set var="customerID" value="${requestScope.custID}"/>
        
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
            
        <form action="customer.addClinic" method="post">
            <input type="hidden" value="${customerID}" name="customerIDInput">
            Enter Clinic Name:<input type="text" name="clinicNameInput" maxlength="255" required><br>
            Enter Clinic Address:<input type="text" name="clinicAddressInput" maxlength="255" required><br>
            Enter Clinic Phone Number:<input type="text" name="clinicPhoneNumInput" maxlength="15" required><br>
            Enter Province:<select name="chosenProvince">
                <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                </c:forEach>
            </select><br><br>
            <input type="submit" value="Add">
        </form>
        
        
        <br><br>
        <a href="Servlets.viewCustomerDetailsServlet?custID=<c:out value="${clinic.getCustomerID()}"/>">Go to Customer Details</a>
        <br><br>
        <a href="salesrep.get?whatFor=searchCustomer">Custom View Customer</a>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
