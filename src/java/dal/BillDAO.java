/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.*;
import model.Bills;

public class BillDAO extends DBContext {

    public List<Bills> getUnpaidBillsByApartment(int apartmentId) {
        List<Bills> list = new ArrayList<>();
        String sql = "SELECT * FROM Bills WHERE ApartmentId=? AND Status='UNPAID'";

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
