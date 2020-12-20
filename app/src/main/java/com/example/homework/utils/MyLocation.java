package com.example.homework.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import com.example.homework.callbacks.CallBack_Location;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

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

    public void turnOnLocation(Activity activity) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(appContext)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();

                if (result.getStatus().getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        status.startResolutionForResult(activity, Constants.PERMISSION_REQUEST_CODE);
                    } catch (Exception ex) { }
                }
            }
        });
    }

    public void getCurrentLocation(CallBack_Location callBack_Location) {
        if (!checkLocationPermission()) {
            callBack_Location.onLocationFailure("You need to allow location");
            return;
        }

        try {
            locationManager = (LocationManager) appContext.getSystemService(LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                callBack_Location.onLocationFailure("You need to allow location");
                return;
            }
        }catch (Exception ex) { }

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
                        return;
                    }
                }
            }
        };

        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }
}

