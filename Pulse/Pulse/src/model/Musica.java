package model;

import java.util.Objects;

public class Musica {

	private int codigo;
	private String titulo;
	private String artista;
	private String album;
	private String endereco;
	private String duracao;
	private int codigoUsuario;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao2) {
		this.duracao = duracao2;
	}

	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Musica musica = (Musica) o;
		return codigo == musica.codigo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

}
