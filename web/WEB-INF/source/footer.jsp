<%-- 
    Document   : footer
    Created on : 04 27, 17, 2:44:29 PM
    Author     : Sydney Cruz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/mui-temp/mui-footer.css" rel="stylesheet" type="text/css" />
        
    </head>
    <body>
        <footer id="footer">
          <div class="mui-container-fluid">
              <div class="mui-row">
                  <div class="mui-col-md-6 mui-col-md-offset-3 mui--text-center">
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
                            Home
                        </a>
                        <br>
                      <c:if test="${state == 'logged in'}">
                            <a href="Servlets.logoutServlet">Log out</a>
                            <br>
                      </c:if>
                      
                      Made with ♥ by <a href="https://www.muicss.com">MUI</a>
                  </div>
              </div>
              <br>
          </div>
        </footer>
        <!--Form Validation-->
        <script src="import/form-validation/jquery.validate.js"></script>
        <script src="js/other-method/other-method.js"></script>
    </body>
</html>
