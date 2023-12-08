package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import model.Usuario;

public class UsuarioDAO {

	public void cadastraUsuario(Usuario usuario) {

		String sql = "INSERT INTO USUARIO (NOME, LOGIN, SENHA) VALUES (?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getLogin());

			String senhaCriptografada = hashSenha(usuario.getSenha());
			ps.setString(3, senhaCriptografada);

			ps.execute();

			if (ps != null) {
				ps.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na inserção de dados!");
		}

	}

	public int validaUsuario(String usuario, String senha) {

		String sql = "SELECT COUNT(*) FROM USUARIO WHERE LOGIN = ? AND SENHA = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, usuario);

			String senhaCriptografada = hashSenha(senha);
			ps.setString(2, senhaCriptografada);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					return 1;
				} else {
					return 0;
				}
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public boolean validaLogin(String login) {

		String sql = "SELECT COUNT(*) FROM USUARIO WHERE LOGIN = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, login);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Usuario buscarUsuario(String login, String senha) {
		String sql = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, login);
			String senhaCriptografada = hashSenha(senha);
			ps.setString(2, senhaCriptografada);

			rs = ps.executeQuery();

			if (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setCodigo(rs.getInt("codigo"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				return usuario;
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Usuario buscarUsuarioPeloCodigo(int codigoUsuario) {
		String sql = "SELECT * FROM USUARIO WHERE CODIGO = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setCodigo(rs.getInt("codigo"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				return usuario;
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private String hashSenha(String senha) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] senhaBytes = senha.getBytes();
			byte[] hashBytes = md.digest(senhaBytes);

			StringBuilder hexHash = new StringBuilder();
			for (byte b : hashBytes) {
				hexHash.append(String.format("%02x", b));
			}

			return hexHash.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

}
