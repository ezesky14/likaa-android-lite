package com.ezesky.likaa.Bus;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ezesky.likaa.R;
import com.ezesky.likaa.BD_sqlite.Fonctions_Bus;
import com.ezesky.likaa.BD_sqlite.Fonctions_Gbaka;
import com.ezesky.likaa.BD_sqlite.Fonctions_Taxi;
import com.ezesky.likaa.MainActivity;
import com.ezesky.likaa.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public class RueActivity extends AppCompatActivity {

    final String id = "id";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    private AutoCompleteTextView complete = null;

    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);

    String module_choisi;
    ImageView im1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rue);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        Button btn = (Button) findViewById(R.id.rue_search);
        ImageView im2 = (ImageView) findViewById(R.id.img_bus_main);


        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Lobster_1.3.otf");
        btn.setTypeface(typeface);
        //recupération de la valeur du module choisi
        Intent intent = getIntent();
        module_choisi = intent.getStringExtra("type");


        complete = (AutoCompleteTextView) findViewById(R.id.rue_edit);
        complete.setThreshold(1);

        /*-------------------------------------------------------------------------------------------------------------------------------------*/
        //Affichage du layout en fonction du module choisi
        /* Ce sont :
           1-Le titre
           2- Le theme
           3- L'arriere plan du toolbar
           4- L'image de fond du navigationView
           5- La recuperation des données pour l'AutoCompleteTextView
           6- Le changement de background et de text pour le bouton de recherche
           7- L image de fond du layout


         */


        if (module_choisi.equals("bus")) {
            setTitle("Recherche à partir d'une rue");
            toolbar.setBackgroundResource(R.color.md_green_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_bus);
            btn.setBackgroundResource(R.color.md_green_500_50);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_bus.AllRue());
            complete.setAdapter(adapter);
            im2.setImageResource(R.drawable.logo_bus);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);

        } else if (module_choisi.equals("taxi")) {

            setTitle("Recherche à partir d'un lieu");
            toolbar.setBackgroundResource(R.color.md_orange_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_taxi);
            // btn.setText("rechercher");
            btn.setBackgroundResource(R.color.md_orange_500_50);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_taxi.AllLieu());
            complete.setAdapter(adapter);
            im2.setImageResource(R.drawable.logo_taxi);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un taxi", "Tout les taxi", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
        } else if (module_choisi.equals("gbaka")) {
            setTitle("Recherche à partir d'un lieu");
            toolbar.setBackgroundResource(R.color.md_blue_100);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_gbaka);
            // btn.setText("rechercher ");
            btn.setBackgroundResource(R.color.md_blue_500_25);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_gbaka.AllLieu());
            complete.setAdapter(adapter);
            im2.setImageResource(R.drawable.logo_gbaka);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un gbaka", "Tout les gbakas", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
        }


    }


    private void Changer_Titre_MenuItem(NavigationView nv, String t1, String t2, String t3, String t4) {


        MenuItem v1 = nv.getMenu().findItem(R.id.item_rec_bus);
        v1.setTitle(t1);


        MenuItem v2 = nv.getMenu().findItem(R.id.item_tout_les_bus);
        v2.setTitle(t2);


        MenuItem v3 = nv.getMenu().findItem(R.id.item_rec_lieu_bus);
        v3.setTitle(t3);
        v3.setChecked(true);

        MenuItem v4 = nv.getMenu().findItem(R.id.item_rec_2_lieux_bus);
        v4.setTitle(t4);


    }


    public void onClick_search_lieu(View v) {

        if (complete.getText().toString().isEmpty()) {
            Toast.makeText(RueActivity.this, "Ligne vide", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(RueActivity.this, Other_searchActivity.class);
            intent.putExtra(id, complete.getText().toString());
            intent.putExtra("type", module_choisi);
            startActivity(intent);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                Intent i = new Intent(RueActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(RueActivity.this, MainActivity.class);
                startActivity(i1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        textView = (TextView) findViewById(R.id.action_bar_title);
                        switch (menuItem.getItemId()) {
                            case R.id.item_rec_bus:
                                Intent i1 = new Intent(RueActivity.this, BusMainActivity.class);
                                i1.putExtra("type", module_choisi);
                                finish();
                                startActivity(i1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_tout_les_bus:
                                Intent i2 = new Intent(RueActivity.this, AllbusActivity.class);
                                i2.putExtra("type", module_choisi);
                                finish();
                                startActivity(i2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_rec_lieu_bus:
                                Toast.makeText(RueActivity.this, "Vous y etes dejà .", Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_rec_2_lieux_bus:
                                Intent i3 = new Intent(RueActivity.this, BusActivity.class);
                                i3.putExtra("type", module_choisi);
                                finish();
                                startActivity(i3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_iti:
                                Intent i4 = new Intent(RueActivity.this, ItineraireActivity.class);
                                i4.putExtra("type", module_choisi);
                                finish();
                                startActivity(i4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                        }
                        return true;
                    }
                });
    }


}
