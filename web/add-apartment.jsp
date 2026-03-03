<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm căn hộ mới</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/add-apartment.css">

</head>
<body>

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
        <a href="Home" class="menu-item">Trang chủ</a>
        <a href="Apartments" class="menu-item active">Quản lý căn hộ</a>
        <a href="Employees" class="menu-item">Quản lý nhân viên</a>
        <a href="Requests" class="menu-item">Quản lý yêu cầu cư dân</a>
        <a href="Logout" class="menu-item" style="margin-top: auto;">Đăng xuất</a>
    </div>

    <div class="main-content">
        <div class="content-section">
            <div class="section-header">
                <h3 style="color: #355872;">Thêm Căn Hộ Mới</h3>
            </div>

            <div class="form-container">
                <form action="AddApartmentController" method="POST">
                    <div class="form-group">
                        <label for="apartmentNumber">Số căn hộ:</label>
                        <input type="text" id="apartmentNumber" name="apartmentNumber" required placeholder="Ví dụ: P205">
                    </div>

                    <div class="form-group">
                        <label for="floor">Tầng:</label>
                        <input type="number" id="floor" name="floor" required min="1" placeholder="Ví dụ: 2">
                    </div>

                    <div class="form-group">
                        <label for="area">Diện tích (m²):</label>
                        <input type="number" step="0.1" id="area" name="area" required placeholder="Ví dụ: 75.5">
                    </div>

                    <div class="form-group">
                        <label for="types">Loại căn hộ:</label>
                        <select id="types" name="types" required>
                            <option value="">-- Chọn loại --</option>
                            <option value="1 Phòng Ngủ">1 Phòng Ngủ</option>
                            <option value="2 Phòng Ngủ">2 Phòng Ngủ</option>
                            <option value="3 Phòng Ngủ">3 Phòng Ngủ</option>
                            <option value="Penthouse">Penthouse</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="status">Trạng thái:</label>
                        <select id="status" name="status" required>
                            <option value="Trống">Trống</option>
                            <option value="Đang ở">Đang ở</option>
                            <option value="Bảo trì">Bảo trì</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="ownerId">Chủ sở hữu (ID):</label>
                        <input type="number" id="ownerId" name="ownerId" placeholder="Nhập ID người dùng (Nếu có)">
                    </div>

                    <div class="form-actions">
                        <button type="button" class="btn btn-delete" onclick="window.location.href='Apartments'">Hủy bỏ</button>
                        <button type="submit" class="btn btn-main-add">Lưu thông tin</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>