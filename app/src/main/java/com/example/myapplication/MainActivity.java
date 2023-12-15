package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.ScoreLogDAO;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.color.utilities.Score;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.myapplication.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.myapplication.PREFERENCES_KEY";
    ActivityMainBinding binding;
    private TextView mMainDisplay;

    private EditText mScore;

    private Button mSubmit;

    private Button logoutBTN;
    private Button buttonToGame;

    private ScoreLogDAO mScoreLogDAO;

    private List<ScoreLog> mScoreLogList;
    private int mUserId = -1;
    private SharedPreferences mPreferences = null;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        checkForUser();
        loginUser(mUserId);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mMainDisplay = binding.textViewScoreLogDisplay;
        mScore = binding.editTextScore;
        mSubmit = binding.buttonSubmit;

        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());
        if(mUser != null && mUser.getIsAdmin()){
            Button adminSettings = binding.buttonAdminSettings;
            adminSettings.setVisibility(View.VISIBLE);
        }
        refreshDisplay();
        logoutUser();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitScoreLog();
                ScoreLog log = getValuesFromDisplay();
                log.setUserId(mUserId);
                mScoreLogDAO.insert(log);
                refreshDisplay();
            }
        });

        goToGame();
        goToAdminSettings();
    }// end of onCreate

    private void goToGame(){
        buttonToGame = findViewById(R.id.buttonToGame);
        buttonToGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // direct to game activity
                Intent intent = new Intent(MainActivity.this, Game.class);
                startActivity(intent);
            }
        });
    }

    private void goToAdminSettings() {
        Button adminSettings = binding.buttonAdminSettings;
        adminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminSettings.class);
                startActivity(intent);
            }
        });
    }


    private void loginUser(int userId) {
        mUser = mScoreLogDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
    }

    private void addUserToPreference(int userId) {
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getDatabase() {
        mScoreLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .getScoreLogDAO();
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        // do we have a user in the preferences?
        if(mUserId != -1) {
            return;
        }

        if(mPreferences == null){
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1){
            return;
        }

        // do we have a user in the database?
        List<User> users = mScoreLogDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("myuser", "user123",false);
            User altUser = new User("myuser2", "user123",false);
            User testUser = new User("testuser1", "testuser1",false);
            User admin2 = new User("admin2", "admin2",true);
            mScoreLogDAO.insert(defaultUser, altUser, testUser, admin2);
        }


        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void logoutUser(){
        logoutBTN = binding.buttonHomeLogOut;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertBuilder.setMessage("Confirm logout.");
                alertBuilder.show();
                alertBuilder.setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearUserFromIntent();
                                clearUserFromPref();
                                mUserId = -1;
                                checkForUser();
//                                Intent intent = LoginActivity.intentFactory(MainActivity.this);
//                                startActivity(intent);
                            }
                        });
                alertBuilder.setNegativeButton(getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                alertBuilder.create().show();
            }
        });

    }
    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }
    private void clearUserFromPref(){
        addUserToPreference(-1);
    }

    private ScoreLog getValuesFromDisplay(){
        int score = Integer.parseInt(mScore.getText().toString());
        ScoreLog log = new ScoreLog(score, mUserId);
        return log;
    }

    private void submitScoreLog(){ // to display scores
        int score = Integer.parseInt(mScore.getText().toString());
        ScoreLog scoreLog = new ScoreLog(score, mUserId);
        mScoreLogDAO.insert(scoreLog);

    }

    private void refreshDisplay() {
        //mScoreLogList = mScoreLogDAO.getLogById(mUserId);
        mScoreLogList = mScoreLogDAO.getLogsByUserId(mUserId);

        if(!mScoreLogList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(ScoreLog log : mScoreLogList){
                sb.append(log.toString()).append("\n");
            }
            mMainDisplay.setText(sb.toString());
        } else {
            mMainDisplay.setText(R.string.no_logs_message);
        }
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

}