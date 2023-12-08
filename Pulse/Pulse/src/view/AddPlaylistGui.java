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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.PlaylistController;

public class AddPlaylistGui implements ActionListener {

	JFrame frame = new JFrame();

	JTextField campoNome = new JTextField();

	JLabel labelAdd = new JLabel("Criar Nova Playlist");
	JLabel labelNome = new JLabel("Nome");

	JButton botaoSalvar = new JButton("Criar");
	JButton botaoCancelar = new JButton("Cancelar");

	EmptyBorder emptyBorder = new EmptyBorder(0, 10, 0, 0);

	private int codUsuario;
	private PrincipalGui principal;

	public AddPlaylistGui(int codigoUsuario, PrincipalGui principalGui) {

		this.codUsuario = codigoUsuario;
		this.principal = principalGui;

		// Propriedades da janela principal
		frame.setSize(340, 245);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Nova Playlist");

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

		labelAdd.setBounds(75, 30, 300, 25);
		labelAdd.setForeground(Color.WHITE);
		labelAdd.setFont(new Font("Arial", Font.BOLD, 20));

		labelNome.setBounds(43, 67, 75, 25);
		labelNome.setForeground(Color.LIGHT_GRAY);
		labelNome.setFont(new Font(null, Font.BOLD, 13));

		campoNome.setBounds(40, 90, 245, 25);
		campoNome.setBorder(null);
		campoNome.setBackground(new Color(60, 60, 60));
		campoNome.setForeground(Color.WHITE);
		campoNome.setFont(new Font("Arial", Font.BOLD, 12));
		campoNome.setBorder(emptyBorder);

		botaoSalvar.setBounds(46, 145, 115, 25);
		botaoSalvar.setBackground(new Color(255, 130, 50));
		botaoSalvar.setForeground(Color.WHITE);
		botaoSalvar.setFocusable(false);
		botaoSalvar.addActionListener(this);
		botaoSalvar.setFont(new Font("Arial", Font.BOLD, 12));
		botaoSalvar.setFocusPainted(false);

		botaoCancelar.setBounds(166, 145, 115, 25);
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

		frame.add(labelAdd);
		frame.add(labelNome);
		frame.add(campoNome);
		frame.add(botaoSalvar);
		frame.add(botaoCancelar);

	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão Salvar
		if (e.getSource() == botaoSalvar) {
			if (campoNome.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o nome da playlist!", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			} else {
				PlaylistController pc = new PlaylistController();
				if (pc.validaNome(campoNome.getText(), codUsuario)) {
					pc.cadastraPlaylist(campoNome.getText(), codUsuario);
					principal.atualizarTabelaPlaylist();
					frame.dispose();
					principal.botaoRemovePlaylist.setVisible(true);
					principal.botaoEditaPlaylist.setVisible(true);
					principal.labelPlaylistVazia.setVisible(false);
					principal.botaoVerMusicas.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Já existe uma playlist com esse nome!", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		// Botão Cancelar
		if (e.getSource() == botaoCancelar) {
			frame.dispose();
		}

	}

}
