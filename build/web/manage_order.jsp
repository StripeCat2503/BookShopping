
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
            <%
                SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
                OrderDAO orderDAO = new OrderDAO();
                List<OrderDTO> orders = orderDAO.getAllOrders();
                if (orders != null && !orders.isEmpty()) {
            %>
            <table border="1">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Total</th>
                        <th>Order Date</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (OrderDTO order : orders) {

                    %>

                    <tr>
                        <td>
                            <a href="OrderDetailsServlet?id=<%= order.getOrderID()%>">#<%= order.getOrderID()%></a>
                        </td>
                        <td class="fw-bold">$<%= order.getTotalPrice()%></td>
                        <td><%= fm.format(order.getOrderDate())%></td>
                    </tr>

                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
                }
            %>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>     
    </body>
</html>
