package controller;

import view.EnrollmentManagement;
import model.IT23540408_dbConn;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentController {

    private final EnrollmentManagement enrollmentView;

    public EnrollmentController(EnrollmentManagement view) {
        this.enrollmentView = view;

        loadEnrollmentsFromDatabase();

        
        enrollmentView.getEnrollmentTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = enrollmentView.getEnrollmentTable().getSelectedRow();
                if (selectedRow >= 0) {
                    enrollmentView.getStudentIdField().setText(enrollmentView.getEnrollmentTable().getValueAt(selectedRow, 0).toString());
                    enrollmentView.getCourseIdField().setText(enrollmentView.getEnrollmentTable().getValueAt(selectedRow, 1).toString());
                    enrollmentView.getProgressField().setText(enrollmentView.getEnrollmentTable().getValueAt(selectedRow, 2).toString());
                }
            }
        });

        enrollmentView.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField studentField = enrollmentView.getStudentIdField();
                JTextField courseField = enrollmentView.getCourseIdField();
                JTextField progressField = enrollmentView.getProgressField();
                JTable table = enrollmentView.getEnrollmentTable();

                String studentId = studentField.getText().trim();
                String courseId = courseField.getText().trim();
                String progress = progressField.getText().trim();

                if (!studentId.isEmpty() && !courseId.isEmpty() && !progress.isEmpty()) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]{studentId, courseId, progress});

                    studentField.setText("");
                    courseField.setText("");
                    progressField.setText("");

                    saveEnrollmentToDatabase(studentId, courseId, progress);

                    JOptionPane.showMessageDialog(null, "Enrollment entered successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        enrollmentView.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = enrollmentView.getEnrollmentTable();
                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0) {
                    
                    String originalStudentId = table.getValueAt(selectedRow, 0).toString();
                    String originalCourseId = table.getValueAt(selectedRow, 1).toString();

                    
                    String studentId = enrollmentView.getStudentIdField().getText().trim();
                    String courseId = enrollmentView.getCourseIdField().getText().trim();
                    String progress = enrollmentView.getProgressField().getText().trim();

                    if (!studentId.isEmpty() && !courseId.isEmpty() && !progress.isEmpty()) {
                       
                        table.setValueAt(studentId, selectedRow, 0);
                        table.setValueAt(courseId, selectedRow, 1);
                        table.setValueAt(progress, selectedRow, 2);

                       
                        updateEnrollmentInDatabase(originalStudentId, originalCourseId, studentId, courseId, progress);

                        JOptionPane.showMessageDialog(null, "Enrollment updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill in all fields before updating.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to update.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        enrollmentView.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = enrollmentView.getEnrollmentTable();
                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0) {
                    String studentId = (String) table.getValueAt(selectedRow, 0);
                    String courseId = (String) table.getValueAt(selectedRow, 1);

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(selectedRow);

                    deleteEnrollmentFromDatabase(studentId, courseId);

                    JOptionPane.showMessageDialog(null, "Enrollment deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void loadEnrollmentsFromDatabase() {
        try (Connection conn = IT23540408_dbConn.getConnection()) {
            String sql = "SELECT * FROM enrollments";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) enrollmentView.getEnrollmentTable().getModel();
            model.setRowCount(0);

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String courseId = rs.getString("course_id");
                String progress = rs.getString("progress");

                model.addRow(new Object[]{studentId, courseId, progress});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading enrollments: " + ex.getMessage());
        }
    }

    private void saveEnrollmentToDatabase(String studentId, String courseId, String progress) {
        try (Connection conn = IT23540408_dbConn.getConnection()) {
            String sql = "INSERT INTO enrollments (student_id, course_id, progress) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.setString(3, progress);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saving enrollment: " + ex.getMessage());
        }
    }

    private void updateEnrollmentInDatabase(String originalStudentId, String originalCourseId, String newStudentId, String newCourseId, String progress) {
        try (Connection conn = IT23540408_dbConn.getConnection()) {
            String sql = "UPDATE enrollments SET student_id = ?, course_id = ?, progress = ? WHERE student_id = ? AND course_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStudentId);
            stmt.setString(2, newCourseId);
            stmt.setString(3, progress);
            stmt.setString(4, originalStudentId);
            stmt.setString(5, originalCourseId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating enrollment: " + ex.getMessage());
        }
    }

    private void deleteEnrollmentFromDatabase(String studentId, String courseId) {
        try (Connection conn = IT23540408_dbConn.getConnection()) {
            String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting enrollment: " + ex.getMessage());
        }
    }
}