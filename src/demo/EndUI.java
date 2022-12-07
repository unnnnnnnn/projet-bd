package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import src.dao.DAORestaurant;

import java.time.LocalDateTime;
public class EndUI {

    private final Loader loader;
    private final Scanner scan;

    public EndUI() {
        scan = new Scanner(System.in);
        loader = new Loader();
    }

    private String input(String message) {
        System.out.println(message);
        return scan.nextLine();
    }

    private boolean forget_me(Connection connection, String mail, String pswrd) {
        if (pswrd.equals(input("Delete account ?\nEnter password for confiramtion :"))) {
            new Oubli(connection, mail, pswrd);
            return true;
        }
        else {
            System.out.println("Incorrect password.");
            return false;
        }
    }
    private void commande(Connection connection, String mail) throws SQLException {
        String date = (new Date(System.currentTimeMillis())).toString();
        int heure = LocalDateTime.now().getHour();
        int min = LocalDateTime.now().getMinute();
        System.out.println(heure);

        PreparedStatement idClStmt = connection.prepareStatement("SELECT idCl FROM Clients where mailCl = ?");
        idClStmt.setString(1, mail);
        ResultSet idClSet = idClStmt.executeQuery();
        idClSet.next();
        int idCl = idClSet.getInt(1);
        idClStmt.close();
        idClSet.close();

        PreparedStatement mailRStmt = connection.prepareStatement("SELECT mailR FROM Restaurant where nomR = ?");
        mailRStmt.setString(1, input("Nom du Restaurant"));
        ResultSet mailRSet = mailRStmt.executeQuery();
        mailRSet.next();
        String mailR = mailRSet.getString(1);
        mailRStmt.close();
        mailRSet.close();

        PreparedStatement typeCmdStmt = connection.prepareStatement("SELECT nomTypeCmd FROM ProposeTypeCmd where mailR = ?");
        typeCmdStmt.setString(1, mailR);
        ResultSet typeCmdSet = typeCmdStmt.executeQuery();
        String typChoice = "";
        while (typeCmdSet.next()) {
            typChoice += typeCmdSet.getString(1) + "   ";
        }
        String typeCmd = input("Choisir un type de commande parmi : " + typChoice);
        typeCmdStmt.close();
        typeCmdSet.close();

        PreparedStatement platsStmt = connection.prepareStatement("SELECT nomP, prixP FROM Plat where mailR = ?");
        platsStmt.setString(1, mailR);
        ResultSet platsSet = platsStmt.executeQuery();
        HashMap<String, Integer> listPlats = new HashMap<>();
        while (platsSet.next()) {
            listPlats.put(platsSet.getString(1), platsSet.getInt(2));
        }
        platsStmt.close();
        platsSet.close();
        String carte = "";
        for (Map.Entry<String,Integer> entry : listPlats.entrySet()) {
            carte += entry.getKey() + "\t\t" + entry.getValue() + "€\n";
        }
        ArrayList<String> plats = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        int prix = 0;
        String plat;
        int quantite;
        while (true) {
            plat = input("Choisissez un plat parmi la carte\n\n"+carte);
            if (plat.equals("fin commande"))
                break;
            if (listPlats.containsKey(plat)) {
                plats.add(plat);
                quantite = Integer.valueOf(input("vous avez choisi " + plat + ". Quelle quantité ?"));
                prix += listPlats.get(plat) * quantite;
                quantities.add(quantite);
            }
            else {
                System.out.println("Incorrect entry.");
            }
        }

        new Commande(connection, mailR, typeCmd, date, heure, min, idCl, prix, plats, quantities);
    }

    private void execution() {

        /* Connection */
        Connection connection = loader.getConnection();
        String mail = input("Enter email");
        String pswrd = input("Enter Password");

        try {
        	PreparedStatement stmt = connection.prepareStatement("SELECT mdpCl FROM Clients where mailCl = ? AND mdpCl = ?");
            stmt.setString(1, mail);
            stmt.setString(2, pswrd);
        	ResultSet rset = stmt.executeQuery();

            if (rset.next()) {
                rset.close();
                stmt.close();
                /* Set up */
                try {
                    connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                    connection.setAutoCommit(false);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
    
                try {
                    String instruction;
                    outerloop:
                    while (true) {
                        /* get instruction */
                        instruction = input("Enter an instruction among : restaurant, command, forget_me, exit");
                        switch (instruction) {
                            case "restaurant":
                                DAORestaurant restaurantQuery = new DAORestaurant(connection);

                                restaurantInstruction = input("What do you want to do ? infos, category, day")

                                switch (restaurantInstruction) {
                                    case "infos":
                                    restaurantQuery.getRestaurantInfos("Enter a restaurant: ");
                                    break;

                                    case "category":
                                    restaurantQuery.getCategories(input("Enter a category to get associated restaurants: "));
                                    // should print all restaurants
                                    break;

                                    case "day":
                                    restaurantQuery.getFromDay(input("Enter a day (can be null): "), (int)input("Enter an opening hour (can be null): "));
                                    break;
                                }

                                break;
                            
                            case "command":
                                commande(connection, mail);
                                break;
    
                            case "forget_me":
                                if (forget_me(connection, mail, pswrd))
                                    break outerloop;
                                break;
                            
                            case "exit":
                                break outerloop;
                            
                            default:
                                System.out.println("Wrong instruction");
                                break;
                        }
                        assert true;
                    }
                }
                catch (Error e) {
                    e.printStackTrace();
                }
            }
            else {
                rset.close();
                stmt.close();
                System.out.println("Invalid Email/Password combination. Closing connexion.");
            }
    
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            /* Disconnect */
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        this.scan.close();
    }

    public static void main(String[] args) {
        EndUI self = new EndUI();
        self.execution();
    }
}