<%-- 
    Document   : login
    Created on : Feb 25, 2026, 8:22:24 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập - Chung Cư</title>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/login.css">
    </head>
    <body>
        <div class="login-container">
            <h2>Đăng nhập hệ thống</h2>

            <p class="error-msg">${ERROR_MSG}</p>

            <form action="login" method="POST" class="login-form">
                <div class="input-group">
                    <label>Tài khoản:</label>
                    <input type="text" name="username" required placeholder="Nhập tên đăng nhập">
                </div>

                <div class="input-group">
                    <label>Mật khẩu:</label>
                    <input type="password" name="password" required placeholder="Nhập mật khẩu">
                </div>

                <button type="submit" class="btn-login">Đăng nhập</button>
            </form>
        </div>
    </body>
</html>
