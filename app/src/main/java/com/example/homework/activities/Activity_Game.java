package com.example.homework.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.homework.R;
import com.example.homework.objects.CardsDeck;
import com.example.homework.objects.GameManagement;
import com.example.homework.objects.Player;
import com.example.homework.utils.Constants;
import com.example.homework.utils.MySP;
import com.example.homework.utils.MySignal;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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

    private FusedLocationProviderClient client;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        isDoubleBackPressToClose = true;

        getCurrentLocation();
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

        MySignal.getInstance().updateImage(R.drawable.player_messi,  game_IMG_playerA);
        MySignal.getInstance().updateImage(R.drawable.player_ronaldo,  game_IMG_playerB);
    }

    private void initViews() {
        game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
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
        game_LBL_scorePlayerA.setTextColor(getResources().getColor(R.color.white));
        game_LBL_scorePlayerB.setTextColor(getResources().getColor(R.color.white));
    }

    private void setNextRound() {
        game_PRG_gameProgress.setProgress(game.getCountRounds());
        game_LBL_roundNumber.setText(Constants.ROUND + game.getCountRounds());
        game_IMG_cardA.setImageResource(getCardId(game.getPlayerA().getCard().getName()));
        game_IMG_cardB.setImageResource(getCardId(game.getPlayerB().getCard().getName()));
    }

    private int getCardId(String cardName) {
        return getResources().getIdentifier(cardName, Constants.DRAWABLE, getPackageName());
    }

    private void playerAWin() {
        game_LBL_scorePlayerA.setText("" + game.getPlayerA().getScore());
        game_LBL_scorePlayerA.setTextColor(getResources().getColor(R.color.increaseScore));
    }

    private void playerBWin() {
        game_LBL_scorePlayerB.setText("" + game.getPlayerB().getScore());
        game_LBL_scorePlayerB.setTextColor(getResources().getColor(R.color.increaseScore));
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

    private void getCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(this);

        if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_CODE);

        if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            });
        }
    }

    private void openResultActivity(String winnerName, int winnerResult) {
        Intent myIntent = new Intent(this, Activity_Result.class);
        myIntent.putExtra(Constants.EXTRA_KEY_WINNER_NAME, winnerName);
        myIntent.putExtra(Constants.EXTRA_KEY_WINNER_RESULT, winnerResult);
        myIntent.putExtra(Constants.EXTRA_KEY_LATITUDE, latitude);
        myIntent.putExtra(Constants.EXTRA_KEY_LONGITUDE, longitude);
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
            }, Constants.ZERO, Constants.TIME_INTERVAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stop() {
        if (gameStart)
            scheduledFuture.cancel(false);
    }
}