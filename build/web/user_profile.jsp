<%-- 
    Document   : user_profile
    Created on : Dec 8, 2020, 4:50:02 PM
    Author     : DuyNK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>User profile</h1>    
        <h4>Full name: ${sessionScope.user.fullName}</h4>
        <h4>Email: ${sessionScope.user.email}</h4>
        <h4>Phone number: ${sessionScope.user.phoneNumber}</h4>
        <h4>Address ${sessionScope.user.address}</h4>
    </body>
</html>
