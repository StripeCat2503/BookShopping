<%-- 
    Document   : welcome
    Created on : Dec 8, 2020, 1:17:34 PM
    Author     : DuyNK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Book Shop</title>
    </head>
    <body>
        <h1>Welcome, <a href="UserProfileServlet">${sessionScope.user.fullName}!</a></h1>
        <a href="LogoutServlet">Logout</a>
    </body>
</html>
