/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ServiceTypes;

public class ServicesDAO extends DBContext {

    public List<ServiceTypes> getServicesByResidentId(int residentId) {

        List<ServiceTypes> list = new ArrayList<>();

        String sql = "SELECT st.* FROM ResidentServices rs "
                + "JOIN ServiceTypes st ON rs.ServiceTypeId = st.ServiceTypeId "
                + "WHERE rs.ResidentId = ? AND rs.IsActive = 1";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, residentId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ServiceTypes s = new ServiceTypes();
                s.setServiceTypeId(rs.getInt("ServiceTypeId"));
                s.setServiceName(rs.getString("ServiceName"));
                s.setUnit(rs.getString("Unit"));
                s.setPricePerUnit(rs.getDouble("PricePerUnit"));
                list.add(s);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    public List<ServiceTypes> getAvailableServicesPaging(int residentId, int page, int pageSize) {

        List<ServiceTypes> list = new ArrayList<>();

        int offset = (page - 1) * pageSize;

        String sql = "SELECT * FROM ServiceTypes st "
                + "WHERE NOT EXISTS ( "
                + " SELECT 1 FROM ResidentServices rs "
                + " WHERE rs.ServiceTypeId = st.ServiceTypeId "
                + " AND rs.ResidentId = ? AND rs.IsActive = 1 ) "
                + "ORDER BY st.ServiceTypeId "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, residentId);
            st.setInt(2, offset);
            st.setInt(3, pageSize);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ServiceTypes s = new ServiceTypes();
                s.setServiceTypeId(rs.getInt("ServiceTypeId"));
                s.setServiceName(rs.getString("ServiceName"));
                s.setUnit(rs.getString("Unit"));
                s.setPricePerUnit(rs.getDouble("PricePerUnit"));
                list.add(s);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    public int countAvailableServices(int residentId) {

        String sql = "SELECT COUNT(*) FROM ServiceTypes st "
                + "WHERE NOT EXISTS ( "
                + " SELECT 1 FROM ResidentServices rs "
                + " WHERE rs.ServiceTypeId = st.ServiceTypeId "
                + " AND rs.ResidentId = ? AND rs.IsActive = 1 )";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, residentId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return 0;
    }

    public void registerServices(int residentId, int apartmentId, String[] serviceIds) {

        String checkSql = "SELECT IsActive FROM ResidentServices WHERE ResidentId=? AND ServiceTypeId=?";
        String insertSql = "INSERT INTO ResidentServices (ResidentId, ServiceTypeId, RegisteredAt, IsActive) VALUES (?, ?, GETDATE(), 1)";
        String updateSql = "UPDATE ResidentServices SET IsActive=1, RegisteredAt=GETDATE() WHERE ResidentId=? AND ServiceTypeId=?";
        String priceSql = "SELECT PricePerUnit FROM ServiceTypes WHERE ServiceTypeId=?";

        BillDAO billDAO = new BillDAO();

        try {
            for (String id : serviceIds) {
                int serviceId = Integer.parseInt(id);

                PreparedStatement priceSt = connection.prepareStatement(priceSql);
                priceSt.setInt(1, serviceId);
                ResultSet priceRs = priceSt.executeQuery();

                double price = 0;
                if (priceRs.next()) {
                    price = priceRs.getDouble("PricePerUnit");
                }

                PreparedStatement checkSt = connection.prepareStatement(checkSql);
                checkSt.setInt(1, residentId);
                checkSt.setInt(2, serviceId);
                ResultSet rs = checkSt.executeQuery();

                if (rs.next()) {
                    PreparedStatement updateSt = connection.prepareStatement(updateSql);
                    updateSt.setInt(1, residentId);
                    updateSt.setInt(2, serviceId);
                    updateSt.executeUpdate();
                } else {
                    PreparedStatement insertSt = connection.prepareStatement(insertSql);
                    insertSt.setInt(1, residentId);
                    insertSt.setInt(2, serviceId);
                    insertSt.executeUpdate();
                }

                // TẠO BILL RIÊNG VÀ LƯU LUÔN SERVICE ID VÀO BILL
                billDAO.createBill(apartmentId, price, serviceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelService(int residentId, int serviceId) {

        String deactivateService = """
            UPDATE ResidentServices
            SET IsActive = 0
            WHERE ResidentId = ? AND ServiceTypeId = ?
        """;

        try {
            PreparedStatement st1 = connection.prepareStatement(deactivateService);
            st1.setInt(1, residentId);
            st1.setInt(2, serviceId);
            st1.executeUpdate();

            UserDAO userDAO = new UserDAO();
            int apartmentId = userDAO.getApartmentIdByUser(residentId);

            BillDAO billDAO = new BillDAO();

            // Truyền cả serviceId để xóa chính xác
            billDAO.deleteLatestBillByApartment(apartmentId, serviceId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
