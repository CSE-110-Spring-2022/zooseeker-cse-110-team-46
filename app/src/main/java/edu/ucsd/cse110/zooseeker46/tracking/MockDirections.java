package edu.ucsd.cse110.zooseeker46.tracking;

import android.widget.EditText;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.directions.Directions;
import edu.ucsd.cse110.zooseeker46.directions.DirectionsActivity;

public class MockDirections {

    private List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    private List<String> exhibitNamesID;
    private int counter;
    public Graph<String, IdentifiedWeightedEdge> exhibitsGraph;
    Directions d;
    public Map<String, ZooData.VertexInfo> exhibitsVertex;
    private DirectionsActivity directionsActivity;



    MockDirections(DirectionsActivity directionsActivity){
        this.directionsActivity = directionsActivity;
        this.exhibitNamesID = directionsActivity.getExhibitNamesID();
        this.counter = directionsActivity.getCounter();
    }

    public Coord convertFromInput(EditText latInput, EditText longInput){
        double lat = Double.parseDouble(latInput.getText().toString());
        double lng = Double.parseDouble(longInput.getText().toString());
        return new Coord(lat, lng);
    }

    public void setExhibitsGraph(Graph<String, IdentifiedWeightedEdge> exhibitsGraph){
        this.exhibitsGraph = exhibitsGraph;
    }

    public void setExhibitsVertex(Map<String, ZooData.VertexInfo> exhibitsVertex){
        this.exhibitsVertex = exhibitsVertex;
    }

    public List<String> getRemainingExhibits(){
        List<String> remainingExhibits = new ArrayList<>();
        for (int i = counter; i < exhibitNamesID.size(); i++){
            remainingExhibits.add(exhibitNamesID.get(i));
        }
        return remainingExhibits;
    }

    /*public String findNearbyExhibit(Coord newLocation){
        List<String> remainingExhibits = getRemainingExhibits();
        for (int i = 0; i < remainingExhibits.size(); i++){
            ZooData.VertexInfo info = exhibitsVertex.get(remainingExhibits.get(i));
            if (info.parent_id == null){
                Coord coord = new Coord(info.lat, info.lng);
                coord.compareCoord(newLocation, coord)
            }
            else if (info.parent_id != null){
                ZooData.VertexInfo info_group = exhibitsVertex.get(info.parent_id);
                Coord coord = new Coord(info_group.lat, info_group.lng);
            }

        }
        return "";
    }*/


}
