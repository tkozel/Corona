package cz.uhk.corona.db;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.net.ContentHandler;
import java.util.Date;

import cz.uhk.corona.model.DayStats;

/**
 * Room database poskytujici DAO dennich statistik
 * Singleton pro minimalizaci opakovneho otvirani DB
 * @see DayStatsDao
 */
@Database(entities = {DayStats.class}, version = 1, exportSchema = false)
@TypeConverters({CovidDatabase.DateConverter.class})
public abstract class CovidDatabase extends RoomDatabase {
    private static CovidDatabase INSTANCE = null;

    public abstract DayStatsDao getDayStatsDao();

    /**
     * Metoda vracejici Singleton DB
     * @param context reference na kontext
     * @return isntance databaze
     */
    public static CovidDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    CovidDatabase.class, "covid.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    /**
     * Konvertor Date na long pro datum v dennich statistikach
     * @see DayStats
     */
    public static class DateConverter {
        @TypeConverter
        public long dateToTimestamp(Date date) {
            return date.getTime();
        }

        @TypeConverter
        public Date timestampToDate(long timestamp) {
            return new Date(timestamp);
        }
    }
}
