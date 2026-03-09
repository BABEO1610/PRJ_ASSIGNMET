<%-- 
    Document   : service-add
    Created on : Mar 3, 2026, 5:51:03 PM
    Author     : Wind-Fubukii
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm dịch vụ</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/services.css?v=2">
    </head>
    <body>

        <div class="top-bar">
            Cổng thông tin Cư dân
        </div>

        <div class="app-container">

            <div class="sidebar">
                <div class="user-profile">
                    <div class="avatar-circle">
                        <svg width="60" height="60" fill="currentColor" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd"
                              d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7
                              a7 7 0 0 0-5.468 11.37C3.242 11.226
                              4.805 10 8 10s4.757 1.225
                              5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                    </div>
                    <h4>Xin chào, ${sessionScope.USER_INFO.fullName}</h4>
                </div>

                <a href="Home_Residents" class="menu-item">Trang chủ</a>
                <a href="RoomInfo" class="menu-item">Thông tin căn hộ</a>
                <a href="Services" class="menu-item active">Dịch vụ</a>
                <a href="user-bills" class="menu-item">Thanh toán</a>
                <a href="Requirements" class="menu-item">Ý kiến</a>
                <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
            </div>

            <!-- MAIN -->
            <div class="main-content">
                <div class="banner">Thêm dịch vụ</div>

                <div class="content-section">

                    <h3>Chọn dịch vụ muốn đăng ký</h3>

                    <form method="post" action="Services" onsubmit="return validateService()">
                        <input type="hidden" name="action" value="register"/>

                        <table>
                            <tr>
                                <th>Chọn</th>
                                <th>Tên dịch vụ</th>
                                <th>Đơn vị</th>
                                <th>Giá</th>
                            </tr>

                            <c:forEach items="${allServices}" var="s">
                                <tr>
                                    <td>
                                        <input type="checkbox"
                                               name="serviceIds"
                                               value="${s.serviceTypeId}"/>
                                    </td>
                                    <td>${s.serviceName}</td>
                                    <td>${s.unit}</td>
                                    <td>${s.pricePerUnit}</td>
                                </tr>
                            </c:forEach>

                        </table>

                        <div class="pagination">

                            <c:if test="${currentPage > 1}">
                                <a href="Services?action=add&page=${currentPage-1}">◀</a>
                            </c:if>

                            <span>Trang ${currentPage} / ${totalPages}</span>

                            <c:if test="${currentPage < totalPages}">
                                <a href="Services?action=add&page=${currentPage+1}">▶</a>
                            </c:if>

                        </div>

                        <p id="errorMsg" class="error-message"></p>

                        <br/>

                        <a href="Services" class="btn-back-service">
                            Quay lại
                        </a>

                        <button type="submit" class="btn-add-service">
                            Thêm dịch vụ
                        </button>

                    </form>

                </div>
            </div>

        </div>
        <script>
            function validateService() {

                const checkboxes = document.querySelectorAll("input[name='serviceIds']");
                const error = document.getElementById("errorMsg");

                let checked = false;

                checkboxes.forEach(cb => {
                    if (cb.checked) {
                        checked = true;
                    }
                });

                if (!checked) {
                    error.innerText = "Chưa chọn dịch vụ nào để thêm!!";
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>