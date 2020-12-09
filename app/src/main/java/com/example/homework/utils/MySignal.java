package com.example.homework.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.homework.objects.Record;

import java.util.ArrayList;

public class MySignal {
    private static MySignal instance;
    private Context appContext;
    private MediaPlayer mp;

    public static MySignal getInstance() {
        return instance;
    }

    private MySignal(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    public static void init(Context appContext) {
        if (instance == null) {
            instance = new MySignal(appContext);
        }
    }

    public void playSound(int rawSound) {
        if (mp != null) {
            try {
                mp.reset();
                mp.release();
            } catch (Exception ex) { }
        }

        mp = MediaPlayer.create(appContext, rawSound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
        mp.start();
    }

    public ArrayAdapter<Record> getArrayAdapter(ArrayList<Record> arrayList) {
        return new ArrayAdapter<Record>(appContext, android.R.layout.simple_list_item_1 ,arrayList);
    }

    public void toast(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }
}
