package principal;

import java.sql.Connection;
import conexao.Conexao;
import model.Usuario;
import view.EditMusicaGui;
import view.LoginGui;
import view.PrincipalGui;

public class Principal {

	public static void main(String[] args) {
		
		Connection connection = Conexao.getConnection();
        if (connection != null) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Falha na conexão com o Banco de Dados.");
        }
		//new LoginGui();
        Usuario u = new Usuario();
        u.setCodigo(1);
        new PrincipalGui(u); 
	}
}
