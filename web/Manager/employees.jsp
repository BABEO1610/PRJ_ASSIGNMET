<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Nhân Viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/employees.css">
</head>
<body>
    <div class="top-bar">
    Quản lý
</div>
<div class="app-container">
    <div class="sidebar">
        <div class="user-profile">
            <div class="avatar-circle">
                <svg width="60" height="60" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg>
            </div>
            <h4>Tên: ${sessionScope.USER_INFO.fullName}</h4>
        </div>
        
        <a href="Home" class="menu-item ">Trang chủ</a>
        <a href="Apartments" class="menu-item">Quản lý căn hộ</a>
        <a href="Employees" class="menu-item active">Quản lý nhân viên</a>
        <a href="Requests" class="menu-item">Quản lý yêu cầu cư dân</a>
        <a href="Notifications" class="menu-item">Thông báo</a>
        <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
    </div>

    <div class="main-content">
        <div class="content-section">
            <div class="emp-header">
                <h3>Danh sách nhân viên</h3>
                <a href="Employees?action=add" class="btn-add-new">+ Thêm mới</a>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Họ Tên</th>
                        <th>Email</th>
                        <th>Chức vụ</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listEmp}" var="e">
                        <tr>
                            <td>${e.fullName}</td>
                            <td>${e.email}</td>
                            <td>${e.staffProfile.position.positionName}</td>
                            <td>
                                <a href="Employees?action=edit&id=${e.userId}" class="action-link btn-update-ui">Sửa</a>
                                <form action="Employees" method="POST" style="display:inline;" onsubmit="return confirm('Bạn chắc chắn muốn xóa nhân viên này?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${e.userId}">
                                    <button type="submit" class="action-link btn-delete-ui">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>