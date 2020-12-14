
<%@page import="java.util.List"%>
<%@page import="com.app.dtos.ProductCategoryDTO"%>
<%@page import="com.app.daos.ProductCategoryDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        ProductCategoryDAO dao = new ProductCategoryDAO();
        List<ProductCategoryDTO> categoryList = dao.getAllCategories();
        request.setAttribute("CATEGORY_LIST", categoryList);
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>       
        <form action="AddProductServlet" class="w-50 mx-auto" method="POST" enctype="multipart/form-data">
            <h3 class="my-3">Add new product</h3>
            <div class="form-group my-2">
                <label for="">Product's name</label>
                <input type="text" class="form-control" name="txtProductName" required="true" value="${requestScope.PRODUCT.productName}">
                <small style="color: red">${requestScope.PRODUCT_ERROR.productNameErr}</small>
            </div>
            <div class="form-group my-2">
                <label for="">Price</label>
                <input type="number" class="form-control" name="txtPrice" required="true" value="${requestScope.PRODUCT.price eq 0 ? '' : requestScope.PRODUCT.price}">
                <small style="color: red">${requestScope.PRODUCT_ERROR.priceErr}</small>
            </div>
            <div class="form-group my-2">
                <label for="">Quantity</label>
                <input type="number" class="form-control" name="txtQuantity" required="true" value="${requestScope.PRODUCT.quantity eq 0 ? '' : requestScope.PRODUCT.quantity}">
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
                    <label for="">Image</label>
                    <input type="file" class="form-control" name="imgProduct">
                </div>
                <div class="form-group my-2">
                    <label for="">Description</label>
                    <textarea class="form-control" name="txtDescription" value="${requestScope.PRODUCT.description}"></textarea>
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
            <small class="text-success d-block my-3">${requestScope.SUCCESS_MSG}</small>           
            <input type="submit" value="Add product" class="btn btn-success">
            <a href="admin" class="btn btn-danger">Cancel</a>
        </form>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
