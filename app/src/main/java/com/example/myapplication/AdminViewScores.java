// @author Aryeh Freud
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.ScoreLogDAO;

import java.util.List;

public class AdminViewScores extends AppCompatActivity {
    private TextView mScoreList;
    private Button mButtonMainScreen;
    private Button mDeleteScores;

    private ScoreLogDAO scoreLogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_scores);

        backToMain(); // back to main screen

        displayScores(); // display scores

        mDeleteScores = findViewById(R.id.buttonDeleteScores);
        mDeleteScores.setOnClickListener(new View.OnClickListener() { // delete scores
            @Override
            public void onClick(View v) {
                deleteScores();
            }
        });
    }

    private void displayScores() { // display scores
        new Thread(new Runnable() {
            @Override
            public void run() {
                scoreLogDAO = Room.databaseBuilder(getApplicationContext(),
                        AppDataBase.class, "ScoreLog.db").allowMainThreadQueries().build().getScoreLogDAO();

                List<ScoreLog> scores = scoreLogDAO.getAllScoreLogs();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mScoreList = findViewById(R.id.textViewScoresTable);
                        mScoreList.setMovementMethod(new ScrollingMovementMethod());
                        mScoreList.setText("");

                        for(ScoreLog score : scores) {
                            mScoreList.append(score.toString() + "\n");
                        }
                    }

                });
            }
        }).start();

    }

    private void backToMain() {
        mButtonMainScreen = findViewById(R.id.buttonReturnMainScreen);
        mButtonMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void deleteScores() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                scoreLogDAO.deleteAllScores();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AdminViewScores.this, "Scores deleted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

}