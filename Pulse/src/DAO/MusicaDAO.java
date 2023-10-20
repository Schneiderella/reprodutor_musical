package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Musica;

public class MusicaDAO {

	public static List<Musica> retornaMusicas(int codigoUsuario) {
<<<<<<< HEAD
		List<Musica> listaMusicas = new ArrayList<>();
		String sql = "SELECT IDMUSICA, TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO FROM MUSICA WHERE CODIGOUSUARIO = ?";
=======
	    List<Musica> listaMusicas = new ArrayList<>();
	    String sql = "SELECT IDMUSICA, TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO FROM MUSICA WHERE CODIGOUSUARIO = ?";
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario); // Configura o código do usuário na consulta
			rs = ps.executeQuery();

<<<<<<< HEAD
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
=======
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
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe

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

<<<<<<< HEAD
	public static List<Musica> retornaMusicasFiltradas(int codigoUsuario, String textoBusca) {
		List<Musica> listaMusicas = new ArrayList<>();
		String sql = "SELECT IDMUSICA, TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO FROM MUSICA WHERE CODIGOUSUARIO = ? AND TITULO LIKE ?";

=======
	
	public void cadastraMusica (Musica musica) {
		
		String sql = "INSERT INTO MUSICA (TITULO, ENDERECO, ARTISTA, ALBUM, DURACAO, CODIGOUSUARIO) VALUES (?, ?, ?, ?, ?, ?)"; 
		
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
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
<<<<<<< HEAD

=======
			
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
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
<<<<<<< HEAD
		String sql = "DELETE FROM MUSICA WHERE IDMUSICA = ? AND CODIGOUSUARIO = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, musicaSelecionada);
			ps.setInt(2, codigoUsuario);
=======
        String sql = "DELETE FROM MUSICA WHERE IDMUSICA = ? AND CODIGOUSUARIO = ?";
        PreparedStatement ps = null;

        try {
        	ps = Conexao.getConnection().prepareStatement(sql);
            ps.setInt(1, musicaSelecionada);
            ps.setInt(2, codigoUsuario);
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe

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


	public boolean editamusica(int codMusica, int codUsuario, String tituloAlterado, String artistaAlterado, String albumAlterado) {
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

}
