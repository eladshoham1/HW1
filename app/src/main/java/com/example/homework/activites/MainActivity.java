package com.example.homework.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homework.R;

public class MainActivity extends AppCompatActivity {
    private Button main_BTN_newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_BTN_newGame = findViewById(R.id.main_BTN_newGame);

        main_BTN_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGame();
            }
        });
    }

    private void openNewGame() {
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
        closeActivity();
    }

    private void closeActivity() {
        finish();
    }
}