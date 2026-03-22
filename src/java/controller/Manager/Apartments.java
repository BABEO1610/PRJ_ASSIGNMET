/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Manager;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Notifications;
import model.Requests;

/**
 *
 * @author Le Duc
 */
@WebServlet(name="Apartments", urlPatterns={"/Apartments"})
public class Apartments extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Apartments</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Apartments at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // 1. Khởi tạo DAO
            UserDAO dao = new UserDAO();
            
            // 2. Gọi hàm getAllApartments đã chỉnh sửa (có JOIN để lấy OwnerName)
            List<model.Apartments> list = dao.getAllApartments();
            
            // 3. Đẩy danh sách vào request attribute để JSP có thể đọc được bằng JSTL
            request.setAttribute("listApts", list);
            
            // 4. Kiểm tra tham số status từ URL (nếu có từ AddApartmentController chuyển về)
            String status = request.getParameter("status");
            if (status != null && status.equals("success")) {
                request.setAttribute("msg", "Thêm căn hộ mới thành công!");
            }

            // 5. Chuyển hướng sang trang apartment.jsp
            request.getRequestDispatcher("/Manager/apartment.jsp").forward(request, response);
            
        } catch (Exception e) {
            // Xử lý lỗi nếu kết nối Database thất bại
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/Manager/apartment.jsp").forward(request, response);
        }
    } 
    

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       UserDAO dao = new UserDAO();
       List<model.Apartments> list = dao.getAllApartments();

    request.setAttribute("listApts", list);

    request.getRequestDispatcher("/Manager/apartment.jsp").forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
