package com.example.homework.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import com.example.homework.callbacks.CallBack_Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import static android.content.Context.LOCATION_SERVICE;

public class MyLocation {
    private static MyLocation instance;
    private Context appContext;

    private FusedLocationProviderClient client;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public static MyLocation getInstance() {
        return instance;
    }

    private MyLocation(Context appContext) {
        this.appContext = appContext.getApplicationContext();
        this.client = LocationServices.getFusedLocationProviderClient(this.appContext);
    }

    public static void init(Context appContext) {
        if (instance == null) {
            instance = new MyLocation(appContext);
        }
    }

    public boolean checkLocationPermission() {
        return (appContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && appContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkIfLocationTurnOn() {
        locationManager = (LocationManager) appContext.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void getCurrentLocation(CallBack_Location callBack_Location) {
        if (!checkLocationPermission() || !checkIfLocationTurnOn()) {
            callBack_Location.onLocationFailure("Location must be turn on");
            return;
        }

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        callBack_Location.onLocationSuccess(location.getLatitude(), location.getLongitude());
                    }
                }
            }
        };

        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }
}

