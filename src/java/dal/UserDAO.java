/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Roles;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DBContext {

    // Hàm mẫu trả về 1 Object User nếu đăng nhập đúng, trả về null nếu sai
    public Users login(String username, String password) {
        // Câu SQL Join 2 bảng để lấy luôn tên Role
        String sql = "SELECT u.*, r.RoleName " +
                     "FROM Users u " +
                     "JOIN Roles r ON u.RoleId = r.RoleId " +
                     "WHERE u.Username = ? AND u.PasswordHash = ?";
        
        try {
            // 1. Chuẩn bị đường ống dẫn lệnh SQL
            PreparedStatement st = connection.prepareStatement(sql);
            
            // 2. Truyền tham số vào các dấu chấm hỏi (?)
            st.setString(1, username);
            st.setString(2, password);
            
            // 3. Thực thi lệnh và hứng kết quả vào bảng ảo ResultSet
            ResultSet rs = st.executeQuery();
            
            // 4. Đọc dữ liệu từ bảng ảo để nặn thành Object User
            if (rs.next()) {
                Users u = new Users();
                u.setUserId(rs.getInt("UserId"));
                u.setUsername(rs.getString("Username"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setFullName(rs.getString("FullName"));
                
                // Nặn Object Role và nhét vào User
                Roles r = new Roles();
                r.setRoleId(rs.getInt("RoleId"));
                r.setRoleName(rs.getString("RoleName"));
                u.setRole(r);
                
                return u; // Đăng nhập thành công
            }
        } catch (SQLException e) {
            System.out.println("Lỗi Login: " + e.getMessage());
        }
        return null; // Đăng nhập thất bại
    }
}
