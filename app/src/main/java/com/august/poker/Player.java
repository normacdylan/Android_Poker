package com.august.poker;

public class Player {
    private int chips;
    private Hand hand;
    private int bet, chips1, chips5, chips25;
    private String name;
    private boolean hasFolded;

    public Player(String name, int chips, Card[] dealtCards) {
        this.name = name;
        this.chips = chips;
        bet = 0;
        hasFolded = false;
        hand = new Hand(dealtCards);
        setInitialChips();
    }

    public void addToChips(int[] amounts) {
        chips1 += amounts[0];
        chips5 += amounts[1];
        chips25 += amounts[2];
    }

    public void addToBet(int bet) {
        this.bet += bet;
    }

    public void placeBet() {
        chips -= bet;
    }

    public void resetBet() {bet = 0;}

    public void fold() {
        hasFolded = true;
    }

    public boolean isBroke() {return chips<1;}

    private void setInitialChips() {
        chips25 = chips/35;
        chips5 = (chips-(get25Chips()*25))/7;
        chips1 = chips-(chips25*25)-(chips5*5);
    }

    public int get1Chips() {
        return chips1;
    }
    public int get5Chips() {
        return chips5;
    }
    public int get25Chips() {
        return chips25;
    }

    public void playChip(int value) {
        switch (value) {
            case 1:
                chips1--;
                break;
            case 5:
                chips5--;
                break;
            case 25:
                chips25--;
                break;
            default:
        }
    }

    public boolean hasFolded() {return hasFolded;}
    public int getChips() {
        return chips;
    }
    public Hand getHand() {
        return hand;
    }
    public int getBet() {
        return bet;
    }
    public String getName() {
        return name;
    }
}

