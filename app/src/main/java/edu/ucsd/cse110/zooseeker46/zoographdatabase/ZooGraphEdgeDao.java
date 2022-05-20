package edu.ucsd.cse110.zooseeker46.zoographdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.locations.ZooEdge;

@Dao
public interface ZooGraphEdgeDao {
    @Insert
    long insert(ZooEdge zooEdge);

    @Query("SELECT * FROM `graph_edges` WHERE `long_id`=:num_id")
    ZooEdge get(long num_id);

    @Query("SELECT * FROM `graph_nodes`")
    List<ZooEdge> getAll();

    @Update
    int update(ZooEdge zooEdge);

    @Delete
    int delete(ZooEdge zooEdge);
}
