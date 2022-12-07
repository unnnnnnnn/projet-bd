package templates;
import java.sql.*;
import demo.Loader;

public class Template {
	private Loader loader;
	public Template() {
		try {
			this.loader = new Loader();
			// Creation de la requete
			Connection connection = this.loader.getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Restaurants WHERE NoteMoy > ? AND Nom_R LIKE ?"); 
			stmt.setInt(1, 3);
			stmt.setString(2, "Le Rest%");
			ResultSet rset = stmt.executeQuery();
			while (rset.next ()) { 
				System.out.println("Restaurant : " + rset.getString("Nom_R")+ " - Note Moyenne : " + rset.getString("NoteMoy"));
			} 
			
			// Fermeture 
			rset.close();
			stmt.close();
			this.loader.close();
		} 
		catch (SQLException e) {
			System.err.println("Failed");
			e.printStackTrace(System.err);
		}
	}
	
	public static void main(String args[]) {
		new Template();
		}
	}

