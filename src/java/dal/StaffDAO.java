/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Staff;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO extends DBContext {

    public List<Staff> getStaffByRequestType(int requestId) {
        List<Staff> list = new ArrayList<>();
        // Câu SQL đi theo đúng sơ đồ bạn yêu cầu
        String sql = """
            SELECT u.UserId, u.FullName, p.PositionName
            FROM Requests r
            JOIN RequestTypes rt ON r.RequestTypeId = rt.RequestTypeId
            JOIN Positions p ON rt.DefaultPositionId = p.PositionId
            JOIN StaffProfiles sp ON p.PositionId = sp.PositionId
            JOIN Users u ON sp.UserId = u.UserId
            WHERE r.RequestId = ?
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Staff st = new Staff(
                    rs.getInt("UserId"),
                    rs.getString("FullName"),
                    rs.getString("PositionName")
                );
                list.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}