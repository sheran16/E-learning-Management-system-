package view;

import controller.EnrollmentController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class EnrollmentManagement extends JPanel {

    private JTextField studentIdField, courseIdField, progressField;
    private JTable enrollmentTable;
    private JButton addButton, updateButton, deleteButton;

    public EnrollmentManagement() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        inputPanel.setBackground(Color.WHITE);

        JLabel studentLabel = new JLabel("Student ID:");
        studentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(studentLabel);

        studentIdField = new JTextField();
        studentIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(studentIdField);

        JLabel courseLabel = new JLabel("Course ID:");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(courseLabel);

        courseIdField = new JTextField();
        courseIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(courseIdField);

        JLabel progressLabel = new JLabel("Progress (%):");
        progressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(progressLabel);

        progressField = new JTextField();
        progressField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(progressField);

        
        inputPanel.add(new JLabel()); 
        addButton = createStyledButton("Enter");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        enrollmentTable = new JTable(new DefaultTableModel(new Object[]{"Student ID", "Course ID", "Progress"}, 0));
        JScrollPane tableScrollPane = new JScrollPane(enrollmentTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(48, 60, 84), 2));
        tableScrollPane.setBackground(new Color(245, 245, 245));
        enrollmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        enrollmentTable.setRowHeight(30);
        enrollmentTable.setFillsViewportHeight(true);
        enrollmentTable.getTableHeader().setBackground(new Color(48, 60, 84));
        enrollmentTable.getTableHeader().setForeground(Color.WHITE);
        enrollmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        add(tableScrollPane, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        updateButton = createStyledButton("Update");
        deleteButton = createStyledButton("Delete");

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        
        new EnrollmentController(this);

        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    JOptionPane.showMessageDialog(null, "Enrollment added successfully!");
                }
            }
        });
    }

    private boolean validateFields() {
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty() || !studentId.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Student ID must be numeric and cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String courseId = courseIdField.getText().trim();
        if (courseId.isEmpty() || !courseId.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Course ID must be numeric and cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String progress = progressField.getText().trim();
        if (progress.isEmpty() || !progress.matches("\\d+\\.?\\d*") || Double.parseDouble(progress) < 0 || Double.parseDouble(progress) > 100) {
            JOptionPane.showMessageDialog(this, "Progress must be a number between 0 and 100.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(33, 43, 54));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    
    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JTable getEnrollmentTable() {
        return enrollmentTable;
    }

    public JTextField getStudentIdField() {
        return studentIdField;
    }

    public JTextField getCourseIdField() {
        return courseIdField;
    }

    public JTextField getProgressField() {
        return progressField;
    }
}
