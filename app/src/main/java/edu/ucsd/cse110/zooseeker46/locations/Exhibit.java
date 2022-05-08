package edu.ucsd.cse110.zooseeker46.locations;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

<<<<<<< HEAD:app/src/main/java/edu/ucsd/cse110/zooseeker46/locations/Exhibit.java
import edu.ucsd.cse110.zooseeker46.locations.Location;

public class Exhibit implements Location {
    public String id;
    public String name;
=======
public class Exhibit {
    private String id;
    private String name;
>>>>>>> 271f5f7013e4789843b009fa84ac940fe387c72d:app/src/main/java/edu/ucsd/cse110/zooseeker46/Exhibit.java
    public List<String> tags;
    private boolean isSelected;

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

    // Custom Comparator for Exhibit
    // Prioritizes those that are selected and than by the names
    // Used in SearchPageUnitTest and plan to be used in ExhibitSelectedAdapter
    public static Comparator<Exhibit> ExhibitNameComparator = new Comparator<Exhibit>() {

        public int compare(Exhibit s1, Exhibit s2) {
            String ExhibitName1 = s1.getName().toLowerCase();
            String ExhibitName2 = s2.getName().toLowerCase();

            if(s1.getisSelected()){
                if(s2.getisSelected()){
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
                if(s2.getisSelected()){
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