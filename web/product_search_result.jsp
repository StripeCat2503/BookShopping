<%-- 
    Document   : index
    Created on : Dec 8, 2020, 5:47:42 PM
    Author     : DuyNK
--%>

<%@page import="com.app.daos.UserDAO"%>
<%@page import="com.app.dtos.UserDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.app.dtos.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.app.daos.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${sessionScope.user.role.roleName eq 'Admin'}">
    <c:redirect url="admin" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Search Results</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/index.css"/>
    </head>
    <body>

        <jsp:include page="header.jsp"/>
        <div class="side-nav border py-3 text-center px-3">
            <h5 class="fw-bold mb-4">Book Categories</h5>
            <c:forEach items="${requestScope.CATEGORY_LIST}" var="category">
                <c:url value="HomeServlet" var="categoryLink">
                    <c:param name="category_id" value="${category.categoryID}"/>
                </c:url>
                <a href="${categoryLink}" class="d-block primary-text-color py-3">${category.categoryName}</a>
            </c:forEach>
        </div>

        <div class="content">
            <div>
                <div class="search-form">
                    <form action="search" method="GET">
                        <input class="search-bar" type="text" placeholder="Search books" name="q" value="${requestScope.SEARCH_VALUE}"/>
                        <input type="submit" value="Search" class="btn-search"/>
                    </form>
                </div>
            </div>
            <c:if test="${not empty requestScope.SEARCH_PRODUCTS}">
                <div class="product-grid mx-auto">
                    <c:forEach items="${requestScope.SEARCH_PRODUCTS}" var="product">
                        <c:if test="${product.status eq true}">
                            <div class="product-card" <c:if test="${product.quantity <= 0}">style='opacity: 0.5;'</c:if>>
                                    <form action="AddToCartServlet" method="POST">
                                        <input type="hidden" placeholder="Search books" name="q" value="${requestScope.SEARCH_VALUE}"/>
                                    <div>
                                        <div class="mx-auto product-image" style="background-image: url('${product.image}');">

                                        </div>

                                    </div>
                                    <div class="info text-center">
                                        <p class="name text-uppercase py-2 fs-6 fw-bold">${product.productName}</p>                           
                                        <p>${product.description eq '' ? 'No description...' : product.description}</p>
                                        <fmt:formatNumber value="${product.price}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ"/>
                                        <p class="price fs-1 fw-bold">${fmtPrice}</p>                           
                                    </div>
                                    <c:if test="${product.quantity <= 0}">
                                        <div class='status text-danger text-uppercase fw-bold'>
                                            Not available
                                        </div>
                                    </c:if>
                                    <c:if test="${product.quantity > 0}">
                                        <div class='status text-success text-uppercase fw-bold'>
                                            available
                                        </div>
                                    </c:if>
                                    <div class="w-100">

                                        <input type="hidden" name="productID" value="${product.productID}">
                                        <input type="hidden" name="price" value="${product.price}">
                                        <input type="hidden" name="productName" value="${product.productName}">
                                        <input type="hidden" name="imgProduct" value="${product.image}">
                                        <input type="submit" class="btn-add-to-cart w-100 text-light" value="Add to cart" <c:if test="${product.quantity <= 0}">disabled</c:if>/>
                                        </div>
                                    </form>
                                </div>
                        </c:if>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty requestScope.SEARCH_PRODUCTS}">
                <div class="not-found">
                    <h3 class="text-uppercase text-muted mt-5">No book found!</h3>
                </div>
            </c:if>
        </div>
        <jsp:include page="footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
