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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import control.UsuarioController;

public class CadastroGui implements ActionListener {

	JFrame frame = new JFrame();
	JButton botaoCadastro = new JButton("Cadastrar");
	JButton botaoEntrar = new JButton("Entrar");
	JTextField campoNome = new JTextField();
	JTextField campoLogin = new JTextField();
	JPasswordField campoSenha = new JPasswordField();
	JPasswordField campoSenha2 = new JPasswordField();
	JLabel labelNome = new JLabel("Nome");
	JLabel labelLogin = new JLabel("Login");
	JLabel labelSenha = new JLabel("Senha");
	JLabel labelSenha2 = new JLabel("Confirme a senha");
	JLabel labelAviso = new JLabel();
	JLabel labelMensagem = new JLabel("Vamos criar a sua conta!");
	JLabel labelEntrar = new JLabel("Já possui uma conta?");

	@SuppressWarnings("serial")
	public CadastroGui() {

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

		labelNome.setBounds(117, 77, 75, 25);
		labelNome.setForeground(Color.WHITE);
		labelNome.setFont(new Font(null, Font.BOLD, 12));

		labelLogin.setBounds(117, 127, 75, 25);
		labelLogin.setForeground(Color.WHITE);
		labelLogin.setFont(new Font(null, Font.BOLD, 12));

		labelSenha.setBounds(117, 177, 75, 25);
		labelSenha.setForeground(Color.WHITE);
		labelSenha.setFont(new Font(null, Font.BOLD, 12));

		labelSenha2.setBounds(117, 227, 125, 25);
		labelSenha2.setForeground(Color.WHITE);
		labelSenha2.setFont(new Font(null, Font.BOLD, 12));

		labelMensagem.setBounds(69, 35, 300, 35);
		labelMensagem.setForeground(Color.WHITE);
		labelMensagem.setFont(new Font("Calibri", Font.BOLD, 28));

		labelEntrar.setBounds(132, 345, 150, 25);
		labelEntrar.setForeground(Color.WHITE);
		labelEntrar.setFont(new Font(null, Font.BOLD, 12));

		campoNome.setBounds(114, 97, 200, 25);
		campoNome.setBorder(new LineBorder(Color.WHITE));
		campoNome.setBackground(new Color(0, 0, 0, 0));
		campoNome.setOpaque(false);
		campoNome.setForeground(Color.WHITE);
		campoNome.setFont(new Font("Arial", Font.PLAIN, 12));

		campoLogin.setBounds(114, 147, 200, 25);
		campoLogin.setBorder(new LineBorder(Color.WHITE));
		campoLogin.setBackground(new Color(0, 0, 0, 0));
		campoLogin.setOpaque(false);
		campoLogin.setForeground(Color.WHITE);
		campoLogin.setFont(new Font("Arial", Font.PLAIN, 12));

		campoSenha.setBounds(114, 197, 200, 25);
		campoSenha.setBorder(new LineBorder(Color.WHITE));
		campoSenha.setBackground(new Color(0, 0, 0, 0));
		campoSenha.setOpaque(false);
		campoSenha.setForeground(Color.WHITE);
		campoSenha.setFont(new Font("Arial", Font.PLAIN, 12));

		campoSenha2.setBounds(114, 247, 200, 25);
		campoSenha2.setBorder(new LineBorder(Color.WHITE));
		campoSenha2.setBackground(new Color(0, 0, 0, 0));
		campoSenha2.setOpaque(false);
		campoSenha2.setForeground(Color.WHITE);
		campoSenha2.setFont(new Font("Arial", Font.PLAIN, 12));

		botaoCadastro.setBounds(164, 297, 100, 25);
		botaoCadastro.setForeground(Color.WHITE);
		botaoCadastro.setBorder(new LineBorder(Color.WHITE));
		botaoCadastro.setFocusable(false);
		botaoCadastro.addActionListener(this);
		botaoCadastro.setContentAreaFilled(false);
		botaoCadastro.setFont(new Font("Arial", Font.BOLD, 12));

		botaoEntrar.setBounds(214, 346, 125, 25);
		botaoEntrar.setForeground(Color.WHITE);
		botaoEntrar.setFocusable(false);
		botaoEntrar.setBorderPainted(false);
		botaoEntrar.setContentAreaFilled(false);
		botaoEntrar.addActionListener(this);
		botaoEntrar.setFont(new Font("Arial", Font.BOLD, 12));

		//// Métodos que reajem com o passar do mouse
		botaoCadastro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoCadastro.setForeground(Color.LIGHT_GRAY);
				botaoCadastro.setBorder(new LineBorder(Color.LIGHT_GRAY));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoCadastro.setForeground(Color.WHITE);
				botaoCadastro.setBorder(new LineBorder(Color.WHITE));
			}
		});

		botaoEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				botaoEntrar.setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				botaoEntrar.setForeground(Color.WHITE);
			}
		});

		// Adicionando os componentes
		frame.add(labelNome);
		frame.add(labelLogin);
		frame.add(labelSenha);
		frame.add(labelSenha2);
		frame.add(labelAviso);
		frame.add(labelMensagem);
		frame.add(labelEntrar);
		frame.add(campoNome);
		frame.add(campoLogin);
		frame.add(campoSenha);
		frame.add(campoSenha2);
		frame.add(botaoCadastro);
		frame.add(botaoEntrar);

		// Propriedades da janela principal
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(440, 440);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Cadastro");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

	}

	// Ações
	@Override
	public void actionPerformed(ActionEvent e) {

		// Botão Cadastrar
		if (e.getSource() == botaoCadastro) {
			String nome = campoNome.getText();
			String login = campoLogin.getText();
			String senha = String.valueOf(campoSenha.getPassword());
			String senha2 = String.valueOf(campoSenha2.getPassword());

			if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || senha2.isEmpty()) {
				labelAviso.setBounds(165, 272, 250, 15);
				labelAviso.setForeground(Color.WHITE);
				labelAviso.setText("Preencha todos os campos!");
				return;
			}

			UsuarioController usuarioController = new UsuarioController();

			boolean loginValido = usuarioController.validaLogin(login);
			if (loginValido == false) {
				labelAviso.setBounds(211, 272, 250, 15);
				labelAviso.setForeground(Color.WHITE);
				labelAviso.setText("Este login já existe!");
				return;
			} else {
				boolean senhaValida = usuarioController.validaSenha(senha, senha2);
				if (senhaValida == false) {
					labelAviso.setBounds(174, 272, 250, 15);
					labelAviso.setForeground(Color.WHITE);
					labelAviso.setText("As senhas não coincidem!");
					return;
				} else {
					usuarioController.cadastraUsuario(nome, login, senha);
					frame.dispose();
					LoginGui loginPage = new LoginGui();
					loginPage.labelAviso.setBounds(124, 252, 250, 15);
					loginPage.labelAviso.setForeground(Color.WHITE);
					loginPage.labelAviso.setText("Usuário cadastrado com sucesso!");
				}
			}
		}

		// Botão Entrar
		if (e.getSource() == botaoEntrar) {
			frame.dispose();
			new LoginGui();
		}

	}

}
