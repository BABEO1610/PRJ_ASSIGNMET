package controller.Manager;

import dal.StaffDAO;
import dal.RequestsDAO;
import model.Staff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AssignRequest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");
        if (idRaw != null) {
            int requestId = Integer.parseInt(idRaw);

            StaffDAO staffDAO = new StaffDAO();
            // Gọi hàm lọc nhân viên theo loại yêu cầu
            List<Staff> listStaff = staffDAO.getStaffByRequestType(requestId);

            request.setAttribute("requestId", requestId);
            request.setAttribute("listStaff", listStaff);

            request.getRequestDispatcher("/Manager/AssignRequest.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy thông tin từ form
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));

        // 2. Lấy ID của quản lý đang đăng nhập (giả sử bạn lưu trong session là USER_INFO)
        // Model.User user = (Model.User) request.getSession().getAttribute("USER_INFO");
        // int managerId = user.getUserId();
        int managerId = 1; // Tạm thời để fix cứng nếu chưa có session

        RequestsDAO dao = new RequestsDAO();
        // Gọi hàm đã sửa ở bước 2
        dao.assignRequest(requestId, staffId, managerId);

        response.sendRedirect("Requests");
    }
}