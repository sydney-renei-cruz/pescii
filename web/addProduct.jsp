<%-- 
    Document   : addProduct
    Created on : 08 20, 16, 1:15:17 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    ArrayList<productClassBean> prodClassList = (ArrayList<productClassBean>)request.getAttribute("prodClassList");
    ArrayList<supplierBean> suppList = (ArrayList<supplierBean>)request.getAttribute("suppliersList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        <c:set var="suppList" value="${requestScope.suppliersList}"/>
        
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">ADD PRODUCT</legend>
                        <c:if test="${success_msg != null}">
                            <div class="mui-col-md-12 mui--text-center">
                                <div id="success-msg">${success_msg}</div>
                                <c:remove var="success_msg" scope="session"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="mui-col-md-8 mui-col-md-offset-2">
                        <form action="Servlets.addProductServlet" method="post" class="mui-form" id="add-product-form">
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="productNameInput" id="productNameInput">
                                    <label for="productNameInput">Product Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <textarea type="text" name="productDescriptionInput" id="productDescriptionInput"></textarea>
                                  <label for="passwordInput">Product Description</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="productPriceInput" id="productPriceInput">
                                    <label for="productPriceInput">Product Price</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="restockPriceInput" id="restockPriceInput">
                                    <label for="restockPriceInput">Restock Price</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="lowStockInput" id="lowStockInput">
                                    <label for="lowStockInput">Low Stock Level</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text"  name="brandInput" id="brandInput">
                                    <label for="brandInput">Brand</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" name="colorInput" id="coloInput">
                                    <label for="colorInput">Color</label>
                                </div>
                            </div>
                            
                            <div class="mui-col-md-12">
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="productClassInput" id="productClassInput">
                                            <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                                                    <option value="${pro.getProductClassID()}">${pro.getProductClassName()}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="productClassInput">Product Class</label>
                                    </div>
                                </div>
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="supplierInput" id="supplierInput">
                                            <c:forEach items="${suppliersList}" var="sup" begin="0" step="1">
                                                    <option value="${sup.getSupplierID()}">${sup.getSupplierName()}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="supplierInput">Supplier</label>
                                    </div>
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
        <script type="text/javascript" src="js/form-validation/add-product-validation.js"></script>
    </body>
</html>
