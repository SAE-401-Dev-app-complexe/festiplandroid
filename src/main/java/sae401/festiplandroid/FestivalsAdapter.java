/*
 * FestivalsAdapter.java                                 20 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adaptateur spécifique pour afficher une liste de type RecyclerView
 * dont les items sont de type InfosFestival
 *
 * @author Enzo Cluzel
 * @author Lucas Descriaud
 * @author Loïc Faugières
 * @author Simon Guiraud
 */
public class FestivalsAdapter extends RecyclerView.Adapter<FestivalsViewHolder> {
    /**
     * Source de données à afficher par la liste
     */
    private List<InfosFestival> lesDonnees;

    /**
     * Constructeur avec en argument la liste source des données
     * @param donnees liste contenant les instances de type
     * InfosFestival que l'adapteur sera chargé de gérer
     */
    public FestivalsAdapter(List<InfosFestival> donnees) {
        lesDonnees = donnees;
    }

    /**
     * Renvoie un contenant de type FestivalsViewHolder qui permettra d'afficher
     * un élément de la liste
     */
    @Override
    public FestivalsViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(
            viewGroup.getContext()).inflate(R.layout.item_liste,
            viewGroup,false);
        return new FestivalsViewHolder(view);
    }
    /**
     * On remplit un item de la liste en fonction de sa position
     */
    @Override
    public void onBindViewHolder(FestivalsViewHolder myViewHolder, int position) {
        InfosFestival myObject = lesDonnees.get(position);
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