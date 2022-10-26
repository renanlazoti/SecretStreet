package View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;

public class Clientes extends JDialog {
	private JTextField txtPesquisarClientes;
	private JTextField txtCliId;
	private JTextField txtCliNome;
	private JTextField txtCliFone;
	private JTextField txtCliCPF;
	private JTextField txtCliEmail;
	private JTextField txtCliCEP;
	private JTextField txtCliEndereco;
	private JTextField txtCliNumero;
	private JTextField txtCliComplemento;
	private JTextField txtCliBairro;
	private JTextField txtCliCidade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Clientes dialog = new Clientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Clientes() {
		setTitle("Cliente");
		setBounds(100, 100, 839, 462);
		getContentPane().setLayout(null);
		
		txtPesquisarClientes = new JTextField();
		txtPesquisarClientes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarClientesTabela();
			}
		});
		txtPesquisarClientes.setColumns(10);
		txtPesquisarClientes.setBounds(90, 25, 262, 20);
		getContentPane().add(txtPesquisarClientes);
		
		JLabel lblNewLabel_14 = new JLabel("Cliente");
		lblNewLabel_14.setBounds(40, 28, 46, 14);
		getContentPane().add(lblNewLabel_14);
		
		btnPesquisar = new JButton("buscar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarClientes();
			}
		});
		btnPesquisar.setToolTipText("Buscar");
		btnPesquisar.setBounds(371, 24, 89, 23);
		getContentPane().add(btnPesquisar);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(573, 28, 46, 14);
		getContentPane().add(lblNewLabel);
		
		txtCliId = new JTextField();
		txtCliId.setEnabled(false);
		txtCliId.setEditable(false);
		txtCliId.setColumns(10);
		txtCliId.setBounds(599, 25, 86, 20);
		getContentPane().add(txtCliId);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 58, 744, 74);
		getContentPane().add(scrollPane);
		
		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCaixasTexto();
			}
		});
		scrollPane.setViewportView(tblClientes);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(40, 143, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtCliNome = new JTextField();
		txtCliNome.setColumns(10);
		txtCliNome.setBounds(77, 140, 275, 20);
		getContentPane().add(txtCliNome);
		
		JLabel lblNewLabel_2 = new JLabel("Data de nascimento");
		lblNewLabel_2.setBounds(373, 143, 165, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtCliFone = new JTextField();
		txtCliFone.setColumns(10);
		txtCliFone.setBounds(657, 140, 127, 20);
		getContentPane().add(txtCliFone);
		
		JLabel lblNewLabel_3 = new JLabel("Fone");
		lblNewLabel_3.setBounds(625, 143, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		txtCliCPF = new JTextField();
		txtCliCPF.setColumns(10);
		txtCliCPF.setBounds(66, 180, 165, 20);
		getContentPane().add(txtCliCPF);
		
		JLabel lblNewLabel_4 = new JLabel("CPF");
		lblNewLabel_4.setBounds(40, 183, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("E-mail");
		lblNewLabel_5.setBounds(252, 183, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		txtCliEmail = new JTextField();
		txtCliEmail.setColumns(10);
		txtCliEmail.setBounds(292, 180, 358, 20);
		getContentPane().add(txtCliEmail);
		
		JLabel lblNewLabel_6 = new JLabel("Marketing");
		lblNewLabel_6.setBounds(672, 183, 94, 14);
		getContentPane().add(lblNewLabel_6);
		
		cboCliMkt = new JComboBox();
		cboCliMkt.setModel(new DefaultComboBoxModel(new String[] {"", "SIM", "N\u00C3O"}));
		cboCliMkt.setBounds(733, 179, 51, 22);
		getContentPane().add(cboCliMkt);
		
		txtCliCEP = new JTextField();
		txtCliCEP.setColumns(10);
		txtCliCEP.setBounds(66, 220, 99, 20);
		getContentPane().add(txtCliCEP);
		
		JLabel lblNewLabel_7 = new JLabel("CEP");
		lblNewLabel_7.setBounds(40, 223, 46, 14);
		getContentPane().add(lblNewLabel_7);
		
		btnCliBuscarCep = new JButton("Buscar CEP");
		btnCliBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCEP();
			}
		});
		btnCliBuscarCep.setToolTipText("Buscar CEP");
		btnCliBuscarCep.setBounds(175, 219, 123, 23);
		getContentPane().add(btnCliBuscarCep);
		
		JLabel lblNewLabel_8 = new JLabel("Endere\u00E7o");
		lblNewLabel_8.setBounds(318, 223, 66, 14);
		getContentPane().add(lblNewLabel_8);
		
		txtCliEndereco = new JTextField();
		txtCliEndereco.setColumns(10);
		txtCliEndereco.setBounds(376, 220, 408, 20);
		getContentPane().add(txtCliEndereco);
		
		txtCliNumero = new JTextField();
		txtCliNumero.setColumns(10);
		txtCliNumero.setBounds(90, 259, 75, 20);
		getContentPane().add(txtCliNumero);
		
		JLabel lblNewLabel_9 = new JLabel("N\u00FAmero");
		lblNewLabel_9.setBounds(40, 262, 46, 14);
		getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Complemento");
		lblNewLabel_10.setBounds(185, 262, 94, 14);
		getContentPane().add(lblNewLabel_10);
		
		txtCliComplemento = new JTextField();
		txtCliComplemento.setColumns(10);
		txtCliComplemento.setBounds(272, 259, 188, 20);
		getContentPane().add(txtCliComplemento);
		
		txtCliBairro = new JTextField();
		txtCliBairro.setColumns(10);
		txtCliBairro.setBounds(515, 259, 269, 20);
		getContentPane().add(txtCliBairro);
		
		JLabel lblNewLabel_11 = new JLabel("Bairro");
		lblNewLabel_11.setBounds(477, 262, 46, 14);
		getContentPane().add(lblNewLabel_11);
		
		txtCliCidade = new JTextField();
		txtCliCidade.setColumns(10);
		txtCliCidade.setBounds(84, 304, 358, 20);
		getContentPane().add(txtCliCidade);
		
		JLabel lblNewLabel_12 = new JLabel("Cidade");
		lblNewLabel_12.setBounds(40, 307, 46, 14);
		getContentPane().add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("UF");
		lblNewLabel_13.setBounds(462, 307, 46, 14);
		getContentPane().add(lblNewLabel_13);
		
		cboCliUF = new JComboBox();
		cboCliUF.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboCliUF.setBounds(501, 303, 56, 22);
		getContentPane().add(cboCliUF);
		
		btnCliAdicionar = new JButton("");
		btnCliAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarClientes();
			}
		});
		btnCliAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/create.png")));
		btnCliAdicionar.setToolTipText("Adicionar");
		btnCliAdicionar.setEnabled(false);
		btnCliAdicionar.setContentAreaFilled(false);
		btnCliAdicionar.setBorderPainted(false);
		btnCliAdicionar.setBounds(292, 337, 64, 64);
		getContentPane().add(btnCliAdicionar);
		
		btnCliAlterar = new JButton("");
		btnCliAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarCliente();
			}
		});
		btnCliAlterar.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnCliAlterar.setToolTipText("Alterar");
		btnCliAlterar.setEnabled(false);
		btnCliAlterar.setContentAreaFilled(false);
		btnCliAlterar.setBorderPainted(false);
		btnCliAlterar.setBounds(360, 337, 64, 64);
		getContentPane().add(btnCliAlterar);
		
		btnCliExcluir = new JButton("");
		btnCliExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		btnCliExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/excluir.png")));
		btnCliExcluir.setToolTipText("Excluir");
		btnCliExcluir.setEnabled(false);
		btnCliExcluir.setContentAreaFilled(false);
		btnCliExcluir.setBorderPainted(false);
		btnCliExcluir.setBounds(434, 337, 64, 64);
		getContentPane().add(btnCliExcluir);
		
		// Validação com o uso da biblioteca Atxy2k
		// txtCliNome
		RestrictedTextField validarNome = new RestrictedTextField(txtCliNome);
		validarNome.setLimit(50);
		// txtCliFone
		RestrictedTextField validarFone = new RestrictedTextField(txtCliFone);
		validarFone.setOnlyNums(true);
		validarFone.setLimit(20);
		// txtCliCPF
		RestrictedTextField validarCPF = new RestrictedTextField(txtCliCPF);
		validarCPF.setOnlyNums(true);
		validarCPF.setLimit(15);
		// txtCliEmail
		RestrictedTextField validarEmail = new RestrictedTextField(txtCliEmail);
		validarEmail.setLimit(50);
		// txtCliCEP
		RestrictedTextField validarCEP = new RestrictedTextField(txtCliCEP);
		validarCEP.setOnlyNums(true);
		validarCEP.setLimit(10);
		// txtCliEndereco
		RestrictedTextField validarEndereco = new RestrictedTextField(txtCliEndereco);
		validarEndereco.setLimit(50);
		// txtCliNumero
		RestrictedTextField validarNumero = new RestrictedTextField(txtCliNumero);
		validarNumero.setOnlyNums(true);
		validarNumero.setLimit(10);
		// txtCliComplemento
		RestrictedTextField validarComplemento = new RestrictedTextField(txtCliComplemento);
		validarComplemento.setLimit(100);
		// txtCliBairro
		RestrictedTextField validarBairro = new RestrictedTextField(txtCliBairro);
		validarBairro.setLimit(50);
		// txtCliCidade
		RestrictedTextField validarCidade = new RestrictedTextField(txtCliCidade);
		validarCidade.setLimit(50);

		getRootPane().setDefaultButton(btnPesquisar);
		
		txtCliNascimento = new JDateChooser();
		txtCliNascimento.setBounds(485, 143, 108, 20);
		getContentPane().add(txtCliNascimento);
		
	} // Fim do construtor
	
	DAO dao = new DAO();
	private JComboBox cboCliMkt;
	private JComboBox cboCliUF;
	private JButton btnCliAdicionar;
	private JButton btnCliAlterar;
	private JButton btnCliExcluir;
	private JScrollPane scrollPane;
	private JTable tblClientes;
	private JButton btnPesquisar;
	private JButton btnCliBuscarCep;
	private JDateChooser txtCliNascimento;
	
	/**
	 * Método responsável pela pesquisa avançada do cliente usando o nome e a
	 * biblioteca rs2xml
	 */

	private void pesquisarClientesTabela() {
		String readT = "select idcli as ID, nome as Cliente, fone as Telefone, cpf as CPF from clientes where nome like ?";
		try {
			// Estabelecer a conexão
			Connection con = dao.conectar();
			// Preparar a execução da Query
			PreparedStatement pst = con.prepareStatement(readT);
			// Setar o argumento (fantasia)
			// Substituir o ? pelo conteúdo da caixa de texto
			pst.setString(1, txtPesquisarClientes.getText() + "%");
			ResultSet rs = pst.executeQuery();
			rs = pst.executeQuery();
			// uso da biblioteca rs2xml para "popular" a tabela
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Método responsável por setar os campos da tabela de acordo com a tabela
	 */

	private void setarCaixasTexto() {
		int setar = tblClientes.getSelectedRow();
		txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
		txtPesquisarClientes.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
		txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
		txtCliCPF.setText(tblClientes.getModel().getValueAt(setar, 3).toString());

	}

	
	/**
	 * Método responsável por setar os campos da tabela de acordo com o id dos
	 * clientes
	 */

	private void pesquisarClientes() {
		if (txtPesquisarClientes.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			txtPesquisarClientes.requestFocus();
		} else {
			// lógica principal
			// query principal ( Instrução SQL)
			String read = "select * from clientes where nome = ?";
			// tratar excessões sempre que lidar com o banco
			try {
				// estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da Query
				PreparedStatement pst = con.prepareStatement(read);
				// Substituir o ? pelo conteúdo da caixa de texto
				pst.setString(1, txtPesquisarClientes.getText());
				// Executar a query e exibir o resultado no formulário
				ResultSet rs = pst.executeQuery();
				// Validação (existência de clientes)
				// rs.next() -> existência de clientes
				limparCampos();
				if (rs.next()) {
					// preencher(setar) os campos do formulario
					txtCliId.setText(rs.getString(1));
					txtCliNome.setText(rs.getString(2));
						String setarDataCad = rs.getString(3);
						Date dataVal = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataCad);
						txtCliNascimento.setDate(dataVal);
					txtCliFone.setText(rs.getString(4));
					txtCliCPF.setText(rs.getString(5));
					txtCliEmail.setText(rs.getString(6));
					cboCliMkt.setSelectedItem(rs.getString(7));
					txtCliCEP.setText(rs.getString(8));
					txtCliEndereco.setText(rs.getString(9));
					txtCliNumero.setText(rs.getString(10));
					txtCliComplemento.setText(rs.getString(11));
					txtCliBairro.setText(rs.getString(12));
					txtCliCidade.setText(rs.getString(13));
					cboCliUF.setSelectedItem(rs.getString(14));

					btnCliAlterar.setEnabled(true);
					btnCliExcluir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Cliente não cadastrado");
					limparCampos();
					btnCliAdicionar.setEnabled(true);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	/**
	 * Método responsável por adicionar um cliente ao banco
	 */

	private void adicionarClientes() {
	
		if (txtCliNascimento.getDate()==null) {
			JOptionPane.showMessageDialog(null, "Informe a data de nascimento do cliente");
			txtCliNascimento.requestFocus();
		} else {
		SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
		String dataVal = formatador.format(txtCliNascimento.getDate());
		if (txtCliNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o nome do cliente");
			txtCliNome.requestFocus();		
		} else if (txtCliFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o telefone do cliente");
			txtCliFone.requestFocus();
		} else if (txtCliCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o CPF do cliente");
			txtCliCPF.requestFocus();
		} else if (cboCliMkt.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe se o cliente quer Marketing");
			cboCliMkt.requestFocus();
		} else {
			// lógica principal
			String create = "insert into clientes(nome,nascimento,fone,cpf,email,marketing,cep,endereco,numero,complemento,bairro,cidade,uf) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da Query
				PreparedStatement pst = con.prepareStatement(create);
				// Substituir o ? pelo conteúdo da caixa de texto
				pst.setString(1, txtCliNome.getText());
				pst.setString(2, dataVal);	
				pst.setString(3, txtCliFone.getText());
				pst.setString(4, txtCliCPF.getText());
				pst.setString(5, txtCliEmail.getText());
				pst.setString(6, cboCliMkt.getSelectedItem().toString());
				pst.setString(7, txtCliCEP.getText());
				pst.setString(8, txtCliEndereco.getText());
				pst.setString(9, txtCliNumero.getText());
				pst.setString(10, txtCliComplemento.getText());
				pst.setString(11, txtCliBairro.getText());
				pst.setString(12, txtCliCidade.getText());
				pst.setString(13, cboCliUF.getSelectedItem().toString());
				// Executar a query e inserir o cliente no banco
				pst.executeUpdate();
				// confirmação
				JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
				// limpar campos
				limparCampos();
				limparCamposClientes();
				// Encerrar a conexão
				con.close();
			} catch (SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "CPF em uso.\nDigite outro CPF");
				txtCliCPF.setText(null);
				txtCliCPF.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		}
	}
	
	
	/**
	 * Método responsavel por alterar os dados de um cliente do banco
	 */

	private void alterarCliente() {
		// validação
		
		if (txtCliNascimento.getDate()==null) {
			JOptionPane.showMessageDialog(null, "Informe a data de nascimento do cliente");
			txtCliNascimento.requestFocus();
		} else {
		SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
		String dataVal = formatador.format(txtCliNascimento.getDate());
		if (txtCliNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o nome");
			txtCliNome.requestFocus();
		} else if (txtCliNascimento.getCalendar().equals(null)) {
			JOptionPane.showMessageDialog(null, "Informe a data de nascimento do cliente");
			txtCliNascimento.requestFocus();
		} else if (txtCliFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o telefone do cliente");
			txtCliFone.requestFocus();
		} else if (txtCliCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o CPF do cliente");
			txtCliCPF.requestFocus();
		} else if (cboCliMkt.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe se o cliente quer Marketing");
			cboCliMkt.requestFocus();
		} else {
			// lógica principal
			String update = "update clientes set nome=?, nascimento=?, fone=?, cpf=?, email=?, marketing=?, cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, uf=? where idCli=?";
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da Query
				PreparedStatement pst = con.prepareStatement(update);
				// Substituir o ? pelo conteúdo da caixa de texto
				pst.setString(1, txtCliNome.getText());
				pst.setString(2, dataVal);
				pst.setString(3, txtCliFone.getText());
				pst.setString(4, txtCliCPF.getText());
				pst.setString(5, txtCliEmail.getText());
				pst.setString(6, cboCliMkt.getSelectedItem().toString());
				pst.setString(7, txtCliCEP.getText());
				pst.setString(8, txtCliEndereco.getText());
				pst.setString(9, txtCliNumero.getText());
				pst.setString(10, txtCliComplemento.getText());
				pst.setString(11, txtCliBairro.getText());
				pst.setString(12, txtCliCidade.getText());
				pst.setString(13, cboCliUF.getSelectedItem().toString());
				pst.setString(14, txtCliId.getText());
				// Executar a query e alterar o cliente no banco
				pst.executeUpdate();
				// confirmação
				JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
				limparCampos();
				limparCamposClientes();
				// Encerrar a conexão
				con.close();
			} catch (SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "CPF em uso.\nEscolha outro CPF");
				txtCliCPF.setText(null);
				txtCliCPF.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		}
	}

	/**
	 * Método responsavel por excluir um cliente do banco
	 */

	private void excluirCliente() {
		// validação (confirmação da exclusão)
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do cliente?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where idCli=?";
			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar a execução da Query
				PreparedStatement pst = con.prepareStatement(delete);
				// Substituir o ? pelo conteúdo da caixa de texto
				pst.setString(1, txtCliId.getText());
				// Executar a query e excluir o cliente do banco
				pst.executeUpdate();
				// confirmação
				JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso");
				limparCampos();
				limparCamposClientes();
				// Encerrar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	/**
	 * Método responsavel por buscar CEP
	 */
	private void buscarCEP() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCliCEP.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCliCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtCliBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboCliUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {

					} else {
						JOptionPane.showMessageDialog(null, "CEP nÃ£o encontrado");
					}
				}

			}
			// Setar Campo Endereço
			txtCliEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * Método responsável por limpar a tabela ao setar os campos
	 * na tabela
	 */
	
	private void limparCamposClientes() {
		// limpar tabela
		((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
	}

	/**
	 * Método responsavel por Limpar campos
	 */
	
	private void limparCampos() {
		txtCliId.setText(null);
		txtCliNome.setText(null);
		txtCliNascimento.setDate(null);
		txtCliFone.setText(null);
		txtCliCPF.setText(null);
		txtCliEmail.setText(null);
		cboCliMkt.setSelectedItem("");
		txtCliCEP.setText(null);
		txtCliEndereco.setText(null);
		txtCliNumero.setText(null);
		txtCliComplemento.setText(null);
		txtCliBairro.setText(null);
		txtCliCidade.setText(null);
		cboCliUF.setSelectedItem("");
		btnCliAdicionar.setEnabled(false);
		btnCliAlterar.setEnabled(false);
		btnCliExcluir.setEnabled(false);
		txtPesquisarClientes.setText(null);
		
	}
} // Fim do código
