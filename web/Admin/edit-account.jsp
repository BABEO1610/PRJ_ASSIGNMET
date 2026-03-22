<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa Tài khoản</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/edit-account.css">
</head>
<body>

<div class="top-bar">Hệ thống quản trị - Chỉnh sửa thông tin</div>

<div class="main-content-centered">
    <div class="form-container">
        <h2>Cập nhật tài khoản: ${userEdit.username}</h2>
        
        <form action="EditAccount" method="POST">
            <input type="hidden" name="userId" value="${userEdit.userId}">
            
            <div class="form-group">
                <label>Họ và tên:</label>
                <input type="text" name="fullName" value="${userEdit.fullName}" required>
            </div>
            
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" value="${userEdit.email}" required>
            </div>
            
            <div class="form-group">
                <label>Số điện thoại:</label>
                <input type="text" name="phone" value="${userEdit.phone}">
            </div>
            
            <div class="form-group">
                <label>Vai trò hệ thống:</label>
                <select name="roleId">
                    <option value="1" ${userEdit.role.roleId == 1 ? 'selected' : ''}>Quản trị viên (Admin)</option>
                    <option value="2" ${userEdit.role.roleId == 2 ? 'selected' : ''}>Quản lý (Manager)</option>
                    <option value="3" ${userEdit.role.roleId == 3 ? 'selected' : ''}>Nhân viên (Staff)</option>
                    <option value="4" ${userEdit.role.roleId == 4 ? 'selected' : ''}>Cư dân (Resident)</option>
                </select>
            </div>

            <div class="form-group" style="display: flex; align-items: center; gap: 10px;">
                <input type="checkbox" name="isActive" ${userEdit.isActive ? 'checked' : ''} style="width: auto;">
                <label style="margin-bottom: 0;">Kích hoạt tài khoản</label>
            </div>

            <div class="form-actions">
                <button type="button" class="btn btn-delete" onclick="window.location.href='Home_Admin'">Hủy bỏ</button>
                <button type="submit" class="btn btn-edit">Lưu thay đổi</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>