package dev.olog.data.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromLongToDate(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

}
