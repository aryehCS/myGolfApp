// @author Aryeh Freud
package com.example.myapplication.DB;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {
    @TypeConverter
    public long convertDateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(long epoch) {
        return new Date(epoch);
    }
}
