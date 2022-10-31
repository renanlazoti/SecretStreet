package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JButton btnUsuarios;
	private JButton btnClientes;
	private JButton btnFornecedores;
	private JButton btnProdutos;
	private JButton btnRelatorios;
	private JButton btnAjuda;
	public final JPanel panelUsuario = new JPanel();
	public JLabel lblPerfil;
	private JLabel lblData;
	private JLabel lblNewLabel;
	public JLabel lblUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/CADEADO SS PRONTO.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				lblData.setText(formatador.format(data));
			}
		});
		setTitle("Tela Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/iconuser3.png")));
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setToolTipText("Usu\u00E1rios");
		btnUsuarios.setBounds(86, 85, 128, 128);
		contentPane.add(btnUsuarios);
		
		btnClientes = new JButton("");
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorderPainted(false);
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/iconcli3.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(323, 85, 128, 128);
		contentPane.add(btnClientes);
		
		btnFornecedores = new JButton("");
		btnFornecedores.setBorderPainted(false);
		btnFornecedores.setContentAreaFilled(false);
		btnFornecedores.setIcon(new ImageIcon(Principal.class.getResource("/img/iconforne3.png")));
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnFornecedores.setToolTipText("Fornecedores");
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setBounds(550, 85, 128, 128);
		contentPane.add(btnFornecedores);
		
		btnProdutos = new JButton("");
		btnProdutos.setBorderPainted(false);
		btnProdutos.setContentAreaFilled(false);
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/iconprod3.png")));
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnProdutos.setBounds(86, 272, 128, 128);
		contentPane.add(btnProdutos);
		
		btnRelatorios = new JButton("");
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setBorderPainted(false);
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/iconrelatorio3.png")));
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setToolTipText("Relatorios");
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setBounds(324, 272, 128, 128);
		contentPane.add(btnRelatorios);
		
		btnAjuda = new JButton("");
		btnAjuda.setBorderPainted(false);
		btnAjuda.setContentAreaFilled(false);
		btnAjuda.setIcon(new ImageIcon(Principal.class.getResource("/img/iconsobre3.png")));
		btnAjuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ajuda ajuda = new Ajuda();
				ajuda.setVisible(true);
			}
		});
		btnAjuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAjuda.setToolTipText("Ajuda");
		btnAjuda.setBounds(551, 272, 128, 128);
		contentPane.add(btnAjuda);
		panelUsuario.setBackground(Color.BLACK);
		panelUsuario.setForeground(Color.BLACK);
		panelUsuario.setBounds(-24, 485, 823, 112);
		contentPane.add(panelUsuario);
		panelUsuario.setLayout(null);
		
		lblUsuario = new JLabel("New label");
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 12));
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setBounds(56, 18, 138, 14);
		panelUsuario.add(lblUsuario);
		
		lblPerfil = new JLabel("New label");
		lblPerfil.setFont(new Font("Arial", Font.BOLD, 12));
		lblPerfil.setForeground(Color.WHITE);
		lblPerfil.setBounds(56, 43, 138, 14);
		panelUsuario.add(lblPerfil);
		
		lblData = new JLabel("New label");
		lblData.setFont(new Font("Arial", Font.BOLD, 14));
		lblData.setForeground(Color.WHITE);
		lblData.setBounds(371, 1, 438, 76);
		panelUsuario.add(lblData);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setEnabled(false);
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/CADEADO SS PRONTO.png")));
		lblNewLabel.setBounds(23, -59, 713, 594);
		contentPane.add(lblNewLabel);
	}
}
