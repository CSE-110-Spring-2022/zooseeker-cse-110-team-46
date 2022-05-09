package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


public class Intersection implements Location{

    private String name;
    private String id;
    private List<String> tags;

    public Intersection(String id, String name, List<String> tags){
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
        return this.id;
    }

    @Override
    public void setId(String ID) {
        this.id = ID;
    }

    @Override
    public List<String> getTags() {
        return this.tags;
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}