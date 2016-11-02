<%-- 
    Document   : index
    Created on : 08 15, 16, 3:00:01 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String message = ""+request.getAttribute("message");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome!</title>
          <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
         <div class="site__container">

            <div class="grid__container">
  
         <form action="Servlets.logInServlet" method="post" class="form form--login">
         
        <div class="form__field">
            <c:if class="fontawesome-lock" test="${message != ''}">
                <p>${message}</p><br><br>
            </c:if>
        </div>
        
        <div class="form__field">
             <label class="fontawesome-user" for="login__username"><span class="hidden">Username</span></label>
             <input type="text" name="usernameInput" id="login__username" type="text" class="form__input" placeholder="Username" required>
        </div>
            
        <div class="form__field">
          <label class="fontawesome-lock" for="login__password"><span class="hidden">Password</span></label>
          <input type="password"name="passwordInput" id="login__password" type="password" class="form__input" placeholder="Password" required>
        </div>

        <div class="form__field">
          <input type="submit" value="Log in">
        </div>

      </form>
           
       <p class="text--center">Don't have an account? <a href="#">Sign up now</a> <span class="fontawesome-arrow-right"></span></p>

    </div>

  </div>
             
    </body>

</html>
