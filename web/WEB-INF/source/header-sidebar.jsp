<%-- 
    Document   : header-sidebar
    Created on : 04 27, 17, 2:43:41 PM
    Author     : Sydney Cruz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <%@include file="/WEB-INF/source/source-import.jsp" %>
        <div id="sidedrawer" class="mui--no-user-select">
          <!-- Side drawer content goes here -->
            <div id="sidedrawer-brand" class="mui--appbar-line-height">
                <c:choose>
                    <c:when test="${accountType == '1'}">
                        <a href="notif.get?forWhat=both">
                    </c:when>
                    <c:when test="${accountType == '3'}">
                        <a href="notif.get?forWhat=invoice">
                    </c:when>
                    <c:when test="${accountType == '4' || accountType == '5'}">
                        <a href="notif.get?forWhat=restock">
                    </c:when>
                    <c:otherwise>
                        <a href="notif.get">
                    </c:otherwise>
                </c:choose>
                
                    <span class="mui--text-title">P.E.S.C.I.I.</span>
                </a>
            </div>
            <div class="mui-divider"></div>
            <ul>
                <li>
                    <c:if test="${state ne 'logged in'}">
                        <a href="notif.get" id="spec-appbar-login"><strong>Log in</strong></a>
                    </c:if>
                    <c:if test="${state eq 'logged in'}">
                    <strong>My Account</strong>
                        <ul>
                            <li><a href="Servlets.logoutServlet">Log out</a></li>  
                            <li><a href="account.get">Edit Account</a></li>
                        </ul>
                    </c:if>
                </li>
                <c:if test="${state ne 'logged in'}">
                    <li>
                        <strong>Product</strong>
                        <ul>
                            <li><a href="Servlets.getProductServlet">View Products</a></li>
                            <li><a href="product.getProductClass?search=yes&searchWhat=prod">Custom View Product</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${state eq 'logged in'}">
                    <c:if test="${accountType eq '1' || accountType eq '2'}">
                                <li>
                                    <strong>Customer</strong>
                                    <ul>
                                        <li><a href="salesrep.get?whatFor=addCustomer">Add Customer</a></li>
                                        <li><a href="Servlets.getCustomerServlet">Edit Customers</a></li>
                                        <li><a href="Servlets.getCustomerServlet">View All Customers</a></li>
                                        <li><a href="customer.getClinic">View All Clinics</a></li>
                                        <li><a href="salesrep.get?whatFor=searchCustomer">Search Customers</a></li>
                                    </ul>
                                </li>
                            </c:if>
                    <c:if test="${accountType eq '3' || accountType eq '6'}">
                                <li>
                                    <strong>Customer</strong>
                                    <ul>
                                        <li><a href="Servlets.getCustomerServlet">View All Customers</a></li>
                                        <li><a href="customer.getClinic">View All Clinics</a></li>
                                        <li><a href="salesrep.get?whatFor=searchCustomer">Search Customers</a></li>
                                    </ul>
                                </li>
                            </c:if>            
                    <c:if test="${accountType eq '1' || accountType eq '4'}">
                                <li>
                                    <strong>Supplier</strong>
                                    <ul>
                                        <li><a href="product.getProductClass?addSupp=yes">Add Supplier</a></li>
                                        <li><a href="supplier.get?viewSupp=yes">Edit Supplier</a></li>
                                        <li><a href="supplier.get?viewSupp=yes">View All Suppliers</a></li>
                                        <li><a href="product.getProductClass?search=yes&searchWhat=supp">Search Supplier</a></li>
                                    </ul>
                                </li>
                    </c:if>
                      <c:if test="${accountType eq '1' || accountType eq '2'}">
                                <li>
                                    <strong>Sales Rep.</strong>
                                    <ul>
                                        <li><a href="account.get?whatFor=addSR">Add Sales Rep</a></li>
                                        <li><a href="salesrep.get">Edit Sales Rep</a></li>
                                        <li><a href="salesrep.get">View All Sales Reps</a></li>
                                        <li><a href="conditionsSalesRep.jsp">Search Sales Reps</a></li>
                                    </ul>
                                </li>
                            </c:if>
                            <c:if test="${accountType eq '1' || accountType eq '2'}">
                                <li>
                                    <strong>General</strong>
                                    <ul>
                                        <li><a href="account.getTypeStatus">Create Account</a></li>
                                        <li><a href="account.get">Edit Accounts</a></li>
                                        <li><a href="account.get">View All Accounts</a></li>
                                        <li><a href="account.getTypeStatus?forSearch=yes">Search Accounts</a></li>
                                    </ul>
                                </li>
                            </c:if>
                    
                    <c:if test="${accountType eq '1' || accountType eq '4'}">
                        <li>
                            <strong>Product</strong>
                            <ul>
                                <li><a href="product.getProductClass">Add Product</a></li>
                                <li> <a href="Servlets.getProductServlet">Edit Product</a></li>
                                <li><a href="Servlets.getProductServlet">View All Products</a></li>
                                <li><a href="Servlets.getProductServlet?forOther=lowstockLevel">Set Low Stock Level</a></li>
                                
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${accountType eq '1' || accountType eq '2' || accountType eq '3' || accountType eq '6'}">
                        <li>
                            <strong>Invoice</strong>
                            <ul>
                                <li><a href="Servlets.getProductServlet?forOther=invoice">Create Invoice</a></li>
                                <li><a href="Servlets.getInvoiceServlet">Edit Invoice</a></li>
                                <li><a href="Servlets.getInvoiceServlet">View All Invoices</a></li>
                                <li><a href="unfinished.get?getTable=invoice">View Unfinished Invoices</a></li>
                                <li><a href="province.get?whatFor=conditionsInvoice">Search Invoice</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${accountType eq '1' || accountType eq '5' || accountType eq '4'}">
                        <li>
                            <strong>Restock Order</strong>
                            <ul>
                                <c:if test="${accountType eq '1' || accountType eq '4'}">
                                    <li>
                                        <c:choose>
                                            <c:when test="${cartType eq 'restock' && (suppID ne null)}">
                                                <a href="Servlets.getProductServlet?forOther=restock">Create Restock Order</a><br>      
                                            </c:when>
                                            <c:otherwise>
                                                <a href="supplier.get?viewSupp=yes&forRestock=yes">Create Restock Order</a><br>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                </c:if>
                                <li><a href="restockOrder.get">Edit Restock Orders</a></li>
                                <li><a href="restockOrder.get">View All Restock Orders</a></li>
                                <li><a href="unfinished.get?getTable=ro">View Unfinished RO</a></li>
                                <li><a href="restockOrder.getStatus">Search RO</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${accountType eq '2'}">
                        <li>
                            <strong>Restock Order</strong>
                            <ul>
                                <li><a href="restockOrder.get">View All Restock Orders</a></li>
                                <li><a href="unfinished.get?getTable=ro">View Unfinished RO</a></li>
                                <li><a href="restockOrder.getStatus">Search RO</a></li>
                            </ul>
                        </li>
                    </c:if>
                </c:if>
            </ul>
        </div>
        <header id="header">
          <div class="mui-appbar mui--appbar-line-height">
            <div class="mui-container-fluid">
              <a class="sidedrawer-toggle mui--visible-xs-inline-block mui--visible-sm-inline-block js-show-sidedrawer">☰</a>
              <a class="sidedrawer-toggle mui--hidden-xs mui--hidden-sm js-hide-sidedrawer">☰</a>
              <span class="mui--text-title mui--visible-xs-inline-block">Brand.io</span>
            </div>
          </div>
        </header>
    </body>
</html>
