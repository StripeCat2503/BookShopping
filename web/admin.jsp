
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/admin.css"/>
        
        <style>
            img{
                width: 100%;
                opacity: 0.5;
                transition: 0.5s;
                cursor: pointer;
            }
            img:hover{
                opacity: 1;
            }
        </style>
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
        <div class="content d-flex justify-content-center align-items-center mt-4" style="flex-direction: column">
            <div>
                <h1 class="text-uppercase">Welcome!</h1>
            </div>
            <div style="width: 30%" class="mt-4">
                <img src="images/laughing.png"/>
            </div>
        </div>
        
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
