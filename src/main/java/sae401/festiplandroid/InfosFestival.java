/*
 * InfosFestival.java                                    20 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

/**
 * Classe modélisant les informations relatives à un festival
 *
 * @author Enzo Cluzel
 * @author Lucas Descriaud
 * @author Loïc Faugières
 * @author Simon Guiraud
 */
public class InfosFestival {

    private String description;
    /** Titre du festival */
    private String titre;

    /** Identifiant de l'illustration, au sein des ressources de type Drawable */
    private int illustration;

    private int idFestival;

    private boolean favori;

    private String dateDeb;

    private String dateFin;

    /**
     * Festival caractérisé par son titre, son illustration, son identifiant,
     * sa date de début, sa date de fin et sa description.
     */
    public InfosFestival(String titre, int illustration, int idFestival, boolean favori,
                         String dateDeb, String dateFin, String description) {
        this.titre = titre;
        this.illustration = illustration;
        this.idFestival = idFestival;
        this.favori = favori;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.description = description;
    }
    /**
     * Renvoie le titre du festival
     * @return une chaîne contenant le titre du festival
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Renvoie l'identifiant de l'illustration du festival
     * @return un entier contenant l'identifiant du festival
     */
    public int getIllustration() {
        return illustration;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public int getIdFestival() {
        return idFestival;
    }

    public int getDescription() {
        return idFestival;
    }

    public boolean isFavori() {
        return favori;
    }

    public void setFavori(boolean favori) {
        this.favori = favori;
    }
}