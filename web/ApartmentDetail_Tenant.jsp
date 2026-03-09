<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Căn hộ</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/home.css">
    <style>
        .info-card { background: #fcfcfc; border: 1px solid #9CD5FF; padding: 20px; border-radius: 8px; margin-bottom: 30px; }
        .info-card p { margin: 8px 0; font-size: 16px; }
        .back-link { display: inline-block; margin-bottom: 20px; text-decoration: none; color: #355872; font-weight: bold; }
    </style>
</head>
<body>
<div class="top-bar">Chi tiết Căn hộ ${apt.apartmentNumber}</div>
<div class="app-container">
    <div class="main-content">
        <div class="content-section">
            <a href="MyManagedApartments" class="back-link">❮ Quay lại danh sách</a>
            
            <div class="info-card">
                <h3 style="margin-top: 0; color: #355872;">Thông tin phòng: ${apt.apartmentNumber}</h3>
                <p><strong>Tầng:</strong> ${apt.floor}</p>
                <p><strong>Diện tích:</strong> ${apt.area} m²</p>
                <p><strong>Loại căn hộ:</strong> ${apt.types}</p>
                <p><strong>Trạng thái:</strong> ${apt.status}</p>
            </div>

            <div class="section-header">
                <h3>Lịch sử Hóa đơn (Bills)</h3>
                <c:if test="${not empty listBills}">
                    <div class="pagination-controls">
                        <button onclick="prevPage()">❮ Trước</button>
                        <span id="bill-page-info">Trang 1</span>
                        <button onclick="nextPage()">Sau ❯</button>
                    </div>
                </c:if>
            </div>
            
            <c:choose>
                <c:when test="${empty listBills}">
                    <div style="text-align: center; padding: 20px; color: #7AAACE;">Chưa có hóa đơn nào cho căn hộ này.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Mã HĐ</th>
                                <th>Loại dịch vụ</th>
                                <th>Kỳ thu</th>
                                <th>Số tiền (VNĐ)</th>
                                <th>Trạng thái</th>
                                <th>Ngày lập</th>
                            </tr>
                        </thead>
                        <tbody id="bill-tbody">
                            <c:forEach items="${listBills}" var="b">
                                <tr>
                                    <td>#${b.billId}</td>

                                    <td><strong>${b.serviceName}</strong></td>

                                    <td>${b.billingMonth}/${b.billingYear}</td>

                                    <td><fmt:formatNumber value="${b.totalAmount}" type="number"/> đ</td>

                                    <td>
                                        <span style="font-weight: bold; color: ${b.status == 'Paid' ? '#7AAACE' : '#e74c3c'};">
                                            ${b.status}
                                        </span>
                                    </td>

                                    <td><fmt:formatDate value="${b.createdAt}" pattern="dd/MM/yyyy"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script>
    // Phân trang 5 dòng cho Hóa đơn
    const ROWS_PER_PAGE = 5;
    let currentPage = 1;

    function renderTable() {
        const tbody = document.getElementById('bill-tbody');
        if (!tbody) return;
        
        const rows = tbody.getElementsByTagName('tr');
        const totalPages = Math.ceil(rows.length / ROWS_PER_PAGE) || 1;
        
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        document.getElementById('bill-page-info').innerText = 'Trang ' + currentPage + ' / ' + totalPages;

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