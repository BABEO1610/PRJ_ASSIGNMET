<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin căn hộ của tôi</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/home.css">
    <style>
        .info-card {
            background-color: #F7F8F0;
            padding: 40px;
            border-radius: 15px;
            width: 100%;
            max-width: 600px;
            margin: 50px auto; /* Căn giữa card */
            border: 1px solid #9CD5FF;
            box-shadow: 0 10px 25px rgba(53, 88, 114, 0.1);
        }
        .info-card h2 { color: #355872; text-align: center; margin-bottom: 30px; }
        .info-row {
            display: flex;
            justify-content: space-between;
            padding: 15px 0;
            border-bottom: 1px dashed #7AAACE;
        }
        .info-label { font-weight: bold; color: #355872; }
        .info-value { color: #444; }
        .no-data { text-align: center; color: #e74c3c; font-style: italic; }
    </style>
</head>
<body>

<div class="top-bar">Cổng thông tin Cư dân - Chi tiết căn hộ</div>

<div class="app-container">
    <div class="sidebar">
        <div class="user-profile">
            <div class="avatar-circle">
                <svg width="60" height="60" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg>
            </div>
            <h4>Xin chào, ${sessionScope.USER_INFO.fullName}</h4>
        </div>
        <a href="Home_Residents" class="menu-item">Trang chủ</a>
        <a href="RoomInfo" class="menu-item active">Thông tin căn hộ</a>
        <a href="Services" class="menu-item">Dịch vụ</a>
        <a href="user-bills" class="menu-item">Thanh toán</a>
        <a href="Requirements" class="menu-item">Ý kiến</a>
        <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
    </div>

    <div class="main-content">
        <c:choose>
            <c:when test="${not empty myRoom}">
                <div class="info-card">
                    <h2>Thông tin căn hộ: ${myRoom.apartmentNumber}</h2>
                    <div class="info-row">
                        <span class="info-label">Tầng:</span>
                        <span class="info-value">${myRoom.floor}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Diện tích:</span>
                        <span class="info-value">${myRoom.area} m²</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Loại căn hộ:</span>
                        <span class="info-value">${myRoom.types}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Trạng thái:</span>
                        <span class="info-value">${myRoom.status}</span>
                    </div>
                    <div style="margin-top: 30px; text-align: center;">
                        <a class="menu-item" style="display:inline-block; border-radius:8px; border:none; cursor:pointer;" href="Home_Residents">Quay lại trang chủ</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="info-card">
                    <p class="no-data">Bạn hiện chưa được gán vào căn hộ nào trên hệ thống.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>