package model;

import java.sql.*;

public class LoginModel {

    public boolean validateLogin(String user, String pass) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/elearning_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {

            stmt.setString(1, user);
            stmt.setString(2, pass);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
