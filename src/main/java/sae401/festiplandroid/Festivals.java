package sae401.festiplandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
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

    private enum TYPE_FESTIVALS{
        PROGRAMMES("Programmes"),
        FAVORIS("Favoris");
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
        int optionChoisi = item.getItemId();
        if(R.id.festivals_programmes == optionChoisi) {
            chargerFestivalsProgrammes();
            typeFestivals = "Programmes";
        } else if(R.id.festivals_favoris == optionChoisi) {
            chargerFestivalsFavoris();
            typeFestivals = "Favoris";
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
