<%-- 
    Document   : header
    Created on : Dec 8, 2020, 5:43:20 PM
    Author     : DuyNK
--%>

<%@page import="com.app.dtos.CartDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.app.daos.ProductCategoryDAO"%>
<%@page import="com.app.dtos.ProductCategoryDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg shadow-sm bg-white sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand text-dark fw-bold" href="${pageContext.request.servletContext.contextPath}">BookWorld</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
             
            </ul>
            <a href="cart" class="mx-3">
                <img width="28px" height="28px" src="icons/shopping-cart.svg" />
                <span class="badge bg-danger rounded-pill">${sessionScope.CART.numberOfItems}</span>
            </a>
            <c:if test="${empty sessionScope.user}">
                <a href="login" class="btn primary-bg-color text-light mx-2 nav-btn-signin">Sign In</a>
                <a href="register" class="btn primary-border-color primary-text-color mx-2 nav-btn-signup">Sign Up</a>

            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <a href="profile" class="mx-3 primary-text-color text-decoration-none">Welcome, ${sessionScope.user.fullName}!</a>
                <a href="LogoutServlet" class="btn primary-bg-color text-light not-rounded">Logout</a>
            </c:if>

        </div>
    </div>
</nav>
