package sae401.festiplandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class Festivals extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    private final String URL_FESTIVAL_FAVORIS = "";

    private final String URL_FESTIVAL_PROGRAMMES = "";
    private ArrayAdapter<String> adaptateurFestivals;

    private ListView listeFestivals;

    public enum TYPE_FESTIVALS {;
        public static final String PROGRAMMES = "Programmes";

        public static final String FAVORIS = "Favoris";

        public static final String DECONNEXION = "Deconnexion";
    }

    private ActivityResultLauncher<Intent> lanceurFestivalsDetails;

    private  int[] idFestivals;

    private int page;

    private String typeFestivals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festivals);

        ActionBar barre = getSupportActionBar();

        barre.setDisplayShowTitleEnabled(false);
        barre.setDisplayShowCustomEnabled(true);
        barre.setCustomView(R.layout.action_bar);
        barre.setBackgroundDrawable(getResources().getDrawable(R.drawable.fond_barre_action));
        
        listeFestivals = findViewById(R.id.listeFestivals);
        adaptateurFestivals = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        listeFestivals.setAdapter(adaptateurFestivals);
        listeFestivals.setOnItemClickListener(this);
        // STUB
        page = 1;
        idFestivals = new int[5];
        idFestivals[0] = 1;
        idFestivals[1] = 2;
        typeFestivals = TYPE_FESTIVALS.PROGRAMMES;
        // FIN STUB
        chargerFestivalsProgrammes();
        lanceurFestivalsDetails =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(), this::retourDetails);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent pageDetails = new Intent(this,DetailsFestival.class);

        int idFestival = idFestivals[position];

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
    private void chargerFestivalsProgrammes() {
        viderAdapdateur();
        ApiManager.appelApiGet(URL_FESTIVAL_PROGRAMMES, this, new ListenerApi() {
            @Override
            public void onReponsePositive(JSONArray reponseApi) {

            }

            @Override
            public void onReponseErreur(String erreur) {

            }

        });
        adaptateurFestivals.add("f1");
        adaptateurFestivals.add("f2");
    }

    private void chargerFestivalsFavoris() {
        viderAdapdateur();
        //ApiManager.appelApiGet();
        adaptateurFestivals.add("f3");
        adaptateurFestivals.add("f4");
    }

    /**
     * Deconnecte l'utilisateur et le redirige vers la page de connexion.
     */
    private void deconnecter() {
        Intent pageConnexion = new Intent(this, Connexion.class);
        startActivity(pageConnexion);
    }

    private void viderAdapdateur() {
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
    public void pageSuivante(View v) {

    }

    /**
     * Affiche les festivals de la page précédente
     * @param v le bouton appuyé
     */
    public void pagePrecedente(View v) {

    }
}
