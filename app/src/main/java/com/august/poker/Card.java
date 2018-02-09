package com.august.poker;

public class Card {
    public enum Suit {
        HEARTS,DIAMONDS,CLUBS,SPADES
    }
    public enum Rank {
        TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING,ACE
    }

    final static String[] abbreviations = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

    private Suit suit;
    private Rank rank;

    public Card(int suit, int rank) {
        this.suit = Suit.values()[suit];
        this.rank = Rank.values()[rank];
    }

    public String getCard() {
        return rank.name()+" of "+suit.name();
    }

    public String getSuit() {
        return suit.name();
    }

    public int getRank() {
        return rank.ordinal();
    }

}
