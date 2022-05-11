package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Gate_items")
public class Gate implements Location {
    @NonNull
    public String id;
    public String name;
    public List<String> tags;

    @PrimaryKey(autoGenerate = true)
    public long long_id;

    public Gate(@NonNull String id, String name, List<String> tags){
        this.name = name;
        this.id = id;
        this.tags = tags;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String ID) {
        this.id = ID;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
