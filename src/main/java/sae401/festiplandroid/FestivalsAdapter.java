/*
 * FestivalsAdapter.java                                 20 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
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
     * Contexte de l'application
     */
    private Context context;

    /**
     * Source de données à afficher par la liste
     */
    private List<InfosFestival> lesDonnees;

    /**
     * Constructeur avec en argument la liste source des données
     * @param donnees liste contenant les instances de type
     * InfosFestival que l'adapteur sera chargé de gérer
     */
    public FestivalsAdapter(Context context, List<InfosFestival> donnees) {
        this.context = context;
        this.lesDonnees = donnees;
    }

    /**
     * Renvoie un contenant de type FestivalsViewHolder qui permettra d'afficher
     * un élément de la liste
     */
    @Override
    public FestivalsViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        System.out.println("imtemtyê"+itemType);
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
        System.out.println("binding");
        System.out.println(position);

        // Récupérer l'objet InfosFestival correspondant à cette position
        InfosFestival myObject = lesDonnees.get(position);

        // Lier les données à la vue holder
        myViewHolder.bind(myObject);

        // Associer l'ID du festival au bouton favori
        myViewHolder.boutonFavori.setTag(myObject.getIdFestival());



        // Associer l'ID du festival à l'item
        myViewHolder.itemView.setTag(myObject.getIdFestival());

        // Définir un écouteur de clic sur le bouton favori
        myViewHolder.boutonFavori.setOnClickListener(view -> {
            ((Festivals) context).clicFavori(view);
        });

        // Définir un écouteur de clic sur l'item
        myViewHolder.itemView.setOnClickListener(view -> {
            ((Festivals) context).clicFestival(view);
        });
    }

    /**
     * Renvoie la taille de la liste
     */
    @Override
    public int getItemCount() {
        return lesDonnees.size();
    }
}