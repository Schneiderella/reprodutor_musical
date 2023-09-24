package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	 private static final String URL = "jdbc:mysql://localhost:3306/pulseDB";
	 private static final String USER = "seuusuario";
	 private static final String PASSWORD = "suasenha";
	
	 public static Connection conn;
	 
	 public static Connection getConnection() {
	        try {
	            conn = DriverManager.getConnection(URL, USER, PASSWORD);
	            return conn;
	        } catch (SQLException e) {
	            return null;
	        }
	 }	 
}
