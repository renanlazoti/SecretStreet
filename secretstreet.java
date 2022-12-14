package View;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;

public class Fornecedores extends JDialog {
	private JTextField txtForPesquisarFornecedor;
	private JTextField txtForFantasia;
	private JTextField txtForIE;
	private JTextField txtForID;
	private JTextField txtForCNPJ;
	private JTextField txtForRazao;
	private JTextField txtForIM;
	private JTextField txtForFone;
	private JTextField txtForContato;
	private JTextField txtForEmail;
	private JTextField txtForCEP;
	private JTextField txtForEndereco;
	private JTextField txtForNumero;
	private JTextField txtForComplemento;
	private JTextField txtForBairro;
	private JTextField txtForCidade;
	private JButton btnBuscarCep;
	private JButton btnPesquisar;
	private JComboBox cboForUF;
	private JTable tblFornecedor;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnAlterar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Fornecedores dialog = new Fornecedores();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Fornecedores() {
		setBounds(100, 100, 825, 601);
		getContentPane().setLayout(null);
		
		JLabel lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setBounds(45, 43, 69, 14);
		getContentPane().add(lblFornecedor);
		
		txtForPesquisarFornecedor = new JTextField();
		txtForPesquisarFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedorTabela();
			}
		});
		txtForPesquisarFornecedor.setColumns(10);
		txtForPesquisarFornecedor.setBounds(130, 40, 140, 20);
		getContentPane().add(txtForPesquisarFornecedor);
		
		btnPesquisar = new JButton("Buscar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarFornecedor();
			}
		});
		btnPesquisar.setBounds(292, 39, 89, 23);
		getContentPane().add(btnPesquisar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 68, 595, 84);
		getContentPane().add(scrollPane);
		
		tblFornecedor = new JTable();
		tblFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limparCampos();
				setarCaixasTexto();
				limparCamposFornecedor();
			}
		});
		scrollPane.setViewportView(tblFornecedor);
		
		JLabel lblIm = new JLabel("Fantasia");
		lblIm.setBounds(45, 207, 81, 14);
		getContentPane().add(lblIm);
		
		txtForFantasia = new JTextField();
		txtForFantasia.setColumns(10);
		txtForFantasia.setBounds(130, 204, 170, 20);
		getContentPane().add(txtForFantasia);
		
		JLabel lblIe_1 = new JLabel("I.E");
		lblIe_1.setBounds(45, 235, 49, 14);
		getContentPane().add(lblIe_1);
		
		txtForIE = new JTextField();
		txtForIE.setColumns(10);
		txtForIE.setBounds(130, 232, 170, 20);
		getContentPane().add(txtForIE);
		
		JLabel lblIe_1_1 = new JLabel("ID");
		lblIe_1_1.setBounds(405, 43, 32, 14);
		getContentPane().add(lblIe_1_1);
		
		txtForID = new JTextField();
		txtForID.setEditable(false);
		txtForID.setColumns(10);
		txtForID.setBounds(439, 40, 55, 20);
		getContentPane().add(txtForID);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(45, 179, 69, 14);
		getContentPane().add(lblCnpj);
		
		txtForCNPJ = new JTextField();
		txtForCNPJ.setColumns(10);
		txtForCNPJ.setBounds(130, 176, 170, 20);
		getContentPane().add(txtForCNPJ);
		
		JLabel lblRazao = new JLabel("Raz\u00E3o");
		lblRazao.setBounds(311, 179, 49, 14);
		getContentPane().add(lblRazao);
		
		txtForRazao = new JTextField();
		txtForRazao.setColumns(10);
		txtForRazao.setBounds(366, 176, 385, 20);
		getContentPane().add(txtForRazao);
		
		JLabel lblIe = new JLabel("I.M");
		lblIe.setBounds(45, 263, 69, 14);
		getContentPane().add(lblIe);
		
		txtForIM = new JTextField();
		txtForIM.setColumns(10);
		txtForIM.setBounds(130, 263, 170, 20);
		getContentPane().add(txtForIM);
		
		JLabel lblRazo = new JLabel("Fone");
		lblRazo.setBounds(45, 294, 69, 14);
		getContentPane().add(lblRazo);
		
		txtForFone = new JTextField();
		txtForFone.setColumns(10);
		txtForFone.setBounds(130, 291, 170, 20);
		getContentPane().add(txtForFone);
		
		JLabel lblContato = new JLabel("Contato");
		lblContato.setBounds(310, 207, 49, 14);
		getContentPane().add(lblContato);
		
		txtForContato = new JTextField();
		txtForContato.setColumns(10);
		txtForContato.setBounds(366, 204, 140, 20);
		getContentPane().add(txtForContato);
		
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(311, 235, 70, 14);
		getContentPane().add(lblEmail);
		
		txtForEmail = new JTextField();
		txtForEmail.setColumns(10);
		txtForEmail.setBounds(366, 232, 210, 20);
		getContentPane().add(txtForEmail);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setBounds(45, 360, 49, 14);
		getContentPane().add(lblCep);
		
		txtForCEP = new JTextField();
		txtForCEP.setColumns(10);
		txtForCEP.setBounds(117, 357, 153, 20);
		getContentPane().add(txtForCEP);
		
		btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtForCEP.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o CEP");
					txtForCEP.requestFocus();
				} else {
					buscarCEP();
				}
			}
		});
		btnBuscarCep.setEnabled(false);
		btnBuscarCep.setBounds(292, 356, 140, 23);
		getContentPane().add(btnBuscarCep);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setBounds(45, 390, 69, 14);
		getContentPane().add(lblEndereco);
		
		txtForEndereco = new JTextField();
		txtForEndereco.setColumns(10);
		txtForEndereco.setBounds(117, 387, 220, 20);
		getContentPane().add(txtForEndereco);
		
		JLabel lblNumero = new JLabel("N\u00FAmero");
		lblNumero.setBounds(347, 387, 49, 14);
		getContentPane().add(lblNumero);
		
		txtForNumero = new JTextField();
		txtForNumero.setColumns(10);
		txtForNumero.setBounds(406, 384, 140, 20);
		getContentPane().add(txtForNumero);
		
		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setBounds(45, 418, 81, 14);
		getContentPane().add(lblComplemento);
		
		txtForComplemento = new JTextField();
		txtForComplemento.setColumns(10);
		txtForComplemento.setBounds(130, 415, 207, 20);
		getContentPane().add(txtForComplemento);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(347, 415, 44, 14);
		getContentPane().add(lblBairro);
		
		txtForBairro = new JTextField();
		txtForBairro.setColumns(10);
		txtForBairro.setBounds(406, 412, 170, 20);
		getContentPane().add(txtForBairro);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(45, 446, 69, 14);
		getContentPane().add(lblCidade);
		
		txtForCidade = new JTextField();
		txtForCidade.setColumns(10);
		txtForCidade.setBounds(117, 443, 183, 20);
		getContentPane().add(txtForCidade);
		
		JLabel lblUf = new JLabel("U.F");
		lblUf.setBounds(347, 444, 44, 14);
		getContentPane().add(lblUf);
		
		cboForUF = new JComboBox();
		cboForUF.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboForUF.setBounds(406, 441, 57, 22);
		getContentPane().add(cboForUF);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarFornecedor();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/create.png")));
		btnAdicionar.setToolTipText("Adicionar Fornecedor");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setDefaultCapable(false);
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBounds(82, 487, 64, 64);
		getContentPane().add(btnAdicionar);
		
		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirFornecedor();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/excluir.png")));
		btnExcluir.setToolTipText("Remover Fornecedor");
		btnExcluir.setEnabled(false);
		btnExcluir.setDefaultCapable(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(182, 487, 64, 64);
		getContentPane().add(btnExcluir);
		
		btnAlterar = new JButton("");
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarFornecedor();
			}
		});
		btnAlterar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/update.png")));
		btnAlterar.setToolTipText("Alterar Fornecedor");
		btnAlterar.setEnabled(false);
		btnAlterar.setDefaultCapable(false);
		btnAlterar.setContentAreaFilled(false);
		btnAlterar.setBorderPainted(false);
		btnAlterar.setBounds(273, 487, 64, 64);
		getContentPane().add(btnAlterar);
		
		// Valida??o com o uso da biblioteca Atxy2k
		RestrictedTextField validarId = new RestrictedTextField(txtForID);
		validarId.setOnlyNums(true);
		validarId.setLimit(4);
		RestrictedTextField validarCNPJ = new RestrictedTextField(txtForCNPJ);
		validarCNPJ.setOnlyNums(true);
		validarCNPJ.setLimit(14);
		RestrictedTextField validarIE = new RestrictedTextField(txtForIE);
		validarIE.setOnlyNums(true);
		validarIE.setLimit(14);
		RestrictedTextField validarIM = new RestrictedTextField(txtForIM);
		validarIM.setOnlyNums(true);
		validarIM.setLimit(14);
		RestrictedTextField validarRazao = new RestrictedTextField(txtForRazao);
		validarRazao.setLimit(50);
		RestrictedTextField validarFantasia = new RestrictedTextField(txtForFantasia);
		validarFantasia.setLimit(60);
		RestrictedTextField validarFone = new RestrictedTextField(txtForFone);
		validarFone.setOnlyNums(true);
		validarFone.setLimit(12);
		RestrictedTextField validarContato = new RestrictedTextField(txtForContato);
		validarContato.setLimit(50);
		RestrictedTextField validarEmail = new RestrictedTextField(txtForEmail);
		validarEmail.setLimit(50);
		RestrictedTextField validarCEP = new RestrictedTextField(txtForCEP);
		validarCEP.setOnlyNums(true);
		validarCEP.setLimit(8);
		RestrictedTextField validarEndereco = new RestrictedTextField(txtForEndereco);
		validarEndereco.setLimit(100);
		RestrictedTextField validarNumero = new RestrictedTextField(txtForNumero);
		validarNumero.setOnlyNums(true);
		validarNumero.setLimit(10);
		RestrictedTextField validarComplemento = new RestrictedTextField(txtForComplemento);
		validarComplemento.setLimit(100);
		RestrictedTextField validarBairro = new RestrictedTextField(txtForBairro);
		validarBairro.setLimit(50);
		RestrictedTextField validarCidade = new RestrictedTextField(txtForCidade);
		validarCidade.setLimit(50);

		getRootPane().setDefaultButton(btnPesquisar);
	}// Fim do construtor
	
	DAO dao = new DAO();
	
	/**
	 * M?todo respons?vel por buscar o CEP
	 */
	private void buscarCEP() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtForCEP.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
		        Element element = it.next();
		        if (element.getQualifiedName().equals("cidade")) {
		        	txtForCidade.setText(element.getText());
		        }
		        if (element.getQualifiedName().equals("bairro")) {
		        	txtForBairro.setText(element.getText());
		        }
		        if (element.getQualifiedName().equals("uf")) {
		        	cboForUF.setSelectedItem(element.getText()); 
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
		        		JOptionPane.showMessageDialog(null, "CEP n?o encontrado");
		        	}
		        }

		    }
			// Setar Campo Endere?o
			txtForEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * M?todo respons?vel pela pesquisa avan?ada do Fornecedor
	 * usando o nome de fantasia e a biblioteca rs2xml
	 */
	private void pesquisarFornecedorTabela() {
		String readT = "select idfor as ID, fantasia as Fornecedor, fone as Fone, contato as Contato from fornecedores where fantasia like ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(readT);
			pst.setString(1, txtForPesquisarFornecedor.getText() + "%");
			ResultSet rs = pst.executeQuery();
			tblFornecedor.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * M?todo respons?vel por setar as caixas de texto
	 * de acordo com os campos da tabela
	 */
	private void setarCaixasTexto() {
		int setar = tblFornecedor.getSelectedRow();
		txtForID.setText(tblFornecedor.getModel().getValueAt(setar, 0).toString());
		txtForFantasia.setText(tblFornecedor.getModel().getValueAt(setar, 1).toString());
		txtForPesquisarFornecedor.setText(tblFornecedor.getModel().getValueAt(setar, 1).toString());
		txtForFone.setText(tblFornecedor.getModel().getValueAt(setar, 2).toString());
		txtForContato.setText(tblFornecedor.getModel().getValueAt(setar, 3).toString());		
	}

	/**
	 * Limpar Campos
	 */
	private void limparCamposFornecedor() {
		((DefaultTableModel)tblFornecedor.getModel()).setRowCount(0);
	}
	
	/**
	 * M?todo respons?vel pela pesquisa de Fornecedores
	 */
	private void pesquisarFornecedor() {
		if (txtForPesquisarFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome fantasia do fornecedor");
			txtForPesquisarFornecedor.requestFocus();
		} else {
			String read = "select * from fornecedores where fantasia = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtForPesquisarFornecedor.getText());
				ResultSet rs = pst.executeQuery();
				limparCampos();
				if (rs.next()) {
					txtForID.setText(rs.getString(1));
					txtForCNPJ.setText(rs.getString(2));
					txtForIE.setText(rs.getString(3));
					txtForIM.setText(rs.getString(4));
					txtForRazao.setText(rs.getString(5));
					txtForFantasia.setText(rs.getString(6));
					txtForFone.setText(rs.getString(7));
					txtForContato.setText(rs.getString(8));
					txtForEmail.setText(rs.getString(9));
					txtForCEP.setText(rs.getString(10));
					txtForEndereco.setText(rs.getString(11));
					txtForNumero.setText(rs.getString(12));
					txtForComplemento.setText(rs.getString(13));
					txtForBairro.setText(rs.getString(14));
					txtForCidade.setText(rs.getString(15));
					cboForUF.setSelectedItem(rs.getString(16));
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnBuscarCep.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor n?o cadastrado");
					limparCamposFornecedor();
					limparCampos();
					btnBuscarCep.setEnabled(true);
					btnAdicionar.setEnabled(true);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void adicionarFornecedor() {
		
		if (txtForCNPJ.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o CNPJ do Fornecedor");
			txtForCNPJ.requestFocus();
		}	else if (txtForRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a raz?o social do fornecedor");
			txtForRazao.requestFocus();
		}	else if (txtForFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a fantasia do fornecedor");
			txtForFantasia.requestFocus();
		}	else if (txtForFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o telefone do fornecedor");
			txtForFone.requestFocus();
		}	else if (txtForCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o CEP do fornecedor");
			txtForCEP.requestFocus();
		}	else if (txtForEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o endere?o do fornecedor");
			txtForEndereco.requestFocus();
		}	else if (txtForNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o n?mero do local do fornecedor");
			txtForNumero.requestFocus();
		}	else if (txtForBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o bairro do fornecedor");
			txtForBairro.requestFocus();
		}	else if (txtForCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a cidade do fornecedor");
			txtForCidade.requestFocus();
		}	else if (cboForUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione a UF do fornecedor");
			cboForUF.requestFocus();
		}
		else {
			String create = "insert into fornecedores(cnpj, ie, im, razao, fantasia, fone, contato, email, cep, endereco, numero, complemento, bairro, cidade, uf) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtForCNPJ.getText());
				pst.setString(2, txtForIE.getText());
				pst.setString(3, txtForIM.getText());
				pst.setString(4, txtForRazao.getText());
				pst.setString(5, txtForFantasia.getText());
				pst.setString(6, txtForFone.getText());
				pst.setString(7, txtForContato.getText());
				pst.setString(8, txtForEmail.getText());
				pst.setString(9, txtForCEP.getText());
				pst.setString(10, txtForEndereco.getText());
				pst.setString(11, txtForNumero.getText());
				pst.setString(12, txtForComplemento.getText());
				pst.setString(13, txtForBairro.getText());
				pst.setString(14, txtForCidade.getText());
				pst.setString(15, cboForUF.getSelectedItem().toString());
				btnBuscarCep.setEnabled(true);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor cadastrado com sucesso");
				limparCamposFornecedor();
				limparCampos();
				txtForPesquisarFornecedor.setText(null);
				con.close();
			} catch(SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "CNPJ ou IE ou IM duplicado.\nEscolha outro.");
				txtForCNPJ.setText(null);
				txtForCNPJ.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
			
	}
	
	private void alterarFornecedor() {
		
		if (txtForCNPJ.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o CNPJ do Fornecedor");
			txtForCNPJ.requestFocus();
		}	else if (txtForRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a raz?o social do fornecedor");
			txtForRazao.requestFocus();
		}	else if (txtForFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a fantasia do fornecedor");
			txtForFantasia.requestFocus();
		}	else if (txtForFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o telefone do fornecedor");
			txtForFone.requestFocus();
		}	else if (txtForCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o CEP do fornecedor");
			txtForCEP.requestFocus();
		}	else if (txtForEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o endere?o do fornecedor");
			txtForEndereco.requestFocus();
		}	else if (txtForNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o n?mero do local do fornecedor");
			txtForNumero.requestFocus();
		}	else if (txtForBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o bairro do fornecedor");
			txtForBairro.requestFocus();
		}	else if (txtForCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a cidade do fornecedor");
			txtForIE.requestFocus();
		}	else if (cboForUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione a UF do fornecedor");
			cboForUF.requestFocus();
		}
		
		else {
			String update = "update fornecedores set cnpj=?, ie=?, im=?, razao=?, fantasia=?, fone=?, contato=?, email=?, cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, uf=? where idfor=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtForCNPJ.getText());
				pst.setString(2, txtForIE.getText());
				pst.setString(3, txtForIM.getText());
				pst.setString(4, txtForRazao.getText());
				pst.setString(5, txtForFantasia.getText());
				pst.setString(6, txtForFone.getText());
				pst.setString(7, txtForContato.getText());
				pst.setString(8, txtForEmail.getText());
				pst.setString(9, txtForCEP.getText());
				pst.setString(10, txtForEndereco.getText());
				pst.setString(11, txtForNumero.getText());
				pst.setString(12, txtForComplemento.getText());
				pst.setString(13, txtForBairro.getText());
				pst.setString(14, txtForCidade.getText());
				pst.setString(15, cboForUF.getSelectedItem().toString());
				pst.setString(16, txtForID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor alterado com sucesso");
				limparCamposFornecedor();
				limparCampos();
				txtForPesquisarFornecedor.setText(null);
				con.close();
			} catch(SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "CNPJ ou IE ou IM duplicado.\nEscolha outro.");
				txtForCNPJ.setText(null);
				txtForCNPJ.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
			
	}
	
	private void excluirFornecedor() {
		int confirma = JOptionPane.showConfirmDialog(null, "Deseja confirmar a exclus?o do fornecedor ?","Aten??o!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from fornecedores where idfor=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtForID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor exclu?do com sucesso!");
				limparCamposFornecedor();
				limparCampos();
				txtForPesquisarFornecedor.setText(null);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
	}
	
	private void limparCampos() {
		txtForFantasia.setText(null);
		txtForCNPJ.setText(null);
		txtForIE.setText(null);
		txtForIM.setText(null);
		txtForRazao.setText(null);
		txtForFone.setText(null);
		txtForContato.setText(null);
		txtForEmail.setText(null);
		txtForCEP.setText(null);
		txtForEndereco.setText(null);
		txtForNumero.setText(null);
		txtForComplemento.setText(null);
		txtForBairro.setText(null);
		txtForCidade.setText(null);
		cboForUF.setSelectedItem("");
		txtForID.setText(null);
		btnAdicionar.setEnabled(false);
		btnAlterar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
}// Fim do c?digo