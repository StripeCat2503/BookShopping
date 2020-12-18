<%-- 
    Document   : admin_header
    Created on : Dec 14, 2020, 8:01:03 PM
    Author     : DuyNK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="${pageContext.request.servletContext.contextPath}">Admin Dashboard</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">          
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.servletContext.contextPath}">Welcome Admin</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-light" href="LogoutServlet">Logout</a>
                </li>
            </ul>          
        </div>
    </div>
</nav>
