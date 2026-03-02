<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thông báo mới</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/notification.css">
</head>
<body>

<div class="top-bar">Quản lý</div>

<div class="app-container">

    <div class="sidebar">
        <a href="Home" class="menu-item">Trang chủ</a>
        <a href="Notifications" class="menu-item active">Thông báo</a>
    </div>

    <div class="main-content">
        <div class="content-section">

            <h3>Thông báo mới</h3>

            <form action="Notifications" method="post">
                <div style="margin-bottom:15px;">
                    <label>Tiêu đề</label><br>
                    <input type="text" name="title" required style="width:100%; padding:8px;">
                </div>

                <div style="margin-bottom:15px;">
                    <label>Nội dung</label><br>
                    <textarea name="content" rows="5" required style="width:100%; padding:8px;"></textarea>
                </div>

                <button type="submit" class="menu-item" 
                        style="width:auto; padding:8px 20px;">
                    Đăng
                </button>

            </form>

        </div>
    </div>
</div>

</body>
</html>