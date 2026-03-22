<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Công việc của nhân viên</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/home.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/staff-tasks.css">
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
                        <path fill-rule="evenodd"
                              d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                    </div>
                    <h4>Tên: ${sessionScope.USER_INFO.fullName}</h4>
                </div>
                <a href="Home_Staffs" class="menu-item">Trang chủ</a>
                <a href="StaffTasks" class="menu-item active">Công việc của bạn</a>
                <a href="Logout" class="menu-item" style="margin-top:20px;">Đăng xuất</a>
            </div>
            <!-- MAIN CONTENT -->
            <div class="main-content">
                <!-- ================= Incoming Tasks ================= -->
                <div class="content-section">

                    <div class="section-header">
                        <h3>Công việc được giao</h3>

                        <div class="pagination-controls">
                            <button onclick="prevPage('working')">❮ Trước</button>
                            <span id="working-page-info">Trang 1</span>
                            <button onclick="nextPage('working')">Sau ❯</button>
                        </div>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Cư dân</th>
                                <th>Phòng</th>
                                <th>Yêu cầu</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>

                        <tbody id="working-tbody">
                            <c:forEach items="${workingTasks}" var="r">
                                <tr>
                                    <td>${r.residentName}</td>
                                    <td>${r.apartmentNumber}</td>
                                    <td>${r.title}</td>
                                    <td>${r.status}</td>
                                    <td>
                                        <a href="TaskDetail?requestId=${r.requestId}">
                                            <button class="btn-primary">Chi tiết</button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
                <!-- ================= Completed Tasks ================= -->
                <div class="content-section">

                    <div class="section-header">
                        <h3>Công việc đã hoàn thành</h3>

                        <div class="pagination-controls">
                            <button onclick="prevPage('done')">❮ Trước</button>
                            <span id="done-page-info">Trang 1</span>
                            <button onclick="nextPage('done')">Sau ❯</button>
                        </div>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Cư dân</th>
                                <th>Phòng</th>
                                <th>Yêu cầu</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>

                        <tbody id="done-tbody">
                            <c:forEach items="${completedTasks}" var="r">
                                <tr>
                                    <td>${r.residentName}</td>
                                    <td>${r.apartmentNumber}</td>
                                    <td>${r.title}</td>
                                    <td>${r.status}</td>
                                    <td>
                                        <a href="TaskDetail?requestId=${r.requestId}">
                                            <button class="btn-primary">Chi tiết</button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
        <script>

            const ROWS_PER_PAGE = 5;
            let currentPage = 1;

            function renderTable() {

                const tbody = document.getElementById("req-tbody");
                const rows = tbody.getElementsByTagName("tr");

                const totalRows = rows.length;
                const totalPages = Math.ceil(totalRows / ROWS_PER_PAGE) || 1;

                if (currentPage < 1)
                    currentPage = 1;
                if (currentPage > totalPages)
                    currentPage = totalPages;

                document.getElementById("page-info").innerText =
                        "Trang " + currentPage + " / " + totalPages;

                const start = (currentPage - 1) * ROWS_PER_PAGE;
                const end = start + ROWS_PER_PAGE;

                for (let i = 0; i < totalRows; i++) {

                    if (i >= start && i < end) {
                        rows[i].style.display = "";
                    } else {
                        rows[i].style.display = "none";
                    }

                }
            }

            function nextPage() {
                currentPage++;
                renderTable();
            }

            function prevPage() {
                currentPage--;
                renderTable();
            }

            window.onload = renderTable;

        </script>
    </body>
</html>