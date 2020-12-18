
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.app.dtos.PaymentMethodDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.app.daos.PaymentMethodDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.app.dtos.ProductDTO"%>
<%@page import="com.app.dtos.CartDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/checkout.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.CART}">
            <c:redirect url="${pageContext.servletContext.contextPath}"></c:redirect>
        </c:if>
    
    <jsp:include page="header.jsp"/>

    <c:if test="${not empty requestScope.PRODUCT_ERR}">
        <div class='toast-container mx-2 my-3'>           
            <c:forEach items="${requestScope.PRODUCT_ERR}" var="error">
                <div class="toast p-3" role="alert" aria-live="assertive" aria-atomic="true" data-bs-autohide='false'>
                    <div class="toast-header">
                        <div class="rounded me-2 bg-danger" style='width: 16px; height: 16px'></div>
                        <strong class="me-auto">Product is not available!</strong>              
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        ${error.value}
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <form action="CheckoutServlet" method="POST">
        <div class="checkout-page">
            <section class="checkout-section">
                <h2 class="text-center text-uppercase mb-5">Checkout</h2>
                <div class="mx-auto w-75">
                    <div class="my-3">
                        <label for="">Customer name</label>
                        <input type="text" value="${not empty sessionScope.user.fullName ? sessionScope.user.fullName : requestScope.fullName}" name="customerName" <c:if test="${not empty sessionScope.user.fullName}">readonly</c:if>>
                        <small class='text-danger my-1'>${requestScope.fullNameError}</small>
                    </div>
                    <div class="my-3">
                        <label for="">Email</label>
                        <input type="email" value="${not empty sessionScope.user.email ? sessionScope.user.email : requestScope.email}" name="email" <c:if test="${not empty sessionScope.user.email}">readonly</c:if>>
                        <small class='text-danger my-1'>${requestScope.emailError}</small>
                    </div>
                    <div class="my-3">
                        <label for="">Phone number</label>
                        <input type="text" value="${not empty sessionScope.user.phoneNumber ? sessionScope.user.phoneNumber : requestScope.phone}" name="phone" <c:if test="${not empty sessionScope.user.phoneNumber}">readonly</c:if>>
                        <small class='text-danger my-1'>${requestScope.phoneError}</small>
                    </div>
                    <div class="my-3">
                        <label for="">Address</label>
                        <textarea name="address" <c:if test="${not empty sessionScope.user.address}">readonly</c:if>>${not empty sessionScope.user.address ? sessionScope.user.address : requestScope.address}</textarea>
                        <small class='text-danger my-1'>${requestScope.addressError}</small>
                    </div>
                    <div class="my-3">
                        <label for="">Payment method</label>
                        <c:if test="${not empty sessionScope.PAYMENT_METHODS}">
                            <c:forEach items="${sessionScope.PAYMENT_METHODS}" var="m">
                                <div class="d-flex align-items-center">
                                    <input type="radio" name="paymentMethod" id="" value="${m.paymentMethodID}" <c:if test="${m.paymentMethodName eq 'Cash On Delivery'}">checked</c:if>/>
                                    <span class="mx-2"><img width="16" height="16" src="${m.paymentMethodName eq 'Momo' ? 'icons/momo.png' : 'icons/cod.png'}"/> ${m.paymentMethodName}</span>                           
                                </div>
                            </c:forEach>
                        </c:if>

                    </div>
                </div>

                <input type="submit" value="CONFIRM CHECKOUT" class="d-block btn-confirm-checkout">
            </section>
            <section class="order-section">
                <h2 class="text-center text-uppercase mb-5 fw-bold">Your Order</h2>
                <div class="total mb-3">
                    <div class="bg-light text-center text-uppercase">Total</div>
                    <div class="bg-secondary text-center text-light fw-bold">
                        <fmt:formatNumber value="${sessionScope.CART.total}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ" />
                        ${fmtPrice}
                    </div>
                </div>
                <div class="order-items">
                    <c:forEach items="${sessionScope.CART.items}" var="item">
                        <div class="item">
                            <div class="">
                                <div class="product-image" style="background-image: url('${item.value.image}');"></div>
                            </div>
                            <div class="">
                                <div style='font-size: 12px'>${item.value.productName}</div>
                                <fmt:formatNumber value="${item.value.price}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol=""/>
                                <div class="fw-bold">${fmtPrice}</div>
                            </div>
                            <div style='font-size: 12px' class="text-end">
                                Quantity: <strong>${item.value.quantity}</strong>
                            </div>
                            <div class="fs-4 fw-bold text-end">
                                <fmt:formatNumber value="${item.value.price * item.value.quantity}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol=""/>
                                ${fmtPrice}
                            </div>
                        </div>
                    </c:forEach>                   

                </div>
            </section>
        </div>
    </form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-2.2.4.js"></script>   
    <script>
        $(document).ready(function () {
            $('.toast').toast('show');
        });
    </script>
</body>
</html>
