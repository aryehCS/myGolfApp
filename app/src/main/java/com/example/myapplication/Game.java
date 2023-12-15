package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.DB.GameUtil;

import org.w3c.dom.Text;

public class Game extends AppCompatActivity {

    private EditText mGameView;
    private TextView mGameDisplay;
    private Button mQuitButton;
    private Button mRandomButton;
    private GameUtil gameUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playGame();
        backToMain();
    }


    private void playGame() {
        mQuitButton = findViewById(R.id.buttonReturnMainScreen);
        mRandomButton = findViewById(R.id.buttonRandomize);
        mGameDisplay = findViewById(R.id.textViewGameView);

        gameUtil = new GameUtil();

        mRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameUtil.createDeck();
                gameUtil.shuffleDeck();
                mGameDisplay.setText(gameUtil.getCard());
            }
        });
    }

    private void backToMain() {
        mQuitButton = findViewById(R.id.buttonReturnMainScreen);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}