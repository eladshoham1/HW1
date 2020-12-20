package com.example.homework.objects;

import com.example.homework.utils.Constants;

public class Player {
    private String name;
    private int score;
    private Card card;

    public Player() {}

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.card = new Card(Constants.UPSIDE_DOWN_CARD, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void increaseScore() {
        this.score++;
    }
}
