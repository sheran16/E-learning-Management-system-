package view;

import controller.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private static JPanel mainContent;
    private static boolean isLoggedIn = false;

    public static void setLoggedIn(boolean status) {
        isLoggedIn = status;
    }

    public static boolean getLoggedIn() {
        return isLoggedIn;
    }

    public static MainGUI getInstance() {
        return new MainGUI(); 
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("E-learning Management System");
        frame.setSize(900, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridBagLayout());
        sidebar.setBackground(new Color(33, 43, 54));
        sidebar.setPreferredSize(new Dimension(220, frame.getHeight()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 10, 15, 10);

        mainContent = new JPanel();
        mainContent.setBackground(new Color(245, 245, 245));
        mainContent.setLayout(new BorderLayout());

        String[] buttonNames = {"Dashboard", "Login", "User", "Course", "Assignment", "Enrollment"};

        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(44, 62, 80));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            button.setPreferredSize(new Dimension(180, 40));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switchPanel(name);
                }
            });

            sidebar.add(button, gbc);
            gbc.gridy++;
        }

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(mainContent, BorderLayout.CENTER);

        switchPanel("Dashboard");

        frame.setVisible(true);
    }

    static void switchPanel(String name) {
        NavigationController.switchPanel(name, mainContent, isLoggedIn, getInstance());
    }
}
//-----------https://www.youtube.com/watch?v=Kmgo00avvEw--------------
