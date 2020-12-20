package com.example.homework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.homework.R;
import com.example.homework.utils.Constants;
import com.example.homework.utils.MySP;

public class Activity_Settings extends Activity_Base {
    private EditText settings_EDT_playerAName;
    private EditText settings_EDT_playerBName;
    private Switch settings_SWT_sound;
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
        settings_SWT_sound = findViewById(R.id.settings_SWT_sound);
        settings_BTN_apply = findViewById(R.id.settings_BTN_apply);
    }

    private void initViews() {
        settings_EDT_playerAName.setHint(MySP.getInstance().getString(MySP.KEYS.PLAYER_A_NAME, MySP.KEYS.PLAYER_A_DEFAULT_NAME));
        settings_EDT_playerBName.setHint(MySP.getInstance().getString(MySP.KEYS.PLAYER_B_NAME, MySP.KEYS.PLAYER_B_DEFAULT_NAME));
        settings_SWT_sound.setChecked(MySP.getInstance().getBoolean(MySP.KEYS.SOUND_ENABLE, true));

        settings_BTN_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    private void checkSettings() {
        String playerAName = settings_EDT_playerAName.getText().toString();
        String playerBName = settings_EDT_playerBName.getText().toString();

        if (checkPlayerName(playerAName)) {
            MySP.getInstance().putString(MySP.KEYS.PLAYER_A_NAME, playerAName);
        }

        if (checkPlayerName(playerBName)) {
            MySP.getInstance().putString(MySP.KEYS.PLAYER_B_NAME, playerBName);
        }

        MySP.getInstance().putBoolean(MySP.KEYS.SOUND_ENABLE, settings_SWT_sound.isChecked());
    }

    private boolean checkPlayerName(String name) {
        return (name.trim().length() > 0 && name.length() <= Constants.EIGHT_CHARACTERS);
    }

    private void openMainActivity() {
        checkSettings();
        Intent myIntent = new Intent(this, Activity_Main.class);
        startActivity(myIntent);
        closeActivity();
    }
}