package com.example.homework.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homework.R;
import com.example.homework.utils.Constants;

public class Activity_Main extends Activity_Base {
    private Button main_BTN_newGame;
    private Button main_BTN_topTen;
    private Button main_BTN_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocationPermission();
        findViews();
        initViews();
    }

    private void checkLocationPermission() {
        if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_CODE);
    }

    private void findViews() {
        main_BTN_newGame = findViewById(R.id.main_BTN_newGame);
        main_BTN_topTen = findViewById(R.id.main_BTN_topTen);
        main_BTN_settings = findViewById(R.id.main_BTN_settings);
    }

    private void initViews() {
        main_BTN_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(Activity_Game.class);
            }
        });

        main_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(Activity_TopTen.class);
            }
        });

        main_BTN_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(Activity_Settings.class);
            }
        });
    }

    private void openNewActivity(Class activity) {
        Intent myIntent = new Intent(this, activity);
        startActivity(myIntent);
    }
}