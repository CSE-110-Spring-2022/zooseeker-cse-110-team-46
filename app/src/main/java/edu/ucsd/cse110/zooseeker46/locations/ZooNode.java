package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "graph_nodes")
public class ZooNode {
    @PrimaryKey(autoGenerate = true)
    public long long_id;

    @NonNull
    public String id;

    public ZooNode(String id){
        this.id = id;
    }

    public ZooNode(){

    }
    public String getId() {
        return id;
    }

    public void setId(String ID) {
        this.id = ID;
    }
}
