package principal;

import java.sql.Connection;
import conexao.Conexao;
import view.LoginGui;
import view.PrincipalGui;
//OI TESTE SOM SOM
public class Principal {

	public static void main(String[] args) {
		
		Connection connection = Conexao.getConnection();
        if (connection != null) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Falha na conexão com o Banco de Dados.");
        }
		new LoginGui();
        //String a = "Pedro";
        //new PrincipalGui(a);
	}
}
