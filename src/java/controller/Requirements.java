/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.RequestsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import model.RequestTypes;
import model.Requests;
import model.Users;

/**
 *
 * @author FPT SHOP
 */
@WebServlet(name="Requirements", urlPatterns={"/Requirements"})
public class Requirements extends HttpServlet {
   
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
            out.println("<title>Servlet Requirements</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Requirements at " + request.getContextPath () + "</h1>");
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
        HttpSession session = request.getSession();
        if (session.getAttribute("USER_INFO") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 1. Gọi DAO để lấy danh sách RequestTypes
        RequestsDAO dao = new RequestsDAO();
        List<RequestTypes> listTypes = dao.getAllRequestTypes();

        // 2. Đẩy danh sách vào request attribute
        request.setAttribute("listTypes", listTypes);

        request.getRequestDispatcher("Requirements.jsp").forward(request, response);
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
        
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("USER_INFO");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int typeId = Integer.parseInt(request.getParameter("requestTypeId"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            // ✅ Bắt buộc phải có title
            if (title == null || title.trim().isEmpty()) {
                request.setAttribute("errorMsg", "Tiêu đề không được để trống!");
                doGet(request, response);
                return;
            }

            RequestsDAO dao = new RequestsDAO();

            int apartmentId = dao.getApartmentIdByOwner(user.getUserId());

            if (apartmentId == -1) {
                request.setAttribute("errorMsg", "Không tìm thấy căn hộ của bạn.");
                doGet(request, response);
                return;
            }

            Requests req = new Requests();
            req.setResidentID(user.getUserId());
            req.setApartmentID(apartmentId);
            req.setRequestTypeID(typeId);
            req.setTitle(title);
            req.setDescription(description); // có thể null

            dao.insertRequest(req);

            request.setAttribute("successMsg",
                    "Yêu cầu của bạn đã được gửi và đang chờ duyệt!");

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Lỗi hệ thống!");
            e.printStackTrace();
        }

        doGet(request, response);
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
