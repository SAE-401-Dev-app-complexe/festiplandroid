package sae401.festiplandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Festivals extends AppCompatActivity {
private final String AUCUN_FESTIVAL_PROGRAMME
    = "Aucun festival n'est programmé actuellement.";

    private final String AUCUN_FESTIVAL_FAVORI
    = "Vous n'avez ajouté aucun festival à vos favoris.";

    private final String FESTIVAL_AJOUTE = "Festival n°%d ajouté aux favoris";

    private final String FESTIVAL_RETIRE = "Festival n°%d retiré des favoris";

    public final static String CLE_TYPE_FESTIVAL = "typeFestivalCle";

    private final int NOMBRE_FESTIVAL_PAGE = 2;

    private FestivalsAdapter adaptateur;

    private String urlFestivalsProgrammes;

    private String urlFestivalsFavoris;

    private String urlAjouterFavori;

    private String urlSupprimerFavori;

    public enum TYPE_FESTIVALS {;
        public static final String PROGRAMMES = "Programmes";
        public static final String FAVORIS = "Favoris";
    }

    private ActivityResultLauncher<Intent> lanceurFestivalsDetails;

    private ArrayList<JSONObject> festivalsStockes;

    private int page;

    private TextView chargementDonnees;

    private TableRow boutons;

    private Button boutonSuivant;

    private Button boutonPrecedent;

    private String typeFestivals;

    /**
     * Liste source des données à afficher :
     * chaque élément contient une instance de InfosFestival
     */
    private ArrayList<InfosFestival> listeFestivals;

    /**
     * Element permettant d'afficher la liste des festivals
     */
    private RecyclerView festivalsRecyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festivals);

        urlFestivalsProgrammes = getString(R.string.lien_api) + "festivals";
        urlFestivalsFavoris = getString(R.string.lien_api) + "favoris";
        urlAjouterFavori = getString(R.string.lien_api) + "ajouterFavori";
        urlSupprimerFavori = getString(R.string.lien_api) + "supprimerFavori";

        ActionBar barre = getSupportActionBar();

        barre.setDisplayShowTitleEnabled(false);
        barre.setDisplayShowCustomEnabled(true);
        barre.setCustomView(R.layout.action_bar);
        barre.setBackgroundDrawable(getResources().getDrawable(R.drawable.fond_barre_action));

        chargementDonnees = findViewById(R.id.message_chargement);
        boutons = findViewById(R.id.boutons);
        boutonPrecedent = findViewById(R.id.bouton_precedent);
        boutonSuivant = findViewById(R.id.bouton_suivant);

        festivalsRecyclerView = findViewById(R.id.liste_festivals);

        typeFestivals = getIntent().getStringExtra(CLE_TYPE_FESTIVAL);
        page = 1;

        listeFestivals = new ArrayList<>();

        LinearLayoutManager gestionnaireLineaire = new LinearLayoutManager(this);
        festivalsRecyclerView.setLayoutManager(gestionnaireLineaire);

        /*
         * On crée un adaptateur personnalisé et permettant de gérer spécifiquement
         * l'affichage des instances de type InfosFestival en tant que item de la liste.
         * Cet adapatateur est associé au RecyclerView
         */
        adaptateur = new FestivalsAdapter(this, listeFestivals);
        festivalsRecyclerView.setAdapter(adaptateur);

        festivalsStockes = new ArrayList<>();

        if (typeFestivals.equals(TYPE_FESTIVALS.PROGRAMMES)) {
            chargerFestivals(TYPE_FESTIVALS.PROGRAMMES, urlFestivalsProgrammes,
                             AUCUN_FESTIVAL_PROGRAMME);
        } else {
            chargerFestivals(TYPE_FESTIVALS.FAVORIS, urlFestivalsFavoris,
                             AUCUN_FESTIVAL_FAVORI);
        }

        lanceurFestivalsDetails =
            registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::retourDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int optionChoisie = item.getItemId();

        if (optionChoisie == R.id.festivals_programmes) {
            chargerFestivals(TYPE_FESTIVALS.PROGRAMMES, urlFestivalsProgrammes,
                             AUCUN_FESTIVAL_PROGRAMME);
            typeFestivals = TYPE_FESTIVALS.PROGRAMMES;
        } else if (optionChoisie == R.id.festivals_favoris) {
            chargerFestivals(TYPE_FESTIVALS.FAVORIS, urlFestivalsFavoris,
                             AUCUN_FESTIVAL_FAVORI);
            typeFestivals = TYPE_FESTIVALS.FAVORIS;
        } else if (optionChoisie == R.id.deconnexion) {
            ApiManager.setCleApi(null);
            deconnecter();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Déconnecte l'utilisateur et le redirige vers la page de connexion.
     */
    private void deconnecter() {
        Intent pageConnexion = new Intent(this, Connexion.class);
        startActivity(pageConnexion);
    }

    private void retourDetails(ActivityResult resultat) { }

    /**
     * Affiche les festivals de la page suivante
     * @param v le bouton appuyé
     */
    public void pageSuivante(View v){
        if (page < (int) Math.ceil((float) festivalsStockes.size() / NOMBRE_FESTIVAL_PAGE)) {
            page +=1;
            afficherPage();
        }
    }

    /**
     * Affiche les festivals de la page précédente
     * @param v le bouton appuyé
     */
    public void pagePrecedente(View v)  {
        if(page > 1) {
            page -=1;
            afficherPage();
        }
    }

    /**
     * Méthode appelée lors du clic sur un festival.
     * @param vue La vue actuelle.
     */
    public void clicFestival(View vue) {
        Intent pageDetails = new Intent(this, DetailsFestival.class);

        InfosFestival festival = listeFestivals.get((Integer) vue.getTag());

        Toast.makeText(this, "Festival n°" + festival.getIdFestival(),
                       Toast.LENGTH_SHORT).show();

        pageDetails.putExtra("idFestival", festival.getIdFestival());
        pageDetails.putExtra("titre", festival.getTitre());
        pageDetails.putExtra("description", festival.getDescription());
        pageDetails.putExtra("dates", "Du "+ festival.getDateDeb() + "\nau " + festival.getDateFin());

        lanceurFestivalsDetails.launch(pageDetails);
    }

    /**
     * Méthode appelée lors du clic sur le bouton de favori.
     * @param vue La vue actuelle.
     */
    public void clicFavori(View vue)  {
        Drawable.ConstantState etoileActive;

        String message;

        JSONObject donnees;

        ImageButton boutonFavori = (ImageButton) vue;

        int position = (Integer) boutonFavori.getTag();

        InfosFestival festival = listeFestivals.get(position);

        int idFestivalClique = festival.getIdFestival();

        try {
            donnees = new JSONObject().put("idFestival", idFestivalClique);

            etoileActive
            = getResources().getDrawable(R.drawable.etoile_active).getConstantState();

            // Si l'étoile est active, on la désactive
            if (boutonFavori.getDrawable().getConstantState().equals(etoileActive)) {
                boutonFavori.setImageResource(R.drawable.etoile_inactive);
                message = String.format(FESTIVAL_RETIRE, idFestivalClique);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                ApiManager.appelApiSansReponse(urlSupprimerFavori,this, donnees, Request.Method.POST);

                retirerFavori(position, typeFestivals.equals(TYPE_FESTIVALS.FAVORIS));
            } else {
                boutonFavori.setImageResource(R.drawable.etoile_active);
                message = String.format(FESTIVAL_AJOUTE, idFestivalClique);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                ApiManager.appelApiSansReponse(urlAjouterFavori,this, donnees, Request.Method.POST);

                ajouterFavori(position);
            }

            // Si la page est celle des favoris, on la réaffiche
            if (typeFestivals.equals(TYPE_FESTIVALS.FAVORIS)) {
                adaptateur.notifyDataSetChanged();
                if (festivalsStockes.size() > 0) {
                    if (listeFestivals.size() == 0) {
                        pagePrecedente(vue);
                    } else {
                        afficherPage();
                    }
                } else {
                    afficherMessageErreur(AUCUN_FESTIVAL_FAVORI);
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Une erreur est survenue",
                           Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Rend faux l'attribut "favori" d'un festival.
     * @param positionFestival La position du festival dans la liste.
     */
    private void retirerFavori(int positionFestival, boolean pageFavoris) {
        int position;

        listeFestivals.get(positionFestival).setFavori(false);

        position = (page - 1) * 2 + positionFestival;

        if (pageFavoris) {
            festivalsStockes.remove(position);
            listeFestivals.remove(positionFestival);
        } else {
            JSONObject festivalStocke = festivalsStockes.get(position);

            try {
                festivalStocke.put("favoris", 0);
            } catch (JSONException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Rend vrai l'attribut "favori" d'un festival.
     * @param positionFestival La position du festival dans la liste.
     */
    private void ajouterFavori(int positionFestival) {
        int position;

        listeFestivals.get(positionFestival).setFavori(true);

        position = (page - 1) * 2 + positionFestival;

        JSONObject festivalStocke = festivalsStockes.get(position);

        try {
            festivalStocke.put("favoris", 1);
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    /**
     * Affiche les festivals de la page actuelle.
     */
    public void afficherPage() {
        listeFestivals.clear();

        // Permet de demander à l'adapteur d'ajouter
        // les données qui vont être ajoutées
        adaptateur.notifyDataSetChanged();

        try {
            int pageDebut = (page - 1) * NOMBRE_FESTIVAL_PAGE ;
            int pageFin = pageDebut + NOMBRE_FESTIVAL_PAGE;

            for (int num = pageDebut; num < festivalsStockes.size() && num < pageFin; num++) {
                JSONObject festivalJson = festivalsStockes.get(num);

                int idFestival = festivalJson.getInt("idFestival");
                String titre = festivalJson.getString("titre");
                String description = festivalJson.getString("description");
                String dateDeb = festivalJson.getString("dateDebut");
                String dateFin = festivalJson.getString("dateFin");

                boolean favoris;

                try {
                    favoris = festivalJson.getInt("favoris") == 1;
                } catch (JSONException e) {
                    favoris = true;
                }

                listeFestivals.add(new InfosFestival(titre, R.drawable.default_illustration,
                                                     idFestival, favoris, dateDeb,
                                                     dateFin,description));
            }

            boutons.setVisibility(festivalsStockes.size() > NOMBRE_FESTIVAL_PAGE
                                  ? View.VISIBLE
                                  : View.GONE);

            boutonPrecedent.setVisibility(page > 1 ? View.VISIBLE : View.GONE);
            boutonSuivant.setVisibility(page < (int) Math.ceil((float) festivalsStockes.size() / NOMBRE_FESTIVAL_PAGE)
                                        ? View.VISIBLE
                                        : View.GONE);
        } catch (JSONException e) { System.err.println(e);}
    }

    private void chargerFestivals(String type, String url, String messageErreur) {
        afficherMessageErreur(getString(R.string.festivals_chargement_donnees));

        ApiManager.appelApiArray(url, this, new CallbackApi<JSONArray>() {
            @Override
            public void onReponsePositive(JSONArray reponseApi) {
                listeFestivals.clear();
                try {
                    festivalsStockes.clear();
                    JSONArray festivals = reponseApi;

                    if (festivals.length() == 0) {
                        afficherMessageErreur(messageErreur);
                    } else {
                        affichageNominal();

                        for (int i = 0; i < festivals.length(); i++) {
                            JSONObject festival = festivals.getJSONObject(i);
                            festivalsStockes.add(festival);
                        }
                    }

                    page = 1;
                    afficherPage();
                } catch (JSONException e) {
                    afficherMessageErreur(messageErreur);
                }
            }

            @Override
            public void onReponseErreur(String erreur) {
                afficherMessageErreur(erreur);
            }
        },null, Request.Method.GET);
    }

    /**
     * Affiche un message d'erreur en haut de la page.
     * @param message Le message d'erreur à afficher.
     */
    private void afficherMessageErreur(String message) {
        chargementDonnees.setText(message);
        chargementDonnees.setVisibility(View.VISIBLE);
        festivalsRecyclerView.setVisibility(View.GONE);
        boutons.setVisibility(View.GONE);
    }

    /**
     * Affiche la liste des festivals, les boutons et cache le message d'erreur.
     */
    private void affichageNominal() {
        chargementDonnees.setVisibility(View.GONE);
        festivalsRecyclerView.setVisibility(View.VISIBLE);
        boutons.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (false) {
            super.onBackPressed();
        }
    }
}
