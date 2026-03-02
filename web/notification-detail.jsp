<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết thông báo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/notification.css">
</head>
<body>

<div class="top-bar">Quản lý</div>

<div class="app-container">

    <div class="sidebar">
        <div class="user-profile">
            <div class="avatar-circle"></div>
            <h4>Tên: ${sessionScope.USER_INFO.fullName}</h4>
        </div>

        <a href="Home" class="menu-item">Trang chủ</a>
        <a href="Notifications" class="menu-item active">Thông báo</a>
        <a href="Logout" class="menu-item" style="margin-top:20px;">Đăng xuất</a>
    </div>

    <div class="main-content">
        <div class="content-section">

            <h3>${notif.title}</h3>

            <p><b>Người tạo:</b> ${notif.senderName}</p>
            <p><b>Ngày tạo:</b> 
                <fmt:formatDate value="${notif.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
            </p>

            <hr>

            <p style="white-space: pre-line;">
                ${notif.content}
            </p>

            <br>

            <a href="Notifications" class="menu-item" 
               style="width:auto; padding:8px 20px;">
                Quay lại
            </a>

        </div>
    </div>

</div>
</body>
</html>