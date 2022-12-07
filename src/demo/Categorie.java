package src.demo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {

    private String nomCategorie;
    private List<Categorie> catFilles = new ArrayList<Categorie>();
    
    public Categorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getNomCategorie() {
        return this.nomCategorie;
    }

    public List<Categorie> getCatFilles() {
        return catFilles;
    }

    public void addCat(Categorie cat) {
        this.catFilles.add(cat);
    }

    @Override
    public String toString() {
        return nomCategorie;
    }
}