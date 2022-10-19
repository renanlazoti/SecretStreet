package View;

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
		setTitle("Login");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(190, 43, 55, 28);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(100, 110, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Senha");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(100, 149, 46, 14);
		contentPane.add(lblNewLabel_1_1);

		txtLogin = new JTextField();
		txtLogin.setBounds(156, 107, 117, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/databaseoff.png")));
		lblStatus.setBounds(342, 182, 64, 64);
		contentPane.add(lblStatus);

		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEntrar.setBounds(171, 178, 89, 23);
		contentPane.add(btnEntrar);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(156, 146, 117, 20);
		contentPane.add(txtSenha);
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
						// principal.btnRelatorios.setEnabled(true);
					// principal.btnUsuarios.setEnabled(true);
						// principal.panelUsuario.setBackground(Color.RED);
						// principal.lblUsuario.setText("Usuário: " + rs.getString(2));

						this.dispose();
					} else {

						//principal.setVisible(true);

						//principal.lblUsuario.setText("Usuário: " + rs.getString(2));

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
