package sae401.festiplandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Festivals extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    private final String FESTIVAL_AJOUTE = "Festival ajouté aux favoris";

    private final String FESTIVAL_RETIRE = "Festival retiré des favoris";

    private final String URL_FESTIVAL_FAVORIS = "";

    private final String URL_FESTIVAL_PROGRAMMES = "";

    private final int NOMBRE_FESTIVAL_PAGE = 3;
    private ArrayAdapter<String> adaptateurFestivals;

    private ListView listeFestivals;

    public enum TYPE_FESTIVALS {;
        public static final String PROGRAMMES = "Programmes";

        public static final String FAVORIS = "Favoris";

        public static final String DECONNEXION = "Deconnexion";
    }

    private ActivityResultLauncher<Intent> lanceurFestivalsDetails;

    private ArrayList<Integer> idFestivals;

    // A modifier si non nécessaire
    private ArrayList<JSONObject> festivalsStockes;

    private int page;

    private String typeFestivals;

    /**
     * Liste source des données à afficher :
     * chaque élément contient une instance de PhotoParis (une photo
     * et son libellé)
     */
    private ArrayList<PhotoParis> listePhoto;

    private ImageButton boutonFavori;

    /**
     * Element permettant d'afficher la liste des photos
     */
    private RecyclerView photoRecyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festivals);

        ActionBar barre = getSupportActionBar();

        barre.setDisplayShowTitleEnabled(false);
        barre.setDisplayShowCustomEnabled(true);
        barre.setCustomView(R.layout.action_bar);
        barre.setBackgroundDrawable(getResources().getDrawable(R.drawable.fond_barre_action));
        
        /*listeFestivals = findViewById(R.id.listeFestivals);
        adaptateurFestivals = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        listeFestivals.setAdapter(adaptateurFestivals);
        listeFestivals.setOnItemClickListener(this);
        // STUB
        page = 1;
        typeFestivals = TYPE_FESTIVALS.PROGRAMMES;
        // FIN STUB
        festivalsStockes = new ArrayList<>();
        idFestivals = new ArrayList<>();
        chargerFestivalsProgrammes();
        lanceurFestivalsDetails =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(), this::retourDetails);*/

        photoRecyclerView = findViewById(R.id.my_recycler_view);
        initialiseListePhoto();

        LinearLayoutManager gestionnaireLineaire = new LinearLayoutManager(this);
        photoRecyclerView.setLayoutManager(gestionnaireLineaire);

        /*
         * On crée un adaptateur personnalisé et permettant de gérer spécifiquement
         * l'affichage des instances de type PhotoParis en tant que item de la liste.
         * Cet adapatateur est associé au RecyclerView
         */
        PhotoAdapter adaptateur = new PhotoAdapter(listePhoto);
        photoRecyclerView.setAdapter(adaptateur);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent pageDetails = new Intent(this,DetailsFestival.class);

        int idFestival = idFestivals.get(position);

        pageDetails.putExtra("idFestival",idFestival);

        lanceurFestivalsDetails.launch(pageDetails);
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
            deconnecter();
            typeFestivals = TYPE_FESTIVALS.DECONNEXION;
        }

        return super.onOptionsItemSelected(item);
    }

    private void chargerFestivals() {
        if (typeFestivals.equals(TYPE_FESTIVALS.PROGRAMMES)) {
            chargerFestivalsProgrammes();

        } else if(typeFestivals.equals(TYPE_FESTIVALS.FAVORIS)) {
            chargerFestivalsFavoris();
        }

    }
    private void chargerFestivalsProgrammes()  {
        /*ApiManager.appelApi(URL_FESTIVAL_PROGRAMMES, this, new ListenerApi() {
            @Override
            public void onReponsePositive(String reponseApi) {
                
            }
            @Override
            public void onReponsePositive(JSONObject reponseApi)  {
                try {
                    festivalsStockes.clear();
                    JSONArray festivals = reponseApi.getJSONArray("festivals");
                    for (int i = 0; i < festivals.length(); i++) {
                        JSONObject festival = festivals.getJSONObject(i);
                        festivalsStockes.add(festival);
                    }
                }catch (JSONException e) {

                }
            }


            @Override
            public void onReponseErreur(String erreur) {

            }
        },null,Request.Method.GET);*/
        afficherPage();
        adaptateurFestivals.add("f1");
        adaptateurFestivals.add("f2");
    }

    private void chargerFestivalsFavoris() {
         /*ApiManager.appelApi(URL_FESTIVAL_FAVORIS, this, new ListenerApi() {
            @Override
            public void onReponsePositive(String reponseApi) {
                
            }
            @Override
            public void onReponsePositive(JSONObject reponseApi) {
                try {
                    festivalsStockes.clear();
                    JSONArray festivals = reponseApi.getJSONArray("festivals");
                    for (int i = 0; i < festivals.length(); i++) {
                        JSONObject festival = festivals.getJSONObject(i);
                        festivalsStockes.add(festival);
                    }
                }catch (JSONException e) {

                }
            }


            @Override
            public void onReponseErreur(String erreur) {

            }

        },null,Request.Method.GET);*/
         JSONObject j1 = new JSONObject();
        JSONObject j2 = new JSONObject();
        JSONObject j3 = new JSONObject();
        JSONObject j4 = new JSONObject();
        try {
            j1.put("idFestival",1);
            j1.put("titre","j1");
            j2.put("idFestival",2);
            j2.put("titre","j2");
            j3.put("idFestival",3);
            j3.put("titre","j3");
            j4.put("idFestival",4);
            j4.put("titre","j4");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

         festivalsStockes.add(j1);
        festivalsStockes.add(j2);
        festivalsStockes.add(j3);
        festivalsStockes.add(j4);
        afficherPage();
    }

    /**
     * Déconnecte l'utilisateur et le redirige vers la page de connexion.
     */
    private void deconnecter() {
        Intent pageConnexion = new Intent(this, Connexion.class);
        startActivity(pageConnexion);
    }

    private void viderAdaptateur() {
        adaptateurFestivals.clear();
    }

    // Non nécessaires les informations sont gardés aprés changement de page
    private void retourDetails(ActivityResult resultat) {
      /*  if(resultat.getResultCode() == Activity.RESULT_OK) {
            //chargerFestivals();
        }
    */
    }

    /**
     * Affiche les festivals de la page suivante
     * @param v le bouton appuyé
     */
    public void pageSuivante(View v){

        System.out.println(page);
        System.out.println(festivalsStockes.size());
        System.out.println((float)festivalsStockes.size()/NOMBRE_FESTIVAL_PAGE);
        if(page < (int)Math.ceil((float)festivalsStockes.size()/NOMBRE_FESTIVAL_PAGE)) {
            System.out.println(page);
            page +=1;
            afficherPage();
        }

    }

    /**
     * Affiche les festivals de la page précédente
     * @param v le bouton appuyé
     */
    public void pagePrecedente(View v)  {
        System.out.println(page);
        if(page > 1) {
            System.out.println(page);page -=1;
            afficherPage();
        }
    }
    public void afficherPage()  {
        viderAdaptateur();
        try {
            int pageDebut = (page-1) * NOMBRE_FESTIVAL_PAGE ;
            int pageFin = pageDebut + NOMBRE_FESTIVAL_PAGE;
            idFestivals.clear();

            for (int num = pageDebut; num < festivalsStockes.size() && num < pageFin; num++) {
                System.out.println(festivalsStockes.get(num));
                idFestivals.add(Integer.parseInt(festivalsStockes.get(num).getString("idFestival")));
                adaptateurFestivals.add("stub");
                //adaptateurFestivals.add(festivalsStockes.get(num).getString("titre"));
            }
        } catch (JSONException e) {

        }
    }

    /**
     * Méthode pour initialiser la liste des photos et des textes
     */
    private void initialiseListePhoto() {
        listePhoto = new ArrayList<>();
        listePhoto.add(new PhotoParis("Exemple festival numéro 1", R.drawable.default_illustration));
        listePhoto.add(new PhotoParis("aaaaaaaaaazzzzssxcvbn,;  bvcxszedfg", R.drawable.default_illustration));
    }

    /**
     * Méthode appelée lors du clic sur le bouton de favori.
     * @param vue La vue actuelle.
     */
    public void clicFavori(View vue) {
        Drawable.ConstantState etoileActive;

        etoileActive
        = getResources().getDrawable(R.drawable.etoile_active).getConstantState();

        boutonFavori = (ImageButton) vue;

        // Si l'étoile est active, on la désactive
        if (boutonFavori.getDrawable().getConstantState().equals(etoileActive)) {
            boutonFavori.setImageResource(R.drawable.etoile_inactive);
            Toast.makeText(this, FESTIVAL_RETIRE, Toast.LENGTH_SHORT).show();
        } else {
            boutonFavori.setImageResource(R.drawable.etoile_active);
            Toast.makeText(this, FESTIVAL_AJOUTE, Toast.LENGTH_SHORT).show();
        }
    }
}
