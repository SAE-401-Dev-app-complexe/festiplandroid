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

public class Festivals extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

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
        typeFestivals = "Programmes";
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
        pageDetails.putExtra("typeFestival",typeFestivals);
        pageDetails.putExtra("page",page);

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
        if (typeFestivals.equals("Programmes")) {
            chargerFestivalsProgrammes();

        } else if(typeFestivals.equals("Favoris")) {
            chargerFestivalsFavoris();
        }

    }
    private void chargerFestivalsProgrammes() {
        viderAdapdateur();
        adaptateurFestivals.add("f1");
        adaptateurFestivals.add("f2");
    }


    private void chargerFestivalsFavoris() {
        viderAdapdateur();
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

    private void retourDetails(ActivityResult resultat) {
        if(resultat.getResultCode() == Activity.RESULT_OK) {
            //TODO Provoque une erreur
            typeFestivals =  resultat.getData().getStringExtra("typeFestivals");
            // page =  resultat.getData().getIntExtra("page",0);
            chargerFestivals();
        }

    }

    public void pageSuivante(View v) {

    }

    public void pagePrecedente(View v) {

    }
}
