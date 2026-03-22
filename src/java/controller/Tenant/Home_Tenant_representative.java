package controller.Tenant;

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

public class Home_Tenant_representative extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("USER_INFO");
        
        // Kiểm tra xem đã đăng nhập chưa
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int tenantId = currentUser.getUserId();
        
        UserDAO Dao = new UserDAO();
        NotificationDAO notifDao = new NotificationDAO();
        
        // 1. Lấy danh sách Yêu cầu thuộc các căn hộ mà người này thuê
        List<Requests> listReq = Dao.getRequestsByTenantId(tenantId);
        
        // 2. Lấy thông báo (Dùng chung hàm của Resident vì logic nhận thông báo là như nhau)
        List<Notifications> listNotif = notifDao.getAllNotifications();
        
        // 3. Đẩy dữ liệu sang JSP
        request.setAttribute("listReq", listReq);
        request.setAttribute("listNotif", listNotif);
        
        NotificationDAO dao = new NotificationDAO();

        String id_raw = request.getParameter("id");

        if (id_raw != null) {
            int id = Integer.parseInt(id_raw);
            model.Notifications n = dao.getNotificationById(id);
            request.setAttribute("notif", n);
            request.getRequestDispatcher("/Tenant/NotificationDetail_Tenant.jsp").forward(request, response);
            return;
        }
        
        request.getRequestDispatcher("/Tenant/Home_Tenant_representative.jsp").forward(request, response);
    }
}