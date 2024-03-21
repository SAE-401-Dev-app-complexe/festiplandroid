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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Festivals extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    private final String FESTIVAL_AJOUTE = "Festival n°%d ajouté aux favoris";

    private final String FESTIVAL_RETIRE = "Festival n°%d retiré des favoris";

    private FestivalsAdapter adaptateur;

    private final String URL_FESTIVAL_FAVORIS = "http://10.0.2.2/API/testAPISAE/API/favoris";

    private final String URL_FESTIVAL_AJOUT_FAVORIS = "http://10.0.2.2/API/testAPISAE/API/ajouterFavoris";

    private final String URL_FESTIVAL_SUPPRIMER_FAVORIS = "http://10.0.2.2/API/testAPISAE/API/supprimerFavoris";

    private final String URL_FESTIVAL_PROGRAMMES =  "http://10.0.2.2/API/testAPISAE/API/festival";

    private final int NOMBRE_FESTIVAL_PAGE = 2;

    public enum TYPE_FESTIVALS {;
        public static final String PROGRAMMES = "Programmes";
        public static final String FAVORIS = "Favoris";
    }

    private ActivityResultLauncher<Intent> lanceurFestivalsDetails;

    private ArrayList<Integer> idFestivals;

    // A modifier si non nécessaire
    private ArrayList<JSONObject> festivalsStockes;

    private int page;

    private TextView chargementDonnes;

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
        //ApiManager.setCleApi("19b7eaf42fd7d743a252");
        ActionBar barre = getSupportActionBar();

        barre.setDisplayShowTitleEnabled(false);
        barre.setDisplayShowCustomEnabled(true);
        barre.setCustomView(R.layout.action_bar);
        barre.setBackgroundDrawable(getResources().getDrawable(R.drawable.fond_barre_action));

        chargementDonnes = findViewById(R.id.chargement);
        festivalsRecyclerView = findViewById(R.id.liste_festivals);
        initialiseListeFestivals();

        LinearLayoutManager gestionnaireLineaire = new LinearLayoutManager(this);
        festivalsRecyclerView.setLayoutManager(gestionnaireLineaire);
        //festivalsRecyclerView.getLayoutManager().addView(R.);
        /*
         * On crée un adaptateur personnalisé et permettant de gérer spécifiquement
         * l'affichage des instances de type InfosFestival en tant que item de la liste.
         * Cet adapatateur est associé au RecyclerView
         */
        adaptateur = new FestivalsAdapter(this, listeFestivals);
        festivalsRecyclerView.setAdapter(adaptateur);


        // STUB
        page = 1;
        typeFestivals = TYPE_FESTIVALS.PROGRAMMES; // TODO modifier par donnée envoyée pour pouvoir choisir entre favoris et programmés via menu en haut de page
        // FIN STUB

        festivalsStockes = new ArrayList<>();
        idFestivals = new ArrayList<>();

        chargerFestivalsProgrammes();

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
            chargerFestivalsProgrammes();
            typeFestivals = TYPE_FESTIVALS.PROGRAMMES;
        } else if (optionChoisie == R.id.festivals_favoris) {
            chargerFestivalsFavoris();
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
        // TODO faire en sorte qu'on ne puisse plus faire retour (via bouton tel) après la déconnexion
    }

    // Non nécessaires les informations sont gardés aprés changement de page
    private void retourDetails(ActivityResult resultat) {
      /*  if(resultat.getResultCode() == Activity.RESULT_OK) {
            //chargerFestivals();
        }
    */
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent pageDetails = new Intent(this,DetailsFestival.class);

        int idFestival = idFestivals.get(position);

        pageDetails.putExtra("idFestival",idFestival);

        lanceurFestivalsDetails.launch(pageDetails);
    }

    /**
     * Affiche les festivals de la page suivante
     * @param v le bouton appuyé
     */
    public void pageSuivante(View v){
        if(page < (int)Math.ceil((float)festivalsStockes.size()/NOMBRE_FESTIVAL_PAGE)) {
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

        int idFestival = (Integer) vue.getTag();

        Toast.makeText(this, "Festival n°" + idFestival, Toast.LENGTH_SHORT).show();

        pageDetails.putExtra("idFestival", idFestival);
        lanceurFestivalsDetails.launch(pageDetails);
    }

    /**
     * Méthode appelée lors du clic sur le bouton de favori.
     * @param vue La vue actuelle.
     */
    public void clicFavori(View vue)  {
        Drawable.ConstantState etoileActive;

        String message;

        ImageButton boutonFavori = (ImageButton) vue;

        int idFestival = idFestival = (Integer) boutonFavori.getTag();
        JSONObject donnees = null;
        System.out.println(idFestival);
        try {
            donnees = new JSONObject().put("idFestival",idFestival);
            System.out.println(donnees);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        etoileActive
        = getResources().getDrawable(R.drawable.etoile_active).getConstantState();

        // Si l'étoile est active, on la désactive
        if (boutonFavori.getDrawable().getConstantState().equals(etoileActive)) {
            message = String.format(FESTIVAL_RETIRE, idFestival);
            boutonFavori.setImageResource(R.drawable.etoile_inactive);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            ApiManager.appelApiObjet(URL_FESTIVAL_SUPPRIMER_FAVORIS,this, new CallbackApi<JSONObject>() {
                @Override
                public void onReponsePositive(JSONObject reponseApi) {}

                @Override
                public void onReponseErreur(String erreur) {}
            },donnees,Request.Method.PUT);

        } else {
            message = String.format(FESTIVAL_AJOUTE, idFestival);
            boutonFavori.setImageResource(R.drawable.etoile_active);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            // Renvoie da6a3d197f916d0a08c7 ???
            ApiManager.appelApiObjet(URL_FESTIVAL_AJOUT_FAVORIS,this, new CallbackApi<JSONObject>() {
                @Override
                public void onReponsePositive(JSONObject reponseApi) {}

                @Override
                public void onReponseErreur(String erreur) {}
            },donnees,Request.Method.PUT);

        }
    }

    /**
     * Affiche les festivals de la page actuelle
     */
    public void afficherPage()  {
        listeFestivals.clear();
        // Permet de demander à l'adapteur de d'ajouter les données qui vont être ajoutés
        adaptateur.notifyDataSetChanged();
        try {
            int pageDebut = (page-1) * NOMBRE_FESTIVAL_PAGE ;
            int pageFin = pageDebut + NOMBRE_FESTIVAL_PAGE;
            idFestivals.clear();
            System.out.println(festivalsStockes.size());
            //listeFestivals.clear();
            for (int num = pageDebut; num < festivalsStockes.size() && num < pageFin; num++) {
                JSONObject festivalJson = festivalsStockes.get(num);
                int idFestival = festivalJson.getInt("idFestival");
                String titre = festivalJson.getString("titre");
                boolean favoris;
                if(festivalJson.getInt("favoris") == 1) {
                    favoris = true;
                } else {
                    favoris = false;
                }
                String dateDeb= festivalJson.getString("dateDebut");
                String dateFin= festivalJson.getString("dateFin");
                idFestivals.add(idFestival);
                listeFestivals.add(new InfosFestival(titre, R.drawable.default_illustration,idFestival,favoris,dateDeb,dateFin));

            }
            chargementDonnes.setVisibility(View.INVISIBLE);
        } catch (JSONException e) { System.err.println(e);}
    }


    /**
     * Méthode pour initialiser la liste des festivals
     */
    private void initialiseListeFestivals() {
        listeFestivals = new ArrayList<>();
        //listeFestivals.add(new InfosFestival("Exemple festival numér", R.drawable.default_illustration, 0,false));
        //listeFestivals.add(new InfosFestival("Exemple festival numéro 1", R.drawable.default_illustration, 1,false));
    }

    /**
     * Méthode pour vider la liste des festivals
     */
    private void viderListeFestivals() {
        listeFestivals.clear();
    }

    private void chargerFestivalsProgrammes()  {
        chargementDonnes.setVisibility(View.VISIBLE);
        ApiManager.appelApiArray(URL_FESTIVAL_PROGRAMMES, this, new CallbackApi<JSONArray>() {

            @Override
            public void onReponsePositive(JSONArray reponseApi)  {
                listeFestivals.clear();
                try {
                    festivalsStockes.clear();
                    JSONArray festivals = reponseApi;
                    for (int i = 0; i < festivals.length(); i++) {
                        JSONObject festival = festivals.getJSONObject(i);
                        festivalsStockes.add(festival);
                    }
                    afficherPage();
                }catch (JSONException e) {

                }
            }

            @Override
            public void onReponseErreur(String erreur) {
            }
        },null, Request.Method.GET);

    }

    private void chargerFestivalsFavoris() {
        chargementDonnes.setVisibility(View.VISIBLE);
         ApiManager.appelApiArray(URL_FESTIVAL_FAVORIS, this, new CallbackApi<JSONArray>() {
            @Override
            public void onReponsePositive(JSONArray reponseApi) {
                listeFestivals.clear();
                try {
                    festivalsStockes.clear();
                    JSONArray festivals = reponseApi;
                    for (int i = 0; i < festivals.length(); i++) {
                        JSONObject festival = festivals.getJSONObject(i);
                        festivalsStockes.add(festival);
                    }
                    afficherPage();
                }catch (JSONException e) {

                }
            }
            @Override
            public void onReponseErreur(String erreur) {

            }
        },null,Request.Method.GET);

    }
}
