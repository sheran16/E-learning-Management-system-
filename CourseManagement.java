package view;

import controller.CourseController;
import model.Course;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class CourseManagement extends JPanel {

    private final JTextField courseIdField = new JTextField();
    private final JTextField courseNameField = new JTextField();
    private final JTextField instructorField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea(3, 20);
    private final JButton addButton = new JButton("Add");
    private final JTable courseTable = new JTable();
    private final DefaultTableModel tableModel;
    private final CourseController controller = new CourseController();
    private final JPanel rightPanel = new JPanel();

    public CourseManagement() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        Font titleFont = new Font("Arial", Font.BOLD, 22);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);
        Font tableHeaderFont = new Font("Arial", Font.BOLD, 16);
        Dimension btnSize = new Dimension(120, 35);

        JLabel titleLabel = new JLabel("Course Management", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(33, 43, 54));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 5));
        TitledBorder formBorder = BorderFactory.createTitledBorder("Course Details");
        formBorder.setTitleFont(titleFont);
        formPanel.setBorder(formBorder);
        formPanel.setBackground(Color.WHITE);

        String[] labels = {"Course ID (CI1234):", "Course Name:", "Instructor Name:", "Instructor Email (@gmail.com):", "Description:"};
        JComponent[] fields = {courseIdField, courseNameField, instructorField, emailField, new JScrollPane(descriptionArea)};

        JLabel[] jLabels = new JLabel[labels.length];
        for (int i = 0; i < labels.length; i++) {
            jLabels[i] = new JLabel(labels[i]);
            jLabels[i].setFont(labelFont);
            if (fields[i] instanceof JTextComponent) {
                ((JTextComponent) fields[i]).setFont(fieldFont);
            }
            formPanel.add(jLabels[i]);
            formPanel.add(fields[i]);
        }

        addButton.setEnabled(false);
        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(0, 51, 102));
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(btnSize);
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Instructor", "Email", "Description"}, 0);
        courseTable.setModel(tableModel);
        courseTable.setFont(fieldFont);
        courseTable.setRowHeight(25);
        courseTable.setShowGrid(true);
        courseTable.setGridColor(Color.BLACK);

        JTableHeader tableHeader = courseTable.getTableHeader();
        tableHeader.setFont(tableHeaderFont);
        tableHeader.setBackground(new Color(173, 216, 230));
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        tablePanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(250, 300));
        rightPanel.setBackground(new Color(204, 229, 255));
        TitledBorder profileBorder = BorderFactory.createTitledBorder("Course Profile");
        profileBorder.setTitleFont(titleFont);
        rightPanel.setBorder(profileBorder);
        add(rightPanel, BorderLayout.EAST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton viewBtn = new JButton("View");

        for (JButton b : new JButton[]{addButton, updateBtn, deleteBtn, viewBtn}) {
            b.setFont(buttonFont);
            b.setPreferredSize(btnSize);
            b.setForeground(Color.WHITE);
        }
        updateBtn.setBackground(new Color(51, 102, 153));
        deleteBtn.setBackground(new Color(255, 102, 102));
        viewBtn.setBackground(new Color(102, 153, 204));

        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(viewBtn);
        add(btnPanel, BorderLayout.SOUTH);

        DocumentListener validation = new DocumentListener() {         //frontend validation for Create 
            void check() {
                boolean valid = Pattern.matches("CI\\d{4}", courseIdField.getText())
                        && !courseNameField.getText().isEmpty()
                        && !instructorField.getText().isEmpty()
                        && Pattern.matches(".+@gmail\\.com", emailField.getText())
                        && !descriptionArea.getText().isEmpty();
                addButton.setEnabled(valid);
            }
            public void insertUpdate(DocumentEvent e) { check(); }
            public void removeUpdate(DocumentEvent e) { check(); }
            public void changedUpdate(DocumentEvent e) { check(); }
        };

        courseIdField.getDocument().addDocumentListener(validation);
        courseNameField.getDocument().addDocumentListener(validation);
        instructorField.getDocument().addDocumentListener(validation);
        emailField.getDocument().addDocumentListener(validation);
        descriptionArea.getDocument().addDocumentListener(validation);

        loadCourseData();

        addButton.addActionListener(e -> {
            Course c = new Course(courseIdField.getText(), courseNameField.getText(),
                    instructorField.getText(), emailField.getText(), descriptionArea.getText());
            controller.addCourse(c);
            tableModel.addRow(new Object[]{c.getCourseId(), c.getCourseName(), c.getInstructorName(), c.getInstructorEmail(), c.getDescription()});
            courseIdField.setText(""); courseNameField.setText(""); instructorField.setText(""); emailField.setText(""); descriptionArea.setText("");
        });

        deleteBtn.addActionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row >= 0) {
                String courseId = (String) tableModel.getValueAt(row, 0);
                controller.deleteCourseById(courseId);
                tableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to delete.");  //frontend validation for Delete
            }
        });

        updateBtn.addActionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row >= 0) {
                JTextField id = new JTextField((String) tableModel.getValueAt(row, 0));
                JTextField name = new JTextField((String) tableModel.getValueAt(row, 1));
                JTextField ins = new JTextField((String) tableModel.getValueAt(row, 2));
                JTextField mail = new JTextField((String) tableModel.getValueAt(row, 3));
                JTextArea desc = new JTextArea((String) tableModel.getValueAt(row, 4));

                JPanel p = new JPanel(new GridLayout(5, 2));
                p.add(new JLabel("Course ID:")); p.add(id);
                p.add(new JLabel("Course Name:")); p.add(name);
                p.add(new JLabel("Instructor:")); p.add(ins);
                p.add(new JLabel("Email:")); p.add(mail);
                p.add(new JLabel("Description:")); p.add(new JScrollPane(desc));

                int result = JOptionPane.showConfirmDialog(null, p, "Update Course", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    Course updated = new Course(id.getText(), name.getText(), ins.getText(), mail.getText(), desc.getText());
                    controller.updateCourse(updated);
                    tableModel.setValueAt(updated.getCourseId(), row, 0);
                    tableModel.setValueAt(updated.getCourseName(), row, 1);
                    tableModel.setValueAt(updated.getInstructorName(), row, 2);
                    tableModel.setValueAt(updated.getInstructorEmail(), row, 3);
                    tableModel.setValueAt(updated.getDescription(), row, 4);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to update.");    //frontend validation for Update
            }
        });

        viewBtn.addActionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row >= 0) {
                rightPanel.removeAll();
                ImageIcon icon = new ImageIcon("src/instructor_profile.jpg");
                Image img = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                String id = (String) tableModel.getValueAt(row, 0);
                String name = (String) tableModel.getValueAt(row, 1);

                Font infoFont = new Font("Segoe UI", Font.BOLD, 16);
                Color labelColor = new Color(0, 51, 102);
                Color valueColor = new Color(0, 102, 153);

                JLabel idLabelView = new JLabel("Course ID: ");
                idLabelView.setFont(infoFont); idLabelView.setForeground(labelColor);
                JLabel idValueView = new JLabel(id);
                idValueView.setFont(infoFont); idValueView.setForeground(valueColor);

                JLabel nameLabelView = new JLabel("Course Name: ");
                nameLabelView.setFont(infoFont); nameLabelView.setForeground(labelColor);
                JLabel nameValueView = new JLabel(name);
                nameValueView.setFont(infoFont); nameValueView.setForeground(valueColor);

                JPanel idLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
                idLine.setBackground(new Color(204, 229, 255));
                idLine.add(idLabelView); idLine.add(idValueView);

                JPanel nameLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                nameLine.setBackground(new Color(204, 229, 255));
                nameLine.add(nameLabelView); nameLine.add(nameValueView);

                rightPanel.add(Box.createVerticalStrut(10));
                rightPanel.add(imageLabel);
                rightPanel.add(Box.createVerticalStrut(5));
                rightPanel.add(idLine);
                rightPanel.add(nameLine);

                rightPanel.revalidate();
                rightPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to view.");   //frontend validation for View
            }
        });
    }

    private void loadCourseData() {
        List<Course> list = controller.getAllCourses();
        for (Course c : list) {
            tableModel.addRow(new Object[]{c.getCourseId(), c.getCourseName(), c.getInstructorName(), c.getInstructorEmail(), c.getDescription()});
        }
    }
}
// -------https://www.youtube.com/watch?v=RLu1I5yoBpQ&list=PLVPt_R_IXgiQ9kz9PflZ3w970jhkA3FMv------