
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
    </head>
    <body>
        <c:if test="${not empty requestScope.ORDER}">
            <fmt:formatDate var="parsedOrderDate" value="${requestScope.ORDER.orderDate}" pattern="dd/MM/yyyy" />
            <p>Order date: ${parsedOrderDate}</p>
            <p>Total: $${requestScope.ORDER.totalPrice}</p>
            <p>Customer's name: ${requestScope.CUSTOMER.fullName}</p>
            <p>Email: ${requestScope.CUSTOMER.email}</p>
            <p>Phone: ${requestScope.CUSTOMER.phoneNumber}</p>
            <p>Address: ${requestScope.CUSTOMER.address}</p>
            <p>Payment method: ${requestScope.METHOD.paymentMethodName}</p>
            <c:forEach items="${requestScope.ORDER_DETAILS}" var="details">
                <div>
                    <img src="${details.product.image}" width="200" height="200"/>
                    <p>${details.product.productName}</p>
                    <p>Price: ${details.price}</p>
                    <p>Quantity: ${details.quantity}</p>
                </div>
            </c:forEach>
        </c:if>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
