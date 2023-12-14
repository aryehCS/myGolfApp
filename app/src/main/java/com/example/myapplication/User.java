// @Author Aryeh Freud
// @Abstract User POJO
package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.DB.AppDataBase;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    @ColumnInfo(name = "mUsername")
    private String mUsername;
    @ColumnInfo(name = "mPassword")
    private String mPassword;
    @ColumnInfo(name = "isAdmin")
    private boolean isAdmin;
    public User(String username, String password, boolean admin) {
        //mUserId = userId;
        mUsername = username;
        mPassword = password;
        isAdmin = admin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getUserId() {
        return mUserId;
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserId=" + mUserId +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
