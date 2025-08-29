package model;

import model.AssignmentDAO;
import model.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AssignmentDAO {
    public int addAssignment(Assignment assignment) throws SQLException {
        String sql = "INSERT INTO assignments (course_id, course_name, title, description, due_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, assignment.getCourseId());
            stmt.setString(2, assignment.getCourseName());
            stmt.setString(3, assignment.getTitle());
            stmt.setString(4, assignment.getDescription());
            stmt.setString(5, assignment.getDueDate());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating assignment failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                throw new SQLException("Creating assignment failed, no ID obtained.");
            }
        }
    }

    public List<Assignment> getAllAssignments() throws SQLException {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignments WHERE is_deleted = FALSE";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("id"));
                assignment.setCourseId(rs.getString("course_id"));
                assignment.setCourseName(rs.getString("course_name"));
                assignment.setTitle(rs.getString("title"));
                assignment.setDescription(rs.getString("description"));
                assignment.setDueDate(rs.getString("due_date"));
                assignment.setAddDate(rs.getTimestamp("add_date").toLocalDateTime());
                
                Timestamp updateTimestamp = rs.getTimestamp("update_date");
                if (updateTimestamp != null) {
                    assignment.setUpdateDate(updateTimestamp.toLocalDateTime());
                }
                
                assignment.setDeleted(rs.getBoolean("is_deleted"));
                assignments.add(assignment);
            }
        }
        return assignments;
    }

    public boolean updateAssignment(Assignment assignment) throws SQLException {
        String sql = "UPDATE assignments SET course_id = ?, course_name = ?, title = ?, description = ?, due_date = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, assignment.getCourseId());
            stmt.setString(2, assignment.getCourseName());
            stmt.setString(3, assignment.getTitle());
            stmt.setString(4, assignment.getDescription());
            stmt.setString(5, assignment.getDueDate());
            stmt.setInt(6, assignment.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean softDeleteAssignment(int id) throws SQLException {
        String sql = "UPDATE assignments SET is_deleted = TRUE, delete_date = NOW() WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}