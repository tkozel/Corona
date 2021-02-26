package cz.uhk.corona.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cz.uhk.corona.model.DayStats;

@Dao
public interface DayStatsDao {
    @Query("SELECT * FROM DayStats ORDER BY day desc")
    List<DayStats> getAll();

    @Insert
    void insertAll(DayStats... items);
}
