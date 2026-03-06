<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giao việc</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/AssignRequest.css">
</head>
<body>

<div class="top-bar">
    Giao việc
</div>

<div class="app-container">

    <!-- SIDEBAR copy nguyên từ Requests.jsp -->

    <div class="main-content">
        <div class="banner">Giao việc cho nhân viên</div>

        <div class="content-section">

            <h3>Danh sách nhân viên</h3>

            <table>
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên nhân viên</th>
                        <th>Chức vụ</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listStaff}" var="s" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td>${s.fullName}</td>
                            <td>${s.positionName}</td>
                            <td>
                                <form action="AssignRequest" method="post" style="margin:0;">
                                    <input type="hidden" name="requestId" value="${requestId}">
                                    <input type="hidden" name="staffId" value="${s.userId}">
                                    <button type="submit"
                                            style="background:#355872; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;">
                                        Giao việc
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="action-bar">
                <button type="button" 
                        onclick="window.location.href='${pageContext.request.contextPath}/Requests'">
                    Quay lại &rarr;
                </button>
            </div>

        </div>
    </div>
</div>

</body>
</html>