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

<%
    ProductCategoryDAO dao = new ProductCategoryDAO();
    List<ProductCategoryDTO> categories = dao.getAllCategories();
    request.setAttribute("CATEGORY_LIST", categories);
    int numberOfCartItem = 0;
    CartDTO cart = (CartDTO) session.getAttribute("CART");
    if (cart != null && cart.getItems() != null) {
        numberOfCartItem = cart.getItems().size();
    }
%>

<nav class="navbar navbar-expand-lg shadow-sm bg-white">
    <div class="container-fluid">
        <a class="navbar-brand text-dark fw-bold" href="${pageContext.request.servletContext.contextPath}">BookWorld</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
                <li class="nav-item mx-4">
                    <a class="nav-link" href="${pageContext.request.servletContext.contextPath}">Home</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link" href="#">Books</a>
                </li>
                <li class="nav-item dropdown mx-4">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Book Categories
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                        <c:forEach items="${requestScope.CATEGORY_LIST}" var="category">
                            <li><a class="dropdown-item" href="#">${category.categoryName}</a></li>
                            </c:forEach>

                    </ul>
                </li>       
            </ul>
            <a href="cart" class="mx-3">
                <img width="28px" height="28px" src="icons/shopping-cart.svg" />
                <span class="badge bg-danger rounded-pill"><%= numberOfCartItem%></span>
            </a>
            <c:if test="${empty sessionScope.user}">
                <a href="login" class="btn primary-bg-color text-light not-rounded mx-2">Sign In</a>
                <a href="register" class="btn primary-border-color primary-text-color not-rounded mx-2">Sign Up</a>

            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <a href="profile" class="mx-3 primary-text-color text-decoration-none">Welcome, ${sessionScope.user.fullName}!</a>
                <a href="LogoutServlet" class="btn primary-bg-color text-light not-rounded">Logout</a>
            </c:if>

        </div>
    </div>
</nav>
