package sae401.festiplandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.PixelCopy;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONObject;


public class DetailsFestival extends AppCompatActivity  {

    private int idFestival;

    private TextView titre;

    private final String URL_FESTIVAL_DETAIL = "http://10.0.2.2/API/testAPISAE/API/festivalDetail";

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

        //idFestival = pagePrecedente.getIntExtra("idFestival",0);
        //JSONObject donnees = new JSONObject().put("idFestival",idFestival);
        /*piManager.appelApiObjet(URL_FESTIVAL_DETAIL, this, new ListenerApi<JSONObject>() {
            @Override
            public void onReponsePositive(JSONObject reponseApi) {
                // TODO afficher données
            }

            @Override
            public void onReponseErreur(String erreur) {

            }
        },donnees, Request.Method.GET);*/

        titre = findViewById(R.id.titre_festival);
        titre.setText("Festival d'id " + idFestival);
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
            chargerFestivalsProgrammes();
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
        startActivity(pageFestivalsProgrammes);
    }

    /**
     * Déconnecte l'utilisateur et le redirige vers la page de connexion.
     */
    private void deconnecter() {
        Intent pageConnexion = new Intent(this, Connexion.class);
        startActivity(pageConnexion);
        // TODO faire en sorte qu'on ne puisse plus faire retour (via bouton tel) après la déconnexion
    }

    public void retour(View v) {
        Intent retour = new Intent(this, Festivals.class);
        setResult(Activity.RESULT_OK, retour);
        finish();
    }

}
