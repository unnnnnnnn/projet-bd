package src.demo;

import java.util.List;

public class Restaurant {

    private String mailR;
	private String nomR;
	private String telR;
	private String adrR;
	private int nbPlacesR;
	private String presentationR;
	private long noteMoy;
    private List<Categorie> categories;

    public Restaurant(String mailR, String nomR, String telR, String adrR, int nbPlacesR, String presentationR, long noteMoy) {
        setMailR(mailR);
        setNomR(nomR);
        setTelR(telR);
        setAdrR(adrR);
        setNbPlacesR(nbPlacesR);
        setPresentationR(presentationR);
        setNoteMoy(noteMoy);
    }

    public Restaurant(String mailR, String nomR, String telR, String adrR, int nbPlacesR, String presentationR, long noteMoy, List<Categorie> categories) {
        setMailR(mailR);
        setNomR(nomR);
        setTelR(telR);
        setAdrR(adrR);
        setNbPlacesR(nbPlacesR);
        setPresentationR(presentationR);
        setNoteMoy(noteMoy);
        setCategories(categories);
    }

    public String getMailR() {
        return this.mailR;
    }

    public void setMailR(String mailR) {
        this.mailR = mailR;
    }

    public String getNomR() {
        return this.nomR;
    }

    public void setNomR(String nomR) {
        this.nomR = nomR;
    }

    public String getTelR() {
        return this.telR;
    }

    public void setTelR(String telR) {
        this.telR = telR;
    }

    public String getAdrR() {
        return this.adrR;
    }

    public void setAdrR(String adrR) {
        this.adrR = adrR;
    }

    public int getNbPlacesR() {
        return this.nbPlacesR;
    }

    public void setNbPlacesR(int nbPlacesR) {
        this.nbPlacesR = nbPlacesR;
    }

    public String getPresentationR() {
        return this.presentationR;
    }

    public void setPresentationR(String presentationR) {
        this.presentationR = presentationR;
    }

    public long getNoteMoy() {
        return this.noteMoy;
    }

    public void setNoteMoy(long noteMoy) {
        this.noteMoy = noteMoy;
    }

    public List<Categorie> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
    
    @Override
    public String toString() {
        String retString = "Restaurant : " + getNomR()
        + " de mail : " + getMailR()
        + " à l'adresse : " + getAdrR()
        + " de numéro tél : "  + getTelR()
        + " avec une note moyenne de : " + getNoteMoy();

        if (!(this.categories == null)) {
            retString.concat(" et a comme catégorie(s) : ");
            for (Categorie categorie: this.categories) {
                retString.concat(categorie + ", ");
            }
        }

        return retString;
    }
}
