package com.august.poker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class activity_table_layout extends View{

    private Paint whiteFill, drawText;
    private String hand1, hand2, winner, name1, name2;
    private int height, width;
    private boolean initialized, betTime, tradeTime;
    private HandGraphic handGraphics1, handGraphics2;
    private StackGraphic stack1, stack2, pot;
    private ArrayList<ChipGraphic> player1Bet, player2Bet;

    public activity_table_layout(Context context) {
        super(context);

        setBackgroundResource(R.drawable.pokertable);
        setWidthAndHeight(context);

        setup();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (initialized) {
            drawHand(handGraphics1, canvas);
            drawHand(handGraphics2, canvas);
            drawStack(pot, canvas);
            if (isBetTime()) {
                drawStack(stack1, canvas);
                drawStack(stack2, canvas);
                drawBets(player1Bet, canvas);
                drawBets(player2Bet, canvas);
                drawStack(pot, canvas);
            }
        } else
            initialize(canvas);

        drawText.setColor(Color.BLACK);
        drawText.setTextSize(xPos(0.06f));

        if (handGraphics1.isFaceUp())
            canvas.drawText(hand1,xPos(0.1f),yPos(0.07f),drawText);
        if (handGraphics2.isFaceUp())
            canvas.drawText(hand2,xPos(0.1f),yPos(0.9f),drawText);

        canvas.drawText(name1, xPos(0.1f), yPos(0.27f), drawText);
        canvas.drawText(name2, xPos(0.1f), yPos(0.7f), drawText);

     //   canvas.drawText(winner, xPos(0.1f), yPos(0.5f), drawText);

        Log.i("SIZE", "width:"+width+" height:"+height);
    }


    private void initialize(Canvas canvas) {
        drawHandInitial(handGraphics1, canvas);
        drawHandInitial(handGraphics2, canvas);
        drawStack(pot, canvas);
        if (betTime) {
            drawStack(stack1, canvas);
            drawStack(stack2, canvas);
            drawBets(player1Bet, canvas);
            drawBets(player2Bet, canvas);
        }

        initialized = true;
    }

    private void setup() {
        whiteFill = new Paint();
        whiteFill.setAntiAlias(true);
        whiteFill.setColor(Color.WHITE);
        whiteFill.setStyle(Paint.Style.FILL);

        drawText = new Paint();
        drawText.setAntiAlias(true);
       // drawText.setTypeface(Typeface.SERIF);

        initialized = false;
        betTime = false;
        tradeTime = true;

        handGraphics1 = new HandGraphic(0.1f, 0.12f, 0.03f, true);
        handGraphics2 = new HandGraphic(0.1f, 0.75f, 0.03f, true);

        stack1 = new StackGraphic(0.68f, handGraphics1.getY()+CardGraphic.getHEIGHT(), 0.01f);
        stack2 = new StackGraphic(0.68f, handGraphics2.getY()+CardGraphic.getHEIGHT(), 0.01f);
        pot = new StackGraphic(0.68f, 0.5f, 0.01f);

        player1Bet = new ArrayList<>();
        player2Bet = new ArrayList<>();
    }

    public void updateName(String name, int handNr) {
        if (handNr==1)
            name1 = name;
        else if (handNr==2)
            name2 = name;
    }

    public void updateChipsAmounts(int[] amounts, int handNr) {
        getStack(handNr).updateAmounts(amounts);
    }

    private void drawHandInitial(HandGraphic handGraphics, Canvas canvas) {
        float x = handGraphics.getX();
        for (int i=0;i<5;i++) {
            handGraphics.getCard(i).updatePosition(x, handGraphics.getY());
            if (handGraphics.isFaceUp())
                drawCard(handGraphics.getCard(i),canvas);
            else
                drawCardBack(handGraphics.getCard(i), canvas);
            x += handGraphics.getCard(i).getWIDTH()+ handGraphics.getMargin();
        }
    }

    private void drawHand(HandGraphic handGraphics, Canvas canvas) {
        if (handGraphics.isFaceUp()) {
            for (int i = 0; i < 5; i++) {
                drawCard(handGraphics.getCard(i), canvas);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                drawCardBack(handGraphics.getCard(i), canvas);
            }
        }
    }

    public void updateHand(String[] suits, String[] abbrevs, String hand, int handNr) {
        if (handNr==1 && getHand(handNr).isFaceUp()) {
            updateCards(suits, abbrevs, getHand(handNr));
            hand1 = hand;
        }
        else if (handNr==2 && getHand(handNr).isFaceUp()) {
            updateCards(suits, abbrevs, getHand(handNr));
            hand2 = hand;
        }
        invalidate();
    }

    private void updateCards(String[] suits, String[] abbrevs, HandGraphic handGraphics) {
          for (int k=0;k<handGraphics.getNrOfCards();k++)
              handGraphics.getCard(k).updateImage(getPicAndColor(suits[k])[0], getPicAndColor(suits[k])[1], abbrevs[k]);
    }

    private void drawCard(CardGraphic card, Canvas canvas) {
        Rect rect = new Rect();
        rect.set(xPos(card.getX()),yPos(card.getY()),xPos(card.getX()+card.getWIDTH()),yPos(card.getY()+card.getHEIGHT()));
        canvas.drawRect(rect,whiteFill);
        card.bitmap = BitmapFactory.decodeResource(getResources(),card.getPic());
        canvas.drawBitmap(card.bitmap,xPos(card.getX()+0.029f),yPos(card.getY()+0.0325f),null);
        drawText.setColor(card.getColor());
        drawText.setTextSize(xPos(0.04f));
        canvas.drawText(card.getAbbrev(),xPos(card.getX()+0.078f),yPos(card.getY()+0.023f),drawText);
        canvas.drawText(card.getAbbrev(),xPos(card.getX()+0.01f),yPos(card.getY()+0.09f),drawText);
    }

    private void drawCardBack(CardGraphic card, Canvas canvas) {
        card.bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.backsmall);
        Bitmap resized = Bitmap.createScaledBitmap(card.bitmap,xPos(card.getWIDTH()),yPos(card.getHEIGHT()),false);
        canvas.drawBitmap(resized,xPos(card.getX()),yPos(card.getY()),null);
    }

    private void drawChip(float x, float y, int value, Canvas canvas) {
        int pic = value==1? R.drawable.chip1: value==5? R.drawable.chip5:R.drawable.chip25;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),pic);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap,xPos(ChipGraphic.RADIUS*2),xPos(ChipGraphic.RADIUS*2),false);
        canvas.drawBitmap(resized,xPos(x)-xPos(ChipGraphic.RADIUS),yPos(y)-xPos(ChipGraphic.RADIUS),null);
    }

    private void drawPile(float x, float y, int value, int amount, Canvas canvas) {
        float yIndex = y;
        for (int i=0;i<amount;i++) {
            drawChip(x, yIndex, value, canvas);
            yIndex -= 0.01f;
        }
    }

    private void drawStack(StackGraphic stack, Canvas canvas) {
        drawPile(stack.getX()+ChipGraphic.RADIUS, stack.getY(), 1, stack.getAmounts()[0], canvas);
        drawPile(stack.getX()+3*ChipGraphic.RADIUS+stack.getMargin(), stack.getY(), 5, stack.getAmounts()[1], canvas);
        drawPile(stack.getX()+5*ChipGraphic.RADIUS+2*stack.getMargin(), stack.getY(), 25, stack.getAmounts()[2], canvas);
        stack.updateChips();
    }

    public void updatePot(int handNr) {
        pot.addToAmounts(getBetAmounts(getBet(handNr)));
    }

    public void resetBet(int handNr) {
        getBet(handNr).clear();
    }

    private int[] getBetAmounts(ArrayList<ChipGraphic> bet) {
        int amounts[] = new int[3];
        for (int i=0;i<bet.size();i++) {
            switch (bet.get(i).getValue()) {
                case 1:
                    amounts[0]++;
                    break;
                case 5:
                    amounts[1]++;
                    break;
                case 25:
                    amounts[2]++;
                    break;
                default:
            }
        }
        return amounts;
    }

    public void updateWinner(String winner) {
        this.winner = winner;
        invalidate();
    }

    public void pickCard(CardGraphic card) {
        if (card.isPicked()) {
            card.updatePosition(card.getX(), card.getY()+0.035f);
            card.unpick();
        } else {
            card.updatePosition(card.getX(), card.getY()-0.035f);
            card.pick();
        }
        invalidate();
    }

    public CardGraphic getTouchedCard(float x, float y, HandGraphic handGraphics) {
        for (int i=0;i<handGraphics.getNrOfCards();i++) {
            if (getRect(handGraphics.getCard(i)).contains(Math.round(x),Math.round(y)))
                return handGraphics.getCard(i);
        }
        return null;
    }

    public ChipGraphic getTouchedChip(float x, float y, StackGraphic stack) {
        int index = -1;
        for (int i=0;i<stack.getChips().length;i++) {
            if (chipContains(x, y, stack.getChips()[i]) && stack.getAmounts()[i]>0)
                index = i;
        }
        return index==-1? null:stack.getChips()[index];
    }

    public ChipGraphic getTouchedChip(float x, float y, ArrayList<ChipGraphic> stack) {
        int index = -1;
        for (int i=0;i<stack.size();i++) {
            if (chipContains(x, y, stack.get(i)))
                index = i;
        }
        return index==-1? null:stack.get(index);
    }

    public void throwChip(ChipGraphic chip, int handNr) {
        Random r = new Random();
        if (handNr==1) {
            float x = r.nextFloat()*0.3f+0.25f;
            float y = r.nextFloat()*0.1f+0.4f;
            ChipGraphic newChip = new ChipGraphic(x,y, chip.getValue());
            player1Bet.add(newChip);
        } else if (handNr==2) {
            float x = r.nextFloat()*0.3f+0.25f;
            float y = r.nextFloat()*0.1f+0.6f;
            ChipGraphic newChip = new ChipGraphic(x,y, chip.getValue());
            player2Bet.add(newChip);
        }
        invalidate();
    }

    private void drawBets(ArrayList<ChipGraphic> chips, Canvas canvas) {
        for (int i=0;i<chips.size();i++) {
            drawChip(chips.get(i).getX(), chips.get(i).getY(), chips.get(i).getValue(), canvas);
        }
    }

    public int[] getPickedCards(HandGraphic handGraphics) {
        int amount = 0;
        for (int i=0;i<handGraphics.getNrOfCards();i++) {
            if (handGraphics.getCard(i).isPicked())
                amount++;
        }
        int[] pickedCards = new int[amount];
        int index = 0;
        for (int i=0;i<handGraphics.getNrOfCards();i++) {
            if (handGraphics.getCard(i).isPicked()) {
                pickedCards[index] = i;
                index++;
            }
        }
        return pickedCards;
    }

    public void setAllCardsUnpicked(HandGraphic handGraphics) {
        for (int i=0;i<handGraphics.getNrOfCards();i++) {
            if (handGraphics.getCard(i).isPicked()) {
                handGraphics.getCard(i).updatePosition(handGraphics.getCard(i).getX(), handGraphics.getCard(i).getY()+0.035f);
                handGraphics.getCard(i).unpick();
            }
        }
    }

    public void setTradeTime() {
        handGraphics1.updatePostition(0.1f, 0.12f, 0.03f);
        handGraphics2.updatePostition(0.1f, 0.75f, 0.03f);
        tradeTime = true;
        betTime = false;
        initialized = false;
        invalidate();
    }

    public void setBetTime() {
        handGraphics1.updatePostition(0.03f, 0.12f, 0.01f);
        handGraphics2.updatePostition(0.03f, 0.75f, 0.01f);
        tradeTime = false;
        betTime = true;
        initialized = false;
        invalidate();
    }

    public void displayMessage(String message, Context context) {
        Toast toast = new Toast(context);
        toast.setText(message);
        toast.setGravity(Gravity.TOP|Gravity.LEFT, xPos(0.2f),yPos(0.45f));
        toast.show();
    }

    public boolean isBetTime() {
        return betTime;
    }
    public boolean isTradeTime() {return tradeTime;}

    private int[] getPicAndColor(String suit) {
        int[] result = new int[2];
        switch (suit) {
            case "CLUBS":
                result[0] = R.drawable.clubs;
                result[1] = Color.BLACK;
                break;
            case "DIAMONDS":
                result[0] = R.drawable.diamond;
                result[1] = Color.RED;
                break;
            case "HEARTS":
                result[0] = R.drawable.hearts;
                result[1] = Color.RED;
                break;
            case "SPADES":
                result[0] = R.drawable.spades;
                result[1] = Color.BLACK;
                break;
            default:
        }
        return result;
    }

    private void setWidthAndHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
    }
    private int xPos(Float x) {
        return Math.round(x*width);
    }
    private int yPos(Float x) {
        return Math.round(x*height);
    }

    private Rect getRect(CardGraphic card) {
        Rect rect = new Rect();
        rect.set(xPos(card.getX()),yPos(card.getY()),xPos(card.getX()+card.getWIDTH()),yPos(card.getY()+card.getHEIGHT()));
        return rect;
    }
    private boolean chipContains(float x, float y, ChipGraphic chip) {
        return (((Math.abs(xPos(chip.getX()))-x) < xPos(ChipGraphic.RADIUS))
                && (Math.abs(yPos(chip.getY())-y) < xPos(ChipGraphic.RADIUS)));
    }

    public HandGraphic getHand(int handNr) {
        switch (handNr) {
            case 1:
                return handGraphics1;
            case 2:
                return handGraphics2;
            default:
                return handGraphics1;
        }
    }
    public StackGraphic getStack(int handNr) {
        return handNr==1? stack1:stack2;
    }
    public ArrayList<ChipGraphic> getBet(int handNr) {return handNr==1? player1Bet:player2Bet;}

    public boolean touchedCorrectHalf(int turn, float y) {
        return (turn==1 && y<=0.5*height) || (turn==2 && y>0.5*height);
    }
}
