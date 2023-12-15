// @author Aryeh Freud
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminSettings extends AppCompatActivity {
    private Button mButtonMainScreen;
    private Button mButtonViewUsers;
    private Button mButtonViewScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        backToMain(); // back to main screen
        viewScores(); // view scores
        //viewUsers();
    }


    private void backToMain() { // back to main screen
        mButtonMainScreen = findViewById(R.id.buttonReturnMainScreen);
        mButtonMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void viewScores() {
        mButtonViewScores = findViewById(R.id.buttonViewScores);
        mButtonViewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to scores page
                Intent intent = new Intent(AdminSettings.this, AdminViewScores.class);
                startActivity(intent);
            }
        });
    }

//    private void viewUsers() {
//        mButtonViewUsers = findViewById(R.id.buttonViewAllTheUsers);
//        mButtonViewUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // go to users page
//                Intent intent = new Intent(AdminSettings.this, AdminViewUsers.class);
//                startActivity(intent);
//            }
//        });
//    }



}