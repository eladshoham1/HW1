package com.example.homework.objects;

import com.example.homework.utils.Constants;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CardsDeck {
    private ArrayList<Card> cards;

    public CardsDeck() { }

    public CardsDeck(Field[] fields) {
        this.cards = new ArrayList<>();
        setCards(fields);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(int index) {
        cards.remove(index);
    }

    public void setCards(Field[] fields) {
        String cardName;
        int cardNumber;

        for (Field field : fields) {
            cardName = field.getName();

            if (cardName.contains(Constants.POKER)) {
                cardNumber = getCardNumberByName(cardName);

                if (cardNumber != Constants.ZERO)
                    addCard(new Card(cardName, cardNumber));
            }
        }
    }

    public int getCardNumberByName(String cardName) {
        cardName = cardName.substring(cardName.length() - Constants.NUMBER_IN_STRING_INDEX);
        if (cardName.matches(Constants.REGEX))
            return Integer.parseInt(cardName.substring(cardName.length() - Constants.NUMBER_IN_STRING_INDEX));

        return Constants.ZERO;
    }
}
