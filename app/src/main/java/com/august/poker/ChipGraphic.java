package com.august.poker;

public class ChipGraphic {
    private float x, y, radius;
    private int value;
    final static float RADIUS = 0.05f;

    public ChipGraphic(float x, float y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getRadius() {
        return radius;
    }
    public int getValue() {
        return value;
    }
}
