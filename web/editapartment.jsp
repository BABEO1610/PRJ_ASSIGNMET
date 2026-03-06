<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa căn hộ</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/editapartment.css">
</head>
<body>
    <div class="top-bar">Chỉnh sửa căn hộ</div>
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
        
        <a href="Home" class="menu-item active">Trang chủ</a>
        <a href="Apartments" class="menu-item">Quản lý căn hộ</a>
        <a href="employees" class="menu-item">Quản lý nhân viên</a>
        <a href="Requests" class="menu-item">Quản lý yêu cầu cư dân</a>
        <a href="Notifications" class="menu-item">Thông báo</a>
        <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
    </div>
        
        <div class="main-content-centered">
            <div class="form-container">
                <h2>Cập nhật thông tin</h2>
                <form action="EditApartmentController" method="POST">
                    <input type="hidden" name="apartmentId" value="${apartment.apartmentId}">
                    
                    <div class="form-group">
                        <label>Số căn hộ:</label>
                        <input type="text" name="apartmentNumber" value="${apartment.apartmentNumber}" required>
                    </div>
                    <div class="form-group">
                        <label>Tầng:</label>
                        <input type="number" name="floor" value="${apartment.floor}" required>
                    </div>
                    <div class="form-group">
                        <label>Diện tích:</label>
                        <input type="number" step="0.1" name="area" value="${apartment.area}" required>
                    </div>
                    <div class="form-group">
                        <label>Loại phòng:</label>
                        <input type="text" name="types" value="${apartment.types}">
                    </div>
                    <div class="form-group">
                        <label>Trạng thái:</label>
                        <select name="status">
                            <option value="Trống" ${apartment.status == 'Trống' ? 'selected' : ''}>Trống</option>
                            <option value="Đang ở" ${apartment.status == 'Đang ở' ? 'selected' : ''}>Đang ở</option>
                            <option value="Bảo trì" ${apartment.status == 'Bảo trì' ? 'selected' : ''}>Bảo trì</option>
                        </select>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="btn btn-delete" onclick="history.back()">Hủy</button>
                        <button type="submit" class="btn btn-edit">Lưu thay đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>