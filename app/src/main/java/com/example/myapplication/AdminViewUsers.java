package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminViewUsers extends AppCompatActivity {

    TextView mUsers;
    TextView mUsersList;
    Button mButtonMainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);

        backToMain();
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
}