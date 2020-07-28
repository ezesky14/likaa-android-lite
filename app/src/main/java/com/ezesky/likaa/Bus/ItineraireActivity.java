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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ezesky.likaa.R;
import com.ezesky.likaa.BD_sqlite.DataBaseWrapper;
import com.ezesky.likaa.BD_sqlite.Itineraire;
import com.ezesky.likaa.MainActivity;
import com.ezesky.likaa.SettingsActivity;

import java.util.ArrayList;

public class ItineraireActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    String module_choisi;
    ArrayList<RadioButton> itineraire;
    DataBaseWrapper db = new DataBaseWrapper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itineraire);
        setTitle("Guide des itineraires");

        final Spinner sp = (Spinner) findViewById(R.id.spinner);
        final TextView moy_tp_iti = (TextView) findViewById(R.id.moy_tp_iti);
        final TextView type_iti = (TextView) findViewById(R.id.type_iti);
        final TextView detail_iti = (TextView) findViewById(R.id.detail_iti);
        final TextView lab_moy_tp_iti = (TextView) findViewById(R.id.lab_moy_tp_iti);
        final TextView lab_type_iti = (TextView) findViewById(R.id.lab_type_iti);
        final TextView lab_detail_iti = (TextView) findViewById(R.id.lab_detail_iti);


        final String[] tp_iti = {""};
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Exo_Regular.otf");
        moy_tp_iti.setTypeface(typeface);
        type_iti.setTypeface(typeface);
        detail_iti.setTypeface(typeface);

        lab_moy_tp_iti.setTypeface(typeface);
        lab_type_iti.setTypeface(typeface);
        lab_detail_iti.setTypeface(typeface);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.AllIti());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Itineraire res = db.Afficher_Iti(sp.getItemAtPosition(position).toString());

                moy_tp_iti.setText(res.getMOYEN_TP_ITI());
                type_iti.setText(res.getTYPE_ITI());
                tp_iti[0] = moy_tp_iti.getText().toString();
                detail_iti.setText(res.getDETAIL_ITI());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    public void Map_Click(View v) {

    }


    public void Tp_Click(View v) {
        System.out.println(db.tmp_moy_tp_iti);
        Intent i = new Intent(ItineraireActivity.this, Detail_TpActivity.class);

        i.putExtra("moy_tp_iti", db.tmp_moy_tp_iti);
        startActivity(i);
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
                Intent i = new Intent(ItineraireActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.acc:
                finish();
                Intent i1 = new Intent(ItineraireActivity.this, MainActivity.class);
                startActivity(i1);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
