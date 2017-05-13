<%-- 
    Document   : getSalesRep
    Created on : 11 1, 16, 11:55:00 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View Sales Rep</title>
    </head>
    <body>
         <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="salesRepsList" value="${requestScope.salesRepsList}"/>
        <c:set var="listSize" value="${salesRepsList.size()}"/>
        
        <div class="mui-textfield mui-textfield--float-label">
                   
                    </div>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            
            
        <legend class="mui--text-center mui--text-display3">Sales Representatives</legend>
         
        <c:if test="${listSize > 0}">
            <table class="mui-table mui--text-center" id="customer-table">
            <thead>
                <tr>
                    <th>Sales Rep ID</th>
                    <th>Last Name</th>
                    <th>First Name</th>
                    <th>Mobile Number</th>
                    <th>Address</th>
                </tr>
            </thead>
           <tbody>
            <c:forEach items="${salesRepsList}" var="sr" begin="0" step="1" varStatus="status">
                <tr>
                        <td>${sr.getSalesRepID()}</td>
                        <td>${sr.getSalesRepLastName()}</td>
                        <td>${sr.getSalesRepFirstName()}</td>
                        <td>${sr.getSalesRepMobileNumber()}</td>
                        <td>${sr.getSalesRepAddress()}</td>
                        <td><a href="salesrep.getDetails?srID=<c:out value="${sr.getSalesRepID()}"/>">Edit</a></td>
                </tr>

            </c:forEach>
           </tbody>
            </table>
        </c:if>
        </div>
        
        <div class="text-center">
            <div id="error-msg">${error_msg}</div>
                <c:if test="${listSize eq 0}">
                    <p> 0 sales representatives found.</p>
                </c:if>
            </div>
        </div>     
        
        <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">
        <br><br>
        <a href="conditionsSalesRep.jsp">Search Sales Rep</a>
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
