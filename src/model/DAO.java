/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe Modelo DAO para se conectar com o banco de dados;
 * 
 * @author renan.flazoti
 * @author Renan Lazoti
 * @author Gustavo Rene
 * @author Dimitri Saraiva
 * @author Fernando Miranda
 * @author Guilherme Ramires
 * @author Maria Eduarda
 *
 */
public class DAO {

		private String driver = "com.mysql.cj.jdbc.Driver";
		private String url = "jdbc:mysql://10.26.49.133:3306/secretstreet ";
		private String user = "root";
		private String password = "123@senac";
	
	/**
	 * 
	 */
		
		public Connection conectar() {	
			Connection con = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, user, password);
				return con;
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}

	}

