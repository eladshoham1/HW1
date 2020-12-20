package com.example.homework.callbacks;

public interface CallBack_Location {
    void onLocationSuccess(double latitude, double longitude);
    void onLocationFailure(String message);
}
