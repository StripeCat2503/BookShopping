<%-- 
    Document   : register
    Created on : Dec 8, 2020, 11:36:48 AM
    Author     : DuyNK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Registration</title>
    </head>
    <body>
        <h2>Register new account</h2>
        <form action="RegisterServlet" method="POST">
            <label>User ID</label><br>
            <input type="text" placeholder="User ID" name="txtUserID"/><br>
            <label>Password</label><br>
            <input type="password" placeholder="Password" name="txtPassword"/><br>
            <label>Full Name</label><br>
            <input type="text" placeholder="Full name" name="txtFullName"/><br>
            <label>Address</label><br>
            <input type="text" placeholder="Address" name="txtAddress"/><br>
            <label>Email</label><br>
            <input type="text" placeholder="Email" name="txtEmail"/><br>
            <label>Phone Number</label><br>
            <input type="text" placeholder="Phone Number" name="txtPhoneNumber"/><br>
            <input type="submit" value="Register" />
        </form>
    </body>
</html>
