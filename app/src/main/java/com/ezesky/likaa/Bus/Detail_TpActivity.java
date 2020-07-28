package com.ezesky.likaa.Bus;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ezesky.likaa.R;
import com.ezesky.likaa.BD_sqlite.Bus;
import com.ezesky.likaa.BD_sqlite.DataBaseWrapper;
import com.ezesky.likaa.BD_sqlite.Fonctions_Bus;
import com.ezesky.likaa.BD_sqlite.Fonctions_Gbaka;
import com.ezesky.likaa.BD_sqlite.Fonctions_Taxi;
import com.ezesky.likaa.BD_sqlite.Gbaka;
import com.ezesky.likaa.BD_sqlite.Moy_Tp_Iti;
import com.ezesky.likaa.BD_sqlite.Woroworo;
import com.ezesky.likaa.MainActivity;
import com.ezesky.likaa.SettingsActivity;

import java.util.ArrayList;

public class Detail_TpActivity extends AppCompatActivity {
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
    ArrayList<Moy_Tp_Iti> tpi = new ArrayList<>();

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    String module_choisi;

    DataBaseWrapper db = new DataBaseWrapper(this);
    Fonctions_Gbaka db_gbaka = new Fonctions_Gbaka(this);
    Fonctions_Taxi db_taxi = new Fonctions_Taxi(this);
    Fonctions_Bus db_bus = new Fonctions_Bus(this);

    Woroworo woro;
    Gbaka gbaka;
    String parametre;
    ImageView im1;
    Typeface typeface;
    String moy_tp_iti;

    int nbre_de_vehicule = 0;
    int position_actuelle = 0;

    Button Btn_suiv;
    Button Btn_prec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__tp);


        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/Exo_Regular.otf");

        Intent intent = getIntent();
        moy_tp_iti = intent.getStringExtra("moy_tp_iti");

        tpi = db.AfficherDetail_Iti(moy_tp_iti);
        nbre_de_vehicule = tpi.size();

        Btn_prec = (Button) findViewById(R.id.btn_prec);
        Btn_suiv = (Button) findViewById(R.id.btn_suiv);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("detail des moyens de transports utilisés");

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
        Btn_prec.setEnabled(false);
        Moy_Tp_Iti vehicule_actuel = tpi.get(position_actuelle);
        Affich_Detail(vehicule_actuel.getLIB_TP_ITI(), vehicule_actuel.getTYPE_TP_ITI());

    }

    private void Affich_Detail(String tp, String type) {
        if (type.equals("bus")) {

            // toolbar.setBackgroundResource(R.color.md_green_300);
            //Lancement de la fonction de recherche des bus
            Result_Bus(tp);


        } else if (type.equals("taxi")) {
            //toolbar.setBackgroundResource(R.color.md_orange_300);
            Result_Taxi(tp);

        } else if (type.equals("gbaka")) {
            //toolbar.setBackgroundResource(R.color.md_blue_100);
            Result_Gbaka(tp);

        }

    }


    public void Prec_Click(View v) {
        if (position_actuelle > 0) {
            Btn_suiv.setEnabled(true);
            Moy_Tp_Iti vehicule_actuel = tpi.get(position_actuelle - 1);
            Affich_Detail(vehicule_actuel.getLIB_TP_ITI(), vehicule_actuel.getTYPE_TP_ITI());
            position_actuelle--;
            System.out.println(position_actuelle + " comme postition actuelle");
            if (position_actuelle - 1 < 0) {
                Btn_prec.setEnabled(false);
            } else {

            }
        }

    }

    public void Suiv_Click(View v) {
        if (position_actuelle < nbre_de_vehicule) {
            Btn_prec.setEnabled(true);
            Moy_Tp_Iti vehicule_actuel = tpi.get(position_actuelle + 1);
            Affich_Detail(vehicule_actuel.getLIB_TP_ITI(), vehicule_actuel.getTYPE_TP_ITI());
            position_actuelle++;

            if (position_actuelle + 1 == nbre_de_vehicule) {
                Btn_suiv.setEnabled(false);
            } else {

            }
        }
    }

    private void Result_Bus(String lib) {
        try {


            lib_detail_woro.setText("Lieux où passe le bus");
            lib_type.setText("Type de bus");
            detail_woro.setBackgroundResource(R.color.md_green_500_50);
            prix_woro.setBackgroundResource(R.color.md_green_500_50);
            type_woro.setBackgroundResource(R.color.md_green_500_50);


            db_bus = new Fonctions_Bus(getApplicationContext());
            if (!lib.equals(null)) {

                lib_woro.setText(lib);

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
            Toast.makeText(Detail_TpActivity.this, "Champ vide ou Ligne inexistante " + e, Toast.LENGTH_LONG).show();

            e.printStackTrace();
            e.getMessage();
        }
    }


    private void Result_Taxi(String lib) {
        try {
            db_taxi = new Fonctions_Taxi(getApplicationContext());
            if (!lib.equals(null)) {

                lib_woro.setText(lib);
                String s1 = lib_woro.getText().toString();
                woro = db_taxi.chercheTaxi(s1);
                depart_woro.setText(woro.getDEPART_WORO());
                terminus_woro.setText(woro.getTERMINUS_WORO());
                detail_woro.setText(woro.getDETAIL_WORO());
                prix_woro.setText(woro.getPRIX_WORO() + " FCFA");
                type_woro.setText(woro.getTYPE_WORO_WORO());


            }
        } catch (Exception e) {
            Toast.makeText(Detail_TpActivity.this, "Champ vide ou Ligne inexistante " + e, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            e.getMessage();
        }

    }


    private void Result_Gbaka(String lib) {
        try {

            type_woro.setBackgroundResource(0);
            type_woro.setText("");
            lib_type.setText("");
            lib_detail_woro.setText("Lieux où passe le gbaka");
            detail_woro.setBackgroundResource(R.color.md_blue_500_25);
            prix_woro.setBackgroundResource(R.color.md_blue_500_25);
            type_woro.setBackgroundResource(0);


            db_gbaka = new Fonctions_Gbaka(getApplicationContext());
            if (!lib.equals(null)) {

                lib_woro.setText(lib);
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
            Toast.makeText(Detail_TpActivity.this, "Champ vide ou Ligne inexistante " + e, Toast.LENGTH_LONG).show();
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
                finish();
                return true;

            case R.id.action_settings:
                Intent i = new Intent(Detail_TpActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(Detail_TpActivity.this, MainActivity.class);
                startActivity(i1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
