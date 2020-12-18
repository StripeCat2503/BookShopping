
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/register.css"/>
    </head>
    <body>

        <form action="RegisterServlet" method="POST">

            <div class="inner-form">
                <h2><a href="/BookShopping">BookWorld</a></h2>
                <p class="text-muted mt-3">Sign Up</p>
                <label for="">User ID <span class="text-danger fw-bold">*</span></label>
                <input type="text" name="txtUserID" value="${requestScope.userID}">
                <c:if test="${not empty requestScope.userIdError}">
                    <small class="text-danger my-1">${requestScope.userIdError}</small>
                </c:if>
                <c:if test="${not empty requestScope.duplicateError}">
                    <small class="text-danger my-1">${requestScope.duplicateError}</small>
                </c:if>
                <label for="">Password <span class="text-danger fw-bold">*</span></label>
                <input type="password" name="txtPassword">
                <c:if test="${not empty requestScope.passwordError}">
                    <small class="text-danger my-1">${requestScope.passwordError}</small>
                </c:if>
                <label for="">Confirm Password <span class="text-danger fw-bold">*</span></label>
                <input type="password" name="txtConfirmPassword">
                <c:if test="${not empty requestScope.confirmPasswordError}">
                    <small class="text-danger my-1">${requestScope.confirmPasswordError}</small>
                </c:if>
                <label for="">Full Name <span class="text-danger fw-bold">*</span></label>
                <input type="text" name="txtFullName" value="${requestScope.fullName}">
                <c:if test="${not empty requestScope.fullNameError}">
                    <small class="text-danger my-1">${requestScope.fullNameError}</small>
                </c:if>
                <label for="">Email <span class="text-danger fw-bold">*</span></label>
                <input type="text" name="txtEmail" value="${requestScope.email}">
                <c:if test="${not empty requestScope.emailError}">
                    <small class="text-danger my-1">${requestScope.emailError}</small>
                </c:if>
                <label for="">Phone Number <span class="text-danger fw-bold">*</span></label>
                <input type="text" name="txtPhoneNumber" value="${requestScope.phone}">
                <c:if test="${not empty requestScope.phoneError}">
                    <small class="text-danger my-1">${requestScope.phoneError}</small>
                </c:if>
                <label for="">Address <span class="text-danger fw-bold">*</span></label>
                <input type="text" name="txtAddress" value="${requestScope.address}">
                <c:if test="${not empty requestScope.addressError}">
                    <small class="text-danger my-1">${requestScope.addressError}</small>
                </c:if>
                <input type="submit" value="SIGN UP" class="btn primary-bg-color text-light btn-signup">
                <div class="text-center my-2">
                    <a href="login" style="font-size: 12px;">Already have account? Sign in</a>
                </div>
            </div>
        </form>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
