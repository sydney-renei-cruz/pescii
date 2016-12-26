<%-- 
    Document   : addCustomer
    Created on : 08 20, 16, 1:14:04 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<customerBean> customersList = (ArrayList<customerBean>)request.getAttribute("customersList");
    ArrayList<salesRepBean> salesRepList = (ArrayList<salesRepBean>)request.getAttribute("salesRepList");
    ArrayList<provinceBean> provinceList = (ArrayList<provinceBean>)request.getAttribute("provList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Add Customer</title>
    </head>
    <body>
        <h1>This is the Add Customer page!</h1>
        
        <form action="Servlets.addCustomerServlet" method="post">
            <b>Customer</b><br>
            Enter Customer PRCID:<input type="text" name="customerIDInput"><br>
            Enter Last Name:<input type="text" name="customerLastNameInput"><br>
            Enter First Name:<input type="text" name="customerFirstNameInput"><br>
            Enter Mobile Number:<input type="text" name="customerMobileNumberInput"><br>
            Enter Telephone Number:<input type="text" name="customerTelephoneNumberInput"><br><br>
            
            <b>Clinic</b><br>
            Enter Clinic Name:<input type="text" name="clinicNameInput"><br>
            Enter Clinic Address:<input type="text" name="clinicAddressInput"><br>
            Enter Clinic Province:<select name="chosenProvince">
                <c:forEach items="<%=provinceList%>" var="prov" begin="0" step="1">
                    <option value="${prov.getProvinceID()}">${prov.getProvinceName()}</option>
                </c:forEach>
            </select><br><br>
            Enter Clinic Phone Number:<input type="text" name="clinicPhoneNumInput"><br>
            Enter Sales Representative:<select name="chosenSalesRep">
                <c:forEach items="${salesRepList}" var="sr" begin="0" step="1">
                    <option value="${sr.getSalesRepID()}">${sr.getSalesRepLastName()}, ${sr.getSalesRepFirstName()}</option>
                </c:forEach>
            </select><br><br>
            <input type="submit" value="Add"><br>
        </form>
        
        
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
