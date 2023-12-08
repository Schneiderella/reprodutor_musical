package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Musica;
import model.Playlist;

public class MusicaDAO {

	public static List<Musica> retornaMusicas(int codigoUsuario) {
		List<Musica> listaMusicas = new ArrayList<>();
		String sql = "SELECT IDMUSICA, TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO FROM MUSICA WHERE CODIGOUSUARIO = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);
			rs = ps.executeQuery();

			while (rs.next()) {
				int codMusica = rs.getInt("IDMUSICA");
				String titulo = rs.getString("TITULO");
				String endereco = rs.getString("ENDERECO");
				String duracao = rs.getString("DURACAO");
				String artista = rs.getString("ARTISTA");
				String album = rs.getString("ALBUM");

				Musica musica = new Musica();
				musica.setCodigo(codMusica);
				musica.setTitulo(titulo);
				musica.setEndereco(endereco);
				musica.setDuracao(duracao);
				musica.setArtista(artista);
				musica.setAlbum(album);

				listaMusicas.add(musica);
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaMusicas;
	}

	public static List<Musica> retornaMusicasFiltradas(int codigoUsuario, String textoBusca) {
		List<Musica> listaMusicas = new ArrayList<>();
		String sql = "SELECT IDMUSICA, TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO FROM MUSICA WHERE CODIGOUSUARIO = ? AND TITULO LIKE ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);
			ps.setString(2, "%" + textoBusca + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				int codMusica = rs.getInt("IDMUSICA");
				String titulo = rs.getString("TITULO");
				String endereco = rs.getString("ENDERECO");
				String duracao = rs.getString("DURACAO");
				String artista = rs.getString("ARTISTA");
				String album = rs.getString("ALBUM");

				Musica musica = new Musica();
				musica.setCodigo(codMusica);
				musica.setTitulo(titulo);
				musica.setEndereco(endereco);
				musica.setDuracao(duracao);
				musica.setArtista(artista);
				musica.setAlbum(album);

				listaMusicas.add(musica);
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaMusicas;
	}

	public void cadastraMusica(Musica musica) {

		String sql = "INSERT INTO MUSICA (TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO, CODIGOUSUARIO) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, musica.getTitulo());
			ps.setString(2, musica.getEndereco());
			ps.setString(3, musica.getArtista());
			ps.setString(4, musica.getAlbum());
			ps.setString(5, musica.getDuracao());
			ps.setInt(6, musica.getCodigoUsuario());

			ps.execute();

			if (ps != null) {
				ps.close();
			}

		} catch (SQLException e) {
			System.out.println("Falha na inserção de dados!");
		}
	}

	public boolean validaMusica(String endereco, int codigoUsuario) {
		String sql = "SELECT COUNT(*) FROM MUSICA WHERE ENDERECO = ? AND CODIGOUSUARIO = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, endereco);
			ps.setInt(2, codigoUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count == 0;
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean removeMusica(int musicaSelecionada, int codigoUsuario) {
		String sql = "DELETE FROM MUSICA WHERE IDMUSICA = ? AND CODIGOUSUARIO = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, musicaSelecionada);
			ps.setInt(2, codigoUsuario);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Falha ao remover a música!");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean editamusica(int codMusica, int codUsuario, String tituloAlterado, String artistaAlterado,
			String albumAlterado) {
		String sql = "UPDATE MUSICA SET TITULO = ?, ARTISTA = ?, ALBUM = ? WHERE IDMUSICA = ? AND CODIGOUSUARIO = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, tituloAlterado);
			ps.setString(2, artistaAlterado);
			ps.setString(3, albumAlterado);
			ps.setInt(4, codMusica);
			ps.setInt(5, codUsuario);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Falha ao atualizar a música!");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean existemMusicasParaUsuario(int codigoUsuario) {
		String sql = "SELECT COUNT(*) FROM MUSICA WHERE CODIGOUSUARIO = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Musica retornaMusicaPeloCodigo(int codigoMusica) {
		String sql = "SELECT * FROM MUSICA WHERE IDMUSICA = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoMusica);

			rs = ps.executeQuery();

			if (rs.next()) {
				Musica musica = new Musica();

				musica.setCodigo(rs.getInt("IDMUSICA"));
				musica.setTitulo(rs.getString("TITULO"));
				musica.setEndereco(rs.getString("ENDERECO"));
				musica.setDuracao(rs.getString("DURACAO"));
				musica.setArtista(rs.getString("ARTISTA"));
				musica.setAlbum(rs.getString("ALBUM"));
				musica.setCodigoUsuario(rs.getInt("CODIGOUSUARIO"));

				return musica;
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void cadastraMusicaPlaylist(Musica musica, Playlist playlist) {

		String sql = "INSERT INTO MUSICAPLAYLIST (TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO, CODIGOUSUARIO, IDMUSICA, CODIGOPLAYLIST) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, musica.getTitulo());
			ps.setString(2, musica.getEndereco());
			ps.setString(3, musica.getArtista());
			ps.setString(4, musica.getAlbum());
			ps.setString(5, musica.getDuracao());
			ps.setInt(6, musica.getCodigoUsuario());
			ps.setInt(7, musica.getCodigo());
			ps.setInt(8, playlist.getCodigo());

			ps.execute();

			if (ps != null) {
				ps.close();
			}

		} catch (SQLException e) {
			System.out.println("Falha na inserção de dados!");
		}
	}

	public static List<Musica> retornaMusicasPlaylist(int codigoPlaylist, int codigoUsuario) {
		List<Musica> listaMusicas = new ArrayList<>();
		String sql = "SELECT IDMUSICA, TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO FROM MUSICAPLAYLIST WHERE CODIGOUSUARIO = ? AND CODIGOPLAYLIST = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);
			ps.setInt(2, codigoPlaylist);
			rs = ps.executeQuery();

			while (rs.next()) {
				int codMusica = rs.getInt("IDMUSICA");
				String titulo = rs.getString("TITULO");
				String endereco = rs.getString("ENDERECO");
				String duracao = rs.getString("DURACAO");
				String artista = rs.getString("ARTISTA");
				String album = rs.getString("ALBUM");

				Musica musica = new Musica();
				musica.setCodigo(codMusica);
				musica.setTitulo(titulo);
				musica.setEndereco(endereco);
				musica.setDuracao(duracao);
				musica.setArtista(artista);
				musica.setAlbum(album);

				listaMusicas.add(musica);
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaMusicas;
	}

	public void excluirTodasMusicasDaPlaylist(int codigoPlaylist) {
		String sql = "DELETE FROM MUSICAPLAYLIST WHERE CODIGOPLAYLIST = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoPlaylist);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Falha ao remover as músicas da playlist!");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean existemMusicasNaPlaylist(int codigoPlaylist) {
		String sql = "SELECT COUNT(*) FROM MUSICAPLAYLIST WHERE CODIGOPLAYLIST = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoPlaylist);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void removeMusicaDaPlaylist(int codigoMusica, int codigoPlaylist) {
		String sql = "DELETE FROM MUSICAPLAYLIST WHERE IDMUSICA = ? AND CODIGOPLAYLIST = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoMusica);
			ps.setInt(2, codigoPlaylist);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Falha ao remover a música da playlist!");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean MusicaEstaNaPlaylist(int codigoMusica, int codigoPlaylist) {
		String sql = "SELECT COUNT(*) FROM MUSICAPLAYLIST WHERE CODIGOPLAYLIST = ? AND IDMUSICA = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoPlaylist);
			ps.setInt(2, codigoMusica);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static List<Musica> retornaMusicasPlaylistOrdenadas(int codigoPlaylist, int codigoUsuario,
			String ordem) {
		List<Musica> listaMusicas = new ArrayList<>();
		String sql = "SELECT IDMUSICA, TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO FROM MUSICAPLAYLIST "
				+ "WHERE CODIGOUSUARIO = ? AND CODIGOPLAYLIST = ? " + "ORDER BY " + ordem;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);
			ps.setInt(2, codigoPlaylist);
			rs = ps.executeQuery();

			while (rs.next()) {
				int codMusica = rs.getInt("IDMUSICA");
				String titulo = rs.getString("TITULO");
				String endereco = rs.getString("ENDERECO");
				String duracao = rs.getString("DURACAO");
				String artista = rs.getString("ARTISTA");
				String album = rs.getString("ALBUM");

				Musica musica = new Musica();
				musica.setCodigo(codMusica);
				musica.setTitulo(titulo);
				musica.setEndereco(endereco);
				musica.setDuracao(duracao);
				musica.setArtista(artista);
				musica.setAlbum(album);

				listaMusicas.add(musica);
			}
		} catch (SQLException e) {
			System.out.println("Falha na consulta de dados!");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaMusicas;
	}
	
}
