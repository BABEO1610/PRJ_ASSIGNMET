package dal;

import java.sql.*;
import java.util.*;
import model.Bills;

public class BillDAO extends DBContext {

    public List<Bills> getUnpaidBillsByApartment(int apartmentId) {

        List<Bills> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM Bills
            WHERE ApartmentId = ?
            AND Status = 'UNPAID'
            ORDER BY BillId DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, apartmentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Bills b = new Bills();

                b.setBillId(rs.getInt("BillId"));
                b.setBillingMonth(rs.getInt("BillingMonth"));
                b.setBillingYear(rs.getInt("BillingYear"));
                b.setTotalAmount(rs.getDouble("TotalAmount"));
                b.setStatus(rs.getString("Status"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public double getBillAmount(int billId) {

        String sql = "SELECT TotalAmount FROM Bills WHERE BillId=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, billId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("TotalAmount");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public void updateBillsToPaid(String txnRef) {

        String sql = """
                UPDATE b
                SET b.Status='PAID'
                FROM Bills b
                JOIN Payments p ON b.BillId = p.BillId
                WHERE p.TransactionCode=?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, txnRef);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createBill(int apartmentId, double amount, int serviceId) {
        String insertBillSql = """
            INSERT INTO Bills 
            (ApartmentId, BillingMonth, BillingYear, TotalAmount, Status, CreatedAt) 
            VALUES (?, MONTH(GETDATE()), YEAR(GETDATE()), ?, 'UNPAID', GETDATE())
        """;
        
        String insertDetailSql = """
            INSERT INTO BillDetails(BillId, ServiceTypeId, Quantity, Amount)
            VALUES (?, ?, 1, ?)
        """;

        try {
            // Thêm Statement.RETURN_GENERATED_KEYS để tóm được ngay cái BillId vừa sinh ra
            PreparedStatement ps = connection.prepareStatement(insertBillSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, apartmentId);
            ps.setDouble(2, amount);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newBillId = rs.getInt(1); // Đây là BillId mới
                
                // Lưu vào BillDetails để sau này có bằng chứng biết hóa đơn này của dịch vụ nào
                PreparedStatement psDetail = connection.prepareStatement(insertDetailSql);
                psDetail.setInt(1, newBillId);
                psDetail.setInt(2, serviceId);
                psDetail.setDouble(3, amount);
                psDetail.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteLatestBillByApartment(int apartmentId, int serviceId) {
        // Câu SQL này dùng Join để tìm đích danh cái hóa đơn chưa thanh toán của đúng Dịch vụ bị hủy
        String findBillSql = """
            SELECT b.BillId FROM Bills b 
            JOIN BillDetails bd ON b.BillId = bd.BillId 
            WHERE b.ApartmentId = ? AND bd.ServiceTypeId = ? AND b.Status = 'UNPAID'
        """;

        try {
            PreparedStatement psFind = connection.prepareStatement(findBillSql);
            psFind.setInt(1, apartmentId);
            psFind.setInt(2, serviceId);
            ResultSet rs = psFind.executeQuery();

            if (rs.next()) {
                int targetBillId = rs.getInt("BillId");

                // 1. Phải xóa trong BillDetails trước để tránh lỗi dính Khóa Ngoại (Foreign Key)
                String delDetailSql = "DELETE FROM BillDetails WHERE BillId = ?";
                PreparedStatement psDelDetail = connection.prepareStatement(delDetailSql);
                psDelDetail.setInt(1, targetBillId);
                psDelDetail.executeUpdate();

                // 2. Sau khi xóa bảng con xong thì mới xóa hóa đơn ở bảng cha (Bills)
                String delBillSql = "DELETE FROM Bills WHERE BillId = ?";
                PreparedStatement psDelBill = connection.prepareStatement(delBillSql);
                psDelBill.setInt(1, targetBillId);
                psDelBill.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Bills> getBillsByApartmentId(int apartmentId) {
        List<Bills> list = new ArrayList<>();

        // Câu SQL: JOIN 3 bảng để lấy ServiceName
        String sql = "SELECT b.BillId, b.ApartmentId, b.BillingMonth, b.BillingYear, "
                   + "b.TotalAmount, b.Status, b.CreatedAt, "
                   + "bd.ServiceTypeId, s.ServiceName "
                   + "FROM Bills b "
                   + "JOIN BillDetails bd ON b.BillId = bd.BillId "
                   + "JOIN ServiceTypes s ON bd.ServiceTypeId = s.ServiceTypeId "
                   + "WHERE b.ApartmentId = ? "
                   + "ORDER BY b.CreatedAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, apartmentId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Bills b = new Bills();
                b.setBillId(rs.getInt("BillId"));
                b.setApartmentID(rs.getInt("ApartmentId"));
                b.setBillingMonth(rs.getInt("BillingMonth"));
                b.setBillingYear(rs.getInt("BillingYear"));
                b.setTotalAmount(rs.getDouble("TotalAmount"));
                b.setStatus(rs.getString("Status"));
                b.setCreatedAt(rs.getDate("CreatedAt"));
public List<Bills> getBillsByApartmentId(int apartmentId) {
        List<Bills> list = new ArrayList<>();

        // Câu SQL: JOIN 3 bảng để lấy ServiceName
        String sql = "SELECT b.BillId, b.ApartmentId, b.BillingMonth, b.BillingYear, "
                   + "b.TotalAmount, b.Status, b.CreatedAt, "
                   + "bd.ServiceTypeId, s.ServiceName "
                   + "FROM Bills b "
                   + "JOIN BillDetails bd ON b.BillId = bd.BillId "
                   + "JOIN ServiceTypes s ON bd.ServiceTypeId = s.ServiceTypeId "
                   + "WHERE b.ApartmentId = ? "
                   + "ORDER BY b.CreatedAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, apartmentId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Bills b = new Bills();
                b.setBillId(rs.getInt("BillId"));
                b.setApartmentID(rs.getInt("ApartmentId"));
                b.setBillingMonth(rs.getInt("BillingMonth"));
                b.setBillingYear(rs.getInt("BillingYear"));
                b.setTotalAmount(rs.getDouble("TotalAmount"));
                b.setStatus(rs.getString("Status"));
                b.set
                // Lấy ID và Tên dịch vụ từ các bảng đã JOIN
                b.setServiceTypeId(rs.getInt("ServiceTypeId"));
                b.setServiceName(rs.getString("ServiceName"));

                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getBillsByApartmentId: " + e.getMessage());
        }
        return list;
    }
}
