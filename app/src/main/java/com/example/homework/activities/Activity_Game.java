package com.example.homework.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homework.R;
import com.example.homework.objects.CardsDeck;
import com.example.homework.objects.GameManagement;
import com.example.homework.objects.Player;
import com.example.homework.utils.Constants;
import com.example.homework.utils.MySP;
import com.example.homework.utils.MySignal;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Activity_Game extends Activity_Base {
    private ProgressBar game_PRG_gameProgress;
    private ImageView game_IMG_playerA;
    private ImageView game_IMG_playerB;
    private TextView game_LBL_playerA;
    private TextView game_LBL_playerB;
    private TextView game_LBL_scorePlayerA;
    private TextView game_LBL_scorePlayerB;
    private ImageView game_IMG_cardA;
    private ImageView game_IMG_cardB;
    private ImageView game_IMG_play;
    private TextView game_LBL_roundNumber;

    private ScheduledFuture<?> scheduledFuture;

    private GameManagement game;
    private boolean gameStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        isDoubleBackPressToClose = true;

        findViews();
        initViews();
        initiateGame();
    }

    @Override
    protected void onStart() {
        super.onStart();
        start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    private void findViews() {
        game_PRG_gameProgress = findViewById(R.id.game_PRG_gameProgress);
        game_IMG_playerA = findViewById(R.id.game_IMG_playerA);
        game_IMG_playerB = findViewById(R.id.game_IMG_playerB);
        game_LBL_playerA = findViewById(R.id.game_LBL_playerA);
        game_LBL_playerB = findViewById(R.id.game_LBL_playerB);
        game_LBL_scorePlayerA = findViewById(R.id.game_LBL_scorePlayerA);
        game_LBL_scorePlayerB = findViewById(R.id.game_LBL_scorePlayerB);
        game_IMG_cardA = findViewById(R.id.game_IMG_cardA);
        game_IMG_cardB = findViewById(R.id.game_IMG_cardB);
        game_IMG_play = findViewById(R.id.game_IMG_play);
        game_LBL_roundNumber = findViewById(R.id.game_LBL_roundNumber);
    }

    private void initViews() {
        updateImage(R.drawable.player_messi, game_IMG_playerA);
        updateImage(R.drawable.player_ronaldo, game_IMG_playerB);

        game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
    }

    public void updateImage(int id, ImageView image) {
        Glide
                .with(this)
                .load(id)
                .into(image);
    }

    private void initiateGame() {
        MySignal.getInstance().playSound(R.raw.snd_game_start);
        String playerAName = MySP.getInstance().getString(MySP.KEYS.PLAYER_A_NAME, MySP.KEYS.PLAYER_A_DEFAULT_NAME);
        String playerBName = MySP.getInstance().getString(MySP.KEYS.PLAYER_B_NAME, MySP.KEYS.PLAYER_B_DEFAULT_NAME);

        CardsDeck cards = new CardsDeck(R.drawable.class.getFields());
        Player playerA = new Player(playerAName);
        Player playerB = new Player(playerBName);
        game = new GameManagement(cards, playerA, playerB);

        game_LBL_playerA.setText(game.getPlayerA().getName());
        game_LBL_playerB.setText(game.getPlayerB().getName());

        gameStart = false;
    }

    private void startNewGame() {
        game_IMG_play.setVisibility(View.INVISIBLE);
        game_LBL_roundNumber.setVisibility(View.VISIBLE);
        gameStart = true;
        start();
    }

    private void nextRound() {
        MySignal.getInstance().playSound(R.raw.snd_flip_cards);
        clearScoreColor();

        switch (game.nextRound()) {
            case Constants.GAME_OVER:
                gameOver();
                break;
            case Constants.PLAYER_A_WIN:
                playerAWin();
                break;
            case Constants.PLAYER_B_WIN:
                playerBWin();
                break;
        }

        setNextRound();
    }

    private void clearScoreColor() {
        game_LBL_scorePlayerA.setTextColor(Color.WHITE);
        game_LBL_scorePlayerB.setTextColor(Color.WHITE);
    }

    private void setNextRound() {
        game_PRG_gameProgress.setProgress(game.getRound());
        game_LBL_roundNumber.setText(Constants.ROUND + game.getRound());
        game_IMG_cardA.setImageResource(getCardId(game.getPlayerA().getCard().getName()));
        game_IMG_cardB.setImageResource(getCardId(game.getPlayerB().getCard().getName()));
    }

    private int getCardId(String cardName) {
        return getResources().getIdentifier(cardName, Constants.DRAWABLE, getPackageName());
    }

    private void playerAWin() {
        game_LBL_scorePlayerA.setText("" + game.getPlayerA().getScore());
        game_LBL_scorePlayerA.setTextColor(Color.GREEN);
    }

    private void playerBWin() {
        game_LBL_scorePlayerB.setText("" + game.getPlayerB().getScore());
        game_LBL_scorePlayerB.setTextColor(Color.GREEN);
    }

    private void gameOver() {
        Player playerA = game.getPlayerA();
        Player playerB = game.getPlayerB();

        if (playerA.getScore() > playerB.getScore())
            openResultActivity(playerA.getName(), playerA.getScore());
        else if (playerA.getScore() < playerB.getScore())
            openResultActivity(playerB.getName(), playerB.getScore());
        else
            openResultActivity(Constants.DRAW_MESSAGE, playerA.getScore());
    }

    private void openResultActivity(String winnerName, int winnerResult) {
        Intent myIntent = new Intent(this, Activity_Result.class);
        myIntent.putExtra(Constants.EXTRA_KEY_WINNER_NAME, winnerName);
        myIntent.putExtra(Constants.EXTRA_KEY_WINNER_RESULT, winnerResult);
        myIntent.putExtra(Constants.EXTRA_KEY_LATITUDE, getIntent().getDoubleExtra(Constants.EXTRA_KEY_LATITUDE, 0));
        myIntent.putExtra(Constants.EXTRA_KEY_LONGITUDE, getIntent().getDoubleExtra(Constants.EXTRA_KEY_LONGITUDE, 0));

        startActivity(myIntent);
        closeActivity();
    }

    public void start() {
        if (gameStart) {
            scheduledFuture = new ScheduledThreadPoolExecutor(5).scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nextRound();
                        }
                    });
                }
            }, 0, Constants.TIME_INTERVAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stop() {
        if (gameStart)
            scheduledFuture.cancel(false);
    }
}