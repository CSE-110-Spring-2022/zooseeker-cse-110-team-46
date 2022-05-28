package edu.ucsd.cse110.zooseeker46.locations;

import java.util.ArrayList;
import java.util.List;

public class ExhibitBuilder {
    public String id;
    public String name;
    public tags tags;
    public boolean isSelected = false;
    private String parent_id;
    private Double latitude;
    private Double longitude;

    public ExhibitBuilder addid(String id){ this.id = id; return this; }
    public ExhibitBuilder addname(String name){ this.name = name; return this; }
    public ExhibitBuilder addtags(List<String> tags){ this.tags = new tags(tags); return this; }
    public ExhibitBuilder addParentid(String parent_id){ this.parent_id = parent_id; return this; }
    public ExhibitBuilder addCoords(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }

    public Exhibit getExhibit(){
        return new Exhibit(id, name, tags, isSelected, parent_id, latitude, longitude);
    }
}
