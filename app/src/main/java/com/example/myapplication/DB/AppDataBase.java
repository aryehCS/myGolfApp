package com.example.myapplication.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.ScoreLog;
import com.example.myapplication.User;

@Database(entities = {ScoreLog.class, User.class}, version = 3)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "ScoreLog.db";
    public static final String SCORELOG_TABLE = "scoreLog_table";

    public static final String USER_TABLE = "USER_TABLE";

    public abstract ScoreLogDAO getScoreLogDAO(); // abstract method to access the DAO

    // private static volatile AppDataBase instance; // can only access this variable from the main memory
    // private static final Object LOCK = new Object(); // something to lock on to prevent multiple threads from accessing the database at the same time


//    public static AppDataBase getInstance(Context context) {
//        if(instance == null) {
//            synchronized (LOCK) {
//                if(instance == null) {
//                    instance = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDataBase.class,
//                            DATABASE_NAME).build();
//                }
//            }
//        }
//        return instance;
//    }

}
