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

    /** Titre du festival */
    private String titre;

    /** Identifiant de l'illustration, au sein des ressources de type Drawable */
    private int illustration;

    private int idFestival;

    /**
     * Constructeur avec en argument les valeurs des 2 attributs
     * @param titre titre du festival
     * @param illustration identifiant de l'illustration du festival
     */
    public InfosFestival(String titre, int illustration, int idFestival) {
        this.titre = titre;
        this.illustration = illustration;
        this.idFestival = idFestival;
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

    public int getIdFestival() {
        return idFestival;
    }
}