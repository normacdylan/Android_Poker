package com.august.poker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class activity_table_layout extends View {

    Paint whiteFill, drawText;
    int[] pics;
    Bitmap[] bitmaps;
    String[] abbrevs;
    int[] colors;
    String hand;


    public activity_table_layout(Context context) {
        super(context);
        setBackgroundResource(R.drawable.greentable);
        whiteFill = new Paint();
        whiteFill.setColor(Color.WHITE);
        whiteFill.setStyle(Paint.Style.FILL);

        drawText = new Paint();
        drawText.setColor(Color.RED);
        drawText.setStyle(Paint.Style.FILL);
        drawText.setTextSize(45);

        pics = new int[5];
        bitmaps = new Bitmap[5];
        abbrevs = new String[5];
        colors = new int[5];

        hand = "Nothing";

        for (int j=0;j<5;j++) {
            pics[j] = R.drawable.clubs;
            bitmaps[j] = BitmapFactory.decodeResource(getResources(),pics[j]);
            abbrevs[j] = "A";
            colors[j] = Color.RED;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 50;
        for (int i=0;i<5;i++) {
            Rect rect = new Rect();
            rect.set(x,1000,x+150,1200);
            canvas.drawRect(rect,whiteFill);
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),pics[i]);
            canvas.drawBitmap(bitmaps[i],x+40,1065,null);
            drawText.setColor(colors[i]);
            canvas.drawText(abbrevs[i],x+100,1045,drawText);
            canvas.drawText(abbrevs[i],x+15,1180,drawText);
            x += 200;
        }
        drawText.setColor(Color.BLACK);
        drawText.setTextSize(90);
        canvas.drawText(hand,350,850,drawText);
    }

    public void updateCards(String[] suits, String[] abbrevs, String hand) {
          for (int k=0;k<5;k++) {
              switch (suits[k]) {
                  case "CLUBS":
                      pics[k] = R.drawable.clubs;
                      colors[k] = Color.BLACK;
                      break;
                  case "DIAMONDS":
                      pics[k] = R.drawable.diamond;
                      colors[k] = Color.RED;
                      break;
                  case "HEARTS":
                      pics[k] = R.drawable.hearts;
                      colors[k] = Color.RED;
                      break;
                  case "SPADES":
                      pics[k] = R.drawable.spades;
                      colors[k] = Color.BLACK;
                      break;
                  default:
              }
              this.abbrevs[k] = abbrevs[k];
              this.hand = hand;
          }
          invalidate();
    }


}
