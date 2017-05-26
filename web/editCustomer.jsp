<%-- 
    Document   : editCustomer
    Created on : 08 20, 16, 5:20:13 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    customerBean customer = (customerBean)request.getAttribute("customer");
    ArrayList<clinicBean> clinicsList = (ArrayList<clinicBean>)request.getAttribute("clinicsList");
    ArrayList<invoiceBean> invoicesList = (ArrayList<invoiceBean>)request.getAttribute("invoicesList");
    ArrayList<salesRepBean> salesRepList = (ArrayList<salesRepBean>)request.getAttribute("salesRepList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">EDIT CUSTOMER</legend>
                        <div class="mui-col-md-12 mui--text-center">
                            <c:if test="${success_msg != null}">
                                    <div id="success-msg">${success_msg}</div>
                                    <c:remove var="success_msg" scope="session"/>
                            </c:if>
                        </div>
                    </div>
                    <div class="mui-col-md-8 mui-col-md-offset-2">
                        <form action="customer.edit" method="post" class="mui-form" id="edit-customer-form">
                            <br>
                            <div class="mui-col-md-12">
                                <legend>Customer Information</legend>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="number" name="PRCIDInput" id="PRCIDInput" value="${customer.getPRCID()}">
                                    <label for="PRCIDInput">Customer Professional Regulation Comission ID</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerFirstNameInput" id="customerFirstNameInput" value="${customer.getCustomerFirstName()}" required>
                                    <label for="customerFirstNameInput">First Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerLastNameInput" id="customerLastNameInput" value="${customer.getCustomerLastName()}" required>
                                    <label for="customerLastNameInput">Last Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerMobileNumberInput" id="customerMobileNumberInput" value="${customer.getCustomerMobileNumber()}">
                                    <label for="customerMobileNumberInput">Mobile Number</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="customerTelephoneNumberInput" id="customerTelephoneNumberInput" value="${customer.getCustomerTelephoneNumber()}">
                                    <label for="customerTelephoneNumberInput">Telephone Number</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <legend>Sales Representative</legend>
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="fromSalesRepInput" id="fromSalesRepInput">
                                            <option value="${customer.getSalesRepID()}">${customer.getSalesRep()}</option>
                                        </select>
                                        <label for="fromSalesRepInput">From</label>
                                    </div>
                                </div>
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="chosenSalesRep" id="chosenSalesRep">
                                            <c:forEach items="${salesRepList}" var="sr" begin="0" step="1">
                                                <option value="${sr.getSalesRepID()}">${sr.getSalesRepLastName()} ${sr.getSalesRepFirstName()}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="chosenSalesRep">To</label>
                                    </div>
                                </div>
                            </div>                            
                            <c:if test="${error_msg != null}">
                                <div class="mui--text-center">
                                    <div class="text-center">
                                        <div id="error-msg">${error_msg}</div>
                                        <c:remove var="error_msg" scope="session"/>
                                    </div>
                                </div>
                            </c:if>
                            <div class="mui--text-center">
                                <input type="hidden" name="customerIDInput" id="customerIDInput" value="${customer.getCustomerID()}">
                                <button type="submit" class="mui-btn mui-btn--raised">Save Changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <br><br>
        <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">                            
                          <c:if test="${accountType eq '2' || accountType eq '1'}">
                              <a href="province.get?whatFor=addClinic&custID=${customer.getCustomerID()}">Add Clinic</a><br>
                            </c:if>
                            <a href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=<c:out value="${customer.getCustomerID()}"/>">Go to Customer Details</a>
                            <br>
                            <a href="Servlets.getCustomerServlet">Go to list of customers</a>
                            <br>
                            <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a>
                      </div>
                  </div>
                  <br>
              </div>
        </footer>
        
        <%@include file="/WEB-INF/source/footer.jsp" %>
        <script type="text/javascript" src="js/form-validation/edit-customer-validation.js"></script>
    </body>
</html>
