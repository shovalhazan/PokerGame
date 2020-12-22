package com.example.pokergame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Winner_Activity extends AppCompatActivity {
    private TextView winner_LBL;
    private Button win_BTN_startGame;
    private Button win_BTN_TopTen;
    public static final String EXTRA_COUNT = "EXTRA_COUNT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_);
        MySignal.getInstance().play(R.raw.win);
        hideTheStatusBar();
        findViews();
        String count = getIntent().getStringExtra(EXTRA_COUNT);
        winner_LBL.setText("" + count);

        win_BTN_TopTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topTenActivity();
            }
        });

        win_BTN_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameActivity();
            }
        });

    }
    private void hideTheStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);

    }
    private void startGameActivity() {
        Intent myIntent = new Intent(Winner_Activity.this, Game_Activity.class);
        startActivity(myIntent);
    }

    private void topTenActivity() {
        Intent myIntent = new Intent(Winner_Activity.this, TopTen_Activity.class);
        startActivity(myIntent);
    }

    public void findViews(){
        winner_LBL = findViewById(R.id.winPlayer_LBL);
        win_BTN_startGame=findViewById(R.id.win_BTN_startGame);
        win_BTN_TopTen=findViewById(R.id.win_BTN_TopTen);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}