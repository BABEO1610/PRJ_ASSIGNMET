<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Tài Khoản Mới</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/add-account.css">
</head>
<body>

<div class="top-bar">Hệ thống quản trị - Thêm tài khoản</div>

<div class="main-content-centered">
    <div class="form-container">
        <h2>Tạo tài khoản mới</h2>
        <c:if test="${not empty error}">
            <p style="color: red; text-align: center;">${error}</p>
        </c:if>
        
        <form action="AddAccount" method="POST">
            <div class="form-group">
                <label>Tên đăng nhập:</label>
                <input type="text" name="username" required placeholder="admin_01">
            </div>
            
            <div class="form-group">
                <label>Mật khẩu:</label>
                <input type="password" name="password" required placeholder="********">
            </div>
            
            <div class="form-group">
                <label>Họ và tên:</label>
                <input type="text" name="fullName" required placeholder="Nguyễn Văn A">
            </div>
            
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" required placeholder="example@email.com">
            </div>
            
            <div class="form-group">
                <label>Số điện thoại:</label>
                <input type="text" name="phone" placeholder="090xxxxxxx">
            </div>
            
            <div class="form-group">
                <label>Vai trò hệ thống:</label>
                <select name="roleId">
                    <option value="1">Quản trị viên (Admin)</option>
                    <option value="2">Quản lý (Manager)</option>
                    <option value="3">Nhân viên (Staff)</option>
                    <option value="4" selected>Cư dân (Resident)</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="button" class="btn btn-cancel" onclick="window.location.href='Home_Admin'">Hủy bỏ</button>
                <button type="submit" class="btn btn-save">Lưu tài khoản</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>