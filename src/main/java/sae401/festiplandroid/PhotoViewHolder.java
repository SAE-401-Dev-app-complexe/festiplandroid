package sae401.festiplandroid;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Description du contenant des items de la liste de type RecyclerView
 * permettant d'afficher des photos de Paris.
 * @author C. Servières
 * @version 1.0
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder{
    /**
     * TextView qui contient le libellé de la photo
     */
    private TextView libellePhoto;
    /**
     * ImageView qui contient la photo
     */
    private ImageView laPhoto;
    /**
     * Constructeur avec en argument une vue correspondant
     * à un item de la liste
     * Le constructeur permet d'initialiser les identifiants des
     * widgets déclarés en tant qu'attributs
     * @param itemView vue décrivant l'affichage d'un item de la liste
     */
    public PhotoViewHolder(View itemView) {
        super(itemView);
        libellePhoto = (TextView) itemView.findViewById(R.id.titre);
        laPhoto = (ImageView) itemView.findViewById(R.id.image);
    }
    /**
     * Permet de placer les informations contenues dans l'argument
     * dans les widgets d'un item de la liste
     * @param maPhoto l'instance qui doit être affichée
     */
    public void bind(PhotoParis maPhoto){
        libellePhoto.setText(maPhoto.getLibelle());
        laPhoto.setImageResource(maPhoto.getPhoto());
    }
}