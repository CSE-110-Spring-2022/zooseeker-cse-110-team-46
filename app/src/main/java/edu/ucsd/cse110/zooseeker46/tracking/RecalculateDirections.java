package edu.ucsd.cse110.zooseeker46.tracking;


import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.directions.Directions;

public class RecalculateDirections {

    public List<String> newOrderList = new ArrayList<>();
    public String startID;

    public Map<String, ZooData.VertexInfo> newMap(List<String> remainingExhibits){
        Map<String, ZooData.VertexInfo> map = new HashMap<>();
        for (int i = 0; i < remainingExhibits.size(); i++){
            ZooData.VertexInfo info = TrackingStatic.places.get(remainingExhibits.get(i));
            map.put(remainingExhibits.get(i), info);
        }
        return map;
    }

    Directions directions = new Directions(newMap(TrackingStatic.remainingExhibits), TrackingStatic.zoo);

    public List<GraphPath<String, IdentifiedWeightedEdge>> newFinalPath(){
        directions.setStartID(startID);
       directions.finalListOfPaths();
       newOrderList = directions.getExhibitsNamesID();
       TrackingStatic.remainingExhibits = newOrderList;
       return directions.getFinalPath();
    }

    public List<GraphPath<String, IdentifiedWeightedEdge>> originalHalfPath(){
        List<GraphPath<String, IdentifiedWeightedEdge>> newFirst = new ArrayList<>();
        for (int i = 0; i < TrackingStatic.counter; i++){
            newFirst.add(TrackingStatic.finalPath.get(i));
        }
        return newFirst;
    }

    public List<GraphPath<String, IdentifiedWeightedEdge>> newRerouted(){
        List<GraphPath<String, IdentifiedWeightedEdge>> newLast = newFinalPath();
        List<GraphPath<String, IdentifiedWeightedEdge>> newFirst = originalHalfPath();
        newFirst.addAll(newLast);
        TrackingStatic.finalPath = newFirst;
        return newFirst;
    }

    public List<String> newListIDs(){
        List<String> firstHalf = TrackingStatic.visitedExhibits;
        firstHalf.addAll(TrackingStatic.remainingExhibits);
        TrackingStatic.exhibitNamesIDs = firstHalf;
        return firstHalf;
    }
}
