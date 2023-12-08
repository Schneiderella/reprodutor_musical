package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import control.MusicaController;
import control.PlaylistController;
import model.Musica;
import view.tablemodel.MusicasTableModel;

public class SeePlaylistGui implements ActionListener {

	JFrame frame = new JFrame();

	JLabel labelPrincipal = new JLabel();
	JLabel labelMusicas = new JLabel();

	JButton botaoOk = new JButton("Ok");
	JButton botaoReproduzir = new JButton("Reproduzir tudo");
	JButton botaoFila = new JButton("Adicionar à fila");

	JTable tabelaMusicasPlaylist;

	EmptyBorder emptyBorder = new EmptyBorder(0, 10, 0, 0);

	MusicaController mc = new MusicaController();
	PlaylistController pc = new PlaylistController();

	private int codUsuario;
	private int codigoPlaylist;
	private String nomePlaylist;
	private PrincipalGui principal;
	protected int codMusicaSelecionado;
	private String ordemPlaylist;

	public SeePlaylistGui(String nomePlaylistSelecionado, int codPlaylistSelecionado, int codigoUsuario,
			PrincipalGui principalGui) {

		this.codUsuario = codigoUsuario;
		this.principal = principalGui;
		this.codigoPlaylist = codPlaylistSelecionado;
		this.nomePlaylist = nomePlaylistSelecionado;
		this.ordemPlaylist = pc.obterOrdemDaPlaylist(codigoPlaylist);
		
		labelPrincipal.setText("Playlist " + nomePlaylist);
		labelPrincipal.setFont(new Font("Calibri", Font.BOLD, 32));
		labelPrincipal.setForeground(Color.WHITE);

		labelMusicas.setText("Músicas");
		labelMusicas.setForeground(Color.LIGHT_GRAY);
		labelMusicas.setFont(new Font(null, Font.BOLD, 12));
		labelMusicas.setBounds(43, 128, 200, 25);
		
		frame.setSize(580, 560);
		
		JPanel panel = new JPanel();
        panel.setBackground(new Color(43, 43, 43)); 
        panel.setBounds(0, 0, frame.getWidth(), 70);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 26));
        panel.add(labelPrincipal);

		// Propriedades da janela principal
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Reproduzir Playlist");

		frame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
					frame.setState(Frame.NORMAL);
				}
			}
		});

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

		Container contentPane = frame.getContentPane();
		contentPane.setBackground(new Color(43, 43, 43));

		botaoReproduzir.setBackground(new Color(255, 130, 50));
		botaoReproduzir.setForeground(Color.WHITE);
		botaoReproduzir.setFocusable(false);
		botaoReproduzir.addActionListener(this);
		botaoReproduzir.setFont(new Font("Arial", Font.BOLD, 12));
		botaoReproduzir.setFocusPainted(false);
		botaoReproduzir.setBounds(43, 90, 125, 25);
		
		botaoFila.setBackground(new Color(25, 25, 25));
		botaoFila.setForeground(Color.WHITE);
		botaoFila.setFocusable(false);
		botaoFila.addActionListener(this);
		botaoFila.setFont(new Font("Arial", Font.BOLD, 12));
		botaoFila.setFocusPainted(false);
		botaoFila.setBounds(178, 90, 125, 25);
		
		botaoOk.setBackground(new Color(255, 130, 50));
		botaoOk.setForeground(Color.WHITE);
		botaoOk.setFocusable(false);
		botaoOk.addActionListener(this);
		botaoOk.setFont(new Font("Arial", Font.BOLD, 12));
		botaoOk.setFocusPainted(false);
		botaoOk.setBounds(223, 460, 115, 25);

		// Tabela de Músicas
		if (ordemPlaylist != null) {
			tabelaMusicasPlaylist = new JTable(
					new MusicasTableModel(MusicaController.retornaMusicasPlaylistOrdenadas(codigoPlaylist, codUsuario, ordemPlaylist)));
		} else {
			tabelaMusicasPlaylist = new JTable(
					new MusicasTableModel(MusicaController.retornaMusicasPlaylist(codigoPlaylist, codUsuario)));
		}
		tabelaMusicasPlaylist.setBackground(new Color(52, 52, 52));
		tabelaMusicasPlaylist.setForeground(Color.WHITE);
		tabelaMusicasPlaylist.setBorder(null);
		tabelaMusicasPlaylist.setShowGrid(false);
		tabelaMusicasPlaylist.setFont(new Font("Arial", Font.BOLD, 12));
		tabelaMusicasPlaylist.setRowHeight(30);
		tabelaMusicasPlaylist.setTableHeader(null);

		tabelaMusicasPlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaMusicasPlaylist.setSelectionBackground(new Color(52, 52, 52));
		tabelaMusicasPlaylist.setSelectionForeground(Color.WHITE);

		// Captura a seleção do usuário
		tabelaMusicasPlaylist.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {
					int selectedRow = tabelaMusicasPlaylist.getSelectedRow();
					if (selectedRow != -1) {
						codMusicaSelecionado = (int) tabelaMusicasPlaylist.getValueAt(selectedRow, 4);
					}
				}
			}
		});

		principal.aplicarRenderizadorATabela(tabelaMusicasPlaylist);

		JScrollPane scroll = new JScrollPane(this.tabelaMusicasPlaylist);
		scroll.setBorder(new MatteBorder(null));
		scroll.getViewport().setBackground(new Color(43, 43, 43));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		scroll.setBounds(40, 152, 490, 282);

		// Métodos que reajem com o passar do mouse
		botaoOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoOk.setBackground(new Color(250, 140, 72));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoOk.setBackground(new Color(255, 130, 50));
			}
		});
		
		frame.add(panel);
		frame.add(labelMusicas);
		frame.add(botaoReproduzir);
		frame.add(botaoFila);
		frame.add(botaoOk);
		frame.add(scroll);

	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão Ok
		if (e.getSource() == botaoOk) {
			frame.dispose();
		}
		
		// Botão Adicionar na Fila
		if (e.getSource() == botaoFila) {
			if (ordemPlaylist!= null) {
				List<Musica> musicasPlaylist = MusicaController.retornaMusicasPlaylistOrdenadas(codigoPlaylist, codUsuario, ordemPlaylist);
				principal.adicionarPlaylistNaFila(musicasPlaylist);
			} else {
				List<Musica> musicasPlaylist = MusicaController.retornaMusicasPlaylist(codigoPlaylist, codUsuario);
				principal.adicionarPlaylistNaFila(musicasPlaylist);
			}	
			JOptionPane.showMessageDialog(frame, "As músicas da Playlist " + nomePlaylist + " foram adicionadas à fila com sucesso!");
			principal.botaoRemoveFila.setVisible(true);
			principal.botaoLimpaFila.setVisible(true);
			principal.labelFilaVazia.setVisible(false);
			
			pc.incrementarReproducoes(codigoPlaylist);
			principal.playlistsMaisOuvidas = pc.retornaTop3Playlists();
			principal.atualizarTabelaPlaylistsOuvidas();
			principal.labelPlaylistsOuvidas.setVisible(true);
		}
		
		// Botão Reproduzir Tudo
		if (e.getSource() == botaoReproduzir) {
			principal.limparFilaDeReproducao();
			if (ordemPlaylist!= null) {
				List<Musica> musicasPlaylist = MusicaController.retornaMusicasPlaylistOrdenadas(codigoPlaylist, codUsuario, ordemPlaylist);
				principal.adicionarPlaylistNaFila(musicasPlaylist);
			} else {
				List<Musica> musicasPlaylist = MusicaController.retornaMusicasPlaylist(codigoPlaylist, codUsuario);
				principal.adicionarPlaylistNaFila(musicasPlaylist);
			}	
			principal.musicaAnterior = null;
			principal.botaoRemoveFila.setVisible(true);
			principal.botaoLimpaFila.setVisible(true);
			principal.labelFilaVazia.setVisible(false);
			Musica proximaMusica = principal.musicasFila.get(0);
			principal.iniciarReproducaoFila(proximaMusica);
			principal.avancaMusica();
			
			pc.incrementarReproducoes(codigoPlaylist);
			principal.playlistsMaisOuvidas = pc.retornaTop3Playlists();
			principal.atualizarTabelaPlaylistsOuvidas();
			principal.labelPlaylistsOuvidas.setVisible(true);
		}

	}

	// Métodos para customizar a scroll
	static class CustomScrollBarUI extends BasicScrollBarUI {
		protected void configureScrollBarColors() {
			thumbColor = Color.DARK_GRAY;
			thumbDarkShadowColor = null;
			thumbHighlightColor = null;
			thumbLightShadowColor = null;
			trackColor = new Color(43, 43, 43);
			scrollbar.setBackground(new Color(0, 0, 0, 0));
		}

		@Override
		protected JButton createDecreaseButton(int orientation) {
			JButton button = super.createDecreaseButton(orientation);
			button.setBackground(new Color(0, 0, 0, 0));
			button.setBorder(BorderFactory.createEmptyBorder());
			return button;
		}

		@Override
		protected JButton createIncreaseButton(int orientation) {
			JButton button = super.createIncreaseButton(orientation);
			button.setBackground(new Color(0, 0, 0, 0));
			button.setBorder(BorderFactory.createEmptyBorder());
			return button;
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
				return;
			}

			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(new Color(100, 100, 100));
			g2.fillRoundRect(thumbBounds.x, thumbBounds.y, 5, thumbBounds.height, 10, 10);
		}
	}

}
