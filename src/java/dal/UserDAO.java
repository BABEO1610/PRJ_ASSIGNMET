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
import java.util.ArrayList;
import java.util.List;
import model.Notifications;
import model.Requests;

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
    
    public List<Notifications> getAllNotifications() {
        List<Notifications> list = new ArrayList<>();
        String sql = "SELECT n.*, u.FullName AS SenderName " +
             "FROM Notifications n " +
             "LEFT JOIN Users u ON n.SenderId = u.UserId " +
             "ORDER BY n.CreatedAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Notifications n = new Notifications();
                n.setNotificationId(rs.getInt("NotificationId"));
                n.setTitle(rs.getString("Title"));
                n.setContent(rs.getString("Content"));
                n.setType(rs.getString("Type"));
                n.setSenderID(rs.getInt("SenderId"));
                n.setSenderName(rs.getString("SenderName"));    
                n.setReceiverID(rs.getInt("ReceiverId"));
                n.setApartmentID(rs.getInt("ApartmentId"));
                n.setIsRead(rs.getBoolean("IsRead"));
                n.setCreatedAt(rs.getTimestamp("CreatedAt")); // Lấy cả ngày và giờ
                n.setIsGlobal(rs.getBoolean("IsGlobal"));

                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getAllNotifications: " + e.getMessage());
        }
        return list;
    }
    
    public List<Notifications> getNotificationsByUserId(int userId) {
        List<Notifications> list = new ArrayList<>();
        // SQL lấy thông báo cá nhân (ReceiverId) HOẶC thông báo chung (IsGlobal = 1)
        String sql = "SELECT n.*, u.FullName as SenderName " +
                     "FROM Notifications n " +
                     "LEFT JOIN Users u ON n.SenderId = u.UserId " +
                     "WHERE n.ReceiverId = ? OR n.IsGlobal = 1 " +
                     "ORDER BY n.CreatedAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Notifications n = new Notifications();
                n.setNotificationId(rs.getInt("NotificationId"));
                n.setTitle(rs.getString("Title"));
                n.setContent(rs.getString("Content"));
                n.setType(rs.getString("Type"));
                n.setSenderID(rs.getInt("SenderId"));
                n.setReceiverID(rs.getInt("ReceiverId"));
                n.setApartmentID(rs.getInt("ApartmentId"));
                n.setIsRead(rs.getBoolean("IsRead"));
                n.setCreatedAt(rs.getTimestamp("CreatedAt"));
                n.setIsGlobal(rs.getBoolean("IsGlobal"));

                // Nếu bạn muốn lưu tên người gửi vào object Notification
                // n.setSenderName(rs.getString("SenderName")); 

                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi Get Notifications: " + e.getMessage());
        }
        return list;
    }
    
    public List<Requests> getAllRequests() {
        List<Requests> list = new ArrayList<>();
        // Truy vấn lấy thông tin yêu cầu kèm tên Cư dân và tên Loại yêu cầu
        String sql = "SELECT r.*, u.FullName AS ResidentName " +
             "FROM Requests r " +
             "LEFT JOIN Users u ON r.ResidentId = u.UserId " +
             "ORDER BY r.CreatedAt DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Requests r = new Requests();
                r.setRequestId(rs.getInt("RequestId"));
                r.setResidentID(rs.getInt("ResidentId"));
                r.setResidentName(rs.getString("ResidentName"));
                r.setApartmentID(rs.getInt("ApartmentId"));
                r.setRequestTypeID(rs.getInt("RequestTypeId"));
                r.setTitle(rs.getString("Title"));
                r.setDescription(rs.getString("Description"));
                r.setTransferDate(rs.getTimestamp("TransferDate"));
                r.setStatus(rs.getString("Status"));
                r.setCreatedAt(rs.getTimestamp("CreatedAt"));
                r.setApprovedBy(rs.getInt("ApprovedBy"));
                r.setAssignedTo(rs.getInt("AssignedTo"));

                // Bạn có thể lưu thêm FullName vào một biến tạm trong đối tượng Requests nếu cần
                // r.setResidentName(rs.getString("FullName"));

                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getAllRequests: " + e.getMessage());
        }
        return list;
    }
}
