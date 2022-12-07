package templates;

import demo.Loader;
import java.sql.*;
import java.util.*;

public class TemplateOubli {
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String USER = "rocchias";
	static final String PASSWD = "rocchias";
	private Loader loader;
	public TemplateOubli(/* Rajouter un argument loader dans la version finale pour faire n requêtes semblables avant de close */) {
		try {
			this.loader = new Loader();
			// Creation de la requete
			PreparedStatement stmt = this.loader.getConnection().prepareStatement("UPDATE Clients_Ano SET mail_C = '' WHERE mail_C = ?");
			PreparedStatement stmt2 = this.loader.getConnection().prepareStatement("DELETE FROM Clients WHERE mail_C = ? AND pwd = ?");
			PreparedStatement stmt3 = this.loader.getConnection().prepareStatement("SELECT * FROM Clients WHERE mail_C = ? AND pwd = ?");
			
			// Récupération du mail du compte à supprimer et vérification du mdp
			Scanner scan = new Scanner(System.in);
			System.out.println("Write the email address of the account to delete");
			String mail = scan.next();
			stmt.setString(1, mail);
			stmt2.setString(1, mail);
			stmt3.setString(1, mail);
			System.out.println("Confirm with your password");
			String pwd = scan.next();
			stmt2.setString(2, pwd);
			stmt3.setString(2, pwd);
			
			ResultSet rset3 = stmt3.executeQuery();
			if(rset3.next()) {
				ResultSet rset = stmt.executeQuery();
				ResultSet rset2 = stmt2.executeQuery();
				System.out.println("Your account has been properly deleted from our database");
				rset.close();
				rset2.close();
			}
			else {
				System.out.println("Couldn't delete, did you enter the correct mail and password ?");
			}
			
			// Fermeture
			scan.close();
			rset3.close();
			stmt.close();
			stmt2.close();
			stmt3.close();
			this.loader.close();
		} 
		catch (SQLException e) {
			System.err.println("Failed");
			e.printStackTrace(System.err);
		}
	}
	
	public static void main(String args[]) {
		new TemplateOubli();
		}
	}

