package sae401.festiplandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static sae401.festiplandroid.Festivals.CLE_TYPE_FESTIVAL;
import static sae401.festiplandroid.Festivals.TYPE_FESTIVALS;

public class DetailsFestival extends AppCompatActivity  {

    private int idFestival;

    private TextView titre;

    private TextView dates;

    private TextView description;

    private final String URL_FESTIVAL_DETAIL = "http://10.0.2.2/API/testAPISAE/API/details";

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_festival);

        ActionBar barre = getSupportActionBar();

        barre.setDisplayShowTitleEnabled(false);
        barre.setDisplayShowCustomEnabled(true);
        barre.setCustomView(R.layout.action_bar);
        barre.setBackgroundDrawable(getResources().getDrawable(R.drawable.fond_barre_action));

        Intent pagePrecedente = getIntent();


        titre = findViewById(R.id.titre_festival);
        dates = findViewById(R.id.dates_festival);

        description = findViewById(R.id.description);

        idFestival = pagePrecedente.getIntExtra("idFestival",0);
        titre.setText(pagePrecedente.getStringExtra("titre"));
        dates.setText(pagePrecedente.getStringExtra("dates"));
        description.setText(pagePrecedente.getStringExtra("description"));
       /* JSONArray donnees = null;
        try {
            donnees = new JSONArray();
            donnees.put(new JSONObject().put("idFestival",idFestival));
            System.out.println(donnees);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ApiManager.appelApiArray(URL_FESTIVAL_DETAIL, this, new CallbackApi<JSONArray>() {
            @Override
            public void onReponsePositive(JSONArray reponseApi) {
                // TODO afficher données

                try {
                    JSONObject festival = reponseApi.getJSONObject(0);
                    String titreFestival =  festival.getString("titre");
                    String datesFestival = "Du "+ festival.getString("dateDebut")+ "\nau " +festival.getString("dateFin");
                    String descriptionFestival = festival.getString("description");
                    titre.setText(titreFestival);
                    dates.setText(datesFestival);
                    description.setText(descriptionFestival);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onReponseErreur(String erreur) {

            }
        },donnees, Request.Method.POST);*/


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
        } else if (optionChoisie == R.id.festivals_favoris) {
            chargerFestivalsFavoris();
        } else if (optionChoisie == R.id.deconnexion) {
            ApiManager.setCleApi(null);
            deconnecter();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Charge la page des festivals programmés.
     */
    private void chargerFestivalsProgrammes() {
        Intent pageFestivalsProgrammes = new Intent(this, Festivals.class);

        pageFestivalsProgrammes.putExtra(CLE_TYPE_FESTIVAL, TYPE_FESTIVALS.PROGRAMMES);

        startActivity(pageFestivalsProgrammes);
    }

    /**
     * Charge la page des festivals favoris.
     */
    private void chargerFestivalsFavoris() {
        Intent pageFestivalsFavoris = new Intent(this, Festivals.class);

        pageFestivalsFavoris.putExtra(CLE_TYPE_FESTIVAL, TYPE_FESTIVALS.FAVORIS);

        startActivity(pageFestivalsFavoris);
    }

    /**
     * Déconnecte l'utilisateur et le redirige vers la page de connexion.
     */
    private void deconnecter() {
        Intent pageConnexion = new Intent(this, Connexion.class);
        startActivity(pageConnexion);
        // TODO faire en sorte qu'on ne puisse plus faire retour (via bouton tel) après la déconnexion
    }

    /**
     * Retourne à la page précédente.
     * @param vue La vue actuelle
     */
    public void retour(View vue) {
        Intent retour = new Intent(this, Festivals.class);
        setResult(Activity.RESULT_OK, retour);
        finish();
    }

}
