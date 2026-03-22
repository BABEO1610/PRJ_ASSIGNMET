<%-- 
    Document   : notification-detail-staff
    Created on : Mar 5, 2026, 12:09:19 PM
    Author     : Wind-Fubukii
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Chi tiết thông báo</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/notification.css">
    </head>

    <body>

        <div class="top-bar">Nhân viên</div>

        <div class="app-container">

            <div class="sidebar">

                <div class="user-profile">
                    <div class="avatar-circle">
                        <svg width="60" height="60" fill="currentColor" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd"
                              d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                    </div>

                    <h4>Tên: ${sessionScope.USER_INFO.fullName}</h4>
                </div>

                <a href="Home_Staffs" class="menu-item">Trang chủ</a>
                <a href="#" class="menu-item">Công việc của bạn</a>
                <a href="Logout" class="menu-item" style="margin-top:20px;">Đăng xuất</a>

            </div>


            <div class="main-content">
                <div class="content-section">

                    <h3>${notif.title}</h3>

                    <p><b>Người gửi:</b> ${notif.senderName}</p>

                    <p>
                        <b>Ngày tạo:</b>
                        <fmt:formatDate value="${notif.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
                    </p>

                    <hr>

                    <p style="white-space:pre-line">
                        ${notif.content}
                    </p>

                    <br>

                    <a href="Home_Staffs" class="menu-item"
                       style="width:auto;padding:8px 20px">
                        Quay lại
                    </a>

                </div>
            </div>

        </div>
    </body>
</html>