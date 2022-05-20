package edu.ucsd.cse110.zooseeker46.zoographdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.locations.ZooNode;

@Dao
public interface ZooGraphNodeDao {
    @Insert
    long insert(ZooNode zooNode);

    @Query("SELECT * FROM `graph_nodes` WHERE `long_id`=:num_id")
    ZooNode get(long num_id);

    @Query("SELECT * FROM `graph_nodes`")
    List<ZooNode> getAll();

    @Update
    int update(ZooNode zooNode);

    @Delete
    int delete(ZooNode zooNode);
}
