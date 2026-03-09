<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <div class="top-bar">
        Cổng thông tin Đại diện Khách thuê
    </div>
        <title>Căn hộ đang thuê</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/home.css">
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
            <h4>${sessionScope.USER_INFO.fullName}</h4>
        </div>

        <a href="Home_Tenant_representative" class="menu-item">Trang chủ</a>
        <a href="MyManagedApartments" class="menu-item active">Căn hộ đang thuê</a>
        <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
    </div>
    <div class="main-content">
        <div class="content-section">
            <div class="section-header">
                <h3>Danh sách Căn hộ quản lý</h3>
                <div class="pagination-controls">
                    <button onclick="prevPage('apt')">❮ Trước</button>
                    <span id="apt-page-info">Trang 1</span>
                    <button onclick="nextPage('apt')">Sau ❯</button>
                </div>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên Căn hộ</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody id="apt-tbody">
                    <c:forEach items="${listApt}" var="a" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td><strong>${a.apartmentNumber}</strong></td>
                            <td>
                                <a href="ApartmentDetail_Tenant?id=${a.apartmentId}" style="color: #355872; font-weight: bold;">Xem chi tiết & Hóa đơn ❯</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    // Phân trang 10 dòng cho Căn hộ
    const ROWS_PER_PAGE = 10;
    let currentPage = 1;

    function renderTable() {
        const rows = document.getElementById('apt-tbody').getElementsByTagName('tr');
        const totalPages = Math.ceil(rows.length / ROWS_PER_PAGE) || 1;
        
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        document.getElementById('apt-page-info').innerText = 'Trang ' + currentPage + ' / ' + totalPages;

        const start = (currentPage - 1) * ROWS_PER_PAGE;
        const end = start + ROWS_PER_PAGE;

        for (let i = 0; i < rows.length; i++) {
            rows[i].style.display = (i >= start && i < end) ? '' : 'none';
        }
    }

    function nextPage() { currentPage++; renderTable(); }
    function prevPage() { currentPage--; renderTable(); }
    window.onload = renderTable;
</script>
</body>
</html>