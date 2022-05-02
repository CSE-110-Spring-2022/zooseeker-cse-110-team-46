package edu.ucsd.cse110.zooseeker46;

import java.util.List;

public class Exhibit {
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

    public String getName() {
        return name;
    }

    public Exhibit() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public boolean getisSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
