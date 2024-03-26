/*
 * InfosFestival.java                                    20 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

import java.io.Serializable;

/**
 * Classe modélisant les informations relatives à un festival
 *
 * @author Enzo Cluzel
 * @author Lucas Descriaud
 * @author Loïc Faugières
 * @author Simon Guiraud
 */
public class InfosFestival implements Serializable {

    /** Clé du festival */
    public final static String CLE_FESTIVAL = "festivalCle";

    /** Id du festival */
    private int idFestival;

    /** Titre du festival */
    private String titre;

    /** Description du festival */
    private String description;

    /** Lien de l'illustration ou identifiant de celle par défaut */
    private int illustration;

    /** Festival favori par rapport à l'utilisateur */
    private boolean favori;

    /** Date de début du festival */
    private String dateDeb;

    /** Date de fin du festival */
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

    /**
     * Renvoie la date de fin du festival
     * @return une chaîne correspondant à la date de fin
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Renvoie la date de début du festival
     * @return une chaîne correspondant à la date de début
     */
    public String getDateDeb() {
        return dateDeb;
    }

    /**
     * Renvoie l'identifiant du festival
     * @return un entier correspondant à l'identifiant
     */
    public int getIdFestival() {
        return idFestival;
    }

    /**
     * Renvoie la description du festival
     * @return une chaîne correspondant à la description
     */
    public int getDescription() {
        return idFestival;
    }

    /**
     * @return true si le festival est un festival favori pour l'utilisateur sinon false
     */
    public boolean isFavori() {
        return favori;
    }

    /**
     * Permet de modifier l'état de favori pour un festival
     */
    public void setFavori(boolean favori) {
        this.favori = favori;
    }
}