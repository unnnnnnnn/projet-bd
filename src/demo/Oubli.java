package demo;
import java.sql.*;

public class Oubli {
	private Connection conn;
	
	public Oubli(Connection connection, String mail, String pwd) {
		try {
			this.conn = connection;
			// Création de la requête
			PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM Clients WHERE mailCl = ?");

			// Récupération du mail du compte à supprimer et de son mdp
			stmt.setString(1, mail);

			// Execution des requêtes
			ResultSet rset = stmt.executeQuery();
			System.out.println("Your account has been properly deleted from our database");
			rset.close();

			// Commit
			this.conn.commit();
			
			// Fermeture
			stmt.close();
		}
		catch (SQLException e) {
			System.err.println("Failed to delete your account");
			e.printStackTrace(System.err);
		}
	}
	
}