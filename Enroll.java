package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Enroll {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		@SuppressWarnings("unused")
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning_db", "root", "");
		System.out.println("Connection created");
	}

}
