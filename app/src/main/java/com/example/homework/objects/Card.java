package com.example.homework.objects;

public class Card {
    int imageId = 0;
    int cardNumber = 0;

    public Card() {};

    public Card(int imageId, int cardNumber) {
        this.imageId = imageId;
        this.cardNumber = cardNumber;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }
}
