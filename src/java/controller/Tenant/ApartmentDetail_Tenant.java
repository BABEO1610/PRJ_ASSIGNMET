package controller.Tenant;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import dal.ApartmentDAO;
import dal.BillDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Apartments;
import model.Bills;

/**
 *
 * @author DELL
 */
public class ApartmentDetail_Tenant extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int aptId = Integer.parseInt(idParam);
            
            ApartmentDAO aDao = new ApartmentDAO();
            BillDAO bDao = new BillDAO();
            
            Apartments apt = aDao.getApartmentById(aptId);
            List<Bills> listBills = bDao.getBillsByApartmentId(aptId);
            
            request.setAttribute("apt", apt);
            request.setAttribute("listBills", listBills);
            
            request.getRequestDispatcher("/Tenant/ApartmentDetail_Tenant.jsp").forward(request, response);
        } else {
            response.sendRedirect("MyManagedApartments");
        }
    }
}
