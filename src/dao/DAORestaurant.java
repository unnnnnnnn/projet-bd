package src.dao;
import java.security.Permission;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.apple.eawt.event.RotationListener;

import src.demo.Categorie;
import src.demo.Restaurant;

public class DAORestaurant {

    private Connection connection;
    private List<Categorie> catList = new ArrayList<Categorie>();

    public DAORestaurant(Connection connection) {
        this.connection = connection;
    }

    public Restaurant constructRestaurant(ResultSet rSet, List<Categorie> categories) throws SQLException {

        Restaurant restaurant = new Restaurant(rSet.getString("mailR"), 
        rSet.getString("nomR"), 
        rSet.getString("telR"), 
        rSet.getString("adrR"), 
        rSet.getInt("nbPlacesR"), 
        rSet.getString("presentationR"), 
        rSet.getLong("noteMoy"),
        categories);

        return restaurant;
    }

    private List<Categorie> getCatMemo(String cat) throws SQLException {
        PreparedStatement getCat = connection.prepareStatement("SELECT * FROM Categories JOIN APourCatParent ON APourCatParent.nomCatParent = ? JOIN APourCatParent ON APourCatParent.nomCatFils = Categories.nomCategorie");
        getCat.setString(1, cat);
        ResultSet rSet = getCat.executeQuery();

        while (rSet.next()) {
            String catFilleNom = rSet.getString("nomCategorie");
            Categorie catFille = new Categorie(catFilleNom);
            if (!catList.contains(catFille)) catList.add(catFille);

            rSet.close();
            getCat.close();

            return getCatMemo(catFilleNom);
        }

        rSet.close();
        getCat.close();

        return null;
        
    }
    
    public void getCategories(String cat) throws SQLException {

        getCatMemo(cat);
        List<Restaurant> restListCat = new ArrayList<Restaurant>();

        for (Categorie categorie: catList) {
            PreparedStatement getMailRest = connection.prepareStatement("SELECT mailR FROM FaitPartieCat WHERE nomCategorie LIKE ?");
            getMailRest.setString(1, categorie.getNomCategorie());
            ResultSet rSet = getMailRest.executeQuery();
            while (rSet.next()) {

                PreparedStatement getRest = connection.prepareStatement("SELECT * FROM Restaurants WHERE mailR LIKE ?");
                getRest.setString(1, rSet.getString(1));
                ResultSet rRestSet = getRest.executeQuery();

                Restaurant restaurant = constructRestaurant(rRestSet, null);
                if (restListCat.contains(restaurant)) restListCat.add(restaurant);

                rRestSet.close();
                getRest.close();
            }

            rSet.close();
            getMailRest.close();

        }

        for (Restaurant restaurant: restListCat) {
            System.out.println(restaurant);
        }
    }

    public void getFromDay(String jour, int hDebut) throws SQLException {
        PreparedStatement dayStmt = null;

        if (jour == null) {
            dayStmt = connection.prepareStatement("SELECT * FROM OuvrePendant WHERE hDebut = ?");
            dayStmt.setInt(1, hDebut);
        }
        if (hDebut == 0) {
            dayStmt = connection.prepareStatement("SELECT * FROM OuvrePendant WHERE jour LIKE ?");
            dayStmt.setString(1, jour);
        }
        if (jour != null && hDebut > 0) {
            dayStmt = connection.prepareStatement("SELECT * FROM OuvrePendant WHERE jour LIKE ? AND hDebut = ?");
            dayStmt.setString(1, jour);
            dayStmt.setInt(2, hDebut);
        }

        ResultSet rSet = dayStmt.executeQuery();

        while (rSet.next()) {
            Restaurant restaurant = constructRestaurant(rSet, null);
            System.out.println(restaurant);
        }
        
        rSet.close();
        dayStmt.close();

    }

    public void getRestaurantInfos(String nomR) throws SQLException {
        PreparedStatement getRest = connection.prepareStatement("SELECT * FROM Restaurants WHERE nomR LIKE ?");
        getRest.setString(1, nomR);
        ResultSet rSet = getRest.executeQuery();

        PreparedStatement getCat = connection.prepareStatement("SELECT * FROM FaitPartieCat WHERE mailR LIKE ?");
        getCat.setString(1, rSet.getString("mailR"));
        ResultSet rCatSet = getRest.executeQuery();

        List<Categorie> categories = new ArrayList<Categorie>();

        while (rCatSet.next()) {
            categories.add(new Categorie(rCatSet.getString("nomCategorie")));
        }

        Restaurant restaurant = constructRestaurant(rSet, categories);

        System.out.println(restaurant);

        rCatSet.close();
        rSet.close();
        getCat.close();
        getRest.close();
    }
}
