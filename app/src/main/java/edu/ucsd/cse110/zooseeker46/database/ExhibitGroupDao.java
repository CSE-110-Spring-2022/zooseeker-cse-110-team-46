package edu.ucsd.cse110.zooseeker46.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit_Group;

@Dao
public interface ExhibitGroupDao {
    @Insert
    long insert(Exhibit_Group exhibitItem);

    @Query("SELECT * FROM `Exhibit_group_items` WHERE `long_id`=:num_id")
    Exhibit_Group get(long num_id);

    @Query("SELECT * FROM `Exhibit_group_items`")
    List<Exhibit_Group> getAll();

    @Update
    int update(Exhibit_Group exhibitItem);

    @Delete
    int delete(Exhibit_Group exhibitItem);

}
