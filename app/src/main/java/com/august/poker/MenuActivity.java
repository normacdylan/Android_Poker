package com.august.poker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void clicked1(View view) {
        Intent deal1Intent = new Intent(this, TableActivity.class);
        deal1Intent.putExtra("PLAYERS", "1");
        startActivity(deal1Intent);
    }

    public void clicked2(View view) {
        Intent deal2Intent = new Intent(this, TableActivity.class);
        deal2Intent.putExtra("PLAYERS","2");
        startActivity(deal2Intent);
    }
}
