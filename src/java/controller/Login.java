/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
import model.Users;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="Login", urlPatterns={"/login"}) 
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // doGet chỉ dùng để ĐIỀU HƯỚNG: Mở trang đăng nhập khi người dùng gõ URL
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // doPost dùng để XỬ LÝ: Nhận dữ liệu từ form khi người dùng bấm nút "Đăng nhập"
        
        // 1. Lấy dữ liệu người dùng nhập
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // 2. Gọi DAO để chui xuống Database kiểm tra
        UserDAO dao = new UserDAO();
        Users account = dao.login(user, pass);

        // 3. Xử lý kết quả trả về
        if (account != null) {
            // Đăng nhập THÀNH CÔNG: Cấp "thẻ căn cước" (Session) cho người dùng
            HttpSession session = request.getSession();
            session.setAttribute("USER_INFO", account);
            
            // Chuyển hướng sang trang chủ
            response.sendRedirect("Home"); 
        } else {
            // Đăng nhập THẤT BẠI: Nhét câu chửi/thông báo lỗi vào hộp (Request) và ném lại trang cũ
            request.setAttribute("ERROR_MSG", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}