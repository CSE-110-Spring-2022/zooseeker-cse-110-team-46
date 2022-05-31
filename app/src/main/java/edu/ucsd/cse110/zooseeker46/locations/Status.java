package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Status_items")
public class Status {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String id;

    @NonNull
    public boolean onDirections;

    public Status(){
        id = "onDir";
        onDirections = false;
    }

    public void setOnDirections(){
        onDirections = true;
    }

    public boolean getOnDirections(){
        return onDirections;
    }
}
