<%-- 
    Document   : editClinic
    Created on : 09 12, 16, 1:52:08 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    clinicBean clinic = (clinicBean)request.getAttribute("clinic");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Clinic</title>
    </head>
    <body>
        <h1>This is the Edit Clinic Page!</h1>
        
        <form action="customer.editClinic" method="post">
                Clinic ID: <input type="hidden" value="${clinic.getClinicID()}" name="clinID">${restockOrder.getRestockOrderID()}<br>
                PRCID: ${clinic.getPRCID()}<br>
                Clinic Name: <input type="text" value="${clinic.getClinicName()}" name="clinicNameInput"><br>
                Clinic Address: <input type="text" value="${clinic.getClinicAddress()}" name="clinicAddressInput"><br>
                Clinic Phone Number: <input type="text" value="${clinic.getClinicPhoneNumber()}" name="clinicPhoneNumberInput"><br>
                
                <br><input type="submit" value="Save Changes">
                
        </form>
            
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
