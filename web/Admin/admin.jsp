<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Tài khoản Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/admin.css">
</head>
<body>

<div class="top-bar">Cổng quản trị hệ thống</div>

<div class="full-page-content">
    <div class="data-container">
        <div class="section-header">
            <h3>Danh sách tài khoản người dùng</h3>
            <div class="pagination-controls">
                <button onclick="prevPage('acc')">❮ Trước</button>
                <span id="acc-page-info">Trang 1</span>
                <button onclick="nextPage('acc')">Sau ❯</button>
            </div>
        </div>

        <table class="data-table">
            <thead>
                <tr>
                    <th>Tài khoản</th>
                    <th>Họ và tên</th>
                    <th>Vai trò (ID)</th> <th>Email</th>
                    <th>Trạng thái</th> <th style="width: 180px;">Hành động</th>
                </tr>
            </thead>
            <tbody id="acc-tbody">
                <c:forEach items="${listUsers}" var="u">
                    <tr>
                        <td><strong>${u.username}</strong></td>
                        <td>${u.fullName}</td>
                        <td>
                            <span class="role-badge">
                                <c:choose>
                                    <c:when test="${u.role.roleId == 1}">Quản trị viên</c:when>
                                    <c:when test="${u.role.roleId == 2}">Quản lý</c:when>
                                    <c:otherwise>Nhân viên/Cư dân</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td>${u.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.isActive}">
                                    <span style="color:green; font-weight:bold;">Hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color:red; font-weight:bold;">Khóa</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div class="btn-group">
                                <button class="btn btn-edit" onclick="window.location.href='EditAccount?id=${u.userId}'">Sửa</button>
                                <button class="btn btn-delete" onclick="confirmDelete('${u.userId}')">Xóa</button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="footer-actions">
            <button class="btn btn-logout" onclick="window.location.href='${pageContext.request.contextPath}/Logout'">Đăng xuất</button>
            <button class="btn btn-main-add" onclick="window.location.href='${pageContext.request.contextPath}/Admin/add-account.jsp'">+ Thêm tài khoản mới</button>
        </div>
    </div>
</div>

<script>
    const ROWS_PER_PAGE = 10; // Giới hạn 10 hàng/trang theo yêu cầu
    let pages = { acc: 1 };

    function renderTable(tableType) {
        const tbody = document.getElementById(tableType + '-tbody');
        if (!tbody) return;
        const rows = tbody.getElementsByTagName('tr');
        const totalRows = rows.length;
        const totalPages = Math.ceil(totalRows / ROWS_PER_PAGE) || 1;
        
        if (pages[tableType] < 1) pages[tableType] = 1;
        if (pages[tableType] > totalPages) pages[tableType] = totalPages;
        let currentPage = pages[tableType];

        document.getElementById(tableType + '-page-info').innerText = 'Trang ' + currentPage + ' / ' + totalPages;
        const start = (currentPage - 1) * ROWS_PER_PAGE;
        const end = start + ROWS_PER_PAGE;

        for (let i = 0; i < totalRows; i++) {
            rows[i].style.display = (i >= start && i < end) ? '' : 'none';
        }
    }

    function nextPage(tableType) { pages[tableType]++; renderTable(tableType); }
    function prevPage(tableType) { pages[tableType]--; renderTable(tableType); }

    window.onload = () => renderTable('acc');

    function confirmDelete(id) {
        if(confirm("Xác nhận xóa tài khoản ID: " + id + "?")) {
            window.location.href = "DeleteAccount?id=" + id;
        }
    }
</script>

</body>
</html>