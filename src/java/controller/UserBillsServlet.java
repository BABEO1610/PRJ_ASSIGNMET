package controller;

import dal.BillDAO;
import dal.PaymentDAO;
import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import model.Bills;
import model.Payments;
import model.Users;

@WebServlet("/user-bills")
public class UserBillsServlet extends HttpServlet {
     @Override
    public void init() throws ServletException {
        System.out.println("UserBillsServlet INIT");
    }
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Users user = (Users) request.getSession().getAttribute("USER_INFO");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();

        int apartmentId = userDAO.getApartmentIdByUser(user.getUserId());

        BillDAO billDAO = new BillDAO();
        PaymentDAO paymentDAO = new PaymentDAO();

        List<Bills> bills = billDAO.getUnpaidBillsByApartment(apartmentId);
        System.out.println("USER ID = " + user.getUserId());
        System.out.println("APARTMENT ID = " + apartmentId);
        System.out.println("Bills size = " + bills.size());
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");

        Integer month = null;
        Integer year = null;

        if (monthStr != null && !monthStr.isEmpty()) {
            month = Integer.parseInt(monthStr);
        }

        if (yearStr != null && !yearStr.isEmpty()) {
            year = Integer.parseInt(yearStr);
        }

        List<Payments> payments =
                paymentDAO.getPaymentsByMonthYear(apartmentId, month, year);

        request.setAttribute("bills", bills);
        request.setAttribute("payments", payments);

        request.getRequestDispatcher("user-bills.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}