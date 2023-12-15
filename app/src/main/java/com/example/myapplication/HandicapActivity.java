// @author Aryeh Freud
package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.ScoreLogDAO;
import com.google.android.material.color.utilities.Score;

import java.util.List;

public class HandicapActivity extends AppCompatActivity {

    Button mButtonMainScreen;
    Button mComplainButton;
    TextView mHandicapDisplay;
    private int mUserId;
    private ScoreLogDAO scoreLogDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handicap);


        mComplainButton = findViewById(R.id.buttonComplain);
        mComplainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupComplaint();
            }
        });

        backToMain();

        mHandicapDisplay = findViewById(R.id.textViewHandicapDisplay);
        mUserId = getIntent().getIntExtra("userId", 0);
        scoreLogDAO = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "ScoreLog.db").allowMainThreadQueries().build().getScoreLogDAO();


        displayHandicap();
    }

    private void displayHandicap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ScoreLog> scores = scoreLogDAO.getAllScoreLogs();
                if (!scores.isEmpty()) {
                    double averageScore = calculateAverage(scores);
                    double handicap = Math.round((calculateHandicap(averageScore)) * 10.0) / 10.0;
                    // round to 2 decimal places
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // get handicap from database
                            // display handicap
                            mHandicapDisplay = findViewById(R.id.textViewHandicapDisplay);
                            mHandicapDisplay.setText(getString(R.string.handicap) + handicap);
                        }
                    });
                }
            }
        }).start();
    }

    private double calculateAverage(List<ScoreLog> scores) {
        double sum = 0;
        for(ScoreLog score : scores) {
            sum += score.getScore();
        }
        return sum / scores.size();
    }

    private double calculateHandicap(double averageScore) {
        return ((averageScore - 70.9) - (113.0 / 127.0));
    }

    private void backToMain() {
        mButtonMainScreen = findViewById(R.id.buttonReturnMS);
        mButtonMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void popupComplaint() {
        // buttonComplain is name of button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Problem?");
        builder.setMessage("No issues, that is your handicap. \nYou just suck.");

        builder.setPositiveButton("Alrighty then", null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}