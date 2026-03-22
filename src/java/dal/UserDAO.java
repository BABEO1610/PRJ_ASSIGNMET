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
import model.Apartments;
import model.Notifications;
import model.Positions;
import model.Requests;
import model.ServiceTypes;
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
            if (this.connection == null) {
                this.connection = getConnection();
            }
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
                n.setSenderName(rs.getString("SenderName"));
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
                + "WHERE u.RoleId = 3 AND u.IsActive = 1";
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
            String sqlUser = "INSERT INTO Users (Username, PasswordHash, FullName, Email, Phone, RoleId, IsActive) VALUES (?, ?, ?, ?, ?, 3, 1)";
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

    public List<Requests> getRequestsByResidentId(int residentId) {
        List<Requests> list = new ArrayList<>();
        // Lọc theo ResidentId của người đang đăng nhập
        String sql = "SELECT r.*, u.FullName AS ResidentName "
                + "FROM Requests r "
                + "LEFT JOIN Users u ON r.ResidentId = u.UserId "
                + "WHERE r.ResidentId = ? "
                + "ORDER BY r.CreatedAt DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, residentId); // Truyền ID vào câu SQL
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Requests r = new Requests();
                r.setRequestId(rs.getInt("RequestId"));
                r.setResidentID(rs.getInt("ResidentId"));
                r.setRequestTypeID(rs.getInt("RequestTypeId"));
                r.setTitle(rs.getString("Title"));
                r.setStatus(rs.getString("Status"));
                r.setResidentName(rs.getString("ResidentName"));

                if (rs.getTimestamp("CreatedAt") != null) {
                    r.setCreatedAt(rs.getTimestamp("CreatedAt"));
                }
                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getRequestsByResidentId: " + e.getMessage());
        }
        return list;
    }

    public int getRoleIDByuserID(int userId) {
        int roleId = -1; // Đặt giá trị mặc định là -1 (nghĩa là không tìm thấy)

        // Câu lệnh SQL lấy RoleId từ bảng Users dựa trên UserId
        String sql = "SELECT RoleId FROM Users WHERE UserId = ?";

        try {
            // connection là đối tượng Connection đã được khởi tạo trong lớp DAO của bạn
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId); // Truyền tham số userId vào dấu ?

            ResultSet rs = st.executeQuery();

            // Nếu tìm thấy kết quả, lấy giá trị cột RoleId gán vào biến
            if (rs.next()) {
                roleId = rs.getInt("RoleId");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tại getRoleIDByuserID: " + e.getMessage());
        }

        return roleId; // Trả về roleId (hoặc -1 nếu lỗi/không tồn tại)
    }

    public int getApartmentIdByUser(int userId) {

        String sql = """
        SELECT ApartmentId
        FROM ApartmentResidents
        WHERE UserId = ?
        AND IsActive = 1
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ApartmentId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Apartments> getAllApartments() {
        List<Apartments> list = new ArrayList<>();
        // SQL: Lấy thông tin căn hộ + Lấy FullName từ bảng Users gán cho OwnerName
        String sql = "SELECT a.[ApartmentId], a.[ApartmentNumber], a.[Floor], a.[Area], "
                + "a.[Types], a.[Status], a.[OwnerId], u.[FullName] AS OwnerName "
                + "FROM [Apartments] a LEFT JOIN [Users] u ON a.[OwnerId] = u.[UserId]";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Apartments apt = new Apartments();
                apt.setApartmentId(rs.getInt("ApartmentId"));
                apt.setApartmentNumber(rs.getString("ApartmentNumber"));
                apt.setFloor(rs.getInt("Floor"));
                apt.setArea(rs.getDouble("Area"));
                apt.setTypes(rs.getNString("Types"));
                apt.setStatus(rs.getNString("Status"));

                // LỖI 1: Gán OwnerId là kiểu int
                apt.setOwnerId(rs.getInt("OwnerId"));

                // LỖI 2: Lấy OwnerName từ kết quả JOIN (vì bảng Apartments không có cột này)
                apt.setOwnerName(rs.getNString("OwnerName"));

                list.add(apt);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getAllApartments: " + e.getMessage());
        }
        return list;
    }

    public Apartments getApartmentById(int id) {
        String sql = "SELECT a.*, u.FullName AS OwnerName FROM Apartments a "
                + "LEFT JOIN Users u ON a.OwnerId = u.UserId WHERE a.ApartmentId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Apartments a = new Apartments();
                a.setApartmentId(rs.getInt("ApartmentId"));
                a.setApartmentNumber(rs.getString("ApartmentNumber"));
                a.setFloor(rs.getInt("Floor"));
                a.setArea(rs.getDouble("Area"));
                a.setTypes(rs.getNString("Types"));
                a.setStatus(rs.getNString("Status"));
                a.setOwnerName(rs.getNString("OwnerName"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateApartment(int id, String number, int floor, double area, String types, String status) {
        String sql = "UPDATE [Apartments] SET [ApartmentNumber] = ?, [Floor] = ?, "
                + "[Area] = ?, [Types] = ?, [Status] = ? WHERE [ApartmentId] = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, number);
            ps.setInt(2, floor);
            ps.setDouble(3, area);
            ps.setNString(4, types);
            ps.setNString(5, status);
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteApartment(int id) {
        // Câu lệnh SQL xóa căn hộ dựa trên ApartmentId
        String sql = "DELETE FROM [Apartments] WHERE [ApartmentId] = ?";

        // Kiểm tra kết nối trước khi thực hiện
        if (this.connection == null) {
            this.connection = getConnection();
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            // Thực thi câu lệnh
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            // In lỗi ra console để debug (ví dụ: lỗi Foreign Key Constraint)
            System.out.println("Lỗi khi xóa căn hộ: " + e.getMessage());
            return false;
        }
    }

    public boolean addApartment(String number, int floor, double area, String types, String status, int ownerId) {
        // Câu lệnh INSERT chính xác theo bảng Apartments của bạn
        String sql = "INSERT INTO [Apartments] ([ApartmentNumber], [Floor], [Area], [Types], [Status], [OwnerId]) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, number);
            ps.setInt(2, floor);
            ps.setDouble(3, area);
            ps.setNString(4, types);
            ps.setNString(5, status);

            // Xử lý logic OwnerId: Nếu là 0 (không nhập) thì set NULL trong DB
            if (ownerId > 0) {
                ps.setInt(6, ownerId);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Apartments getApartmentByResidentId(int userId) {
        // Truy vấn căn hộ thông qua bảng trung gian ApartmentResidents
        String sql = "SELECT a.* FROM Apartments a "
                + "JOIN ApartmentResidents ar ON a.ApartmentId = ar.ApartmentId "
                + "WHERE ar.UserId = ? AND ar.IsActive = 1";
        try {
            if (connection == null) {
                connection = getConnection();
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Apartments a = new Apartments();
                a.setApartmentId(rs.getInt("ApartmentId"));
                a.setApartmentNumber(rs.getString("ApartmentNumber"));
                a.setFloor(rs.getInt("Floor"));
                a.setArea(rs.getDouble("Area"));
                a.setTypes(rs.getNString("Types"));
                a.setStatus(rs.getNString("Status"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Requests> getRequestsByTenantId(int tenantId) {
        List<Requests> list = new ArrayList<>();

        // SQL JOIN 3 bảng: Requests, Users (lấy tên người gửi), Apartments (lấy tên phòng và lọc theo TenantId)
        String sql = "SELECT r.*, u.FullName AS ResidentName, a.ApartmentNumber "
                + "FROM Requests r "
                + "LEFT JOIN Users u ON r.ResidentId = u.UserId "
                + "LEFT JOIN Apartments a ON r.ApartmentId = a.ApartmentId "
                + "WHERE a.TenantId = ? "
                + "ORDER BY r.CreatedAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantId); // Truyền ID của Đại diện khách thuê (người đang đăng nhập)
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Requests r = new Requests();
                r.setRequestId(rs.getInt("RequestId"));
                r.setResidentID(rs.getInt("ResidentId"));
                r.setApartmentID(rs.getInt("ApartmentId"));
                r.setApartmentNumber(rs.getString("ApartmentNumber")); // Lấy Tên căn hộ
                r.setRequestTypeID(rs.getInt("RequestTypeId"));
                r.setTitle(rs.getString("Title"));
                r.setStatus(rs.getString("Status"));
                r.setResidentName(rs.getString("ResidentName"));

                if (rs.getTimestamp("CreatedAt") != null) {
                    r.setCreatedAt(rs.getTimestamp("CreatedAt"));
                }
                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getRequestsByTenantId: " + e.getMessage());
        }
        return list;
    }

    public List<Users> getAllUsers() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try {
            if (this.connection == null) {
                this.connection = getConnection();
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                u.setUserId(rs.getInt("UserId"));
                u.setUsername(rs.getString("Username"));
                u.setFullName(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setIsActive(rs.getBoolean("IsActive"));

                Roles r = new Roles();
                r.setRoleId(rs.getInt("RoleId"));
                u.setRole(r);

                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addAccount(String user, String pass, String name, String email, String phone, int roleId) {
        // Câu lệnh SQL chèn dữ liệu vào bảng Users
        String sql = "INSERT INTO [Users] ([Username], [PasswordHash], [FullName], [Email], [Phone], [RoleId], [IsActive], [CreatedAt]) "
                + "VALUES (?, ?, ?, ?, ?, ?, 1, GETDATE())";

        try {
            if (this.connection == null) {
                this.connection = getConnection();
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass); // Lưu ý: Nên mã hóa password nếu hệ thống yêu cầu
            ps.setNString(3, name);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setInt(6, roleId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi addAccount: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int id) {
        // 1. Khai báo SQL - Mở rộng subquery để dọn dẹp triệt để RequestLogs
        String sqlNotifs = "DELETE FROM Notifications WHERE SenderId = ? OR ReceiverId = ?";

        // SỬA ĐỔI: Xóa log của mọi yêu cầu mà User này là người gửi, người duyệt hoặc người thực hiện
        String sqlLogs = "DELETE FROM RequestLogs WHERE UpdatedBy = ? "
                + "OR RequestId IN (SELECT RequestId FROM Requests WHERE ResidentId = ? OR AssignedTo = ? OR ApprovedBy = ?)";

        String sqlRequests = "DELETE FROM Requests WHERE ResidentId = ? OR AssignedTo = ? OR ApprovedBy = ?";
        String sqlServices = "DELETE FROM ResidentServices WHERE ResidentId = ?"; //
        String sqlStaff = "DELETE FROM StaffProfiles WHERE UserId = ?"; //
        String sqlResidents = "DELETE FROM ApartmentResidents WHERE UserId = ?";
        String sqlApts = "UPDATE Apartments SET OwnerId = NULL WHERE OwnerId = ?";
        String sqlUser = "DELETE FROM Users WHERE UserId = ?";

        try {
            if (this.connection == null) {
                this.connection = getConnection();
            }
            connection.setAutoCommit(false);

            // BƯỚC 1: Xóa thông báo
            try (PreparedStatement ps = connection.prepareStatement(sqlNotifs)) {
                ps.setInt(1, id);
                ps.setInt(2, id);
                ps.executeUpdate();
            }

            // BƯỚC 2: Xóa nhật ký xử lý yêu cầu (Nâng cấp subquery)
            try (PreparedStatement ps = connection.prepareStatement(sqlLogs)) {
                ps.setInt(1, id); // UpdatedBy
                ps.setInt(2, id); // ResidentId trong subquery
                ps.setInt(3, id); // AssignedTo trong subquery
                ps.setInt(4, id); // ApprovedBy trong subquery
                ps.executeUpdate();
            }

            // BƯỚC 3: Xóa yêu cầu (Requests)
            try (PreparedStatement ps = connection.prepareStatement(sqlRequests)) {
                ps.setInt(1, id);
                ps.setInt(2, id);
                ps.setInt(3, id);
                ps.executeUpdate();
            }

            // BƯỚC 4: Xóa dịch vụ cư dân đăng ký
            try (PreparedStatement ps = connection.prepareStatement(sqlServices)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // BƯỚC 5: Xóa hồ sơ nhân viên
            try (PreparedStatement ps = connection.prepareStatement(sqlStaff)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // BƯỚC 6: Xóa liên kết cư dân - căn hộ
            try (PreparedStatement ps = connection.prepareStatement(sqlResidents)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // BƯỚC 7: Gỡ vai trò chủ nhà
            try (PreparedStatement ps = connection.prepareStatement(sqlApts)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // BƯỚC 8: XÓA TÀI KHOẢN
            try (PreparedStatement ps = connection.prepareStatement(sqlUser)) {
                ps.setInt(1, id);
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    connection.commit();
                    return true;
                }
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                }
            }
            System.out.println("Lỗi Hard Delete triệt để: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    // 1. Lấy thông tin 1 người dùng theo ID
    public Users getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE UserId = ?";
        try {
            if (this.connection == null) {
                this.connection = getConnection();
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Users u = new Users();
                u.setUserId(rs.getInt("UserId"));
                u.setUsername(rs.getString("Username"));
                u.setFullName(rs.getNString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setIsActive(rs.getBoolean("IsActive"));

                // Gán đối tượng Roles
                Roles r = new Roles();
                r.setRoleId(rs.getInt("RoleId"));
                u.setRole(r);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

// 2. Cập nhật thông tin tài khoản
    public boolean updateUser(int id, String name, String email, String phone, int roleId, boolean active) {
        //them code
        String sql = "UPDATE [Users] SET [FullName] = ?, [Email] = ?, [Phone] = ?, "
                + "[RoleId] = ?, [IsActive] = ? WHERE [UserId] = ?";
        try {
            if (this.connection == null) {
                this.connection = getConnection();
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setNString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, roleId);
            ps.setBoolean(5, active);
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
