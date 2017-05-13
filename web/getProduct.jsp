<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/ajax-modal/product-details.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="productsList" value="${requestScope.productsList}"/>
        <c:set var="listSize" value="${productsList.size()}"/>
        <c:set var="forInvoice" value="${requestScope.forInvoice}"/>
        <c:set var="forRestock" value="${requestScope.forRestock}"/>
        <c:set var="forLowstock" value="${requestScope.forLowstock}"/>
        <c:set var="cartType" value="${sessionScope.cartType}"/>
        
        
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <c:choose>
                            <c:when test="${cartType eq 'invoice'}">
                                <legend class="mui--text-center mui--text-display3">SELECT PRODUCTS FOR INVOICE</legend>
                            </c:when>
                            <c:when test="${cartType eq 'restock'}">
                                <legend class="mui--text-center mui--text-display3">SELECT PRODUCTS FOR RESTOCK</legend>
                            </c:when>
                            <c:when test="${cartType eq 'lowstockLevel'}">
                                <legend class="mui--text-center mui--text-display3">SELECT PRODUCTS WITH LOW STOCK LEVEL</legend>
                            </c:when>
                            <c:otherwise>
                                <legend class="mui--text-center mui--text-display3">PRODUCTS</legend>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:choose>
                        <c:when test="${cartType eq 'invoice' || cartType eq 'restock'}">
                            <div class="mui-col-md-6 mui-col-md-offset-3">
                        </c:when>
                        <c:when test="${cartType eq 'restock'}">
                            <div class="mui-col-md-6 mui-col-md-offset-1">
                        </c:when>
                        <c:otherwise>
                            <div class="mui-col-md-6 mui-col-md-offset-3">
                        </c:otherwise>
                    </c:choose>
                        <table class="mui-table mui--text-center" id="product-table">
                            <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>Product Price</th>
                                    <th>Restock Price</th>
                                    <th>Stocks Remaining</th>
                                    <th>Low Stock</th>
                                    <th>Product Class</th>
                                    <c:if test="${forInvoice eq 'yes'}">
                                        <th></th>
                                    </c:if>
                                    <c:if test="${forRestock eq 'yes'}">
                                        <th></th>
                                    </c:if>
                                    <c:if test="${accountType != null && (accountType == '1' || accountType == '4' || accountType == '5')}">
                                        <th></th>
                                    </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${productsList}" var="prod" begin="0" step="1" varStatus="status">
                                    <tr>
                                        <td>
                                            <button class="btn btn-link show-detail">
                                                <input type="hidden" name="productIDInput" class="productIDInput" value="${prod.getProductID()}">
                                                ${prod.getProductName()}
                                            </button>
                                        </td>
                                        <td>${prod.getProductPrice()}</td>
                                        <td>${prod.getRestockPrice()}</td>
                                        <td>${prod.getStocksRemaining()}</td>
                                        <td>${prod.getLowStock()}</td>
                                        <td>${prod.getProductClassName()}</td>
                                        <c:if test="${cartType eq 'invoice'}">
                                            <td>
                                                <a href="addToCart?prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>&prodPrice=<c:out value="${prod.getProductPrice()}"/>">
                                                    Add to Cart
                                                </a>
                                            </td>
                                        </c:if>
                                        <c:if test="${cartType eq 'lowstockLevel'}">
                                            <td><a href="addToProdCart?prodName=<c:out value="${prod.getProductName()}"/>&prodID=<c:out value="${prod.getProductID()}"/>&prodPrice=<c:out value="${prod.getRestockPrice()}"/>&suppID=<c:out value="${prod.getSupplierID()}"/>&suppName=<c:out value="${prod.getSupplierName()}"/>&brand=<c:out value="${prod.getBrand()}"/>&prodClass=<c:out value="${prod.getProductClassName()}"/>&color=<c:out value="${prod.getColor()}"/>&lowStock=<c:out value="${prod.getLowStock()}"/>">Select</a></td>
                                        </c:if>
                                        <c:if test="${cartType eq 'restock'}">
                                          <td><a href="product.getDetails?forRestock=yes&prodID=<c:out value="${prod.getProductID()}"/>">ADD to RO</a></td>
                                        </c:if> 
                                        <c:if test="${state eq 'logged in' && (accountType == '1' || accountType == '4')}">
                                            <td><a href="product.getDetails?forEdit=yes&prodID=<c:out value="${prod.getProductID()}"/>">EDIT</a></td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">
                            <c:choose>
                                <c:when test="${cartType eq 'invoice'}">
                                    <a href="viewCart.jsp">View Cart</a><br>
                                    <a href="invoice.add?cancel=yes">Cancel Invoice</a><br>
                                    <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=invoice">Search Product</a>
                                </c:when>

                                <c:when test="${cartType eq 'restock'}">
                                    <a href="viewROCart.jsp">View Cart<br>
                                    <a href="supplier.get?viewSupp=yes&forRestock=yes">Change Supplier</a><br>
                                    <a href="Servlets.createRestockOrderServlet?cancel=yes">Cancel Restock Order</a><br>
                                    <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=restock">Search Product</a>
                                </c:when>

                                <c:when test="${cartType eq 'lowstockLevel'}">
                                    <a href="viewProdCart.jsp">View selected Products</a><br>
                                    <a href="product.setLowstockLevel?cancel=yes">Cancel Low Stock Setting</a><br>
                                    <a href="product.getProductClass?search=yes&searchWhat=prod&forOther=lowstockLevel">Search Product</a>
                                </c:when>        

                                <c:when test="${forInvoice ne 'yes' && forRestock ne 'yes' && forLowstock ne 'yes'}">
                                    <a href="product.getProductClass?search=yes&searchWhat=prod">Search Product</a>
                                </c:when>
                            </c:choose>
                      </div>  
                  </div>
            </div>
        </footer>        
        <!--Modal for product details-->
        <div id="product-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">

                        <div class="mui-container">
                            <div class="mui-row">
                                <!--Details of the product-->
                                <div class="mui-col-md-12">
                                    <div id="show-products"></div>
                                </div>
                                <!--Details of the product-->
                                <!--Buttons-->
                                <div class="mui-col-md-12 mui--text-center">
                                    <c:if test="${cartType eq 'invoice'}">
                                        <div id="invoice-btns"></div>
                                    </c:if>
                                    <c:if test="${cartType eq 'restock'}">
                                        <div id="restock-btns"></div>
                                    </c:if>
                                    <c:if test="${cartType eq 'lowstockLevel'}">
                                        <div id="lowstock-btns"></div>
                                    </c:if>
                                    <c:if test="${state eq 'logged in' && (accountType == '1' || accountType == '4')}">
                                        <div id="edit-btn"></div>
                                    </c:if>
                                </div>
                                <!--Buttons-->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal for product details-->
        <%@include file="/WEB-INF/source/footer.jsp" %>
        <!--script for getting the details for the modal using json-->
        <script>
            $(document).ready(function(){
                $('.show-detail').click(function(){
                    $('#show-products').empty();
                    $('#invoice-btns').empty();
                    $('#restock-btns').empty();
                    $('#lowstock-btns').empty();
                    $('#edit-btn').empty();
                    
                    var $productIDInput = $(this).find('.productIDInput').val();
                    $.getJSON('getproductdetails', {productIDInput: $productIDInput})
                        .done(function(json){
                            var $divData = "";
                            var $invData = "";
                            var $rstData = "";
                            var $lslData = "";
                            var $edtData = "";
                            for(var i=0; i<json.length; i++){
                                
//                                product information
                                $divData = $('<div class="mui--text-center"/>');
                                $divData.append('<div class="mui-col-md-12"><p><strong class="mui--text-display1 mui--divider-bottom">' + json[i].productName + '</stron></p></div>');
                                $divData.append('<div class="mui-col-md-6 mui-col-md-offset-3 mui--divider-bottom"><br><p><strong>' + json[i].productDescription + '</strong><br>Product Description</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].supplierName + '</strong><br>Supplier</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].brand + '</strong><br>Brand</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].productPrice) + '</strong><br>Product Price</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].restockPrice) + '</strong><br>Restock Price</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].stocksRemaining) + '</strong><br>Stocks Remaining</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].lowStock) + '</strong><br>Low Stock Level</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].productClassName + '</strong><br>Product Class</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].color + '</strong><br>Color</p></div>');
                                $divData.append('<div class="mui-col-md-6"><br><p><strong>' + json[i].dateCreated + '</strong><br>Date Created</p></div>');
                                $divData.append('<div class="mui-col-md-6"><br><p><strong>' + nullConverter(json[i].lastEdittedBy) + '</strong><br>Last Edited By</p></div>');
                                $('#show-products').append($divData);
//                                product information
//                                invoice buttons
                                $invData = $('<span/>');
                                $invData.append('<div class="mui-col-md-6 mui-col-md-offset-3 mui--divider-top"><br><a href="addToCart?prodName=' + json[i].productName + '&prodID=' + json[i].productID + '">Add to Invoice</a><div>');
                                $('#invoice-btns').append($invData);
//                                invoice buttons

//                                restock buttons
                                $rstData = $('<span/>');
                                $rstData.append('<a class="mui-col-md-6 mui-col-md-offset-3" href="product.getDetails?forRestock=yes&prodID=' + json[i].productID + '"/>">ADD to RO</a>');
                                $('#restock-btns').append($rstData);
//                                restock buttons

//                                low stock level buttons
                                $lslData = $('<span/>');
                                $lslData.append('<a class="mui-col-md-6 mui-col-md-offset-3" href="ddToProdCart?prodName=' + json[i].productName + '&prodID=' + json[i].productID + '&prodPrice=' + json[i].restockPrice + '&suppID=' + json[i].supplierID + '&suppName=' + json[i].supplierName + '&brand=' + json[i].brand + '&prodClass=' + json[i].productClassName + '&color=' + json[i].color + '&lowStock=' + json[i].lowStock + '">Select</a>');
                                $('#lowstock-btns').append($lslData);
//                                low stock level buttons

//                                edit button
                                $edtData = $('<span>');
                                $edtData.append('<div class="mui-col-md-6 mui-col-md-offset-3"><br><a href="product.getDetails?forEdit=yes&prodID=' + json[i].productID + '">Edit Product</a><div>');
                                $('#edit-btn').append($edtData);
//                                edit button
                            }
                        
                        });
                    $('#product-modal').modal('show');
                });
            });
        </script>
        <!--script for getting the details for the modal using json-->
        
        <!--script for converting the tables into datatables-->
        <c:if test="${accountType != null && forInvoice ne 'yes' && forRestock ne 'yes'}">
            <script>
                $(function(){
                  $("#product-table").dataTable({
                            "columnDefs": [
                                {"orderable": false, "targets": 6}
                            ]
                    });
                    $.noConflict();
                });
            </script>
        </c:if>
        <c:if test="${forInvoice eq 'yes' || forRestock eq 'yes'}">
            <script>
                $(function(){
                  $("#product-table").dataTable({
                            "columnDefs": [
                                {"orderable": false, "targets": 6},
                                {"orderable": false, "targets": 7}
                            ]
                    });
                    $.noConflict();
                });
            </script>
        </c:if>
        <c:if test="${accountType == null}">
            <script>
                $(function(){
                  $("#product-table").dataTable();
                  $.noConflict();
                });
            </script>
        </c:if>
        <!--script for converting the tables into datatables-->
    </body>
</html>
