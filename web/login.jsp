<%-- 
    Document   : login
    Created on : Dec 8, 2020, 10:39:33 AM
    Author     : DuyNK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login to Book Shop</title>
    </head>
    <body>
        <h2>
            Login to Book Shop
        </h2>
        <form action="LoginServlet" method="POST">
            <label>User ID</label><br>
            <input type="text" placeholder="User ID" name="txtUserID" /><br>
            <label>Password</label><br>
            <input type="password" placeholder="Password" name="txtPassword" /><br>
            <c:if test="${not empty requestScope.error}">
                <small style="color: red">${requestScope.error}</small><br>
            </c:if>
            <input type="submit" value="Login" />
        </form>
    </body>
</html>
