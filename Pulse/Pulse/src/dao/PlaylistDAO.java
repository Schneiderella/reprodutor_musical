package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Playlist;

public class PlaylistDAO {

	public void cadastraPlaylist(Playlist playlist) {

		String sql = "INSERT INTO PLAYLIST (NOME, CODUSUARIO, REPRODUCOES) VALUES (?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, playlist.getNome());
			ps.setInt(2, playlist.getCodigoUsuario());
			ps.setInt(3, 0);

			ps.execute();

			if (ps != null) {
				ps.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Falha na inserção de dados!");
		}
	}

	public static List<Playlist> retornaPlaylists(int codigoUsuario) {
		List<Playlist> listaPlaylists = new ArrayList<>();
		String sql = "SELECT CODIGO, NOME, QTDMUSICAS, DURACAO FROM PLAYLIST WHERE CODUSUARIO = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);
			rs = ps.executeQuery();

			while (rs.next()) {
				int codPlaylist = rs.getInt("CODIGO");
				String nome = rs.getString("NOME");
				int qtdMusicas = rs.getInt("QTDMUSICAS");
				String duracao = rs.getString("DURACAO");

				Playlist playlist = new Playlist();
				playlist.setCodigo(codPlaylist);
				playlist.setNome(nome);
				playlist.setqtdMusicas(qtdMusicas);
				playlist.setDuracao(duracao);

				listaPlaylists.add(playlist);
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
		return listaPlaylists;
	}

	public static List<String> retornaNomes(int codigoUsuario) {
		List<String> listaPlaylists = new ArrayList<>();
		String sql = "SELECT NOME FROM PLAYLIST WHERE CODUSUARIO = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoUsuario);
			rs = ps.executeQuery();

			while (rs.next()) {
				String nome = rs.getString("NOME");
				listaPlaylists.add(nome);
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
		return listaPlaylists;
	}

	public boolean removePlaylist(int playlistSelecionada, int codigoUsuario) {
		String sql = "DELETE FROM PLAYLIST WHERE CODIGO = ? AND CODUSUARIO = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, playlistSelecionada);
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

	public boolean existemPlaylistsParaUsuario(int codigoUsuario) {
		String sql = "SELECT COUNT(*) FROM PLAYLIST WHERE CODUSUARIO = ?";
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

	public boolean validaNome(String nome, int codUsuario) {

		String sql = "SELECT COUNT(*) FROM PLAYLIST WHERE NOME = ? AND CODUSUARIO = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, nome);
			ps.setInt(2, codUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					return false;
				} else {
					return true;
				}
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

	public Playlist buscarPlaylist(String nome, int codUsuario) {
		String sql = "SELECT * FROM PLAYLIST WHERE NOME = ? AND CODUSUARIO = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, nome);
			ps.setInt(2, codUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				Playlist playlist = new Playlist();

				playlist.setCodigo(rs.getInt("CODIGO"));
				playlist.setNome(rs.getString("NOME"));

				return playlist;
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

	public int obterQuantidadeMusicasPlaylist(int codigoPlaylist) {
		String sql = "SELECT COUNT(*) AS qtdMusicas FROM MUSICAPLAYLIST WHERE CODIGOPLAYLIST = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoPlaylist);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("qtdMusicas");
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
		return 0;
	}

	public String obterDuracaoTotalPlaylist(int codigoPlaylist) {
		String sql = "SELECT DURACAO FROM MUSICAPLAYLIST WHERE CODIGOPLAYLIST = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codigoPlaylist);

			rs = ps.executeQuery();

			int duracaoTotalSegundos = 0;

			while (rs.next()) {
				String duracao = rs.getString("DURACAO");
				String[] partes = duracao.split(":");
				if (partes.length == 2) {
					int minutos = Integer.parseInt(partes[0]);
					int segundos = Integer.parseInt(partes[1]);
					duracaoTotalSegundos += minutos * 60 + segundos;
				}
			}

			int duracaoMinutos = duracaoTotalSegundos / 60;
			int duracaoSegundos = duracaoTotalSegundos % 60;
			String duracaoTotalFormatada = String.format("%02d:%02d", duracaoMinutos, duracaoSegundos);
			return duracaoTotalFormatada;
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

	public void atualizarCamposPlaylist(int codigoPlaylist, int qtdMusicas, String duracaoTotal) {
		String sql = "UPDATE PLAYLIST SET QTDMUSICAS = ?, DURACAO = ? WHERE CODIGO = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, qtdMusicas);
			ps.setString(2, duracaoTotal);
			ps.setInt(3, codigoPlaylist);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Falha na atualização de campos da playlist!");
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

	public void editaPlaylist(int codPlaylist, String tituloAlterado, String ordemAlterada) {
		String sql = "UPDATE PLAYLIST SET NOME = ?, ORDEM = ? WHERE CODIGO = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, tituloAlterado);
			ps.setString(2, ordemAlterada);
			ps.setInt(3, codPlaylist);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Falha ao atualizar o título da playlist ou a ordem!");
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

	public void editaTituloPlaylist(int codPlaylist, String tituloAlterado) {
		String sql = "UPDATE PLAYLIST SET NOME = ? WHERE CODIGO = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, tituloAlterado);
			ps.setInt(2, codPlaylist);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Falha ao atualizar o título da playlist!");
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

	public String obterOrdemDaPlaylist(int codPlaylist) {
		String ordem = null;
		String sql = "SELECT ORDEM FROM PLAYLIST WHERE CODIGO = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, codPlaylist);
			rs = ps.executeQuery();

			if (rs.next()) {
				ordem = rs.getString("ORDEM");
			}
		} catch (SQLException e) {
			System.out.println("Falha ao obter a ordem da playlist!");
			e.printStackTrace();
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

		return ordem;
	}

	public List<Playlist> retornaTop3Playlists() {
		String sql = "SELECT * FROM PLAYLIST WHERE REPRODUCOES > 0 ORDER BY REPRODUCOES DESC LIMIT 3";
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Playlist> top3Playlists = new ArrayList<>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Playlist playlist = new Playlist();

				playlist.setCodigo(rs.getInt("CODIGO"));
				playlist.setNome(rs.getString("NOME"));
				playlist.setqtdMusicas(rs.getInt("QTDMUSICAS"));
				playlist.setDuracao(rs.getString("DURACAO"));
				playlist.setOrdem(rs.getString("ORDEM"));
				playlist.setCodigoUsuario(rs.getInt("CODUSUARIO"));
				playlist.setReproducoes(rs.getInt("REPRODUCOES"));

				top3Playlists.add(playlist);
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

		return top3Playlists;
	}
	
	public void incrementarReproducoes(int codigoPlaylist) {
        String sql = "UPDATE PLAYLIST SET REPRODUCOES = REPRODUCOES + 1 WHERE CODIGO = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConnection().prepareStatement(sql);
            ps.setInt(1, codigoPlaylist);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao incrementar as reproduções da playlist!");
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
	
    public void zerarReproducoesDeTodasPlaylists() {
        String sql = "UPDATE PLAYLIST SET REPRODUCOES = 0";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Falha ao zerar as reproduções de todas as playlists!");
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

}
