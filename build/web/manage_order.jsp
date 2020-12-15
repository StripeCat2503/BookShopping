
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.app.dtos.OrderDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.app.daos.OrderDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/manage_order.css"/>
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
            <h3 class="text-uppercase">Order Management</h3>
            <c:if test="${not empty requestScope.ORDERS}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Total</th>
                            <th>Order Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.ORDERS}" var="order">
                            <tr>
                                <td>
                                    <c:url var="orderDetailsUrl" value="OrderDetailsServlet">
                                        <c:param name="id" value="${order.orderID}" />
                                    </c:url>
                                    <a href="${orderDetailsUrl}">#${order.orderID}</a>
                                </td>
                                <td class="fw-bold">$${order.totalPrice}</td>
                                <td>
                                    <fmt:formatDate var="orderDate" pattern="dd/MM/yyyy" value="${order.orderDate}"/>
                                    ${orderDate}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
