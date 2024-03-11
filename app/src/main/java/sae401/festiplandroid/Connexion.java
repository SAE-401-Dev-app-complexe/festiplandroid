package sae401.festiplandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    public  void connexion(View vue) throws UnsupportedEncodingException {
        // TODO
        String motPasse = motDePasse.getText().toString();
        String login = utilisateur.getText().toString();
        motPasse = URLEncoder.encode(motPasse , "UTF-8");
        login = URLEncoder.encode(login , "UTF-8");

        String url = String.format(getString(R.string.lien_api),
                "/authentification/"+login+"/"+motPasse);

        StringRequest
    }
}