package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ButtonRenderer extends JButton implements TableCellRenderer { //  butms in table 
    public ButtonRenderer(String text, Color color) {
        setText(text);
        setForeground(Color.WHITE);
        setBackground(color);
        setFocusPainted(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus,
    int row, int column) {
        return this;
    }
}


@SuppressWarnings("serial")
class ButtonEditor extends DefaultCellEditor { // handling butms
    protected JButton button;
    private String label;

    public ButtonEditor(JCheckBox checkBox, String label, Color bgColor) {
        super(checkBox);
        this.label = label;
        button = new JButton(label);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> stopCellEditing()); 
    }
    // return the edit compnts in edit mde
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }
    // return the store vle in butm
    @Override
    public Object getCellEditorValue() {
        return label;
    }
}
