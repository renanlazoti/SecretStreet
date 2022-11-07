package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Font;

public class Ajuda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Ajuda dialog = new Ajuda();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Ajuda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ajuda.class.getResource("/img/CADEADO SS PRONTO prenchido.png")));
		setTitle("Sobre");
		setBounds(100, 100, 647, 388);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		lblNewLabel.setEnabled(false);
		lblNewLabel.setIcon(new ImageIcon(Ajuda.class.getResource("/img/CADEADO SS PRONTO.png")));
		lblNewLabel.setBounds(331, 152, 436, 364);
		
		contentPanel.add(lblNewLabel);
		
		JTextPane txtpnSecretStreetVerso = new JTextPane();
		txtpnSecretStreetVerso.setFont(new Font("Arial", Font.PLAIN, 14));
		txtpnSecretStreetVerso.setText("Secret Street\r\nVers\u00E3o 1.0\r\nSistema para a gest\u00E3o e controle do estoque de produtos, fornecedores, clientes, usu\u00E1rios e\r\nrelat\u00F3rios da organiza\u00E7\u00E3o Secret Street.\r\n\r\nLicen\u00E7a MIT\r\n\r\n*link do site*");
		txtpnSecretStreetVerso.setBounds(106, 117, 421, 172);
		contentPanel.add(txtpnSecretStreetVerso);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Ajuda.class.getResource("/img/SS Escrito em Preto 64x.png")));
		lblNewLabel_1.setBounds(270, 31, 83, 64);
		contentPanel.add(lblNewLabel_1);
	}
}
