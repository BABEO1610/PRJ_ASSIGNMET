/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
package dal;

import java.sql.PreparedStatement;

public class BillDetailsDAO extends DBContext {

    public void addBillDetail(int billId, int serviceTypeId, double amount){

        String sql = """
            INSERT INTO BillDetails(BillId, ServiceTypeId, Quantity, Amount)
            VALUES (?, ?, 1, ?)
        """;

        try{

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, billId);
            ps.setInt(2, serviceTypeId);
            ps.setDouble(3, amount);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
