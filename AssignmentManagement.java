package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@SuppressWarnings("serial")
public class AssignmentManagement extends JPanel {
    public JTable table;
    public DefaultTableModel model;
    public JButton addBtn, updateBtn, deleteBtn;

    public AssignmentManagement() {
        setLayout(new BorderLayout());

       
        String[] columns = {"ID", "Course ID", "Course Name", "Title", "Description", "Due Date", "Added On", "Status"};

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Integer.class : String.class;
            }
        };

        table = new JTable(model);

        
        table.removeColumn(table.getColumnModel().getColumn(0));

        
        table.getColumnModel().getColumn(0).setPreferredWidth(100); 
        table.getColumnModel().getColumn(1).setPreferredWidth(150); 
        table.getColumnModel().getColumn(2).setPreferredWidth(150); 
        table.getColumnModel().getColumn(3).setPreferredWidth(250); 
        table.getColumnModel().getColumn(4).setPreferredWidth(100); 
        table.getColumnModel().getColumn(5).setPreferredWidth(150); 
        table.getColumnModel().getColumn(6).setPreferredWidth(80);  

        JScrollPane scrollPane = new JScrollPane(table);

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        new controller.AssignmentController(this);
    }
}
