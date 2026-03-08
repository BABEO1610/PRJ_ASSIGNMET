<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gửi Ý Kiến - Cư dân</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/home.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/requirements.css">
</head>
<body>

<div class="top-bar">Cổng thông tin Cư dân - Gửi yêu cầu</div>

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
        <a href="Room_info" class="menu-item">Thông tin căn hộ</a>
        <a href="Services" class="menu-item">Dịch vụ</a>
        <a href="Payment" class="menu-item">Thanh toán</a>
        <a href="Requirements" class="menu-item">Ý kiến</a>
        <a href="Logout" class="menu-item active" style="margin-top: 20px;">Đăng xuất</a>
    </div>

    <div class="main-content">
        <div class="content-section">
            <div class="section-header">
                <h3>Tạo yêu cầu / Ý kiến mới</h3>
            </div>

            <c:if test="${not empty successMsg}">
                <div class="alert alert-success">${successMsg}</div>
            </c:if>
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">${errorMsg}</div>
            </c:if>

            <div class="form-container">
                <form action="Requirements" method="POST">
                    <input type="hidden" name="residentId" value="${sessionScope.USER_INFO.userId}">
                    
                    <div class="form-group">
                        <label>Loại yêu cầu:</label>
                        <select name="requestTypeId" required>
                            <c:forEach var="type" items="${listTypes}">
                                <option value="${type.requestTypeId}">
                                    ${type.typeName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Tiêu đề:</label>
                        <input type="text" name="title" placeholder="Nhập tóm tắt vấn đề..." required>
                    </div>

                    <div class="form-group">
                        <label>Nội dung chi tiết:</label>
                        <textarea name="description" rows="5"
                        placeholder="Mô tả chi tiết yêu cầu của bạn..."></textarea>
                    </div>

                    <button type="submit" class="btn-submit">Gửi yêu cầu</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>