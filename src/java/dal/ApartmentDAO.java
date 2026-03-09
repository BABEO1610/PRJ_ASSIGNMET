/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.*;
import model.Apartments;

/**
 *
 * @author DELL
 */
public class ApartmentDAO extends DBContext{
    // Hàm lấy danh sách căn hộ theo ID người đại diện thuê
    public List<Apartments> getApartmentsByTenantId(int tenantId) {
        List<Apartments> list = new ArrayList<>();
        String sql = "SELECT * FROM Apartments WHERE TenantId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Apartments a = new Apartments();
                a.setApartmentId(rs.getInt("ApartmentId"));
                a.setApartmentNumber(rs.getString("ApartmentNumber"));
                // Lấy thêm các thông tin khác nếu có
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getApartmentsByTenantId: " + e.getMessage());
        }
        return list;
    }

    // Hàm lấy chi tiết 1 căn hộ
    public Apartments getApartmentById(int apartmentId) {
        String sql = "SELECT * FROM Apartments WHERE ApartmentId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, apartmentId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Apartments a = new Apartments();
                a.setApartmentId(rs.getInt("ApartmentId"));
                a.setApartmentNumber(rs.getString("ApartmentNumber"));
                // Ghi chú: Hãy đảm bảo Model Apartments của bạn có các trường này
                a.setFloor(rs.getInt("Floor")); 
                a.setArea(rs.getDouble("Area"));
                a.setTypes(rs.getString("Types"));
                a.setStatus(rs.getString("Status"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getApartmentById: " + e.getMessage());
        }
        return null;
    }
}
