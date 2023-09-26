package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import control.MusicaController;
import model.Usuario;
import player.PlayerController;

public class PrincipalGui implements ActionListener{
	
	JFrame frame = new JFrame();
	
	PlayerController pc = new PlayerController("/Pulse/src/auxiliar/music/01 Shine on You Crazy Diamond.mp3");
	
	ImageIcon iconeAddMusica = new ImageIcon("/Pulse/src/auxiliar/icone_addmusica.png");
	ImageIcon iconeInicio = new ImageIcon("/Pulse/src/auxiliar/icone_inicio.png");
    ImageIcon iconeBiblioteca = new ImageIcon("/Pulse/src/auxiliar/icone_biblioteca.png");
    ImageIcon iconeFila = new ImageIcon("/Pulse/src/auxiliar/icone_fila.png");
    ImageIcon iconePlaylist = new ImageIcon("/Pulse/src/auxiliar/icone_playlist.png");
    ImageIcon iconeConfig = new ImageIcon("/Pulse/src/auxiliar/icone_config.png"); 
    ImageIcon iconePlay = new ImageIcon("/Pulse/src/auxiliar/icone_play.png");
    ImageIcon iconePause = new ImageIcon("/Pulse/src/auxiliar/icone_pause.png");
    ImageIcon iconeAvancar = new ImageIcon("/Pulse/src/auxiliar/icone_avancar.png");
    ImageIcon iconeVoltar = new ImageIcon("/Pulse/src/auxiliar/icone_voltar.png");
    ImageIcon iconeRemover = new ImageIcon("/Pulse/src/auxiliar/icone_remover.png");
    ImageIcon iconeEditar = new ImageIcon("/Pulse/src/auxiliar/icone_editar.png");
    ImageIcon iconeAddMusicaPlaylist = new ImageIcon("/Pulse/src/auxiliar/icone_addMusicaPlaylist.png"); 
    
    Border emptyBorder = BorderFactory.createEmptyBorder();
    Border bordaDireita = new MatteBorder(0, 0, 0, 1, Color.BLACK);
    Border bordaSuperior = new MatteBorder(1, 0, 0, 0, Color.BLACK);
    Border bordaScroll = new MatteBorder(null);
    Border bordaCabecalho = new MatteBorder(2, 2, 1, 2, Color.GRAY);
    Border bordaAddMusica = new MatteBorder(1, 1, 1, 1, Color.GRAY);

	JLabel labelBemVindo = new JLabel("Bem-Vindo(a)!");
	
	JButton botaoFolder = new JButton(" 🎵 Adicionar Músicas ");
	
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

	private Usuario usuario;
	private CardLayout cardLayout;
	boolean isPlaying = false;
	
	String tituloSelecionado;
	String artistaSelecionado;
	String albumSelecionado;
	
	public PrincipalGui(Usuario usuario){
		
		this.usuario = usuario;
		
	//Componentes
		labelBemVindo.setFont(new Font(null,Font.PLAIN,15));
		labelBemVindo.setText("Bem-Vindos " + usuario.getNome() + "!");
		
		barraProgresso.setPreferredSize(new Dimension(600, 4)); 
		barraProgresso.setBackground(Color.WHITE);
		barraProgresso.setForeground(Color.RED); 
		barraProgresso.setBorderPainted(false);
        barraProgresso.setString(""); 
        barraProgresso.setIndeterminate(false);
        barraProgresso.setValue(50);
        
        botaoFolder.setPreferredSize(new Dimension(130, 25)); 
        botaoFolder.setBorder(bordaAddMusica);
		botaoFolder.setFocusable(false);
		botaoFolder.addActionListener(this);
        botaoFolder.setBackground(new Color(57, 57, 57));
        botaoFolder.setForeground(Color.WHITE); 
        
        botaoFolder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	botaoFolder.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	botaoFolder.setBackground(new Color(57, 57, 57));
            }
        });

        

	//Frame principal
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
		
	//Paineis
		panelLateral.setBackground(new Color(32,32,32));
		panelReproducao.setBackground(new Color(35,42,42));
		panelInicio.setBackground(new Color(37,40,41));
		panelBiblioteca.setBackground(new Color(37,40,41));
		panelPlaylist.setBackground(new Color(37,40,41));
		panelFila.setBackground(new Color(37,40,41));
		panelConfig.setBackground(new Color(37,40,41));
		
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

	//Painel Inicio
		JLabel labelInicio = new JLabel("");
		labelInicio.setFont(new Font("Calibri",Font.BOLD,40));
		labelInicio.setForeground(Color.WHITE);
		labelInicio.setText("   Início");
		
		JPanel northPanelInicio = new JPanel();
		northPanelInicio.setLayout(new BorderLayout());
		northPanelInicio.setBackground(new Color(37,40,41));
		northPanelInicio.setPreferredSize(new Dimension(1, 100));
		northPanelInicio.add(labelInicio,BorderLayout.WEST);
        
		panelInicio.add(northPanelInicio,BorderLayout.NORTH);
		
	//Painel Biblioteca
		JLabel labelBiblioteca = new JLabel("");
		labelBiblioteca.setFont(new Font("Calibri",Font.BOLD,40));
		labelBiblioteca.setForeground(Color.WHITE);
		labelBiblioteca.setText("   Músicas");
		
		JPanel eastNorthPanelBiblioteca = new JPanel();
		eastNorthPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 34));
		eastNorthPanelBiblioteca.setBackground(new Color(37,40,41));
		eastNorthPanelBiblioteca.setPreferredSize(new Dimension(225, 100));
		eastNorthPanelBiblioteca.add(botaoFolder);
		
		JPanel eastSouthNorthPanelBiblioteca = new JPanel();
		eastSouthNorthPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 3));
		eastSouthNorthPanelBiblioteca.setBackground(new Color(37,40,41));
		eastSouthNorthPanelBiblioteca.setPreferredSize(new Dimension(190, 40));
		eastSouthNorthPanelBiblioteca.add(botaoRemoveMusica);
		eastSouthNorthPanelBiblioteca.add(botaoEditaMusica);
		eastSouthNorthPanelBiblioteca.add(botaoAddMusicaPlaylist);
		
		JPanel southNorthPanelBiblioteca = new JPanel();
		southNorthPanelBiblioteca.setLayout(new BorderLayout());
		southNorthPanelBiblioteca.setBackground(new Color(37,40,41));
		southNorthPanelBiblioteca.setPreferredSize(new Dimension(200, 40));
		southNorthPanelBiblioteca.add(eastSouthNorthPanelBiblioteca,BorderLayout.EAST);

		JPanel northPanelBiblioteca = new JPanel();
		northPanelBiblioteca.setLayout(new BorderLayout());
		northPanelBiblioteca.setBackground(new Color(37,40,41));
		northPanelBiblioteca.setPreferredSize(new Dimension(1, 140));
		northPanelBiblioteca.add(labelBiblioteca,BorderLayout.WEST);
		northPanelBiblioteca.add(eastNorthPanelBiblioteca,BorderLayout.EAST);
		northPanelBiblioteca.add(southNorthPanelBiblioteca,BorderLayout.SOUTH);
        
		panelBiblioteca.add(northPanelBiblioteca,BorderLayout.NORTH);
		
		//Paineis de espaçamento
		JPanel eastPanelBiblioteca = new JPanel();
		eastPanelBiblioteca.setBackground(new Color(37,40,41));
		eastPanelBiblioteca.setPreferredSize(new Dimension(50, 50));
		
		JPanel westPanelBiblioteca = new JPanel();
		westPanelBiblioteca.setBackground(new Color(37,40,41));
		westPanelBiblioteca.setPreferredSize(new Dimension(50, 50));
		
		JPanel southPanelBiblioteca = new JPanel();
		southPanelBiblioteca.setBackground(new Color(37,40,41));
		southPanelBiblioteca.setPreferredSize(new Dimension(90, 90));
		southPanelBiblioteca.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		
		panelBiblioteca.add(eastPanelBiblioteca,BorderLayout.EAST);
		panelBiblioteca.add(westPanelBiblioteca,BorderLayout.WEST);
		panelBiblioteca.add(southPanelBiblioteca,BorderLayout.SOUTH);
		
        //Tabela de Músicas
		tabelaMusicas = new JTable(new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo())));	
		tabelaMusicas.setBackground(new Color(37,40,41));
		tabelaMusicas.setBackground(new Color(52, 52, 52));
		tabelaMusicas.setForeground(new Color(255, 130, 50));
		tabelaMusicas.setBorder(null);
		tabelaMusicas.setShowGrid(false);
		tabelaMusicas.setIntercellSpacing(new Dimension(0, 0)); // Remove o espaço entre as células
		tabelaMusicas.setFont(new Font("Arial", Font.BOLD, 13));
		tabelaMusicas.setRowHeight(35);

		JTableHeader cabecalho = tabelaMusicas.getTableHeader();
		cabecalho.setBackground(new Color(48,48,48));
		cabecalho.setForeground(Color.LIGHT_GRAY);
		cabecalho.setBorder(bordaCabecalho);
		cabecalho.setFont(cabecalho.getFont().deriveFont(Font.BOLD, 14));

		JScrollPane scroll = new JScrollPane(this.tabelaMusicas);
		scroll.setBorder(bordaScroll);
		scroll.getViewport().setBackground(new Color(37,40,41));
		
		panelBiblioteca.add(scroll, BorderLayout.CENTER);
		
		tabelaMusicas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        //Captura a seleção do usuário
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = tabelaMusicas.getSelectedRow();
		            if (selectedRow != -1) {
		                tituloSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 0);
		                artistaSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 1);
		                albumSelecionado = (String) tabelaMusicas.getValueAt(selectedRow, 2);
		            }
		        }
		    }
		});

		
	//Painel Playlist
		JLabel labelPlaylist = new JLabel("");
		labelPlaylist.setFont(new Font("Calibri",Font.BOLD,40));
		labelPlaylist.setForeground(Color.WHITE);
		labelPlaylist.setText("   Playlists");	
		
		JPanel northPanelPlaylist = new JPanel();
		northPanelPlaylist.setLayout(new BorderLayout());
		northPanelPlaylist.setBackground(new Color(37,40,41));
		northPanelPlaylist.setPreferredSize(new Dimension(1, 100));
		northPanelPlaylist.add(labelPlaylist,BorderLayout.WEST);
        
		panelPlaylist.add(northPanelPlaylist,BorderLayout.NORTH);
		
	//Painel Fila
		JLabel labelFila = new JLabel("");
		labelFila.setFont(new Font("Calibri",Font.BOLD,40));
		labelFila.setForeground(Color.WHITE);
		labelFila.setText("   Fila de Reprodução");

		JPanel northPanelFila = new JPanel();
		northPanelFila.setLayout(new BorderLayout());
		northPanelFila.setBackground(new Color(37,40,41));
		northPanelFila.setPreferredSize(new Dimension(1, 100));
		northPanelFila.add(labelFila,BorderLayout.WEST);
        
		panelFila.add(northPanelFila,BorderLayout.NORTH);
		
	//Painel Config
		JLabel labelConfig = new JLabel("");
		labelConfig.setFont(new Font("Calibri",Font.BOLD,40));
		labelConfig.setForeground(Color.WHITE);
		labelConfig.setText("   Configurações");
		
		JPanel northPanelConfig = new JPanel();
		northPanelConfig.setLayout(new BorderLayout());
		northPanelConfig.setBackground(new Color(37,40,41));
		northPanelConfig.setPreferredSize(new Dimension(1, 100));
		northPanelConfig.add(labelConfig,BorderLayout.WEST);
        
		panelConfig.add(northPanelConfig,BorderLayout.NORTH);
		
	//Painel Inferior (Reprodutor)	
		JPanel southPanelInferior = new JPanel();
        southPanelInferior.setLayout(new FlowLayout());
        southPanelInferior.setBackground(new Color(35,42,42));

        southPanelInferior.add(botaoVoltar);
        southPanelInferior.add(botaoPlay);
        southPanelInferior.add(botaoAvancar);
        
        JPanel northPanelInferior = new JPanel();
        northPanelInferior.setLayout(new FlowLayout());
        northPanelInferior.setBackground(new Color(35,42,42));  
        
        northPanelInferior.add(barraProgresso);
        
        panelReproducao.add(southPanelInferior,BorderLayout.CENTER);
        panelReproducao.add(northPanelInferior,BorderLayout.NORTH);
        
        //Método que expande a barra de reprodução
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = frame.getWidth() - 90; 
                barraProgresso.setPreferredSize(new Dimension(newWidth, 4));
            }
        });
		
	//Painel Lateral		
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
	    
		//Método que expande a barra larteral
	    frame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                	panelLateral.setPreferredSize(new Dimension(250, 100)); 
                	panelLateral.add(westPanelLateral,BorderLayout.WEST);
                    barraProgresso.setPreferredSize(new Dimension(frame.getWidth() - 90, 4)); 
                } else {
                	panelLateral.setPreferredSize(new Dimension(70, 100)); 
                	panelLateral.add(westPanelLateral,BorderLayout.CENTER);
                    barraProgresso.setPreferredSize(new Dimension(frame.getWidth() - 90, 4));
                }
                frame.revalidate();
            }
        });
	    frame.revalidate();
	}
	
	//Ações
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Botão File/Folder Chooser
		int musicasAdicionadas = 0;
		boolean printaQuantidade = true;
		
		if (e.getSource() == botaoFolder) {
			
		    JFileChooser folderChooser = new JFileChooser();
		    folderChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    folderChooser.setCurrentDirectory(new File("C:\\")); 

		    int response = folderChooser.showOpenDialog(null);

		    if (response == JFileChooser.APPROVE_OPTION) {
		        File selectedFileOrFolder = folderChooser.getSelectedFile();

		        //Pasta selecionada
		        if (selectedFileOrFolder.isDirectory()) {
		            File[] files = selectedFileOrFolder.listFiles();

		            for (File file : files) {
		                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
		                	
		                	String titulo = file.getName(); 
		                    String endereco = file.getAbsolutePath(); 
		                    
		                    String artista = null;
	                        String album = null;
	                        float duracao = 0;
		            
		                    MusicaController musicaController = new MusicaController();
		                    
		                    if (musicaController.validaMusica(endereco, usuario.getCodigo())) {
			                    musicaController.cadastraMusica(titulo, artista, album, endereco, duracao, usuario.getCodigo());
			                    musicasAdicionadas++;
		                    }
		                }
		            }
		            
		        //Arquivo selecionado    
		        } else if (selectedFileOrFolder.isFile() && selectedFileOrFolder.getName().toLowerCase().endsWith(".mp3")) {
		          
		            String titulo = selectedFileOrFolder.getName();
		            String endereco = selectedFileOrFolder.getAbsolutePath();

                    //String artista = null;
                    //String album = null;
                    //float duracao = 0;
                    
                    String artista = "Teste";
                    String album = "Album1";
                    float duracao = 2;
		            
		            MusicaController musicaController = new MusicaController();
	
			            if (musicaController.validaMusica(endereco, usuario.getCodigo())) {
			                musicaController.cadastraMusica(titulo, artista, album, endereco, duracao, usuario.getCodigo());
			                musicasAdicionadas++;
			            } else {
			            	System.out.println("Esta música já está na biblioteca!");
			            	printaQuantidade = false;
			            }
		            
		        } else {
		            System.out.println("Selecione uma pasta ou um arquivo MP3!");
		            printaQuantidade = false;
		        }
		        
		        if(printaQuantidade) {
		        	if (musicasAdicionadas == 0) {
		        		System.out.println("Não existem arquivos MP3 na pasta selecionada!");
		        	} else {
		        		System.out.println("Foram adicionadas " + musicasAdicionadas + " músicas à biblioteca!");
			        	MusicasTableModel novoModelo = new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo()));
			        	tabelaMusicas.setModel(novoModelo);
		        	}
		        }
		        
		    }
		}

		//Botão Play/Pause
		if (e.getSource() == botaoPlay) {
		    if (!isPlaying) {
		    	pc.tocar();
		        botaoPlay.setIcon(iconePause);
		    } else {
		    	pc.parar();
		        botaoPlay.setIcon(iconePlay);    
		    }
		    isPlaying = !isPlaying;
		}
		
		//Botão Remove Música
		if (e.getSource() == botaoRemoveMusica) {
			MusicaController uc = new MusicaController();
			uc.removeMusica(tituloSelecionado, usuario.getCodigo());
			MusicasTableModel novoModelo = new MusicasTableModel(MusicaController.retornaMusicas(usuario.getCodigo()));
        	tabelaMusicas.setModel(novoModelo);
		}
		
		//Botão Edita Música
		if (e.getSource() == botaoEditaMusica) {
			

		}
		
		//Botões da barra lateral
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
			
	}
	
	//Método que cria botões com ícones
	private JButton criarBotaoPersonalizado(ImageIcon icone) {
        JButton botao = new JButton(icone);
        
        botao.setFocusable(false);
		botao.setBorder(emptyBorder);
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
		botao.addActionListener(this);

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(Color.LIGHT_GRAY);
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
                UIManager.put("Button.select", new Color(92, 93, 93)); 
            }
        });
        return botao;
    }
	
}
