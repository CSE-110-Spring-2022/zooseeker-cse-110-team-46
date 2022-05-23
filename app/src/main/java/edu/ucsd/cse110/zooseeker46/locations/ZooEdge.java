package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "graph_edges")
public class ZooEdge {
    @PrimaryKey(autoGenerate = true)
    public long long_id;

    @NonNull
    public String id;
    public String source;
    public String target;
    public double weight;

    public ZooEdge(String id, String source, String target, double weight) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public ZooEdge() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
