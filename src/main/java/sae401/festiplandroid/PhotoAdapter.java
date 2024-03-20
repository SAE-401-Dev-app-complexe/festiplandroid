package sae401.festiplandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adaptateur spécifique pour afficher une liste de type RecyclerView
 * dont les items sont de type PhotoParis
 * @author Servières
 * @version 1.0
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    /**
     * Source de données à afficher par la liste
     */
    private List<PhotoParis> lesDonnees;

    /**
     * Constructeur avec en argument la liste source des données
     * @param donnees liste contenant les instances de type
     * PhotoParis que l'adapteur sera chargé de gérer
     */
    public PhotoAdapter(List<PhotoParis> donnees) {
        lesDonnees = donnees;
    }
    /**
     * Renvoie un contenant de type PhotoViewHolder qui permettra d'afficher
     * un élément de la liste
     */
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.vue_item_liste,
                viewGroup,false);
        return new PhotoViewHolder(view);
    }
    /**
     * On remplit un item de la liste en fonction de sa position
     */
    @Override
    public void onBindViewHolder(PhotoViewHolder myViewHolder, int position) {
        PhotoParis myObject = lesDonnees.get(position);
        myViewHolder.bind(myObject);
    }
    /**
     * Renvoie la taille de la liste
     */
    @Override
    public int getItemCount() {
        return lesDonnees.size();
    }
}