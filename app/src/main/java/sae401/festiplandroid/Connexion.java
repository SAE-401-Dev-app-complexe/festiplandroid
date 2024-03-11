package sae401.festiplandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Connexion extends AppCompatActivity {

    private TextView connexionErreur;

    private EditText utilisateur;

    private EditText motDePasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        connexionErreur = findViewById(R.id.connexion_erreur);
        utilisateur = findViewById(R.id.user);
        motDePasse = findViewById(R.id.mdp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_navigation, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void clicConnexion(View vue) throws UnsupportedEncodingException {
        String motDePasse = motDePasse.getText().toString();
        String login = utilisateur.getText().toString();
        
        motDePasse = URLEncoder.encode(motDePasse , "UTF-8");
        login = URLEncoder.encode(login , "UTF-8");

        String url = String.format(getString(R.string.lien_api),
                "/authentification/" + login + "/" + motDePasse);

        if (true) {
            connexionErreur.setVisibility(View.VISIBLE);
        }
    }
}