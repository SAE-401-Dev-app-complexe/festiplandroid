package sae401.festiplandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static sae401.festiplandroid.Festivals.CLE_TYPE_FESTIVAL;
import static sae401.festiplandroid.Festivals.TYPE_FESTIVALS;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsFestival extends AppCompatActivity  {

    private String urlDetailsFestival;

    private InfosFestival festival;

    private int idFestival;

    private TextView titre;

    private TextView dates;

    private TextView description;

    private TextView[] spectacles;

    /**
     * Appelé lors de la création de l'activité.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_festival);

        urlDetailsFestival = getString(R.string.lien_api) + "details";

        ActionBar barre = getSupportActionBar();

        barre.setDisplayShowTitleEnabled(false);
        barre.setDisplayShowCustomEnabled(true);
        barre.setCustomView(R.layout.action_bar);
        barre.setBackgroundDrawable(ResourcesCompat
            .getDrawable(getResources(), R.drawable.fond_barre_action, null));

        titre = findViewById(R.id.titre_festival);
        dates = findViewById(R.id.dates_festival);
        description = findViewById(R.id.description);

        Intent pagePrecedente = getIntent();

        try {
            festival = (InfosFestival) pagePrecedente.getSerializableExtra(InfosFestival.CLE_FESTIVAL);

            idFestival = festival.getIdFestival();
            titre.setText(festival.getTitre());
            dates.setText("Du " + festival.getDateDeb() + " au " + festival.getDateFin());
            description.setText(festival.getDescription());
        } catch (Exception e) {
            afficherMessageErreur(null);
        }
    }

    /**
     * Appelé lors de la création du menu de l'activité.
     * @param menu The options menu in which you place your items.
     * @return true pour afficher le menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Appelé lorsqu'un élément du menu est sélectionné.
     * @param item The menu item that was selected.
     * @return false pour permettre la poursuite
     *         du traitement de l'élément sélectionné.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int optionChoisie = item.getItemId();

        if (optionChoisie == R.id.festivals_programmes) {
            chargerFestivals(TYPE_FESTIVALS.PROGRAMMES);
        } else if (optionChoisie == R.id.festivals_favoris) {
            chargerFestivals(TYPE_FESTIVALS.FAVORIS);
        } else if (optionChoisie == R.id.deconnexion) {
            deconnecter();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Charge la page des festivals en fonction du type de festivals.
     * @param typeFestivals Le type de festivals à charger.
     */
    private void chargerFestivals(String typeFestivals) {
        Intent pageFestivals = new Intent(this, Festivals.class);

        pageFestivals.putExtra(CLE_TYPE_FESTIVAL, typeFestivals);

        startActivity(pageFestivals);
    }

    /**
     * Déconnecte l'utilisateur et le redirige vers la page de connexion.
     */
    private void deconnecter() {
        ApiManager.setCleApi(null);
        Intent pageConnexion = new Intent(this, Connexion.class);
        startActivity(pageConnexion);
    }

    /**
     * Charge les détails du festival et les affiche.
     */
    private void chargerDetailsFestival() {
        JSONArray donnees;

        donnees = new JSONArray();
        try {
            donnees.put(new JSONObject().put("idFestival", idFestival));
        } catch (JSONException e) {
            afficherMessageErreur(null);
        }

        ApiManager.appelApiArray(urlDetailsFestival, this,
                                 new CallbackApi<JSONArray>() {
            @Override
            public void onReponsePositive(JSONArray reponseApi) {
                // TODO afficher données

                try {
                    JSONArray spectacles = reponseApi.getJSONArray(0);
                } catch (JSONException e) {
                    afficherMessageErreur(null);
                }
            }

            @Override
            public void onReponseErreur(String erreur) {
                afficherMessageErreur(erreur);
            }
        }, donnees, Request.Method.GET);
    }

    /**
     * Affiche un message d'erreur.
     * @param message Le message à afficher.
     */
    private void afficherMessageErreur(String message) {
        Toast.makeText(this, message != null
                           ? message
                           : getString(R.string.erreur_survenue),
                       Toast.LENGTH_SHORT).show();
        deconnecter();
    }

}
