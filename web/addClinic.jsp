<%-- 
    Document   : addClinic
    Created on : 09 12, 16, 11:28:52 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String customerID = ""+request.getParameter("custID");
    ArrayList<provinceBean> provList = (ArrayList<provinceBean>)request.getAttribute("provList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Add Clinic</title>
    </head>
    <body>
        <h1>This is the Add Clinic Page!</h1>
        <form action="customer.addClinic" method="post">
            <input type="hidden" value="<%=customerID%>" name="customerIDInput">
            Enter Clinic Name:<input type="text" name="clinicNameInput"><br>
            Enter Clinic Address:<input type="text" name="clinicAddressInput"><br>
            Enter Clinic Phone Number:<input type="text" name="clinicPhoneNumInput"><br>
            Enter Province:<select name="chosenProvince">
                <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                </c:forEach>
            </select><br><br>
            <input type="submit" value="Add">
        </form>
        
        
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
