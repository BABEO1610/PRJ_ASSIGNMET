<%-- 
    Document   : Home_Staffs
    Created on : Mar 5, 2026, 11:13:06 AM
    Author     : Wind-Fubukii
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trang chủ Nhân viên</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/home.css">
    </head>

    <body>

        <div class="top-bar">
            Nhân viên
        </div>

        <div class="app-container">

            <!-- SIDEBAR -->
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

                <a href="Home_Staffs" class="menu-item active">Trang chủ</a>
                <a href="StaffTasks" class="menu-item">Công việc của bạn</a>
                <a href="Logout" class="menu-item" style="margin-top:20px;">Đăng xuất</a>

            </div>


            <!-- MAIN CONTENT -->

            <div class="main-content">

                <div class="banner">
                    Chào mừng nhân viên!
                </div>


                <div class="content-section">

                    <div class="section-header">
                        <h3>Thông báo từ quản lý</h3>

                        <div class="pagination-controls">
                            <button onclick="prevPage('notif')">❮ Trước</button>
                            <span id="notif-page-info">Trang 1</span>
                            <button onclick="nextPage('notif')">Sau ❯</button>
                        </div>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Tiêu đề</th>
                                <th>Người tạo</th>
                                <th>Ngày tạo</th>
                                <th>Nơi nhận</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>

                        <tbody id="notif-tbody">

                            <c:forEach items="${listNotif}" var="n">

                                <tr>

                                    <td>
                                        <a href="NotificationStaffDetail?id=${n.notificationId}"
                                           style="text-decoration:none;color:#355872;font-weight:bold">
                                            ${n.title}
                                        </a>
                                    </td>

                                    <td>${n.senderName}</td>

                                    <td>
                                        <fmt:formatDate value="${n.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
                                    </td>

                                    <td>${n.isGlobal ? 'Tất cả' : 'Cá nhân'}</td>

                                    <td>${n.isRead ? 'Đã gửi' : 'Mới'}</td>

                                </tr>

                            </c:forEach>

                        </tbody>
                    </table>

                </div>

            </div>

        </div>
        <script>
            const ROWS_PER_PAGE = 5;

            let pages = {notif: 1};

            function renderTable(tableType) {

                const tbody = document.getElementById(tableType + '-tbody');
                if (!tbody)
                    return;

                const rows = tbody.getElementsByTagName('tr');
                const totalRows = rows.length;
                const totalPages = Math.ceil(totalRows / ROWS_PER_PAGE) || 1;

                let currentPage = pages[tableType];

                if (currentPage < 1)
                    currentPage = 1;
                if (currentPage > totalPages)
                    currentPage = totalPages;

                pages[tableType] = currentPage;

                document.getElementById(tableType + '-page-info').innerText =
                        'Trang ' + currentPage + ' / ' + totalPages;

                const start = (currentPage - 1) * ROWS_PER_PAGE;
                const end = start + ROWS_PER_PAGE;

                for (let i = 0; i < totalRows; i++) {
                    if (i >= start && i < end) {
                        rows[i].style.display = '';
                    } else {
                        rows[i].style.display = 'none';
                    }
                }
            }

            function nextPage(tableType) {
                pages[tableType]++;
                renderTable(tableType);
            }

            function prevPage(tableType) {
                pages[tableType]--;
                renderTable(tableType);
            }

            window.onload = function () {
                renderTable('notif');
            };
        </script>
    </body>
</html>