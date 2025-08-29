package view;

import controller.LoginController;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    @SuppressWarnings("unused")
	private MainGUI mainGUI;
    private JLabel statusLabel;
    private LoginController controller;
    
  
    public LoginPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.controller = new LoginController();
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setPreferredSize(new Dimension(600, 400));
        backgroundPanel.setBackground(new Color(33, 43, 54));
        backgroundPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel loginCard = new JPanel();
        loginCard.setLayout(null);
        loginCard.setBackground(Color.WHITE);
        loginCard.setPreferredSize(new Dimension(320, 200));
        loginCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 20, 150, 25);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        usernameField = new JTextField();
        usernameField.setBounds(130, 20, 150, 25);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 60, 150, 25);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 60, 150, 25);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 110, 100, 30);
        loginBtn.setBackground(new Color(33, 150, 243));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setBounds(20, 150, 280, 25);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        loginBtn.addActionListener(e -> handleLogin());

        loginCard.add(userLabel);
        loginCard.add(usernameField);
        loginCard.add(passLabel);
        loginCard.add(passwordField);
        loginCard.add(loginBtn);
        loginCard.add(statusLabel);

        backgroundPanel.add(loginCard);
        add(backgroundPanel);
    }

    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = String.valueOf(passwordField.getPassword());

        if (controller.validate(user, pass)) {
            statusLabel.setForeground(new Color(0, 128, 0));
            statusLabel.setText("Login successful.");
           MainGUI.setLoggedIn(true);
            MainGUI.switchPanel("Dashboard");
        } else {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Invalid credentials.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon background = new ImageIcon("src/img4.jpg");
        Image img = background.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    } 
}

// -----------https://www.youtube.com/watch?v=Hiv3gwJC5kw--------------
// -----------https://www.youtube.com/watch?v=Kmgo00avvEw--------------
