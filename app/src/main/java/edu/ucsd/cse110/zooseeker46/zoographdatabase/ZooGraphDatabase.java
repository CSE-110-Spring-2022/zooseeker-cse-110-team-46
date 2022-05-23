package edu.ucsd.cse110.zooseeker46.zoographdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;
import edu.ucsd.cse110.zooseeker46.locations.ZooEdge;
import edu.ucsd.cse110.zooseeker46.locations.ZooNode;

@Database(entities = {ZooNode.class, ZooEdge.class}, version = 1, exportSchema = false)
public abstract class ZooGraphDatabase extends RoomDatabase {
    public abstract ZooGraphNodeDao zooNodeDao();
    public abstract ZooGraphEdgeDao zooEdgeDao();
}
