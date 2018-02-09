package com.august.poker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class TableActivity extends AppCompatActivity {

    private activity_table_layout layout;
    private Deck deck;
    private Player player1, player2;
    private boolean multiplayer;
    private int turn, tradeRound;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new activity_table_layout(this);
        setContentView(layout);

        deck = new Deck();
        player1 = new Player("Kalle", 100, deck.dealCards(5));
        player2 = new Player("Sven", 100, deck.dealCards(5));

        tradeRound = 1;

        if (getIntent().getStringExtra("PLAYERS").equals("1")) {
            layout.getHand(1).turnCards();
            multiplayer = false;
            turn = 2;
        } else {
            multiplayer = true;
            turn = 1;
        }

        setup();

        //Ändra touch till move på chips

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                if (layout.isTradeTime())
                    tradeTouch(x, y);
                else if (layout.isBetTime())
                    betTouch(x, y);

                return false;
            }
        });
    }
    private void setup() {
        updateHand(player1, 1);
        updateHand(player2, 2);
        layout.updateName(player1.getName(),1);
        layout.updateName(player2.getName(), 2);

        updateChipAmounts(player1, 1);
        updateChipAmounts(player2, 2);
        layout.updateWinner(getWinner());
    }

    private void updateChipAmounts(Player player, int handNr) {
        int[] amounts = new int[3];
        amounts[0] = player.get1Chips();
        amounts[1] = player.get5Chips();
        amounts[2] = player.get25Chips();
        layout.updateChipsAmounts(amounts, handNr);
    }

    private String[] getSuits(Hand hand) {
        String[] suits = new String[5];
        for (int i=0;i<5;i++) {
            suits[i] = hand.getCard(i).getSuit();
        }
        return suits;
    }

    private String[] getAbbrevs(Hand hand) {
        String[] abbrevs = new String[5];
        for (int i=0;i<5;i++) {
            abbrevs[i] = Card.abbreviations[hand.getCard(i).getRank()];
        }
        return abbrevs;
    }

    private String getWinner() {
        if (player1.getHand().equalTo(player2.getHand()))
            return "It's a draw.";
        else if (player1.getHand().betterThan(player2.getHand()))
            return "The upper hand is winning.";
        else
            return "The lower hand is winning.";
    }

    private void updateHand(Player player, int handNr) {
        layout.updateHand(getSuits(player.getHand()), getAbbrevs(player.getHand()),"You've got "+player.getHand().getHandName(), handNr);
    }

    private void tradeCards(Hand hand, int handNr) {
        hand.tradeCards(layout.getPickedCards(layout.getHand(handNr)), deck.dealCards(layout.getPickedCards(layout.getHand(handNr)).length));
    }

    private void tradeTouch(float x, float y) {
        if (layout.getTouchedCard(x,y,layout.getHand(turn))!=null)
            layout.pickCard(layout.getTouchedCard(x,y,layout.getHand(turn)));
        else if (layout.getTouchedCard(x,y,layout.getHand(turn))==null && layout.touchedCorrectHalf(turn, y)) {
            tradeCards(getPlayer(turn).getHand(), turn);
            updateHand(getPlayer(turn), turn);
            layout.setAllCardsUnpicked(layout.getHand(turn));

            if (turn==2)
                layout.setBetTime();
            if (multiplayer)
                turn = getOtherTurn();
        }
    }

    private void betTouch(float x, float y) {
        if (layout.getTouchedChip(x, y, layout.getStack(turn))!=null) {
            layout.throwChip(layout.getTouchedChip(x, y, layout.getStack(turn)), turn);
            getPlayer(turn).addToBet(layout.getTouchedChip(x, y, layout.getStack(turn)).getValue());
            getPlayer(turn).playChip(layout.getTouchedChip(x, y, layout.getStack(turn)).getValue());
            updateChipAmounts(getPlayer(turn), turn);
            //  layout.updateWinner(""+player2.getChips());
        } else if (layout.getTouchedChip(x, y, layout.getBet(turn))!=null) {
            layout.updatePot(turn);
            layout.resetBet(turn);
            getPlayer(turn).placeBet();
            getPlayer(turn).resetBet();
            layout.invalidate();

            if (turn==2)
                layout.setTradeTime();
            if (multiplayer)
                turn = getOtherTurn();
        }
    }

    private Player getPlayer(int nr) {
        return nr==1? player1:player2;
    }
    private int getOtherTurn() {
        return turn==1? 2:1;
    }

}
