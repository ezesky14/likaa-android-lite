package com.ezesky.likaa;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ezesky.likaa.Bus.BusMainActivity;

public class MainActivity extends AppCompatActivity {
    Button Btn_Bus;
    Button Btn_Taxi;
    Button Btn_Gbaka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Btn_Bus = (Button) findViewById(R.id.btn_bus);
        Btn_Taxi = (Button) findViewById(R.id.btn_taxi);
        Btn_Gbaka = (Button) findViewById(R.id.btn_gbaka);
        TextView l = (TextView) findViewById(R.id.l);

        TextView l1 = (TextView) findViewById(R.id.lib_fbk);
        TextView l2 = (TextView) findViewById(R.id.lib_param);


        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Lobster_1.3.otf");

        Btn_Bus.setTypeface(typeface);
        Btn_Taxi.setTypeface(typeface);
        Btn_Gbaka.setTypeface(typeface);
        l.setTypeface(typeface);


        l1.setTypeface(typeface);
        l2.setTypeface(typeface);


        ImageView img_param = (ImageView) findViewById(R.id.im_param);
        img_param.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });


        ImageView img_fbk = (ImageView) findViewById(R.id.im_fbk);
        img_fbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CODE
            }
        });


    }


    public void bus_Click(View view) {
        Intent intent = new Intent(MainActivity.this, BusMainActivity.class);
        intent.putExtra("type", "bus");
        startActivity(intent);
    }


    public void taxi_Click(View view) {
        Intent intent = new Intent(MainActivity.this, BusMainActivity.class);
        intent.putExtra("type", "taxi");
        startActivity(intent);
    }


    public void gbaka_Click(View view) {
        Intent intent = new Intent(MainActivity.this, BusMainActivity.class);
        intent.putExtra("type", "gbaka");
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.autres_menu, menu);
        return true;
    }


}

