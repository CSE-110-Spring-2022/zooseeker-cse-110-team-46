package edu.ucsd.cse110.zooseeker46.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;

@Dao
public interface IntersectionDao {
    @Insert
    long insert(Intersection interItem);

    @Query("SELECT * FROM `Intersection_items` WHERE `id`=:num_id")
    Intersection get(String num_id);

    @Query("SELECT * FROM `Intersection_items`")
    List<Intersection> getAll();


    @Update
    int update(Intersection intersectionItem);

    @Delete
    int delete(Intersection intersectionItem);
}
