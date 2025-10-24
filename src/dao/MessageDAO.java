package dao;

import db.DatabaseConnection;
import model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public void insertMessage(Message msg) {
        String sql = "INSERT INTO messages (sender, receiver, content) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, msg.getSender());
            ps.setString(2, msg.getReceiver());
            ps.setString(3, msg.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
