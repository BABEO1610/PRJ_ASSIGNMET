<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý yêu cầu cư dân</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/requests.css">
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

                <a href="Home" class="menu-item">Trang chủ</a>
                <a href="Apartments" class="menu-item">Quản lý căn hộ</a>
                <a href="employees" class="menu-item">Quản lý nhân viên</a>
                <a href="Requests" class="menu-item active">Quản lý yêu cầu cư dân</a>
                <a href="Notifications" class="menu-item">Thông báo</a>
                <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
            </div>

            <div class="main-content">


                <div class="content-section">

                    <div class="section-header">
                        <h3>Danh sách yêu cầu</h3>
                        <div class="pagination-controls">
                            <button onclick="prevPage()">❮ Trước</button>
                            <span id="page-info">Trang 1</span>
                            <button onclick="nextPage()">Sau ❯</button>
                        </div>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>STT</th>
                                <th>Yêu cầu</th>
                                <th>Cư dân</th>
                                <th>Phòng</th>
                                <th>Trạng thái</th>
                                <th>Ngày tạo</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="req-tbody">
                            <c:forEach items="${listReq}" var="r" varStatus="loop">
                                <tr>
                                    <td>${loop.count}</td>
                                    <td>${r.title}</td>
                                    <td>${r.residentName}</td>
                                    <td>${r.apartmentNumber}</td>
                                    <td>${r.status}</td>
                                    <td>
                                        <fmt:formatDate value="${r.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${r.status == 'Pending' || r.status == 'Cancelled'}">
                                                <button type="button" class="btn-primary"
                                                        onclick="location.href = 'AssignRequest?id=${r.requestId}'">
                                                    Giao việc
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary" style="color: gray;">
                                                    Đã giao
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>

        <script>
            const ROWS_PER_PAGE = 14;
            let currentPage = 1;

            function renderTable() {
                const tbody = document.getElementById('req-tbody');
                const rows = tbody.getElementsByTagName('tr');
                const totalRows = rows.length;
                const totalPages = Math.ceil(totalRows / ROWS_PER_PAGE) || 1;

                if (currentPage < 1)
                    currentPage = 1;
                if (currentPage > totalPages)
                    currentPage = totalPages;

                document.getElementById('page-info').innerText =
                        'Trang ' + currentPage + ' / ' + totalPages;

                const start = (currentPage - 1) * ROWS_PER_PAGE;
                const end = start + ROWS_PER_PAGE;

                for (let i = 0; i < totalRows; i++) {
                    rows[i].style.display = (i >= start && i < end) ? '' : 'none';
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