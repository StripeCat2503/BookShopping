<%-- 
    Document   : header
    Created on : Dec 8, 2020, 5:43:20 PM
    Author     : DuyNK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">BookWorld</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
                <li class="nav-item mx-4">
                    <a class="nav-link" href="#">Home</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link" href="#">Books</a>
                </li>
                <li class="nav-item dropdown mx-4">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Book Categories
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another action</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Something else here</a></li>
                    </ul>
                </li>       
            </ul>
            <c:if test="${empty sessionScope.user}">
                <a href="LoginServlet" class="btn btn-danger mx-3">Login</a>
                <a href="RegisterServlet" class="btn btn-outline-light">Register account</a><br>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <a href="UserProfileServlet" class="mx-3 text-light text-decoration-none">Welcome, ${sessionScope.user.fullName}!</a>
                <a href="LogoutServlet" class="btn btn-primary">Logout</a>
            </c:if>

        </div>
    </div>
</nav>
