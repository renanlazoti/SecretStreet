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
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Cursor;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				txtBarCode.requestFocus();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/CADEADO SS PRONTO prenchido.png")));
		setTitle("Produtos");
		setBounds(100, 100, 952, 656);
		getContentPane().setLayout(null);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBounds(0, 0, 985, 681);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(null, "Produto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("Produtos\r\n");
		panel.setBounds(13, 11, 913, 152);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 893, 84);
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
		txtPesquisarProd.setBackground(SystemColor.menu);
		txtPesquisarProd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarProdutosTabela();
				
			}
		});
		txtPesquisarProd.setBounds(10, 24, 570, 20);
		panel.add(txtPesquisarProd);
		txtPesquisarProd.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(609, 27, 92, 14);
		panel.add(lblNewLabel);
		
		txtIdProd = new JTextField();
		txtIdProd.setBackground(SystemColor.menu);
		txtIdProd.setEditable(false);
		txtIdProd.setBounds(654, 25, 86, 20);
		panel.add(txtIdProd);
		txtIdProd.setColumns(10);
		
		btnPesquisarProd = new JButton("Pesquisar");
		btnPesquisarProd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisarProd.setBackground(SystemColor.menu);
		btnPesquisarProd.setContentAreaFilled(false);
		btnPesquisarProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PesquisarProdutoCodigo();
			}
		});
		btnPesquisarProd.setBounds(758, 24, 138, 23);
		panel.add(btnPesquisarProd);
		
		JLabel lblNewLabel_1 = new JLabel("BarCode");
		lblNewLabel_1.setBounds(48, 185, 193, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtBarCode = new JTextField();
		txtBarCode.setBackground(SystemColor.menu);
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					PesquisarProdutoCodigodeBarras();
				}
			}
		});
		txtBarCode.setBounds(151, 182, 313, 20);
		contentPanel.add(txtBarCode);
		txtBarCode.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Produto");
		lblNewLabel_3.setBounds(48, 227, 139, 14);
		contentPanel.add(lblNewLabel_3);
		
		txtProduto = new JTextField();
		txtProduto.setBackground(SystemColor.menu);
		txtProduto.setBounds(151, 224, 313, 20);
		contentPanel.add(txtProduto);
		txtProduto.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel_4.setBounds(46, 406, 78, 14);
		contentPanel.add(lblNewLabel_4);
		
		txtAreaDesc = new JTextArea();
		txtAreaDesc.setBorder(new EmptyBorder(4, 4, 4, 4));
		txtAreaDesc.setBackground(SystemColor.menu);
		txtAreaDesc.setBounds(151, 369, 235, 86);
		contentPanel.add(txtAreaDesc);
		
		JLabel lblNewLabel_5 = new JLabel("Tamanho");
		lblNewLabel_5.setBounds(48, 268, 124, 14);
		contentPanel.add(lblNewLabel_5);
		
		cboTamanho = new JComboBox();
		cboTamanho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboTamanho.setBackground(SystemColor.menu);
		cboTamanho.setModel(new DefaultComboBoxModel(new String[] {"", "P", "M", "G", "GG"}));
		cboTamanho.setBounds(151, 264, 64, 22);
		contentPanel.add(cboTamanho);
		
		JLabel lblNewLabel_6 = new JLabel("Cor");
		lblNewLabel_6.setBounds(48, 302, 108, 14);
		contentPanel.add(lblNewLabel_6);
		
		cboCor = new JComboBox();
		cboCor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboCor.setBackground(SystemColor.menu);
		cboCor.setModel(new DefaultComboBoxModel(new String[] {"", "PRETA", "BRANCA"}));
		cboCor.setBounds(151, 298, 108, 22);
		contentPanel.add(cboCor);
		
		JLabel lblNewLabel_7 = new JLabel("Fabricante");
		lblNewLabel_7.setBounds(48, 473, 139, 14);
		contentPanel.add(lblNewLabel_7);
		
		txtFabricante = new JTextField();
		txtFabricante.setBackground(SystemColor.menu);
		txtFabricante.setBounds(151, 470, 235, 20);
		contentPanel.add(txtFabricante);
		txtFabricante.setColumns(10);
		
		JLabel lblNewLabel_7_1 = new JLabel("Estoque");
		lblNewLabel_7_1.setBounds(48, 511, 108, 14);
		contentPanel.add(lblNewLabel_7_1);
		
		txtEstoque = new JTextField();
		txtEstoque.setBackground(SystemColor.menu);
		txtEstoque.setBounds(151, 546, 86, 20);
		contentPanel.add(txtEstoque);
		txtEstoque.setColumns(10);
		
		JLabel lblNewLabel_7_1_1 = new JLabel("Estoque Min.");
		lblNewLabel_7_1_1.setBounds(48, 549, 139, 14);
		contentPanel.add(lblNewLabel_7_1_1);
		
		txtEstoqueMin = new JTextField();
		txtEstoqueMin.setBackground(SystemColor.menu);
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBounds(151, 508, 86, 20);
		contentPanel.add(txtEstoqueMin);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(490, 174, 436, 195);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 68, 416, 113);
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
		txtPesquisarFor.setBackground(SystemColor.menu);
		txtPesquisarFor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedorTabela();
			}
		});
		txtPesquisarFor.setBounds(11, 30, 265, 20);
		panel_1.add(txtPesquisarFor);
		txtPesquisarFor.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("IDFOR");
		lblNewLabel_8.setBounds(306, 34, 77, 14);
		panel_1.add(lblNewLabel_8);
		
		txtIdFor = new JTextField();
		txtIdFor.setBackground(SystemColor.menu);
		txtIdFor.setEditable(false);
		txtIdFor.setText("");
		txtIdFor.setBounds(362, 30, 64, 20);
		panel_1.add(txtIdFor);
		txtIdFor.setColumns(10);
		
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
		btnAdicionarProd.setBounds(608, 508, 64, 64);
		contentPanel.add(btnAdicionarProd);
		btnAdicionarProd.setIcon(new ImageIcon(Produtos.class.getResource("/img/iconadd64.png")));
		
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
		btnAtualizarProd.setBounds(696, 508, 64, 64);
		contentPanel.add(btnAtualizarProd);
		btnAtualizarProd.setIcon(new ImageIcon(Produtos.class.getResource("/img/iconupload64.png")));
		
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
		btnDeletarProd.setBounds(780, 508, 64, 64);
		contentPanel.add(btnDeletarProd);
		btnDeletarProd.setIcon(new ImageIcon(Produtos.class.getResource("/img/icondelete64.png")));
		
		JLabel lblNewLabel_10 = new JLabel("Entrada");
		lblNewLabel_10.setBounds(594, 464, 139, 14);
		contentPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Custo");
		lblNewLabel_11.setBounds(490, 393, 157, 14);
		contentPanel.add(lblNewLabel_11);
		
		txtCusto = new JTextField();
		txtCusto.setBackground(SystemColor.menu);
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validaï¿½ï¿½o (somente nï¿½meros ao digitar)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
				
			}
		});
		txtCusto.setBounds(556, 390, 124, 20);
		contentPanel.add(txtCusto);
		txtCusto.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("Lucro (%)");
		lblNewLabel_12.setBounds(720, 393, 139, 14);
		contentPanel.add(lblNewLabel_12);
		
		txtLucro = new JTextField();
		txtLucro.setBackground(SystemColor.menu);
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validaï¿½ï¿½o (somente nï¿½meros ao digitar)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
				
			}
		});
		txtLucro.setBounds(797, 390, 124, 20);
		contentPanel.add(txtLucro);
		txtLucro.setColumns(10);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBackground(SystemColor.menu);
		txtQuantidade.setBounds(151, 334, 86, 20);
		contentPanel.add(txtQuantidade);
		txtQuantidade.setColumns(10);
		
		dataEntrada = new JDateChooser();
		dataEntrada.setBounds(661, 461, 153, 20);
		contentPanel.add(dataEntrada);
		
		JLabel lblNewLabel_13 = new JLabel("Venda");
		lblNewLabel_13.setBounds(540, 424, 157, 14);
		contentPanel.add(lblNewLabel_13);
		
		txtVenda = new JTextField();
		txtVenda.setBackground(SystemColor.menu);
		txtVenda.setEditable(false);
		txtVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validaï¿½ï¿½o (somente nï¿½meros ao digitar)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtVenda.setBounds(602, 421, 124, 20);
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
		validarCusto.setLimit(6);

		RestrictedTextField validarLucro = new RestrictedTextField(txtLucro);
		validarLucro.setLimit(6);
		
		RestrictedTextField validarVenda = new RestrictedTextField(txtVenda);
		validarVenda.setLimit(6);
		
		
		
		JLabel lblNewLabel_14 = new JLabel("Quantidade");
		lblNewLabel_14.setBounds(48, 337, 156, 14);
		contentPanel.add(lblNewLabel_14);
		
		btnNewButton = new JButton("Calcular");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBackground(SystemColor.menu);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtVenda.setText( String.valueOf(Double.parseDouble(txtCusto.getText()) * (1+Double.parseDouble(txtLucro.getText())/100)));
			}
		});
		btnNewButton.setBounds(752, 420, 124, 23);
		contentPanel.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setEnabled(false);
		lblNewLabel_2.setIcon(new ImageIcon(Produtos.class.getResource("/img/CADEADO SS PRONTO.png")));
		lblNewLabel_2.setBounds(608, 334, 361, 353);
		contentPanel.add(lblNewLabel_2);
		
		
	
	} // fim do construtor
	
	DAO dao = new DAO();
	private JComboBox cboTamanho;
	private JComboBox cboCor;
	private JTextField txtVenda;
	private JButton btnPesquisarProd;
	private JTextField txtQuantidade;
	private JButton btnNewButton;
	
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
		 * Método responsável por pesquisar produto por código de barras
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

				String create = "insert into produtos(barcode,produto,descricao,tamanho,cor,quantidade,fabricante,estoque,estoquemin,custo,lucro,venda,idfor) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
					pst.setString(12, txtVenda.getText());
					pst.setString(13, txtIdFor.getText());		
					pst.executeUpdate();			
					limparCampos();
					LimparCamposFornecedor();
					LimparCamposProdutos();
					txtPesquisarProd.setText(null);
					txtIdProd.setText(null);
					JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso!");
					con.close();
				} catch (SQLIntegrityConstraintViolationException ex) {
					JOptionPane.showMessageDialog(null, "BarCode exixtente.\nDigite outro");
					txtBarCode.setText(null);
					txtBarCode.requestFocus();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		
		/**
		 * Método responsável por alterar os dados de um fornecedor do banco
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

				String update = "update produtos set barcode=?,produto=?,descricao=?,tamanho=?,cor=?,quantidade=?,fabricante=?,datacad=?,estoque=?,estoquemin=?,custo=?,lucro=?,venda=?,idfor=? where idprod=?";
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
					pst.setString(13, txtVenda.getText());	
					pst.setString(14, txtIdFor.getText());					
					pst.setString(15, txtIdProd.getText());	
					pst.executeUpdate();	
					limparCampos();
					LimparCamposFornecedor();
					LimparCamposProdutos();
					txtPesquisarProd.setText(null);
					JOptionPane.showMessageDialog(null, "Produto Alterado com Sucesso!");
					con.close();
				} catch (SQLIntegrityConstraintViolationException ex) {
					JOptionPane.showMessageDialog(null, "BarCode existente.\nDigite outro");
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
