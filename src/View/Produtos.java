package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;

public class Produtos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tblProdutos;
	private JTextField txtPesquisarProd;
	private JTextField txtIdProd;
	private JTextField txtBarCode;
	private JTextField txtProduto;
	private JTextArea txtAreaDesc;
	private JTextField txtFabricante;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTable tblFornecedores;
	private JTextField txtPesquisarFor;
	private JTextField txtIdFor;
	private JButton btnAtualizarProd;
	private JButton btnAdicionarProd;
	private JScrollPane scrollPane;
	private JButton btnDeletarProd;
	private JTextField txtCusto;
	private JTextField txtLucro;
	private JDateChooser dataEntrada;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Produtos dialog = new Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Produtos() {
		setTitle("SecretStreet - Produtos");
		setBounds(100, 100, 752, 577);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 736, 538);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Produto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("Produtos\r\n");
		panel.setBounds(10, 11, 716, 152);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 696, 84);
		panel.add(scrollPane);
		
		tblProdutos = new JTable();
		tblProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCaixasTexto2();
			}
		});
		scrollPane.setViewportView(tblProdutos);
		
		txtPesquisarProd = new JTextField();
		txtPesquisarProd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarProdutosTabela();
				
			}
		});
		txtPesquisarProd.setBounds(10, 24, 324, 20);
		panel.add(txtPesquisarProd);
		txtPesquisarProd.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("IDPROD");
		lblNewLabel.setBounds(412, 27, 46, 14);
		panel.add(lblNewLabel);
		
		txtIdProd = new JTextField();
		txtIdProd.setEditable(false);
		txtIdProd.setBounds(460, 24, 86, 20);
		panel.add(txtIdProd);
		txtIdProd.setColumns(10);
		
		btnPesquisarProd = new JButton("Pesquisar");
		btnPesquisarProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PesquisarProdutoCodigo();
			}
		});
		btnPesquisarProd.setBounds(572, 23, 98, 23);
		panel.add(btnPesquisarProd);
		
		JLabel lblNewLabel_1 = new JLabel("BarCode");
		lblNewLabel_1.setBounds(13, 184, 65, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtBarCode = new JTextField();
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					PesquisarProdutoCodigodeBarras();
				}
			}
		});
		txtBarCode.setBounds(72, 182, 160, 20);
		contentPanel.add(txtBarCode);
		txtBarCode.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Produtos.class.getResource("/img/lupa.png")));
		lblNewLabel_2.setBounds(244, 166, 64, 48);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Produto");
		lblNewLabel_3.setBounds(14, 227, 46, 14);
		contentPanel.add(lblNewLabel_3);
		
		txtProduto = new JTextField();
		txtProduto.setBounds(62, 224, 160, 20);
		contentPanel.add(txtProduto);
		txtProduto.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel_4.setBounds(14, 400, 78, 14);
		contentPanel.add(lblNewLabel_4);
		
		txtAreaDesc = new JTextArea();
		txtAreaDesc.setBounds(72, 374, 193, 73);
		contentPanel.add(txtAreaDesc);
		
		JLabel lblNewLabel_5 = new JLabel("Tamanho");
		lblNewLabel_5.setBounds(14, 268, 64, 14);
		contentPanel.add(lblNewLabel_5);
		
		cboTamanho = new JComboBox();
		cboTamanho.setModel(new DefaultComboBoxModel(new String[] {"", "P", "M", "G", "GG"}));
		cboTamanho.setBounds(72, 264, 64, 22);
		contentPanel.add(cboTamanho);
		
		JLabel lblNewLabel_6 = new JLabel("Cor");
		lblNewLabel_6.setBounds(15, 302, 31, 14);
		contentPanel.add(lblNewLabel_6);
		
		cboCor = new JComboBox();
		cboCor.setModel(new DefaultComboBoxModel(new String[] {"", "PRETA", "BRANCA"}));
		cboCor.setBounds(72, 298, 81, 22);
		contentPanel.add(cboCor);
		
		JLabel lblNewLabel_7 = new JLabel("Fabricante");
		lblNewLabel_7.setBounds(12, 460, 69, 14);
		contentPanel.add(lblNewLabel_7);
		
		txtFabricante = new JTextField();
		txtFabricante.setBounds(81, 458, 113, 20);
		contentPanel.add(txtFabricante);
		txtFabricante.setColumns(10);
		
		JLabel lblNewLabel_7_1 = new JLabel("Estoque");
		lblNewLabel_7_1.setBounds(11, 490, 69, 14);
		contentPanel.add(lblNewLabel_7_1);
		
		txtEstoque = new JTextField();
		txtEstoque.setBounds(61, 488, 71, 20);
		contentPanel.add(txtEstoque);
		txtEstoque.setColumns(10);
		
		JLabel lblNewLabel_7_1_1 = new JLabel("Estoque Min");
		lblNewLabel_7_1_1.setBounds(145, 491, 78, 14);
		contentPanel.add(lblNewLabel_7_1_1);
		
		txtEstoqueMin = new JTextField();
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBounds(222, 488, 71, 20);
		contentPanel.add(txtEstoqueMin);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(367, 176, 356, 195);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 68, 336, 113);
		panel_1.add(scrollPane_1);
		
		tblFornecedores = new JTable();
		tblFornecedores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCaixasTexto();
			}
		});
		scrollPane_1.setViewportView(tblFornecedores);
		
		txtPesquisarFor = new JTextField();
		txtPesquisarFor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedorTabela();
			}
		});
		txtPesquisarFor.setBounds(11, 30, 132, 20);
		panel_1.add(txtPesquisarFor);
		txtPesquisarFor.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("IDFOR");
		lblNewLabel_8.setBounds(225, 32, 46, 14);
		panel_1.add(lblNewLabel_8);
		
		txtIdFor = new JTextField();
		txtIdFor.setEditable(false);
		txtIdFor.setText("");
		txtIdFor.setBounds(275, 28, 64, 20);
		panel_1.add(txtIdFor);
		txtIdFor.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setIcon(new ImageIcon(Produtos.class.getResource("/img/lupa.png")));
		lblNewLabel_9.setBounds(151, 15, 38, 39);
		panel_1.add(lblNewLabel_9);
		
		btnAdicionarProd = new JButton("");
		btnAdicionarProd.setContentAreaFilled(false);
		btnAdicionarProd.setBorderPainted(false);
		btnAdicionarProd.setEnabled(false);
		btnAdicionarProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarProduto();
			}
		});
		btnAdicionarProd.setToolTipText("Adicionar Produto");
		btnAdicionarProd.setBounds(490, 469, 78, 66);
		contentPanel.add(btnAdicionarProd);
		btnAdicionarProd.setIcon(new ImageIcon(Produtos.class.getResource("/img/create.png")));
		
		btnAtualizarProd = new JButton("");
		btnAtualizarProd.setContentAreaFilled(false);
		btnAtualizarProd.setBorderPainted(false);
		btnAtualizarProd.setEnabled(false);
		btnAtualizarProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarProduto();
			}
			
		});
		btnAtualizarProd.setToolTipText("Atualizar Produto");
		btnAtualizarProd.setBounds(571, 466, 78, 70);
		contentPanel.add(btnAtualizarProd);
		btnAtualizarProd.setIcon(new ImageIcon(Produtos.class.getResource("/img/update.png")));
		
		btnDeletarProd = new JButton("");
		btnDeletarProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirProduto();
			}
		});
		btnDeletarProd.setContentAreaFilled(false);
		btnDeletarProd.setBorderPainted(false);
		btnDeletarProd.setEnabled(false);
		btnDeletarProd.setToolTipText("Excluir Produto");
		btnDeletarProd.setBounds(652, 468, 78, 66);
		contentPanel.add(btnDeletarProd);
		btnDeletarProd.setIcon(new ImageIcon(Produtos.class.getResource("/img/excluir.png")));
		
		JLabel lblNewLabel_10 = new JLabel("Entrada");
		lblNewLabel_10.setBounds(378, 393, 54, 14);
		contentPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Custo");
		lblNewLabel_11.setBounds(594, 392, 46, 14);
		contentPanel.add(lblNewLabel_11);
		
		txtCusto = new JTextField();
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação (somente números ao digitar)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
				
			}
		});
		txtCusto.setBounds(637, 388, 86, 20);
		contentPanel.add(txtCusto);
		txtCusto.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("Lucro (%)");
		lblNewLabel_12.setBounds(379, 425, 64, 14);
		contentPanel.add(lblNewLabel_12);
		
		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação (somente números ao digitar)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
				
			}
		});
		txtLucro.setBounds(441, 423, 86, 20);
		contentPanel.add(txtLucro);
		txtLucro.setColumns(10);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(86, 338, 86, 20);
		contentPanel.add(txtQuantidade);
		txtQuantidade.setColumns(10);
		
		dataEntrada = new JDateChooser();
		dataEntrada.setBounds(426, 389, 153, 20);
		contentPanel.add(dataEntrada);
		
		JLabel lblNewLabel_13 = new JLabel("Venda");
		lblNewLabel_13.setBounds(591, 422, 46, 14);
		contentPanel.add(lblNewLabel_13);
		
		txtVenda = new JTextField();
		txtVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação (somente números ao digitar)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtVenda.setBounds(631, 419, 86, 20);
		contentPanel.add(txtVenda);
		txtVenda.setColumns(10);
		
		// txtProduto
		RestrictedTextField validarProduto = new RestrictedTextField(txtProduto);
		validarProduto.setLimit(20);
		//txtPesquisarProd
		RestrictedTextField validarPesquisarProd = new RestrictedTextField(txtPesquisarProd);
		validarPesquisarProd.setLimit(30);
		//txtIdProd
		RestrictedTextField validarIdProd = new RestrictedTextField(txtIdProd);
		validarIdProd.setOnlyNums(true);
		validarIdProd.setLimit(3);
		// txtBarCode
		 RestrictedTextField validarBarCode = new RestrictedTextField(txtBarCode);
		 validarBarCode.setOnlyNums(true);
		 validarBarCode.setLimit(20);
		// txtFabricante
		RestrictedTextField validarFabricante = new RestrictedTextField(txtFabricante);
		validarFabricante.setLimit(30);
		// txtQuantidade
		RestrictedTextField validarQuantidade = new RestrictedTextField(txtQuantidade);
		validarQuantidade.setOnlyNums(true);
		validarQuantidade.setLimit(4);
		
		// txtEstoque
		RestrictedTextField validarEstoque = new RestrictedTextField(txtEstoque);
		validarEstoque.setOnlyNums(true);
		validarEstoque.setLimit(6);
		// txtEstoqueMin
		RestrictedTextField validarEstoqueMin = new RestrictedTextField(txtEstoqueMin);
		validarEstoqueMin.setOnlyNums(true);
		validarEstoqueMin.setLimit(6);
		// txtPesquisarFor
		RestrictedTextField validarPesquisarFor = new RestrictedTextField(txtPesquisarFor);
		validarPesquisarFor.setLimit(30);
		// txtIdFor
		RestrictedTextField validarIdFor = new RestrictedTextField(txtIdFor);
		validarIdFor.setOnlyNums(true);
		validarIdFor.setLimit(4);
		// txtCusto
		RestrictedTextField validarCusto = new RestrictedTextField(txtCusto);
		validarCusto.setOnlyNums(true);
		validarCusto.setLimit(6);

		RestrictedTextField validarLucro = new RestrictedTextField(txtLucro);
		validarLucro.setOnlyNums(true);
		validarLucro.setLimit(6);
		
		RestrictedTextField validarVenda = new RestrictedTextField(txtVenda);
		validarVenda.setOnlyNums(true);
		validarVenda.setLimit(6);
		
		
		
		JLabel lblNewLabel_14 = new JLabel("Quantidade");
		lblNewLabel_14.setBounds(16, 341, 76, 14);
		contentPanel.add(lblNewLabel_14);
		
		
	
	} // fim do construtor
	
	DAO dao = new DAO();
	private JComboBox cboTamanho;
	private JComboBox cboCor;
	private JTextField txtVenda;
	private JButton btnPesquisarProd;
	private JTextField txtQuantidade;
	
	/**
	 * Método responsável por pesquisar um produto na tabela;
	 */
	private void pesquisarProdutosTabela() {
		String readT = "select idprod as ID,produto as Produto,barcode as BarCode,fabricante as Fabricante, cor as Cor, custo as custo from produtos where produto like ?";
		try {			
			Connection con = dao.conectar();		
			PreparedStatement pst = con.prepareStatement(readT);			
			pst.setString(1, txtPesquisarProd.getText() + "%");
			ResultSet rs = pst.executeQuery();		
			tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void setarCaixasTexto2() {	
		int setar = tblProdutos.getSelectedRow();
		txtIdProd.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
		txtPesquisarProd.setText(tblProdutos.getModel().getValueAt(setar, 1).toString());
	}
	/**
	 * Método responsável por pesquisar um fornecedor na tabela;
	 */
	private void pesquisarFornecedorTabela() {
		String readT = "select idfor as ID,fantasia as Fornecedor,fone as Telefone,contato as Contato from fornecedores where fantasia like ?";
		try {			
			Connection con = dao.conectar();		
			PreparedStatement pst = con.prepareStatement(readT);	
			pst.setString(1, txtPesquisarFor.getText() + "%");
			ResultSet rs = pst.executeQuery();
			tblFornecedores.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void setarCaixasTexto() {
		int setar = tblFornecedores.getSelectedRow();
		txtIdFor.setText(tblFornecedores.getModel().getValueAt(setar, 0).toString());
		txtPesquisarFor.setText(tblFornecedores.getModel().getValueAt(setar, 1).toString());
	}
	/**
	 * Método responsável de pesquisar produto pelo ID
	 */
		private void PesquisarProdutoCodigo() {
			String read = "select * from produtos where produto = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtPesquisarProd.getText());
				ResultSet rs = pst.executeQuery();
				limparCampos();
				if (rs.next()) {
					txtBarCode.setText(rs.getString(2));
					txtProduto.setText(rs.getString(3));
					txtAreaDesc.setText(rs.getString(4));
					cboTamanho.setSelectedItem(rs.getString(5));
					cboCor.setSelectedItem(rs.getString(6));
					txtQuantidade.setText(rs.getString(7));
					txtFabricante.setText(rs.getString(8));
					String setarDataCad = rs.getString(9);
					txtEstoque.setText(rs.getString(10));
					txtEstoqueMin.setText(rs.getString(11));
					txtCusto.setText(rs.getString(12));
					txtLucro.setText(rs.getString(13));
					txtVenda.setText(rs.getString(14));					
					txtIdFor.setText(rs.getString(15));		
					Date dataVal = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataCad);
					dataEntrada.setDate(dataVal);
					btnAtualizarProd.setEnabled(true);
					btnDeletarProd.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não cadastrado");
					limparCampos();
					LimparCamposFornecedor();
				    txtIdProd.setText(null);
					btnAdicionarProd.setEnabled(true);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		/**
		 * Método resonsável por pesquisar produto por código de barras
		 */
		private void PesquisarProdutoCodigodeBarras() {
			String read2 = "select * from produtos where barcode = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read2);
				pst.setString(1, txtBarCode.getText());
				ResultSet rs = pst.executeQuery();
				limparCamposCodigo();
				if (rs.next()) {
					txtIdProd.setText(rs.getString(1));
					txtBarCode.setText(rs.getString(2));
					txtProduto.setText(rs.getString(3));
					txtAreaDesc.setText(rs.getString(4));
					cboTamanho.setSelectedItem(rs.getString(5));
					cboCor.setSelectedItem(rs.getString(6));
					txtFabricante.setText(rs.getString(7));
					txtEstoque.setText(rs.getString(9));
					txtEstoqueMin.setText(rs.getString(10));
					txtCusto.setText(rs.getString(11));
					txtLucro.setText(rs.getString(12));
					txtVenda.setText(rs.getString(13));		
					txtQuantidade.setText(rs.getString(14));
					String setarDataCad = rs.getString(8);				
					Date dataVal = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataCad);
					dataEntrada.setDate(dataVal);
					btnAtualizarProd.setEnabled(true);
					btnDeletarProd.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não cadastrado");
					limparCamposCodigo();
					LimparCamposFornecedor();
					btnAdicionarProd.setEnabled(true);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/**
		 * Método responsável por criar um produto no banco
		 */
				
		private void adicionarProduto() {	
			if (txtProduto.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Nome do Produto");
				txtProduto.requestFocus();
			} else if (cboTamanho.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(null, "Informe o tamanho do produto");
				cboTamanho.requestFocus();
			} else if (cboCor.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(null, "Informe a cor do produto");
				cboCor.requestFocus();
			} else if (txtQuantidade.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe a quantidade de produtos desejada");
				txtQuantidade.requestFocus();
			} else if (txtAreaDesc.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe a descrição do produto");
				txtAreaDesc.requestFocus(); 
			} else if (txtFabricante.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o fabricante do produto");
				txtFabricante.requestFocus();
			} else if (txtEstoque.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o estoque do produto");
				txtEstoque.requestFocus();
			} else if (txtEstoqueMin.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o estoque mínimo do produto");
				txtEstoqueMin.requestFocus();
			} else if (txtCusto.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o custo do produto");
				txtCusto.requestFocus(); 
			} else if (txtLucro.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o lucro desejado em cima do produto");
				txtLucro.requestFocus();
			} else if (txtIdFor.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o ID do fornecedor");
				txtIdFor.requestFocus();
			}else {

				String create = "insert into produtos(barcode,produto,descricao,tamanho,cor,quantidade,fabricante,estoque,estoquemin,custo,lucro,idfor) values (?,?,?,?,?,?,?,?,?,?,?,?)";
				try {				
					Connection con = dao.conectar();			
					PreparedStatement pst = con.prepareStatement(create);				
					pst.setString(1, txtBarCode.getText());
					pst.setString(2, txtProduto.getText());
					pst.setString(3, txtAreaDesc.getText());
					pst.setString(4, cboTamanho.getSelectedItem().toString());
					pst.setString(5, cboCor.getSelectedItem().toString());
					pst.setString(6, txtQuantidade.getText());
					pst.setString(7, txtFabricante.getText());
					pst.setString(8, txtEstoque.getText());
					pst.setString(9, txtEstoqueMin.getText());	
					pst.setString(10, txtCusto.getText());
					pst.setString(11, txtLucro.getText());			
					pst.setString(12, txtIdFor.getText());		
					pst.executeUpdate();			
					limparCampos();
					LimparCamposFornecedor();
					LimparCamposProdutos();
					txtPesquisarProd.setText(null);
					txtIdProd.setText(null);
					JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso!");
					con.close();
					
				} catch (SQLIntegrityConstraintViolationException ex) {
					JOptionPane.showMessageDialog(null, "BarCode já exixtente.\nDigite outro");
					txtBarCode.setText(null);
					txtBarCode.requestFocus();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		
		/**
		 * Método responsavel por alterar os dados de um fornecedor do banco
		 */
		private void alterarProduto() {		
			if (txtProduto.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Nome do Produto");
				txtProduto.requestFocus();
			} else if (txtAreaDesc.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe a descrição do produto");
				txtAreaDesc.requestFocus();
			} else if (cboTamanho.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(null, "Informe o tamanho do produto");
				cboTamanho.requestFocus();
			} else if (cboCor.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(null, "Informe a cor do produto");
				cboCor.requestFocus();
			} else if (txtFabricante.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o fabricante do produto");
				txtFabricante.requestFocus();
			} else if (txtEstoque.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o estoque do produto");
				txtEstoque.requestFocus();
			} else if (txtEstoqueMin.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o estoque mínimo do produto");
				txtEstoqueMin.requestFocus();
			} else if (txtCusto.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o custo do produto");
				txtCusto.requestFocus();
			} else if (txtIdFor.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o ID do fornecedor");
				txtIdFor.requestFocus();
			}else if (txtQuantidade.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe a quantidade de produtos desejada");
				txtQuantidade.requestFocus();
			} else if (txtLucro.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o lucro desejado em cima do produto");
				txtLucro.requestFocus();
			} else {

				String update = "update produtos set barcode=?,produto=?,descricao=?,tamanho=?,cor=?,quantidade=?,fabricante=?,datacad=?,estoque=?,estoquemin=?,custo=?,lucro=?,idfor=? where idprod=?";
				try {		
					Connection con = dao.conectar();		
					PreparedStatement pst = con.prepareStatement(update);		
					SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
					String dataVal = formatador.format(dataEntrada.getDate());
					pst.setString(1, txtBarCode.getText());
					pst.setString(2, txtProduto.getText());
					pst.setString(3, txtAreaDesc.getText());
					pst.setString(4, cboTamanho.getSelectedItem().toString());
					pst.setString(5, cboCor.getSelectedItem().toString());
					pst.setString(6, txtQuantidade.getText());
					pst.setString(7, txtFabricante.getText());
					pst.setString(8, dataVal);
					pst.setString(9, txtEstoque.getText());
					pst.setString(10, txtEstoqueMin.getText());	
					pst.setString(11, txtCusto.getText());
					pst.setString(12, txtLucro.getText());			
					pst.setString(13, txtIdFor.getText());					
					pst.setString(14, txtIdProd.getText());	
					pst.executeUpdate();	
					limparCampos();
					LimparCamposFornecedor();
					LimparCamposProdutos();
					txtPesquisarProd.setText(null);
					txtIdProd.setText(null);
					JOptionPane.showMessageDialog(null, "Produto Alterado com Sucesso!");
					con.close();
				} catch (SQLIntegrityConstraintViolationException ex) {
					JOptionPane.showMessageDialog(null, "BarCode já existente.\nDigite outro");
					txtBarCode.setText(null);
					txtBarCode.requestFocus();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		/**
		 * Método responsável para excluir um produto
		 */
		
		private void excluirProduto() {		
			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do produto?", "Atenção!",
					JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				String delete = "delete from produtos where idprod=?";
				try {			
					Connection con = dao.conectar();			
					PreparedStatement pst = con.prepareStatement(delete);			
					pst.setString(1, txtIdProd.getText());			
					pst.executeUpdate();				
					limparCampos();
					LimparCamposFornecedor();
					LimparCamposProdutos();
					txtPesquisarProd.setText(null);
					txtIdProd.setText(null);
					JOptionPane.showMessageDialog(null, "Produto excluido com sucesso");					
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		}
		
		/**
		 * Método responsável por limpar os campos da tabela fornecedor
		 */
		private void LimparCamposFornecedor() {
			((DefaultTableModel) tblFornecedores.getModel()).setRowCount(0);
		}
		/**
		 * Método responsável por limpar os campos da tabela produtos
		 */
		private void LimparCamposProdutos() {		
			((DefaultTableModel) tblProdutos.getModel()).setRowCount(0);
		}
		/**
		 * Método responsável por limpar os campos da tela produtos
		 */
		private void limparCampos() {
			txtBarCode.setText(null);
			txtProduto.setText(null);
			cboTamanho.setSelectedItem("");
			cboCor.setSelectedItem("");
			txtAreaDesc.setText(null);
			txtFabricante.setText(null);
			txtEstoque.setText(null);
			txtEstoqueMin.setText(null);
			dataEntrada.setDate(null);
			txtCusto.setText(null);
			txtLucro.setText(null);	
			txtVenda.setText(null);	
			txtQuantidade.setText(null);
			txtPesquisarFor.setText(null);	
			txtIdFor.setText(null);	
			btnAdicionarProd.setEnabled(false);
			btnAtualizarProd.setEnabled(false);
			btnDeletarProd.setEnabled(false);
		}
		/**
		 * Método responsável por limpar os campos da tela produtos barcode
		 */
		private void limparCamposCodigo() {
			txtBarCode.setText(null);
			txtProduto.setText(null);
			cboTamanho.setSelectedItem("");
			cboCor.setSelectedItem("");
			txtAreaDesc.setText(null);
			txtFabricante.setText(null);
			txtEstoque.setText(null);
			txtEstoqueMin.setText(null);
			dataEntrada.setDate(null);
			txtQuantidade.setText(null);
			txtCusto.setText(null);
			txtLucro.setText(null);	
			txtVenda.setText(null);	
			txtIdProd.setText(null);
			txtPesquisarFor.setText(null);	
			txtIdFor.setText(null);	
			btnAdicionarProd.setEnabled(false);
			btnAtualizarProd.setEnabled(false);
			btnDeletarProd.setEnabled(false);
		}
} // fim do código
