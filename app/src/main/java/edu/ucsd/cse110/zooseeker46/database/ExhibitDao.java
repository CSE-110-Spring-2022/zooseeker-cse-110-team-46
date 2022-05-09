package edu.ucsd.cse110.zooseeker46.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;

@Dao
public interface ExhibitDao {
    @Insert
    long insert(Exhibit exhibitItem);

    @Query("SELECT * FROM 'Exhibit_items' WHERE 'long_id' = :id")
    Exhibit get(long num_id);

    /*@Query("Select * from  'Exhibit_items' order by 'order'")
    Exhibit getAll();
     */

    @Update
    int update(Exhibit exhibitItem);

    @Delete
    int delete(Exhibit exhibitItem);

}
