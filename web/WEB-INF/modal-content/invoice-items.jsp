<%-- 
    Document   : invoice-items
    Created on : 05 10, 17, 9:03:03 PM
    Author     : Sydney Cruz
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
            <table class="mui-table mui-table--bordered mui--text-center">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity Purchased</th>
                        <th>Total Cost</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="al" items="${al}">
                        <tr>
                            <td>${al.getProductName()}</td>
                            <td>${al.getQuantityPurchased()}</td>
                            <td>${al.getTotalCost()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
        </div>
    </body>
</html>
