package com.example.homework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homework.R;
import com.example.homework.objects.Record;
import com.example.homework.objects.TopTen;
import com.example.homework.utils.Constants;
import com.example.homework.utils.MySP;
import com.example.homework.utils.MySignal;

import com.google.gson.Gson;

public class Activity_Result extends Activity_Base {
    private TextView result_LBL_winnerStatus;
    private TextView result_LBL_score;
    private Button result_BTN_menu;
    private Button result_BTN_topTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViews();
        initViews();
        showResults();
    }

    private void findViews() {
        result_LBL_winnerStatus = findViewById(R.id.result_LBL_winnerStatus);
        result_LBL_score = findViewById(R.id.result_LBL_score);
        result_BTN_menu = findViewById(R.id.result_BTN_menu);
        result_BTN_topTen = findViewById(R.id.result_BTN_topTen);
    }

    private void initViews() {
        result_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(Activity_Main.class);
            }
        });

        result_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(Activity_TopTen.class);
            }
        });
    }

    private void showResults() {
        MySignal.getInstance().playSound(R.raw.snd_game_over);
        String playerName = getIntent().getStringExtra(Constants.EXTRA_KEY_WINNER_NAME);
        int playerScore = getIntent().getIntExtra(Constants.EXTRA_KEY_WINNER_RESULT, Constants.ZERO);
        double latitude = getIntent().getDoubleExtra(Constants.EXTRA_KEY_LATITUDE, Constants.ZERO);
        double longitude = getIntent().getDoubleExtra(Constants.EXTRA_KEY_LONGITUDE, Constants.ZERO);
        Record record = new Record(playerName, System.currentTimeMillis(), playerScore, latitude, longitude);

        int rank;

        if (!(playerName.equals(Constants.DRAW_MESSAGE))) {
            result_LBL_winnerStatus.setText(playerName + " Win");

            rank = checkRecord(record);
            if (rank != Constants.NOT_IN_TOP_TEN)
                MySignal.getInstance().toast("New Record, Rank " + rank + " in Top 10");
        } else
            result_LBL_winnerStatus.setText(Constants.DRAW_MESSAGE );

        result_LBL_score.setText("Score: " + playerScore);
    }

    private int checkRecord(Record record) {
        int rank;

        String topTenString = MySP.getInstance().getString(MySP.KEYS.TOP_TEN, "");
        TopTen topTen = topTenString.isEmpty() ? new TopTen() : new Gson().fromJson(topTenString, TopTen.class);

        rank = topTen.addNewRecord(record);
        MySP.getInstance().putString(MySP.KEYS.TOP_TEN, new Gson().toJson(topTen));

        return rank;
    }

    private void openNewActivity(Class activity) {
        Intent myIntent = new Intent(this, activity);
        startActivity(myIntent);
        closeActivity();
    }
}