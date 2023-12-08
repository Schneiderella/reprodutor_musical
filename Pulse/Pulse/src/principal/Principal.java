package principal;

import config.BancoConfig;
import config.UsuarioConfig;
import control.UsuarioController;
import model.Usuario;
import view.LoginGui;
import view.PrincipalGui;

public class Principal {

	public static void main(String[] args) {

		BancoConfig bc = new BancoConfig();
		
		boolean conn = bc.iniciaConexao();

		if (conn) {
			bc.verificarECriarTabelas();
		}
		
		int cod = UsuarioConfig.carregarCodigoUsuario();
		if (cod != -1) {
			UsuarioController uc = new UsuarioController();
			Usuario u = uc.buscarUsuarioPeloCodigo(cod);
			new PrincipalGui(u);
		} else {
			new LoginGui();
		}
		
	}
}
