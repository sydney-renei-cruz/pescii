<%-- 
    Document   : getClinic
    Created on : 05 11, 17, 3:22:33 AM
    Author     : Romulus
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
        
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="clinicsList" value="${requestScope.clinicsList}"/>
        <c:set var="listSize" value="${clinicsList.size()}"/>
        
        <div class="mui-textfield mui-textfield--float-label">
                   
                    </div>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            
            
        <legend class="mui--text-center mui--text-display3">Clinics</legend> 
        
        <c:if test="${listSize > 0}">
        <table class="mui-table mui--text-center" >
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
       </div>
        
        <div class="text-center">
            <div id="error-msg">${error_msg}</div>
            <c:if test="${listSize eq 0}">
                <p> 0 suppliers found.</p>
            </c:if>
        </div>
            
       
        <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        </div>  
                  </div>
            </div>
        </footer>      
    </body>
</html>
