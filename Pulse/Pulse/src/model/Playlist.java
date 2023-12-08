package model;

public class Playlist {

	private int codigo;
	private String nome;
	private int qtdMusicas;
	private String duracao;
	private String ordem;
	private int codigoUsuario;
	private int reproducoes;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getqtdMusicas() {
		return qtdMusicas;
	}

	public void setqtdMusicas(int qtdMusicas) {
		this.qtdMusicas = qtdMusicas;
	}

	public String getDuracao() {
		return duracao;
	}

	public String setDuracao(String duracao) {
		return this.duracao = duracao;
	}

	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public int getReproducoes() {
		return reproducoes;
	}
	
	public void setReproducoes(int reproducoes) {
		this.reproducoes = reproducoes;
	}

    public void incrementarReproducoes() {
        reproducoes++;
    }

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

}
