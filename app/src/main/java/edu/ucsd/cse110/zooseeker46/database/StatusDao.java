package edu.ucsd.cse110.zooseeker46.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Status;

@Dao
public interface StatusDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Long insert(Status statusitem);

    @Query("SELECT * FROM `Status_items` WHERE `id`=:num_id")
    boolean get(String num_id);
}
