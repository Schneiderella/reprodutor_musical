package control;

import java.util.List;

import DAO.MusicaDAO;
import model.Musica;

public class MusicaController {

	MusicaDAO musicaDAO = new MusicaDAO();
	
	public void cadastraMusica(String titulo, String artista, String album, String endereco, String duracao, int codigoUsuario) {
		Musica m = new Musica();
		m.setTitulo(titulo);
		m.setArtista(artista);
		m.setAlbum(album);
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

	public boolean removeMusica(int codMusica, int codigoUsuario) {
		boolean removido = musicaDAO.removeMusica(codMusica, codigoUsuario);
		return removido;
	}

	public boolean editaMusica(int codMusica, int codUsuario, String tituloAlterado, String artistaAlterado, String albumAlterado) {
		boolean editado = musicaDAO.editamusica(codMusica, codUsuario, tituloAlterado, artistaAlterado, albumAlterado);
		return editado;
	}
	
}
