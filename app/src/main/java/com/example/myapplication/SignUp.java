package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.ScoreLogDAO;

public class SignUp extends AppCompatActivity {

    private Button mSignUpButton;
    private Button mLoginButton;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private String mUsername;
    private String mPassword;
    private String mConfirmPassword;
    private User mUser;
    private ScoreLogDAO mScoreLogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUpButton = findViewById(R.id.buttonSignupScreen);
        mLoginButton = findViewById(R.id.buttonSignupScreenLogin);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.intentFactory(SignUp.this);
                startActivity(intent);
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if username is taken

                //Check if passwords match

                //Check if passwords are empty

                //Check if username is empty

                //create User

                wireupDisplay();
                getDatabase();

            }
        });
    }


    private void wireupDisplay() {
        mUsernameField = findViewById(R.id.editTextSignupUsername);
        mPasswordField = findViewById(R.id.editTextSignupPassword);
        mConfirmPasswordField = findViewById(R.id.editTextSignupPasswordConfirm);
        mSignUpButton = findViewById(R.id.buttonSignupScreen);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if conditions are met, assign user details to database
//                if(!isUsernameEmpty()){
//                    Toast.makeText(SignUp.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
//                } else {
//                    if(!arePasswordsMatching()){
//                        Toast.makeText(SignUp.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if(!isPasswordEmpty()){
//                            Toast.makeText(SignUp.this, "Password field cannot be empty!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            if(!isUsernameEmpty()){
//                                Toast.makeText(SignUp.this, "Username field cannot be empty!", Toast.LENGTH_SHORT).show();
//                            } else {
//                                addUser(mUsernameField, mPasswordField);
//                                Intent intent = LoginActivity.intentFactory(SignUp.this);
//                                startActivity(intent);
//                            }
//                        }
//
//                    }
//                }
                String errMSG = "";
                if(!isUsernameEmpty()){
                    errMSG += "Username cannot be empty!\n";
                }
                else if(!isPasswordEmpty()){
                    errMSG += "Password field cannot be empty!\n";
                }
                else if(!arePasswordsMatching()){
                    errMSG += "Passwords do not match!\n";
                }
                else if(!doesUsernameExist()){
                    errMSG += "Username already exists!\n";
                }
                if(errMSG == "") {
                    addUser(mUsernameField, mPasswordField);
                    Intent intent = LoginActivity.intentFactory(SignUp.this);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUp.this, errMSG, Toast.LENGTH_SHORT).show();
                }


            }
        });
        getDatabase();
    }

    private boolean doesUsernameExist(){
        mUsername = mUsernameField.getText().toString();
        mUser = mScoreLogDAO.getUserByUsername(mUsername);
        if(mUser != null){
            return false;
        }
        return true;
    }

    private boolean checkForUserInDatabase(){
        mUser = mScoreLogDAO.getUserByUsername(mUsername);
        if(mUser != null){
            Toast.makeText(this, "User " + mUsername + " already exists!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean arePasswordsMatching(){
        mPassword = mPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();
        return mPassword.equals(mConfirmPassword);
    }

    private boolean isPasswordEmpty(){
        mPassword = mPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();
        if(mPassword.isEmpty() || mConfirmPassword.isEmpty()){

            return false;
        }
        return true;
    }

    private boolean isUsernameEmpty(){
        mUsername = mUsernameField.getText().toString();
        if(mUsername.isEmpty()){
            return false;
        }
        return true;
    }

    private void addUser(EditText username, EditText password){
        // convert edittext to string
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        // create user object
        mUser = new User(usernameString, passwordString,false);
        // add user to database
        mScoreLogDAO.insert(mUser);
    }
    private void getDatabase() {
        mScoreLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .getScoreLogDAO();
    }
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, SignUp.class);
        return intent;
    }
}