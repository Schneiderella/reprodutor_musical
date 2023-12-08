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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import control.MusicaController;
import control.PlaylistController;
import view.tablemodel.MusicasTableModel;

public class EditPlaylistGui implements ActionListener {

	JFrame frame = new JFrame();

	ImageIcon iconeRemover = new ImageIcon("auxiliar/icones/icone_remover.png");

	JTextField campoNome = new JTextField();

	JLabel labelEdicao = new JLabel("Editar Informações da Playlist");
	JLabel labelNome = new JLabel("Nome");
	JLabel labelMusicas = new JLabel();
	JLabel labelOrdena = new JLabel("Ordenação");

	JButton botaoSalvar = new JButton("Salvar");
	JButton botaoCancelar = new JButton("Cancelar");
	JButton botaoRemoveMusicaPlaylist = criarBotaoPersonalizado("", iconeRemover);

	Vector<String> opcoesOrdenacao = new Vector<>();
	JComboBox<String> comboOrdena = new JComboBox<>(opcoesOrdenacao);

	JTable tabelaMusicasPlaylist;

	EmptyBorder emptyBorder = new EmptyBorder(0, 10, 0, 0);

	MusicaController mc = new MusicaController();
	PlaylistController pc = new PlaylistController();

	private int codUsuario;
	private int codigoPlaylist;
	private String nomePlaylist;
	private PrincipalGui principal;
	protected int codMusicaSelecionado;
	private String ordenacao;

	public EditPlaylistGui(String nomePlaylistSelecionado, int codPlaylistSelecionado, int codigoUsuario,
			PrincipalGui principalGui) {

		this.codUsuario = codigoUsuario;
		this.principal = principalGui;
		this.codigoPlaylist = codPlaylistSelecionado;
		this.nomePlaylist = nomePlaylistSelecionado;
		this.ordenacao = pc.obterOrdemDaPlaylist(codigoPlaylist);
		
		opcoesOrdenacao.add("Nenhuma");
		opcoesOrdenacao.add("Título (A-Z)");
		opcoesOrdenacao.add("Título (Z-A)");
		opcoesOrdenacao.add("Artista");
		opcoesOrdenacao.add("Aleatório");

		JComboBox<String> comboOrdena = new JComboBox<>(opcoesOrdenacao);	
		
		if ("RAND()".equals(ordenacao)) {
			comboOrdena.setSelectedItem("Aleatório");
		} else if ("TITULO".equals(ordenacao)) {
			comboOrdena.setSelectedItem("Título (A-Z)");
		} else if ("TITULO DESC".equals(ordenacao)) {
			comboOrdena.setSelectedItem("Título (Z-A)");
		} else if ("ARTISTA".equals(ordenacao)) {
			comboOrdena.setSelectedItem("Artista");
		} else if (ordenacao == null) {
			comboOrdena.setSelectedItem("Nenhuma");
		} 	
	
		boolean existemMusicasPlaylist = mc.existemMusicasNaPlaylist(codigoPlaylist);

		// O layout muda com o fato de existirem músicas ou não na playlist
		if (existemMusicasPlaylist) {
			frame.setSize(580, 560);
			botaoSalvar.setBounds(165, 460, 115, 25);
			botaoCancelar.setBounds(285, 460, 115, 25);
			botaoRemoveMusicaPlaylist.setVisible(true);
			labelOrdena.setVisible(true);
			comboOrdena.setVisible(true);
			labelMusicas.setText("Músicas");
			campoNome.setBounds(40, 95, 160, 25);
			labelNome.setBounds(43, 75, 75, 25);
			labelNome.setFont(new Font(null, Font.BOLD, 12));
			labelMusicas.setBounds(43, 128, 200, 25);
			labelEdicao.setBounds(120, 26, 500, 25);
			labelEdicao.setFont(new Font("Arial", Font.BOLD, 24));
		} else {
			frame.setSize(400, 265);
			botaoSalvar.setBounds(75, 165, 115, 25);
			botaoCancelar.setBounds(195, 165, 115, 25);
			botaoRemoveMusicaPlaylist.setVisible(false);
			labelOrdena.setVisible(false);
			comboOrdena.setVisible(false);
			labelMusicas.setText("Esta Playlist está vazia!");
			campoNome.setBounds(69, 90, 245, 25);
			labelNome.setBounds(72, 67, 75, 25);
			labelNome.setFont(new Font(null, Font.BOLD, 13));
			labelMusicas.setBounds(122, 123, 200, 25);
			labelEdicao.setBounds(50, 25, 500, 25);
			labelEdicao.setFont(new Font("Arial", Font.BOLD, 20));
		}

		// Propriedades da janela principal
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Edição de Playlist");

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

		labelEdicao.setForeground(Color.WHITE);

		labelMusicas.setForeground(Color.LIGHT_GRAY);
		labelMusicas.setFont(new Font(null, Font.BOLD, 12));

		labelNome.setForeground(Color.LIGHT_GRAY);

		labelOrdena.setBounds(223, 75, 75, 25);
		labelOrdena.setForeground(Color.LIGHT_GRAY);
		labelOrdena.setFont(new Font(null, Font.BOLD, 12));

		campoNome.setBorder(null);
		campoNome.setBackground(new Color(60, 60, 60));
		campoNome.setForeground(Color.WHITE);
		campoNome.setFont(new Font("Arial", Font.BOLD, 12));
		campoNome.setBorder(emptyBorder);
		campoNome.setText(nomePlaylist);

		comboOrdena.setBounds(220, 95, 160, 25);
		comboOrdena.setBorder(null);
		comboOrdena.setBackground(new Color(1, 1, 1));
		comboOrdena.setForeground(Color.WHITE);
		comboOrdena.setFont(new Font("Arial", Font.BOLD, 12));
		comboOrdena.setBorder(null);
		comboOrdena.setFocusable(false);
		comboOrdena.setRenderer(new CustomComboBoxRenderer());

		// Ouvinte de eventos para o JComboBox
		comboOrdena.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selecao = (String) comboOrdena.getSelectedItem();

				if ("Aleatório".equals(selecao)) {
					ordenacao = "RAND()";
					ordenarTabelaPrincipal();
				} else if ("Título (A-Z)".equals(selecao)) {
					ordenacao = "TITULO";
					ordenarTabelaPrincipal();
				} else if ("Título (Z-A)".equals(selecao)) {
					ordenacao = "TITULO DESC";
					ordenarTabelaPrincipal();
				} else if ("Artista".equals(selecao)) {
					ordenacao = "ARTISTA";
					ordenarTabelaPrincipal();
				} else if ("Nenhuma".equals(selecao)) {
					ordenacao = null;
					ordenarTabelaPrincipal();
				} 
			}
		});

		botaoRemoveMusicaPlaylist.setBounds(490, 120, 22, 22);
		botaoRemoveMusicaPlaylist.setToolTipText("Remover da Playlist");

		botaoSalvar.setBackground(new Color(255, 130, 50));
		botaoSalvar.setForeground(Color.WHITE);
		botaoSalvar.setFocusable(false);
		botaoSalvar.addActionListener(this);
		botaoSalvar.setFont(new Font("Arial", Font.BOLD, 12));
		botaoSalvar.setFocusPainted(false);

		botaoCancelar.setBackground(new Color(25, 25, 25));
		botaoCancelar.setForeground(Color.WHITE);
		botaoCancelar.setFocusable(false);
		botaoCancelar.addActionListener(this);
		botaoCancelar.setFont(new Font("Arial", Font.BOLD, 12));
		botaoCancelar.setFocusPainted(false);

		// Tabela de Músicas da Playlist
		if (ordenacao != null) {
			tabelaMusicasPlaylist = new JTable(
					new MusicasTableModel(MusicaController.retornaMusicasPlaylistOrdenadas(codigoPlaylist, codUsuario, ordenacao)));
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
		tabelaMusicasPlaylist.setSelectionBackground(new Color(65, 65, 65));
		tabelaMusicasPlaylist.setSelectionForeground(new Color(255, 130, 50));

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
		botaoSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoSalvar.setBackground(new Color(250, 140, 72));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoSalvar.setBackground(new Color(255, 130, 50));
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

		frame.add(labelEdicao);
		frame.add(labelNome);
		frame.add(labelOrdena);
		frame.add(labelMusicas);
		frame.add(campoNome);
		frame.add(comboOrdena);
		frame.add(botaoRemoveMusicaPlaylist);
		frame.add(botaoSalvar);
		frame.add(botaoCancelar);
		frame.add(scroll);

	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão Remover Música da Playlist
		if (e.getSource() == botaoRemoveMusicaPlaylist) {
			if (codMusicaSelecionado != 0) {
				int resposta = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja remover\n esta música da playlist?", "Confirmação",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					mc.removeMusicaDaPlaylist(codMusicaSelecionado, codigoPlaylist);
					atualizarTabelaPrincipal();
					atualizarCamposPlaylist(codigoPlaylist);
				}
			}
		}

		// Botão Salvar
		if (e.getSource() == botaoSalvar) {
			PlaylistController pc = new PlaylistController();
			pc.editaPlaylist(codigoPlaylist, campoNome.getText(), ordenacao);
			principal.atualizarTabelaPlaylist();
			principal.codPlaylistSelecionado = 0;
			principal.nomePlaylistSelecionado = null;
			frame.dispose();
		}

		// Botão Cancelar
		if (e.getSource() == botaoCancelar) {
			frame.dispose();
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

	// Classe que customiza o combobox
	@SuppressWarnings("serial")
	static class CustomComboBoxRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			label.setBackground(Color.GRAY);
			label.setForeground(Color.white);

			return label;
		}
	}

	// Método que cria botões com ícones
	public JButton criarBotaoPersonalizado(String texto, ImageIcon icone) {
		JButton botao = new JButton(texto, icone);

		botao.setFocusable(false);
		botao.setBorder(emptyBorder);
		botao.setOpaque(false);
		botao.setContentAreaFilled(false);
		botao.setBorderPainted(false);
		botao.setFocusPainted(false);
		botao.addActionListener(this);
		botao.setBackground(null);
		botao.setForeground(Color.white);
		botao.setFont(new Font("Calibri", Font.BOLD, 16));
		botao.setVerticalTextPosition(SwingConstants.BOTTOM);

		botao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botao.setBackground(null);
				botao.setContentAreaFilled(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botao.setBackground(null);
				botao.setContentAreaFilled(false);
			}
		});

		botao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIManager.put("Button.select", new Color(93, 93, 93));
			}
		});
		return botao;
	}

	// Método que atualiza a tabela de Músicas da Playlist
	public void atualizarTabelaPrincipal() {
		MusicasTableModel novoModelo = new MusicasTableModel(
				MusicaController.retornaMusicasPlaylist(codigoPlaylist, codUsuario));
		tabelaMusicasPlaylist.setModel(novoModelo);
		principal.aplicarRenderizadorATabela(tabelaMusicasPlaylist);
	}

	// Método que atualiza ordena tabela de Músicas da Playlist
	public void ordenarTabelaPrincipal() {
		MusicasTableModel novoModelo = new MusicasTableModel(
				MusicaController.retornaMusicasPlaylistOrdenadas(codigoPlaylist, codUsuario, ordenacao));
		tabelaMusicasPlaylist.setModel(novoModelo);
		principal.aplicarRenderizadorATabela(tabelaMusicasPlaylist);
	}

	// Atualiza os campos de quantidade de músicas e de duração total da playlist
	public void atualizarCamposPlaylist(int codigoPlaylist) {
		PlaylistController pc = new PlaylistController();
		int qtdMusicas = pc.obterQuantidadeMusicas(codigoPlaylist);
		String duracaoTotal = pc.obterDuracaoTotal(codigoPlaylist);
		pc.atualizarCamposPlaylist(codigoPlaylist, qtdMusicas, duracaoTotal);
		principal.atualizarTabelaPlaylist();
	}

}
