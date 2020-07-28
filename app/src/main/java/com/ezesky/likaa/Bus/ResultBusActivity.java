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

import java.util.ArrayList;
import java.util.List;

public class ResultBusActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    public List<Bus> result_bus = new ArrayList<>();
    public List<String> result1_bus = new ArrayList<>();

    public List<Woroworo> result_taxi = new ArrayList<>();
    public List<String> result1_taxi = new ArrayList<>();

    public List<Gbaka> result_gbaka = new ArrayList<>();
    public List<String> result1_gbaka = new ArrayList<>();

    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);


    String module_choisi;
    ImageView im1;

    Typeface typeface;
    AssetManager assetManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_bus);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout2);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);


        Intent intent = getIntent();
        String depart = intent.getStringExtra("depart");
        String arrivee = intent.getStringExtra("arrivee");
        module_choisi = intent.getStringExtra("type");


        System.out.println("depart = " + depart);
        System.out.println("terminus = " + arrivee);

        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/Exo_Regular.otf");

        if (module_choisi.equals("bus")) {
            setTitle("Résultats ");
            toolbar.setBackgroundResource(R.color.md_green_300);
            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_bus);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);

            result1_bus = db_bus.Chercher_Bus(depart, arrivee);

            for (String p : result1_bus) {
                System.out.println("bus :" + p);
                result_bus.add(db_bus.chercheBus(p));
            }

        } else if (module_choisi.equals("taxi")) {

            setTitle("Résultats ");
            toolbar.setBackgroundResource(R.color.md_orange_300);
            result1_taxi = db_taxi.Chercher_Taxi(depart, arrivee);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un taxi", "Tout les taxi", "Recherche à partir d'un lieu", "Recherches avancées");

            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_taxi);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
            for (String p : result1_taxi) {
                System.out.println("bus :" + p);
                result_taxi.add(db_taxi.chercheTaxi2(p));

            }

        } else if (module_choisi.equals("gbaka")) {
            setTitle("Résultats ");
            toolbar.setBackgroundResource(R.color.md_blue_100);

            result1_gbaka = db_gbaka.Chercher_Gbaka(depart, arrivee);

            for (String p : result1_gbaka) {
                System.out.println("gbaka :" + p);
                result_gbaka.add(db_gbaka.chercheGbaka2(p));
            }

            View v = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_bus);
            im1 = (ImageView) v.findViewById(R.id.img_bus);
            im1.setImageResource(R.drawable.logo_gbaka);
            navigationView.setBackgroundResource(R.color.md_white_1000_60);
            Changer_Titre_MenuItem(navigationView, "Recherche d'un gbaka", "Tout les gbakas", "Recherche à partir d'un lieu", "Recherches avancées");


        }


        populateListViewFromDB();
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

    private void populateListViewFromDB() {

        if (module_choisi.equals("bus")) {
            ArrayAdapter<Bus> adapter = new MyListAdapter_Bus();


            // Set the adapter for the list view
            ListView myList = (ListView) findViewById(R.id.listView_result);
            myList.setAdapter(adapter);
            TextView itineraire = (TextView) findViewById(R.id.itineraire);
            TextView combinaison = (TextView) findViewById(R.id.conbinaison);
            TextView lib_2 = (TextView) findViewById(R.id.lib_2);
            lib_2.setTypeface(typeface);
            lib_2.setText("Voici le détail des lignes de bus ci dessous");
            itineraire.setTypeface(typeface);
            combinaison.setTypeface(typeface);


            if (db_bus.itineraire_final.equals("") || db_bus.combinaison_possibles.equals("")) {
                itineraire.setText("Desolé il n' y a aucun résultat ");
                combinaison.setText("");
            } else {
                itineraire.setText(db_bus.itineraire_final);
                combinaison.setText(db_bus.combinaison_possibles);
            }

        } else if (module_choisi.equals("taxi")) {
            ArrayAdapter<Woroworo> adapter = new MyListAdapter_Taxi();

            // Set the adapter for the list view
            ListView myList = (ListView) findViewById(R.id.listView_result);
            myList.setAdapter(adapter);


            TextView itineraire = (TextView) findViewById(R.id.itineraire);
            TextView combinaison = (TextView) findViewById(R.id.conbinaison);
            TextView lib_2 = (TextView) findViewById(R.id.lib_2);
            lib_2.setTypeface(typeface);
            lib_2.setText("Voici le détail des taxis ci dessous");
            itineraire.setTypeface(typeface);
            combinaison.setTypeface(typeface);

            if (db_taxi.itineraire_final.equals("") || db_taxi.combinaison_possibles.equals("")) {
                itineraire.setText("Desolé il n' y a aucun résultat ");
                combinaison.setText("");
            } else {
                itineraire.setText(db_taxi.itineraire_final);
                combinaison.setText(db_taxi.combinaison_possibles);
            }
        } else if (module_choisi.equals("gbaka")) {
            ArrayAdapter<Gbaka> adapter = new MyListAdapter_Gbaka();

            // Set the adapter for the list view
            ListView myList = (ListView) findViewById(R.id.listView_result);
            myList.setAdapter(adapter);


            TextView itineraire = (TextView) findViewById(R.id.itineraire);
            TextView combinaison = (TextView) findViewById(R.id.conbinaison);
            TextView lib_2 = (TextView) findViewById(R.id.lib_2);
            lib_2.setTypeface(typeface);
            lib_2.setText("Voici le détail des lignes de gbakas ci dessous");
            itineraire.setTypeface(typeface);
            combinaison.setTypeface(typeface);

            if (db_gbaka.itineraire_final.equals("") || db_gbaka.combinaison_possibles.equals("")) {
                itineraire.setText("Desolé il n' y a aucun résultat ");
                combinaison.setText("");
            } else {
                itineraire.setText(db_gbaka.itineraire_final);
                combinaison.setText(db_gbaka.combinaison_possibles);
            }
        }


    }

    private class MyListAdapter_Taxi extends ArrayAdapter<Woroworo> {

        public MyListAdapter_Taxi() {

            super(ResultBusActivity.this, R.layout.view_all_taxi, result_taxi);
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

            dep.setTypeface(typeface);
            arr.setTypeface(typeface);
            nom_taxi.setTypeface(typeface);
            type_taxi.setTypeface(typeface);
            prix_taxi.setTypeface(typeface);


            dep.setText(currentTaxi.getDEPART_WORO());
            arr.setText(currentTaxi.getTERMINUS_WORO());
            nom_taxi.setText(currentTaxi.getLIB_WORO());
            type_taxi.setText(currentTaxi.getTYPE_WORO_WORO());
            prix_taxi.setText(currentTaxi.getPRIX_WORO());


            return itemView;
        }


    }

    private class MyListAdapter_Bus extends ArrayAdapter<Bus> {

        public MyListAdapter_Bus() {

            super(ResultBusActivity.this, R.layout.resultat_test_view, result_bus);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            int type_bus;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.resultat_test_view, parent, false);
            }
            Bus currentBus = result_bus.get(position);


            // ImageView type_bus_img = (ImageView) itemView.findViewById(R.id.type_bus_img);
            TextView dep_bus = (TextView) itemView.findViewById(R.id.dep_bus);
            TextView arr_bus = (TextView) itemView.findViewById(R.id.arr_bus);
            TextView ligne_bus = (TextView) itemView.findViewById(R.id.num_bus2);
            TextView prix_bus = (TextView) itemView.findViewById(R.id.prix_bus);

            dep_bus.setTypeface(typeface);
            arr_bus.setTypeface(typeface);
            ligne_bus.setTypeface(typeface);
            prix_bus.setTypeface(typeface);


            dep_bus.setText(currentBus.getDEPART_BUS());
            arr_bus.setText(currentBus.getTERMINUS_BUS());
            ligne_bus.setText(currentBus.getID_BUS());

            type_bus = Integer.parseInt(currentBus.getTYPE_BUS());

            if (type_bus == 1) {

                prix_bus.setText("200 FCFA");
            } else if (type_bus == 2) {
                prix_bus.setText("500 FCFA");
            }


            return itemView;
        }


    }

    private class MyListAdapter_Gbaka extends ArrayAdapter<Gbaka> {

        public MyListAdapter_Gbaka() {

            super(ResultBusActivity.this, R.layout.view_all_taxi, result_gbaka);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.view_all_taxi, parent, false);
            }
            Gbaka currentGbaka = result_gbaka.get(position);


            // ImageView type_bus_img = (ImageView) itemView.findViewById(R.id.type_bus_img);

            TextView a = (TextView) itemView.findViewById(R.id.a);
            TextView de = (TextView) itemView.findViewById(R.id.de);
            TextView dep = (TextView) itemView.findViewById(R.id.dep_taxi);
            TextView arr = (TextView) itemView.findViewById(R.id.arr_taxi);
            TextView nom_taxi = (TextView) itemView.findViewById(R.id.nom_taxi);
            TextView type_taxi = (TextView) itemView.findViewById(R.id.type_taxi);
            TextView prix_taxi = (TextView) itemView.findViewById(R.id.prix_taxi);

            a.setBackgroundResource(R.color.md_blue_500_25);
            de.setBackgroundResource(R.color.md_blue_500_25);
            dep.setBackgroundResource(R.color.md_blue_500_25);
            arr.setBackgroundResource(R.color.md_blue_500_25);
            type_taxi.setBackgroundResource(0);

            dep.setTypeface(typeface);
            arr.setTypeface(typeface);
            nom_taxi.setTypeface(typeface);
            type_taxi.setTypeface(typeface);
            prix_taxi.setTypeface(typeface);


            dep.setText(currentGbaka.getDEPART_GBAKA());
            arr.setText(currentGbaka.getTERMINUS_GBAKA());
            nom_taxi.setText(currentGbaka.getLIB_GBAKA());
            type_taxi.setText("");
            if (currentGbaka.getPRIX_GBAKA_FIXE().equals("none") || currentGbaka.getPRIX_GBAKA_FIXE().equals("NONE")) {
                prix_taxi.setText(currentGbaka.getPRIX_PERIODE().replace("f", " FCFA"));
            } else {
                prix_taxi.setText(currentGbaka.getPRIX_GBAKA_FIXE() + " FCFA");
            }


            return itemView;
        }


    }

    public void Iti_Click(View v) {
        Intent intent = new Intent(ResultBusActivity.this, ItineraireActivity.class);
        intent.putExtra("type", module_choisi);
        startActivity(intent);
    }


    private void registerListClickCallback() {
        ListView myList = (ListView) findViewById(R.id.listView_result);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {


                if (module_choisi.equals("bus")) {
                    Bus b = result_bus.get(position);
                    Intent intent = new Intent(ResultBusActivity.this, ResultActivity.class);
                    intent.putExtra("id", b.getID_BUS());
                    intent.putExtra("type", module_choisi);
                    startActivity(intent);
                } else if (module_choisi.equals("taxi")) {

                    Woroworo b = result_taxi.get(position);
                    Intent intent = new Intent(ResultBusActivity.this, ResultActivity.class);
                    intent.putExtra("id", b.getLIB_WORO());
                    intent.putExtra("type", module_choisi);
                    startActivity(intent);
                } else if (module_choisi.equals("gbaka")) {

                    Gbaka b = result_gbaka.get(position);
                    Intent intent = new Intent(ResultBusActivity.this, ResultActivity.class);
                    intent.putExtra("id", b.getLIB_GBAKA());
                    intent.putExtra("type", module_choisi);
                    startActivity(intent);
                }
            }
        });
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
                Intent i = new Intent(ResultBusActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(ResultBusActivity.this, MainActivity.class);
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
                                Intent i1 = new Intent(ResultBusActivity.this, BusMainActivity.class);
                                i1.putExtra("type", module_choisi);
                                finish();
                                startActivity(i1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_tout_les_bus:
                                Intent i2 = new Intent(ResultBusActivity.this, AllbusActivity.class);
                                i2.putExtra("type", module_choisi);
                                finish();
                                startActivity(i2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_lieu_bus:
                                Intent i3 = new Intent(ResultBusActivity.this, RueActivity.class);
                                finish();
                                startActivity(i3);

                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_rec_2_lieux_bus:
                                Intent i = new Intent(ResultBusActivity.this, BusActivity.class);
                                finish();
                                startActivity(i);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_iti:
                                Intent i4 = new Intent(ResultBusActivity.this, ItineraireActivity.class);
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
