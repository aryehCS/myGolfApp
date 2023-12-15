// @author Aryeh Freud
package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.DB.AppDataBase;

import java.util.Date;

@Entity(tableName = AppDataBase.SCORELOG_TABLE)
public class ScoreLog {
    @PrimaryKey(autoGenerate = true)
    private int mLogId;
    private int mScore;

    private Date mDate;

    private int mUserId;

    public ScoreLog(int score, int userId) {
        mScore = score;

        mDate = new Date();

        mUserId = userId;
    }

    @Override
    public String toString() {
        return  "\n" + "Log # " + mLogId + "\n" +
                "Score: " + mScore + "\n" +
                "Date: " + mDate + "\n" +
                "User ID: " + mUserId + "\n" +
                "--------------------------";
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int logId) {
        mLogId = logId;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
