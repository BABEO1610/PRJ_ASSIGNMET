/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Le Duc
 */
@WebServlet(name="DeleteApartment", urlPatterns={"/DeleteApartment"})
public class DeleteApartment extends HttpServlet {
   
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
            out.println("<title>Servlet DeleteApartment</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteApartment at " + request.getContextPath () + "</h1>");
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
        try {
            // 1. Lấy ID căn hộ cần xóa từ URL (ví dụ: DeleteApartment?id=5)
            String idRaw = request.getParameter("id");
            
            if (idRaw != null && !idRaw.isEmpty()) {
                int id = Integer.parseInt(idRaw);
                
                // 2. Gọi DAO để thực hiện xóa
                UserDAO dao = new UserDAO();
                boolean isDeleted = dao.deleteApartment(id);
                
                if (isDeleted) {
                    // 3. Xóa thành công, quay lại trang danh sách với thông báo
                    response.sendRedirect("Apartments?status=delete_success");
                } else {
                    // Xóa thất bại (thường do ràng buộc khóa ngoại)
                    response.sendRedirect("Apartments?status=delete_fail");
                }
            } else {
                response.sendRedirect("Apartments");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Apartments?status=error");
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
        processRequest(request, response);
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
