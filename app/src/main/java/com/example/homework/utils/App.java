package com.example.homework.utils;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MySP.init(this);
        MySignal.init(this);
    }
}
