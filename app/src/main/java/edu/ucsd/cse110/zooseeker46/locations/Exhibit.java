package edu.ucsd.cse110.zooseeker46.locations;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.locations.Location;

public class Exhibit implements Location {
    public String id;
    public String name;
    public List<String> tags;
    public boolean isSelected;

    public Exhibit(String id, String name, List<String> tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.isSelected = false;
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
        return tags;
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Exhibit() {
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
