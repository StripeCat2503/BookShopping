
<%@page import="java.util.List"%>
<%@page import="com.app.dtos.ProductDTO"%>
<%@page import="com.app.daos.ProductDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        ProductDAO dao = new ProductDAO();
        List<ProductDTO> products = dao.getAllProducts();
        request.setAttribute("PRODUCT_LIST", products);
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/admin.css"/>
    </head>
    <body>
        <jsp:include page="admin_header.jsp" /> 
        <div class="side-nav">
            <div class="text-center side-nav-item">
                <a href="manageProduct">Manage Product</a>
            </div>
            <div class="text-center side-nav-item">
                <a href="order">Manage Order</a>
            </div>
        </div>

        <div class="content">
            <h3 class="text-uppercase">Product management</h3>       
            <div class="my-2">
                <a href="addProduct" class="btn btn-success">ADD PRODUCT</a>
            </div>
            <table class="table table-light table-hover">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Category</th>
                        <th scope="col">Status</th>
                        <th scope="col" colspan="2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.PRODUCT_LIST}" var="product" varStatus="counter">
                        <tr>                       
                            <td>${counter.count}</td>
                            <td>${product.productName}</td>
                            <td>${product.price}</td>
                            <td>${product.quantity}</td>
                            <td>${product.category.categoryName}</td>
                            <td>
                                <c:if test="${product.status eq true}">
                                    Active
                                </c:if>
                                <c:if test="${product.status eq false}">
                                    Inactive
                                </c:if>
                            </td>
                            <td>
                                <a class="btn btn-primary text-uppercase" href="editProduct?productID=${product.productID}">Edit</a>
                            </td>
                            <td>
                                <a class="btn btn-danger text-uppercase" href="DeleteProductServlet?productID=${product.productID}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty requestScope.PRODUCT_LIST}">
                        <tr>
                            <td colspan="8" class='text-center'>There's no book!</td>
                        </tr>
                    </c:if>

                </tbody>
            </table>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
