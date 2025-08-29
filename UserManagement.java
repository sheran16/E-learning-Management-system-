package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import controller.UserManagementController;

@SuppressWarnings("serial")
public class UserManagement extends JPanel {
    private JTable actionTable; 
    private DefaultTableModel tableModel; 
    private JTextField idField, nameField, emailField; 
    private JComboBox<String> roleField; 
    private JButton btnCreate; 

    public UserManagement() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        // shows the user management
        JLabel label = new JLabel("User Management", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(label, BorderLayout.NORTH);

        Color customBlue = Color.decode("#94B4C1");
        // show the left panel user details
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(customBlue);
        TitledBorder formTitle = BorderFactory.createTitledBorder("User Details");
        formTitle.setTitleFont(new Font("Arial", Font.BOLD, 18));
        formPanel.setBorder(formTitle);
        // tabe grid hw i show it
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        // show the labels in user details
        JLabel lblId = new JLabel("ID:");
        idField = new JTextField(20);
        idField.setFont(new Font("Arial", Font.PLAIN, 16));
        idField.setEditable(false); 

        JLabel lblName = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblEmail = new JLabel("Email:");
        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblRole = new JLabel("Role:");
        roleField = new JComboBox<>(new String[]{"Admin", "Instructor", "Student"});
        roleField.setFont(new Font("Arial", Font.PLAIN, 16));
        roleField.setPreferredSize(new Dimension(200, 25));
        // put the elements in place 
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblId, gbc);
        gbc.gridx = 1; formPanel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblName, gbc);
        gbc.gridx = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblEmail, gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblRole, gbc);
        gbc.gridx = 1; formPanel.add(roleField, gbc);

        String[] columns = {"ID", "Name", "Email", "Role", "update", "read", "delete"};
        tableModel = new DefaultTableModel(columns, 0);
        actionTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column >= 4;
            }
        };

        actionTable.setFont(new Font("Arial", Font.PLAIN, 16));
        actionTable.setRowHeight(40);
        actionTable.setShowGrid(true);
        actionTable.setGridColor(Color.GRAY);
        actionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane tableScrollPane = new JScrollPane(actionTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 300));
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // right side tbale user list
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(customBlue);
        TitledBorder tableTitle = BorderFactory.createTitledBorder("User List");
        tableTitle.setTitleFont(new Font("Arial", Font.BOLD, 18));
        tablePanel.setBorder(tableTitle);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0);
        add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        btnCreate = new JButton("create");
        btnCreate.setBackground(new Color(76, 175, 80));
        btnCreate.setForeground(Color.WHITE);
        btnCreate.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(btnCreate, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        new UserManagementController(this);
    }

    public JTextField getIdField() { return idField; }
    public JTextField getNameField() { return nameField; }
    public JTextField getEmailField() { return emailField; }
    public JComboBox<String> getRoleField() { return roleField; }
    public JTable getActionTable() { return actionTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnCreate() { return btnCreate; }
}
