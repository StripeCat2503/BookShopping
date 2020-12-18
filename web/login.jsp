
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/login.css"/>
    </head>
    <body>

        <form action="LoginServlet" method="POST">

            <div class="inner-form">
                <h2><a href="/BookShopping">BookWorld</a></h2>
                <p class="text-muted mt-3">Sign In</p>
                <label for="">User ID</label>
                <input type="text" name="txtUserID" placeholder="Username">
                <label for="">Password</label>
                <input type="password" name="txtPassword" placeholder="Password">
                <c:if test="${not empty requestScope.loginError}">
                    <small class="text-danger">${requestScope.loginError}</small><br>
                </c:if>
                <input type="submit" value="SIGN IN" class="btn primary-bg-color text-light btn-signin" />
                <a class="btn-google btn btn-danger my-2 d-block text-light" 
                   href="https://accounts.google.com/o/oauth2/auth?scope=profile email&redirect_uri=http://localhost:8080/BookShopping/login-google&response_type=code&client_id=197892025877-qe8gqh72tj7v1rk77hle8m327ji98me6.apps.googleusercontent.com"
                   >Sign in with Gmail <img class="mx-1" src="icons/google.svg" width="10" height="10"/></a>
                <div class="text-center">
                    <a href="register" style="font-size: 12px;">Does not have account? Sign up now</a>
                </div>
            </div>

        </form>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
