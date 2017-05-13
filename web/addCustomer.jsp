<%-- 
    Document   : addCustomer
    Created on : 08 20, 16, 1:14:04 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    String message = ""+request.getAttribute("message");
    ArrayList<salesRepBean> salesRepList = (ArrayList<salesRepBean>)request.getAttribute("salesRepsList");
    ArrayList<provinceBean> provinceList = (ArrayList<provinceBean>)request.getAttribute("provList");
%>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        <c:set var="provinceList" value="${requestScope.provList}"/>
        <c:set var="salesRepList" value="${requestScope.salesRepsList}"/>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">ADD CUSTOMER</legend>
                        <c:if test="${success_msg != null}">
                            <div class="mui-col-md-12 mui--text-center">
                                <div id="success-msg">${success_msg}</div>
                                <c:remove var="success_msg" scope="session"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="mui-col-md-8 mui-col-md-offset-2">
                        <form action="Servlets.addCustomerServlet" method="post" class="mui-form" id="add-customer-form">
                            <br>
                            <div class="mui-col-md-12">
                                <legend>Customer Information</legend>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="number" name="customerIDInput" id="customerIDInput">
                                    <label for="customerIDInput">Customer Professional Regulation Comission ID</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerFirstNameInput" id="customerFirstNameInput">
                                    <label for="customerFirstNameInput">First Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerLastNameInput" id="customerLastNameInput">
                                    <label for="customerLastNameInput">Last Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerMobileNumberInput" id="customerMobileNumberInput">
                                    <label for="customerMobileNumberInput">Mobile Number</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerTelephoneNumberInput" id="customerTelephoneNumberInput">
                                    <label for="customerTelephoneNumberInput">Telephone Number</label>
                                </div>
                            </div>
                            
                            <div class="mui-col-md-12">
                                <legend>Clinic Information</legend>
                            </div>
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
                            <c:set var="provinceList" value="${requestScope.provList}"/>
                            <c:set var="salesRepList" value="${requestScope.salesRepsList}"/>
                            <div class="mui-col-md-6">
                                <div class="mui-select">
                                    <select name="chosenProvince">
                                        <c:forEach items="${provinceList}" var="prov" begin="0" step="1">
                                            <option value="${prov.getProvinceID()}">${prov.getProvinceName()}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="chosenProvince">Province</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-select">
                                    <select name="chosenSalesRep">
                                        <c:forEach items="${salesRepList}" var="sr" begin="0" step="1">
                                            <option value="${sr.getSalesRepID()}">${sr.getSalesRepLastName()}, ${sr.getSalesRepFirstName()}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="chosenSalesRep">Sales Representative</label>
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
                                <button type="submit" class="mui-btn mui-btn--raised">Submit</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="mui--appbar-height"></div>
        <%@include file="/WEB-INF/source/footer.jsp" %>
        <script type="text/javascript" src="js/form-validation/add-customer-validation.js"></script>
    </body>
</html>
