
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <jsp:include page="header.jsp"/>
        <%
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (cart != null) {
                if (cart.getItems() != null && !cart.getItems().isEmpty()) {
                    double total = 0;
                    for(ProductDTO p : cart.getItems().values()){
                        total += p.getPrice() * p.getQuantity();
                    }
        %>
        <form action="CheckoutServlet" method="POST">
            <div class="checkout-page">
                <section class="checkout-section">
                    <h2 class="text-center text-uppercase mb-5">Checkout</h2>
                    <div class="mx-auto w-75">
                        <div class="my-3">
                            <label for="">Customer name</label>
                            <input type="text" value="${sessionScope.user.fullName}" name="customerName" <c:if test="${not empty sessionScope.user}">readonly</c:if>>
                        </div>
                        <div class="my-3">
                            <label for="">Email</label>
                            <input type="email" value="${sessionScope.user.email}" name="email" <c:if test="${not empty sessionScope.user}">readonly</c:if>>
                        </div>
                        <div class="my-3">
                            <label for="">Phone number</label>
                            <input type="text" value="${sessionScope.user.phoneNumber}" name="phone" <c:if test="${not empty sessionScope.user}">readonly</c:if>>
                        </div>
                        <div class="my-3">
                            <label for="">Address</label>
                            <textarea name="address" <c:if test="${not empty sessionScope.user}">readonly</c:if>>${sessionScope.user.address}</textarea>
                        </div>
                        <div class="my-3">
                            <label for="">Payment method</label>
                            <div class="d-flex align-items-center">
                                <input type="radio" name="paymentMethod" id="" value="cod" checked="true"/>
                                <span class="mx-2">Cash on Delivery (COD)</span>                           
                            </div>
                            <div class="d-flex align-items-center">
                                <input type="radio" name="paymentMethod" id="" value="momo"/>
                                <span class="mx-2">Momo (Online payment)</span>                           
                            </div>
                        </div>
                    </div>

                    <input type="submit" value="CONFIRM CHECKOUT" class="d-block btn-confirm-checkout">
                </section>
                <section class="order-section">
                    <h2 class="text-center text-uppercase mb-5 fw-bold">Your Order</h2>
                    <div class="total mb-3">
                        <div class="bg-light text-center text-uppercase">Total</div>
                        <div class="bg-secondary text-center text-light fw-bold">
                            <span class="">$<%= total %></span>
                        </div>
                    </div>
                    <div class="order-items">
                        <%
                            
                            for (ProductDTO orderItem : cart.getItems().values()) {                            
                        %>
                        <div class="item">
                            <div class="">
                                <div class="product-image" style="background-image: url('<%= orderItem.getImage() %>');"></div>
                            </div>
                            <div class="">
                                <div style='font-size: 12px'><%= orderItem.getProductName() %></div>
                                <div class="fw-bold">$<%= orderItem.getPrice()%></div>
                            </div>
                            <div style='font-size: 12px' class="text-end">
                                Quantity: <strong><%= orderItem.getQuantity()%></strong>
                            </div>
                            <div class="fs-4 fw-bold text-end">
                                $<%= orderItem.getPrice() * orderItem.getQuantity() %>
                            </div>
                        </div>
                        <%
                            }
                        %>

                    </div>
                </section>
            </div>
        </form>
        <%
                }
            }
        %>



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
