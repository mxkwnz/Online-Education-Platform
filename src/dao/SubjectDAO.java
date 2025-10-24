package dao;

import db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    public List<String> getSubjectsByCourse(int courseNumber) {
        List<String> subjects = new ArrayList<>();
        String sql = "SELECT subject_name FROM subjects WHERE course_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subjects.add(rs.getString("subject_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
}
