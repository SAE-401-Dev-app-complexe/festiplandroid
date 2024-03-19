/*
 * Connexion.java                                        18 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Gestion de la connexion de l'utilisateur.
 * <p>
 *     Cette classe permet à l'utilisateur de se connecter à l'application.
 *     Elle vérifie que les identifiants sont corrects et redirige l'utilisateur
 *     vers la page principale de l'application.
 *     <br>
 *     Si les identifiants sont incorrects, un message d'erreur est affiché.
 * </p>
 *
 * @author Enzo  Cluzel
 * @author Lucas Descriaud
 * @author Loïc  Faugières
 * @author Simon Guiraud
 */
public class Connexion extends AppCompatActivity {

    private TextView connexionErreur;

    private EditText pseudo;

    private EditText motDePasse;

    /**
     * Méthode appelée à la création de l'activité.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     *     <b><i>Note: Otherwise it is null.</i></b>
     */
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

    /**
     * Méthode appelée lors du clic sur le bouton de connexion.
     *
     * @param vue La vue actuelle.
     * @throws UnsupportedEncodingException Si l'encodage des données échoue.
     * @throws JSONException Si la création du JSON échoue.
     */
    public void clicConnexion(View vue) throws UnsupportedEncodingException, JSONException {
        String login = pseudo.getText().toString();
        String mdp = motDePasse.getText().toString();

        login = URLEncoder.encode(login, "UTF-8");
        mdp = URLEncoder.encode(mdp, "UTF-8");

        JSONObject donnees = new JSONObject();
        donnees.put("login", login);
        donnees.put("password", mdp);

        String url = getString(R.string.lien_api) + "authentification";

        if (login.isEmpty() || mdp.isEmpty()) {
            connexionErreur.setVisibility(View.VISIBLE);
            connexionErreur.setText(R.string.connexion_non_renseigne);
        } else {
            ApiManager.appelApi(url, this, new ListenerApi() {
                @Override
                public void onReponsePositive(JSONObject reponseApi) {
                    String cleApi = null;
                    try {
                        cleApi = reponseApi.getString("cleApi");
                    } catch (JSONException e) {
                        gestionConnexionEchouee(null);
                    } finally {
                        if (cleApi.equals("null")) {
                            gestionConnexionEchouee(null);
                        } else {
                            gestionConnexionReussie(cleApi);
                        }
                    }
                }

                @Override
                public void onReponseErreur(String erreur) {
                    gestionConnexionEchouee(erreur);
                }
            }, donnees, Request.Method.POST);
        }
    }

    /**
     * Si la connexion est réussie, redirige l'utilisateur vers la page principale.
     * @param cleApi La clé API de l'utilisateur.
     */
    private void gestionConnexionReussie(String cleApi) {
        ApiManager.setCleApi(cleApi);

        Intent pageFestivals = new Intent(this, Festivals.class);
        
        startActivity(pageFestivals);
    }

    /**
     * Si la connexion échoue, affiche un message d'erreur.
     * @param erreur Le message d'erreur à afficher.
     */
    private void gestionConnexionEchouee(String erreur) {
        connexionErreur.setVisibility(View.VISIBLE);
        if (erreur == null) {
            connexionErreur.setText(R.string.connexion_erreur_connexion);
            motDePasse.setText("");
        } else {
            connexionErreur.setText(erreur);
        }
    }
}
