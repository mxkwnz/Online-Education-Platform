package dao;

import db.DatabaseConnection;
import model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    public List<Teacher> getAllTeachers() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Teacher(rs.getString("name"), rs.getString("subject")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
