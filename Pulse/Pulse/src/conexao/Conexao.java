package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.BancoConfig;

public class Conexao {

	public static Connection conn;

	public static Connection getConnection() {
		
        BancoConfig config = new BancoConfig();
        String url = config.getUrl();
        String user = config.getUser();
        String password = config.getPassword();
        
		try {
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			return null;
		}
	}
}