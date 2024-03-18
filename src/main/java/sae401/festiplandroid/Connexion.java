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

import org.json.JSONArray;

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
     */
    public void clicConnexion(View vue) throws UnsupportedEncodingException {
        String login = pseudo.getText().toString();
        String mdp = motDePasse.getText().toString();

        login = URLEncoder.encode(login , "UTF-8");
        mdp = URLEncoder.encode(mdp , "UTF-8");

        String url = String.format(getString(R.string.lien_api),
                "/authentification/" + login + "/" + mdp);

        // TODO passer données dans le contenu de la requête POST

        if (login.isEmpty() || mdp.isEmpty()) {
            connexionErreur.setVisibility(View.VISIBLE);
            connexionErreur.setText(R.string.connexion_non_renseigne);
        } else {

            //TODO changer url à celle correcte
            ApiManager.appelApiGet("https://geo.api.gouv.fr/regions",
                                   this, new ListenerApi() {

                @Override
                public void onReponsePositive(JSONArray reponseApi) {
                    // TODO modifier pour ajouter la verification de la reponse de l'api
                    gestionConnexionReussie();
                }

                @Override
                public void onReponseErreur(String erreur) {
                    connexionErreur.setVisibility(View.VISIBLE);
                    connexionErreur.setText(erreur);
                }
            });
        }
    }

    /**
     * Si la connexion est réussie, redirige l'utilisateur vers la page principale.
     */
    private void gestionConnexionReussie() {
        Intent pageFestivals = new Intent(this, Festivals.class);

        startActivity(pageFestivals); // TODO choisir avec communication ou sans
    }
}
