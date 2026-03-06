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
}
