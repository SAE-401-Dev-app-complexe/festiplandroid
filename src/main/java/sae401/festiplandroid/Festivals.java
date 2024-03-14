package sae401.festiplandroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Festivals extends AppCompatActivity {

    private ActivityResultLauncher<Intent> lanceurActiviteFille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festivals);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_navigation, menu);
        menu.getItem(1).setIcon(R.drawable.ic_accueil);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int optionChoisi = item.getItemId();
        if(R.id.festivals_programmes == optionChoisi) {

        }

        return super.onOptionsItemSelected(item);
    }
}
