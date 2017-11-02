package com.august.poker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {


    private TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        text = (TextView)findViewById(R.id.text);
    }

    public void clickedDeal(View view) {
        Intent dealIntent = new Intent(this, TableActivity.class);
        startActivity(dealIntent);
    }

}
