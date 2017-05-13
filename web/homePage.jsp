<%-- 
    Document   : homePage
    Created on : 08 16, 16, 9:12:22 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <c:set var="state" value="${sessionScope.state}"/>
        <c:set var="userName" value="${sessionScope.userName}"/>
        <c:set var="message" value="${sessionScope.message}"/>
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="suppID" value="${sessionScope.suppID}"/>
        <c:set var="restocksList" value="${requestScope.restocksList}"/>
        <c:set var="invoiceList" value="${requestScope.invoiceList}"/>
        <c:set var="productsList" value="${productsList}"/>
        
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <c:choose>
                            <c:when test="${state ne 'logged in'}">
                                <img src="img/pescii-logo.png" alt="*LOGO*" class="img img-responsive">
                                <form action="Servlets.logInServlet" method="post" class="mui-form" id="login-form">
                                    <div class="mui--appbar-height"></div>
                                    <div class="mui-textfield mui-textfield--float-label">
                                      <input type="text" name="usernameInput" id="usernameInput">
                                      <label for="usernameInput">Username</label>
                                    </div>
                                    <div class="mui-textfield mui-textfield--float-label">
                                      <input type="password" name="passwordInput" id="passwordInput">
                                      <label for="passwordInput">Password</label>
                                    </div>
                                    <c:if test="${error_login != null}">
                                        <div class="mui--text-center">
                                            <div class="text-center">
                                                <div id="error-msg">${error_login}</div>
                                                <c:remove var="error_login" scope="session"/>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="mui--text-center">
                                        <button type="submit" class="mui-btn mui-btn--raised">Login</button>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="mui--text-display3 mui--text-center">Welcome, ${userName}!</div>
                                    <div class="mui--appbar-height"></div>
                                    <div class="mui--text-display1 mui--text-center">Notice Board</div>
                                    <div class="mui-panel mui--text-center">
                                        <c:if test="${accountType eq '3' || accountType eq '2' || accountType eq '1'}">
                                            <c:if test="${invoiceList.size() eq 0}">
                                                <p>You have <b>${invoiceList.size()} Invoices</b> with deadlines within the next 7 days</p>
                                            </c:if>
                                            <c:if test="${invoiceList.size() ne 0}">
                                                <p>You have <b>${invoiceList.size()} Invoice/s</b> with deadlines within the next 7 days</p>
                                                <table class="mui-table mui-table--bordered">
                                                    <thead>
                                                        <tr>
                                                            <th>Invoice Name</th>
                                                            <th>Items Purchased</th>
                                                            <th>Customer Name</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${invoiceList}" var="inv" begin="0" step="1" varStatus="status">
                                                            <tr>
                                                                <td>
                                                                    <button class="btn btn-link show-inv-details">
                                                                        <input type="hidden" name="invoiceIDInput" class="invoiceIDInput" value="${inv.getInvoiceID()}">
                                                                        ${inv.getInvoiceName()}
                                                                    </button>
                                                                </td>
                                                                <td>
                                                                    <button class="btn btn-link show-inv-items">
                                                                        <input type="hidden" name="invoiceIDInput" class="invoiceIDInput" value="${inv.getInvoiceID()}">
                                                                        View Items
                                                                    </button>
                                                                </td>
                                                                <td>${inv.getCustomerName()}</td>
                                                                <td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${inv.getInvoiceID()}"/>">Edit</a></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${accountType == '4' || accountType == '5' || accountType == '2' || accountType == '1'}">
                                            <c:if test="${restocksList.size() eq 0}">
                                                <p>You have <b>${restocksList.size()} ROs</b> with deadlines within the next 7 days</p>
                                            </c:if>
                                            <c:if test="${restocksList.size() ne 0}">
                                                <p>You have <b>${restocksList.size()} RO/s</b> with deadlines within the next 7 days</p>
                                                <table class="mui-table mui-table--bordered">
                                                    <thead>
                                                        <tr>
                                                            <th>Restock Order Name</th>
                                                            <th>Restock Order Items</th>
                                                            <th>Date Due</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${restocksList}" var="ro" begin="0" step="1" varStatus="status">
                                                            <tr>
                                                                <td>
                                                                    <button class="btn btn-link show-ro-details">
                                                                        <input type="hidden" name="restockIDInput" class="restockIDInput" value="${ro.getRestockOrderID()}">
                                                                        ${ro.getRestockOrderName()}
                                                                    </button>
                                                                </td>
                                                                <td>
                                                                    <button class="btn btn-link show-ro-items">
                                                                        <input type="hidden" name="restockIDInput" class="restockIDInput" value="${ro.getRestockOrderID()}">
                                                                        View Items
                                                                    </button>
                                                                </td>
                                                                <td>${ro.getRODateDue()}</td>
                                                                <td><a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${accountType == '4' || accountType == '5' || accountType == '2' || accountType == '1'}"> 
                                            <c:if test="${productsList.size() eq 0}">
                                                <p>You have <b>${productsList.size()} Products</b> with low stocks.</p>
                                            </c:if>
                                            <c:if test="${productsList.size() ne 0}">
                                                <p>You have <b>${productsList.size()} Product/s</b> with low stocks</p>
                                                <table class="mui-table mui-table--bordered">
                                                    <thead>
                                                        <tr>
                                                            <th>Product Name</th>
                                                            <th>Stocks Remaining</th>
                                                            <th>Low Stock</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${productsList}" var="prod" begin="0" step="1" varStatus="status">
                                                            <tr>
                                                                <td>
                                                                    <button class="btn btn-link show-prod-details">
                                                                        <input type="hidden" name="productIDInput" class="productIDInput" value="${prod.getProductID()}">
                                                                        ${prod.getProductName()}
                                                                    </button>
                                                                </td>
                                                                <td>${prod.getStocksRemaining()}</td>
                                                                <td>${prod.getLowStock()}</td>
                                                                <c:if test="${accountType eq '4' || accountType eq '1'}">
                                                                    <td><a href="product.getDetails?forEdit=yes&prodID=<c:out value="${prod.getProductID()}"/>">EDIT</a></td>
                                                                </c:if>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:if>
                                        </c:if>
                                    </div>
                            </c:otherwise>
                        </c:choose>
                    </div>            
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/source/footer.jsp" %>
        <script type="text/javascript" src="js/form-validation/login-validation.js"></script>
        <!--Modal for invoice details-->
        <div id="invoice-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--Details of the invoice-->
                                <div class="mui-col-md-12">
                                    <div id="show-invoice"></div>
                                </div>
                                <!--Details of the invoice-->
                                <!--Buttons-->
                                <div class="mui-col-md-12 mui--text-center">
                                    <div id="edit-invoice-btn"></div>
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
        <div id="invoice-items-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--invoice items-->
                                <div class="mui-col-md-12">
                                    <table class="mui-table mui-table--bordered mui--text-center">
                                        <thead>
                                            <tr>
                                                <td>Product Name</td>
                                                <td>Quantity Purchased</td>
                                                <td>Total Cost</td>
                                            </tr>
                                        </thead>
                                        <tbody id="show-invoice-items"></tbody>
                                    </table>
                                </div>
                                <!--invoice items-->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal for invoice details-->
        <!--Modal for restock details-->
        <div id="restock-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--Details of the restock-->
                                <div class="mui-col-md-12">
                                    <div id="show-restock"></div>
                                </div>
                                <!--Details of the restock-->
                                <!--Buttons-->
                                <div class="mui-col-md-12 mui--text-center">
                                    <div id="edit-restock-btn"></div>
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
        <div id="restock-items-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--invoice items-->
                                <div class="mui-col-md-12">
                                    <table class="mui-table mui-table--bordered mui--text-center">
                                        <thead>
                                            <tr>
                                                <td>Product Name</td>
                                                <td>Quantity Purchased</td>
                                                <td>Quantity Received</td>
                                                <td>Total Cost</td>
                                            </tr>
                                        </thead>
                                        <tbody id="show-restock-items"></tbody>
                                    </table>
                                </div>
                                <!--invoice items-->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal for restock details-->
        <!--Modal for low stock details-->
        <div id="lowstock-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--Details of the lowstock-->
                                <div class="mui-col-md-12">
                                    <div id="show-lowstock"></div>
                                </div>
                                <!--Details of the lowstock-->
                                <!--Buttons-->
                                <div class="mui-col-md-12 mui--text-center">
                                    <div id="edit-lowstock-btn"></div>
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
        <!--Modal for lowstock details-->
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
        <!--Script for getting the invoice details-->
        <script>
            $(document).ready(function(){
                $('.show-inv-details').click(function(){
                    $('#show-invoice').empty();
                    $('#edit-invoice-btn').empty();
                    
                    var $invoiceIDInput = $(this).find('.invoiceIDInput').val();
                    $.getJSON('getinvoicedetails', {invoiceIDInput: $invoiceIDInput})
                        .done(function(json){
                            var $divData = "";
                            var $edtData = "";
                            for(var i=0; i<json.length; i++){
                                
//                                product information
                                $divData = $('<div class="mui--text-center"/>');
                                $divData.append('<div class="mui-col-md-12"><p><strong class="mui--text-display1 mui--divider-bottom">' + json[i].invoiceName + '</stron></p></div>');
                                $divData.append('<div class="mui-col-md-8 mui-col-md-offset-2 mui--divider-bottom"><br><p><strong>' + json[i].PRCID + '</strong><br>Customer Professional Regulation Comission ID</p></div>');
                                $divData.append('<div class="mui-col-md-12 mui--divider-bottom"><br><p><strong>' + json[i].customerName + '</strong><br>Customer Name</p></div>');
                                $divData.append('<div class="mui-col-md-12 mui--divider-bottom"><br><p><strong>' + json[i].clinicName + '</strong><br>Clinic</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].provinceName + '</strong><br>Province</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].invoiceDate + '</strong><br>Invoice Date</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].deliveryDate + '</strong><br>Delivery Date</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + nullConverter(json[i].dateDelivered) + '</strong><br>Date Delivered</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].termsOfPayment + '</strong><br>Terms of Payment</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].amountDue) + '</strong><br>Amount Due</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].discount + '</strong><br>Discount</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].amountPaid) + '</strong><br>Amount Paid</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + nullConverter(json[i].dateClosed) + '</strong><br>Date Closed</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].statusName + '</strong><br>Invoice Status</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].overdueFee) + '</strong><br>Overdue Fee</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].salesRepName + '</strong><br>Sales Representative</p></div>');
                                $('#show-invoice').append($divData);
//                                product information

//                                edit button
                                $edtData = $('<span>');
                                if(!$.trim(json[i]).lastEdittedBy){
                                    $edtData.append('<div class="mui-col-md-12"><br><p><strong>' + nullConverter(json[i].lastEdittedBy) + '</strong><br>Last Edited By</p></div>');
                                }
                                $edtData.append('<div class="mui-col-md-6 mui-col-md-offset-3"><br><a class="btn btn-link" href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=' + json[i].invoiceID + '">Edit</a><div>');
                                $('#edit-invoice-btn').append($edtData);
//                                edit button
                            }
                        
                        });
                    $('#invoice-modal').modal('show');
                });                
            });
        </script>
        <script>
            $(document).ready(function(){
                $('.show-inv-items').click(function(){
                    $('#show-invoice-items').empty();
                    var $invoiceIDInput = $(this).find('.invoiceIDInput').val();
                    $.getJSON('getinvoiceitems', {invoiceIDInput: $invoiceIDInput})
                        .done(function(json){
                            var $tableData = "";
                            for(var i=0; i<json.length; i++){
                                $tableData = $('<tr/>');
                                $tableData.append('<td>' + json[i].productName + '</td>');
                                $tableData.append('<td>' + commaSeparateNumber(json[i].quantityPurchased) + '</td>');
                                $tableData.append('<td>' + commaSeparateNumber(json[i].totalCost) + '</td>');
                                $('#show-invoice-items').append($tableData);
                            }
                    });
                    $('#invoice-items-modal').modal('show');
                });
            });
        </script>
        <!--Script for getting the invoice details-->
        <!--Script for getting the restock details-->
        <script>
            $(document).ready(function(){
                $('.show-ro-details').click(function(){
                    $('#show-restock').empty();
                    $('#edit-restock-btn').empty();
                    
                    var $restockIDInput = $(this).find('.restockIDInput').val();
                    $.getJSON('getrestockorderdetails', {restockIDInput: $restockIDInput})
                        .done(function(json){
                            var $divData = "";
                            var $edtData = "";
                            for(var i=0; i<json.length; i++){
                                
//                                product information
                                $divData = $('<div class="mui--text-center"/>');
                                $divData.append('<div class="mui-col-md-12"><p><strong class="mui--text-display1 mui--divider-bottom">' + json[i].ROName + '</stron></p></div>');
                                $divData.append('<div class="mui-col-md-8 mui-col-md-offset-2 mui--divider-bottom"><br><p><strong>' + json[i].purpose + '</strong><br>Purpose</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].supplierName + '</strong><br>Supplier Name</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].statusName + '</strong><br>Status</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].restockPrice) + '</strong><br>Total Amount</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + commaSeparateNumber(json[i].amountPaid) + '</strong><br>Amount Paid</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].discount + '</strong><br>Discount</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].RODateDue + '</strong><br>Date Due</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + nullConverter(json[i].RODateDelivered) + '</strong><br>Date Received</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].dateCreated + '</strong><br>Date Created</p></div>');
                                $('#show-restock').append($divData);
//                                product information

//                                edit button
                                $edtData = $('<span>');
                                if(!$.trim(json[i]).lastEdittedBy){
                                    $edtData.append('<div class="mui-col-md-12"><br><p><strong>' + nullConverter(json[i].lastEdittedBy) + '</strong><br>Last Edited By</p></div>');
                                }
                                $edtData.append('<div class="mui-col-md-6 mui-col-md-offset-3"><br>  <a href="restockOrder.getDetails?editRestock=yes&restockID=' + json[i].restockOrderID +'">Edit</a><div>');
                                $('#edit-restock-btn').append($edtData);
//                                edit button
                            }
                        
                        });
                    $('#restock-modal').modal('show');
                });
            });
        </script>
        <script>
            $(document).ready(function(){
                $('.show-ro-items').click(function(){
                    $('#show-restock-items').empty();
                    var $restockIDInput = $(this).find('.restockIDInput').val();
                    $.getJSON('getrestockorderitems', {restockIDInput: $restockIDInput})
                        .done(function(json){
                            var $tableData = "";
                            for(var i=0; i<json.length; i++){
                                $tableData = $('<tr/>');
                                $tableData.append('<td>' + json[i].productName + '</td>');
                                $tableData.append('<td>' + json[i].quantityPurchased + '</td>');
                                $tableData.append('<td>' + json[i].quantityReceived + '</td>');
                                $tableData.append('<td>' + json[i].totalCost + '</td>');
                                $('#show-restock-items').append($tableData);
                            }
                    });
                    $('#restock-items-modal').modal('show');
                });
                $.noConflict();
            });
        </script>
        <!--Script for getting the restock details-->
        <!--Script for getting the low stock details-->
        <!--script for getting the details for the modal using json-->
        <script>
            $(document).ready(function(){
                $('.show-prod-details').click(function(){
                    $('#show-products').empty();
                    $('#edit-btn').empty();
                    
                    var $productIDInput = $(this).find('.productIDInput').val();
                    $.getJSON('getproductdetails', {productIDInput: $productIDInput})
                        .done(function(json){
                            var $divData = "";
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
                                $divData.append('<div class="mui-col-md-6"><br><p><strong>' + json[i].lastEdittedBy + '</strong><br>Last Edited By</p></div>');
                                $('#show-products').append($divData);
//                                product information
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
        <!--Script for getting the lowstock details-->
    </body>
</html>
