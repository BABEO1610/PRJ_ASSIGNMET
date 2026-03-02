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
import model.Positions;
import model.Requests;
import model.StaffProfiles;

public class UserDAO extends DBContext {

    // Hàm mẫu trả về 1 Object User nếu đăng nhập đúng, trả về null nếu sai
    public Users login(String username, String password) {
        // Câu SQL Join 2 bảng để lấy luôn tên Role
        String sql = "SELECT u.*, r.RoleName "
                + "FROM Users u "
                + "JOIN Roles r ON u.RoleId = r.RoleId "
                + "WHERE u.Username = ? AND u.PasswordHash = ?";

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
        String sql = "SELECT n.*, u.FullName AS SenderName "
                + "FROM Notifications n "
                + "LEFT JOIN Users u ON n.SenderId = u.UserId "
                + "ORDER BY n.CreatedAt DESC";

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
        String sql = "SELECT n.*, u.FullName as SenderName "
                + "FROM Notifications n "
                + "LEFT JOIN Users u ON n.SenderId = u.UserId "
                + "WHERE n.ReceiverId = ? OR n.IsGlobal = 1 "
                + "ORDER BY n.CreatedAt DESC";

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
        String sql = "SELECT r.*, u.FullName AS ResidentName "
                + "FROM Requests r "
                + "LEFT JOIN Users u ON r.ResidentId = u.UserId "
                + "ORDER BY r.CreatedAt DESC";
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

    public List<Users> getAllEmployees() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT u.*, p.PositionId, p.PositionName "
                + "FROM Users u "
                + "JOIN StaffProfiles sp ON u.UserId = sp.UserId "
                + "JOIN Positions p ON sp.PositionId = p.PositionId "
                + "WHERE u.RoleId = 2 AND u.IsActive = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                u.setUserId(rs.getInt("UserId"));
                u.setUsername(rs.getString("Username"));
                u.setFullName(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));

                // KHỞI TẠO NỀN TẢNG (OOP CHUẨN)
                Positions p = new Positions();
                p.setPositionId(rs.getInt("PositionId"));
                p.setPositionName(rs.getString("PositionName"));

                StaffProfiles sp = new StaffProfiles();
                sp.setPosition(p);
                sp.setUser(u);

                u.setStaffProfile(sp); // Gắn két sắt vào Users
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Users getEmployeeById(int userId) {
        String sql = "SELECT u.*, p.PositionId, p.PositionName FROM Users u "
                + "JOIN StaffProfiles sp ON u.UserId = sp.UserId "
                + "JOIN Positions p ON sp.PositionId = p.PositionId WHERE u.UserId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Users u = new Users();
                u.setUserId(rs.getInt("UserId"));
                u.setUsername(rs.getString("Username"));
                u.setFullName(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));

                Positions p = new Positions();
                p.setPositionId(rs.getInt("PositionId"));
                p.setPositionName(rs.getString("PositionName"));

                StaffProfiles sp = new StaffProfiles();
                sp.setPosition(p);
                u.setStaffProfile(sp);
                return u;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // Các hàm Insert/Update/Delete giữ nguyên logic Transaction đã gửi ở trên
    // Thêm mới nhân viên (Dùng Transaction)
    public boolean insertEmployee(Users u, int positionId) {
        try {
            connection.setAutoCommit(false);
            String sqlUser = "INSERT INTO Users (Username, PasswordHash, FullName, Email, Phone, RoleId, IsActive) VALUES (?, ?, ?, ?, ?, 2, 1)";
            PreparedStatement st1 = connection.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
            st1.setString(1, u.getUsername());
            st1.setString(2, u.getPasswordHash()); // Thực tế nên mã hóa MD5/SHA256
            st1.setString(3, u.getFullName());
            st1.setString(4, u.getEmail());
            st1.setString(5, u.getPhone());
            st1.executeUpdate();

            ResultSet rs = st1.getGeneratedKeys();
            if (rs.next()) {
                int newUserId = rs.getInt(1);
                String sqlProfile = "INSERT INTO StaffProfiles (UserId, PositionId) VALUES (?, ?)";
                PreparedStatement st2 = connection.prepareStatement(sqlProfile);
                st2.setInt(1, newUserId);
                st2.setInt(2, positionId);
                st2.executeUpdate();
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
            System.out.println("Lỗi insert: " + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
            }
        }
        return false;
    }

    // Cập nhật nhân viên (Dùng Transaction)
    public boolean updateEmployee(Users u, int positionId) {
        try {
            connection.setAutoCommit(false);
            String sqlUser = "UPDATE Users SET FullName = ?, Email = ?, Phone = ? WHERE UserId = ?";
            PreparedStatement st1 = connection.prepareStatement(sqlUser);
            st1.setString(1, u.getFullName());
            st1.setString(2, u.getEmail());
            st1.setString(3, u.getPhone());
            st1.setInt(4, u.getUserId());
            st1.executeUpdate();

            String sqlProfile = "UPDATE StaffProfiles SET PositionId = ? WHERE UserId = ?";
            PreparedStatement st2 = connection.prepareStatement(sqlProfile);
            st2.setInt(1, positionId);
            st2.setInt(2, u.getUserId());
            st2.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
            System.out.println("Lỗi update: " + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
            }
        }
        return false;
    }

    // Xóa mềm (Soft Delete)
    public void deleteEmployee(int userId) {
        String sql = "UPDATE Users SET IsActive = 0 WHERE UserId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    // Thêm hàm này vào cuối class UserDAO.java

    public List<Positions> getAllPositions() {
        List<Positions> list = new ArrayList<>();
        // Truy vấn trực tiếp bảng Positions
        String sql = "SELECT * FROM Positions";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Positions p = new Positions();
                p.setPositionId(rs.getInt("PositionId"));
                p.setPositionName(rs.getString("PositionName"));
                p.setDescription(rs.getString("Description"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getAllPositions: " + e.getMessage());
        }
        return list;
    }
}
