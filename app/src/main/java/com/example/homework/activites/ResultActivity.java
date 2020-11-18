package com.example.homework.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework.R;

public class ResultActivity extends AppCompatActivity {
    public static final String EXTRA_KEY_WINNER_NAME = "EXTRA_KEY_WINNER_NAME";
    public static final String EXTRA_KEY_WINNER_RESULT = "EXTRA_KEY_WINNER_RESULT";
    public static final String WIN = " Win";
    public static final String DRAW = "Draw";
    public static final String SCORE = "Score: ";

    private TextView result_LBL_winnerStatus;
    private TextView result_LBL_winnerScore;
    private ImageView result_IMG_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result_LBL_winnerStatus = findViewById(R.id.result_LBL_winnerStatus);
        result_LBL_winnerScore = findViewById(R.id.result_LBL_winnerScore);
        result_IMG_return = findViewById(R.id.result_IMG_return);

        showResults();

        result_IMG_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
    }

    private void showResults() {
        String winnerStatusFromPreviousActivity = getIntent().getStringExtra(EXTRA_KEY_WINNER_NAME);
        int scoreFromPreviousActivity = getIntent().getIntExtra(EXTRA_KEY_WINNER_RESULT, GameActivity.ZERO);

        if (!(winnerStatusFromPreviousActivity.equals(DRAW)))
            result_LBL_winnerStatus.setText(winnerStatusFromPreviousActivity + WIN);
        else
            result_LBL_winnerStatus.setText(DRAW);

        result_LBL_winnerScore.setText(SCORE + scoreFromPreviousActivity);
    }

    private void openGameActivity() {
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
        closeActivity();
    }

    private void closeActivity() {
        finish();
    }
}