package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

<<<<<<< HEAD
	private static final String URL = "jdbc:mysql://localhost:3306/pulseDB";
	private static final String USER = "root";
	private static final String PASSWORD = "senhamysql123";

	public static Connection conn;

	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		} catch (SQLException e) {
			return null;
		}
	}
=======
	 private static final String URL = "jdbc:mysql://localhost:3306/pulseDB";
	 private static final String USER = "root";
	 private static final String PASSWORD = "senhamysql123";
	
	 public static Connection conn;
	 
	 public static Connection getConnection() {
	        try {
	            conn = DriverManager.getConnection(URL, USER, PASSWORD);
	            return conn;
	        } catch (SQLException e) {
	            return null;
	        }
	 }	 
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
}
