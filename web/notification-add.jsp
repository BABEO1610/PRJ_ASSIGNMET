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
                <div class="user-profile">
                    <div class="avatar-circle">
                        <svg width="60" height="60" fill="currentColor" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd"
                              d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                    </div>
                    <h4>Tên: ${sessionScope.USER_INFO.fullName}</h4>
                </div>

                <a href="Home" class="menu-item">Trang chủ</a>
                <a href="Apartments" class="menu-item">Quản lý căn hộ</a>
                <a href="employees" class="menu-item">Quản lý nhân viên</a>
                <a href="Requests" class="menu-item">Quản lý yêu cầu cư dân</a>
                <a href="Notifications" class="menu-item active">Thông báo</a>

                <a href="Logout" class="menu-item" style="margin-top:20px;">Đăng xuất</a>
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