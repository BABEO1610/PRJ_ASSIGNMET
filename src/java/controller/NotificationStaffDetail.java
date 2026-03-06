/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.NotificationDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name="NotificationStaffDetail", urlPatterns={"/NotificationStaffDetail"})
public class NotificationStaffDetail extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String id_raw = request.getParameter("id");

        if(id_raw != null){

            int id = Integer.parseInt(id_raw);

            NotificationDAO dao = new NotificationDAO();

            model.Notifications n = dao.getNotificationById(id);

            request.setAttribute("notif", n);

            request.getRequestDispatcher("notification-detail-staff.jsp")
                    .forward(request, response);
        }

    }
}