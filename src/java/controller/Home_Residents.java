package controller;

import dal.NotificationDAO;
import dal.UserDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Notifications;
import model.Requests;
import model.Users; // Giả sử model User của bạn tên là Users

@WebServlet(name = "ResidentHomeServlet", urlPatterns = {"/Home_Residents"})
public class Home_Residents extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Lấy session hiện tại
        HttpSession session = request.getSession();
        
        // 2. Lấy thông tin user đã đăng nhập từ Session (Tên attribute "USER_INFO" phải khớp với lúc bạn Login)
        Users currentUser = (Users) session.getAttribute("USER_INFO");
        
        // 3. Kiểm tra bảo mật: Nếu chưa đăng nhập thì đuổi về trang Login
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // 4. Lấy ID của Cư dân đang đăng nhập
        int residentId = currentUser.getUserId(); // Thay đổi tên hàm getUserId() cho khớp với Class Users của bạn
        
        // 5. Khởi tạo DAO và gọi các hàm lọc theo ID vừa tạo ở Bước 1
        UserDAO Dao = new UserDAO();
        
        List<Requests> myRequests = Dao.getRequestsByResidentId(residentId);
        List<Notifications> myNotifications = Dao.getAllNotifications();
        
        // 6. Gửi dữ liệu qua file JSP
        request.setAttribute("listReq", myRequests);
        request.setAttribute("listNotif", myNotifications);
        
        NotificationDAO dao = new NotificationDAO();

        String id_raw = request.getParameter("id");

        if (id_raw != null) {
            int id = Integer.parseInt(id_raw);
            model.Notifications n = dao.getNotificationById(id);
            request.setAttribute("notif", n);
            request.getRequestDispatcher("notification_detail_Residents.jsp").forward(request, response);
            return;
        }
        
        // 7. Chuyển hướng sang trang residentHome.jsp
        request.getRequestDispatcher("Home_Residents.jsp").forward(request, response);
    }
}