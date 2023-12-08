package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.MusicaController;
import model.Usuario;

public class EditMusicaGui implements ActionListener {

	JFrame frame = new JFrame();

	JTextField campoTitulo = new JTextField();
	JTextField campoArtista = new JTextField();
	JTextField campoAlbum = new JTextField();

	JLabel labelEdicao = new JLabel("Editar Informações da Música");
	JLabel labelTitulo = new JLabel("Título");
	JLabel labelArtista = new JLabel("Artista");
	JLabel labelAlbum = new JLabel("Álbum");

	JButton botaoSalvar = new JButton("Salvar");
	JButton botaoCancelar = new JButton("Cancelar");

	EmptyBorder emptyBorder = new EmptyBorder(0, 10, 0, 0);

	int codUsuario;
	int codMusica;

	private PrincipalGui principal;

	public EditMusicaGui(int musicaSelecionada, int codigoUsuario, Usuario usuario, PrincipalGui principalGui) {

		this.codUsuario = codigoUsuario;
		this.codMusica = musicaSelecionada;
		this.principal = principalGui;

		// Propriedades da janela principal
		frame.setSize(340, 385);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Edição de Música");

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

		labelEdicao.setBounds(21, 30, 300, 25);
		labelEdicao.setForeground(Color.WHITE);
		labelEdicao.setFont(new Font("Arial", Font.BOLD, 20));

		labelTitulo.setBounds(43, 67, 75, 25);
		labelTitulo.setForeground(Color.LIGHT_GRAY);
		labelTitulo.setFont(new Font(null, Font.BOLD, 13));

		labelArtista.setBounds(43, 137, 75, 25);
		labelArtista.setForeground(Color.LIGHT_GRAY);
		labelArtista.setFont(new Font(null, Font.BOLD, 13));

		labelAlbum.setBounds(43, 207, 75, 25);
		labelAlbum.setForeground(Color.LIGHT_GRAY);
		labelAlbum.setFont(new Font(null, Font.BOLD, 13));

		campoTitulo.setBounds(40, 90, 245, 25);
		campoTitulo.setBorder(null);
		campoTitulo.setBackground(new Color(60, 60, 60));
		campoTitulo.setForeground(Color.WHITE);
		campoTitulo.setFont(new Font("Arial", Font.BOLD, 12));
		campoTitulo.setBorder(emptyBorder);

		campoAlbum.setBounds(40, 230, 245, 25);
		campoAlbum.setBorder(null);
		campoAlbum.setBackground(new Color(60, 60, 60));
		campoAlbum.setForeground(Color.WHITE);
		campoAlbum.setFont(new Font("Arial", Font.BOLD, 12));
		campoAlbum.setBorder(emptyBorder);

		campoArtista.setBounds(40, 160, 245, 25);
		campoArtista.setBorder(null);
		campoArtista.setBackground(new Color(60, 60, 60));
		campoArtista.setForeground(Color.WHITE);
		campoArtista.setFont(new Font("Arial", Font.BOLD, 12));
		campoArtista.setBorder(emptyBorder);

		botaoSalvar.setBounds(46, 285, 115, 25);
		botaoSalvar.setBackground(new Color(255, 130, 50));
		botaoSalvar.setForeground(Color.WHITE);
		botaoSalvar.setFocusable(false);
		botaoSalvar.addActionListener(this);
		botaoSalvar.setFont(new Font("Arial", Font.BOLD, 12));
		botaoSalvar.setFocusPainted(false);

		botaoCancelar.setBounds(166, 285, 115, 25);
		botaoCancelar.setBackground(new Color(25, 25, 25));
		botaoCancelar.setForeground(Color.WHITE);
		botaoCancelar.setFocusable(false);
		botaoCancelar.addActionListener(this);
		botaoCancelar.setFont(new Font("Arial", Font.BOLD, 12));
		botaoCancelar.setFocusPainted(false);

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
		frame.add(labelTitulo);
		frame.add(labelArtista);
		frame.add(labelAlbum);
		frame.add(campoAlbum);
		frame.add(campoArtista);
		frame.add(campoTitulo);
		frame.add(botaoSalvar);
		frame.add(botaoCancelar);

	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão Salvar
		if (e.getSource() == botaoSalvar) {
			MusicaController mc = new MusicaController();
			mc.editaMusica(codMusica, codUsuario, campoTitulo.getText(), campoArtista.getText(), campoAlbum.getText());
			principal.atualizarTabelaPrincipal();
			principal.tituloSelecionado = null;
			principal.artistaSelecionado = null;
			principal.albumSelecionado = null;
			frame.dispose();
		}

		// Botão Cancelar
		if (e.getSource() == botaoCancelar) {
			frame.dispose();
		}

	}
}
