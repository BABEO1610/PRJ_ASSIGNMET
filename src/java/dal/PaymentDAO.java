/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.*;
import model.Payments;

public class PaymentDAO extends DBContext {

    public void insertPendingPayment(int billId,
                                     double amount,
                                     String txnRef) {

        String sql = """
                INSERT INTO Payments
                (BillId, PaymentMethod, PaymentDate,
                 Amount, TransactionCode, Status)
                VALUES (?, 'VNPAY', GETDATE(), ?, ?, 'PENDING')
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, billId);
            ps.setDouble(2, amount);
            ps.setString(3, txnRef);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePaymentStatus(String txnRef, String status) {

        String sql = "UPDATE Payments SET Status=? WHERE TransactionCode=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, txnRef);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Payments> getPaymentsByMonthYear(int apartmentId,
                                                 Integer month,
                                                 Integer year) {

        List<Payments> list = new ArrayList<>();

        String sql = """
                SELECT p.*
                FROM Payments p
                JOIN Bills b ON p.BillId = b.BillId
                WHERE b.ApartmentId = ?
                AND UPPER(p.Status) = 'SUCCESS'
                """;

        if (month != null) {
            sql += " AND MONTH(p.PaymentDate) = ?";
        }

        if (year != null) {
            sql += " AND YEAR(p.PaymentDate) = ?";
        }

        sql += " ORDER BY p.PaymentDate DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            int index = 1;

            ps.setInt(index++, apartmentId);

            if (month != null) {
                ps.setInt(index++, month);
            }

            if (year != null) {
                ps.setInt(index++, year);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Payments p = new Payments();

                p.setTransactionCode(rs.getString("TransactionCode"));
                p.setAmount(rs.getDouble("Amount"));
                p.setStatus(rs.getString("Status"));
                p.setPaymentDate(rs.getDate("PaymentDate"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}