package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import conexao.Conexao;

public class BancoConfig {

    private Properties properties = new Properties();

    // Lê o arquivo de propriedades do banco e retorna os dados necessários para a conexão
    public BancoConfig() {
        try {
            FileInputStream input = new FileInputStream("auxiliar/properties/database.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return properties.getProperty("db.url");
    }

    public String getUser() {
        return properties.getProperty("db.user");
    }

    public String getPassword() {
        return properties.getProperty("db.password");
    }
	
	// Inicia a conexão com o banco de dados
	public boolean iniciaConexao() {
		Connection connection = Conexao.getConnection();
		if (connection != null) {
			System.out.println("Conexão bem-sucedida!");
			return true;
		} else {
			System.out.println("Falha na conexão com o Banco de Dados.");
			return false;
		}
	}

	// Verifica se as tabelas já existem no banco de dados e se não existirem, cria elas
	public void verificarECriarTabelas() {
		verificarECriarTabelaUsuario();
		verificarECriarTabelaMusica();
		verificarECriarTabelaPlaylist();
		verificarECriarTabelaMusicaPlaylist();
	}

	private void verificarECriarTabelaUsuario() {
		try (Connection connection = Conexao.getConnection(); Statement statement = connection.createStatement()) {

			String sqlVerificarTabelaUsuario = "SELECT 1 FROM USUARIO LIMIT 1";

			try {
				statement.executeQuery(sqlVerificarTabelaUsuario);
				//System.out.println("A tabela 'USUARIO' já existe.");

			} catch (SQLException ex) {
				System.out.println("A tabela 'USUARIO' não existe. Criando...");
				criarTabelaUsuario(statement);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na verificação e criação da tabela 'USUARIO'");
		}
	}

	private void criarTabelaUsuario(Statement statement) {

		String sqlCriarTabelaUsuario = "CREATE TABLE USUARIO (codigo INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(100) NOT NULL, login VARCHAR(100) NOT NULL, senha VARCHAR(255) NOT NULL)";
		try {
			statement.execute(sqlCriarTabelaUsuario);
			System.out.println("Tabela 'USUARIO' criada com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na criação da tabela 'USUARIO'");
		}
	}

	private void verificarECriarTabelaMusica() {
		try (Connection connection = Conexao.getConnection(); Statement statement = connection.createStatement()) {

			String sqlVerificarTabelaMusica = "SELECT 1 FROM MUSICA LIMIT 1";

			try {
				statement.executeQuery(sqlVerificarTabelaMusica);
				//System.out.println("A tabela 'MUSICA' já existe.");
			} catch (SQLException ex) {
				System.out.println("A tabela 'MUSICA' não existe. Criando...");
				criarTabelaMusica(statement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na verificação e criação da tabela 'MUSICA'");
		}
	}

	private void criarTabelaMusica(Statement statement) {
		String sqlCriarTabelaMusica = "CREATE TABLE MUSICA (idmusica INT AUTO_INCREMENT PRIMARY KEY, titulo VARCHAR(200), endereco VARCHAR(200) NOT NULL, duracao VARCHAR(20) NOT NULL, codigoUsuario INT NOT NULL, artista VARCHAR(100), album VARCHAR(100))";
		try {
			statement.execute(sqlCriarTabelaMusica);
			System.out.println("Tabela 'MUSICA' criada com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na criação da tabela 'MUSICA'");
		}
	}

	private void verificarECriarTabelaPlaylist() {
		try (Connection connection = Conexao.getConnection(); Statement statement = connection.createStatement()) {

			String sqlVerificarTabelaPlaylist = "SELECT 1 FROM PLAYLIST LIMIT 1";

			try {
				statement.executeQuery(sqlVerificarTabelaPlaylist);
				//System.out.println("A tabela 'PLAYLIST' já existe.");
			} catch (SQLException ex) {
				System.out.println("A tabela 'PLAYLIST' não existe. Criando...");
				criarTabelaPlaylist(statement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na verificação e criação da tabela 'PLAYLIST'");
		}
	}

	private void criarTabelaPlaylist(Statement statement) {
		String sqlCriarTabelaPlaylist = "CREATE TABLE PLAYLIST (codigo INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(150), qtdMusicas INT, duracao VARCHAR(20), codUsuario INT, ordem VARCHAR(30), reproducoes INT)";
		try {
			statement.execute(sqlCriarTabelaPlaylist);
			System.out.println("Tabela 'PLAYLIST' criada com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na criação da tabela 'PLAYLIST'");
		}
	}

	private void verificarECriarTabelaMusicaPlaylist() {
		try (Connection connection = Conexao.getConnection(); Statement statement = connection.createStatement()) {

			String sqlVerificarTabelaMusicaPlaylist = "SELECT 1 FROM MUSICAPLAYLIST LIMIT 1";

			try {
				statement.executeQuery(sqlVerificarTabelaMusicaPlaylist);
				//System.out.println("A tabela 'MUSICAPLAYLIST' já existe.");
			} catch (SQLException ex) {
				System.out.println("A tabela 'MUSICAPLAYLIST' não existe. Criando...");
				criarTabelaMusicaPlaylist(statement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na verificação e criação da tabela 'MUSICAPLAYLIST'");
		}
	}

	private void criarTabelaMusicaPlaylist(Statement statement) {
		String sqlCriarTabelaMusicaPlaylist = "CREATE TABLE MUSICAPLAYLIST (codigo INT AUTO_INCREMENT PRIMARY KEY, codigoPlaylist INT, idmusica INT, titulo VARCHAR(200), endereco VARCHAR(200), duracao VARCHAR(20), artista VARCHAR(100), album VARCHAR(100), codigoUsuario INT)";
		try {
			statement.execute(sqlCriarTabelaMusicaPlaylist);
			System.out.println("Tabela 'MUSICAPLAYLIST' criada com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na criação da tabela 'MUSICAPLAYLIST'");
		}
	}

}