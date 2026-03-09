/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ApartmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Users;

/**
 *
 * @author DELL
 */
public class MyManagedApartments extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("USER_INFO");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp"); return;
        }
        
        ApartmentDAO aDao = new ApartmentDAO();
        List<model.Apartments> listApt = aDao.getApartmentsByTenantId(currentUser.getUserId());
        
        request.setAttribute("listApt", listApt);
        request.getRequestDispatcher("myManagedApartments.jsp").forward(request, response);
    }
}
