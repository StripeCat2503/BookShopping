
<%@page import="com.app.dtos.ProductDTO"%>
<%@page import="com.app.daos.ProductDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.app.dtos.ProductCategoryDTO"%>
<%@page import="com.app.daos.ProductCategoryDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String productIDStr = request.getParameter("productID");
        ProductCategoryDAO dao = new ProductCategoryDAO();
        List<ProductCategoryDTO> categoryList = dao.getAllCategories();
        request.setAttribute("CATEGORY_LIST", categoryList);

        if (productIDStr != null) {
            ProductDAO productDAO = new ProductDAO();
            int productID = Integer.parseInt(productIDStr);
            ProductDTO product = productDAO.getProductByID(productID);
            if (product != null) {
                request.setAttribute("PRODUCT", product);
            }
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/admin.css"/>
        <style>
            img{
                max-width: 200px;
                max-height: 200px;
            }
        </style>
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
            <form action="UpdateProductServlet" class="w-75 mx-auto" method="POST" enctype="multipart/form-data">
                <h3 class="my-3 fw-bold text-uppercase">Edit product</h3>
                <input type="hidden" name="txtProductID" value="${requestScope.PRODUCT.productID}"/>
                <div class="form-group my-2">
                    <label for="">Product's name</label>
                    <input type="text" class="form-control" name="txtProductName" required="true" value="${requestScope.PRODUCT.productName}">
                    <small style="color: red">${requestScope.PRODUCT_ERROR.productNameErr}</small>
                </div>
                <div class="form-group my-2">
                    <label for="">Price</label>
                    <input type="number" class="form-control" name="txtPrice" required="true" value="${requestScope.PRODUCT.price}">
                    <small style="color: red">${requestScope.PRODUCT_ERROR.priceErr}</small>
                </div>
                <div class="form-group my-2">
                    <label for="">Quantity</label>
                    <input type="number" class="form-control" name="txtQuantity" required="true" value="${requestScope.PRODUCT.quantity}">
                    <small style="color: red">${requestScope.PRODUCT_ERROR.quantityErr}</small>
                </div>
                <div class="form-group my-2">
                    <label for="">Status</label>
                    <select name="slStatus" class="form-select">
                        <option value="1" <c:if test="${requestScope.PRODUCT.status eq true}">selected</c:if>>
                                Active
                            </option>
                            <option value="0" <c:if test="${requestScope.PRODUCT.status eq false}">selected</c:if>>
                                Inactive
                            </option>
                        </select>
                    </div>
                    <div class="form-group my-2">
                        <label for="" class="d-block">Image</label>
                        <div class="my-3">
                            <img src="${requestScope.PRODUCT.image}" />
                    </div>
                    <input type="hidden" name="oldImgProduct" value="${requestScope.PRODUCT.image}"/>
                    <input type="file" class="form-control" name="imgProduct">
                </div>
                <div class="form-group my-2">
                    <label for="">Description</label>
                    <textarea class="form-control" name="txtDescription">${requestScope.PRODUCT.description}</textarea>
                </div>
                <div class="form-group my-2">
                    <label for="">Category</label>
                    <select name="slCategory" class="form-select">
                        <c:forEach items="${requestScope.CATEGORY_LIST}" var="category">
                            <option value="${category.categoryID}" <c:if test="${category.categoryID eq requestScope.PRODUCT.category.categoryID}">selected</c:if>>${category.categoryName}</option>
                        </c:forEach>
                        <c:if test="${empty requestScope.CATEGORY_LIST}">
                            <option>There's no category</option>
                        </c:if>

                    </select>
                </div>
                <small class="text-success d-block">${requestScope.SUCCESS_MSG}</small>           
                <input type="submit" value="Save" class="btn btn-success">
                <a href="manageProduct" class="btn btn-danger">Cancel</a>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
