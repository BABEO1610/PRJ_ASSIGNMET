package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Requests;

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
}