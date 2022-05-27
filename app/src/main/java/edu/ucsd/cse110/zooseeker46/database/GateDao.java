package edu.ucsd.cse110.zooseeker46.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Gate;

@Dao
public interface GateDao {
    @Insert
    long insert(Gate gateItem);

    @Query("SELECT * FROM `Gate_items` WHERE `id`=:num_id")
    Gate get(String num_id);

    @Query("SELECT * FROM `Gate_items`")
    List<Gate> getAll();


    @Update
    int update(Gate gateItem);

    @Delete
    int delete(Gate gateItem);
}
