package controller;

import view.*;

import javax.swing.*;
import java.awt.*;

public class NavigationController {

    public static void switchPanel(String name, JPanel mainContent, boolean isLoggedIn, MainGUI mainGUI) {
        mainContent.removeAll();

        if (!isLoggedIn && !(name.equals("Login") || name.equals("Dashboard"))) {
            JLabel label = new JLabel("Access Denied. Please login first.", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 18));
            mainContent.add(label, BorderLayout.CENTER);
        } else if (name.equals("User")) {
            mainContent.add(new UserManagement(), BorderLayout.CENTER);
        } else if (name.equals("Course")) {
             mainContent.add(new CourseManagement(), BorderLayout.CENTER);
        } else if (name.equals("Assignment")) {
             mainContent.add(new AssignmentManagement(), BorderLayout.CENTER);
        } else if (name.equals("Enrollment")) {
             mainContent.add(new EnrollmentManagement(), BorderLayout.CENTER);
        } else if (name.equals("Login")) {
            mainContent.add(new LoginPanel(mainGUI), BorderLayout.CENTER);
        } else if (name.equals("Dashboard")) {
        	mainContent.add(new view.DashboardPanel(), BorderLayout.CENTER);
        } else {
            JLabel label = new JLabel("Welcome to " + name, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 18));
            mainContent.add(label, BorderLayout.CENTER);
        }

        mainContent.revalidate();
        mainContent.repaint();
    }
}
