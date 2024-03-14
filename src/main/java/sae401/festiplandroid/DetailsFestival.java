package sae401.festiplandroid;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class DetailsFestival extends AppCompatActivity  {

    private int idFestival;
    private int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_festival);
        Intent pagePrecedente = getIntent();
        idFestival = pagePrecedente.getIntExtra("idFestival",0);
        page = pagePrecedente.getIntExtra("page",0);
        System.out.println(page);

    }

    public void retour(View v) {
        Intent retour = new Intent(this, Festivals.class);
        retour.putExtra("page", page);

        setResult(Activity.RESULT_OK);
        finish();
    }

}
