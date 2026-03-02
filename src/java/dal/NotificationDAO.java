package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Notifications;

public class NotificationDAO extends DBContext {

    public List<Notifications> getAllNotifications() {
        List<Notifications> list = new ArrayList<>();
        String sql = "SELECT n.*, u.FullName AS SenderName "
                + "FROM Notifications n "
                + "LEFT JOIN Users u ON n.SenderId = u.UserId "
                + "ORDER BY n.CreatedAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Notifications n = new Notifications();
                n.setNotificationId(rs.getInt("NotificationId"));
                n.setTitle(rs.getString("Title"));
                n.setContent(rs.getString("Content"));
                n.setType(rs.getString("Type"));
                n.setSenderID(rs.getInt("SenderId"));
                n.setSenderName(rs.getString("SenderName"));
                n.setReceiverID(rs.getInt("ReceiverId"));
                n.setApartmentID(rs.getInt("ApartmentId"));
                n.setIsRead(rs.getBoolean("IsRead"));
                n.setCreatedAt(rs.getTimestamp("CreatedAt"));
                n.setIsGlobal(rs.getBoolean("IsGlobal"));

                list.add(n);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAllNotifications: " + e.getMessage());
        }
        return list;
    }

    public void insertNotification(String title, String content, int senderId) {
        String sql = "INSERT INTO Notifications (Title, Content, Type, SenderId, IsRead, CreatedAt, IsGlobal) "
                + "VALUES (?, ?, 'System', ?, 0, GETDATE(), 1)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, content);
            st.setInt(3, senderId);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insertNotification: " + e.getMessage());
        }
    }

    public Notifications getNotificationById(int id) {
        String sql = "SELECT n.*, u.FullName AS SenderName "
                + "FROM Notifications n "
                + "LEFT JOIN Users u ON n.SenderId = u.UserId "
                + "WHERE n.NotificationId = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Notifications n = new Notifications();
                n.setNotificationId(rs.getInt("NotificationId"));
                n.setTitle(rs.getString("Title"));
                n.setContent(rs.getString("Content"));
                n.setCreatedAt(rs.getTimestamp("CreatedAt"));
                n.setSenderName(rs.getString("SenderName"));
                return n;
            }
        } catch (Exception e) {
            System.out.println("Lỗi getNotificationById: " + e.getMessage());
        }
        return null;
    }
}