<%-- 
    Document   : editProduct
    Created on : 08 20, 16, 5:25:45 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    productBean product = (productBean)request.getAttribute("pbean");
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
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">EDIT PRODUCT</legend>
                        <div class="mui-col-md-12 mui--text-center">
                            <c:if test="${success_msg != null}">
                                    <div id="success-msg">${success_msg}</div>
                                    <c:remove var="success_msg" scope="session"/>
                            </c:if>
                        </div>
                    </div>
                    <div class="mui-col-md-8 mui-col-md-offset-2">
                        <form action="product.edit" method="post" class="mui-form" id="edit-product-form">
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" value="${product.getProductName()}" name="productNameInput" id="productNameInput">
                                    <label for="productNameInput">Product Name</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <textarea type="text" name="productDescriptionInput" id="productDescriptionInput">${product.getProductDescription()}</textarea>
                                  <label for="passwordInput">Product Description</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" value="${product.getProductPrice()}" name="productPriceInput" id="productPriceInput">
                                    <label for="productPriceInput">Product Price</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" value="${product.getRestockPrice()}" name="restockPriceInput" id="restockPriceInput">
                                    <label for="restockPriceInput">Restock Price</label>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" value="${product.getLowStock()}" name="lowStockInput" id="lowStockInput">
                                    <label for="lowStockInput">Low Stock Level</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" value="${product.getBrand()}" name="brandInput" id="brandInput">
                                    <label for="brandInput">Brand</label>
                                </div>
                            </div>
                            <div class="mui-col-md-6">
                                <div class="mui-textfield mui-textfield--float-label">
                                    <input type="text" value="${product.getColor()}" name="colorInput" id="coloInput">
                                    <label for="colorInput">Color</label>
                                </div>
                            </div>
                            
                            <div class="mui-col-md-12">
                                <legend>Product Class</legend>
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="fromProductClassInput" id="fromProductClassInput">
                                            <option value="${product.getProductClassID()}">${product.getProductClassName()}</option>
                                        </select>
                                        <label for="fromProductClassInput">From</label>
                                    </div>
                                </div>
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="productClassInput" id="productClassInput">
                                            <c:forEach items="${prodClassList}" var="pro" begin="0" step="1">
                                                    <option value="${pro.getProductClassID()}">${pro.getProductClassName()}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="productClassInput">To</label>
                                    </div>
                                </div>
                            </div>
                            <div class="mui-col-md-12">
                                <legend>Supplier</legend>
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="fromSupplierInput" id="fromSupplierInput">
                                            <option value="${product.getSupplierID()}">${product.getSupplierName()}</option>
                                        </select>
                                        <label for="fromSupplierInput">From</label>
                                    </div>
                                </div>
                                <div class="mui-col-md-6">
                                    <div class="mui-select">
                                        <select name="supplierInput">
                                            <c:forEach items="${suppliersList}" var="suppliersList" begin="0" step="1">
                                                <option value="${suppliersList.getSupplierID()}">${suppliersList.getSupplierName()}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="supplierInput">To</label>
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
                                <input type="hidden" name="productIDInput" id="productIDInput" value="${product.getProductID()}">
                                <button type="submit" class="mui-btn mui-btn--raised">Save Changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>  
        <div class="mui--appbar-height"></div>              
        <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">
                            <a href="product.getDetails?prodID=<c:out value="${product.getProductID()}"/>">Return to Product details</a>
                            <br>
                            <a href="Servlets.getProductServlet">Go to Products list</a>
                            <br>
                            <a href="product.getProductClass?search=yes&searchWhat=prod">Custom View Product</a>
                      </div>
                  </div>
                  <br>
              </div>
        </footer>
        <%@include file="/WEB-INF/source/footer.jsp" %>
        <script type="text/javascript" src="js/form-validation/edit-product-validation.js"></script>

    </body>
</html>

