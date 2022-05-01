package edu.ucsd.cse110.zooseeker46;

public class Exhibit {
    String animal_name;
    String animal_location;
    boolean selected;
    public Exhibit(String animal_name, String animal_location){
        this.animal_name = animal_name;
        this.animal_location = animal_location;
        this.selected = false;
    }
    public Exhibit(){

    }
    public void markSelected(){
        this.selected = true;
    }
}
