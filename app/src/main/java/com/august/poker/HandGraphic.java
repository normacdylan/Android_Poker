package com.august.poker;

import android.graphics.Color;

public class HandGraphic {
    private CardGraphic[] cardGraphics;
    private float x, y, margin;
    private boolean faceUp;

    public HandGraphic(float x, float y, float margin, boolean faceUp) {
        this.x = x;
        this.y = y;
        this.margin = margin;
        this.faceUp = faceUp;
        cardGraphics = new CardGraphic[5];
        initializeCards();
    }

    private void initializeCards() {
        for (int i=0;i<cardGraphics.length;i++) {
            cardGraphics[i] = new CardGraphic(R.drawable.clubs, Color.BLACK,"A",0,0);
        }
    }

    public void updatePostition(float x, float y, float margin) {
        this.x = x;
        this.y = y;
        this.margin = margin;
    }

    public void turnCards() {faceUp = !faceUp;}
    public int getNrOfCards() {return cardGraphics.length;}
    public CardGraphic getCard(int index) {return cardGraphics[index];}
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getMargin() {
        return margin;
    }
    public boolean isFaceUp() {
        return faceUp;
    }
}
