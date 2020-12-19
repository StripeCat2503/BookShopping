
<%@page import="com.app.dtos.ProductDTO"%>
<%@page import="com.app.dtos.CartDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/cart.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <div class="cart-page my-5 mx-2">           

            <c:if test="${not empty sessionScope.CART and not empty sessionScope.CART.items}">
                <section class="cart">
                    <form action="UpdateCartServlet" method="POST">
                        <table>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>BOOK</th>
                                    <th>PRICE</th>
                                    <th>QUANTITY</th>
                                    <th>TOTAL</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${sessionScope.CART.items}" var="item">
                                    <tr>
                                        <td>
                                            <c:url var="deleteCartItemUrl" value="DeleteCartItemServlet">
                                                <c:param name="productID" value="${item.key}"/>
                                            </c:url>
                                            <a href="${deleteCartItemUrl}">
                                                <img src="icons/remove_icon.svg" />
                                            </a>
                                        </td>
                                        <td>
                                            <div class="p-1">
                                                <div class="product-image mx-auto"
                                                     style="background: url('${item.value.image}') no-repeat; background-position: center;background-size: cover;">
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <a style='text-decoration: none;' href="ProductDetailsServlet?id=${item.value.productID}">${item.value.productName}</a>
                                        </td>
                                        <fmt:formatNumber value="${item.value.price}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ"/>
                                        <td>${fmtPrice}</td>
                                        <td>
                                            <div class="input-group mx-auto" style="width: 120px;">
                                                <input type="hidden" value="${item.key}" class="product-id" name="productID"/>
                                                <button id="btnIncrease-${item.key}" class="input-group-btn quantity-btn">+</button>
                                                <input readonly="true" type="number" name="quantity-${item.key}" id="quantity-${item.key}" 
                                                       class="form-control quantity-control text-center" value="${item.value.quantity}">
                                                <button id="btnDecrease-${item.key}" class="input-group-btn quantity-btn">-</button>
                                            </div>
                                        </td>
                                        <fmt:formatNumber value="${item.value.price * item.value.quantity}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ"/>
                                        <td>${fmtPrice}</td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                        <input type="submit" value = "UPDATE CART" class="btn btn-success my-2" />
                    </form>
                </section>

                <section class="cart-total p-2">
                    <h4 class="text-uppercase text-center">Cart Total</h4>
                    <fmt:formatNumber value="${sessionScope.CART.total}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ"/>
                    <div class="text-center fw-bold fs-1">${fmtPrice}</div>
                    <div class="text-center">
                        <a href="checkout" class="d-block btn-checkout">CHECKOUT</a>
                    </div>

                </section>
            </c:if>

            <c:if test="${empty sessionScope.CART or empty sessionScope.CART.items}">
                <div class="p-5 border m-3 w-50">
                    <h4 class="text-uppercase text-muted mb-3">There's no book in cart!</h4>
                    <a href="${pageContext.servletContext.contextPath}" class="btn btn-danger text-light d-block" style="padding: 20px 0;">Go Shopping</a>
                </div>
            </c:if>          

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
        <script>
            $(document).ready(function () {
                const productIDFields = $('.product-id')
                var productIds = []

                for (var i = 0; i < productIDFields.length; i++) {
                    productIds.push(productIDFields[i].value)
                }

                for (var i = 0; i < productIds.length; i++) {
                    var id = productIds[i]
                    const quantityInput = $('#quantity-' + id)
                    const increaseBtn = $('#btnIncrease-' + id)
                    const decreaseBtn = $('#btnDecrease-' + id)

                    increaseBtn.click(function (e) {
                        e.preventDefault()
                        var currentQuantity = parseInt(quantityInput.val())
                        quantityInput.attr('value', currentQuantity + 1)
                    })

                    decreaseBtn.click(function (e) {
                        e.preventDefault()
                        var currentQuantity = parseInt(quantityInput.val())
                        if (currentQuantity <= 1)
                            return;
                        quantityInput.attr('value', currentQuantity - 1)
                    })
                }
            })
        </script>
    </body>
</html>
