/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import dal.ServicesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.ServiceTypes;
import model.Users;

/**
 *
 * @author Wind-Fubukii
 */
@WebServlet(name = "Services", urlPatterns = {"/Services"})
public class Services extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<h1>Servlet Services at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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

        ServicesDAO dao = new ServicesDAO();
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int page = 1;
            int pageSize = 10;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }

            int total = dao.countAvailableServices(user.getUserId());
            int totalPages = (int) Math.ceil((double) total / pageSize);

            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.setAttribute("allServices",
                    dao.getAvailableServicesPaging(user.getUserId(), page, pageSize));
            request.getRequestDispatcher("service-add.jsp").forward(request, response);
            return;
        }

        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        List<ServiceTypes> myServices
                = dao.getServicesByResidentId(user.getUserId());

        int total = myServices.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<ServiceTypes> pageList = myServices.subList(start, end);

        request.setAttribute("myServices", pageList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("services.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
        ServicesDAO dao = new ServicesDAO();

        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String[] serviceIds = request.getParameterValues("serviceIds");
            if (serviceIds != null) {
                UserDAO userDAO = new UserDAO();
                int apartmentId = userDAO.getApartmentIdByUser(user.getUserId());

                dao.registerServices(user.getUserId(), apartmentId, serviceIds);
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
