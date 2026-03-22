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

/**
 *
 * @author Le Duc
 */
@WebServlet(name="EditApartmentController", urlPatterns={"/EditApartmentController"})
public class EditApartmentController extends HttpServlet {
   
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
            out.println("<title>Servlet EditApartmentController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditApartmentController at " + request.getContextPath () + "</h1>");
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
            int id = Integer.parseInt(request.getParameter("id"));
            UserDAO dao = new UserDAO();
            
            // Lấy đối tượng căn hộ từ database theo ID
            model.Apartments apt = dao.getApartmentById(id);
            
            if (apt != null) {
                request.setAttribute("apartment", apt);
                request.getRequestDispatcher("/Manager/editapartment.jsp").forward(request, response);
            } else {
                response.sendRedirect("Apartments");
            }
        } catch (Exception e) {
            response.sendRedirect("Apartments");
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
        request.setCharacterEncoding("UTF-8");
        
        try {
            // Lấy dữ liệu từ các trường input trong edit-apartment.jsp
            int id = Integer.parseInt(request.getParameter("apartmentId"));
            String number = request.getParameter("apartmentNumber");
            int floor = Integer.parseInt(request.getParameter("floor"));
            double area = Double.parseDouble(request.getParameter("area"));
            String types = request.getParameter("types");
            String status = request.getParameter("status");

            UserDAO dao = new UserDAO();
            // Gọi hàm update trong DAO
            boolean isUpdated = dao.updateApartment(id, number, floor, area, types, status);

            if (isUpdated) {
                // Thành công: Quay lại danh sách căn hộ
                response.sendRedirect("Apartments?status=update_success");
            } else {
                request.setAttribute("error", "Cập nhật thất bại. Vui lòng kiểm tra lại.");
                doGet(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            doGet(request, response);
        }
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
