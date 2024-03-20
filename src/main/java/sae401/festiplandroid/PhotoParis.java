package sae401.festiplandroid;

public class PhotoParis {
    /** Libellé de la photo */
    private String libelle;
    /** Identifiant de la photo, au sein des ressources de type Drawable */
    private int photo;
    /**
     * Constructeur avec en argument les valeurs des 2 attributs
     * @param libelle libellé de la photo
     * @param photo identifiant de la photo
     */
    public PhotoParis(String libelle, int photo) {
        this.libelle = libelle;
        this.photo = photo;
    }
    /**
     * Renvoie le libellé de la photo
     * @return une chaîne contenant le libellé
     */
    public String getLibelle() {
        return libelle;
    }
    /**
     * Renvoie l'identifiant de la photo
     * @return un entier contenant l'identifiant de la photo
     */
    public int getPhoto() {
        return photo;
    }
}