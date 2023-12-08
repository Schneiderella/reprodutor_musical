package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import config.UsuarioConfig;
import control.UsuarioController;
import model.Usuario;

public class LoginGui implements ActionListener {

	JFrame frame = new JFrame();
	JButton botaoLogin = new JButton("Login");
	JButton botaoCadastro = new JButton("Cadastre-se");
	JTextField campoLogin = new JTextField();
	JPasswordField campoSenha = new JPasswordField();
	JLabel labelLogin = new JLabel("Login");
	JLabel labelSenha = new JLabel("Senha");
	JLabel labelAviso = new JLabel();
	JLabel labelMensagem = new JLabel("Bem-vindo ao Pulse!");
	JLabel labelCadastro = new JLabel("Não possui uma conta?");
	ImageIcon imagem = new ImageIcon("auxiliar/icones/icone_login.png");
	JLabel labelImagem = new JLabel(imagem);

	@SuppressWarnings("serial")
	public LoginGui() {

		// Painel que faz o fade de cores
		frame.setContentPane(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				int width = getWidth();
				int height = getHeight();
				Color color1 = new Color(228, 44, 100);
				Color color2 = new Color(97, 74, 211);
				GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, width, height);
			}
		});

		// Componentes
		labelAviso.setFont(new Font(null, Font.ITALIC, 12));

		labelImagem.setBounds(174, 83, 80, 80);

		labelLogin.setBounds(117, 157, 75, 25);
		labelLogin.setForeground(Color.WHITE);
		labelLogin.setFont(new Font(null, Font.BOLD, 12));

		labelSenha.setBounds(117, 207, 75, 25);
		labelSenha.setForeground(Color.WHITE);
		labelSenha.setFont(new Font(null, Font.BOLD, 12));

		labelMensagem.setBounds(77, 44, 300, 35);
		labelMensagem.setForeground(Color.WHITE);
		labelMensagem.setFont(new Font("Calibri", Font.BOLD, 32));

		labelCadastro.setBounds(109, 327, 150, 25);
		labelCadastro.setForeground(Color.WHITE);
		labelCadastro.setFont(new Font(null, Font.BOLD, 12));

		campoLogin.setBounds(114, 177, 200, 25);
		campoLogin.setBorder(new LineBorder(Color.WHITE));
		campoLogin.setBackground(new Color(0, 0, 0, 0));
		campoLogin.setOpaque(false);
		campoLogin.setForeground(Color.WHITE);
		campoLogin.setFont(new Font("Arial", Font.PLAIN, 12));

		campoSenha.setBounds(114, 227, 200, 25);
		campoSenha.setBorder(new LineBorder(Color.WHITE));
		campoSenha.setBackground(new Color(0, 0, 0, 0));
		campoSenha.setOpaque(false);
		campoSenha.setForeground(Color.WHITE);
		campoSenha.setFont(new Font("Arial", Font.PLAIN, 12));

		botaoLogin.setBounds(164, 277, 100, 25);
		botaoLogin.setForeground(Color.WHITE);
		botaoLogin.setBorder(new LineBorder(Color.WHITE));
		botaoLogin.setFocusable(false);
		botaoLogin.addActionListener(this);
		botaoLogin.setContentAreaFilled(false);
		botaoLogin.setFont(new Font("Arial", Font.BOLD, 12));

		botaoCadastro.setBounds(219, 328, 125, 25);
		botaoCadastro.setForeground(Color.WHITE);
		botaoCadastro.setFocusable(false);
		botaoCadastro.setBorderPainted(false);
		botaoCadastro.setContentAreaFilled(false);
		botaoCadastro.addActionListener(this);
		botaoCadastro.setFont(new Font("Arial", Font.BOLD, 12));

		// Métodos que reajem com o passar do mouse
		botaoLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoLogin.setForeground(Color.LIGHT_GRAY);
				botaoLogin.setBorder(new LineBorder(Color.LIGHT_GRAY));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoLogin.setForeground(Color.WHITE);
				botaoLogin.setBorder(new LineBorder(Color.WHITE));
			}
		});

		botaoCadastro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoCadastro.setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoCadastro.setForeground(Color.WHITE);
			}
		});

		// Adicionando os componentes
		frame.add(labelImagem);
		frame.add(labelLogin);
		frame.add(labelSenha);
		frame.add(labelCadastro);
		frame.add(labelAviso);
		frame.add(labelMensagem);
		frame.add(campoLogin);
		frame.add(campoSenha);
		frame.add(botaoLogin);
		frame.add(botaoCadastro);

		// Propriedades da janela principal
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(440, 440);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Login");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão Login
		if (e.getSource() == botaoLogin) {
			String login = campoLogin.getText();
			String senha = String.valueOf(campoSenha.getPassword());

			if (login.isEmpty() || senha.isEmpty()) {
				labelAviso.setBounds(165, 252, 250, 15);
				labelAviso.setForeground(Color.WHITE);
				labelAviso.setText("Preencha todos os campos!");
				return;
			}

			UsuarioController usuarioController = new UsuarioController();
			int loginValido = usuarioController.validaUsuario(login, senha);

			if (loginValido == 1) {
				frame.dispose();
				UsuarioController uc = new UsuarioController();
				Usuario usuario = uc.buscarUsuario(login, senha);
				UsuarioConfig.salvarCodigoUsuario(usuario.getCodigo());
				new PrincipalGui(usuario);
			} else {
				if (loginValido == 0) {
					labelAviso.setBounds(198, 252, 250, 15);
					labelAviso.setForeground(Color.WHITE);
					labelAviso.setText("Credenciais inválidas!");
				} else {
					if (loginValido == -1) {
						labelAviso.setBounds(90, 252, 300, 15);
						labelAviso.setForeground(Color.RED);
						labelAviso.setText("Problemas com a consulta no Banco de Dados!");
					}
				}
			}
		}

		// Botão Cadastro
		if (e.getSource() == botaoCadastro) {
			frame.dispose();
			new CadastroGui();
		}

	}

}
