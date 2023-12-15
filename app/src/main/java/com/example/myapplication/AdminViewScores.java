package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
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

        backToMain();

        // initialize DAO
        scoreLogDAO = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "ScoreLog.db").allowMainThreadQueries().build().getScoreLogDAO();



        mDeleteScores = findViewById(R.id.buttonDeleteScores);
        mDeleteScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteScores();
            }
        });

        // viewUsers();
    }
//    private void viewUsers() {
//        mScoreList = findViewById(R.id.textViewScoresTable);
//        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
//                AppDataBase.class, "scoreLog-database").allowMainThreadQueries().build();
//        scoreLogDAO = db.getScoreLogDAO();
//        List<User> users = scoreLogDAO.getAllUsers();
//        displayUsers(users);
//    }
//
//    private void displayUsers(List<User> users) {
//        StringBuilder sb = new StringBuilder();
//        for(User user : users) {
//            sb.append(user.toString());
//            sb.append("\n");
//        }
//        mScoreList.setText(sb.toString());
//    }

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