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
import jakarta.servlet.http.HttpSession;
import model.Users;

/**
 *
 * @author Wind-Fubukii
 */
@WebServlet(name="Services", urlPatterns={"/Services"})
public class Services extends HttpServlet {
   
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
            out.println("<title>Servlet Services</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Services at " + request.getContextPath () + "</h1>");
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
        Users user = (Users) session.getAttribute("USER_INFO");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        UserDAO dao = new UserDAO();
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            request.setAttribute("allServices", dao.getAllServiceTypes());
            request.getRequestDispatcher("service-add.jsp").forward(request, response);
            return;
        }

        request.setAttribute("myServices", 
            dao.getServicesByResidentId(user.getUserId()));

        request.getRequestDispatcher("services.jsp").forward(request, response);
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
        UserDAO dao = new UserDAO();

        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String[] serviceIds = request.getParameterValues("serviceIds");
            if (serviceIds != null) {
                dao.registerServices(user.getUserId(), serviceIds);
            }
        }

        if ("cancel".equals(action)) {
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            dao.cancelService(user.getUserId(), serviceId);
        }

        response.sendRedirect("Services");
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
