package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import control.MusicaController;
import model.Usuario;
import player.PlayerController;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class PrincipalGui implements ActionListener{
	
	JFrame frame = new JFrame();
	
	ImageIcon iconeInicio = new ImageIcon("auxiliar/icone_inicio.png");
    ImageIcon iconeBiblioteca = new ImageIcon("auxiliar/icone_biblioteca.png");
    ImageIcon iconeFila = new ImageIcon("auxiliar/icone_fila.png");
    ImageIcon iconePlaylist = new ImageIcon("auxiliar/icone_playlist.png");
    ImageIcon iconeConfig = new ImageIcon("auxiliar/icone_config.png"); 
    ImageIcon iconePlay = new ImageIcon("auxiliar/icone_play.png");
    ImageIcon iconePause = new ImageIcon("auxiliar/icone_pause.png");
    ImageIcon iconeAvancar = new ImageIcon("auxiliar/icone_avancar.png");
    ImageIcon iconeVoltar = new ImageIcon("auxiliar/icone_voltar.png");
    ImageIcon iconeRemover = new ImageIcon("auxiliar/icone_remover.png");
    ImageIcon iconeEditar = new ImageIcon("auxiliar/icone_editar.png");
    ImageIcon iconeAddMusicaPlaylist = new ImageIcon("auxiliar/icone_addMusicaPlaylist.png"); 
    
    Border emptyBorder =  new MatteBorder(0, 0, 0, 0, Color.BLACK);
    Border bordaDireita = new MatteBorder(0, 0, 0, 1, Color.BLACK);
    Border bordaSuperior = new MatteBorder(1, 0, 0, 0, Color.BLACK);
    Border bordaBotaoClicado = new MatteBorder(0, 3, 0, 0, (new Color(255, 130, 50)));
    Border bordaScroll = new MatteBorder(null);
    
    Color corLateral = new Color(1);
    Color corInferior = new Color(1);
    Color corCentral = new Color(39,39,39);

	JLabel labelBemVindo = new JLabel("Bem-Vindo(a)!");
	JLabel labelInicio = new JLabel();
	JLabel labelBiblioteca = new JLabel();
	JLabel labelPlaylist = new JLabel();
	JLabel labelFila = new JLabel();
	JLabel labelConfig = new JLabel();
	
	JButton botaoFolder = new JButton("Adicionar Músicas");
	JButton botaoInicio = criarBotaoPersonalizado(iconeInicio);
    JButton botaoBiblioteca = criarBotaoPersonalizado(iconeBiblioteca);
    JButton botaoFila = criarBotaoPersonalizado(iconeFila);
    JButton botaoPlaylist = criarBotaoPersonalizado(iconePlaylist);
    JButton botaoConfig = criarBotaoPersonalizado(iconeConfig);
    JButton botaoPlay = criarBotaoPersonalizado(iconePlay);
    JButton botaoAvancar = criarBotaoPersonalizado(iconeAvancar);
    JButton botaoVoltar = criarBotaoPersonalizado(iconeVoltar);
    JButton botaoRemoveMusica = criarBotaoPersonalizado(iconeRemover);
    JButton botaoEditaMusica = criarBotaoPersonalizado(iconeEditar);
    JButton botaoAddMusicaPlaylist = criarBotaoPersonalizado(iconeAddMusicaPlaylist);
    JButton ultimoBotaoClicado; 
    
	JPanel panelLateral = new JPanel();
	JPanel panelReproducao = new JPanel();
	JPanel panelInicio = new JPanel();
	JPanel panelBiblioteca = new JPanel();
	JPanel panelPlaylist = new JPanel();
	JPanel panelFila = new JPanel();
	JPanel panelConfig = new JPanel();
	JPanel cards;
	
	JProgressBar barraProgresso = new JProgressBar();
	
	JTable tabelaMusicas;	 
	
	private PlayerController pc;

	private Usuario usuario;
	private CardLayout cardLayout;
	boolean isPlaying = false;
	
	private int codMusicaSelecionado;
	protected String tituloSelecionado;
	protected String artistaSelecionado;
	protected String albumSelecionado;
	private String enderecoSelecionado;


	public PrincipalGui(Usuario usuario){
		
		// Usuário logado
		this.usuario = usuario;

		// Define um look and feel para o projeto
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
		updateButtonUI();
		
		// Componentes
		labelBemVindo.setFont(new Font("Calibri",Font.BOLD,17));
		labelBemVindo.setForeground(Color.LIGHT_GRAY);
		labelBemVindo.setText("Bem-Vindo(a) " + usuario.getNome() + "!         ");
		
		labelInicio.setFont(new Font("Calibri",Font.BOLD,40));
		labelInicio.setForeground(Color.WHITE);
		labelInicio.setText("   Início");		
		labelBiblioteca.setFont(new Font("Calibri",Font.BOLD,40));
		labelBiblioteca.setForeground(Color.WHITE);
		labelBiblioteca.setText("   Músicas");		
		labelPlaylist.setFont(new Font("Calibri",Font.BOLD,40));
		labelPlaylist.setForeground(Color.WHITE);
		labelPlaylist.setText("   Playlists");	
		labelFila.setFont(new Font("Calibri",Font.BOLD,40));
		labelFila.setForeground(Color.WHITE);
		labelFila.setText("   Fila de Reprodução");	
		labelConfig.setFont(new Font("Calibri",Font.BOLD,40));
		labelConfig.setForeground(Color.WHITE);
		labelConfig.setText("   Configurações");
        
        botaoInicio.setBorderPainted(true);
        botaoInicio.setBorder(bordaBotaoClicado);
        
        botaoInicio.setToolTipText("Início");
        botaoBiblioteca.setToolTipText("Biblioteca");
        botaoFila.setToolTipText("Playlist");
        botaoPlaylist.setToolTipText("Fila");
        botaoConfig.setToolTipText("Configurações");
        botaoRemoveMusica.setToolTipText("Excluir");
        botaoEditaMusica.setToolTipText("Editar");
 
		barraProgresso.setPreferredSize(new Dimension(600, 3)); 
		barraProgresso.setBackground(Color.WHITE);
		barraProgresso.setForeground(new Color(255, 130, 50));
        barraProgresso.setString(""); 
        barraProgresso.setIndeterminate(false);
        barraProgresso.setValue(20);
        barraProgresso.setBorderPainted(false);
		barraProgresso.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        botaoFolder.setPreferredSize(new Dimension(130, 25)); 
        botaoFolder.setBorder(null);
		botaoFolder.setFocusable(false);
		botaoFolder.addActionListener(this);
        botaoFolder.setBackground(new Color(25, 25, 25));
        botaoFolder.setForeground(Color.WHITE); 
        botaoFolder.setFont(new Font("Arial", Font.BOLD, 12));
        botaoFolder.setFocusPainted(false);

        botaoFolder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	botaoFolder.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	botaoFolder.setBackground(new Color(20, 20, 20));
            }
        });
       
        // Frame principal
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(680,680);
		frame.setMinimumSize(new Dimension(680,680));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setTitle("Pulse");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screenSize.width - frame.getWidth()) / 2;
	    int y = (screenSize.height - frame.getHeight()) / 2;
	    frame.setLocation(x, y);
		
	    // Paineis
		panelLateral.setBackground(new Color(32,32,32));
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
	
		panelLateral.setPreferredSize(new Dimension(70,100));
		panelReproducao.setPreferredSize(new Dimension(100,100));
		
		frame.add(panelLateral,BorderLayout.WEST);
		frame.add(panelReproducao,BorderLayout.SOUTH);
		frame.add(cards, BorderLayout.CENTER);

		// Painel Inicio
		JPanel northPanelInicio = new JPanel();
		northPanelInicio.setLayout(new BorderLayout());
		northPanelInicio.setBackground(corCentral);
		northPanelInicio.setPreferredSize(new Dimension(1, 100));
		
		northPanelInicio.add(labelInicio,BorderLayout.WEST);
		//northPanelInicio.add(labelBemVindo,BorderLayout.EAST);
        
		panelInicio.add(northPanelInicio,BorderLayout.NORTH);
		
		// Painel Biblioteca
		JPanel eastNorthPanelBiblioteca = new JPanel();
		eastNorthPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 33));
		eastNorthPanelBiblioteca.setBackground(corCentral);
		eastNorthPanelBiblioteca.setPreferredSize(new Dimension(215, 100));
		eastNorthPanelBiblioteca.add(botaoFolder);
		
		JLabel espacamento = new JLabel(" ");
		
		JPanel eastSouthNorthPanelBiblioteca = new JPanel();
		eastSouthNorthPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		eastSouthNorthPanelBiblioteca.setBackground(corCentral);
		eastSouthNorthPanelBiblioteca.setPreferredSize(new Dimension(150, 40));
		eastSouthNorthPanelBiblioteca.add(botaoEditaMusica);
		eastSouthNorthPanelBiblioteca.add(espacamento);
		eastSouthNorthPanelBiblioteca.add(botaoRemoveMusica);
		//eastSouthNorthPanelBiblioteca.add(botaoAddMusicaPlaylist);
		
		JPanel southNorthPanelBiblioteca = new JPanel();
		southNorthPanelBiblioteca.setLayout(new BorderLayout());
		southNorthPanelBiblioteca.setBackground(corCentral);
		southNorthPanelBiblioteca.setPreferredSize(new Dimension(200, 40));
		southNorthPanelBiblioteca.add(eastSouthNorthPanelBiblioteca,BorderLayout.EAST);

		JPanel northPanelBiblioteca = new JPanel();
		northPanelBiblioteca.setLayout(new BorderLayout());
		northPanelBiblioteca.setBackground(corCentral);
		northPanelBiblioteca.setPreferredSize(new Dimension(1, 140));
		northPanelBiblioteca.add(labelBiblioteca,BorderLayout.WEST);
		northPanelBiblioteca.add(eastNorthPanelBiblioteca,BorderLayout.EAST);
		northPanelBiblioteca.add(southNorthPanelBiblioteca,BorderLayout.SOUTH);
        
		panelBiblioteca.add(northPanelBiblioteca,BorderLayout.NORTH);
		
		//Paineis de espaçamento
		JPanel eastPanelBiblioteca = new JPanel();
		eastPanelBiblioteca.setBackground(corCentral);
		eastPanelBiblioteca.setPreferredSize(new Dimension(45, 45));
		
		JPanel westPanelBiblioteca = new JPanel();
		westPanelBiblioteca.setBackground(corCentral);
		westPanelBiblioteca.setPreferredSize(new Dimension(45, 45));
		
		JPanel southPanelBiblioteca = new JPanel();
		southPanelBiblioteca.setBackground(corCentral);
		southPanelBiblioteca.setPreferredSize(new Dimension(90, 90));
		southPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		
		panelBiblioteca.add(eastPanelBiblioteca,BorderLayout.EAST);
		panelBiblioteca.add(westPanelBiblioteca,BorderLayout.WEST);
		panelBiblioteca.add(southPanelBiblioteca,BorderLayout.SOUTH);
		
        // Tabela de Músicas
		tabelaMusicas = new JTable(new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo())));	
		tabelaMusicas.setBackground(new Color(52, 52, 52));
		tabelaMusicas.setForeground(Color.WHITE);
		tabelaMusicas.setBorder(null);
		tabelaMusicas.setShowGrid(false);
		tabelaMusicas.setFont(new Font("Arial", Font.BOLD, 12));
		tabelaMusicas.setRowHeight(30);
		tabelaMusicas.setTableHeader(null); 
		
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
		            	albumSelecionado  = (String) tabelaMusicas.getValueAt(selectedRow, 2);
		            	codMusicaSelecionado = (int) tabelaMusicas.getValueAt(selectedRow, 4);
		            	enderecoSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 5);	
		            }
		        }
		    }
		});
		
		aplicarRenderizadorATabela(tabelaMusicas);
		
		JScrollPane scroll = new JScrollPane(this.tabelaMusicas);
		scroll.setBorder(bordaScroll);
		scroll.getViewport().setBackground(corCentral);
		
		panelBiblioteca.add(scroll, BorderLayout.CENTER);
		
		// Painel Playlist
		JPanel northPanelPlaylist = new JPanel();
		northPanelPlaylist.setLayout(new BorderLayout());
		northPanelPlaylist.setBackground(corCentral);
		northPanelPlaylist.setPreferredSize(new Dimension(1, 100));
		northPanelPlaylist.add(labelPlaylist,BorderLayout.WEST);
        
		panelPlaylist.add(northPanelPlaylist,BorderLayout.NORTH);
		
		// Painel Fila
		JPanel northPanelFila = new JPanel();
		northPanelFila.setLayout(new BorderLayout());
		northPanelFila.setBackground(corCentral);
		northPanelFila.setPreferredSize(new Dimension(1, 100));
		northPanelFila.add(labelFila,BorderLayout.WEST);
        
		panelFila.add(northPanelFila,BorderLayout.NORTH);
		
		// Painel Config
		JPanel northPanelConfig = new JPanel();
		northPanelConfig.setLayout(new BorderLayout());
		northPanelConfig.setBackground(corCentral);
		northPanelConfig.setPreferredSize(new Dimension(1, 100));
		northPanelConfig.add(labelConfig,BorderLayout.WEST);
        
		panelConfig.add(northPanelConfig,BorderLayout.NORTH);
		
		// Painel Inferior (Reprodutor)	
		JPanel southPanelInferior = new JPanel();
        southPanelInferior.setLayout(new FlowLayout());
        southPanelInferior.setBackground(corCentral);

        southPanelInferior.add(botaoVoltar);
        southPanelInferior.add(botaoPlay);
        southPanelInferior.add(botaoAvancar);
        
        JPanel northPanelInferior = new JPanel();
        northPanelInferior.setLayout(new FlowLayout());
        northPanelInferior.setBackground(corCentral);
        
        northPanelInferior.add(barraProgresso);
        
        panelReproducao.add(southPanelInferior,BorderLayout.CENTER);
        panelReproducao.add(northPanelInferior,BorderLayout.NORTH);
        
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
        westPanelLateral.setBackground(new Color(32,32,32));
        
        westPanelLateral.add(Box.createRigidArea(new Dimension(28, 0))); //Espaçamento lateral
        westPanelLateral.add(Box.createVerticalStrut(80)); //Espaçamento
        westPanelLateral.add(botaoInicio);
        westPanelLateral.add(Box.createVerticalStrut(10)); //Espaçamento
        westPanelLateral.add(botaoBiblioteca);
        westPanelLateral.add(Box.createVerticalStrut(10)); //Espaçamento
        westPanelLateral.add(botaoPlaylist);
        westPanelLateral.add(Box.createVerticalStrut(10)); //Espaçamento
        westPanelLateral.add(botaoFila);
        westPanelLateral.add(Box.createVerticalStrut(25)); //Espaçamento
        westPanelLateral.add(botaoConfig);
	    
	    panelLateral.add(westPanelLateral,BorderLayout.CENTER);
	    
		// Método que expande a barra larteral
	    frame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                	panelLateral.setPreferredSize(new Dimension(250, 100)); 
                	panelLateral.add(westPanelLateral,BorderLayout.WEST);
                    barraProgresso.setPreferredSize(new Dimension(frame.getWidth() - 90, 3));  
                } else {
                	panelLateral.setPreferredSize(new Dimension(70, 100)); 
                	panelLateral.add(westPanelLateral,BorderLayout.CENTER);
                    barraProgresso.setPreferredSize(new Dimension(frame.getWidth() - 90, 3));  
                }
                frame.revalidate();
            }
        });
	    
	    frame.revalidate();
	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Botão File/Folder Chooser
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
	                           
	                            duracao = minutos + ":" + segundos;
	                            
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
		                    }
		                }
		            }
		            
		        // Arquivo selecionado    
		        } else if (selectedFileOrFolder.isFile() && selectedFileOrFolder.getName().toLowerCase().endsWith(".mp3")) {
		          
		            String endereco = selectedFileOrFolder.getAbsolutePath();
		            String titulo = null;
                    String artista = null;
                    String album = null;
                    String duracao = null;
                    int duracaoEmSegundos = 0;
                    
                    try {
                        AudioFile audioFile = AudioFileIO.read(selectedFileOrFolder);
                        Tag tag = audioFile.getTag();
                        
                        //Obtem as informações da música
                        titulo = tag.getFirst(FieldKey.TITLE);
                        artista = tag.getFirst(FieldKey.ARTIST);
                        album = tag.getFirst(FieldKey.ALBUM);
                        duracaoEmSegundos = audioFile.getAudioHeader().getTrackLength();

                        int minutos = duracaoEmSegundos / 60;
                        int segundos = duracaoEmSegundos % 60;
                        
                        duracao = minutos + ":" + segundos;
                        
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
			                JOptionPane.showMessageDialog(null, "Esta música já está na biblioteca!", "Atenção!", JOptionPane.WARNING_MESSAGE);
			            }
		            
		        } else {     
		            printaQuantidade = false;
		            JOptionPane.showMessageDialog(null, "Selecione uma pasta ou um arquivo MP3!", "Atenção!", JOptionPane.WARNING_MESSAGE);
		        }
		        
		        if(printaQuantidade) {
		        	if (musicasAdicionadas == 0) {
		                JOptionPane.showMessageDialog(null, "Não existem arquivos MP3 na pasta selecionada!", "Atenção!", JOptionPane.WARNING_MESSAGE);
		        	} else {
			        	MusicasTableModel novoModelo = new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo()));
			        	tabelaMusicas.setModel(novoModelo);
			        	aplicarRenderizadorATabela(tabelaMusicas);
			        	JOptionPane.showMessageDialog(null, "Foram adicionadas " + musicasAdicionadas + " músicas à biblioteca!", "Adição de Músicas", JOptionPane.INFORMATION_MESSAGE);
		        	}
		        }
		        
		    }
		}

		// Botão Play/Pause
		if (e.getSource() == botaoPlay) {
		    if (!isPlaying) {
		        if (pc == null) {
		            if (enderecoSelecionado != null) {
		                pc = new PlayerController(enderecoSelecionado);
		                pc.tocar();
		                if (pc.musicaCarregadaComSucesso()) {
		                    isPlaying = true;
		                    botaoPlay.setIcon(iconePause);
		                } else {
		                	JOptionPane.showMessageDialog(null, "O endereço desta música foi alterado! Considere remove-la e adicionar novamente!", "Erro!", JOptionPane.ERROR_MESSAGE);
		                    pc = null;
		                }
		            } else {
		                System.out.println("Selecione uma música para reproduzir!");
		            }
		        }
		    } else {
		        if (pc != null) {
		            pc.parar();
		            pc = null;
		        }
		        isPlaying = false;
		        botaoPlay.setIcon(iconePlay);
		    }
		}
		
		/*if (e.getSource() == botaoPlay) {
			if (!isPlaying) {
				if (pc == null) {
					if (enderecoSelecionado != null) {
						pc = new PlayerController(enderecoSelecionado);
						pc.tocar();
						
					} else {
						System.out.println("Selecione uma música para reproduzir!");
					}
				}
				
			} else {
				if (pc != null) {
					pc.parar();
					pc = null;
				}
			}
			isPlaying = !isPlaying;
		    botaoPlay.setIcon(isPlaying ? iconePause : iconePlay);
		}*/

		
		// Botão Remover Música
		if (e.getSource() == botaoRemoveMusica) {
				if (codMusicaSelecionado != 0) {
					int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar esta música?", "Confirmação", JOptionPane.YES_NO_OPTION);
					if (resposta == JOptionPane.YES_OPTION) {
						MusicaController uc = new MusicaController();
						uc.removeMusica(codMusicaSelecionado, usuario.getCodigo());
						MusicasTableModel novoModelo = new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo()));
			        	tabelaMusicas.setModel(novoModelo);
			        	aplicarRenderizadorATabela(tabelaMusicas);
			        	codMusicaSelecionado = 0;
			        	frame.revalidate();
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
	        	frame.revalidate();
			}	
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
		if (e.getSource() == botaoInicio || e.getSource() == botaoBiblioteca || e.getSource() == botaoPlaylist || e.getSource() == botaoFila || e.getSource() == botaoConfig) {
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
		
	}
	
	// Método que aplica o renderizador personalizado na tabela das músicas
	private void aplicarRenderizadorATabela(JTable tabela) {
	        @SuppressWarnings("serial")
			DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
	            @Override
	            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	                if (isSelected) {
	                    setBorder(BorderFactory.createEmptyBorder());
	                }

	                setHorizontalAlignment(SwingConstants.CENTER);
	                
	                tabela.getColumnModel().getColumn(0).setPreferredWidth(180);
	        		tabela.getColumnModel().getColumn(3).setPreferredWidth(20);
	        		
	        		//Define as colunas do código e do endereço como invisíveis
	        		TableColumn colunaCodigo = tabelaMusicas.getColumnModel().getColumn(4);
	        		colunaCodigo.setMaxWidth(0);
	        		colunaCodigo.setMinWidth(0);
	        		colunaCodigo.setPreferredWidth(0);
	        		colunaCodigo.setResizable(false);
	        		
	        		TableColumn colunaEndereco = tabelaMusicas.getColumnModel().getColumn(5);
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
	
	// Método que cria botões com ícones
	private JButton criarBotaoPersonalizado(ImageIcon icone) {
        JButton botao = new JButton(icone);
        
        botao.setFocusable(false);
		botao.setBorder(emptyBorder);
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
		botao.addActionListener(this);
		botao.setBackground(null);

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
    }
    
    public void atualizarTabelaPrincipal() {
        MusicasTableModel novoModelo = new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo()));
        tabelaMusicas.setModel(novoModelo);
        aplicarRenderizadorATabela(tabelaMusicas);
    }
    
}
