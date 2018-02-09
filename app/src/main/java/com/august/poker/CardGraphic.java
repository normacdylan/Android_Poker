package com.august.poker;

import android.graphics.Bitmap;

public class CardGraphic {
    private int pic, color;
    public Bitmap bitmap;
    private String abbrev;
    final static float WIDTH = 0.125f;
    final static float HEIGHT = 0.1f;
    private float x, y;
    private boolean picked;

    public CardGraphic(int pic, int color, String abbrev, int x, int y) {
        this.pic = pic;
        this.color = color;
        this.abbrev = abbrev;
        this.x = x;
        this.y = y;
        picked = false;
    }

    public void updateImage(int pic, int color, String abbrev) {
        this.pic = pic;
        this.color = color;
        this.abbrev = abbrev;
    }

    public void updatePosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void pick() {
        picked = true;
    }
    public void unpick() {picked = false;}

    public boolean isPicked() {
        return picked;
    }
    public int getPic() {
        return pic;
    }
    public int getColor() {
        return color;
    }
    public static float getWIDTH() {
        return WIDTH;
    }
    public static float getHEIGHT() {
        return HEIGHT;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public String getAbbrev() {
        return abbrev;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
