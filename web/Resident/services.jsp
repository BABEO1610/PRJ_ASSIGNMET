<%-- 
    Document   : services
    Created on : Mar 3, 2026, 5:47:38 PM
    Author     : Wind-Fubukii
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dịch vụ - Cư dân</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/services.css">
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
                <div class="banner">Dịch vụ của bạn</div>

                <div class="content-section">

                    <div class="section-header">
                        <h3>Dịch vụ đã đăng ký</h3>
                        <a href="Services?action=add" class="btn-add-service">
                            Thêm dịch vụ
                        </a>
                    </div>

                    <c:choose>
                        <c:when test="${empty myServices}">
                            <div class="empty-message">
                                Bạn chưa đăng ký dịch vụ nào.
                            </div>
                        </c:when>

                        <c:otherwise>
                            <table>
                                <tr>
                                    <th>Tên dịch vụ</th>
                                    <th>Đơn vị</th>
                                    <th>Giá</th>
                                    <th>Hành động</th>
                                </tr>

                                <c:forEach items="${myServices}" var="s">
                                    <tr>
                                        <td>${s.serviceName}</td>
                                        <td>${s.unit}</td>
                                        <td>${s.pricePerUnit}</td>
                                        <td>
                                            <form method="post" action="Services"
                                                  onsubmit="return confirm('Bạn có chắc chắn muốn hủy dịch vụ này không?');">
                                                <input type="hidden" name="action" value="cancel"/>
                                                <input type="hidden" name="serviceId" value="${s.serviceTypeId}"/>
                                                <button type="submit" class="btn-cancel-service">
                                                    Hủy
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>

                            <div class="pagination">

                                <c:if test="${currentPage > 1}">
                                    <a href="Services?page=${currentPage-1}"><</a>
                                </c:if>

                                <span>Trang ${currentPage} / ${totalPages}</span>

                                <c:if test="${currentPage < totalPages}">
                                    <a href="Services?page=${currentPage+1}">></a>
                                </c:if>

                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>

        </div>

    </body>
</html>