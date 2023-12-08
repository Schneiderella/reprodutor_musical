package control;

import java.io.File;
import java.util.List;

import dao.MusicaDAO;
import model.Musica;
import model.Playlist;

public class MusicaController {

	MusicaDAO musicaDAO = new MusicaDAO();

	public void cadastraMusica(String titulo, String artista, String album, String endereco, String duracao,
			int codigoUsuario) {
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

	public static List<Musica> retornaMusicasFiltradas(int codigoUsuario, String textoBusca) {
		List<Musica> listaMusicas = MusicaDAO.retornaMusicasFiltradas(codigoUsuario, textoBusca);
		return listaMusicas;
	}

	public boolean removeMusica(int codMusica, int codigoUsuario) {
		boolean removido = musicaDAO.removeMusica(codMusica, codigoUsuario);
		return removido;
	}

	public boolean editaMusica(int codMusica, int codUsuario, String tituloAlterado, String artistaAlterado,
			String albumAlterado) {
		boolean editado = musicaDAO.editamusica(codMusica, codUsuario, tituloAlterado, artistaAlterado, albumAlterado);
		return editado;
	}

	public boolean existemMusicasParaUsuario(int codigoUsuario) {
		boolean existem = musicaDAO.existemMusicasParaUsuario(codigoUsuario);
		return existem;
	}

	public Musica retornaMusicaPeloCodigo(int codigoMusica) {
		Musica musica = musicaDAO.retornaMusicaPeloCodigo(codigoMusica);
		return musica;
	}

	public void cadastraMusicaPlaylist(Musica musica, Playlist playlist) {
		musicaDAO.cadastraMusicaPlaylist(musica, playlist);
	}

	public static List<Musica> retornaMusicasPlaylist(int codigoPlaylist, int codigoUsuario) {
		List<Musica> listaMusicas = MusicaDAO.retornaMusicasPlaylist(codigoPlaylist, codigoUsuario);
		return listaMusicas;
	}

	public void excluirTodasMusicasDaPlaylist(int codigoPlaylist) {
		musicaDAO.excluirTodasMusicasDaPlaylist(codigoPlaylist);
	}

	public boolean existemMusicasNaPlaylist(int codigoPlaylist) {
		boolean existem = musicaDAO.existemMusicasNaPlaylist(codigoPlaylist);
		return existem;
	}

	public void removeMusicaDaPlaylist(int codigoMusica, int codigoPlaylist) {
		musicaDAO.removeMusicaDaPlaylist(codigoMusica, codigoPlaylist);
	}

	public boolean MusicaEstaNaPlaylist(int codigoMusica, int codigoPlaylist) {
		boolean existem = musicaDAO.MusicaEstaNaPlaylist(codigoMusica, codigoPlaylist);
		return existem;
	}

	public static List<Musica> retornaMusicasPlaylistOrdenadas(int codigoPlaylist, int codigoUsuario,
			String colunaOrdenacao) {
		List<Musica> listaMusicas = MusicaDAO.retornaMusicasPlaylistOrdenadas(codigoPlaylist, codigoUsuario,
				colunaOrdenacao);
		return listaMusicas;
	}
	
	public boolean verificaEnderecoMusicaExistente(String enderecoMusica) {
	    File arquivoMusica = new File(enderecoMusica);
	    return arquivoMusica.exists() && arquivoMusica.isFile();
	}

}
