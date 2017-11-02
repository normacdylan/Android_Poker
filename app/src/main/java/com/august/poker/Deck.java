package com.august.poker;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;
    private Random r;

    public Deck() {
        cards = new ArrayList<>();
        r = new Random();
        fillDeck();
    }

    private void fillDeck() {
        for (int i=0;i<4;i++) {
            for (int j=0;j<13;j++)
                cards.add(new Card(i,j));
        }
    }

    public int getNrOfCards() {
        return cards.size();
    }

    public Card dealCard() {
        int random = r.nextInt(cards.size());
        Card card = cards.get(random);
        cards.remove(random);
        return card;
    }

    public Card[] dealCards(int amount) {
        Card[] dealtCards = new Card[amount];
        for (int i=0;i<amount;i++) {
            dealtCards[i] = dealCard();
        }
        return dealtCards;
    }

}

