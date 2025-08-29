package controller;

import model.User;
import model.Userconnection;
import view.UserManagement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserManagementController {
    private UserManagement view;
    private Userconnection Userconnection;

    public UserManagementController(UserManagement view) {
        this.view = view;
        this.Userconnection = new Userconnection();

        view.getBtnCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.getNameField().getText().trim();
                String email = view.getEmailField().getText().trim();
                String role = view.getRoleField().getSelectedItem().toString();
                // validations for the name n email
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Name cannot be empty.");
                    return;
                }

                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid email address.");
                    return;
                }

                User user = new User(0, name, email, role);
                Userconnection.insertUser(user);

                view.getNameField().setText("");
                view.getEmailField().setText("");

                refreshTable();
            }
        });

        addActionButtonsToTable();
        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel tableModel = view.getTableModel();
        @SuppressWarnings("unused")
		JTable table = view.getActionTable();
        tableModel.setRowCount(0);
        List<User> users = Userconnection.getAllUsers();
        for (User u : users) {
            tableModel.addRow(new Object[]{u.getId(), u.getName(), u.getEmail(), u.getRole(), "Update", "Read", "Delete"});
        }
    }

    private void addActionButtonsToTable() {
        JTable table = view.getActionTable();
        String[] actions = {"update", "read", "delete"};
        Color[] colors = {new Color(33, 150, 243), Color.BLACK, new Color(244, 67, 54)};

        for (int i = 0; i < actions.length; i++) {
            int col = 4 + i;
            table.getColumnModel().getColumn(col).setCellRenderer(new ButtonRenderer(actions[i], colors[i]));
            table.getColumnModel().getColumn(col).setCellEditor(new ButtonEditor(new JCheckBox(), actions[i], colors[i]));
        }
    }

    @SuppressWarnings("serial")
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer(String text, Color color) {
            setText(text);
            setForeground(Color.WHITE);
            setBackground(color);
            setFocusPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    @SuppressWarnings("serial")
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private int currentRow; 

        public ButtonEditor(JCheckBox checkBox, String label, Color bgColor) {
            super(checkBox);
            this.label = label;
            button = new JButton(label);
            button.setFocusPainted(false);
            button.setBackground(bgColor);
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> stopCellEditing());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row; 
            return button;
        }

        public Object getCellEditorValue() { // from buttonrender class 
            DefaultTableModel tableModel = view.getTableModel();
            @SuppressWarnings("unused")
			JTable table = view.getActionTable();

            if (currentRow < 0 || currentRow >= tableModel.getRowCount()) return label;

            int id = Integer.parseInt(tableModel.getValueAt(currentRow, 0).toString());
            String name = tableModel.getValueAt(currentRow, 1).toString();
            String email = tableModel.getValueAt(currentRow, 2).toString();
            String role = tableModel.getValueAt(currentRow, 3).toString();
            // butm update 
            if (label.contains("update")) {
                JTextField nameField = new JTextField(name);
                JTextField emailField = new JTextField(email);
                JComboBox<String> roleBox = new JComboBox<>(new String[]{"Admin", "Instructor", "Student"});
                roleBox.setSelectedItem(role);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Email:"));
                panel.add(emailField);
                panel.add(new JLabel("Role:"));
                panel.add(roleBox);

                int result = JOptionPane.showConfirmDialog(view, panel, "Update User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String newName = nameField.getText().trim();
                    String newEmail = emailField.getText().trim();
                    String newRole = roleBox.getSelectedItem().toString();

                    if (newName.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Name cannot be empty.");
                    } else if (!newEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        JOptionPane.showMessageDialog(view, "Invalid email format.");
                    } else {
                        User updatedUser = new User(id, newName, newEmail, newRole);
                        Userconnection.updateUser(updatedUser);
                    }
                } // delete butm
            } else if (label.contains("delete")) {
                int confirm = JOptionPane.showConfirmDialog(view, "Delete user ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Userconnection.deleteUser(id);
                }// read butm 
            } else if (label.contains("read")) {
                JOptionPane.showMessageDialog(view,
                        "ID: " + id + "\nName: " + name + "\nEmail: " + email + "\nRole: " + role,
                        "User Details", JOptionPane.INFORMATION_MESSAGE);
            }

            SwingUtilities.invokeLater(() -> refreshTable());

            return label;
        }
    }
}
