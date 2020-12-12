<%-- 
    Document   : index
    Created on : Dec 8, 2020, 5:47:42 PM
    Author     : DuyNK
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.app.dtos.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.app.daos.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Book Shopping</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/index.css"/>
    </head>
    <body>
        <%
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> products = dao.getAllProducts();
            request.setAttribute("PRODUCT_LIST", products);
        %>
        <jsp:include page="header.jsp"/>
        <div class="product-grid mx-auto">
            <c:forEach items="${requestScope.PRODUCT_LIST}" var="product">
                <div class="product-card">
                    <form action="AddToCartServlet" method="POST">
                        <div class="mt-4">
                            <div class="mx-auto" style="background-image: url('${product.image}'); width: 200px; height: 200px; background-position: center; 
                                 background-size: cover;">

                            </div>

                        </div>
                        <div class="info text-center">
                            <p class="name text-uppercase py-2 fs-6 fw-bold">${product.productName}</p>                           
                            <p>${product.description eq '' ? 'No description...' : product.description}</p>
                            <p class="price fs-1 fw-bold">$${product.price}</p>                           
                        </div>
                        <div class="w-100">
                            <input type="hidden" name="productID" value="${product.productID}">
                            <input type="hidden" name="price" value="${product.price}">
                            <input type="hidden" name="productName" value="${product.productName}">
                            <input type="hidden" name="imgProduct" value="${product.image}">
                            <input type="submit" class="btn-add-to-cart w-100 text-light" value="Add to cart" />
                        </div>
                    </form>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
