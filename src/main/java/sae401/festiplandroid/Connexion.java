package sae401.festiplandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Connexion extends AppCompatActivity {

    private TextView connexionErreur;

    private EditText pseudo;

    private EditText motDePasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        connexionErreur = findViewById(R.id.connexion_erreur);
        pseudo = findViewById(R.id.connexion_pseudo);
        motDePasse = findViewById(R.id.connexion_mdp);

        ActionBar barre = getSupportActionBar();

        barre.setDisplayShowTitleEnabled(false);
        barre.setDisplayShowCustomEnabled(true);
        barre.setCustomView(R.layout.action_bar);
        barre.setBackgroundDrawable(getResources().getDrawable(R.drawable.fond_barre_action));
    }

    public void clicConnexion(View vue) throws UnsupportedEncodingException {
        String login = pseudo.getText().toString();
        String mdp = motDePasse.getText().toString();

        login = URLEncoder.encode(login , "UTF-8");
        mdp = URLEncoder.encode(mdp , "UTF-8");

        String url = String.format(getString(R.string.lien_api),
                "/authentification/" + login + "/" + mdp);

        // TODO passer données dans le contenu de la requête POST

        if (login == null || mdp == null || login.isEmpty() || mdp.isEmpty()) {
            connexionErreur.setVisibility(View.VISIBLE);
            connexionErreur.setText(R.string.connexion_erreur_connexion);
        } else {

            //TODO changer url à celle correcte
            ApiManager.appelApiGet("https://geo.api.gouv.fr/regions",this,new ListenerApi() {

                @Override
                public void onReponsePositive(JSONArray reponseApi) {
                    // TODO modifier pour ajouter la verification de la reponse de l'api
                    connexionReussi();
                }

                @Override
                public void onReponseErreur(String erreur) {
                    connexionErreur.setVisibility(View.VISIBLE);

                    connexionErreur.setText(erreur);
                }
            });
        }
    }

    private void connexionReussi() {
        Intent pageFestivals = new Intent(this, Festivals.class);

        startActivity(pageFestivals); // TODO choisir avec communication ou sans
    }
}
