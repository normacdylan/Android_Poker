package com.august.poker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TableActivity extends AppCompatActivity {

    activity_table_layout layout;
    Deck deck;
    Hand hand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new activity_table_layout(this);
        setContentView(layout);

        deck = new Deck();
        hand = new Hand(deck.dealCards(5));

        layout.updateCards(getSuits(),getAbbrevs(),hand.getHand());
    }

    private String[] getSuits() {
        String[] suits = new String[5];
        for (int i=0;i<5;i++) {
            suits[i] = hand.getCard(i).getSuit();
        }
        return suits;
    }

    private String[] getAbbrevs() {
        String[] abbrevs = new String[5];
        for (int i=0;i<5;i++) {
            abbrevs[i] = Card.abbreviations[hand.getCard(i).getRank()];
        }
        return abbrevs;
    }

}
