package controller;

import dal.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddApartmentController", urlPatterns = {"/AddApartmentController"})
public class AddApartmentController extends HttpServlet {

    // Thông tin kết nối Database (Thay đổi theo cấu hình máy bạn)
    private final String serverName = "localhost";
    private final String dbName = "PRJ_ASSIGNMET";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "123";
    private final String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            // 1. Lấy dữ liệu từ các ô input trong add-apartment.jsp
            String apartmentNumber = request.getParameter("apartmentNumber");
            int floor = Integer.parseInt(request.getParameter("floor"));
            double area = Double.parseDouble(request.getParameter("area"));
            String types = request.getParameter("types");
            String status = request.getParameter("status");
            String ownerIdRaw = request.getParameter("ownerId");

            // 2. Xử lý OwnerId (Lỗi 1 bạn gặp: chuyển từ Object sang int)
            // Nếu người dùng không nhập OwnerId, ta để mặc định là 0 hoặc xử lý null trong DAO
            int ownerId = 0;
            if (ownerIdRaw != null && !ownerIdRaw.trim().isEmpty()) {
                ownerId = Integer.parseInt(ownerIdRaw);
            }

            // 3. Gọi DAO để thực hiện lệnh INSERT
            UserDAO dao = new UserDAO();
            // Phương thức addApartment này bạn cần thêm vào UserDAO (xem mục 2 bên dưới)
            boolean isAdded = dao.addApartment(apartmentNumber, floor, area, types, status, ownerId);

            if (isAdded) {
                // 4. Nếu thành công, quay về trang danh sách với thông báo success
                response.sendRedirect("Apartments?status=success");
            } else {
                throw new Exception("Không thể thêm căn hộ. Có thể số căn hộ đã tồn tại.");
            }

        } catch (Exception e) {
            // 5. Nếu có lỗi (sai định dạng số, trùng mã...), gửi thông báo lỗi về trang thêm
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("add-apartment.jsp").forward(request, response);
        }
    }
}