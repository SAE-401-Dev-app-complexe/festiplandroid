package sae401.festiplandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_navigation, menu);
        menu.getItem(1).setIcon(R.drawable.ic_accueil);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void clicConnexion(View vue) throws UnsupportedEncodingException {
        String login = pseudo.getText().toString();
        String mdp = motDePasse.getText().toString();

        login = URLEncoder.encode(login , "UTF-8");
        mdp = URLEncoder.encode(mdp , "UTF-8");

        String url = String.format(getString(R.string.lien_api),
                "/authentification/" + login + "/" + mdp);

        // TODO passer données dans le contenu de la requête POST

        if (true) {
            connexionErreur.setVisibility(View.VISIBLE);
        }
    }
}