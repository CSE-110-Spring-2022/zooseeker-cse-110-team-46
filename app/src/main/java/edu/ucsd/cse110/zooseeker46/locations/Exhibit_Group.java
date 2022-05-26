package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Exhibit_group_items")
public class Exhibit_Group implements Location{
    @NonNull
    public String name;
    public String id;
    //public tags tags;

    @PrimaryKey(autoGenerate = true)
    public long long_id;

    private double latitude;
    private double longitude;

    /*public Exhibit_Group(@NonNull String id, String name, List<String> tags, double lat, double lng){
        this.name = name;
        this.id = id;
        this.tags = new tags(tags);
        this.latitude = lat;
        this.longitude = lng;
    }*/

    public Exhibit_Group(@NonNull String id, String name, double lat, double lng){
        this.name = name;
        this.id = id;
        //this.tags = new tags(new ArrayList<String>());
        this.latitude = lat;
        this.longitude = lng;
    }

    public Exhibit_Group() {}

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


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
