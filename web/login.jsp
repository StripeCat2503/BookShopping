
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
                <input type="text" name="txtUserID">
                <label for="">Password</label>
                <input type="password" name="txtPassword">
                <c:if test="${not empty requestScope.loginError}">
                    <small class="text-danger">${requestScope.loginError}</small><br>
                </c:if>
                <input type="submit" value="SIGN IN" class="btn primary-bg-color text-light not-rounded">
            </div>
        </form>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
