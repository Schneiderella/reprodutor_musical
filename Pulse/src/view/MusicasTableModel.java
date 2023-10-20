package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Musica;

@SuppressWarnings("serial")
public class MusicasTableModel extends AbstractTableModel {

	private List<Musica> musicas;
<<<<<<< HEAD
	private static final String nomes[] = { "Título", "Artista", "Álbum", "Duração", "Código", "Endereço" };
=======
	private static final String nomes[] = {"Título", "Artista", "Álbum", "Duração", "Código", "Endereço" };
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe

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
		
		columnIndex++;

<<<<<<< HEAD
		columnIndex++;

		switch (columnIndex) {
		case 1:
			return msc.getTitulo();
		case 2:
			return msc.getArtista();
		case 3:
			return msc.getAlbum();
		case 4:
			return msc.getDuracao();
		case 5:
			return msc.getCodigo();
		case 6:
			return msc.getEndereco();
		}
=======
        switch (columnIndex) {
        case 1:
            return msc.getTitulo();
        case 2: 
            return msc.getArtista();
        case 3: 
            return msc.getAlbum();
        case 4: 
            return msc.getDuracao();
        case 5: 
            return msc.getCodigo(); 
        case 6: 
            return msc.getEndereco(); 
        }
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
		return null;
	}

}
