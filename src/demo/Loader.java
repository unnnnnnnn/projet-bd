package demo;
import java.sql.*;
public class Loader {

	static final String USER = "rocchias";
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String PASSWORD = "rocchias";
	private Connection connection;
	
	/**
	 * Initialise la connection
	 */
	public Loader() {
		try {
			System.out.println("Loading Oracle Driver ...\n");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Loaded\n");
			System.out.println("Connecting to the data base...\n");
			this.connection = DriverManager.getConnection(CONN_URL,USER,PASSWORD);
			System.out.println("Connected\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * @return Renvoie la connection créée précédement
	 */
	public Connection getConnection() {
		return this.connection;
	}
	
	/**
	 * Ferme la connection créée
	 */
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}