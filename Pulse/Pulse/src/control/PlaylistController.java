package control;

import java.util.List;

import dao.PlaylistDAO;
import model.Playlist;

public class PlaylistController {

	PlaylistDAO playlistDAO = new PlaylistDAO();

	public void cadastraPlaylist(String nome, int codigoUsuario) {
		Playlist p = new Playlist();
		p.setNome(nome);
		p.setCodigoUsuario(codigoUsuario);
		playlistDAO.cadastraPlaylist(p);
	}

	public static List<Playlist> retornaMusicas(int codigoUsuario) {
		List<Playlist> listaMusicas = PlaylistDAO.retornaPlaylists(codigoUsuario);
		return listaMusicas;
	}

	public boolean removePlaylist(int codPlaylist, int codigoUsuario) {
		boolean removido = playlistDAO.removePlaylist(codPlaylist, codigoUsuario);
		return removido;
	}

	public boolean existemPlaylistsParaUsuario(int codigoUsuario) {
		boolean existem = playlistDAO.existemPlaylistsParaUsuario(codigoUsuario);
		return existem;
	}

	public boolean validaNome(String nome, int codUsuario) {
		boolean validaNome = playlistDAO.validaNome(nome, codUsuario);
		return validaNome;
	}

	public Playlist buscarPlaylist(String nome, int codUsuario) {
		Playlist codigoPlaylist = playlistDAO.buscarPlaylist(nome, codUsuario);
		return codigoPlaylist;
	}

	public int obterQuantidadeMusicas(int codigoPlaylist) {
		int qtdMusicas = playlistDAO.obterQuantidadeMusicasPlaylist(codigoPlaylist);
		return qtdMusicas;
	}

	public String obterDuracaoTotal(int codigoPlaylist) {
		String duracaoTotal = playlistDAO.obterDuracaoTotalPlaylist(codigoPlaylist);
		return duracaoTotal;
	}
	
	public List<Playlist> retornaTop3Playlists() {
		List<Playlist> playlist = playlistDAO.retornaTop3Playlists();
		return playlist;
	}

	public void atualizarCamposPlaylist(int codigoPlaylist, int qtdMusicas, String duracaoTotal) {
		playlistDAO.atualizarCamposPlaylist(codigoPlaylist, qtdMusicas, duracaoTotal);
	}
	
	public void editaPlaylist(int codPlaylist, String tituloAlterado, String ordemAlterada) {
		playlistDAO.editaPlaylist(codPlaylist, tituloAlterado, ordemAlterada);
	}

	public void editaTituloPlaylist(int codigoPlaylist, String tituloAlterado) {
		playlistDAO.editaTituloPlaylist(codigoPlaylist, tituloAlterado);
	}
	
	public String obterOrdemDaPlaylist(int codPlaylist) {
		String ordem = playlistDAO.obterOrdemDaPlaylist(codPlaylist);
		return ordem;
	}
	
	public void incrementarReproducoes(int codigoPlaylist) {
		playlistDAO.incrementarReproducoes(codigoPlaylist);
	}
	
	public void zerarReproducoesDeTodasPlaylists() {
		playlistDAO.zerarReproducoesDeTodasPlaylists();
	}

}
