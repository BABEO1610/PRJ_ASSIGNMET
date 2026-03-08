package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Requests;
import model.RequestTypes;

public class RequestsDAO extends DBContext {

    public List<Requests> getAllRequests() {

        List<Requests> list = new ArrayList<>();

        String sql = """
        SELECT r.*, u.FullName, a.ApartmentNumber
        FROM Requests r
        JOIN Users u ON r.ResidentId = u.UserId
        JOIN Apartments a ON r.ApartmentId = a.ApartmentId
        ORDER BY 
        CASE 
            WHEN r.Status = 'Pending' THEN 0
            WHEN r.Status = 'Cancelled' THEN 1
            ELSE 2
        END,
        r.CreatedAt DESC
    """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Requests req = new Requests();

                req.setRequestId(rs.getInt("RequestId"));
                req.setResidentID(rs.getInt("ResidentId"));
                req.setResidentName(rs.getString("FullName"));
                req.setApartmentID(rs.getInt("ApartmentId"));
                req.setApartmentNumber(rs.getString("ApartmentNumber"));
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

    public List<Requests> getTasksByStaff(int staffId) {
        List<Requests> list = new ArrayList<>();

        String sql = """
            SELECT r.*, u.FullName, a.ApartmentNumber
            FROM Requests r
            JOIN Users u ON r.ResidentId = u.UserId
            JOIN Apartments a ON r.ApartmentId = a.ApartmentId
            WHERE r.AssignedTo = ?
            ORDER BY r.CreatedAt DESC
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Requests r = new Requests();

                r.setRequestId(rs.getInt("RequestId"));
                r.setResidentID(rs.getInt("ResidentId"));
                r.setResidentName(rs.getString("FullName"));
                r.setApartmentID(rs.getInt("ApartmentId"));
                r.setApartmentNumber(rs.getString("ApartmentNumber"));
                r.setTitle(rs.getString("Title"));
                r.setDescription(rs.getString("Description"));
                r.setStatus(rs.getString("Status"));
                r.setCreatedAt(rs.getTimestamp("CreatedAt"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Requests getRequestById(int id) {
        String sql = """
            SELECT r.*, u.FullName, a.ApartmentNumber
            FROM Requests r
            JOIN Users u ON r.ResidentId = u.UserId
            JOIN Apartments a ON r.ApartmentId = a.ApartmentId
            WHERE r.RequestId = ?
        """;

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Requests r = new Requests();

                r.setRequestId(rs.getInt("RequestId"));
                r.setResidentName(rs.getString("FullName"));
                r.setApartmentNumber(rs.getString("ApartmentNumber"));
                r.setTitle(rs.getString("Title"));
                r.setDescription(rs.getString("Description"));
                r.setStatus(rs.getString("Status"));

                return r;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void startTask(int requestId) {

        String sql = """
        UPDATE Requests
        SET Status = 'Processing'
        WHERE RequestId = ?
    """;

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, requestId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeTask(int requestId) {

        String sql = """
        UPDATE Requests
        SET Status = 'Completed'
        WHERE RequestId = ?
    """;

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, requestId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int requestId, String status) {

        try {

            PreparedStatement ps;

            // Nếu Cancelled thì reset AssignedTo và ApprovedBy
            if ("Cancelled".equalsIgnoreCase(status)) {

                String sql = """
                UPDATE Requests
                SET Status = ?, 
                    AssignedTo = NULL,
                    ApprovedBy = NULL
                WHERE RequestId = ?
            """;

                ps = connection.prepareStatement(sql);

                ps.setString(1, status);
                ps.setInt(2, requestId);

            } else {

                String sql = "UPDATE Requests SET Status = ? WHERE RequestId = ?";

                ps = connection.prepareStatement(sql);

                ps.setString(1, status);
                ps.setInt(2, requestId);
            }

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
