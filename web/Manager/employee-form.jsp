<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${emp != null ? 'Chỉnh sửa' : 'Thêm mới'} Nhân Viên</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/home.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/employee-form.css">
    </head>
    <body>
        <div class="app-container">
            <div class="main-content">
                <div class="form-wrapper">
                    <h3 class="form-title">${emp != null ? 'Cập nhật' : 'Thêm mới'} nhân viên</h3>

                    <form action="Employees" method="POST">
                        <input type="hidden" name="action" value="${emp != null ? 'update' : 'insert'}">

                        <c:if test="${emp != null}">
                            <input type="hidden" name="id" value="${emp.userId}">
                        </c:if>

                        <div class="row-input">
                            <label>Tên đăng nhập (Username):</label>
                            <input type="text" name="username" value="${emp.username}" 
                                   ${emp != null ? 'readonly class="lock-input"' : 'required'}>
                        </div>

                        <c:if test="${emp == null}">
                            <div class="row-input">
                                <label>Mật khẩu:</label>
                                <input type="password" name="password" required>
                            </div>
                        </c:if>

                        <div class="row-input">
                            <label>Họ Tên:</label>
                            <input type="text" name="fullname" value="${emp.fullName}" required>
                        </div>

                        <div class="row-input">
                            <label>Email:</label>
                            <input type="email" name="email" value="${emp.email}">
                        </div>

                        <div class="row-input">
                            <label>Số điện thoại:</label>
                            <input type="text" name="phone" value="${emp.phone}">
                        </div>

                        <div class="row-input">
                            <label>Chức vụ:</label>
                            <select name="positionId">
                                <c:forEach items="${listP}" var="p">
                                    <option value="${p.positionId}" ${emp.staffProfile.position.positionId == p.positionId ? 'selected' : ''}>
                                        ${p.positionName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <button type="submit" class="btn-save-data">Lưu thông tin</button>
                    </form>

                    <p style="text-align:center; margin-top:15px;">
                        <a href="Employees" style="color:#355872; font-weight:bold; text-decoration:none;">❮ Hủy bỏ</a>
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>