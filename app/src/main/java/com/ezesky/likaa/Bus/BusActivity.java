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

public class BusActivity extends AppCompatActivity {
    AutoCompleteTextView rue1_bus = null;
    AutoCompleteTextView rue2_bus = null;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);
    String module_choisi;
    ImageView im1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        Intent intent = getIntent();
        module_choisi = intent.getStringExtra("type");


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

        rue1_bus = (AutoCompleteTextView) findViewById(R.id.rue1_bus);
        rue2_bus = (AutoCompleteTextView) findViewById(R.id.rue2_bus);
        rue1_bus.setThreshold(1);
        rue2_bus.setThreshold(1);
        ImageView im2 = (ImageView) findViewById(R.id.img1);
        Button btn = (Button) findViewById(R.id.Search_button2);


        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Lobster_1.3.otf");
        btn.setTypeface(typeface);


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
            setTitle("Recherches avancées");
            toolbar.setBackgroundResource(R.color.md_green_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_bus);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_bus.AllRue());
            rue1_bus.setAdapter(adapter);
            rue2_bus.setAdapter(adapter);
            im2.setImageResource(R.drawable.logo_bus);
            btn.setBackgroundResource(R.color.md_green_500_50);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);


        } else if (module_choisi.equals("taxi")) {
            setTitle("Recherches avancées");
            toolbar.setBackgroundResource(R.color.md_orange_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_taxi);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_taxi.AllLieu());
            rue1_bus.setAdapter(adapter);
            rue2_bus.setAdapter(adapter);
            im2.setImageResource(R.drawable.logo_taxi);
            btn.setBackgroundResource(R.color.md_orange_500_50);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un taxi", "Tout les taxi", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
        } else if (module_choisi.equals("gbaka")) {
            setTitle("Recherches avancées");
            toolbar.setBackgroundResource(R.color.md_blue_100);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_gbaka);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_gbaka.AllLieu());
            rue1_bus.setAdapter(adapter);
            rue2_bus.setAdapter(adapter);
            im2.setImageResource(R.drawable.logo_gbaka);
            btn.setBackgroundResource(R.color.md_blue_500_25);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un gbaka", "Tout les gbakas", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
        }
        //registerListClickCallback();
    }


    public void Bus_Click(View view) {//EVENEMENT LORS DU CLIC POUR AFFICHER LES RESULTATS D UNE LIGNE DE BUS
        if (rue1_bus.getText().toString().isEmpty() || rue2_bus.getText().toString().isEmpty()) {
            Toast.makeText(BusActivity.this, "Ligne(s) vide(s) . Entrez les rues ou lieux SVP ", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(BusActivity.this, ResultBusActivity.class);
            intent.putExtra("depart", rue1_bus.getText().toString());
            intent.putExtra("arrivee", rue2_bus.getText().toString());
            intent.putExtra("type", module_choisi);

            startActivity(intent);
        }
    }

    private void Changer_Titre_MenuItem(NavigationView nv, String t1, String t2, String t3, String t4) {


        MenuItem v1 = nv.getMenu().findItem(R.id.item_rec_bus);
        v1.setTitle(t1);


        MenuItem v2 = nv.getMenu().findItem(R.id.item_tout_les_bus);
        v2.setTitle(t2);


        MenuItem v3 = nv.getMenu().findItem(R.id.item_rec_lieu_bus);
        v3.setTitle(t3);

        MenuItem v4 = nv.getMenu().findItem(R.id.item_rec_2_lieux_bus);
        v4.setTitle(t4);
        v4.setChecked(true);


    }

/*
    private void registerListClickCallback() {
        ListView myList = (ListView) findViewById(R.id.essai_list);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

                Intent intent = new Intent(BusActivity.this, ResultActivity.class);
                intent.putExtra(id, idInDB);

                startActivity(intent);
            }
        });
    }
*/
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
                Intent i = new Intent(BusActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(BusActivity.this, MainActivity.class);
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
                        switch (menuItem.getItemId()) {
                            case R.id.item_rec_bus:
                                Intent i1 = new Intent(BusActivity.this, BusMainActivity.class);
                                i1.putExtra("type", module_choisi);
                                finish();
                                startActivity(i1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_tout_les_bus:
                                Intent i = new Intent(BusActivity.this, AllbusActivity.class);
                                i.putExtra("type", module_choisi);
                                finish();
                                startActivity(i);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_lieu_bus:
                                Intent i3 = new Intent(BusActivity.this, RueActivity.class);
                                i3.putExtra("type", module_choisi);
                                finish();
                                startActivity(i3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_2_lieux_bus:
                                menuItem.setChecked(true);
                                Toast.makeText(BusActivity.this, "Vous y etes dejà .", Toast.LENGTH_SHORT).show();

                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_iti:
                                Intent i4 = new Intent(BusActivity.this, ItineraireActivity.class);
                                i4.putExtra("type", module_choisi);
                                startActivity(i4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }


}
