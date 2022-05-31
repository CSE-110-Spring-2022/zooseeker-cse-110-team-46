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

public class MockDirections {

    private List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    private List<String> exhibitNamesID;
    private int counter;
    private Graph<String, IdentifiedWeightedEdge> exhibitsGraph;
    Directions d;
    private Map<String, ZooData.VertexInfo> exhibitsVertex;

    public void setFinalPath(List<GraphPath<String, IdentifiedWeightedEdge>> finalPath) {
        this.finalPath = finalPath;
    }

    public void setExhibitNamesID(List<String> exhibitNamesID) {
        this.exhibitNamesID = exhibitNamesID;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setExhibitsGraphTracking(Graph<String, IdentifiedWeightedEdge> exhibitsGraph){
        this.exhibitsGraph = exhibitsGraph;
    }

    public void setExhibitsVertexTracking(Map<String, ZooData.VertexInfo> exhibitsVertex){
        this.exhibitsVertex=exhibitsVertex;
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

    public List<GraphPath<String, IdentifiedWeightedEdge>> getFinalPath() {
        return finalPath;
    }

    public List<String> getExhibitNamesID() {
        return exhibitNamesID;
    }

    public int getCounter() {
        return counter;
    }

    public Graph<String, IdentifiedWeightedEdge> getExhibitsGraph() {
        return exhibitsGraph;
    }

    public Map<String, ZooData.VertexInfo> getExhibitsVertex() {
        return exhibitsVertex;
    }

    public List<String> getRemainingExhibits(){
        List<String> remainingExhibits = new ArrayList<>();
        for (int i = counter; i < exhibitNamesID.size(); i++){
            remainingExhibits.add(exhibitNamesID.get(i));
        }
        TrackingStatic.remainingExhibits = remainingExhibits;
        return remainingExhibits;
    }

    public List<String> getVisitedExhibits(){
        List<String> visitedExhibits = new ArrayList<>();
        for (int i = 0; i < counter; i++){
            visitedExhibits.add(exhibitNamesID.get(i));
        }
        TrackingStatic.visitedExhibits = visitedExhibits;
        return visitedExhibits;
    }

    public Coord locationOfExhibit(List<String> idList, int count){
        String currExhibit = idList.get(count);
        ZooData.VertexInfo info = exhibitsVertex.get(currExhibit);

        if (info.group_id == null){
            Coord coord = new Coord(info.lat, info.lng);
            return coord;
        }
        else if (info.group_id != null){
            ZooData.VertexInfo info_group = exhibitsVertex.get(info.group_id);
            Coord coord = new Coord(info_group.lat, info_group.lng);
            return coord;
        }

        return null;
    }

    public boolean checkOffRoute(Coord newLocation){
        List<String> remainingExhibits = getRemainingExhibits();
        Coord original = locationOfExhibit(remainingExhibits, 0);
        double shortestWeight = original.compareCoord(original, newLocation);
        for (int i = 0; i < remainingExhibits.size(); i++){
            Coord locationVal = locationOfExhibit(remainingExhibits, i);
            double checkOff = locationVal.compareCoord(locationVal, newLocation);
            if (checkOff < shortestWeight){
                shortestWeight = checkOff;
                return true;
            }
        }
        return false;
    }


}
