package View;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Relatorios extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Relatorios dialog = new Relatorios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Relatorios() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Reposi\u00E7\u00E3o");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioReposicao();
			}
		});
		btnNewButton.setBounds(54, 47, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setBounds(188, 47, 89, 23);
		getContentPane().add(btnClientes);
		
		JButton btnFornecedores = new JButton("Fornecedores");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioFornecedores();
			}
		});
		btnFornecedores.setBounds(109, 97, 89, 23);
		getContentPane().add(btnFornecedores);
		
		JButton btnNewButton_1 = new JButton("Lucro Total");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioLucro();
			}
		});
		btnNewButton_1.setBounds(253, 97, 89, 23);
		getContentPane().add(btnNewButton_1);
	} // Fim do construtor

	DAO dao = new DAO();

	// método responsável pela impressão do relatório de clientes
	private void relatorioClientes() {
		// criar objeto para construir a página pdf
		Document document = new Document();
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			document.open();
			// gerar o conteúdo do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(new Paragraph("Data de emissão: " + formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Clientes cadastrados"));
			document.add(new Paragraph(" "));
			// ... Demais conteúdos (imagem, tabela, gráfico, etc)
			PdfPTable tabela = new PdfPTable(4);
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("CPF"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			// Acessar o banco de dados
			String relClientes = "select nome,fone,cpf,email from clientes";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o código independente do resultado OK ou não
			document.close();
		}

		// abrir o documento que foi gerado no leitor padrão de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// método responsável pela impressão do relatório de clientes
	private void relatorioReposicao() {
		// criar objeto para construir a página pdf
		Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("reposicao.pdf"));
			document.open();
			// gerar o conteúdo do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(new Paragraph("Data de emissão: " + formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Reposição de estoque"));
			document.add(new Paragraph(" "));
			// ... Demais conteúdos (imagem, tabela, gráfico, etc)
			PdfPTable tabela = new PdfPTable(5);
			PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Produto"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Entrada"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Estoque"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Estoque mínimo"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			// Acessar o banco de dados
			String relReposicao = "select idprod,produto,date_format(datacad,'%d/%m/%Y'), estoque, estoquemin from produtos where estoque < estoquemin";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relReposicao);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o código independente do resultado OK ou não
			document.close();
		}

		// abrir o documento que foi gerado no leitor padrão de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("reposicao.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// método responsável pela impressão do relatório de fornecedores
		private void relatorioFornecedores() {
			// criar objeto para construir a página pdf
			Document document = new Document();
			// gerar o documento pdf
			try {
				// cria um documento pdf em branco de nome fornecedores.pdf
				PdfWriter.getInstance(document, new FileOutputStream("fornecedores.pdf"));
				document.open();
				// gerar o conteúdo do documento
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(new Paragraph("Data de emissão: " + formatador.format(data))));
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Fornecedores cadastrados"));
				document.add(new Paragraph(" "));
				// ... Demais conteúdos (imagem, tabela, gráfico, etc)
				PdfPTable tabela = new PdfPTable(4);
				PdfPCell col1 = new PdfPCell(new Paragraph("Nome Fantasia"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Contato"));
				PdfPCell col4 = new PdfPCell(new Paragraph("CNPJ"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				// Acessar o banco de dados
				String relFornecedores = "select fantasia,fone,contato,cnpj from fornecedores";
				try {
					Connection con = dao.conectar();
					PreparedStatement pst = con.prepareStatement(relFornecedores);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						tabela.addCell(rs.getString(1));
						tabela.addCell(rs.getString(2));
						tabela.addCell(rs.getString(3));
						tabela.addCell(rs.getString(4));
					}

				} catch (Exception e) {
					System.out.println(e);
				}
				// Adicionar a tabela ao documento pdf
				document.add(tabela);
			} catch (Exception e) {
				System.out.println(e);
			} finally { // executa o código independente do resultado OK ou não
				document.close();
			}

			// abrir o documento que foi gerado no leitor padrão de pdf do sistema (PC)
			try {
				Desktop.getDesktop().open(new File("fornecedores.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		private void relatorioLucro() {
			// criar objeto para construir a página pdf
			Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
			// gerar o documento pdf
			try {
				// cria um documento pdf em branco de nome lucro.pdf
				PdfWriter.getInstance(document, new FileOutputStream("lucro.pdf"));
				document.open();
				// gerar o conteúdo do documento
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(new Paragraph("Data de emissão: " + formatador.format(data))));
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Lucro total baseado no estoque"));
				document.add(new Paragraph(" "));
				// ... Demais conteúdos (imagem, tabela, gráfico, etc)
				PdfPTable tabela = new PdfPTable(1);
				PdfPCell col1 = new PdfPCell(new Paragraph("Total"));
				tabela.addCell(col1);
				// Acessar o banco de dados
				String relReposicao = "select sum(estoque * custo) as total from produtos;";
				try {
					Connection con = dao.conectar();
					PreparedStatement pst = con.prepareStatement(relReposicao);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						tabela.addCell(rs.getString(1));
					}

				} catch (Exception e) {
					System.out.println(e);
				}
				// Adicionar a tabela ao documento pdf
				document.add(tabela);
			} catch (Exception e) {
				System.out.println(e);
			} finally { // executa o código independente do resultado OK ou não
				document.close();
			}

			// abrir o documento que foi gerado no leitor padrão de pdf do sistema (PC)
			try {
				Desktop.getDesktop().open(new File("lucro.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
} // Fim do código
