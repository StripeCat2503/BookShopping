
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${requestScope.PRODUCT.productName}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/product_details.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="product">
            <div class="product-image" style="background-image: url('${requestScope.PRODUCT.image}');">

            </div>
            <div class="product-info">
                <c:if test="${not empty sessionScope.CART.items[requestScope.PRODUCT.productID]}">
                    <div class='bg-secondary text-light p-3 mb-4' style='font-size: 10px; border-radius: 5px;'>
                        This product is currently in your cart, quantity: <strong>${sessionScope.CART.items[requestScope.PRODUCT.productID].quantity}</strong>
                    </div>
                </c:if>
                <h5>${requestScope.PRODUCT.productName}</h5>
                <fmt:formatNumber value="${requestScope.PRODUCT.price}" var="fmtPrice" type="currency" currencySymbol="" maxFractionDigits="0"/>
                <h3 class="fw-bold fs-2">${fmtPrice} đ</h3>
                <p class="text-secondary">${empty requestScope.PRODUCT.description ? 'No description...' : requestScope.PRODUCT.description}</p>
                <div class="mt-5">
                    <p><strong>Author: </strong>${empty requestScope.PRODUCT.author ? 'Unknown' : requestScope.PRODUCT.author}</p>
                    <p><strong>Publisher: </strong>${empty requestScope.PRODUCT.publisher ? 'Unknown' : requestScope.PRODUCT.publisher}</p>
                </div>

                <div>
                    <form action="AddToCartServlet" method="POST">
                        <div class="input-group">
                            <button id="btnQuantityIncrease" class="border-0 btn btn-success">+</button>
                            <input id="quantityInput" name="quantity" placeholder="Quantity" value="1" class="border text-center" type="number"
                                   name="" id="" readonly style="width: 100px;">
                            <button id="btnQuantityDecrease" class="border-0 btn btn-danger">-</button>
                        </div>
                        <input type="hidden" name="productID" value="${requestScope.PRODUCT.productID}">
                        <input type="hidden" name="price" value="${requestScope.PRODUCT.price}">
                        <input type="hidden" name="productName" value="${requestScope.PRODUCT.productName}">
                        <input type="hidden" name="imgProduct" value="${requestScope.PRODUCT.image}">
                        <input class="text-uppercase w-100 mt-3 btn-add-to-cart" type="submit" value="Add to cart">
                    </form>
                </div>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>   

        <script>
            $(document).ready(function () {

                $('#btnQuantityIncrease').click((e) => {
                    e.preventDefault()
                    var currentQuantity = parseInt($('#quantityInput').val())

                    if (currentQuantity >= 20)
                        return
                    $('#quantityInput').attr('value', currentQuantity + 1)
                })

                $('#btnQuantityDecrease').click((e) => {
                    e.preventDefault()
                    var currentQuantity = parseInt($('#quantityInput').val())
                    if (currentQuantity <= 1)
                        return
                    $('#quantityInput').attr('value', currentQuantity - 1)
                })
            })
        </script>
    </body>
</html>
