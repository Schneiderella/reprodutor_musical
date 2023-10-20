package principal;

import java.sql.Connection;
import conexao.Conexao;
import model.Usuario;
<<<<<<< HEAD
=======
import view.EditMusicaGui;
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
import view.LoginGui;
import view.PrincipalGui;

public class Principal {

	public static void main(String[] args) {

		Connection connection = Conexao.getConnection();
<<<<<<< HEAD
		if (connection != null) {
			System.out.println("Conexão bem-sucedida!");
		} else {
			System.out.println("Falha na conexão com o Banco de Dados.");
		}
		// new LoginGui();
		Usuario u = new Usuario();
		u.setCodigo(1);
		new PrincipalGui(u);
=======
        if (connection != null) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Falha na conexão com o Banco de Dados.");
        }
		//new LoginGui();
        Usuario u = new Usuario();
        u.setCodigo(1);
        new PrincipalGui(u); 
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
	}
}
