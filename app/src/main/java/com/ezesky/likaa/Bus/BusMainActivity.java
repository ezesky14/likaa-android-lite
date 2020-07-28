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


/*
CETTE ACTIVITE VA SERVIR A GERER LA RECHERCHE D UNE LIGNE DE BUS D UN TAXI OU D UN GBAKA .
 L UTILISATEUR AURA A CHOISIR LA LIGNE OU IL SOUHAITE AVOIR DES INFOS
 LA GESTION SE FERA EN MULTI PUISQUE CETTE ACTIVITY GERERA EN MEME TEMPS
 1.BUS
 2.TAXI
 3.GBAKA
*/

public class BusMainActivity extends AppCompatActivity {
    final String id = "id";
    final String lieu = "lieu";
    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);

    ImageView im1;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

    private AutoCompleteTextView complete = null;
    private TextView rue_edit = null;
    // Declaration du tableau contenant tout  les libellés des  lignes
    private static final String[] LIGNE_BUS = new String[]{
            "02", "03", "04", "06", "07", "08", "09", "10", "11", "11 Partiel", "12", "13", "14", "15", "17", "18", "19", "20", "21", "21 Partiel", "22", "23", "24", "25", "26", "27", "28", "28 Partiel", "29", "30", "31", "32", "33", "34", "35", "36", "37", "39", "40", "41", "42", "43", "44", "45", "46", "46 Partiel", "47", "49", "49Semi", "51", "52", "53", "55", "58", "59", "64", "67", "74", "75", "76", "78", "81", "82", "83", "84", "85", "90", "91", "92", "610", "201", "202", "203", "204", "205", "206", "207", "209", "210", "211", "212", "212 Partiel", "219"};

    String module_choisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_main);




        /*ON PROCEDE A UNE ANALYSE
         * POUR SAVOIR QUEL EST LE MODULE CHOISI PAR L UTILISATEUR
         * ON ADATERA LE MENU EN FONCTION DE CE MODULE A SAVOIR
         *
         * - BACKGROUND (COULEUR ET IMAGES)
         * - FONCTIONS ET PROCEDURES
         * - VARIALBLES
         * */


        complete = (AutoCompleteTextView) findViewById(R.id.num_bus_edit);
        complete.setThreshold(1);
        Button btn = (Button) findViewById(R.id.Search_button);
        ImageView im2 = (ImageView) findViewById(R.id.img_bus_main);
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Lobster_1.3.otf");
        btn.setTypeface(typeface);

        //recupération de la valeur du module choisi
        Intent intent = getIntent();
        module_choisi = intent.getStringExtra("type");

        //INSTANCIATION DU TOOLBAR DU DRAWERLAYOUT DU NAVIGATIONVIEW ET DE L ACTIONBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);


        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
            //navigationView.setItemBackgroundResource(R.color.md_white_1000_60);

        }
        setupNavigationDrawerContent(navigationView);


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
            setTitle("Recherche d'une ligne de bus");
            toolbar.setBackgroundResource(R.color.md_green_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_bus);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
            // navigationView.setItemBackgroundResource(R.color.md_green_500_25);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, LIGNE_BUS);
            complete.setAdapter(adapter);
            complete.setHint("Entrez une ligne de bus");
            btn.setText("rechercher la ligne de bus");
            btn.setBackgroundResource(R.color.md_green_500_50);
            im2.setImageResource(R.drawable.logo_bus);
        } else if (module_choisi.equals("taxi")) {
            setTitle("Recherche d'un taxi");
            toolbar.setBackgroundResource(R.color.md_orange_300);

            navigationView.setBackgroundResource(R.color.md_white_1000_60);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_taxi);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un taxi", "Tout les taxi", "Recherche à partir d'un lieu", "Recherches avancées");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_taxi.Chercher_Libellé("woroworo", "lib_woro"));
            complete.setAdapter(adapter);
            complete.setHint("Entrez un taxi");
            btn.setText("rechercher le taxi");
            btn.setBackgroundResource(R.color.md_orange_500_50);
            im2.setImageResource(R.drawable.logo_taxi);

        } else if (module_choisi.equals("gbaka")) {
            setTitle("Recherche d'un gbaka");
            toolbar.setBackgroundResource(R.color.md_blue_100);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_gbaka);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un gbaka", "Tout les gbakas", "Recherche à partir d'un lieu", "Recherches avancées");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db_gbaka.Chercher_Libellé("gbaka", "lib_gbaka"));
            complete.setAdapter(adapter);
            complete.setHint("Entrez un gbaka");
            im2.setImageResource(R.drawable.logo_gbaka);
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

        v1.setChecked(true);
    }


    public void onClick_search_bus(View v) {

        //EVENEMENT LORS DU CLIC POUR AFFICHER LES RESULTATS D UNE LIGNE DE BUS
        if (complete.getText().toString().isEmpty()) {
            Toast.makeText(BusMainActivity.this, "Ligne vide", Toast.LENGTH_LONG).show();
        } else {

            Intent intent = new Intent(BusMainActivity.this, ResultActivity.class);
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
                Intent i = new Intent(BusMainActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(BusMainActivity.this, MainActivity.class);
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
                                Toast.makeText(BusMainActivity.this, "Vous y etes dejà .", Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_tout_les_bus:
                                Intent i2 = new Intent(BusMainActivity.this, AllbusActivity.class);
                                i2.putExtra("type", module_choisi);
                                finish();
                                startActivity(i2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_lieu_bus:
                                Intent i = new Intent(BusMainActivity.this, RueActivity.class);
                                i.putExtra("type", module_choisi);
                                finish();
                                startActivity(i);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_rec_2_lieux_bus:
                                Intent i3 = new Intent(BusMainActivity.this, BusActivity.class);
                                i3.putExtra("type", module_choisi);
                                finish();
                                startActivity(i3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_iti:
                                Intent i4 = new Intent(BusMainActivity.this, ItineraireActivity.class);
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
