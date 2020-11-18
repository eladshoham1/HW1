package com.example.homework.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework.R;
import com.example.homework.objects.Card;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    public static final String POKER = "poker";
    public static final String DRAWABLE = "drawable";
    public static final String SCORE_PLAYER_A = "SCORE_PLAYER_A";
    public static final String SCORE_PLAYER_B = "SCORE_PLAYER_B";
    public static final int ZERO = 0;
    public static final int NUMBER_IN_STRING_INDEX = 2;

    private EditText game_EDT_playerA;
    private EditText game_EDT_playerB;
    private TextView game_LBL_scorePlayerA;
    private TextView game_LBL_scorePlayerB;
    private ImageView game_IMG_cardA;
    private ImageView game_IMG_cardB;
    private ImageView game_BTN_play;
    private ArrayList<Card> cards;
    private MediaPlayer mp;
    private int scorePlayerA;
    private int scorePlayerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playSound(R.raw.snd_game_start);

        cards = new ArrayList<>();
        game_EDT_playerA = findViewById(R.id.game_EDT_playerA);
        game_EDT_playerB = findViewById(R.id.game_EDT_playerB);
        game_LBL_scorePlayerA = findViewById(R.id.game_LBL_scorePlayerA);
        game_LBL_scorePlayerB = findViewById(R.id.game_LBL_scorePlayerB);
        game_IMG_cardA = findViewById(R.id.game_IMG_cardA);
        game_IMG_cardB = findViewById(R.id.game_IMG_cardB);
        game_BTN_play = findViewById(R.id.game_BTN_play);
        scorePlayerA = ZERO;
        scorePlayerB = ZERO;
        setAllCards();

        game_BTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRound();
            }
        });

        try {
            if (savedInstanceState != null) {
                scorePlayerA = savedInstanceState.getInt(SCORE_PLAYER_A);
                scorePlayerB = savedInstanceState.getInt(SCORE_PLAYER_A);
                game_LBL_scorePlayerA.setText("" + scorePlayerA);
                game_LBL_scorePlayerB.setText("" + scorePlayerB);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_PLAYER_A, scorePlayerA);
        outState.putInt(SCORE_PLAYER_B, scorePlayerB);
    }

    /**
     * function to set all the cards in ArrayList
     */
    private void setAllCards() {
        int cardId, cardNumber;
        Field[] drawables = R.drawable.class.getFields();

        for (Field field : drawables) {
            if (field.getName().contains(POKER)) {
                cardId = getResources().getIdentifier(field.getName(), DRAWABLE, this.getPackageName());
                cardNumber = getCardNumberById(cardId);

                if (cardNumber != ZERO)
                    cards.add(new Card(cardId, cardNumber));
            }
        }
    }

    /**
     * function to get the number of card by id
     * @param cardId the card id
     * @return the number of the card
     */
    private int getCardNumberById(int cardId) {
        int cardNumber = ZERO;
        String cardName = getResources().getResourceEntryName(cardId);
        cardName = cardName.substring(cardName.length() - NUMBER_IN_STRING_INDEX);

        try {
            cardNumber = Integer.parseInt(cardName);
        } catch (Exception ex) { }

        return cardNumber;
    }

    /**
     * function to make a new round
     */
    private void newRound() {
        Random rnd = new Random();
        Card cardA, cardB;
        int index;

        playSound(R.raw.snd_flip_cards);

        index = rnd.nextInt(cards.size());
        cardA = cards.get(index);
        game_IMG_cardA.setImageResource(cardA.getImageId());
        cards.remove(cardA);

        index = rnd.nextInt(cards.size());
        cardB = cards.get(index);
        game_IMG_cardB.setImageResource(cardB.getImageId());
        cards.remove(cardB);

        setScoreAndCheckEnd(cardA, cardB);
    }

    /**
     * function to set players score by they cards
     * @param cardPlayerA the card of player A
     * @param cardPlayerB the card of player B
     */
    private void setScoreAndCheckEnd(Card cardPlayerA, Card cardPlayerB) {
        game_LBL_scorePlayerA.setTextColor(getResources().getColor(R.color.white));
        game_LBL_scorePlayerB.setTextColor(getResources().getColor(R.color.white));

        if (cardPlayerA.getCardNumber() > cardPlayerB.getCardNumber()) {
            game_LBL_scorePlayerA.setText("" + ++scorePlayerA);
            game_LBL_scorePlayerA.setTextColor(getResources().getColor(R.color.increaseScore));
        } else if (cardPlayerA.getCardNumber() < cardPlayerB.getCardNumber()) {
            game_LBL_scorePlayerB.setText("" + ++scorePlayerB);
            game_LBL_scorePlayerB.setTextColor(getResources().getColor(R.color.increaseScore));
        }

        if (cards.size() == ZERO)
            endGame();
    }

    /**
     * function that end the game and call to result activity to display the winner and score
     */
    private void endGame() {
        int scorePlayerA = Integer.parseInt(game_LBL_scorePlayerA.getText().toString());
        int scorePlayerB = Integer.parseInt(game_LBL_scorePlayerB.getText().toString());

        if (scorePlayerA > scorePlayerB)
            openResultActivity(game_EDT_playerA.getText().toString(), scorePlayerA);
        else if (scorePlayerA < scorePlayerB)
            openResultActivity(game_EDT_playerB.getText().toString(), scorePlayerB);
        else
            openResultActivity(ResultActivity.DRAW, scorePlayerA);
    }

    private void playSound(int rawSound) {
        if (mp != null) {
            try {
                mp.reset();
                mp.release();
            } catch (Exception ex) { }
        }

        mp = MediaPlayer.create(this, rawSound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });
        mp.start();
    }

    private void openResultActivity(String winnerName, int winnerResult) {
        Intent myIntent = new Intent(this, ResultActivity.class);
        myIntent.putExtra(ResultActivity.EXTRA_KEY_WINNER_NAME, winnerName);
        myIntent.putExtra(ResultActivity.EXTRA_KEY_WINNER_RESULT, winnerResult);
        startActivity(myIntent);
        closeActivity();
    }

    private void closeActivity() {
        finish();
    }
}