package com.example.homework.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySP {

    public interface KEYS {
        public static final String MY_SP = "MY_SP";
        public static final String PLAYER_A_NAME = "PLAYER_A_NAME";
        public static final String PLAYER_A_DEFAULT_NAME = "Player A";
        public static final String PLAYER_B_NAME = "PLAYER_B_NAME";
        public static final String PLAYER_B_DEFAULT_NAME = "Player B";
        public static final String SOUND_ENABLE = "SOUND_ENABLE";
        public static final String TOP_TEN = "TOP_TEN";
    }

    private static MySP instance;
    private SharedPreferences prefs;

    public static MySP getInstance() {
        return instance;
    }

    private MySP(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(KEYS.MY_SP, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new MySP(context);
        }
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int def) {
        return prefs.getInt(key, def);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean def) {
        return prefs.getBoolean(key, def);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key).apply();
    }
}
