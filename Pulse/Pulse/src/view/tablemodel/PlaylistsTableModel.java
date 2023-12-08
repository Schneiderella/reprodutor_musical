package view.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Playlist;

@SuppressWarnings("serial")
public class PlaylistsTableModel extends AbstractTableModel {

	private List<Playlist> playlists;
	private static final String nomes[] = { "Nome", "Qtd Músicas", "Duração", "Código" };

	public PlaylistsTableModel() {
		this.playlists = new ArrayList<Playlist>();
	}

	public PlaylistsTableModel(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylist(List<Playlist> playlists) {
		this.playlists = playlists;
		this.fireTableDataChanged();
	}

	public String getColumnName(int number) {
		return nomes[number];
	}

	@Override
	public int getRowCount() {
		if (playlists != null) {
			return playlists.size();
		} else {
			return 0;
		}
	}

	@Override
	public int getColumnCount() {
		return nomes.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Playlist ply = playlists.get(rowIndex);

		columnIndex++;

		switch (columnIndex) {
		case 1:
			return ply.getNome();
		case 2:
			return ply.getqtdMusicas();
		case 3:
			return ply.getDuracao();
		case 4:
			return ply.getCodigo();
		}
		return null;
	}

}
