package com.august.poker;

public class StackGraphic {
    private ChipGraphic[] chips;
    private float x, y, margin;
    private boolean showing;
    private int[] amounts;
    //Marginkonstant?

    public StackGraphic(float x, float y, float margin) {
        this.x = x;
        this.y = y;
        this.margin = margin;
        showing = false;
        amounts = new int[3];
        initiateChips();
    }

    private void initiateChips() {
        chips = new ChipGraphic[3];
        chips[0] = new ChipGraphic(x+ChipGraphic.RADIUS, y-amounts[0]*margin, 1);
        chips[1] = new ChipGraphic(x+3*ChipGraphic.RADIUS+margin, y-amounts[1]*margin, 5);
        chips[2] = new ChipGraphic(x+5*ChipGraphic.RADIUS+2*margin, y-amounts[2]*margin, 25);
    }

    //getPile(value)
    //getChip(value)

    public ChipGraphic[] getChips() {
        return chips;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getMargin() {
        return margin;
    }
    public int[] getAmounts() {
        return amounts;
    }

    public void updateChips() {
        chips[0] = new ChipGraphic(x+ChipGraphic.RADIUS, y-amounts[0]*margin, 1);
        chips[1] = new ChipGraphic(x+3*ChipGraphic.RADIUS+margin, y-amounts[1]*margin, 5);
        chips[2] = new ChipGraphic(x+5*ChipGraphic.RADIUS+2*margin, y-amounts[2]*margin, 25);
    }
    public void updateAmounts(int[] amounts) {
        this.amounts = amounts;
    }
    public void addToAmounts(int[] amounts) {
        for (int i=0;i<this.amounts.length;i++)
            this.amounts[i] += amounts[i];
    }
    public void updatePosition(float x, float y, float margin) {
        this.x = x;
        this.y = y;
        this.margin = margin;
    }
    public void show() {
        showing = true;
    }
    public void hide() {showing = false;}


}
