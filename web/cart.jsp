
<%@page import="com.app.dtos.ProductDTO"%>
<%@page import="com.app.dtos.CartDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <%
                CartDTO cart = (CartDTO) session.getAttribute("CART");

                if (cart != null && !cart.getItems().isEmpty()) {
                    double cartTotal = 0;
            %>

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
                            <%
                                for (ProductDTO product : cart.getItems().values()) {
                                    cartTotal += product.getPrice() * product.getQuantity();
                            %>
                            <tr>
                                <td>
                                    <a href="DeleteCartItemServlet?productID=<%= product.getProductID()%>">
                                        <img src="icons/remove_icon.svg" />
                                    </a>
                                </td>
                                <td>
                                    <div class="p-1">
                                        <div class="product-image mx-auto"
                                             style="background: url('<%= product.getImage().isEmpty() ? "product_images/default_product.png" : product.getImage().replace("\\", "/")%>') no-repeat; background-position: center;background-size: cover;">
                                        </div>
                                    </div>
                                </td>
                                <td><%= product.getProductName()%></td>
                                <td>$<%= product.getPrice()%></td>
                                <td>
                                    <div class="input-group mx-auto" style="width: 120px;">
                                        <input type="hidden" value="<%= product.getProductID() %>" class="product-id" name="productID"/>
                                        <button id="btnIncrease-<%= product.getProductID()%>" class="input-group-btn quantity-btn">+</button>
                                        <input readonly="true" type="number" name="quantity-<%= product.getProductID()%>" id="quantity-<%= product.getProductID()%>" 
                                               class="form-control quantity-control text-center" value="<%= product.getQuantity()%>">
                                        <button id="btnDecrease-<%= product.getProductID()%>" class="input-group-btn quantity-btn">-</button>
                                    </div>
                                </td>
                                <td>$<%= product.getPrice() * product.getQuantity()%></td>
                            </tr>
                            <%
                                }
                            %>

                        </tbody>
                    </table>
                    <input type="submit" value = "UPDATE CART" class="btn btn-success my-2" />
                </form>
            </section>

            <section class="cart-total p-2">
                <h4 class="text-uppercase text-center">Cart Total</h4>
                <div class="text-center fw-bold fs-1">$<%= cartTotal%></div>
                <div class="text-center">
                    <a href="CheckoutServlet" class="d-block btn-checkout">CHECKOUT</a>
                </div>

            </section>
            <%
            } else {
            %>
            <h3>There's no book in cart!</h3>
            <%
                }
            %>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
        <script>
            $(document).ready(function(){
                const productIDFields = $('.product-id')
                var productIds = []
                
                for(var i = 0; i < productIDFields.length; i++){
                    productIds.push(productIDFields[i].value)
                }
                
                for(var i = 0; i < productIds.length; i++){
                    var id = productIds[i]
                    const quantityInput = $('#quantity-' + id)
                    const increaseBtn = $('#btnIncrease-' + id)
                    const decreaseBtn = $('#btnDecrease-' + id)
                    
                    increaseBtn.click(function(e){
                        e.preventDefault()
                        var currentQuantity = parseInt(quantityInput.val())
                        quantityInput.attr('value', currentQuantity + 1)
                    })
                    
                    decreaseBtn.click(function(e){
                        e.preventDefault()                      
                        var currentQuantity = parseInt(quantityInput.val())
                        if(currentQuantity <= 1) return;
                        quantityInput.attr('value', currentQuantity - 1)
                    })
                }
            })
        </script>
    </body>
</html>
