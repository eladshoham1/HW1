package com.example.homework.objects;

import com.example.homework.utils.Constants;

import java.util.Random;

public class GameManagement {
    private CardsDeck cards;
    private Player playerA;
    private Player playerB;
    private int countRounds;

    public GameManagement() {}

    public GameManagement(CardsDeck cards, Player playerA, Player playerB) {
        setCards(cards);
        setPlayerA(playerA);
        setPlayerB(playerB);
        this.countRounds = 0;
    }

    public CardsDeck getCards() {
        return cards;
    }

    public void setCards(CardsDeck cards) {
        this.cards = cards;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }

    public int getCountRounds() {
        return countRounds;
    }

    public void setCountRounds(int countRounds) {
        this.countRounds = countRounds;
    }

    public int nextRound() {
        Random rnd = new Random();
        int index;

        if (gameOver())
            return Constants.GAME_OVER;

        index = rnd.nextInt(cards.getNumberOfCards());
        playerA.setCard(cards.getCard(index));
        cards.removeCard(index);

        index = rnd.nextInt(cards.getNumberOfCards());
        playerB.setCard(cards.getCard(index));
        cards.removeCard(index);

        this.countRounds++;
        return setScore();
    }

    public int setScore() {
        int result;
        int cardANumber = playerA.getCard().getNumber();
        int cardBNumber = playerB.getCard().getNumber();

        if (cardANumber > cardBNumber) {
            playerA.increaseScore();
            result = Constants.PLAYER_A_WIN;
        } else if (cardANumber < cardBNumber) {
            playerB.increaseScore();
            result = Constants.PLAYER_B_WIN;
        } else {
            result = Constants.DRAW;
        }

        return gameOver() ? Constants.GAME_OVER : result;
    }

    public boolean gameOver() {
        return countRounds == Constants.HALF_CARDS_DECK;
    }
}
