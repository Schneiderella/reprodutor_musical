package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Musica;

@SuppressWarnings("serial")
public class MusicasTableModel extends AbstractTableModel {

	private List<Musica> musicas;
	private static final String nomes[] = { "Título", "Artista", "Álbum", "Duração" };

	public MusicasTableModel() {
		this.musicas = new ArrayList<Musica>();
	}

	public MusicasTableModel(List<Musica> musicas) {
		this.musicas = musicas;
	}

	public List<Musica> getMusicas() {
		return musicas;
	}

	public void setMusica(List<Musica> musicas) {
		this.musicas = musicas;
		this.fireTableDataChanged();
	}

	public String getColumnName(int number) {
		return nomes[number];
	}

	@Override
	public int getRowCount() {
		if (musicas != null) {
			return musicas.size();
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

		Musica msc = musicas.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return msc.getTitulo();
		case 1:
			return msc.getArtista();
		case 2:
			return msc.getAlbum();
		case 3:
			return msc.getDuracao();
		}
		return null;
	}
	
}
