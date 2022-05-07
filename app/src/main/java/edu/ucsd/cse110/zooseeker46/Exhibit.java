package edu.ucsd.cse110.zooseeker46;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Exhibit {
    private String id;
    private String name;
    public List<String> tags;
    private boolean isSelected;

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