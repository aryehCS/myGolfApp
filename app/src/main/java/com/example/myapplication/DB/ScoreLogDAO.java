// @author Aryeh Freud
// @abstract DAO File
package com.example.myapplication.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.ScoreLog;
import com.example.myapplication.User;

import java.util.List;

@Dao
public interface ScoreLogDAO { // entry point for accessing the database
    @Insert
    void insert (ScoreLog... scoreLog);

    @Update
    void update (ScoreLog... scoreLog);

    @Delete
    void delete (ScoreLog scoreLog);

    @Query("SELECT * FROM " + AppDataBase.SCORELOG_TABLE + " ORDER BY mDate DESC")
    List<ScoreLog> getAllScoreLogs(); // returns every score log in the database

    @Query("SELECT * FROM " + AppDataBase.SCORELOG_TABLE + " WHERE mLogId = :logId")
    List<ScoreLog> getLogById(int logId); // returns a score log with the given id

    @Query("SELECT * FROM " + AppDataBase.SCORELOG_TABLE + " WHERE mUserId = :userId  ORDER BY mDate DESC")
    List<ScoreLog> getLogsByUserId(int userId);

    @Insert
    void insert (User... users);

    @Update
    void update (User... users);

    @Delete
    void delete (User user);


    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUsername = :username ")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserId = :userId ")
    User getUserByUserId(int userId);
}
