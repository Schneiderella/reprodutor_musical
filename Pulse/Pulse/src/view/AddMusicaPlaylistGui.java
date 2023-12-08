package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import control.MusicaController;
import control.PlaylistController;
import dao.PlaylistDAO;
import model.Musica;
import model.Playlist;

public class AddMusicaPlaylistGui implements ActionListener {

	JFrame frame = new JFrame();

	JLabel labelPrincipal = new JLabel("Adicionar à Playlist");
	JLabel labelPlaylists = new JLabel();

	JButton botaoOk = new JButton("Selecionar");
	JButton botaoCancelar = new JButton("Cancelar");
	JButton botaoCriarPlaylist = new JButton("Criar Nova Playlist");

	EmptyBorder emptyBorder = new EmptyBorder(0, 10, 0, 0);

	PlaylistController pc = new PlaylistController();
	
	JList<String> jList;
	JScrollPane scrollPane;

	int codUsuario;
	int codMusica;
	PrincipalGui principal;

	@SuppressWarnings("serial")
	public AddMusicaPlaylistGui(int codigoMusica, int codigoUsuario, PrincipalGui principalGui) {

		this.codUsuario = codigoUsuario;
		this.codMusica = codigoMusica;
		this.principal = principalGui;

		// Propriedades da janela principal
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Adicionar à Playlist");

		frame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
					frame.setState(Frame.NORMAL);
				}
			}
		});

		Container contentPane = frame.getContentPane();
		contentPane.setBackground(new Color(43, 43, 43));

		List<String> playlists = PlaylistDAO.retornaNomes(codigoUsuario);

		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (String item : playlists) {
			listModel.addElement(item);
		}

		jList = new JList<>(listModel);
		jList.setBackground(new Color(52, 52, 52));
		jList.setForeground(Color.WHITE);
		jList.setBorder(null);
		jList.setFont(new Font("Arial", Font.BOLD, 12));

		jList.setSelectionBackground(new Color(65, 65, 65));
		jList.setSelectionForeground(new Color(255, 130, 50));

		// Remover a borda da seleção
		jList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (isSelected) {
					setBorder(null);
				}

				setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 3));

				return c;
			}
		});

		scrollPane = new JScrollPane(jList);
		scrollPane.setBounds(40, 92, 245, 125);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(new Color(43, 43, 43));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		labelPrincipal.setBounds(67, 30, 300, 25);
		labelPrincipal.setForeground(Color.WHITE);
		labelPrincipal.setFont(new Font("Arial", Font.BOLD, 20));

		labelPlaylists.setBounds(40, 65, 230, 25);
		labelPlaylists.setForeground(Color.LIGHT_GRAY);
		labelPlaylists.setFont(new Font(null, Font.BOLD, 13));

		botaoOk.setBounds(46, 230, 115, 25);
		botaoOk.setBackground(new Color(255, 130, 50));
		botaoOk.setForeground(Color.WHITE);
		botaoOk.setFocusable(false);
		botaoOk.addActionListener(this);
		botaoOk.setFont(new Font("Arial", Font.BOLD, 12));
		botaoOk.setFocusPainted(false);

		botaoCancelar.setBackground(new Color(25, 25, 25));
		botaoCancelar.setForeground(Color.WHITE);
		botaoCancelar.setFocusable(false);
		botaoCancelar.addActionListener(this);
		botaoCancelar.setFont(new Font("Arial", Font.BOLD, 12));
		botaoCancelar.setFocusPainted(false);
		
		botaoCriarPlaylist.setBackground(new Color(255, 130, 50));
		botaoCriarPlaylist.setForeground(Color.WHITE);
		botaoCriarPlaylist.setFocusable(false);
		botaoCriarPlaylist.addActionListener(this);
		botaoCriarPlaylist.setFont(new Font("Arial", Font.BOLD, 13));
		botaoCriarPlaylist.setFocusPainted(false);

		if (pc.existemPlaylistsParaUsuario(codUsuario)) {
			jList.setVisible(true);
			labelPlaylists.setText("Selecione a playlist");
			botaoOk.setVisible(true);
			botaoCancelar.setBounds(166, 230, 115, 25);
			botaoCriarPlaylist.setBounds(90, 130, 145, 30);
			frame.setSize(340, 330);
			botaoCriarPlaylist.setVisible(false);
		} else {
			jList.setVisible(false);
			labelPlaylists.setText("Você não possui playlists");
			botaoOk.setVisible(false);
			botaoCancelar.setBounds(107, 180, 115, 25);
			botaoCriarPlaylist.setBounds(90, 112, 145, 30);
			frame.setSize(340, 280);
			botaoCriarPlaylist.setVisible(true);
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);
		
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

		botaoCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoCancelar.setBackground(new Color(62, 62, 62));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoCancelar.setBackground(new Color(25, 25, 25));
			}
		});

		frame.add(labelPrincipal);
		frame.add(labelPlaylists);
		frame.add(botaoOk);
		frame.add(botaoCriarPlaylist);
		frame.add(botaoCancelar);
		frame.add(scrollPane);
	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão Salvar
		if (e.getSource() == botaoOk) {
			String selectedValue = jList.getSelectedValue();
			if (selectedValue != null) {
				Playlist playlist = pc.buscarPlaylist(selectedValue, codUsuario);
				String nomePlaylist = playlist.getNome();
				int codigoPlaylist = playlist.getCodigo();
				MusicaController mc = new MusicaController();
				boolean existe = mc.MusicaEstaNaPlaylist(codMusica, codigoPlaylist);
				if (!existe) {
					Musica musica = mc.retornaMusicaPeloCodigo(codMusica);
					String tituloMusica = musica.getTitulo();

					mc.cadastraMusicaPlaylist(musica, playlist);
					atualizarCamposPlaylist(codigoPlaylist);

					JOptionPane.showMessageDialog(frame, "A música " + tituloMusica + " foi adicionada \n à playlist "
							+ nomePlaylist + " com sucesso!");
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Esta música já está nessa playlist!", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Selecione uma playlist!", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		}

		// Botão Cancelar
		if (e.getSource() == botaoCancelar) {
			frame.dispose();
		}
		
		// Botão Criar Playlist
		if (e.getSource() == botaoCriarPlaylist) {
			frame.dispose();
			principal.botaoPlaylist.doClick();
			principal.botaoAddPlaylist.doClick();
		}

	}

	// Atualiza os campos de quantidade de músicas e de duração total da playlist
	public void atualizarCamposPlaylist(int codigoPlaylist) {
		int qtdMusicas = pc.obterQuantidadeMusicas(codigoPlaylist);
		String duracaoTotal = pc.obterDuracaoTotal(codigoPlaylist);
		pc.atualizarCamposPlaylist(codigoPlaylist, qtdMusicas, duracaoTotal);
		principal.atualizarTabelaPlaylist();
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
