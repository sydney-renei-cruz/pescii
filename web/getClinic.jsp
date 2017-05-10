<%-- 
    Document   : getClinic
    Created on : 05 10, 17, 6:45:23 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Clinics</title>
    </head>
    <body>
        <h1>This is the View Clinics Page!</h1>
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="clinicsList" value="${requestScope.clinicsList}"/>
        <c:set var="listSize" value="${clinicsList.size()}"/>
        
        <c:if test="${listSize > 0}">
        <table border="1">
            <tr>
                <th>Clinic ID</th>
                <th>Clinic Name</th>
                <th>Customer Name</th>
                <th>Clinic Address</th>
                <th>Province</th>
                <th>Clinic Phone #</th>
            </tr>
        
        <c:forEach items="${clinicsList}" var="clin" begin="0" step="1">
            <tr>
                <td>${clin.getClinicID()}</td>
                <td>${clin.getClinicName()}</td>
                <td><a href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=<c:out value="${clin.getCustomerID()}"/>">${clin.getCustomerLastName()}, ${clin.getCustomerFirstName()}</a></td>
                <td>${clin.getClinicAddress()}</td>
                <td>${clin.getProvinceName()}</td>
                <td>${clin.getClinicPhoneNumber()}</td>
                <c:if test="${accountType eq '2' || accountType eq '1'}">
                <td><a href="customer.getClinic?clinID=<c:out value="${clin.getClinicID()}"/>">Edit</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </table>
        </c:if>
        <br><br>
        
        <c:if test="${listSize eq 0}">
            <p> 0 clinics found.</p>
        </c:if>
        
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>
