/*
 * FestivalsViewHolder.java                              20 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Description du contenant des items de la liste de type RecyclerView
 * permettant d'afficher des festivals.
 *
 * @author Enzo Cluzel
 * @author Lucas Descriaud
 * @author Loïc Faugières
 * @author Simon Guiraud
 */
public class FestivalsViewHolder extends RecyclerView.ViewHolder {

    /**
     * TextView qui contient le titre du festival
     */
    private TextView titre;

    /**
     * ImageView qui contient l'illustration du festival
     */
    private ImageView illustration;

    /**
     * ImageButton qui permet de marquer un festival en favori
     */
    public ImageButton boutonFavori;

    /**
     * Constructeur avec en argument une vue correspondant
     * à un item de la liste
     * Le constructeur permet d'initialiser les identifiants des
     * widgets déclarés en tant qu'attributs
     * @param itemView vue décrivant l'affichage d'un item de la liste
     */
    public FestivalsViewHolder(View itemView) {
        super(itemView);
        titre = (TextView) itemView.findViewById(R.id.titre);
        illustration = (ImageView) itemView.findViewById(R.id.illustration);
        boutonFavori = (ImageButton) itemView.findViewById(R.id.bouton_favori);
    }

    /**
     * Permet de placer les informations contenues dans l'argument
     * dans les widgets d'un item de la liste
     * @param festival l'instance qui doit être affichée
     */
    public void bind(InfosFestival festival) {
        titre.setText(festival.getTitre());
        illustration.setImageResource(festival.getIllustration());
    }
}