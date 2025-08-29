package controller;

import javax.swing.*;

import model.Assignment;
import model.AssignmentDAO;
import view.AssignmentManagement;

import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AssignmentController {
    private final AssignmentManagement view;
    private final AssignmentDAO dao;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AssignmentController(AssignmentManagement view) {
        this.view = view;
        this.dao = new AssignmentDAO();
        initController();
        loadAssignments();
    }

    private void initController() {
        view.addBtn.addActionListener(e -> addAssignment());
        view.updateBtn.addActionListener(e -> updateAssignment());
        view.deleteBtn.addActionListener(e -> deleteAssignment());
    }

    private void loadAssignments() {
        try {
            List<Assignment> assignments = dao.getAllAssignments();
            view.model.setRowCount(0); // Clear existing rows

            for (Assignment a : assignments) {
                view.model.addRow(new Object[]{
                        a.getId(), // Hidden in view but stored in model
                        a.getCourseId(),
                        a.getCourseName(),
                        a.getTitle(),
                        a.getDescription(),
                        a.getDueDate(),
                        a.getAddDate() != null ? a.getAddDate().format(dateFormatter) : "",
                        a.isDeleted() ? "Deleted" : "Active"
                });
            }
        } catch (SQLException e) {
            showError("Error loading assignments: " + e.getMessage());
        }
    }

    private void addAssignment() {
        JTextField courseIdField = new JTextField(10);
        JTextField courseNameField = new JTextField(10);
        JTextField titleField = new JTextField(10);
        JTextArea descField = new JTextArea(3, 20);
        JTextField dueField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Course ID:"));
        panel.add(courseIdField);
        panel.add(new JLabel("Course Name:"));
        panel.add(courseNameField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descField));
        panel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        panel.add(dueField);

        int result = JOptionPane.showConfirmDialog(view, panel, "Add Assignment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Assignment a = new Assignment(
                        courseIdField.getText(),
                        courseNameField.getText(),
                        titleField.getText(),
                        descField.getText(),
                        dueField.getText()
                );

                int id = dao.addAssignment(a);
                a.setId(id);
                loadAssignments();
                JOptionPane.showMessageDialog(view, "Assignment added successfully!");
            } catch (SQLException e) {
                showError("Error adding assignment: " + e.getMessage());
            }
        }
    }

    private void updateAssignment() {
        int row = view.table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select an assignment to update");
            return;
        }

        int id = (Integer) view.model.getValueAt(row, 0);
        String courseId = (String) view.model.getValueAt(row, 1);
        String courseName = (String) view.model.getValueAt(row, 2);
        String title = (String) view.model.getValueAt(row, 3);
        String description = (String) view.model.getValueAt(row, 4);
        String dueDate = (String) view.model.getValueAt(row, 5);

        JTextField courseIdField = new JTextField(courseId, 10);
        JTextField courseNameField = new JTextField(courseName, 10);
        JTextField titleField = new JTextField(title, 10);
        JTextArea descField = new JTextArea(description, 3, 20);
        JTextField dueField = new JTextField(dueDate, 10);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Course ID:"));
        panel.add(courseIdField);
        panel.add(new JLabel("Course Name:"));
        panel.add(courseNameField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descField));
        panel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        panel.add(dueField);

        int result = JOptionPane.showConfirmDialog(view, panel, "Update Assignment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Assignment a = new Assignment(
                        courseIdField.getText(),
                        courseNameField.getText(),
                        titleField.getText(),
                        descField.getText(),
                        dueField.getText()
                );
                a.setId(id);

                if (dao.updateAssignment(a)) {
                    loadAssignments();
                    JOptionPane.showMessageDialog(view, "Assignment updated successfully!");
                }
            } catch (SQLException e) {
                showError("Error updating assignment: " + e.getMessage());
            }
        }
    }

    private void deleteAssignment() {
        int row = view.table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select an assignment to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete this assignment?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (Integer) view.model.getValueAt(row, 0);
                if (dao.softDeleteAssignment(id)) {
                    loadAssignments();
                    JOptionPane.showMessageDialog(view, "Assignment deleted successfully!");
                }
            } catch (SQLException e) {
                showError("Error deleting assignment: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}