package sae401.festiplandroid;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class DetailsFestival extends AppCompatActivity  {

    private int idFestival;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_festival);
        Intent pagePrecedente = getIntent();
        idFestival = pagePrecedente.getIntExtra("idFestival",0);
    }

    public void retour(View v) {
        Intent retour = new Intent(this, Festivals.class);
        setResult(Activity.RESULT_OK,retour);
        finish();
    }

}
