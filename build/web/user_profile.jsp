<%-- 
    Document   : user_profile
    Created on : Dec 8, 2020, 4:50:02 PM
    Author     : DuyNK
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/index.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="text-uppercase bg-light w-50 mx-auto mt-5 text-center fs-4 border fw-bold" style="padding: 30px 0;">
            User profile
        </div>
        <div class="w-50 mx-auto mb-3 border px-5 py-3">

            <div>
                <c:if test="${not empty requestScope.PROFILE_ERROR}">
                    <div class="bg-danger text-light px-2 py-4 w-100 text-center" style="border-radius: 10px;">${requestScope.PROFILE_ERROR}</div>
                </c:if>       
                <form action="UpdateProfileServlet" method="POST">
                    <div class="input-group mt-3">
                        <span class="input-group-text" id="basic-addon1">Full name</span>
                        <input name="fullName" value="${sessionScope.user.fullName}" type="text" class="form-control" placeholder="Full name" aria-label="Full name" aria-describedby="basic-addon1">
                    </div>
                    <small class="text-danger">${requestScope.ERROR.fullNameError}</small>
                    <div class="input-group mt-3">
                        <span class="input-group-text" id="basic-addon1">Email</span>
                        <input name="email" value="${sessionScope.user.email}" type="text" class="form-control" placeholder="Email" aria-label="Email" aria-describedby="basic-addon1">
                    </div>
                    <small class="text-danger">${requestScope.ERROR.emailError}</small>
                    <div class="input-group mt-3">
                        <span class="input-group-text" id="basic-addon1">Phone number</span>
                        <input name="phoneNumber" value="${sessionScope.user.phoneNumber}" type="text" class="form-control" placeholder="Phone number" aria-label="Phone number" aria-describedby="basic-addon1">
                    </div>
                    <small class="text-danger">${requestScope.ERROR.phoneError}</small>
                    <div class="form-group mt-3">
                        <label>Address</label>
                        <textarea name="address" class="form-control">${sessionScope.user.address}</textarea>
                    </div>
                    <small class="text-danger">${requestScope.ERROR.addressError}</small>
                    
                    <input type="hidden" name="warn" value="${requestScope.PROFILE_ERROR}" />
                    <input type="hidden" name="userID" value="${sessionScope.user.userID}"/>
                    <small class="text-success my-2 d-block">${requestScope.MSG}</small>
                    <input type="submit" value="Update profile" class="btn btn-success d-block w-100 text-uppercase my-2" style="height: 50px;"/>
                </form>
            </div>
        </div>
    </body>

    <script src="https://code.jquery.com/jquery-2.2.4.js"></script>   

</html>
