package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import config.UsuarioConfig;
import control.MusicaController;
import control.PlaylistController;
import javazoom.jl.decoder.JavaLayerException;
import model.Musica;
import model.Playlist;
import model.Usuario;
import player.MusicTimer;
import player.PlayerController;
import view.tablemodel.MusicasTableModel;
import view.tablemodel.PlaylistsTableModel;

public class PrincipalGui implements ActionListener {

	JFrame frame = new JFrame();

	ImageIcon iconeInicio = new ImageIcon("auxiliar/icones/icone_inicio.png");
	ImageIcon iconeBiblioteca = new ImageIcon("auxiliar/icones/icone_biblioteca.png");
	ImageIcon iconeFila = new ImageIcon("auxiliar/icones/icone_fila.png");
	ImageIcon iconePlaylist = new ImageIcon("auxiliar/icones/icone_playlist.png");
	ImageIcon iconeConfig = new ImageIcon("auxiliar/icones/icone_config.png");
	ImageIcon iconePlay = new ImageIcon("auxiliar/icones/icone_play.png");
	ImageIcon iconePause = new ImageIcon("auxiliar/icones/icone_pause.png");
	ImageIcon iconeAvancar = new ImageIcon("auxiliar/icones/icone_avancar.png");
	ImageIcon iconeVoltar = new ImageIcon("auxiliar/icones/icone_voltar.png");
	ImageIcon iconeRemover = new ImageIcon("auxiliar/icones/icone_remover.png");
	ImageIcon iconeEditar = new ImageIcon("auxiliar/icones/icone_editar.png");
	ImageIcon iconeRemoverPlaylist = new ImageIcon("auxiliar/icones/icone_remover.png");
	ImageIcon iconeEditarPlaylist = new ImageIcon("auxiliar/icones/icone_editar.png");
	ImageIcon iconeAddMusicaPlaylist = new ImageIcon("auxiliar/icones/icone_addPlaylist.png");
	ImageIcon iconeAddMusicaFila = new ImageIcon("auxiliar/icones/icone_addFila.png");
	ImageIcon iconeVerMusicas = new ImageIcon("auxiliar/icones/icone_ver.png");
	ImageIcon iconeRemoverFila = new ImageIcon("auxiliar/icones/icone_remover.png");
	ImageIcon iconeLimpaFila = new ImageIcon("auxiliar/icones/icone_limpaFila.png");

	Border emptyBorder = new MatteBorder(0, 0, 0, 0, Color.BLACK);
	Border bordaDireita = new MatteBorder(0, 0, 0, 1, Color.BLACK);
	Border bordaSuperior = new MatteBorder(1, 0, 0, 0, Color.BLACK);
	Border bordaBotaoClicado = new MatteBorder(0, 3, 0, 0, (new Color(255, 130, 50)));
	Border bordaScroll = new MatteBorder(null);
	MatteBorder bottomBorder = new MatteBorder(0, 0, 1, 0, new Color(60, 60, 60));

	Color corLateral = new Color(1);
	Color corInferior = new Color(1);
	Color corCentral = new Color(39, 39, 39);

	JLabel labelBemVindo = new JLabel();
	JLabel labelInicio = new JLabel();
	JLabel labelBiblioteca = new JLabel();
	JLabel labelPlaylist = new JLabel();
	JLabel labelFila = new JLabel();
	JLabel labelConfig = new JLabel();
	JLabel labelArtistaTitulo = new JLabel();
	JLabel labelAlbum = new JLabel();
	JLabel labelDuracao = new JLabel();
	JLabel labelSeparadora2 = new JLabel("  -  ");
	JLabel labelBibliotecaVazia = new JLabel("       Sua biblioteca está vazia!");
	JLabel labelPlaylistVazia = new JLabel("       Você não possui nenhuma playlist!");
	JLabel labelFilaVazia = new JLabel("       A fila está vazia!");
	JLabel labelMusicasRecentes = new JLabel("Suas músicas e playlists recentes aparecerão aqui!");
	JLabel labelPlaylistsOuvidas = new JLabel("      Playlists mais ouvidas");
	JLabel labelSobre = new JLabel("Sobre");
	JLabel labelDoc = new JLabel("Documentação");
	JLabel labelLogoff = new JLabel("Sair do Sistema");

	JButton botaoFolder = new JButton("Adicionar Músicas");
	JButton botaoAddPlaylist = new JButton("Criar Nova Playlist");
	JButton botaoInicio = criarBotaoPersonalizado("", iconeInicio);
	JButton botaoBiblioteca = criarBotaoPersonalizado("", iconeBiblioteca);
	JButton botaoFila = criarBotaoPersonalizado("", iconeFila);
	JButton botaoPlaylist = criarBotaoPersonalizado("", iconePlaylist);
	JButton botaoConfig = criarBotaoPersonalizado("", iconeConfig);
	JButton botaoPlay = criarBotaoPersonalizado("", iconePlay);
	JButton botaoAvancar = criarBotaoPersonalizado("", iconeAvancar);
	JButton botaoVoltar = criarBotaoPersonalizado("", iconeVoltar);
	JButton botaoRemoveMusica = criarBotaoPersonalizado("", iconeRemover);
	JButton botaoEditaMusica = criarBotaoPersonalizado("", iconeEditar);
	JButton botaoAddMusicaPlaylist = criarBotaoPersonalizado("", iconeAddMusicaFila);
	JButton botaoAddMusicaFila = criarBotaoPersonalizado("", iconeAddMusicaPlaylist);
	JButton ultimoBotaoClicado;
	JButton botaoRemovePlaylist = criarBotaoPersonalizado("", iconeRemoverPlaylist);
	JButton botaoEditaPlaylist = criarBotaoPersonalizado("", iconeEditarPlaylist);
	JButton botaoVerMusicas = criarBotaoPersonalizado("", iconeVerMusicas);
	JButton botaoRemoveFila = criarBotaoPersonalizado("", iconeRemoverFila);
	JButton botaoLimpaFila = criarBotaoPersonalizado("", iconeLimpaFila);
	JButton botaoSair = new JButton("Sair");
	JButton botaoDoc = new JButton("Abrir PDF");

	JTextField campoBuscaMusica = new JTextField();

	JPanel panelLateral = new JPanel();
	JPanel panelReproducao = new JPanel();
	JPanel panelInicio = new JPanel();
	JPanel panelBiblioteca = new JPanel();
	JPanel panelPlaylist = new JPanel();
	JPanel panelFila = new JPanel();
	JPanel panelConfig = new JPanel();
	JPanel cards;
	protected JPanel westSouthNorthPanelInicio = new JPanel();

	JProgressBar barraProgresso = new JProgressBar();
	MusicTimer timerLabel = new MusicTimer(barraProgresso, this);

	JTable tabelaMusicas;
	JTable tabelaPlaylists;
	JTable tabelaMusicasFila;
	JTable tabelaMusicasRecentes;
	JTable tabelaPlaylistsOuvidas;

	protected List<Musica> musicasFila = new ArrayList<>();
	protected LinkedList<Musica> listaRecentes = new LinkedList<>();
	protected List<Playlist> playlistsMaisOuvidas = new ArrayList<>();

	private int maxSize = 6;

	private PlayerController playerController;
	private Usuario usuario;
	private CardLayout cardLayout;

	boolean existemMusicas;
	boolean existemPlaylists;

	boolean isPlaying = false;
	boolean pausado = false;
	String currentEndereco;

	protected int currentIndex = -1;
	protected int codMusicaSelecionado;
	protected String tituloSelecionado;
	protected String artistaSelecionado;
	protected String albumSelecionado;
	protected String duracaoSelecionada;
	protected String enderecoSelecionado;
	protected int codPlaylistSelecionado;
	protected String nomePlaylistSelecionado;
	protected int codMusicaFilaSelecionado;
	protected int selectedRowFila;
	protected Musica musicaAnterior;

	public PrincipalGui(Usuario usuario) {

		// Usuário logado
		this.usuario = usuario;

		// Define um look and feel para o projeto
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		updateButtonUI();

		PlaylistController pc = new PlaylistController();
		pc.zerarReproducoesDeTodasPlaylists();
		labelPlaylistsOuvidas.setVisible(false);

		MusicaController mc = new MusicaController();
		existemMusicas = mc.existemMusicasParaUsuario(usuario.getCodigo());
		if (existemMusicas) {
			botaoRemoveMusica.setVisible(true);
			botaoEditaMusica.setVisible(true);
			campoBuscaMusica.setVisible(true);
			botaoAddMusicaPlaylist.setVisible(true);
			botaoAddMusicaFila.setVisible(true);
			labelBibliotecaVazia.setVisible(false);
			botaoFolder.setBorder(null);
		} else {
			botaoRemoveMusica.setVisible(false);
			botaoEditaMusica.setVisible(false);
			campoBuscaMusica.setVisible(false);
			botaoAddMusicaPlaylist.setVisible(false);
			botaoAddMusicaFila.setVisible(false);
			labelBibliotecaVazia.setVisible(true);
			botaoFolder.setBorder(null);
		}

		existemPlaylists = pc.existemPlaylistsParaUsuario(usuario.getCodigo());
		if (existemPlaylists) {
			botaoRemovePlaylist.setVisible(true);
			botaoEditaPlaylist.setVisible(true);
			botaoVerMusicas.setVisible(true);
			labelPlaylistVazia.setVisible(false);
		} else {
			botaoRemovePlaylist.setVisible(false);
			botaoEditaPlaylist.setVisible(false);
			botaoVerMusicas.setVisible(false);
			labelPlaylistVazia.setVisible(true);
		}

		botaoRemoveFila.setVisible(false);
		botaoLimpaFila.setVisible(false);
		labelFilaVazia.setVisible(true);

		// Componentes
		labelBemVindo.setFont(new Font("Calibri", Font.BOLD, 17));
		labelBemVindo.setForeground(Color.LIGHT_GRAY);
		if (usuario.getNome().endsWith("a")) {
			labelBemVindo.setText("       Bem-vinda " + usuario.getNome() + "!");
		} else {
			labelBemVindo.setText("       Bem-vindo " + usuario.getNome() + "!");
		}

		labelInicio.setFont(new Font("Calibri", Font.BOLD, 40));
		labelInicio.setForeground(Color.WHITE);
		labelInicio.setText("   Início");
		labelBiblioteca.setFont(new Font("Calibri", Font.BOLD, 40));
		labelBiblioteca.setForeground(Color.WHITE);
		labelBiblioteca.setText("   Músicas");
		labelPlaylist.setFont(new Font("Calibri", Font.BOLD, 40));
		labelPlaylist.setForeground(Color.WHITE);
		labelPlaylist.setText("   Playlists");
		labelFila.setFont(new Font("Calibri", Font.BOLD, 40));
		labelFila.setForeground(Color.WHITE);
		labelFila.setText("   Fila de Reprodução");
		labelConfig.setFont(new Font("Calibri", Font.BOLD, 40));
		labelConfig.setForeground(Color.WHITE);
		labelConfig.setText("   Configurações");

		labelArtistaTitulo.setForeground(Color.white);
		labelArtistaTitulo.setFont(new Font("Arial", Font.BOLD, 13));

		labelAlbum.setForeground(Color.LIGHT_GRAY);
		labelAlbum.setFont(new Font("Arial", Font.BOLD, 12));

		labelDuracao.setForeground(Color.LIGHT_GRAY);
		labelDuracao.setFont(new Font("Arial", Font.BOLD, 12));

		labelBibliotecaVazia.setFont(new Font("Calibri", Font.BOLD, 17));
		labelBibliotecaVazia.setForeground(Color.LIGHT_GRAY);

		labelPlaylistVazia.setFont(new Font("Calibri", Font.BOLD, 17));
		labelPlaylistVazia.setForeground(Color.LIGHT_GRAY);

		labelFilaVazia.setFont(new Font("Calibri", Font.BOLD, 17));
		labelFilaVazia.setForeground(Color.LIGHT_GRAY);

		labelMusicasRecentes.setFont(new Font("Calibri", Font.BOLD, 21));
		labelMusicasRecentes.setForeground(Color.LIGHT_GRAY);

		labelPlaylistsOuvidas.setFont(new Font("Calibri", Font.BOLD, 21));
		labelPlaylistsOuvidas.setForeground(Color.LIGHT_GRAY);

		labelSobre.setFont(new Font("Calibri", Font.BOLD, 21));
		labelSobre.setForeground(Color.LIGHT_GRAY);
		labelDoc.setFont(new Font("Calibri", Font.BOLD, 17));
		labelDoc.setForeground(Color.LIGHT_GRAY);
		labelLogoff.setFont(new Font("Calibri", Font.BOLD, 17));
		labelLogoff.setForeground(Color.LIGHT_GRAY);

		timerLabel.setVisible(false);

		botaoInicio.setBorderPainted(true);
		botaoInicio.setBorder(bordaBotaoClicado);

		botaoInicio.setToolTipText("Início");
		botaoBiblioteca.setToolTipText("Biblioteca");
		botaoPlaylist.setToolTipText("Playlists");
		botaoFila.setToolTipText("Fila de Reprodução");
		botaoConfig.setToolTipText("Configurações");
		botaoRemoveMusica.setToolTipText("Excluir");
		botaoEditaMusica.setToolTipText("Editar");
		botaoAddMusicaPlaylist.setToolTipText("Adicionar à Playlist");
		botaoAddMusicaFila.setToolTipText("Adicionar à Fila de Reprodução");
		botaoRemovePlaylist.setToolTipText("Excluir");
		botaoEditaPlaylist.setToolTipText("Editar");
		botaoRemoveFila.setToolTipText("Remover da Fila");
		botaoLimpaFila.setToolTipText("Limpar Fila");
		botaoVerMusicas.setToolTipText("Gerenciar reprodução");

		barraProgresso.setPreferredSize(new Dimension(600, 3));
		barraProgresso.setBackground(Color.WHITE);
		barraProgresso.setForeground(new Color(255, 130, 50));
		barraProgresso.setString("");
		barraProgresso.setIndeterminate(false);
		barraProgresso.setBorderPainted(false);
		barraProgresso.setBorder(new EmptyBorder(0, 0, 0, 0));

		campoBuscaMusica.setBorder(bottomBorder);
		campoBuscaMusica.setBackground(null);
		campoBuscaMusica.setForeground(Color.GRAY);
		campoBuscaMusica.setFont(new Font("Arial", Font.PLAIN, 13));
		campoBuscaMusica.setPreferredSize(new Dimension(110, 20));
		campoBuscaMusica.setText("Pesquisar");

		campoBuscaMusica.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				atualizarPesquisa();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				atualizarPesquisa();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				atualizarPesquisa();
			}
		});

		campoBuscaMusica.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (campoBuscaMusica.getText().equals("Pesquisar")) {
					campoBuscaMusica.setText("");
					campoBuscaMusica.setForeground(Color.white);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (campoBuscaMusica.getText().isEmpty()) {
					campoBuscaMusica.setText("Pesquisar");
					campoBuscaMusica.setForeground(Color.GRAY);
				}
			}
		});

		botaoFolder.setPreferredSize(new Dimension(130, 25));
		botaoFolder.setFocusable(false);
		botaoFolder.addActionListener(this);
		botaoFolder.setBackground(new Color(5, 5, 5));
		botaoFolder.setForeground(Color.WHITE);
		botaoFolder.setFont(new Font("Arial", Font.BOLD, 12));
		botaoFolder.setFocusPainted(false);

		botaoFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoFolder.setBackground(new Color(20, 20, 20));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoFolder.setBackground(new Color(5, 5, 5));
			}
		});

		botaoAddPlaylist.setPreferredSize(new Dimension(130, 25));
		botaoAddPlaylist.setBorder(null);
		botaoAddPlaylist.setFocusable(false);
		botaoAddPlaylist.addActionListener(this);
		botaoAddPlaylist.setBackground(new Color(5, 5, 5));
		botaoAddPlaylist.setForeground(Color.WHITE);
		botaoAddPlaylist.setFont(new Font("Arial", Font.BOLD, 12));
		botaoAddPlaylist.setFocusPainted(false);

		botaoSair.setPreferredSize(new Dimension(100, 25));
		botaoSair.setMaximumSize(new Dimension(100, 25));
		botaoSair.setBorder(null);
		botaoSair.setFocusable(false);
		botaoSair.addActionListener(this);
		botaoSair.setBackground(new Color(5, 5, 5));
		botaoSair.setForeground(Color.WHITE);
		botaoSair.setFont(new Font("Arial", Font.BOLD, 12));
		botaoSair.setFocusPainted(false);

		botaoDoc.setPreferredSize(new Dimension(100, 25));
		botaoDoc.setMaximumSize(new Dimension(100, 25));
		botaoDoc.setBorder(null);
		botaoDoc.setFocusable(false);
		botaoDoc.addActionListener(this);
		botaoDoc.setBackground(new Color(5, 5, 5));
		botaoDoc.setForeground(Color.WHITE);
		botaoDoc.setFont(new Font("Arial", Font.BOLD, 12));
		botaoDoc.setFocusPainted(false);

		botaoAddPlaylist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoAddPlaylist.setBackground(new Color(20, 20, 20));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoAddPlaylist.setBackground(new Color(5, 5, 5));
			}
		});

		// Frame principal
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(680, 680);
		frame.setMinimumSize(new Dimension(730, 730));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setTitle("Pulse");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

		// Paineis
		panelLateral.setBackground(new Color(32, 32, 32));
		panelReproducao.setBackground(corCentral);
		panelInicio.setBackground(corCentral);
		panelBiblioteca.setBackground(corCentral);
		panelPlaylist.setBackground(corCentral);
		panelFila.setBackground(corCentral);
		panelConfig.setBackground(corCentral);

		panelLateral.setLayout(new BorderLayout());
		panelReproducao.setLayout(new BorderLayout());
		panelInicio.setLayout(new BorderLayout());
		panelBiblioteca.setLayout(new BorderLayout());
		panelPlaylist.setLayout(new BorderLayout());
		panelFila.setLayout(new BorderLayout());
		panelConfig.setLayout(new BorderLayout());

		cards = new JPanel();
		cardLayout = new CardLayout();
		cards.setLayout(cardLayout);

		cards.add(panelInicio, "Inicio");
		cards.add(panelBiblioteca, "Biblioteca");
		cards.add(panelPlaylist, "Playlist");
		cards.add(panelFila, "Fila");
		cards.add(panelConfig, "Config");

		panelLateral.setBorder(bordaDireita);
		panelReproducao.setBorder(bordaSuperior);

		panelLateral.setPreferredSize(new Dimension(70, 100));
		panelReproducao.setPreferredSize(new Dimension(100, 100));

		frame.add(panelLateral, BorderLayout.WEST);
		frame.add(panelReproducao, BorderLayout.SOUTH);
		frame.add(cards, BorderLayout.CENTER);

// Painel Inicio
		westSouthNorthPanelInicio.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 12));
		westSouthNorthPanelInicio.setBackground(corCentral);
		westSouthNorthPanelInicio.setPreferredSize(new Dimension(488, 40));
		westSouthNorthPanelInicio.add(labelMusicasRecentes);

		JPanel southNorthPanelInicio = new JPanel();
		southNorthPanelInicio.setLayout(new BorderLayout());
		southNorthPanelInicio.setBackground(corCentral);
		southNorthPanelInicio.setPreferredSize(new Dimension(200, 40));
		southNorthPanelInicio.add(westSouthNorthPanelInicio, BorderLayout.WEST);

		JPanel southWestNorthPanelInicio = new JPanel();
		southWestNorthPanelInicio.setLayout(new BorderLayout());
		southWestNorthPanelInicio.setBackground(corCentral);
		southWestNorthPanelInicio.setPreferredSize(new Dimension(1, 30));
		southWestNorthPanelInicio.add(labelBemVindo, BorderLayout.CENTER);

		JPanel northWestNorthPanelInicio = new JPanel();
		northWestNorthPanelInicio.setLayout(new BorderLayout());
		northWestNorthPanelInicio.setBackground(corCentral);
		northWestNorthPanelInicio.setPreferredSize(new Dimension(1, 30));

		JPanel westNorthPanelInicio = new JPanel();
		westNorthPanelInicio.setLayout(new BorderLayout());
		westNorthPanelInicio.setBackground(corCentral);
		westNorthPanelInicio.add(labelInicio, BorderLayout.WEST);
		westNorthPanelInicio.add(southWestNorthPanelInicio, BorderLayout.SOUTH);
		westNorthPanelInicio.add(northWestNorthPanelInicio, BorderLayout.NORTH);

		JPanel northPanelInicio = new JPanel();
		northPanelInicio.setLayout(new BorderLayout());
		northPanelInicio.setBackground(corCentral);
		northPanelInicio.setPreferredSize(new Dimension(1, 140));
		northPanelInicio.add(westNorthPanelInicio, BorderLayout.CENTER);
		northPanelInicio.add(southNorthPanelInicio, BorderLayout.SOUTH);

		panelInicio.add(northPanelInicio, BorderLayout.NORTH);

		JPanel eastPanelInicio = new JPanel();
		eastPanelInicio.setBackground(corCentral);
		eastPanelInicio.setPreferredSize(new Dimension(45, 45));

		JPanel westPanelInicio = new JPanel();
		westPanelInicio.setBackground(corCentral);
		westPanelInicio.setPreferredSize(new Dimension(45, 45));

		JPanel southPanelInicio = new JPanel();
		southPanelInicio.setBackground(corCentral);
		southPanelInicio.setPreferredSize(new Dimension(260, 260));
		southPanelInicio.setLayout(new BorderLayout());

		JPanel northSouthPanelInicio = new JPanel();
		northSouthPanelInicio.setBackground(corCentral);
		northSouthPanelInicio.setPreferredSize(new Dimension(50, 50));
		northSouthPanelInicio.setLayout(new BorderLayout());

		JPanel westNorthSouthPanelInicio = new JPanel();
		westNorthSouthPanelInicio.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 22));
		westNorthSouthPanelInicio.setBackground(corCentral);
		westNorthSouthPanelInicio.setPreferredSize(new Dimension(220, 40));
		westNorthSouthPanelInicio.add(labelPlaylistsOuvidas);

		JPanel eastSouthPanelInicio = new JPanel();
		eastSouthPanelInicio.setBackground(corCentral);
		eastSouthPanelInicio.setPreferredSize(new Dimension(45, 45));

		JPanel westSouthPanelInicio = new JPanel();
		westSouthPanelInicio.setBackground(corCentral);
		westSouthPanelInicio.setPreferredSize(new Dimension(45, 45));

		JPanel southSouthPanelInicio = new JPanel();
		southSouthPanelInicio.setBackground(corCentral);
		southSouthPanelInicio.setPreferredSize(new Dimension(60, 60));

		northSouthPanelInicio.add(westNorthSouthPanelInicio, BorderLayout.WEST);
		southPanelInicio.add(northSouthPanelInicio, BorderLayout.NORTH);
		southPanelInicio.add(eastSouthPanelInicio, BorderLayout.EAST);
		southPanelInicio.add(westSouthPanelInicio, BorderLayout.WEST);
		southPanelInicio.add(southSouthPanelInicio, BorderLayout.SOUTH);

		panelInicio.add(eastPanelInicio, BorderLayout.EAST);
		panelInicio.add(westPanelInicio, BorderLayout.WEST);
		panelInicio.add(southPanelInicio, BorderLayout.SOUTH);

		// Tabela de Músicas Recentes
		tabelaMusicasRecentes = new JTable(new MusicasTableModel(listaRecentes));
		tabelaMusicasRecentes.setBackground(new Color(52, 52, 52));
		tabelaMusicasRecentes.setForeground(Color.WHITE);
		tabelaMusicasRecentes.setBorder(null);
		tabelaMusicasRecentes.setShowGrid(false);
		tabelaMusicasRecentes.setFont(new Font("Arial", Font.BOLD, 12));
		tabelaMusicasRecentes.setRowHeight(30);
		tabelaMusicasRecentes.setTableHeader(null);

		tabelaMusicasRecentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaMusicasRecentes.setSelectionBackground(new Color(52, 52, 52));
		tabelaMusicasRecentes.setSelectionForeground(Color.WHITE);

		aplicarRenderizadorATabela(tabelaMusicasRecentes);

		JScrollPane scrollInicio = new JScrollPane(this.tabelaMusicasRecentes);
		scrollInicio.setBorder(new MatteBorder(null));
		scrollInicio.getViewport().setBackground(corCentral);
		scrollInicio.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollInicio.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollInicio.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		panelInicio.add(scrollInicio, BorderLayout.CENTER);

		// Tabela de Playlists mais ouvidas
		tabelaPlaylistsOuvidas = new JTable(new PlaylistsTableModel(playlistsMaisOuvidas));
		tabelaPlaylistsOuvidas.setBackground(new Color(52, 52, 52));
		tabelaPlaylistsOuvidas.setForeground(Color.WHITE);
		tabelaPlaylistsOuvidas.setBorder(null);
		tabelaPlaylistsOuvidas.setShowGrid(false);
		tabelaPlaylistsOuvidas.setFont(new Font("Arial", Font.BOLD, 16));
		tabelaPlaylistsOuvidas.setRowHeight(40);
		tabelaPlaylistsOuvidas.setTableHeader(null);

		tabelaPlaylistsOuvidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaPlaylistsOuvidas.setSelectionBackground(new Color(52, 52, 52));
		tabelaPlaylistsOuvidas.setSelectionForeground(Color.WHITE);

		aplicarRenderizadorATabelaPlaylists(tabelaPlaylistsOuvidas);

		JScrollPane scrollPlaylistsOuvidas = new JScrollPane(this.tabelaPlaylistsOuvidas);
		scrollPlaylistsOuvidas.setBorder(bordaScroll);
		scrollPlaylistsOuvidas.getViewport().setBackground(corCentral);
		scrollPlaylistsOuvidas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPlaylistsOuvidas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPlaylistsOuvidas.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		southPanelInicio.add(scrollPlaylistsOuvidas, BorderLayout.CENTER);

		// Painel Biblioteca
		JPanel eastNorthPanelBiblioteca = new JPanel();
		eastNorthPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 36));
		eastNorthPanelBiblioteca.setBackground(corCentral);
		eastNorthPanelBiblioteca.setPreferredSize(new Dimension(220, 100));
		eastNorthPanelBiblioteca.add(botaoFolder);

		JLabel espacamento = new JLabel(" ");
		JLabel espacamento2 = new JLabel("  ");
		JLabel espacamento3 = new JLabel("  ");
		JPanel eastSouthNorthPanelBiblioteca = new JPanel();
		eastSouthNorthPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		eastSouthNorthPanelBiblioteca.setBackground(corCentral);
		eastSouthNorthPanelBiblioteca.setPreferredSize(new Dimension(230, 40));
		eastSouthNorthPanelBiblioteca.add(botaoAddMusicaFila);
		eastSouthNorthPanelBiblioteca.add(espacamento2);
		eastSouthNorthPanelBiblioteca.add(botaoAddMusicaPlaylist);
		eastSouthNorthPanelBiblioteca.add(espacamento3);
		eastSouthNorthPanelBiblioteca.add(botaoEditaMusica);
		eastSouthNorthPanelBiblioteca.add(espacamento);
		eastSouthNorthPanelBiblioteca.add(botaoRemoveMusica);

		JPanel westSouthNorthPanelBiblioteca = new JPanel();
		westSouthNorthPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		westSouthNorthPanelBiblioteca.setBackground(corCentral);
		westSouthNorthPanelBiblioteca.setPreferredSize(new Dimension(210, 40));
		westSouthNorthPanelBiblioteca.add(campoBuscaMusica);

		JPanel southNorthPanelBiblioteca = new JPanel();
		southNorthPanelBiblioteca.setLayout(new BorderLayout());
		southNorthPanelBiblioteca.setBackground(corCentral);
		southNorthPanelBiblioteca.setPreferredSize(new Dimension(200, 40));
		southNorthPanelBiblioteca.add(eastSouthNorthPanelBiblioteca, BorderLayout.EAST);
		southNorthPanelBiblioteca.add(westSouthNorthPanelBiblioteca, BorderLayout.WEST);

		JPanel southWestNorthPanelBiblioteca = new JPanel();
		southWestNorthPanelBiblioteca.setLayout(new BorderLayout());
		southWestNorthPanelBiblioteca.setBackground(corCentral);
		southWestNorthPanelBiblioteca.add(labelBibliotecaVazia, BorderLayout.CENTER);
		southWestNorthPanelBiblioteca.setPreferredSize(new Dimension(1, 30));

		JPanel northWestNorthPanelBiblioteca = new JPanel();
		northWestNorthPanelBiblioteca.setLayout(new BorderLayout());
		northWestNorthPanelBiblioteca.setBackground(corCentral);
		northWestNorthPanelBiblioteca.setPreferredSize(new Dimension(1, 30));

		JPanel westNorthPanelBiblioteca = new JPanel();
		westNorthPanelBiblioteca.setLayout(new BorderLayout());
		westNorthPanelBiblioteca.setBackground(corCentral);
		westNorthPanelBiblioteca.add(labelBiblioteca, BorderLayout.WEST);
		westNorthPanelBiblioteca.add(southWestNorthPanelBiblioteca, BorderLayout.SOUTH);
		westNorthPanelBiblioteca.add(northWestNorthPanelBiblioteca, BorderLayout.NORTH);

		JPanel northPanelBiblioteca = new JPanel();
		northPanelBiblioteca.setLayout(new BorderLayout());
		northPanelBiblioteca.setBackground(corCentral);
		northPanelBiblioteca.setPreferredSize(new Dimension(1, 140));
		northPanelBiblioteca.add(westNorthPanelBiblioteca, BorderLayout.CENTER);
		northPanelBiblioteca.add(eastNorthPanelBiblioteca, BorderLayout.EAST);
		northPanelBiblioteca.add(southNorthPanelBiblioteca, BorderLayout.SOUTH);

		panelBiblioteca.add(northPanelBiblioteca, BorderLayout.NORTH);

		// Paineis de espaçamento
		JPanel eastPanelBiblioteca = new JPanel();
		eastPanelBiblioteca.setBackground(corCentral);
		eastPanelBiblioteca.setPreferredSize(new Dimension(45, 45));

		JPanel westPanelBiblioteca = new JPanel();
		westPanelBiblioteca.setBackground(corCentral);
		westPanelBiblioteca.setPreferredSize(new Dimension(45, 45));

		JPanel southPanelBiblioteca = new JPanel();
		southPanelBiblioteca.setBackground(corCentral);
		southPanelBiblioteca.setPreferredSize(new Dimension(60, 60));
		southPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

		panelBiblioteca.add(eastPanelBiblioteca, BorderLayout.EAST);
		panelBiblioteca.add(westPanelBiblioteca, BorderLayout.WEST);
		panelBiblioteca.add(southPanelBiblioteca, BorderLayout.SOUTH);

		// Tabela de Músicas
		tabelaMusicas = new JTable(new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo())));
		tabelaMusicas.setBackground(new Color(52, 52, 52));
		tabelaMusicas.setForeground(Color.WHITE);
		tabelaMusicas.setBorder(null);
		tabelaMusicas.setShowGrid(false);
		tabelaMusicas.setFont(new Font("Arial", Font.BOLD, 12));
		tabelaMusicas.setRowHeight(30);
		tabelaMusicas.setTableHeader(null);

		tabelaMusicas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaMusicas.setSelectionBackground(new Color(65, 65, 65));
		tabelaMusicas.setSelectionForeground(new Color(255, 130, 50));

		// Captura a seleção do usuário
		tabelaMusicas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {
					int selectedRow = tabelaMusicas.getSelectedRow();
					if (selectedRow != -1) {
						tituloSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 0);
						artistaSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 1);
						albumSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 2);
						duracaoSelecionada = (String) tabelaMusicas.getValueAt(selectedRow, 3);
						codMusicaSelecionado = (int) tabelaMusicas.getValueAt(selectedRow, 4);
						enderecoSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 5);
						currentIndex = selectedRow;
					}
				}
			}
		});

		aplicarRenderizadorATabela(tabelaMusicas);

		JScrollPane scroll = new JScrollPane(this.tabelaMusicas);
		scroll.setBorder(bordaScroll);
		scroll.getViewport().setBackground(corCentral);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		panelBiblioteca.add(scroll, BorderLayout.CENTER);

		// Painel Playlist
		JPanel eastNorthPanelPlaylist = new JPanel();
		eastNorthPanelPlaylist.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 36));
		eastNorthPanelPlaylist.setBackground(corCentral);
		eastNorthPanelPlaylist.setPreferredSize(new Dimension(220, 100));
		eastNorthPanelPlaylist.add(botaoAddPlaylist);

		JLabel espacamentoPlaylist = new JLabel(" ");
		JLabel espacamentoPlaylist2 = new JLabel(" ");
		JPanel eastSouthNorthPanelPlaylist = new JPanel();
		eastSouthNorthPanelPlaylist.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		eastSouthNorthPanelPlaylist.setBackground(corCentral);
		eastSouthNorthPanelPlaylist.setPreferredSize(new Dimension(197, 40));
		eastSouthNorthPanelPlaylist.add(botaoVerMusicas);
		eastSouthNorthPanelPlaylist.add(espacamentoPlaylist2);
		eastSouthNorthPanelPlaylist.add(botaoEditaPlaylist);
		eastSouthNorthPanelPlaylist.add(espacamentoPlaylist);
		eastSouthNorthPanelPlaylist.add(botaoRemovePlaylist);

		JPanel westSouthNorthPanelPlaylist = new JPanel();
		westSouthNorthPanelPlaylist.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		westSouthNorthPanelPlaylist.setBackground(corCentral);
		westSouthNorthPanelPlaylist.setPreferredSize(new Dimension(210, 40));

		JPanel southNorthPanelPlaylist = new JPanel();
		southNorthPanelPlaylist.setLayout(new BorderLayout());
		southNorthPanelPlaylist.setBackground(corCentral);
		southNorthPanelPlaylist.setPreferredSize(new Dimension(200, 40));
		southNorthPanelPlaylist.add(eastSouthNorthPanelPlaylist, BorderLayout.EAST);
		southNorthPanelPlaylist.add(westSouthNorthPanelPlaylist, BorderLayout.WEST);

		JPanel southWestNorthPanelPlaylist = new JPanel();
		southWestNorthPanelPlaylist.setLayout(new BorderLayout());
		southWestNorthPanelPlaylist.setBackground(corCentral);
		southWestNorthPanelPlaylist.setPreferredSize(new Dimension(1, 30));
		southWestNorthPanelPlaylist.add(labelPlaylistVazia, BorderLayout.CENTER);

		JPanel northWestNorthPanelPlaylist = new JPanel();
		northWestNorthPanelPlaylist.setLayout(new BorderLayout());
		northWestNorthPanelPlaylist.setBackground(corCentral);
		northWestNorthPanelPlaylist.setPreferredSize(new Dimension(1, 30));

		JPanel westNorthPanelPlaylist = new JPanel();
		westNorthPanelPlaylist.setLayout(new BorderLayout());
		westNorthPanelPlaylist.setBackground(corCentral);
		westNorthPanelPlaylist.add(labelPlaylist, BorderLayout.WEST);
		westNorthPanelPlaylist.add(southWestNorthPanelPlaylist, BorderLayout.SOUTH);
		westNorthPanelPlaylist.add(northWestNorthPanelPlaylist, BorderLayout.NORTH);

		JPanel northPanelPlaylist = new JPanel();
		northPanelPlaylist.setLayout(new BorderLayout());
		northPanelPlaylist.setBackground(corCentral);
		northPanelPlaylist.setPreferredSize(new Dimension(1, 140));
		northPanelPlaylist.add(westNorthPanelPlaylist, BorderLayout.CENTER);
		northPanelPlaylist.add(eastNorthPanelPlaylist, BorderLayout.EAST);
		northPanelPlaylist.add(southNorthPanelPlaylist, BorderLayout.SOUTH);

		panelPlaylist.add(northPanelPlaylist, BorderLayout.NORTH);

		// Paineis de espaçamento
		JPanel eastPanelPlaylist = new JPanel();
		eastPanelPlaylist.setBackground(corCentral);
		eastPanelPlaylist.setPreferredSize(new Dimension(45, 45));

		JPanel westPanelPlaylist = new JPanel();
		westPanelPlaylist.setBackground(corCentral);
		westPanelPlaylist.setPreferredSize(new Dimension(45, 45));

		JPanel southPanelPlaylist = new JPanel();
		southPanelPlaylist.setBackground(corCentral);
		southPanelPlaylist.setPreferredSize(new Dimension(60, 60));
		southPanelPlaylist.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

		panelPlaylist.add(eastPanelPlaylist, BorderLayout.EAST);
		panelPlaylist.add(westPanelPlaylist, BorderLayout.WEST);
		panelPlaylist.add(southPanelPlaylist, BorderLayout.SOUTH);

		// Tabela de Playlists
		tabelaPlaylists = new JTable(new PlaylistsTableModel(PlaylistController.retornaMusicas(usuario.getCodigo())));
		tabelaPlaylists.setBackground(new Color(52, 52, 52));
		tabelaPlaylists.setForeground(Color.WHITE);
		tabelaPlaylists.setBorder(null);
		tabelaPlaylists.setShowGrid(false);
		tabelaPlaylists.setFont(new Font("Arial", Font.BOLD, 16));
		tabelaPlaylists.setRowHeight(40);
		tabelaPlaylists.setTableHeader(null);

		tabelaPlaylists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaPlaylists.setSelectionBackground(new Color(65, 65, 65));
		tabelaPlaylists.setSelectionForeground(new Color(255, 130, 50));

		// Captura a seleção do usuário
		tabelaPlaylists.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {
					int selectedRow = tabelaPlaylists.getSelectedRow();
					if (selectedRow != -1) {
						codPlaylistSelecionado = (int) tabelaPlaylists.getValueAt(selectedRow, 3);
						nomePlaylistSelecionado = (String) tabelaPlaylists.getValueAt(selectedRow, 0);
					}
				}
			}
		});
		
		// Adicione um ouvinte de mouse (duplo clique) à tabela de playlists
		tabelaPlaylists.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Verifica se foi um duplo clique
                    int linhaSelecionada = tabelaPlaylists.getSelectedRow();
                    if (linhaSelecionada != -1) {          
                    	botaoVerMusicas.doClick();
                    }
                }
            }
        });

		aplicarRenderizadorATabelaPlaylists(tabelaPlaylists);

		JScrollPane scrollPlaylist = new JScrollPane(this.tabelaPlaylists);
		scrollPlaylist.setBorder(bordaScroll);
		scrollPlaylist.getViewport().setBackground(corCentral);
		scrollPlaylist.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPlaylist.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPlaylist.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		panelPlaylist.add(scrollPlaylist, BorderLayout.CENTER);

		// Painel Fila
		JLabel espacamentoFila = new JLabel(" ");
		JPanel eastSouthNorthPanelFila = new JPanel();
		eastSouthNorthPanelFila.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		eastSouthNorthPanelFila.setBackground(corCentral);
		eastSouthNorthPanelFila.setPreferredSize(new Dimension(170, 40));
		eastSouthNorthPanelFila.add(botaoLimpaFila);
		eastSouthNorthPanelFila.add(espacamentoFila);
		eastSouthNorthPanelFila.add(botaoRemoveFila);

		JPanel westSouthNorthPanelFila = new JPanel();
		westSouthNorthPanelFila.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		westSouthNorthPanelFila.setBackground(corCentral);
		westSouthNorthPanelFila.setPreferredSize(new Dimension(210, 40));

		JPanel southNorthPanelFila = new JPanel();
		southNorthPanelFila.setLayout(new BorderLayout());
		southNorthPanelFila.setBackground(corCentral);
		southNorthPanelFila.setPreferredSize(new Dimension(200, 40));
		southNorthPanelFila.add(eastSouthNorthPanelFila, BorderLayout.EAST);
		southNorthPanelFila.add(westSouthNorthPanelFila, BorderLayout.WEST);

		JPanel southWestNorthPanelFila = new JPanel();
		southWestNorthPanelFila.setLayout(new BorderLayout());
		southWestNorthPanelFila.setBackground(corCentral);
		southWestNorthPanelFila.setPreferredSize(new Dimension(1, 30));
		southWestNorthPanelFila.add(labelFilaVazia, BorderLayout.CENTER);

		JPanel northWestNorthPanelFila = new JPanel();
		northWestNorthPanelFila.setLayout(new BorderLayout());
		northWestNorthPanelFila.setBackground(corCentral);
		northWestNorthPanelFila.setPreferredSize(new Dimension(1, 30));

		JPanel westNorthPanelFila = new JPanel();
		westNorthPanelFila.setLayout(new BorderLayout());
		westNorthPanelFila.setBackground(corCentral);
		westNorthPanelFila.add(labelFila, BorderLayout.WEST);
		westNorthPanelFila.add(southWestNorthPanelFila, BorderLayout.SOUTH);
		westNorthPanelFila.add(northWestNorthPanelFila, BorderLayout.NORTH);

		JPanel northPanelFila = new JPanel();
		northPanelFila.setLayout(new BorderLayout());
		northPanelFila.setBackground(corCentral);
		northPanelFila.setPreferredSize(new Dimension(1, 140));
		northPanelFila.add(westNorthPanelFila, BorderLayout.CENTER);
		northPanelFila.add(southNorthPanelFila, BorderLayout.SOUTH);

		panelFila.add(northPanelFila, BorderLayout.NORTH);

		// Paineis de espaçamento
		JPanel eastPanelFila = new JPanel();
		eastPanelFila.setBackground(corCentral);
		eastPanelFila.setPreferredSize(new Dimension(45, 45));

		JPanel westPanelFila = new JPanel();
		westPanelFila.setBackground(corCentral);
		westPanelFila.setPreferredSize(new Dimension(45, 45));

		JPanel southPanelFila = new JPanel();
		southPanelFila.setBackground(corCentral);
		southPanelFila.setPreferredSize(new Dimension(60, 60));
		southPanelFila.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

		panelFila.add(eastPanelFila, BorderLayout.EAST);
		panelFila.add(westPanelFila, BorderLayout.WEST);
		panelFila.add(southPanelFila, BorderLayout.SOUTH);

		// Tabela de Músicas da Fila
		tabelaMusicasFila = new JTable(new MusicasTableModel(musicasFila));
		tabelaMusicasFila.setBackground(new Color(52, 52, 52));
		tabelaMusicasFila.setForeground(Color.WHITE);
		tabelaMusicasFila.setBorder(null);
		tabelaMusicasFila.setShowGrid(false);
		tabelaMusicasFila.setFont(new Font("Arial", Font.BOLD, 12));
		tabelaMusicasFila.setRowHeight(30);
		tabelaMusicasFila.setTableHeader(null);

		tabelaMusicasFila.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaMusicasFila.setSelectionBackground(new Color(65, 65, 65));
		tabelaMusicasFila.setSelectionForeground(new Color(255, 130, 50));

		// Captura a seleção do usuário
		tabelaMusicasFila.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {
					selectedRowFila = tabelaMusicasFila.getSelectedRow();
					if (selectedRowFila != -1) {
						codMusicaFilaSelecionado = (int) tabelaMusicasFila.getValueAt(selectedRowFila, 4);
					}
				}
			}
		});

		aplicarRenderizadorATabela(tabelaMusicasFila);

		JScrollPane scrollFila = new JScrollPane(this.tabelaMusicasFila);
		scrollFila.setBorder(new MatteBorder(null));
		scrollFila.getViewport().setBackground(corCentral);
		scrollFila.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollFila.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollFila.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		panelFila.add(scrollFila, BorderLayout.CENTER);

		// Painel Config
		JPanel northPanelConfig = new JPanel();
		northPanelConfig.setLayout(new BorderLayout());
		northPanelConfig.setBackground(corCentral);
		northPanelConfig.setPreferredSize(new Dimension(1, 100));
		northPanelConfig.add(labelConfig, BorderLayout.WEST);

		JPanel westPanelConfig = new JPanel();
		westPanelConfig.setLayout(new BorderLayout());
		westPanelConfig.setBackground(corCentral);
		westPanelConfig.setPreferredSize(new Dimension(30, 1));

		JPanel eastPanelConfig = new JPanel();
		eastPanelConfig.setLayout(new BorderLayout());
		eastPanelConfig.setBackground(corCentral);
		eastPanelConfig.setPreferredSize(new Dimension(85, 1));

		String texto = "<html>O Pulse é um reprodutor musical desenvolvido por Pedro Gollo, Bárbara Schneider e Franco Michelon durante a disciplina de Projeto Temático 1, sob a orientação do Professor Krohn, no âmbito do curso de Análise e Desenvolvimento de Sistemas da Universidade de Caxias do Sul. Este aplicativo foi concebido com o objetivo de aplicar os conhecimentos adquiridos ao longo do curso, com ênfase em programação orientada a objetos (POO), fundamentos de banco de dados e princípios fundamentais de engenharia de software. O Pulse oferece aos usuários uma experiência musical completa e prática, sendo uma demonstração da capacidade de desenvolver aplicações funcionais e eficazes usando a linguagem Java e a interface gráfica do Swing.</html>";
		JLabel labelTexto = new JLabel(texto);
		labelTexto.setFont(new Font("Arial", Font.BOLD, 12));
		labelTexto.setForeground(Color.GRAY);

		JPanel centerPanelConfig = new JPanel();
		centerPanelConfig.setLayout(new BoxLayout(centerPanelConfig, BoxLayout.Y_AXIS));
		centerPanelConfig.setBackground(corCentral);
		centerPanelConfig.add(labelSobre);
		centerPanelConfig.add(Box.createVerticalStrut(2)); // Espaçamento
		centerPanelConfig.add(labelTexto);
		centerPanelConfig.add(Box.createVerticalStrut(25)); // Espaçamento
		centerPanelConfig.add(labelDoc);
		centerPanelConfig.add(Box.createVerticalStrut(2)); // Espaçamento
		centerPanelConfig.add(botaoDoc);
		centerPanelConfig.add(Box.createVerticalStrut(25)); // Espaçamento
		centerPanelConfig.add(labelLogoff);
		centerPanelConfig.add(Box.createVerticalStrut(2)); // Espaçamento
		centerPanelConfig.add(botaoSair);

		panelConfig.add(northPanelConfig, BorderLayout.NORTH);
		panelConfig.add(centerPanelConfig, BorderLayout.CENTER);
		panelConfig.add(westPanelConfig, BorderLayout.WEST);
		panelConfig.add(eastPanelConfig, BorderLayout.EAST);

		// Painel Inferior (Reprodutor)
		JPanel southPanelInferior = new JPanel();
		southPanelInferior.setLayout(new BorderLayout());
		southPanelInferior.setBackground(corCentral);

		JPanel centerSouthPanelInferior = new JPanel();
		centerSouthPanelInferior.setLayout(new FlowLayout());
		centerSouthPanelInferior.setBackground(corCentral);

		JLabel espacamentoInferior = new JLabel("                                                                 ");
		centerSouthPanelInferior.add(botaoVoltar);
		centerSouthPanelInferior.add(botaoPlay);
		centerSouthPanelInferior.add(botaoAvancar);
		centerSouthPanelInferior.add(espacamentoInferior);

		JPanel westSouthPanelInferior = new JPanel();
		westSouthPanelInferior.setLayout(new BorderLayout());
		westSouthPanelInferior.setPreferredSize(new Dimension(210, 50));
		westSouthPanelInferior.setBackground(corCentral);

		JPanel northWestSouthPanelInferior = new JPanel();
		northWestSouthPanelInferior.setLayout(new BoxLayout(northWestSouthPanelInferior, BoxLayout.Y_AXIS));
		northWestSouthPanelInferior.setPreferredSize(new Dimension(210, 50));
		northWestSouthPanelInferior.setBackground(corCentral);

		JPanel southWestSouthPanelInferior = new JPanel();
		southWestSouthPanelInferior.setBackground(corCentral);
		southWestSouthPanelInferior.setLayout(new BoxLayout(southWestSouthPanelInferior, BoxLayout.X_AXIS));
		southWestSouthPanelInferior.setPreferredSize(new Dimension(210, 50));

		JLabel labelSeparadora = new JLabel("     ");
		labelSeparadora2.setForeground(Color.LIGHT_GRAY);
		labelSeparadora2.setFont(new Font("Arial", Font.BOLD, 12));
		labelSeparadora2.setVisible(false);

		southWestSouthPanelInferior.add(labelSeparadora);
		southWestSouthPanelInferior.add(timerLabel);
		southWestSouthPanelInferior.add(labelSeparadora2);
		southWestSouthPanelInferior.add(labelDuracao);

		JLabel labelVazia = new JLabel(" ");

		northWestSouthPanelInferior.add(labelVazia);
		northWestSouthPanelInferior.add(labelArtistaTitulo);
		northWestSouthPanelInferior.add(labelAlbum);

		westSouthPanelInferior.add(northWestSouthPanelInferior, BorderLayout.NORTH);
		westSouthPanelInferior.add(southWestSouthPanelInferior, BorderLayout.SOUTH);

		JPanel northPanelInferior = new JPanel();
		northPanelInferior.setLayout(new FlowLayout());
		northPanelInferior.setBackground(corCentral);

		southPanelInferior.add(westSouthPanelInferior, BorderLayout.WEST);
		southPanelInferior.add(centerSouthPanelInferior, BorderLayout.CENTER);
		northPanelInferior.add(barraProgresso);

		panelReproducao.add(southPanelInferior, BorderLayout.CENTER);
		panelReproducao.add(northPanelInferior, BorderLayout.NORTH);

		// Método que expande a barra de reprodução
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int newWidth = frame.getWidth() - 90;
				barraProgresso.setPreferredSize(new Dimension(newWidth, 3));
				barraProgresso.revalidate();
			}
		});

		// Painel Lateral
		JPanel westPanelLateral = new JPanel();
		westPanelLateral.setLayout(new FlowLayout());
		westPanelLateral.setLayout(new BoxLayout(westPanelLateral, BoxLayout.Y_AXIS));
		westPanelLateral.setBackground(new Color(32, 32, 32));

		westPanelLateral.add(Box.createRigidArea(new Dimension(28, 0))); // Espaçamento lateral
		westPanelLateral.add(Box.createVerticalStrut(80)); // Espaçamento
		westPanelLateral.add(botaoInicio);
		westPanelLateral.add(Box.createVerticalStrut(10)); // Espaçamento
		westPanelLateral.add(botaoBiblioteca);
		westPanelLateral.add(Box.createVerticalStrut(10)); // Espaçamento
		westPanelLateral.add(botaoPlaylist);
		westPanelLateral.add(Box.createVerticalStrut(10)); // Espaçamento
		westPanelLateral.add(botaoFila);
		westPanelLateral.add(Box.createVerticalStrut(35)); // Espaçamento
		westPanelLateral.add(botaoConfig);

		panelLateral.add(westPanelLateral, BorderLayout.CENTER);

		// Método que reaje com a maximização da janela
		frame.addWindowStateListener(new WindowAdapter() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
					panelLateral.setPreferredSize(new Dimension(230, 100));
					panelLateral.add(westPanelLateral, BorderLayout.WEST);
					botaoInicio.setText(" Início  ");
					botaoBiblioteca.setText(" Biblioteca  ");
					botaoFila.setText(" Fila de Reprodução  ");
					botaoPlaylist.setText(" Playlists  ");
					botaoConfig.setText(" Configurações  ");
					botaoInicio.setToolTipText(null);
					botaoBiblioteca.setToolTipText(null);
					botaoPlaylist.setToolTipText(null);
					botaoFila.setToolTipText(null);
					botaoConfig.setToolTipText(null);
					botaoRemoveMusica.setToolTipText(null);
					botaoEditaMusica.setToolTipText(null);
					westSouthPanelInferior.setPreferredSize(new Dimension(380, 50));
					espacamentoInferior.setText(
							"                                                                                                                                  ");
				} else {
					panelLateral.setPreferredSize(new Dimension(70, 100));
					panelLateral.add(westPanelLateral, BorderLayout.CENTER);
					botaoInicio.setText("");
					botaoBiblioteca.setText("");
					botaoFila.setText("");
					botaoPlaylist.setText("");
					botaoConfig.setText("");
					botaoInicio.setToolTipText("Início");
					botaoBiblioteca.setToolTipText("Biblioteca");
					botaoPlaylist.setToolTipText("Playlists");
					botaoFila.setToolTipText("Fila de Reprodução");
					botaoConfig.setToolTipText("Configurações");
					botaoRemoveMusica.setToolTipText("Excluir");
					botaoEditaMusica.setToolTipText("Editar");
					westSouthPanelInferior.setPreferredSize(new Dimension(210, 50));
					espacamentoInferior.setText("                                                                 ");
				}
				frame.revalidate();
			}
		});

		frame.revalidate();
		frame.repaint();
	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão File/Folder Chooser (Adicionar Músicas)
		if (e.getSource() == botaoFolder) {

			int musicasAdicionadas = 0;
			boolean printaQuantidade = true;

			JFileChooser folderChooser = new JFileChooser();
			folderChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			folderChooser.setCurrentDirectory(new File("C:\\Users\\pedro\\Music"));

			int response = folderChooser.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) {
				File selectedFileOrFolder = folderChooser.getSelectedFile();

				// Pasta selecionada
				if (selectedFileOrFolder.isDirectory()) {
					File[] files = selectedFileOrFolder.listFiles();

					for (File file : files) {
						if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {

							String endereco = file.getAbsolutePath();
							String titulo = null;
							String artista = null;
							String album = null;
							String duracao = null;
							int duracaoEmSegundos = 0;

							try {
								AudioFile audioFile = AudioFileIO.read(file);
								Tag tag = audioFile.getTag();

								// Obtem as informações da música
								titulo = tag.getFirst(FieldKey.TITLE);
								artista = tag.getFirst(FieldKey.ARTIST);
								album = tag.getFirst(FieldKey.ALBUM);
								duracaoEmSegundos = audioFile.getAudioHeader().getTrackLength();

								int minutos = duracaoEmSegundos / 60;
								int segundos = duracaoEmSegundos % 60;
								DecimalFormat formato = new DecimalFormat("00");
								String segundosFormatados = formato.format(segundos);

								duracao = minutos + ":" + segundosFormatados;

							} catch (IOException | CannotReadException | ReadOnlyFileException e1) {
								e1.printStackTrace();
							} catch (TagException e1) {
								e1.printStackTrace();
							} catch (InvalidAudioFrameException e1) {
								e1.printStackTrace();
							}

							MusicaController musicaController = new MusicaController();

							if (musicaController.validaMusica(endereco, usuario.getCodigo())) {
								musicaController.cadastraMusica(titulo, artista, album, endereco, duracao,
										usuario.getCodigo());
								musicasAdicionadas++;
							}
						}
					}

					// Arquivo selecionado
				} else if (selectedFileOrFolder.isFile()
						&& selectedFileOrFolder.getName().toLowerCase().endsWith(".mp3")) {

					String endereco = selectedFileOrFolder.getAbsolutePath();
					String titulo = null;
					String artista = null;
					String album = null;
					String duracao = null;
					int duracaoEmSegundos = 0;

					try {
						AudioFile audioFile = AudioFileIO.read(selectedFileOrFolder);
						Tag tag = audioFile.getTag();

						// Obtem as informações da música
						titulo = tag.getFirst(FieldKey.TITLE);
						artista = tag.getFirst(FieldKey.ARTIST);
						album = tag.getFirst(FieldKey.ALBUM);
						duracaoEmSegundos = audioFile.getAudioHeader().getTrackLength();

						int minutos = duracaoEmSegundos / 60;
						int segundos = duracaoEmSegundos % 60;
						DecimalFormat formato = new DecimalFormat("00");
						String segundosFormatados = formato.format(segundos);

						duracao = minutos + ":" + segundosFormatados;

					} catch (IOException | CannotReadException | ReadOnlyFileException e1) {
						e1.printStackTrace();
					} catch (TagException e1) {
						e1.printStackTrace();
					} catch (InvalidAudioFrameException e1) {
						e1.printStackTrace();
					}

					MusicaController musicaController = new MusicaController();

					if (musicaController.validaMusica(endereco, usuario.getCodigo())) {
						musicaController.cadastraMusica(titulo, artista, album, endereco, duracao, usuario.getCodigo());
						musicasAdicionadas++;
					} else {
						printaQuantidade = false;
						JOptionPane.showMessageDialog(null, "Esta música já está na biblioteca!", "Atenção!",
								JOptionPane.WARNING_MESSAGE);
					}

				} else {
					printaQuantidade = false;
					JOptionPane.showMessageDialog(null, "Selecione uma pasta ou um arquivo MP3!", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
				}

				if (printaQuantidade) {
					if (musicasAdicionadas == 0) {
						JOptionPane.showMessageDialog(null, "Não existem arquivos MP3 na pasta selecionada!",
								"Atenção!", JOptionPane.WARNING_MESSAGE);
					} else {
						MusicasTableModel novoModelo = new MusicasTableModel(
								MusicaController.retornaMusicas(usuario.getCodigo()));
						tabelaMusicas.setModel(novoModelo);
						aplicarRenderizadorATabela(tabelaMusicas);
						botaoRemoveMusica.setVisible(true);
						botaoEditaMusica.setVisible(true);
						campoBuscaMusica.setVisible(true);
						botaoAddMusicaPlaylist.setVisible(true);
						botaoAddMusicaFila.setVisible(true);
						labelBibliotecaVazia.setVisible(false);
						JOptionPane.showMessageDialog(null,
								"Foram adicionadas " + musicasAdicionadas + " músicas à biblioteca!",
								"Adição de Músicas", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}

		// Botão Remover Música
		if (e.getSource() == botaoRemoveMusica) {
			if (codMusicaSelecionado != 0) {
				int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar esta música?",
						"Confirmação", JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					MusicaController mc = new MusicaController();
					mc.removeMusica(codMusicaSelecionado, usuario.getCodigo());
					MusicasTableModel novoModelo = new MusicasTableModel(
							MusicaController.retornaMusicas(usuario.getCodigo()));
					tabelaMusicas.setModel(novoModelo);
					aplicarRenderizadorATabela(tabelaMusicas);
					codMusicaSelecionado = 0;
					existemMusicas = mc.existemMusicasParaUsuario(usuario.getCodigo());
					if (existemMusicas) {
						botaoRemoveMusica.setVisible(true);
						botaoEditaMusica.setVisible(true);
						campoBuscaMusica.setVisible(true);
						botaoAddMusicaPlaylist.setVisible(true);
						botaoAddMusicaFila.setVisible(true);
						labelBibliotecaVazia.setVisible(false);
					} else {
						botaoRemoveMusica.setVisible(false);
						botaoEditaMusica.setVisible(false);
						campoBuscaMusica.setVisible(false);
						botaoAddMusicaPlaylist.setVisible(false);
						botaoAddMusicaFila.setVisible(false);
						labelBibliotecaVazia.setVisible(true);
					}
				}
			}
		}

		// Botão Editar Música
		if (e.getSource() == botaoEditaMusica) {
			if (codMusicaSelecionado != 0) {
				EditMusicaGui editgui = new EditMusicaGui(codMusicaSelecionado, usuario.getCodigo(), usuario, this);
				editgui.campoTitulo.setText(tituloSelecionado);
				editgui.campoArtista.setText(artistaSelecionado);
				editgui.campoAlbum.setText(albumSelecionado);
			}
		}

		// Botão Add Musica Playlist
		if (e.getSource() == botaoAddMusicaPlaylist) {
			if (codMusicaSelecionado != 0) {
				new AddMusicaPlaylistGui(codMusicaSelecionado, usuario.getCodigo(), this);
			}
		}

		// Botão Add Musica Fila
		if (e.getSource() == botaoAddMusicaFila) {
			if (codMusicaSelecionado != 0) {
				MusicaController mc = new MusicaController();
				Musica musica = mc.retornaMusicaPeloCodigo(codMusicaSelecionado);
				String tituloMusica = musica.getTitulo();
				adicionarMusicaAFila(musica);
				JOptionPane.showMessageDialog(frame,
						"A música " + tituloMusica + " foi adicionada à fila com sucesso!");
				botaoRemoveFila.setVisible(true);
				botaoLimpaFila.setVisible(true);
				labelFilaVazia.setVisible(false);
			}
		}

		// Botão Remover Música da Fila
		if (e.getSource() == botaoRemoveFila) {
			if (codMusicaFilaSelecionado != 0) {
				int resposta = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja remover esta música da fila?", "Confirmação",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					removerMusicaSelecionada();
					if (musicasFila.isEmpty()) {
						botaoRemoveFila.setVisible(false);
						botaoLimpaFila.setVisible(false);
						labelFilaVazia.setVisible(true);
					}
				}
			}

		}

		// Botão Limpar Fila
		if (e.getSource() == botaoLimpaFila) {
			int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar a fila?", "Confirmação",
					JOptionPane.YES_NO_OPTION);
			if (resposta == JOptionPane.YES_OPTION) {
				limparFilaDeReproducao();
				botaoRemoveFila.setVisible(false);
				botaoLimpaFila.setVisible(false);
				labelFilaVazia.setVisible(true);
			}
		}

		// Botão Adicionar Playlist
		if (e.getSource() == botaoAddPlaylist) {
			AddPlaylistGui addplygui = new AddPlaylistGui(usuario.getCodigo(), this);
			addplygui.campoNome.setText(null);
		}

		// Botão Remover Playlist
		if (e.getSource() == botaoRemovePlaylist) {
			if (codPlaylistSelecionado != 0) {
				int resposta = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja deletar esta playlist?\nSuas músicas serão desvinculadas dela!",
						"Confirmação", JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					PlaylistController pc = new PlaylistController();
					pc.removePlaylist(codPlaylistSelecionado, usuario.getCodigo());
					PlaylistsTableModel novoModelo = new PlaylistsTableModel(
							PlaylistController.retornaMusicas(usuario.getCodigo()));
					tabelaPlaylists.setModel(novoModelo);
					aplicarRenderizadorATabelaPlaylists(tabelaPlaylists);
					MusicaController mc = new MusicaController();
					mc.excluirTodasMusicasDaPlaylist(codPlaylistSelecionado);
					codPlaylistSelecionado = 0;
					existemPlaylists = pc.existemPlaylistsParaUsuario(usuario.getCodigo());
					if (existemPlaylists) {
						botaoRemovePlaylist.setVisible(true);
						botaoEditaPlaylist.setVisible(true);
						botaoVerMusicas.setVisible(true);
						labelPlaylistVazia.setVisible(false);
					} else {
						botaoRemovePlaylist.setVisible(false);
						botaoEditaPlaylist.setVisible(false);
						botaoVerMusicas.setVisible(false);
						labelPlaylistVazia.setVisible(true);
					}
				}
			}
		}

		// Botão Editar Playlist
		if (e.getSource() == botaoEditaPlaylist) {
			if (codPlaylistSelecionado != 0) {
				new EditPlaylistGui(nomePlaylistSelecionado, codPlaylistSelecionado, usuario.getCodigo(), this);
			}
		}

		// Botão ver músicas da Playlist
		if (e.getSource() == botaoVerMusicas) {
			if (codPlaylistSelecionado != 0) {
				MusicaController mc = new MusicaController();
				boolean existem = mc.existemMusicasNaPlaylist(codPlaylistSelecionado);
				if (existem) {
					new SeePlaylistGui(nomePlaylistSelecionado, codPlaylistSelecionado, usuario.getCodigo(), this);
				} else {
					JOptionPane.showMessageDialog(null, "Esta playlist está vazia!", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		// Botão de Logout
		if (e.getSource() == botaoSair) {
			int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Confirmação",
					JOptionPane.YES_NO_OPTION);
			if (resposta == JOptionPane.YES_OPTION) {
				UsuarioConfig.realizarLogoff();
				frame.dispose();
				new LoginGui();
			}
		}

		// Botão Ver Documentação
		if (e.getSource() == botaoDoc) {
			new PDFViewerGui("auxiliar/doc/Artigo.pdf");
		}

		// Botões da barra lateral
		if (e.getSource() == botaoInicio) {
			cardLayout.show(cards, "Inicio");

		}

		if (e.getSource() == botaoBiblioteca) {
			cardLayout.show(cards, "Biblioteca");

		}

		if (e.getSource() == botaoPlaylist) {
			cardLayout.show(cards, "Playlist");

		}

		if (e.getSource() == botaoFila) {
			cardLayout.show(cards, "Fila");

		}

		if (e.getSource() == botaoConfig) {
			cardLayout.show(cards, "Config");

		}

		// Adiciona a borda lateral no botão clicado
		if (e.getSource() == botaoInicio || e.getSource() == botaoBiblioteca || e.getSource() == botaoPlaylist
				|| e.getSource() == botaoFila || e.getSource() == botaoConfig) {
			JButton botaoClicado = (JButton) e.getSource();

			if (ultimoBotaoClicado != null) {
				ultimoBotaoClicado.setBorderPainted(false);
				ultimoBotaoClicado.setBorder(emptyBorder);
			} else {
				botaoInicio.setBorderPainted(false);
				botaoInicio.setBorder(emptyBorder);
			}

			botaoClicado.setBorderPainted(true);
			botaoClicado.setBorder(bordaBotaoClicado);

			ultimoBotaoClicado = botaoClicado;
		}

		// Botão Play/Pause
		if (e.getSource() == botaoPlay) {
			if (!isPlaying) {
				if (!pausado) {
					if (enderecoSelecionado != null) {
						iniciarReproducao();
					} else {
						if (!musicasFila.isEmpty()) {
							Musica primeiraMusicaFila = musicasFila.get(0);
							iniciarReproducaoFila(primeiraMusicaFila);
							avancaMusica();
						} else {
							JOptionPane.showMessageDialog(null, "Selecione uma música da biblioteca ou uma playlist para reproduzir.",
									"Atenção!", JOptionPane.WARNING_MESSAGE);
						}
					}
				} else {
					if (!enderecoSelecionado.equals(currentEndereco) && musicasFila.isEmpty()) {
						iniciarReproducao();
					} else {
						playerController.resume();
						timerLabel.resume();
						pausado = false;
						isPlaying = true;
						botaoPlay.setIcon(iconePause);
					}
				}
			} else {
				pausarReproducaoAtual();
			}
		}

		// Botão Previous
		if (e.getSource() == botaoVoltar) {
			voltaMusica();
		}

		// Botão Next
		if (e.getSource() == botaoAvancar) {
			avancaMusica();
		}

	}

	// Método que adiciona uma música no fim da Fila de Reprodução
	public void adicionarMusicaAFila(Musica musica) {
		musicasFila.add(musica);
		atualizarTabelaFila();
	}

	// Método que adiciona uma Playlist no fim da Fila de Reprodução
	public void adicionarPlaylistNaFila(List<Musica> musicas) {
		if (musicasFila == null) {
			musicasFila = new ArrayList<>();
		}
		musicasFila.addAll(musicas);
		atualizarTabelaFila();
	}

	// Método que limpa a fila de reprodução
	public void limparFilaDeReproducao() {
		if (musicasFila != null) {
			musicasFila.clear();
			atualizarTabelaFila();
		}
	}

	// Método que remove a música selecionada da fila de reprodução
	public void removerMusicaSelecionada() {
		if (selectedRowFila >= 0) {
			if (musicasFila != null && !musicasFila.isEmpty()) {
				musicasFila.remove(selectedRowFila);
				atualizarTabelaFila();
			}
		}
	}

	// Método que adiciona músicas na lista de músicas recentes
	public void adicionarMusicaRecente(Musica musica) {

		if (listaRecentes.contains(musica)) {
			listaRecentes.remove(musica);
		}

		if (listaRecentes.size() >= maxSize) {
			listaRecentes.removeLast();
		}

		listaRecentes.addFirst(musica);
		atualizarTabelaMusicasRecentes();
	}

	// Método para iniciar a reprodução da música
	private void iniciarReproducao() {
		if (currentIndex >= 0) {
			MusicaController mc = new MusicaController();
			Musica musica = mc.retornaMusicaPeloCodigo(codMusicaSelecionado);
			if (musica != null) {
				File arquivoMusica = new File(musica.getEndereco());
				if (arquivoMusica.exists()) {
					pararReproducaoAtual();
					FileInputStream input = null;
					try {
						input = new FileInputStream(arquivoMusica);
						playerController = new PlayerController(input);
						playerController.play();
						timerLabel.setVisible(true);
						labelSeparadora2.setVisible(true);
						long duracaoMilisegundos = convertDurationToMillis(musica.getDuracao());
						timerLabel.setDuration(duracaoMilisegundos);
						timerLabel.start();
						isPlaying = true;
						botaoPlay.setIcon(iconePause);
						labelArtistaTitulo.setText("    " + musica.getArtista() + " - " + musica.getTitulo());
						labelAlbum.setText("     " + musica.getAlbum());
						labelDuracao.setText(musica.getDuracao());
						currentEndereco = musica.getEndereco();
						adicionarMusicaRecente(musica);
						labelMusicasRecentes.setText("Músicas recentes");
						westSouthNorthPanelInicio.setPreferredSize(new Dimension(205, 40));
						
					} catch (FileNotFoundException | JavaLayerException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "O arquivo da música não foi encontrado.",
							"Erro ao Reproduzir a Música", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Música inválida. Selecione uma música válida para reproduzir.",
						"Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// Método para iniciar a reprodução da música da fila
	protected void iniciarReproducaoFila(Musica musica) {
		if (musica != null) {
			File arquivoMusica = new File(musica.getEndereco());
			if (arquivoMusica.exists()) {
				pararReproducaoAtual();
				FileInputStream input = null;
				try {
					input = new FileInputStream(arquivoMusica);
					playerController = new PlayerController(input);
					playerController.play();
					timerLabel.setVisible(true);
					labelSeparadora2.setVisible(true);
					long duracaoMilisegundos = convertDurationToMillis(musica.getDuracao());
					timerLabel.setDuration(duracaoMilisegundos);
					timerLabel.start();
					isPlaying = true;
					botaoPlay.setIcon(iconePause);
					labelArtistaTitulo.setText("    " + musica.getArtista() + " - " + musica.getTitulo());
					labelAlbum.setText("     " + musica.getAlbum());
					labelDuracao.setText(musica.getDuracao());
					currentEndereco = musica.getEndereco();
					adicionarMusicaRecente(musica);
					labelMusicasRecentes.setText("Músicas recentes");
					westSouthNorthPanelInicio.setPreferredSize(new Dimension(205, 40));
				} catch (FileNotFoundException | JavaLayerException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "O arquivo da música não foi encontrado.",
						"Erro ao Reproduzir a Música", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Música inválida. Selecione uma música válida para reproduzir.",
					"Atenção!", JOptionPane.WARNING_MESSAGE);
		}
	}

	// Método para pausar a reprodução da música
	private void pausarReproducaoAtual() {
		if (playerController != null) {
			timerLabel.pause();
			playerController.pause();
			isPlaying = false;
			botaoPlay.setIcon(iconePlay);
			pausado = true;
		}
	}

	// Método para parar a reprodução da música
	private void pararReproducaoAtual() {
		if (playerController != null) {
			timerLabel.resume();
			timerLabel.reset();
			playerController.stop();
			isPlaying = false;
			botaoPlay.setIcon(iconePlay);
		}
	}

	// Método que retona para a música anterior e inicia a reprodução
	public void voltaMusica() {
		if (playerController != null) {
			if (currentIndex > 0) {
				currentIndex--;
				atualizarSelecaoETocar();
			} else {
				JOptionPane.showMessageDialog(null, "Você já está na primeira música.", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// Método que avança para a próxima música e inicia a reprodução
	public void avancaMusica() {
		if (playerController != null) {
			if (!musicasFila.isEmpty()) {
				if (musicaAnterior != null) {
					musicasFila.remove(musicaAnterior);
					atualizarTabelaFila();
					if (musicasFila.isEmpty()) {
						botaoRemoveFila.setVisible(false);
						botaoLimpaFila.setVisible(false);
						labelFilaVazia.setVisible(true);
						musicaAnterior = null;
					}
				}
				if (!musicasFila.isEmpty()) {
					Musica proximaMusica = musicasFila.get(0);
					iniciarReproducaoFila(proximaMusica);
					musicaAnterior = proximaMusica;
					int rowIndex = musicasFila.indexOf(proximaMusica);
					tabelaMusicasFila.setRowSelectionInterval(rowIndex, rowIndex);
				} else {
					if (currentIndex < tabelaMusicas.getRowCount() - 1) {
						currentIndex++;
						atualizarSelecaoETocar();
					} else {
						JOptionPane.showMessageDialog(null, "Você já está na última música.", "Atenção!",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			} else {
				if (currentIndex < tabelaMusicas.getRowCount() - 1) {
					currentIndex++;
					atualizarSelecaoETocar();
				} else {
					JOptionPane.showMessageDialog(null, "Você já está na última música.", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	// Método para atualizar seleção e iniciar reprodução
	private void atualizarSelecaoETocar() {
		if (currentIndex >= 0 && currentIndex < tabelaMusicas.getRowCount()) {
			tabelaMusicas.setRowSelectionInterval(currentIndex, currentIndex);
			iniciarReproducao();
		}
	}

	// Método que converte a duração para milisegundos
	public static long convertDurationToMillis(String durationString) {
		String[] parts = durationString.split(":");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Formato de duração inválido. Use o formato 'minutos:segundos'.");
		}

		try {
			int minutes = Integer.parseInt(parts[0]);
			int seconds = Integer.parseInt(parts[1]);
			return (long) (minutes * 60_000) + (seconds * 1_000);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Formato de duração inválido. Use números inteiros para minutos e segundos.");
		}
	}

	// Método que atualiza a tabela de acordo com a pesquisa
	private void atualizarPesquisa() {
		String textoBusca = campoBuscaMusica.getText();
		if (textoBusca != null && !textoBusca.equals("Pesquisar") && textoBusca.length() > 1) {
			MusicasTableModel novoModelo = new MusicasTableModel(
					MusicaController.retornaMusicasFiltradas(usuario.getCodigo(), textoBusca));
			tabelaMusicas.setModel(novoModelo);
			aplicarRenderizadorATabela(tabelaMusicas);
		} else {
			MusicasTableModel novoModelo = new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo()));
			tabelaMusicas.setModel(novoModelo);
			aplicarRenderizadorATabela(tabelaMusicas);
		}
	}

	// Método que aplica o renderizador personalizado na tabela das músicas
	public void aplicarRenderizadorATabela(JTable tabela) {
		@SuppressWarnings("serial")
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (isSelected) {
					setBorder(BorderFactory.createEmptyBorder());
				}

				setHorizontalAlignment(SwingConstants.CENTER);

				tabela.getColumnModel().getColumn(0).setPreferredWidth(180);
				tabela.getColumnModel().getColumn(3).setPreferredWidth(20);

				// Define as colunas do código e do endereço como invisíveis
				TableColumn colunaCodigo = tabela.getColumnModel().getColumn(4);
				colunaCodigo.setMaxWidth(0);
				colunaCodigo.setMinWidth(0);
				colunaCodigo.setPreferredWidth(0);
				colunaCodigo.setResizable(false);

				TableColumn colunaEndereco = tabela.getColumnModel().getColumn(5);
				colunaEndereco.setMaxWidth(0);
				colunaEndereco.setMinWidth(0);
				colunaEndereco.setPreferredWidth(0);
				colunaEndereco.setResizable(false);

				return c;
			}
		};

		// Aplica o renderizador a todas as colunas da tabela
		for (int i = 0; i < tabela.getColumnCount(); i++) {
			tabela.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
	}

	// Método que aplica o renderizador personalizado na tabela das playlists
	private void aplicarRenderizadorATabelaPlaylists(JTable tabela) {
		@SuppressWarnings("serial")
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (isSelected) {
					setBorder(BorderFactory.createEmptyBorder());
				}

				setHorizontalAlignment(SwingConstants.CENTER);

				tabela.getColumnModel().getColumn(0).setPreferredWidth(250);

				// Define a coluna do código como invisivel
				TableColumn colunaCodigo = tabela.getColumnModel().getColumn(3);
				colunaCodigo.setMaxWidth(0);
				colunaCodigo.setMinWidth(0);
				colunaCodigo.setPreferredWidth(0);
				colunaCodigo.setResizable(false);

				if (column == 1) {
					if (value != null && value instanceof Integer) {
						int quantidadeMusicas = (int) value;
						setText(quantidadeMusicas + " Músicas");
						setFont(getFont().deriveFont(13f));
					}
				}

				if (column == 2) {
					if (value != null && value instanceof String) {
						String duracaoStr = (String) value;
						String[] partes = duracaoStr.split(":");
						if (partes.length == 2) {
							try {
								int minutos = Integer.parseInt(partes[0]);
								int segundos = Integer.parseInt(partes[1]);
								setText(String.format("%02d:%02d", minutos, segundos));
							} catch (NumberFormatException e) {

							}
						}
					} else {
						setText("00:00");
					}
					setFont(getFont().deriveFont(13f));
				}

				return c;
			}
		};

		// Aplica o renderizador a todas as colunas da tabela
		for (int i = 0; i < tabela.getColumnCount(); i++) {
			tabela.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
	}

	// Métodos para customizar a scroll
	static class CustomScrollBarUI extends BasicScrollBarUI {
		protected void configureScrollBarColors() {
			thumbColor = Color.DARK_GRAY;
			thumbDarkShadowColor = null;
			thumbHighlightColor = null;
			thumbLightShadowColor = null;
			trackColor = new Color(39, 39, 39);
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

	// Método que atualiza o look and feel dos botões
	private void updateButtonUI() {
		SwingUtilities.updateComponentTreeUI(botaoFolder);
		SwingUtilities.updateComponentTreeUI(botaoInicio);
		SwingUtilities.updateComponentTreeUI(botaoBiblioteca);
		SwingUtilities.updateComponentTreeUI(botaoFila);
		SwingUtilities.updateComponentTreeUI(botaoPlaylist);
		SwingUtilities.updateComponentTreeUI(botaoConfig);
		SwingUtilities.updateComponentTreeUI(botaoPlay);
		SwingUtilities.updateComponentTreeUI(botaoAvancar);
		SwingUtilities.updateComponentTreeUI(botaoVoltar);
		SwingUtilities.updateComponentTreeUI(botaoRemoveMusica);
		SwingUtilities.updateComponentTreeUI(botaoEditaMusica);
		SwingUtilities.updateComponentTreeUI(botaoAddMusicaPlaylist);
		SwingUtilities.updateComponentTreeUI(botaoAddMusicaFila);
		SwingUtilities.updateComponentTreeUI(botaoRemovePlaylist);
		SwingUtilities.updateComponentTreeUI(botaoEditaPlaylist);
		SwingUtilities.updateComponentTreeUI(botaoAddPlaylist);
		SwingUtilities.updateComponentTreeUI(botaoSair);
		SwingUtilities.updateComponentTreeUI(botaoDoc);
		SwingUtilities.updateComponentTreeUI(botaoVerMusicas);
		SwingUtilities.updateComponentTreeUI(botaoRemoveFila);
		SwingUtilities.updateComponentTreeUI(botaoLimpaFila);
	}

	// Método que atualiza a tabela de Músicas
	public void atualizarTabelaPrincipal() {
		MusicasTableModel novoModelo = new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo()));
		tabelaMusicas.setModel(novoModelo);
		aplicarRenderizadorATabela(tabelaMusicas);
	}

	// Método que atualiza a tabela de Playlists
	public void atualizarTabelaPlaylist() {
		PlaylistsTableModel novoModelo = new PlaylistsTableModel(
				PlaylistController.retornaMusicas(usuario.getCodigo()));
		tabelaPlaylists.setModel(novoModelo);
		aplicarRenderizadorATabelaPlaylists(tabelaPlaylists);
	}

	// Método que atualiza a tabela da Fila de Reprodução
	public void atualizarTabelaFila() {
		MusicasTableModel novoModelo = new MusicasTableModel(musicasFila);
		tabelaMusicasFila.setModel(novoModelo);
		aplicarRenderizadorATabela(tabelaMusicasFila);
	}

	// Método que atualiza a tabela da Fila de Reprodução
	public void atualizarTabelaMusicasRecentes() {
		MusicasTableModel novoModelo = new MusicasTableModel(listaRecentes);
		tabelaMusicasRecentes.setModel(novoModelo);
		aplicarRenderizadorATabela(tabelaMusicasRecentes);
	}

	// Método que atualiza a tabela da Fila de Reprodução
	public void atualizarTabelaPlaylistsOuvidas() {
		PlaylistsTableModel novoModelo = new PlaylistsTableModel(playlistsMaisOuvidas);
		tabelaPlaylistsOuvidas.setModel(novoModelo);
		aplicarRenderizadorATabelaPlaylists(tabelaPlaylistsOuvidas);
	}

}
