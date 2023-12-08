package control;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioController {

	UsuarioDAO usuarioDAO = new UsuarioDAO();

	public int validaUsuario(String login, String senha) {
		int validaUsuario = usuarioDAO.validaUsuario(login, senha);
		return validaUsuario;
	}

	public void cadastraUsuario(String nome, String login, String senha) {
		Usuario u = new Usuario();
		u.setNome(nome);
		u.setLogin(login);
		u.setSenha(senha);
		usuarioDAO.cadastraUsuario(u);
	}

	public boolean validaLogin(String login) {
		boolean validaUsuario = usuarioDAO.validaLogin(login);
		return validaUsuario;
	}

	public boolean validaSenha(String senha, String senha2) {
		return senha.equals(senha2);
	}

	public Usuario buscarUsuario(String login, String senha) {
		Usuario u = usuarioDAO.buscarUsuario(login, senha);
		return u;
	}

	public Usuario buscarUsuarioPeloCodigo(int codigoUsuario) {
		Usuario u = usuarioDAO.buscarUsuarioPeloCodigo(codigoUsuario);
		return u;
	}

}
