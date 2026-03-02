/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
import model.Users;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Positions;
/**
 *
 * @author ADMIN
 */
@WebServlet(name="employees", urlPatterns={"/employees"})
public class employees extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
    UserDAO dao = new UserDAO();

    if (action == null || action.equals("list")) {
        List<Users> list = dao.getAllEmployees();
        request.setAttribute("listEmp", list);
        request.getRequestDispatcher("employees.jsp").forward(request, response);
        
    } else if (action.equals("add")) {
        // ĐÚNG: Lấy danh sách chức vụ động từ DB
        List<Positions> listP = dao.getAllPositions();
        request.setAttribute("listP", listP);
        request.getRequestDispatcher("employee-form.jsp").forward(request, response);
        
    } else if (action.equals("edit")) {
        int id = Integer.parseInt(request.getParameter("id"));
        Users emp = dao.getEmployeeById(id);
        
        // ĐÚNG: Cung cấp cả dữ liệu nhân viên cũ và danh sách chức vụ mới
        List<Positions> listP = dao.getAllPositions();
        request.setAttribute("listP", listP);
        request.setAttribute("emp", emp);
        request.getRequestDispatcher("employee-form.jsp").forward(request, response);
    }
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();

        if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteEmployee(id);
            response.sendRedirect("employees");
            
        } else if (action.equals("insert")) {
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            int positionId = Integer.parseInt(request.getParameter("positionId"));

            Users u = new Users();
            u.setUsername(username); u.setPasswordHash(pass); 
            u.setFullName(fullname); u.setEmail(email); u.setPhone(phone);
            
            dao.insertEmployee(u, positionId);
            response.sendRedirect("employees");
            
        } else if (action.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            int positionId = Integer.parseInt(request.getParameter("positionId"));

            Users u = new Users();
            u.setUserId(id); u.setFullName(fullname); 
            u.setEmail(email); u.setPhone(phone);
            
            dao.updateEmployee(u, positionId);
            response.sendRedirect("employees");
        }
    }
}
