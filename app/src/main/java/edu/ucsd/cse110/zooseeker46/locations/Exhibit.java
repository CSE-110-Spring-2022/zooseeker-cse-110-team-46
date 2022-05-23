package edu.ucsd.cse110.zooseeker46.locations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.List;

@Entity(tableName = "Exhibit_items")
public class Exhibit implements Location {
    
    @PrimaryKey(autoGenerate = true)
    public long long_id;

    @NonNull
    public String id;
    public String name;
    public tags tags;
    public boolean isSelected;

    private String parent_id;

    private double latitude;
    private double longitude;

    public Exhibit() {
    }

    /*
        We will never be using this constructor because if it does not have a parent_id,
        then it will have its own long/lat coordinates.
        Only kept for the purpose of not redoing unit Tests.
     */
    public Exhibit(@NonNull String id, String name, List<String> tags) {
        this.id = id;
        this.name = name;
        this.parent_id = "";
        this.tags = new tags(tags);
        this.isSelected = false;
        this.latitude = 0;
        this.longitude = 0;
    }

    /*
        Use for exhibit that does not belong in an exhibit group, meaning no parent_id
     */
    public Exhibit(@NonNull String id, String name, List<String> tags, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.parent_id = "";
        this.tags = new tags(tags);
        this.isSelected = false;
        this.latitude = lat;
        this.longitude = lng;
    }

    /*
        Use for an exhibit that has parent_id and no long/lat
     */
    public Exhibit(@NonNull String id, String name, String pid, List<String> tags) {
        this.id = id;
        this.name = name;
        this.parent_id = pid;
        this.tags = new tags(tags);
        this.isSelected = false;
        this.latitude = 0;
        this.longitude = 0;
    }

    /*
        General Constructor for all member variables
     */
    public Exhibit(@NonNull String id, String name, String pid, List<String> tags, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.parent_id = pid;
        this.tags = new tags(tags);
        this.isSelected = false;
        this.latitude = lat;
        this.longitude = lng;
    }

    @Override
    public String getName() {
        return name;
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
        return tags.getTags();
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags = new tags(tags);
    }

    public String getParent_id() { return this.parent_id; }

    public void setParent_id(String pid) { this.parent_id = pid; }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        // We should update Dao here! exhibitDao.update(this);
        isSelected = selected;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this. latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    // Custom Comparator for Exhibit
    // Prioritizes those that are selected and than by the names
    // Used in SearchPageUnitTest and plan to be used in ExhibitSelectedAdapter
    public static Comparator<Exhibit> ExhibitNameComparator = new Comparator<Exhibit>() {

        public int compare(Exhibit s1, Exhibit s2) {
            String ExhibitName1 = s1.getName().toLowerCase();
            String ExhibitName2 = s2.getName().toLowerCase();

            if(s1.getIsSelected()){
                if(s2.getIsSelected()){
                    // Just keep it in alphabetical order
                    return ExhibitName1.compareTo(ExhibitName2);
                }
                // s1 checked, s2 unchecked
                else{
                    // Prioritize s1 as selected
                    return -1;
                }
            }
            else{
                if(s2.getIsSelected()){
                    // Prioritize s2 as selected
                    return 1;
                }
                else{
                    // Just keep it in alphabetical order
                    return ExhibitName1.compareTo(ExhibitName2);
                }
            }

        }};
}