package com.august.poker;

import java.util.ArrayList;

public class Hand {
    public enum Hands {
        NOTHING,PAIR,TWOPAIRS,TRIPS,STRAIGHT,FLUSH,FULLHOUSE,QUADS,STRAIGHTFLUSH
    }

    final static String[] handNames = {"nothing", "a pair", "two pairs", "trips", "a straight", "a flush", "a full house",
                                    "quads", "a straight flush"};

    private ArrayList<Card> cards;

    public Hand(Card[] dealtCards) {
        cards = new ArrayList<>();
        fillHand(dealtCards);
    }

    //Metod för att visa shufflad hand (som håller koll på mappingen)

    private void fillHand(Card[] dealtCards) {
        if (getNrOfCards()==0) {
            cards.add(dealtCards[0]);
            for (int i=1;i<dealtCards.length;i++) {
                for (int j=0;j<cards.size();j++) {
                    if (dealtCards[i].getRank()<cards.get(j).getRank()) {
                        cards.add(j,dealtCards[i]);
                        break;
                    }
                }
                if (dealtCards[i].getRank()>=cards.get(cards.size()-1).getRank())
                    cards.add(dealtCards[i]);
            }
        } else {
            for (int i=0;i<dealtCards.length;i++) {
                for (int j = 0; j < cards.size(); j++) {
                    if (dealtCards[i].getRank()<cards.get(j).getRank()) {
                        cards.add(j, dealtCards[i]);
                        break;
                    }
                }
                if (dealtCards[i].getRank()>=cards.get(cards.size()-1).getRank())
                    cards.add(dealtCards[i]);
            }
        }
    }

    public String[] getCards() {
        String[] result = new String[cards.size()];
        for (int i=0;i<cards.size();i++)
            result[i] = cards.get(i).getCard();
        return result;
    }

    public String getCardsText() {
        String result = "";
        String[] list = getCards();
        for (int i=0;i<list.length;i++)
            result += list[i]+'\n';
        return result;
    }

    public Card getCard(int place) {
        return cards.get(place);
    }

    public int getNrOfCards() {
        return cards.size();
    }

    public void receiveCard(Card card) {
        if (cards.size()==0)
            cards.add(card);
        else {
            for (int j = 0; j < cards.size(); j++) {
                if (card.getRank() < cards.get(j).getRank()) {
                    cards.add(j, card);
                    break;
                }
            }
            if (card.getRank() > cards.get(cards.size() - 1).getRank())
                cards.add(card);
        }
    }

    private boolean hasFlush() {
        for (int i=1;i<cards.size();i++) {
            if (!cards.get(0).getSuit().equals(cards.get(i).getSuit()))
                return false;
        }
        return true;
    }

    private boolean hasStraight() {
        //Kollar först om handen har stege med Ess Lågt
        if (cards.get(0).getRank()==0 && cards.get(1).getRank()==1
                && cards.get(2).getRank()==2 && cards.get(3).getRank()==3
                && cards.get(4).getRank()==12)
            return true;

        for (int i=0;i<cards.size()-1;i++) {
            if (cards.get(i).getRank()+1 != cards.get(i+1).getRank())
                return false;
        }
        return true;
    }

    private boolean hasQuads() {
        return cards.get(0).getRank()==cards.get(3).getRank() || cards.get(1).getRank()==cards.get(4).getRank();
    }

    private boolean hasFullHouse() {
        if (cards.get(0).getRank()==cards.get(1).getRank() && cards.get(2).getRank()==cards.get(4).getRank())
            return true;
        else if (cards.get(0).getRank()==cards.get(2).getRank() && cards.get(3).getRank()==cards.get(4).getRank())
            return true;
        return false;
    }

    private boolean hasTrips() {
        for (int i=0;i<3;i++) {
            if (cards.get(i).getRank()==cards.get(i+2).getRank())
                return true;
        }
        return false;
    }

    private boolean hasTwoPairs() {
        for (int i=0;i<3;i++) {
            if (cards.get(i).getRank()==cards.get(i+1).getRank()) {
                for (int j=i+2;j<4;j++) {
                    if (cards.get(j).getRank()==cards.get(j+1).getRank())
                        return true;
                }
            }
        }
        return false;
    }

    private boolean hasPair() {
        for (int i=0;i<4;i++) {
            if (cards.get(i).getRank()==cards.get(i+1).getRank())
                return true;
        }
        return false;
    }

    public int getScore() {
        if (hasFlush() && hasStraight())
            return 8;
        else if (hasQuads())
            return 7;
        else if (hasFullHouse())
            return 6;
        else if (hasFlush() && !hasStraight())
            return 5;
        else if (hasStraight() && !hasFlush())
            return 4;
        else if (hasTrips() && !hasFullHouse() && !hasQuads())
            return 3;
        else if (hasTwoPairs() && !hasFullHouse() && !hasTrips())
            return 2;
        else if (hasPair() && !hasQuads() && !hasFullHouse() && !hasTrips() && !hasTwoPairs())
            return 1;
        else
            return 0;
    }

    public String getHand() {
        return Hands.values()[getScore()].name();
    }

    public String getHandName() {return handNames[getScore()];}

    private int[] getKickers() {
        int[] kickers = new int[5];
        switch (getScore()) {
            case 8:
                if (cards.get(4).getRank()==13 && cards.get(3).getRank()==3)
                    kickers[0] = cards.get(3).getRank();
                else
                    kickers[0] = cards.get(4).getRank();
                return kickers;
            case 7:
                if (cards.get(0).getRank()==cards.get(3).getRank()) {
                    kickers[0] = cards.get(0).getRank();
                    kickers[1] = cards.get(4).getRank();
                } else {
                    kickers[0] = cards.get(1).getRank();
                    kickers[1] = cards.get(0).getRank();
                }
                return kickers;
            case 6:
                if (cards.get(0).getRank()==cards.get(2).getRank()) {
                    kickers[0] = cards.get(0).getRank();
                    kickers[1] = cards.get(4).getRank();
                } else {
                    kickers[0] = cards.get(4).getRank();
                    kickers[1] = cards.get(0).getRank();
                }
                return kickers;
            case 5:
                for (int i=4;i>-1;i--) {
                    kickers[4-i] = cards.get(i).getRank();
                }
                return kickers;
            case 4:
                if (cards.get(4).getRank()==13 && cards.get(3).getRank()==3)
                    kickers[0] = cards.get(3).getRank();
                else
                    kickers[0] = cards.get(4).getRank();
                return kickers;
            case 3:
                if (cards.get(0).getRank()==cards.get(2).getRank()) {
                    kickers[0] = cards.get(0).getRank();
                    kickers[1] = cards.get(4).getRank();
                    kickers[2] = cards.get(3).getRank();
                } else if (cards.get(1).getRank()==cards.get(3).getRank()) {
                    kickers[0] = cards.get(2).getRank();
                    kickers[1] = cards.get(4).getRank();
                    kickers[2] = cards.get(0).getRank();
                } else {
                    kickers[0] = cards.get(4).getRank();
                    kickers[1] = cards.get(1).getRank();
                    kickers[2] = cards.get(0).getRank();
                }
                return kickers;
            case 2:
                if (cards.get(4).getRank()==cards.get(3).getRank() && cards.get(2).getRank()==cards.get(1).getRank()) {
                    kickers[0] = cards.get(4).getRank();
                    kickers[1] = cards.get(2).getRank();
                    kickers[2] = cards.get(0).getRank();
                } else if (cards.get(3).getRank()==cards.get(2).getRank() && cards.get(1).getRank()==cards.get(0).getRank()) {
                    kickers[0] = cards.get(3).getRank();
                    kickers[1] = cards.get(1).getRank();
                    kickers[2] = cards.get(4).getRank();
                } else {
                    kickers[0] = cards.get(4).getRank();
                    kickers[1] = cards.get(1).getRank();
                    kickers[2] = cards.get(2).getRank();
                }
                return kickers;
            case 1:
                if (cards.get(4).getRank()==cards.get(3).getRank()) {
                    kickers[0] = cards.get(4).getRank();
                    kickers[1] = cards.get(2).getRank();
                    kickers[2] = cards.get(1).getRank();
                    kickers[3] = cards.get(0).getRank();
                } else if (cards.get(3).getRank()==cards.get(2).getRank()) {
                    kickers[0] = cards.get(3).getRank();
                    kickers[1] = cards.get(4).getRank();
                    kickers[2] = cards.get(1).getRank();
                    kickers[3] = cards.get(0).getRank();
                } else if (cards.get(2).getRank()==cards.get(1).getRank()) {
                    kickers[0] = cards.get(2).getRank();
                    kickers[1] = cards.get(4).getRank();
                    kickers[2] = cards.get(3).getRank();
                    kickers[3] = cards.get(0).getRank();
                } else {
                    kickers[0] = cards.get(1).getRank();
                    kickers[1] = cards.get(4).getRank();
                    kickers[2] = cards.get(3).getRank();
                    kickers[3] = cards.get(2).getRank();
                }
                return kickers;
            case 0:
                for (int i=4;i>-1;i--) {
                    kickers[4-i] = cards.get(i).getRank();
                }
                return kickers;
            default:
                return kickers;
        }
    }

    private boolean betterKicker(Hand hand) {
        int[] kickers = getKickers();
        int[] oppKickers = hand.getKickers();

        for (int i=0;i<5;i++) {
            if (kickers[i]>oppKickers[i])
                return true;
            else if (kickers[i]<oppKickers[i])
                return false;
        }
        return false;
    }

    public boolean equalTo(Hand hand) {
        if (getScore()!=hand.getScore())
            return false;
        else {
            for (int i=0;i<5;i++) {
                if (cards.get(i).getRank()!=hand.getCard(i).getRank())
                    return false;
            }
        }
        return true;
    }

    public boolean betterThan(Hand hand) {
        if (getScore()==hand.getScore()){
            return betterKicker(hand);
        } else
            return getScore()>hand.getScore();
    }

    public void tradeCards(int[] chosenCards, Card[] dealtCards) {
        if (chosenCards.length>0) {
            for (int i=chosenCards.length-1;i>-1;i--)
                cards.remove(chosenCards[i]);
            fillHand(dealtCards);
        }

    }
}
