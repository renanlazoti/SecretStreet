package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;

public class Usuarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsuId;
	private JTextField txtUsuNome;
	private JTextField txtUsuLogin;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		setBounds(100, 100, 462, 321);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtUsuId = new JTextField();
		txtUsuId.setEditable(false);
		txtUsuId.setBounds(80, 37, 71, 20);
		contentPanel.add(txtUsuId);
		txtUsuId.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(24, 40, 46, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usu\u00E1rio");
		lblNewLabel_1.setBounds(24, 94, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtUsuNome = new JTextField();
		txtUsuNome.setBounds(77, 91, 204, 20);
		contentPanel.add(txtUsuNome);
		txtUsuNome.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(24, 132, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		txtUsuLogin = new JTextField();
		txtUsuLogin.setBounds(80, 129, 115, 20);
		contentPanel.add(txtUsuLogin);
		txtUsuLogin.setColumns(10);
		
		JLabel txtUsu = new JLabel("Senha");
		txtUsu.setBounds(24, 175, 46, 14);
		contentPanel.add(txtUsu);
		
		JLabel lblNewLabel_5 = new JLabel("Perfil");
		lblNewLabel_5.setBounds(266, 132, 46, 14);
		contentPanel.add(lblNewLabel_5);
		
		cboUsuPerfil_1 = new JComboBox();
		cboUsuPerfil_1.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user"}));
		cboUsuPerfil_1.setBounds(323, 128, 70, 22);
		contentPanel.add(cboUsuPerfil_1);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkSenha_1.isSelected()) {
					alterarUsuarioSenha();
				}else
				adicionarUsuario();
			}
		});
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setFocusTraversalKeysEnabled(false);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(71, 203, 64, 64);
		contentPanel.add(btnAdicionar);
		
		btnAlterar = new JButton("");
		btnAlterar.setContentAreaFilled(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkSenha_1.isSelected()) {
					alterarUsuarioSenha();
				}else
				alterarUsuario();
			}
		});
		btnAlterar.setEnabled(false);
		btnAlterar.setBorderPainted(false);
		btnAlterar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update.png")));
		btnAlterar.setBounds(160, 203, 64, 64);
		contentPanel.add(btnAlterar);
		
		btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
				
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/excluir.png")));
		btnExcluir.setBounds(249, 203, 64, 64);
		contentPanel.add(btnExcluir);
		
		txtUsuSenha = new JPasswordField();
		txtUsuSenha.setBounds(80, 172, 201, 20);
		contentPanel.add(txtUsuSenha);
		
		btnBuscar = new JButton("");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarUsuario();
			}
		});
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/lupa.png")));
		btnBuscar.setBounds(205, 122, 32, 32);
		contentPanel.add(btnBuscar);
		
		RestrictedTextField validarId = new RestrictedTextField(txtUsuId);
		validarId.setOnlyNums(true);
		validarId.setLimit(4);
		// UsuNome
		RestrictedTextField validarNome = new RestrictedTextField(txtUsuNome);

		validarNome.setLimit(50);
		
		// UsuSenha
				RestrictedTextField validarSenha = new RestrictedTextField(txtUsuSenha);
				
				chkSenha_1 = new JCheckBox("Alterar senha");
				chkSenha_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtUsuSenha.setText(null);
						txtUsuSenha.requestFocus();
						txtUsuSenha.setEditable(true);
		
		// UsuLogin
		RestrictedTextField validarLogin = new RestrictedTextField(txtUsuLogin);

		validarLogin.setLimit(15);
		
			}
		});
		chkSenha_1.setVisible(false);
		chkSenha_1.setBounds(300, 171, 125, 23);
		contentPanel.add(chkSenha_1);

		validarSenha.setLimit(255);
		
		
	}
	//fim do construtor
	DAO dao = new DAO();
	private JButton btnAdicionar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JCheckBox chkSenha_1;
	private JPasswordField txtUsuSenha;
	private JButton btnBuscar;
	private JComboBox cboUsuPerfil_1;

	

	/**
	 * Metodo responsavel pela pesquisa de usuario
	 */

	private void pesquisarUsuario() {
		// validação
		if (txtUsuLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuário");
			txtUsuLogin.requestFocus();
		} else {
			// lógica principal
			// Query (instrução SQL)
			String read = "select * from usuarios where login = ?";
			// tratar exceções sempre que lidar com o banco
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da query
				PreparedStatement pst = con.prepareStatement(read);
				// Setar o argumento (id)
				// Substituir o ? pelo conteúdo da caixa de texto
				pst.setString(1, txtUsuLogin.getText());
				// Executar a query e exibir o resultado no formulario
				ResultSet rs = pst.executeQuery();
				// Validação (existência de usuário)
				// rs.next() -> existência de usuário
				limparCampos();
				if (rs.next()) {
					// preencher(setar) os campos do formulário
					txtUsuId.setText(rs.getString(1));
					txtUsuNome.setText(rs.getString(2));
					txtUsuLogin.setText(rs.getString(3));
					cboUsuPerfil_1.setSelectedItem(rs.getString(5));
					txtUsuSenha.setText(rs.getString(4));
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					chkSenha_1.setVisible(true);
					txtUsuSenha.setEditable(false);

				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
					txtUsuNome.setEditable(true);
					cboUsuPerfil_1.setEnabled(true);
					txtUsuSenha.setEditable(true);
					txtUsuNome.requestFocus();
					btnAdicionar.setEnabled(true);
				}
				// NUNCA esquecer de encerrar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Método responsável por adicionar um novo usuário no banco
	 */
	private void adicionarUsuario() {
		// validação da senha (captura segura)
		String capturaSenha = new String(txtUsuSenha.getPassword());

		// validação
		if (txtUsuNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Usuário");
			txtUsuNome.requestFocus();
		} else if (txtUsuLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");
			txtUsuLogin.requestFocus();
		} else if (cboUsuPerfil_1.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione o perfil do usuário");
			cboUsuPerfil_1.requestFocus();
		} else if (txtUsuSenha.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtUsuSenha.requestFocus();
		} else {
			// lógica principal
			String create = "insert into usuarios(usuario,login,senha,perfil) values(?,?,md5(?),?)";
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da query
				PreparedStatement pst = con.prepareStatement(create);
				// Substituir os ???? pelo conteúdo das caixas de texto
				pst.setString(1, txtUsuNome.getText());
				pst.setString(2, txtUsuLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboUsuPerfil_1.getSelectedItem().toString());
				// Executar a query e inserir o usuário no banco
				pst.executeUpdate();
				// Encerrar a conexão
				JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso");
				limparCampos();
				con.close();
			} catch (SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login em uso.\nEscolha outro login.");
				txtUsuLogin.setText(null);
				txtUsuLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	/**
	 * Método responsável por alterar os dados de um usuário no banco exceto a senha
	 */
	private void alterarUsuario() {
		// validação
		if (txtUsuNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Usuário");
			txtUsuNome.requestFocus();
		} else if (txtUsuLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");
			txtUsuLogin.requestFocus();
		} else if (cboUsuPerfil_1.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione o perfil do usuário");
			cboUsuPerfil_1.requestFocus();
		} else if (txtUsuSenha.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtUsuSenha.requestFocus();
		} else {
			// lógica principal
			String update = "update usuarios set usuario=?, login=?, perfil=? where idusu=?";
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da query
				PreparedStatement pst = con.prepareStatement(update);
				// Substituir os ????? pelo conteúdo das caixas de texto
				pst.setString(1, txtUsuNome.getText());
				pst.setString(2, txtUsuLogin.getText());
				pst.setString(3, cboUsuPerfil_1.getSelectedItem().toString());
				pst.setString(4, txtUsuId.getText());
				// Executar a query e inserir o usuário no banco
				pst.executeUpdate();
				// Encerrar a conexão
				JOptionPane.showMessageDialog(null, "Dados do usuário exceto senha, alterados com sucesso");
				limparCampos();
				con.close();
			} catch (SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login em uso.\nEscolha outro login.");
				txtUsuLogin.setText(null);
				txtUsuLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Método responsável por alterar os dados de um usuário e sua respectiva senha no banco
	 */
	private void alterarUsuarioSenha() {
		// validação da senha (captura segura)
		String capturaSenha = new String(txtUsuSenha.getPassword());
		// validação
		if (txtUsuNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Usuário");
			txtUsuNome.requestFocus();
		} else if (txtUsuLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");
			txtUsuLogin.requestFocus();
		} else if (cboUsuPerfil_1.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione o perfil do usuário");
			cboUsuPerfil_1.requestFocus();
		} else if (txtUsuSenha.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtUsuSenha.requestFocus();
		} else {
			// lógica principal
			String update2 = "update usuarios set usuario=?, login=?,senha=md5(?), perfil=? where idusu=?";
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da query
				PreparedStatement pst = con.prepareStatement(update2);
				// Substituir os ????? pelo conteúdo das caixas de texto
				pst.setString(1, txtUsuNome.getText());
				pst.setString(2, txtUsuLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboUsuPerfil_1.getSelectedItem().toString());
				pst.setString(5, txtUsuId.getText());
				// Executar a query e inserir o usuário no banco
				pst.executeUpdate();
				// Encerrar a conexão
				JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso");
				limparCampos();
				con.close();
			} catch (SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login em uso.\nEscolha outro login.");
				txtUsuLogin.setText(null);
				txtUsuLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Método responsável por excluir um usuário do banco
	 */
	private void excluirUsuario() {
		// validação (confirmação de exclusão)
		int confirma = JOptionPane.showConfirmDialog(null, "Confima a exclusão do usuário?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// lógica principal
			String delete = "delete from usuarios where idusu=?";
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a query(comando sql) substituindo a ? pelo idusu
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtUsuId.getText());
				// Executar a query
				pst.executeUpdate();
				// confimação
				limparCampos();
				JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso.");
				// encerrar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Método usado para limpar e validar os campos e objetos do formulário
	 */
	private void limparCampos() {
		txtUsuId.setText(null);
		txtUsuNome.setText(null);
		txtUsuLogin.setText(null);
		txtUsuSenha.setEditable(true);
		txtUsuSenha.setText(null);
		cboUsuPerfil_1.setSelectedItem("");
		btnAdicionar.setEnabled(false);
		btnAlterar.setEnabled(false);
		btnExcluir.setEnabled(false);
		chkSenha_1.setSelected(false);
	}
}// fim do código