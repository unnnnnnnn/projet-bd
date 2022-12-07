package demo;
import java.sql.*;
import java.util.*;
public class Request {
	private Connection connect;
	private String cmd;
	
	/**
	 * Constructeur de la Request
	 * @param connect Conection initialisé précédement
	 * @param cmd La commande que l'on veut éxécuter
	 */
	public Request (Connection connect, String cmd) {
		this.cmd = cmd;
		this.connect = connect;
	}
	
	/**
	 * Execute la commande et demande des arguments lors des PreparedStatement
	 * si necessaire
	 * @return Le ResultSet qui nous permet d'avoir nos données
	 */
	public ResultSet execute() {
		ResultSet result = null;
		try {
			int nb = 0;
			for (int i=0; i < cmd.length(); i++)
			{
				if (cmd.charAt(i) == '?') {
					nb++;
				}
			}
			if (nb!=0) {
				PreparedStatement stm = this.connect.prepareStatement(cmd);
				Scanner scan = new Scanner(System.in);
				System.out.println(cmd);
				for (int i = 0; i<nb; i++) {
					System.out.println("Argument " + i + ": ");
					String argu = scan.next();
					stm.setString(i, argu);
					scan.nextLine ();
				}
				stm.close();
				scan.close();
				result = stm.executeQuery();
				
			} else {
				Statement stm = this.connect.createStatement();
				result = stm.executeQuery(cmd);
				stm.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}