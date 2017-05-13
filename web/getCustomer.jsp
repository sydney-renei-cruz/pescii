<%-- 
    Document   : getCustomer
    Created on : 08 20, 16, 5:21:01 PM
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
        <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        <c:set var="customersList" value="${requestScope.customersList}"/>
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="addInvoice" value="${requestScope.addInvoice}"/>
        <c:set var="listSize" value="${customersList.size()}"/>
        <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <c:choose>
                            <c:when test="${addInvoice ne 'yes'}">
                                <legend class="mui--text-center mui--text-display3">CUSTOMERS</legend>
                            </c:when>
                            <c:otherwise>
                                <legend class="mui--text-center mui--text-display3">SELECT THE CUSTOMER MAKING THE INVOICE.</legend>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <c:if test="${listSize > 0}">
                            <table class="mui-table mui--text-center" id="customer-table">
                                <thead>
                                    <tr>
                                        <th>PRC ID</th>
                                        <th>Name</th>
                                        <th>Clinic</th>
                                        <th>Invoice</th>
                                        <c:if test="${accountType eq '2' || accountType eq '1'}">
                                            <th></th>
                                        </c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${customersList}" var="cust" begin="0" step="1" varStatus="status">
                                        <tr>
                                                <td>${cust.getPRCID()}</td>
                                                <td>
                                                    <button class="btn btn-link show-customer-details">
                                                        <input type="hidden" name="customerIDInput" class="customerIDInput" value="${cust.getCustomerID()}">
                                                        ${cust.getCustomerFirstName()} ${cust.getCustomerLastName()}
                                                    </button>
                                                </td>
                                                <td>
                                                    <button class="btn btn-link show-clinic-details">
                                                        <input type="hidden" name="customerIDInput" class="customerIDInput" value="${cust.getCustomerID()}">
                                                        View Clinic
                                                    </button>
                                                </td>
                                                <td>
                                                    <button class="btn btn-link show-invoice-details">
                                                        <input type="hidden" name="customerIDInput" class="customerIDInput" value="${cust.getCustomerID()}">
                                                        View Invoice
                                                    </button>
                                                </td>
                                                <c:if test="${accountType eq '2' || accountType eq '1'}">
                                                    <td><a href="Servlets.viewCustomerDetailsServlet?editWhat=cust&forEdit=yes&custID=<c:out value="${cust.getCustomerID()}"/>">Edit</a></td>
                                                </c:if>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <footer id="footer">
            <div class="mui-container-fluid">
                  <div class="mui-row">
                      <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">
                            <c:if test="${addInvoice eq 'yes'}">
                                <a href="viewCart.jsp">View Cart</a>
                                <br><br>
                                <a href="invoice.add?cancel=yes">Cancel Invoice</a>
                                <br><br>
                            </c:if>
                            <a href="salesrep.get?whatFor=searchCustomer">Search Customers</a>
                      </div>  
                  </div>
            </div>
        </footer> 
        <br><br>
        <!--Modal for customer details-->
        <div id="customer-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--Details of the customer-->
                                <div class="mui-col-md-12">
                                    <div id="show-customer"></div>
                                </div>
                                <!--Details of the customer-->
                                <!--Buttons-->
                                <div class="mui-col-md-12 mui--text-center">
                                    <div id="edit-customer-btn"></div>
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
        <!--Modal for customer details-->
        <!--Modal for clinic details-->
        <div id="clinic-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--details of the clinic-->
                                <div class="mui-col-md-12" id="clinic-part">
                                    <table class="mui-table mui-table--bordered mui--text-center">
                                        <thead>
                                            <tr>
                                                <th>Clinic Name</th>
                                                <th>Clinic Address</th>
                                                <th>Province</th>
                                                <th>Phone Number</th>
                                                <c:if test="${accountType eq '2' || accountType eq '1'}">
                                                    <th></th>
                                                </c:if>
                                            </tr>
                                        </thead>
                                        <tbody id="show-clinic"></tbody>
                                    </table>
                                    <div id="add-clinic-btn"></div>
                                    <div id="no-clinic-record"></div>
                                </div>
                                <!--details of the clinic-->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal for clinic details-->
        <!--Modal for customer invoice details-->
        <div id="invoice-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="mui-container">
                            <div class="mui-row">
                                <!--details of the invoice-->
                                <div class="mui-col-md-12">
                                    <table class="mui-table mui-table--bordered mui--text-center">
                                        <thead>
                                            <tr>
                                                <th>Invoice Name</th>
                                                <th>Clinic Name</th>
                                                <th>Payment Due Date</th>
                                                <th>Date Paid</th>
                                                <th>Status</th>
                                                <c:if test="${accountType eq '3' || accountType eq '1'}">
                                                <th></th>
                                                </c:if>
                                            </tr>
                                        </thead>
                                        <tbody id="show-customer-invoice"></tbody>
                                    </table>
                                    <div id="no-invoice-record"></div>
                                </div>
                                <!--details of the invoice-->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="invoice-items-modal" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                </div>
            </div>
        </div>
        <!--Modal for customer invoice details-->
        <!--Script for getting the customer details-->
        <script>
            $(document).ready(function(){
                $('.show-customer-details').click(function(){
                    $('#show-customer').empty();
                    $('#edit-customer-btn').empty();
                    
                    var $customerIDInput = $(this).find('.customerIDInput').val();
                    $.getJSON('getcustomerdetails', {customerIDInput: $customerIDInput})
                        .done(function(json){
                            var $divData = "";
                            var $edtData = "";
                            for(var i=0; i<json.length; i++){
                                
//                                product information
                                $divData = $('<div class="mui--text-center"/>');
                                $divData.append('<div class="mui-col-md-12"><p><strong class="mui--text-display1 mui--divider-bottom">' + json[i].customerFirstName + ' ' + json[i].customerLastName + '</stron></p></div>');
                                $divData.append('<div class="mui-col-md-8 mui-col-md-offset-2 mui--divider-bottom"><br><p><strong>' + json[i].PRCID + '</strong><br>Customer Professional Regulation Comission ID</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].customerMobileNumber + '</strong><br>Mobile Number</p></div>');
                                $divData.append('<div class="mui-col-md-6 mui--divider-bottom"><br><p><strong>' + json[i].customerTelephoneNumber + '</strong><br>Telephone Number</p></div>');
                                $divData.append('<div class="mui-col-md-8 mui-col-md-offset-2 mui--divider-bottom"><br><p><strong>' + json[i].salesRep + '</strong><br>Sales Representative</p></div>');
                                $('#show-customer').append($divData);
//                                product information

//                                edit button
                                $edtData = $('<span>');
                                if($.trim(json[i].lastEdittedBy)){
                                    $edtData.append('<div class="mui-col-md-12"><br><p><strong>' + nullConverter(json[i].lastEdittedBy) + '</strong><br>Last Edited By</p></div>');
                                }
                                $edtData.append('<div class="mui-col-md-6 mui-col-md-offset-3"><br><a class="btn btn-link" href="Servlets.viewCustomerDetailsServlet?viewDetails=yes&custID=' + json[i].customerID + '">Add Invoice</a><div>');
                                $edtData.append('<div class="mui-col-md-6 mui-col-md-offset-3"><br><a class="btn btn-link" href="Servlets.viewCustomerDetailsServlet?editWhat=cust&forEdit=yes&custID=' + json[i].customerID + '">Edit</a><div>');
                                $('#edit-customer-btn').append($edtData);
//                                edit button
                            }
                        
                        });
                    $('#customer-modal').modal('show');
                });                
            });
        </script>
        <!--Script for getting the customer details-->
        <!--Script for getting the clinic details-->
        <script>
            $(document).ready(function(){
                $('.show-clinic-details').click(function(){
                    $('#show-clinic').empty();
                    $('#add-clinic-btn').empty();
                    var $customerIDInput = $(this).find('.customerIDInput').val();
                    $.getJSON('getcustomerclinicdetails', {customerIDInput: $customerIDInput})
                        .done(function(json){
                            var $tableData = "";
                            var $btnData = "";
                            if(json.length > 0){
                                $('#no-clinic-record').empty();
                                for(var i=0; i<json.length; i++){

                                        $tableData = $('<tr/>');
                                        $tableData.append('<td>' + json[i].clinicName + '</td>');
                                        $tableData.append('<td>' + json[i].clinicAddress + '</td>');
                                        $tableData.append('<td>' + json[i].provinceName + '</td>');
                                        $tableData.append('<td>' + json[i].clinicPhoneNumber + '</td>');
                                    <c:if test="${accountType eq '2' || accountType eq '1'}">
                                        $tableData.append('<td><a href="customer.getClinic?clinID=' + json[i].clinicID + '">Edit</a></td>');
                                    </c:if>
                                        $('#show-clinic').append($tableData);
                                    
                                    <c:if test="${accountType eq '2' || accountType eq '1'}">
                                        $btnData = $('<span/>');
                                        $btnData.append('<div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center"><a href="province.get?whatFor=addClinic&custID=' + json[i].customerID + '">Add Clinic</a></div>');
                                        $('#add-clinic-btn').append($btnData);
                                    </c:if>
                                }
                            }else{
                                $('#no-clinic-record').empty();
                                $('#no-clinic-record').append('<div class="mui-col-md-12 mui--text-center"> No Records Found </div>');
                            }
                    });
                    $('#clinic-modal').modal('show');
                });
            });
        </script>
        <!--Script for getting the clinic details-->
        <!--Script for getting the invoice details-->
        <script>
            $(document).ready(function(){
                $('.show-invoice-details').click(function(){
                    $('#show-customer-invoice').empty();
                    var $customerIDInput = $(this).find('.customerIDInput').val();
                    $.getJSON('getcustomerinvoicedetails', {customerIDInput: $customerIDInput})
                        .done(function(json){
                            var $tableData = "";
                            if(json.length > 0){
                                $('#no-invoice-record').empty();
                                for(var i=0; i<json.length; i++){

                                        $tableData = $('<tr/>');
                                        $tableData.append('<td><a class="btn btn-link show-inv-items" data-toggle="modal" href="getcustomerinvoiceitems?invoiceIDInput=' + json[i].invoiceID + '" data-target="#invoice-items-modal"><input type="hidden" name="invoiceIDInput" class="invoiceIDInput" value="' + json[i].invoiceID + '">' + json[i].invoiceName + '</a></td>');
                                        $tableData.append('<td>' + json[i].clinicName + '</td>');
                                        $tableData.append('<td>' + json[i].paymentDueDate + '</td>');
                                        $tableData.append('<td>' + nullConverter(json[i].datePaid) + '</td>');
                                        $tableData.append('<td>' + json[i].statusName + '</td>');
                                        <c:if test="${accountType eq '3' || accountType eq '1'}">
                                        $tableData.append('<td><a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=' + json[i].invoiceID + '">Edit</a></td>');
                                        </c:if>
                                        $('#show-customer-invoice').append($tableData);

                                }
                            }else{
                                $('#no-invoice-record').empty();
                                $('#no-invoice-record').append('<div class="mui-col-md-12 mui--text-center"> No Records Found </div>');
                            }
                    });
                    $('#invoice-modal').modal('show');
                    console.log("shown invoice modal");
                });
            });
        </script>
        
        <!--Script for getting the invoice details-->
        <%@include file="/WEB-INF/source/footer.jsp" %>
        
        <!--script for converting the tables into datatables-->
        <c:choose>
            <c:when test="${accountType eq '2' || accountType eq '1'}">
                <script>
                    $(function(){
                      $("#customer-table").dataTable({
                                "columnDefs": [
                                    {"orderable": false, "targets": 4}
                                ]
                        });
                        $.noConflict();
                    });
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    $(function(){
                      $("#customer-table").dataTable();
                        $.noConflict();
                    });
                </script>
            </c:otherwise>
        </c:choose>
        <!--script for converting the tables into datatables-->
        <script>
            $(document).ready(function(){
                $('#invoice-items-modal').on('hidden.bs.modal', function(){
                    $(this).removeData();
                    console.log("hidden");
                });
            });
            
        </script>
    </body>
</html>
