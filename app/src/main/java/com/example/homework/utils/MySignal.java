package com.example.homework.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.homework.objects.Record;
import com.example.homework.objects.TopTen;

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

    public void updateImage(int id, ImageView image) {
        Glide
                .with(appContext)
                .load(id)
                .into(image);
    }

    public void playSound(int rawSound) {
        if (mp != null) {
            try {
                mp.reset();
                mp.release();
            } catch (Exception ex) { }
        }

        mp = null;
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

    public ArrayAdapter<Record> getArrayAdapter(TopTen topTen) {
        return new ArrayAdapter<Record>(appContext, android.R.layout.simple_list_item_1 , topTen.getAllRecords());
    }

    public void toast(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }
}
