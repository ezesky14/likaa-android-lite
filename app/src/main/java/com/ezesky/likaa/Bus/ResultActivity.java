package com.ezesky.likaa.Bus;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ezesky.likaa.R;
import com.ezesky.likaa.BD_sqlite.Bus;
import com.ezesky.likaa.BD_sqlite.Fonctions_Bus;
import com.ezesky.likaa.BD_sqlite.Fonctions_Gbaka;
import com.ezesky.likaa.BD_sqlite.Fonctions_Taxi;
import com.ezesky.likaa.BD_sqlite.Gbaka;
import com.ezesky.likaa.BD_sqlite.Woroworo;
import com.ezesky.likaa.MainActivity;
import com.ezesky.likaa.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public class ResultActivity extends AppCompatActivity {

    TextView lib_woro;
    TextView depart_woro;
    TextView terminus_woro;
    TextView detail_woro;
    TextView prix_woro;
    TextView type_woro;

    TextView lib_type;
    TextView lib_pr;
    TextView lib_detail_woro;


    Bus SelectedBus;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    String module_choisi;


    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);

    Woroworo woro;
    Gbaka gbaka;
    String parametre;
    ImageView im1;
    Typeface typeface;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);


        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/Exo_Regular.otf");

        Intent intent = getIntent();
        module_choisi = intent.getStringExtra("type");
        parametre = intent.getStringExtra("id");


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


        lib_woro = (TextView) findViewById(R.id.lib_woro);
        depart_woro = (TextView) findViewById(R.id.depart_woro);
        terminus_woro = (TextView) findViewById(R.id.terminus_woro);
        detail_woro = (TextView) findViewById(R.id.detail_woro);
        prix_woro = (TextView) findViewById(R.id.prix_woro);
        type_woro = (TextView) findViewById(R.id.type_woro);

        lib_type = (TextView) findViewById(R.id.lib_typ);
        lib_pr = (TextView) findViewById(R.id.lib_pr);
        lib_detail_woro = (TextView) findViewById(R.id.lib_detail_woro);


        lib_woro.setTypeface(typeface);
        depart_woro.setTypeface(typeface);
        terminus_woro.setTypeface(typeface);
        detail_woro.setTypeface(typeface);
        prix_woro.setTypeface(typeface);
        type_woro.setTypeface(typeface);
        lib_type.setTypeface(typeface);
        lib_pr.setTypeface(typeface);
        lib_detail_woro.setTypeface(typeface);


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
            setTitle("Résultats de la recherche");
            toolbar.setBackgroundResource(R.color.md_green_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_bus);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
            //Lancement de la fonction de recherche des bus
            Result_Bus();


        } else if (module_choisi.equals("taxi")) {
            setTitle("Résultats de la recherche");
            toolbar.setBackgroundResource(R.color.md_orange_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_taxi);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un taxi", "Tout les taxi", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);

            //Lancement de la fonction de recherche des bus
            Result_Taxi();

        } else if (module_choisi.equals("gbaka")) {
            setTitle("Résultats de la recherche");
            toolbar.setBackgroundResource(R.color.md_blue_100);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_gbaka);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un gbaka", "Tout les gbakas", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
            //Lancement de la fonction de recherche des bus
            Result_Gbaka();

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


    }

    private void Result_Bus() {
        try {


            lib_detail_woro.setText("Lieux où passe le bus");
            lib_type.setText("Type de bus");
            detail_woro.setBackgroundResource(R.color.md_green_500_50);
            prix_woro.setBackgroundResource(R.color.md_green_500_50);
            type_woro.setBackgroundResource(R.color.md_green_500_50);


            db_bus = new Fonctions_Bus(getApplicationContext());
            if (!parametre.equals(null)) {

                lib_woro.setText(parametre);

                String s1 = lib_woro.getText().toString();
                SelectedBus = db_bus.chercheBus(s1);
                depart_woro.setText(SelectedBus.getDEPART_BUS());
                terminus_woro.setText(SelectedBus.getTERMINUS_BUS());
                detail_woro.setText(SelectedBus.getDETAIL_BUS());
                int i = Integer.parseInt(SelectedBus.getTYPE_BUS());

                if (i == 1) {
                    prix_woro.setText("200 FCFA");
                    type_woro.setText("BUS ORDINAIRE");
                    //img.setImageResource(R.drawable.bus_ordinaire);
                } else {
                    prix_woro.setText("500 FCFA");
                    type_woro.setText("BUS EXPRESS");
                    // img.setImageResource(R.drawable.bus_express);}


                }


            }

        } catch (Exception e) {
            Toast.makeText(ResultActivity.this, "Champ vide ou Ligne inexistante " + e, Toast.LENGTH_LONG).show();

            e.printStackTrace();
            e.getMessage();
        }
    }


    private void Result_Taxi() {
        try {
            db_taxi = new Fonctions_Taxi(getApplicationContext());
            if (!parametre.equals(null)) {

                lib_woro.setText(parametre);
                String s1 = lib_woro.getText().toString();
                woro = db_taxi.chercheTaxi(s1);
                depart_woro.setText(woro.getDEPART_WORO());
                terminus_woro.setText(woro.getTERMINUS_WORO());
                detail_woro.setText(woro.getDETAIL_WORO());
                prix_woro.setText(woro.getPRIX_WORO() + " FCFA");
                type_woro.setText(woro.getTYPE_WORO_WORO());


            }
        } catch (Exception e) {
            Toast.makeText(ResultActivity.this, "Champ vide ou Ligne inexistante " + e, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            e.getMessage();
        }

    }


    private void Result_Gbaka() {
        try {

            type_woro.setBackgroundResource(0);
            type_woro.setText("");
            lib_type.setText("");
            lib_detail_woro.setText("Lieux où passe le gbaka");
            detail_woro.setBackgroundResource(R.color.md_blue_500_25);
            prix_woro.setBackgroundResource(R.color.md_blue_500_25);
            type_woro.setBackgroundResource(0);


            db_gbaka = new Fonctions_Gbaka(getApplicationContext());
            if (!parametre.equals(null)) {

                lib_woro.setText(parametre);
                String s1 = lib_woro.getText().toString();
                gbaka = db_gbaka.chercheGbaka(s1);
                depart_woro.setText(gbaka.getDEPART_GBAKA());
                terminus_woro.setText(gbaka.getTERMINUS_GBAKA());
                detail_woro.setText(gbaka.getDETAIL_GBAKA());


                if (gbaka.getPRIX_GBAKA_FIXE().equals("none") || gbaka.getPRIX_GBAKA_FIXE().equals("NONE")) {
                    prix_woro.setText(gbaka.getPRIX_PERIODE().replace("f", " FCFA"));
                } else if (gbaka.getPRIX_PERIODE().equals("none") || gbaka.getPRIX_PERIODE().equals("NONE")) {
                    prix_woro.setText(gbaka.getPRIX_GBAKA_FIXE() + " FCFA");
                }
            }


        } catch (Exception e) {
            Toast.makeText(ResultActivity.this, "Champ vide ou Ligne inexistante " + e, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            e.getMessage();
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
                Intent i = new Intent(ResultActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(ResultActivity.this, MainActivity.class);
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
                                Intent i1 = new Intent(ResultActivity.this, BusMainActivity.class);
                                i1.putExtra("type", module_choisi);
                                finish();
                                startActivity(i1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_tout_les_bus:
                                Intent i2 = new Intent(ResultActivity.this, AllbusActivity.class);
                                i2.putExtra("type", module_choisi);
                                finish();
                                startActivity(i2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_rec_lieu_bus:
                                Intent i = new Intent(ResultActivity.this, RueActivity.class);
                                i.putExtra("type", module_choisi);
                                finish();
                                startActivity(i);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_2_lieux_bus:
                                Intent i3 = new Intent(ResultActivity.this, BusActivity.class);
                                i3.putExtra("type", module_choisi);
                                finish();
                                startActivity(i3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_iti:
                                Intent i4 = new Intent(ResultActivity.this, ItineraireActivity.class);
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
			

			

