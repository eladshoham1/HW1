package com.example.homework.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.homework.R;
import com.example.homework.callbacks.CallBack_Location;
import com.example.homework.utils.Constants;
import com.example.homework.utils.MyLocation;
import com.example.homework.utils.MySignal;

public class Activity_Main extends Activity_Base {
    private Button main_BTN_newGame;
    private Button main_BTN_topTen;
    private Button main_BTN_settings;

    private boolean locationEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationPermission();
        findViews();
        initViews();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            locationEnable = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
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
                getLocationAndStartNewGame();
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

    private void locationPermission() {
        if (!MyLocation.getInstance().checkLocationPermission()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            locationEnable = true;
        }
    }

    private void getLocationAndStartNewGame() {
        if (!locationEnable) {
            MySignal.getInstance().toast("You must to allow location");
            return;
        }

        MyLocation.getInstance().getCurrentLocation(new CallBack_Location() {
            @Override
            public void onLocationSuccess(double latitude, double longitude) {
                startNewGame(latitude, longitude);
            }

            @Override
            public void onLocationFailure(String message) {
                MySignal.getInstance().toast(message);
            }
        });
    }

    private void startNewGame(double latitude, double longitude) {
        Intent myIntent = new Intent(this, Activity_Game.class);
        myIntent.putExtra(Constants.EXTRA_KEY_LATITUDE, latitude);
        myIntent.putExtra(Constants.EXTRA_KEY_LONGITUDE, longitude);
        startActivity(myIntent);
    }

    private void openNewActivity(Class activity) {
        Intent myIntent = new Intent(this, activity);
        startActivity(myIntent);
    }
}