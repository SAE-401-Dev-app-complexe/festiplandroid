package sae401.festiplandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Connexion extends AppCompatActivity {

    private TextView connexionErreur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        connexionErreur = findViewById(R.id.connexion_erreur);

    }
    public  void connexion(View vue) {
        // TODO
        if(false) {
            connexionErreur.setVisibility(View.VISIBLE);
        } else {
            Intent intent ;
        }
    }
}