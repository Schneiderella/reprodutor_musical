package control;

import java.util.List;

import DAO.MusicaDAO;
import model.Musica;

public class MusicaController {

	MusicaDAO musicaDAO = new MusicaDAO();
	
	public void cadastraMusica(String titulo, String artista, String album, String endereco, float duracao, int codigoUsuario) {
		Musica m = new Musica();
		m.setTitulo(titulo);
		m.setEndereco(endereco);
		m.setDuracao(duracao);
		m.setCodigoUsuario(codigoUsuario);
		musicaDAO.cadastraMusica(m);
	}

	public boolean validaMusica(String endereco, int codigoUsuario){
		boolean validaMusica = musicaDAO.validaMusica(endereco, codigoUsuario);
		return validaMusica;
	}

	public static List<Musica> retornaMusicas(int codigoUsuario) {
		List<Musica> listaMusicas = MusicaDAO.retornaMusicas(codigoUsuario);
		return listaMusicas;
	}

	public boolean removeMusica(String titulo, int codigoUsuario) {
		boolean removido = musicaDAO.removeMusica(titulo, codigoUsuario);
		return removido;
	}
	
}
