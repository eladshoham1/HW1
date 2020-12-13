package com.example.homework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework.R;
import com.example.homework.utils.Constants;
import com.example.homework.utils.MySP;
import com.example.homework.utils.MySignal;

public class Activity_Settings extends Activity_Base {
    private EditText settings_EDT_playerAName;
    private EditText settings_EDT_playerBName;
    private Button settings_BTN_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViews();
        initViews();
    }

    private void findViews() {
        settings_EDT_playerAName = findViewById(R.id.settings_EDT_playerAName);
        settings_EDT_playerBName = findViewById(R.id.settings_EDT_playerBName);
        settings_BTN_apply = findViewById(R.id.settings_BTN_apply);
    }

    private void initViews() {
        settings_BTN_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    private boolean checkSettings() {
        String playerAName = settings_EDT_playerAName.getText().toString();
        String playerBName = settings_EDT_playerBName.getText().toString();

        if (checkPlayerName(playerAName) && checkPlayerName(playerBName)) {
            MySP.getInstance().putString(MySP.KEYS.PLAYER_A_NAME, playerAName);
            MySP.getInstance().putString(MySP.KEYS.PLAYER_B_NAME, playerBName);

            return true;
        }

        return false;
    }

    private boolean checkPlayerName(String name) {
        return (name.trim().length() > Constants.ZERO && name.length() <= Constants.EIGHT_CHARACTERS);
    }

    private void openMainActivity() {
        if (checkSettings()) {
            Intent myIntent = new Intent(this, Activity_Main.class);
            startActivity(myIntent);
            closeActivity();
        } else
            MySignal.getInstance().toast("Invalid players names");
    }
}