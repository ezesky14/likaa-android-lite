package com.ezesky.likaa.Bus;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ezesky.likaa.R;
import com.ezesky.likaa.BD_sqlite.Fonctions_Bus;
import com.ezesky.likaa.BD_sqlite.Fonctions_Gbaka;
import com.ezesky.likaa.BD_sqlite.Fonctions_Taxi;
import com.ezesky.likaa.BD_sqlite.Passer_par;
import com.ezesky.likaa.MainActivity;
import com.ezesky.likaa.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Other_searchActivity extends AppCompatActivity {

    String module_choisi;
    String nom_rue = "";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    final String EXTRA_nom_rue = "id";
    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);
    ImageView im1;
    Typeface typeface;
    AssetManager assetManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_passer_par);
        setTitle("Résultats de la recherche");


        assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/Exo_Regular.otf");

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


        recherch_ligne(navigationView);
        registerListClickCallback();


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void recherch_ligne(NavigationView navigationView) {

        Intent intent = getIntent();
        ListView essai_list = (ListView) findViewById(R.id.essai_list);


        switch (module_choisi) {
            case "bus": {
                navigationView.setBackgroundResource(R.color.md_white_1000_60);
                toolbar.setBackgroundResource(R.color.md_green_300);
                View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
                im1 = (ImageView) v.findViewById(R.id.img_bus);
                im1.setImageResource(R.drawable.logo_bus);
                TextView t = (TextView) findViewById(R.id.t1);
                t.setTypeface(typeface);
                List<String> tab = new ArrayList<>();

                String i = "";
                String rue = intent.getStringExtra(EXTRA_nom_rue);
                nom_rue = rue;
                List<Passer_par> allToDos = db_bus.AllPasser_par(rue);

                for (Passer_par todo : allToDos) {
                    i = i + todo.getID_BUS() + "    ";
                    tab.add(todo.getID_BUS());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tab);
                essai_list.setAdapter(adapter);

                break;
            }
            case "taxi": {
                navigationView.setBackgroundResource(R.color.md_white_1000_60);
                Changer_Titre_MenuItem(navigationView, "Recherche d'un taxi", "Tout les taxi", "Recherche à partir d'un lieu", "Recherches avancées");

                toolbar.setBackgroundResource(R.color.md_orange_300);
                View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
                im1 = (ImageView) v.findViewById(R.id.img_bus);
                im1.setImageResource(R.drawable.logo_taxi);

                TextView t = (TextView) findViewById(R.id.t1);
                t.setText("Liste de taxis passant par " + nom_rue);
                t.setTypeface(typeface);


                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, db_taxi.AllSilloner(nom_rue));
                essai_list.setAdapter(adapter);


                break;
            }
            case "gbaka": {
                navigationView.setBackgroundResource(R.color.md_white_1000_60);
                Changer_Titre_MenuItem(navigationView, "Recherche d'un gbaka", "Tout les gbakas", "Recherche à partir d'un lieu", "Recherches avancées");

                toolbar.setBackgroundResource(R.color.md_blue_100);
                View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
                im1 = (ImageView) v.findViewById(R.id.img_bus);
                im1.setImageResource(R.drawable.logo_gbaka);


                String rue = intent.getStringExtra(EXTRA_nom_rue);
                nom_rue = rue;
                TextView t = (TextView) findViewById(R.id.t1);
                t.setTypeface(typeface);
                t.setText("Liste de gbakas passant par " + nom_rue);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, db_gbaka.AllRouler(rue));
                essai_list.setAdapter(adapter);
                break;
            }
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                Intent i = new Intent(Other_searchActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(Other_searchActivity.this, MainActivity.class);
                startActivity(i1);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void registerListClickCallback() {
        final ListView myList = (ListView) findViewById(R.id.essai_list);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {


                switch (module_choisi) {
                    case "bus": {
                        List<Passer_par> allToDos = db_bus.AllPasser_par(nom_rue);
                        Intent intent = new Intent(Other_searchActivity.this, ResultActivity.class);
                        intent.putExtra("id", allToDos.get(position).getID_BUS());
                        intent.putExtra("type", module_choisi);
                        startActivity(intent);
                        break;
                    }
                    case "taxi": {
                        List<String> allToDos = db_taxi.AllSilloner(nom_rue);
                        Intent intent = new Intent(Other_searchActivity.this, ResultActivity.class);
                        intent.putExtra("id", allToDos.get(position));
                        intent.putExtra("type", module_choisi);
                        startActivity(intent);
                        break;
                    }
                    case "gbaka": {
                        List<String> allToDos = db_gbaka.AllRouler(nom_rue);
                        Intent intent = new Intent(Other_searchActivity.this, ResultActivity.class);
                        intent.putExtra("id", allToDos.get(position));
                        intent.putExtra("type", module_choisi);

                        startActivity(intent);
                        break;
                    }
                }


            }
        });
    }


    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        textView = (TextView) findViewById(R.id.action_bar_title);
                        switch (menuItem.getItemId()) {
                            case R.id.item_rec_bus:
                                menuItem.setChecked(false);
                                Intent i1 = new Intent(Other_searchActivity.this, BusMainActivity.class);
                                i1.putExtra("type", module_choisi);
                                finish();
                                startActivity(i1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_tout_les_bus:
                                Intent i3 = new Intent(Other_searchActivity.this, AllbusActivity.class);
                                i3.putExtra("type", module_choisi);
                                finish();
                                startActivity(i3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_lieu_bus:
                                Intent i2 = new Intent(Other_searchActivity.this, AllbusActivity.class);
                                i2.putExtra("type", module_choisi);
                                finish();
                                startActivity(i2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_rec_2_lieux_bus:
                                Intent intent = new Intent(Other_searchActivity.this, BusActivity.class);
                                intent.putExtra("type", module_choisi);
                                finish();
                                startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_iti:
                                Intent i4 = new Intent(Other_searchActivity.this, ItineraireActivity.class);
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
