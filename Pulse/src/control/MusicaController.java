package control;

import java.util.List;

import DAO.MusicaDAO;
import model.Musica;

public class MusicaController {

	MusicaDAO musicaDAO = new MusicaDAO();
<<<<<<< HEAD

	public void cadastraMusica(String titulo, String artista, String album, String endereco, String duracao,
			int codigoUsuario) {
=======
	
	public void cadastraMusica(String titulo, String artista, String album, String endereco, String duracao, int codigoUsuario) {
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
		Musica m = new Musica();
		m.setTitulo(titulo);
		m.setArtista(artista);
		m.setAlbum(album);
		m.setEndereco(endereco);
		m.setDuracao(duracao);
		m.setCodigoUsuario(codigoUsuario);
		musicaDAO.cadastraMusica(m);
	}

	public boolean validaMusica(String endereco, int codigoUsuario) {
		boolean validaMusica = musicaDAO.validaMusica(endereco, codigoUsuario);
		return validaMusica;
	}

	public static List<Musica> retornaMusicas(int codigoUsuario) {
		List<Musica> listaMusicas = MusicaDAO.retornaMusicas(codigoUsuario);
		return listaMusicas;
	}

<<<<<<< HEAD
	public static List<Musica> retornaMusicasFiltradas(int codigoUsuario, String textoBusca) {
		List<Musica> listaMusicas = MusicaDAO.retornaMusicasFiltradas(codigoUsuario, textoBusca);
		return listaMusicas;
	}

=======
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
	public boolean removeMusica(int codMusica, int codigoUsuario) {
		boolean removido = musicaDAO.removeMusica(codMusica, codigoUsuario);
		return removido;
	}

<<<<<<< HEAD
	public boolean editaMusica(int codMusica, int codUsuario, String tituloAlterado, String artistaAlterado,
			String albumAlterado) {
=======
	public boolean editaMusica(int codMusica, int codUsuario, String tituloAlterado, String artistaAlterado, String albumAlterado) {
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
		boolean editado = musicaDAO.editamusica(codMusica, codUsuario, tituloAlterado, artistaAlterado, albumAlterado);
		return editado;
	}
	
	public boolean existemMusicasParaUsuario(int codigoUsuario) {
		boolean existem = musicaDAO.existemMusicasParaUsuario(codigoUsuario);
		return existem;
	}

}
