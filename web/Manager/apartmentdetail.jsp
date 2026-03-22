<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết căn hộ</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/apartmentdetail.css">
</head>
<body>
    <div class="top-bar">Thông tin chi tiết</div>
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
        <a href="Employees" class="menu-item">Quản lý nhân viên</a>
        <a href="Requests" class="menu-item">Quản lý yêu cầu cư dân</a>
        <a href="Notifications" class="menu-item">Thông báo</a>
        <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
    </div>
        
        <div class="main-content-centered">
            <div class="detail-card">
                <h2>Căn hộ: ${apartment.apartmentNumber}</h2>
                <hr>
                <div class="info-item"><strong>Mã hệ thống:</strong> ${apartment.apartmentId}</div>
                <div class="info-item"><strong>Tầng:</strong> ${apartment.floor}</div>
                <div class="info-item"><strong>Diện tích:</strong> ${apartment.area} m²</div>
                <div class="info-item"><strong>Loại phòng:</strong> ${apartment.types}</div>
                <div class="info-item"><strong>Trạng thái:</strong> ${apartment.status}</div>
                <div class="info-item"><strong>Chủ sở hữu:</strong> ${apartment.ownerName != null ? apartment.ownerName : 'N/A'}</div>
                
                <div style="margin-top: 30px; text-align: center;">
                    <button class="btn btn-view" onclick="history.back()">Quay lại</button>
                    <button class="btn btn-edit" onclick="window.location.href='EditApartmentController?id=${apartment.apartmentId}'">Sửa thông tin</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>