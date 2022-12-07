package demo;
import java.sql.*;
import java.util.ArrayList;

public class Commande {
	private Connection connection;
	private String mailResto;
	private String typeCmd;
	private String date;
	private int heure;
	private int minutes;
	private int idCl;
	private int prix;
	private ArrayList<String> plats;
	private ArrayList<Integer> quantites;
	
	public Commande(Connection connection, String mailResto, String typeCmd,
					String date, int heure, int minutes, int idCl,
					int prix, ArrayList<String> plats, ArrayList<Integer> quantites){
		this.connection = connection;
		this.mailResto = mailResto;
		this.typeCmd = typeCmd;
		this.date = date;
		this.heure = heure;
		this.minutes = minutes;
		this.idCl = idCl;
		this.plats = plats;
		this.quantites = quantites;
		this.prix = prix;
	}
	
	/**
	 * Vérifie que le nombre de places restant dans le restaurant est >= au nombre de places libres
	 * @return
	 */
	private boolean testPossible (int nbPlaces) {
		Request request = new Request(this.connection,"SELECT nbPlacesR FROM Restaurant WHERE mailR = " + this.mailResto + ";");
		ResultSet result = request.execute();
		try {
			int nbPlacesR = result.getInt("nbPlacesR");
			Request Rcomm;
			if (nbPlaces > nbPlacesR) {
				return false;
			}
			if (this.heure <= 14 || this.heure >= 12) {
				Rcomm = new Request(this.connection,"SELECT nbCouvertsSP FROM Sur_Place WHERE mailR = " + this.mailResto + 
						"AND dateCmd = " + this.date + "AND hCmd <= 14 AND hCmd >= 12");
			} else if (this.heure >= 19 || this.heure <= 23) {
				Rcomm = new Request(this.connection,"SELECT nbCouvertsSP FROM Sur_Place WHERE mailR = " + this.mailResto + 
						"AND dateCmd = " + this.date + "AND hCmd <= 23 AND hCmd >= 19");
			} else {
				return false;
			}
			int placeOccupe = 0;
			ResultSet resultat = Rcomm.execute();
			while (resultat.next()) {
				placeOccupe += resultat.getInt("nbCouvertsSP");
			}
			if (placeOccupe + nbPlaces > nbPlacesR) {
				return false;
			}
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Ajoute les différents plats à la commande
	 */
	public void ajout_plat(){
		for (int indice = 0; indice <plats.size(); indice++) {
			Request ajout_plat = new Request(this.connection,"insert into ContientPlats values ("
			+ this.date + ", " + this.heure + ", " + this.minutes + ", " + plats.get(indice) + ", " + this.idCl
			+ ", " + this.mailResto + ", " + quantites.get(indice));
			ajout_plat.execute();
		}
	}

	/**
	 * Ajoute une commande en livraision
	 * @param adrLiv Adresse de livraison
	 */
	public void execLiv(String adrLiv) {
		Request request = new Request(this.connection,"insert into Livraison values (" +
		this.date + "," + this.heure + "," + this.minutes + "," + this.idCl + "," +
		this.mailResto + "," + adrLiv + ",NULL,NULL");
		request.execute();
		execComm();
		ajout_plat();
	}
	public void execDefault() {
		execComm();
}
	/**
	 * Ajoute une commande sur place si le nombre de places restantes dans le restaurant est suffisant
	 * @param nbPlaces Nombre de place de la commande
	 * @param hArriv Heure d'arrivée des clients
	 * @param minArriv minute d'arrivée des clients
	 */
	public void execSP(int nbPlaces, int hArriv, int minArriv) {
		if (testPossible(nbPlaces)) {
			Request request = new Request(this.connection,"insert into Sur_place values (" +
			this.mailResto + "," + this.idCl + "," + this.date + "," + this.heure + "," +
			this.minutes + "," + nbPlaces + "," + hArriv + "," + minArriv);
			request.execute();
			execComm();
			ajout_plat();
		}
		else {
			System.out.println("Le nombre de places restantes dans ce restaurant est insuffisant \n");
			System.out.println("Veuillez choisir un autre restaurant \n");
		}
}
	/**
	 * Ajoute une commande classique
	 */
	private void execComm() {
		Request request2 = new Request(this.connection,"insert into Commande values (" + this.mailResto + ","
			+ this.idCl + "," + this.date + "," + this.heure + "," + this.minutes + "," + this.prix + "," + this.typeCmd + ", validée");
			request2.execute();
		}
}