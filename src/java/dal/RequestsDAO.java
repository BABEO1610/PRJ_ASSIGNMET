package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Requests;
import model.RequestTypes;

public class RequestsDAO extends DBContext {

// Trong RequestsDAO.java
    public List<model.Requests> getAllRequests() {
        List<model.Requests> list = new ArrayList<>();
        // SQL JOIN để lấy đầy đủ tên cư dân và số phòng
        String sql = "SELECT r.*, u.FullName, a.ApartmentNumber " +
                     "FROM Requests r " +
                     "JOIN Users u ON r.ResidentId = u.UserId " +
                     "JOIN Apartments a ON r.ApartmentId = a.ApartmentId " +
                     "ORDER BY CASE WHEN r.Status = 'Pending' THEN 0 ELSE 1 END, " +
                     "r.CreatedAt DESC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.Requests req = new model.Requests();
                req.setRequestId(rs.getInt("RequestId"));
                req.setResidentID(rs.getInt("ResidentId"));
                req.setResidentName(rs.getString("FullName")); // Lấy từ bảng Users
                req.setApartmentID(rs.getInt("ApartmentId"));
                req.setApartmentNumber(rs.getString("ApartmentNumber")); // Lấy từ bảng Apartments
                req.setRequestTypeID(rs.getInt("RequestTypeId"));
                req.setTitle(rs.getString("Title"));
                req.setDescription(rs.getString("Description"));
                req.setStatus(rs.getString("Status"));
                req.setCreatedAt(rs.getTimestamp("CreatedAt"));
                req.setApprovedBy(rs.getInt("ApprovedBy"));
                req.setAssignedTo(rs.getInt("AssignedTo"));

                list.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void assignRequest(int requestId, int staffId, int managerId) {
        String sql = """
            UPDATE Requests
            SET AssignedTo = ?, 
                Status = 'Approved', 
                ApprovedBy = ?
            WHERE RequestId = ?
        """;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, staffId);
            ps.setInt(2, managerId); // ID của manager đang login
            ps.setInt(3, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getApartmentIdByOwner(int userId) {
        String sql = "SELECT ApartmentId FROM Apartments WHERE OwnerId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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

    public void insertRequest(Requests req) {

        String sql = """
            INSERT INTO Requests
            (ResidentId, ApartmentId, RequestTypeId, Title, Description, Status, CreatedAt, ApprovedBy, AssignedTo)
            VALUES (?, ?, ?, ?, ?, 'Pending', GETDATE(), NULL, NULL)
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, req.getResidentID());
            ps.setInt(2, req.getApartmentID());
            ps.setInt(3, req.getRequestTypeID());
            ps.setString(4, req.getTitle());

            if (req.getDescription() == null || req.getDescription().isEmpty()) {
                ps.setNull(5, java.sql.Types.NVARCHAR);
            } else {
                ps.setString(5, req.getDescription());
            }

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<RequestTypes> getAllRequestTypes() {
        List<RequestTypes> list = new ArrayList<>();
        // Câu lệnh SQL lấy dữ liệu từ bảng RequestTypes như trong ảnh database của bạn
        String sql = "SELECT RequestTypeId, TypeName FROM RequestTypes";

        try {
            // 'connection' là đối tượng kết nối từ DBContext mà class bạn đã kế thừa
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RequestTypes rt = new RequestTypes();
                rt.setRequestTypeId(rs.getInt("RequestTypeId"));
                rt.setTypeName(rs.getString("TypeName"));

                list.add(rt);
            }
        } catch (Exception e) {
            // Ghi log lỗi để dễ kiểm tra nếu truy vấn thất bại
            System.out.println("Error at getAllRequestTypes: " + e.getMessage());
        }
        return list;
    }
}