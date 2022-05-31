package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Intersection_items")
public class Intersection implements Location{
    @NonNull
    public String name;
    //public tags tags;

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String id;

    //@PrimaryKey(autoGenerate = true)
    //public long long_id;

    @Nullable
    private Double latitude;

    @Nullable
    private Double longitude;

    public Intersection(@NonNull String id, String name){
        this.name = name;
        this.id = id;
        //this.tags = new tags(tags);
        this.latitude = null;
        this.longitude= null;
    }

    public Intersection() {

    }


    public Intersection(@NonNull String id, String name, List<String> tags, Double lat, Double lng){
        this.name = name;
        this.id = id;
        //this.tags = new tags(tags);
        this.latitude = lat;
        this.longitude = lng;
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

//    public List<String> getTags() {
//        return tags.getTags();
//    }
//
//    public void setTags(List<String> tags) {
//        this.tags = new tags(tags);
//    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
