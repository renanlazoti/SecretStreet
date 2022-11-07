package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.Cursor;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/CADEADO SS PRONTO prenchido.png")));
		setTitle("Login");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 366);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(321, 79, 113, 28);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_1.setBounds(166, 128, 127, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Senha");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(166, 167, 117, 14);
		contentPane.add(lblNewLabel_1_1);

		txtLogin = new JTextField();
		txtLogin.setBackground(SystemColor.menu);
		txtLogin.setBounds(260, 125, 183, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/databaseoff.png")));
		lblStatus.setBounds(34, 231, 64, 64);
		contentPane.add(lblStatus);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEntrar.setContentAreaFilled(false);
		btnEntrar.setBackground(SystemColor.menu);
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEntrar.setBounds(261, 208, 182, 23);
		contentPane.add(btnEntrar);
		
		txtSenha = new JPasswordField();
		txtSenha.setBackground(SystemColor.menu);
		txtSenha.setBounds(260, 164, 183, 20);
		contentPane.add(txtSenha);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setEnabled(false);
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/img/CADEADO SS PRONTO.png")));
		lblNewLabel_2.setBounds(324, 115, 372, 362);
		contentPane.add(lblNewLabel_2);
	} // fim do construtor

	DAO dao = new DAO();
	private JLabel lblStatus;
	private JPasswordField txtSenha;
	private JButton btnEntrar;

	private void status() {
		try {
			Connection con = dao.conectar();
			if (con == null) {		
	     		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/databaseoff.png")));
			} else {		
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/databaseon.png")));
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void logar() {

		String capturaSenha = new String(txtSenha.getPassword());

		// validação
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Insira o login");
			txtLogin.requestFocus();
		} else if (txtSenha.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Insira a senha");
			txtSenha.requestFocus();
		} else {

			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(read);

				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					String perfil = rs.getString(5);
					 Principal principal = new Principal();
					if (perfil.equals("admin")) {
					 principal.setVisible(true);
						principal.lblUsuario.setText("Usuário: " + rs.getString(2));
						principal.lblPerfil.setText("Login: " + rs.getString(5));

						this.dispose();
					} else {

						principal.setVisible(true);
						// setar o nome do usuario na tela principal
						principal.lblUsuario.setText("Usuário: " + rs.getString(2));
						principal.lblPerfil.setText("Login: " + rs.getString(5));
						principal.panelUsuario.setBackground(Color.black);
						// fechar a tela de login 
						con.close();
						this.dispose();
					}

					con.close();

				} else {
					JOptionPane.showMessageDialog(null, "Login e/ou senha inválido(s)");
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
} // fim do código
