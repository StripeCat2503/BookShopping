
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/admin.css"/>
        <link rel="stylesheet" href="css/order_details.css"/>
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

            <c:if test="${not empty requestScope.ORDER}">
                <fmt:formatDate var="parsedOrderDate" value="${requestScope.ORDER.orderDate}" pattern="dd/MM/yyyy" />
                <div class="order-details-section">
                    <div class="py-4 text-center bg-success text-light">Order No. #${requestScope.ORDER.orderID}</div>
                    <div class="py-3 text-center">Order at <strong>${parsedOrderDate}</strong></div>

                    <div class="customer-section">
                        <div class="text-uppercase fw-bold text-center py-1 fs-5 mb-3">customer</div>
                        <div class="info">
                            <label for="">full name</label>
                            <p>${requestScope.CUSTOMER.fullName}</p>
                        </div>
                        <div class="info">
                            <label for="">email</label>
                            <p>${requestScope.CUSTOMER.email}</p>
                        </div>
                        <div class="info">
                            <label for="">phone number</label>
                            <p>${requestScope.CUSTOMER.phoneNumber}</p>
                        </div>
                        <div class="info">
                            <label for="">address</label>
                            <p>${requestScope.CUSTOMER.address}</p>
                        </div>
                    </div>

                    <div class="payment-section">
                        <div class="fw-bold">Payment method</div>
                        <div><img class="mx-2" width="20" height="20" src="${requestScope.METHOD.paymentMethodName eq 'Momo' ? 'icons/momo.png' : 'icons/cod.png'}"/> ${requestScope.METHOD.paymentMethodName}</div>
                    </div>
                    <div class="payment-section">
                        <div class="fw-bold">Order Status</div>
                        <div>
                            <form action="UpdateOrderStatusServlet" method="POST">
                                <input type="hidden" name="orderID" value="${requestScope.ORDER.orderID}" />
                                <select style="border: none; outline: none;" name="statusID">
                                    <c:forEach items="${sessionScope.ORDER_STATUS_LIST}" var="status">
                                        <option value="${status.statusID}" <c:if test="${status.statusID eq requestScope.ORDER.status.statusID}">selected</c:if>>
                                            ${status.statusName}
                                        </option>
                                    </c:forEach>
                                </select>
                                <input type="submit" value="Update status" class="btn btn-primary"/>
                            </form>

                        </div>                     
                    </div>                    
                    <div class="payment-section">
                        <div class="fw-bold">Money Paid</div>
                        <c:if test="${requestScope.ORDER.moneyPaid eq true}">
                            <div class="text-success fw-bold text-uppercase">Paid</div>
                        </c:if>
                        <c:if test="${requestScope.ORDER.moneyPaid eq false}">
                            <div class="text-danger fw-bold text-uppercase">Not Paid</div>
                        </c:if>
                    </div>

                    <div class="order-section">
                        <table>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Book</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>                             

                                <c:if test="${not empty requestScope.ORDER_DETAILS}">
                                    <c:forEach items="${requestScope.ORDER_DETAILS}" var="details">

                                        <tr>
                                            <td>
                                                <div class="product-image" style="background-image: url('${details.product.image}');"></div>
                                            </td>
                                            <td>
                                                <span class="">${details.product.productName}</span>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${details.price}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ"/>
                                                <span>${fmtPrice}</span>
                                            </td>
                                            <td>
                                                <span>${details.quantity}</span>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${details.quantity * details.price}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ"/>
                                                <strong>${fmtPrice}</strong>
                                            </td>
                                        </tr>                              
                                    </c:forEach>    
                                </c:if>        
                                <c:if test="${empty requestScope.ORDER_DETAILS}">
                                    <tr>
                                        <td colspan="5" class="text-danger" style="height: 50px;">Product is not available!</td>
                                    </tr>
                                </c:if>

                            </tbody>
                        </table>

                        <div class="total">
                            <div for="" class="text-uppercase text-light" style="background-color: gray;">total</div>
                            <fmt:formatNumber value="${requestScope.ORDER.totalPrice}" var="fmtPrice" type="currency" maxFractionDigits="0" currencySymbol="đ"/>
                            <div for="" class="primary-bg-color text-light">${fmtPrice}</div>
                        </div> 

                    </div>
                </div>


            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
