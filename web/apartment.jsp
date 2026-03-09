<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Căn hộ</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/apartment.css">
</head>
<body>
    <div class="top-bar">Quản lý căn hộ</div>
    <div class="app-container">
        <div class="sidebar">
            <div class="user-profile">
                <div class="avatar-circle">
                    <svg width="60" height="60" fill="currentColor" viewBox="0 0 16 16"><path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/><path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/></svg>
                </div>
               <h4>Tên: ${sessionScope.USER_INFO.fullName}</h4>
            </div>

            <a href="Home" class="menu-item">Trang chủ</a>
            <a href="Apartments" class="menu-item active">Quản lý căn hộ</a>
            <a href="Employees" class="menu-item">Quản lý nhân viên</a>
            <a href="Requests" class="menu-item">Quản lý yêu cầu cư dân</a>
            <a href="Notifications" class="menu-item">Thông báo</a>
            <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
        </div>
        <div class="main-content">
            <div class="content-section">
                <div class="section-header">
                    <h3>Danh sách căn hộ</h3>
                    <div class="pagination-controls">
                    <button onclick="prevPage('apt')">❮ Trước</button>
                    <span id="apt-page-info">Trang 1</span>
                    <button onclick="nextPage('apt')">Sau ❯</button>
                </div>
                </div>
               <table class="data-table">
                    <thead>
                        <tr>
                           <th>Số căn hộ</th>
                           <th>Chủ sở hữu</th>
                            <th style="width: 280px;">Hành động</th>
                        </tr>
                    </thead>
                    <tbody id="apt-tbody">
                        <c:forEach items="${listApts}" var="a">
                            <tr id="row-${a.apartmentId}">
                            <td>
                                <a href="ApartmentDetail?id=${a.apartmentId}" class="apt-link">
                                ${a.apartmentNumber}
                                </a>
                            </td>
        
                            <td>${a.ownerName != null ? a.ownerName : 'Chưa có'}</td>
        
                            <td>
                                <div class="btn-group">
                                    <button class="btn btn-edit" onclick="window.location.href='EditApartmentController?id=${a.apartmentId}'">Sửa</button>
                                <button class="btn btn-delete" onclick="confirmDelete('${a.apartmentId}')">Xóa</button>
                                </div>
                            </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div style="text-align: right; margin-top: 20px;">
                    <button class="btn btn-main-add" onclick="window.location.href='add-apartment.jsp'">+ Thêm căn hộ mới</button>
                </div>
           </div>
        </div>
    </div>

    <script>
        function confirmDelete(id) {
            if(confirm("Xác nhận xóa vĩnh viễn căn hộ mã " + id + " khỏi hệ thống?")) {
                window.location.href = "DeleteApartment?id=" + id;
           }
        }
    </script>
    <script>
    const ROWS_PER_PAGE = 10;
    let pages = { apt: 1 };

    function renderTable(tableType) {
        const tbody = document.getElementById(tableType + '-tbody');
        if (!tbody) return;      

        const rows = tbody.getElementsByTagName('tr');
        const totalRows = rows.length;
        const totalPages = Math.ceil(totalRows / ROWS_PER_PAGE) || 1;
        
        let currentPage = pages[tableType];        
        if (currentPage < 1) currentPage = 1
        if (currentPage > totalPages) currentPage = totalPages;
        pages[tableType] = currentPage;

        document.getElementById(tableType + '-page-info').innerText = 'Trang ' + currentPage + ' / ' + totalPages;

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

    function nextPage(tableType) { pages[tableType]++; renderTable(tableType); }
    function prevPage(tableType) { pages[tableType]--; renderTable(tableType); }

   // Chạy khi trang web tải xong
    window.onload = function() {
        renderTable('apt');
    };
</script>
</body>
</html>
