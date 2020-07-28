package com.ezesky.likaa.Bus;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.List;


public class AllbusActivity extends AppCompatActivity {
    public List<Bus> result_bus;
    public List<Woroworo> result_taxi;
    public List<Gbaka> result_gbaka;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    String module_choisi;

    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);

    ImageView im1;
    Typeface typeface;
    AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allbus);
        assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/Exo_Regular.otf");
        Intent i = getIntent();
        module_choisi = i.getStringExtra("type");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
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
            setTitle("Tout les bus");
            toolbar.setBackgroundResource(R.color.md_green_300);
            assert navigationView != null;
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_bus);
            result_bus = db_bus.getAllRows();
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
        } else if (module_choisi.equals("taxi")) {
            setTitle("Tout les taxis");
            toolbar.setBackgroundResource(R.color.md_orange_300);
            assert navigationView != null;
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_taxi);
            result_taxi = db_taxi.getAllTaxi();
            Changer_Titre_MenuItem(navigationView, "Recherche d'un taxi", "Tout les taxi", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
        } else if (module_choisi.equals("gbaka")) {
            setTitle("Tout les gbakas");
            toolbar.setBackgroundResource(R.color.md_blue_100);
            assert navigationView != null;
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_gbaka);
            result_gbaka = db_gbaka.getAllGbaka();
            Changer_Titre_MenuItem(navigationView, "Recherche d'un gbaka", "Tout les gbakas", "Recherche à partir d'un lieu", "Recherches avancées");
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
        }


        populateListViewFromDB(module_choisi);
        registerListClickCallback();


    }

    private void Changer_Titre_MenuItem(NavigationView nv, String t1, String t2, String t3, String t4) {


        MenuItem v1 = nv.getMenu().findItem(R.id.item_rec_bus);
        v1.setTitle(t1);


        MenuItem v2 = nv.getMenu().findItem(R.id.item_tout_les_bus);
        v2.setTitle(t2);
        v2.setChecked(true);


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

    private void populateListViewFromDB(String type) {

        switch (type) {
            case "bus": {
                ArrayAdapter<Bus> adapter = new MyListAdapter_Bus();
                ListView myList = (ListView) findViewById(R.id.listView_all_bus);
                myList.setAdapter(adapter);

                break;
            }
            case "taxi": {
                ArrayAdapter<Woroworo> adapter = new MyListAdapter_Taxi();
                ListView myList = (ListView) findViewById(R.id.listView_all_bus);
                myList.setAdapter(adapter);
                break;
            }
            case "gbaka": {
                ArrayAdapter<Gbaka> adapter = new MyListAdapter_Gbaka();
                ListView myList = (ListView) findViewById(R.id.listView_all_bus);
                myList.setAdapter(adapter);
                break;
            }
        }


    }


    private class MyListAdapter_Bus extends ArrayAdapter<Bus> {

        public MyListAdapter_Bus() {

            super(AllbusActivity.this, R.layout.affiche, result_bus);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            int type_bus;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.affiche, parent, false);
            }
            Bus currentBus = result_bus.get(position);


            ImageView type_bus_img = (ImageView) itemView.findViewById(R.id.type_bus_img);
            TextView dep = (TextView) itemView.findViewById(R.id.dep);
            TextView arr = (TextView) itemView.findViewById(R.id.arr);
            TextView ligne_bus = (TextView) itemView.findViewById(R.id.ligne_bus);
            dep.setTypeface(typeface);
            arr.setTypeface(typeface);
            ligne_bus.setTypeface(typeface);

            dep.setText(currentBus.getDEPART_BUS());
            arr.setText(currentBus.getTERMINUS_BUS());
            ligne_bus.setText(currentBus.getID_BUS());
            type_bus = Integer.parseInt(currentBus.getTYPE_BUS());

            if (type_bus == 1) {

                type_bus_img.setImageResource(R.drawable.bus_ordinaire);
            } else if (type_bus == 2) {
                type_bus_img.setImageResource(R.drawable.bus_express);
            }


            return itemView;
        }


    }

    private class MyListAdapter_Gbaka extends ArrayAdapter<Gbaka> {

        public MyListAdapter_Gbaka() {

            super(AllbusActivity.this, R.layout.view_all_taxi, result_gbaka);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.view_all_taxi, parent, false);
            }
            Gbaka currentTaxi = result_gbaka.get(position);


            // ImageView type_bus_img = (ImageView) itemView.findViewById(R.id.type_bus_img);
            TextView dep = (TextView) itemView.findViewById(R.id.dep_taxi);
            TextView arr = (TextView) itemView.findViewById(R.id.arr_taxi);
            TextView nom_taxi = (TextView) itemView.findViewById(R.id.nom_taxi);
            TextView type_taxi = (TextView) itemView.findViewById(R.id.type_taxi);
            TextView prix_gbaka = (TextView) itemView.findViewById(R.id.prix_taxi);
            TextView de = (TextView) itemView.findViewById(R.id.de);
            TextView a = (TextView) itemView.findViewById(R.id.a);

            dep.setTypeface(typeface);
            arr.setTypeface(typeface);
            nom_taxi.setTypeface(typeface);
            type_taxi.setTypeface(typeface);
            prix_gbaka.setTypeface(typeface);
            de.setTypeface(typeface);
            a.setTypeface(typeface);


            //Gestion des background
            dep.setBackgroundResource(R.color.md_blue_500_25);
            arr.setBackgroundResource(R.color.md_blue_500_25);
            prix_gbaka.setBackgroundResource(R.color.md_blue_500_25);
            type_taxi.setBackgroundResource(0);
            de.setBackgroundResource(R.color.md_blue_500_25);
            a.setBackgroundResource(R.color.md_blue_500_25);


            dep.setText(currentTaxi.getDEPART_GBAKA());
            arr.setText(currentTaxi.getTERMINUS_GBAKA());
            nom_taxi.setText(currentTaxi.getLIB_GBAKA());
            //  type_taxi.setText(currentTaxi.getTYPE_WORO_WORO());


            if (currentTaxi.getPRIX_GBAKA_FIXE().equals("none") || currentTaxi.getPRIX_GBAKA_FIXE().equals("NONE")) {
                prix_gbaka.setText("Prix variant : " + currentTaxi.getPRIX_PERIODE().replace("f", " FCFA"));
            } else if (currentTaxi.getPRIX_PERIODE().equals("none") || currentTaxi.getPRIX_PERIODE().equals("NONE")) {
                prix_gbaka.setText("à " + currentTaxi.getPRIX_GBAKA_FIXE() + " FCFA");
            }

            type_taxi.setEnabled(false);
            return itemView;
        }


    }

    private class MyListAdapter_Taxi extends ArrayAdapter<Woroworo> {

        public MyListAdapter_Taxi() {

            super(AllbusActivity.this, R.layout.view_all_taxi, result_taxi);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.view_all_taxi, parent, false);
            }
            Woroworo currentTaxi = result_taxi.get(position);


            // ImageView type_bus_img = (ImageView) itemView.findViewById(R.id.type_bus_img);
            TextView dep = (TextView) itemView.findViewById(R.id.dep_taxi);
            TextView arr = (TextView) itemView.findViewById(R.id.arr_taxi);
            TextView nom_taxi = (TextView) itemView.findViewById(R.id.nom_taxi);
            TextView type_taxi = (TextView) itemView.findViewById(R.id.type_taxi);
            TextView prix_taxi = (TextView) itemView.findViewById(R.id.prix_taxi);
            TextView de = (TextView) itemView.findViewById(R.id.de);
            TextView a = (TextView) itemView.findViewById(R.id.a);


            dep.setTypeface(typeface);
            arr.setTypeface(typeface);
            nom_taxi.setTypeface(typeface);
            type_taxi.setTypeface(typeface);
            prix_taxi.setTypeface(typeface);
            de.setTypeface(typeface);
            a.setTypeface(typeface);


            dep.setText(currentTaxi.getDEPART_WORO());
            arr.setText(currentTaxi.getTERMINUS_WORO());
            nom_taxi.setText(currentTaxi.getLIB_WORO());
            type_taxi.setText(currentTaxi.getTYPE_WORO_WORO());
            prix_taxi.setText(currentTaxi.getPRIX_WORO() + " FCFA");


            return itemView;
        }


    }


    private void registerListClickCallback() {
        ListView myList = (ListView) findViewById(R.id.listView_all_bus);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {


                if (module_choisi.equals("bus")) {
                    Bus b = result_bus.get(position);
                    Intent intent = new Intent(AllbusActivity.this, ResultActivity.class);
                    intent.putExtra("id", b.getID_BUS());
                    intent.putExtra("type", module_choisi);
                    startActivity(intent);
                } else if (module_choisi.equals("taxi")) {
                    Woroworo b = result_taxi.get(position);

                    Intent intent = new Intent(AllbusActivity.this, ResultActivity.class);
                    intent.putExtra("id", b.getLIB_WORO());
                    intent.putExtra("type", module_choisi);

                    startActivity(intent);
                } else if (module_choisi.equals("gbaka")) {
                    Gbaka b = result_gbaka.get(position);
                    Intent intent = new Intent(AllbusActivity.this, ResultActivity.class);
                    intent.putExtra("id", b.getLIB_GBAKA());
                    intent.putExtra("type", module_choisi);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                Intent i = new Intent(AllbusActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(AllbusActivity.this, MainActivity.class);
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
                                Intent i1 = new Intent(AllbusActivity.this, BusMainActivity.class);
                                i1.putExtra("type", module_choisi);
                                finish();
                                startActivity(i1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_tout_les_bus:
                                Toast.makeText(AllbusActivity.this, "Vous y etes dejà .", Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_lieu_bus:
                                Intent i3 = new Intent(AllbusActivity.this, RueActivity.class);
                                i3.putExtra("type", module_choisi);
                                finish();
                                startActivity(i3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_2_lieux_bus:
                                Intent i = new Intent(AllbusActivity.this, BusActivity.class);
                                i.putExtra("type", module_choisi);
                                finish();
                                startActivity(i);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;


                            case R.id.item_iti:
                                Intent i4 = new Intent(AllbusActivity.this, ItineraireActivity.class);
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
