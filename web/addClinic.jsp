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
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <c:set var="customerID" value="${requestScope.custID}"/>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">ADD CLINIC</legend>
                        <c:if test="${success_msg != null}">
                            <div class="mui-col-md-12 mui--text-center">
                                <div id="success-msg">${success_msg}</div>
                                <c:remove var="success_msg" scope="session"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="mui-col-md-8 mui-col-md-offset-2">
                        <form action="customer.addClinic" method="post" class="mui-form" id="add-clinic-form"> 
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="clinicNameInput" id="clinicNameInput">
                                    <label for="clinicNameInput">Clinic Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <textarea type="text" name="clinicAddressInput" id="clinicAddressInput"></textarea>
                                    <label for="clinicAddressInput">Clinic Address</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="clinicPhoneNumberInput" id="clinicPhoneNumberInput">
                                    <label for="clinicPhoneNumberInput">Clinic Phone Number</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-select">
                                    <select name="chosenProvince">
                                        <c:forEach items="${provList}" var="pro" begin="0" step="1">
                                            <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="chosenProvince">Province</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12 mui--text-center">
                                <c:if test="${error_msg != null}">
                                    <div>
                                        <div class="text-center">
                                            <div id="error-msg">${error_msg}</div>
                                            <c:remove var="error_msg" scope="session"/>
                                        </div>
                                    </div>
                                </c:if>
                                <input type="hidden" value="${customerID}" name="customerIDInput" id="customerIDInput">
                                <button type="submit" class="mui-btn mui-btn--raised">Submit</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        
        
        <br><br>
        <a href="Servlets.viewCustomerDetailsServlet?custID=<c:out value="${clinic.getCustomerID()}"/>">Go to Customer Details</a>
        <br><br>
        <a href="salesrep.get?whatFor=searchCustomer">Custom View Customer</a>
        <br><br>
        <%@include file="/WEB-INF/source/footer.jsp" %>
        <script type="text/javascript" src="js/form-validation/add-clinic-validation.js"></script>
        
    </body>
</html>
