package edu.ucsd.cse110.zooseeker46.directions;

import android.content.Context;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;

/*
    Calculates the route of the trip from selected exhibits
 */
public class Directions {
    private List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    private Map<String, ZooData.VertexInfo> exhibitsToVisit;
    private Graph<String, IdentifiedWeightedEdge> zooGraph;
    private String startID = "entrance_exit_gate";

    public Directions(Map<String, ZooData.VertexInfo> exhibitsToVisit,
                      Graph<String, IdentifiedWeightedEdge> zooGraph){
        this.zooGraph = zooGraph;
        this.exhibitsToVisit = exhibitsToVisit;
        this.finalPath = new ArrayList<>();
    }
    public void findStart(Map<String, ZooData.VertexInfo> places) {
        for (String placeToVisit : places.keySet()) {
            if (places.get(placeToVisit).kind == ZooData.VertexInfo.Kind.GATE) {
                this.startID = placeToVisit;
            }
        }
    }

    public GraphPath<String, IdentifiedWeightedEdge> findNearestNeighbor
            (String begin, Map<String, ZooData.VertexInfo> exhibitsToVisit){
        GraphPath<String,IdentifiedWeightedEdge> shortestPathAtoB = null;
        GraphPath<String, IdentifiedWeightedEdge> newPath = null;
        for (String placeToVisit : exhibitsToVisit.keySet()) {
            if(placeToVisit != begin) {
                newPath = DijkstraShortestPath.findPathBetween(zooGraph, begin, placeToVisit);
            }
            if (shortestPathAtoB == null || newPath.getWeight() < shortestPathAtoB.getWeight()) {
                shortestPathAtoB = newPath;
            }
        }
        this.exhibitsToVisit.remove(begin);
        return shortestPathAtoB;
    }

    public void finalListOfPaths(){
        String begin = startID;
        while(exhibitsToVisit.size() > 1){
            GraphPath<String, IdentifiedWeightedEdge> toAdd = findNearestNeighbor(begin, exhibitsToVisit);
            finalPath.add(toAdd);
            begin = toAdd.getEndVertex();
        }
        finalPath.add(DijkstraShortestPath.findPathBetween(zooGraph, begin, startID));
    }

    public void setExhibitsToVisit(Map<String,ZooData.VertexInfo> exhibitsToVisit){
        this.exhibitsToVisit = exhibitsToVisit;
    }

    public void setZooGraph(Graph<String,IdentifiedWeightedEdge> zooGraph){
        this.zooGraph = zooGraph;
    }

    public void setStartID(String startID){
        this.startID = startID;
    }

    public List<GraphPath<String,IdentifiedWeightedEdge>> getFinalPath(){
        return finalPath;
    }

    public Map<String, ZooData.VertexInfo> getExhibitsToVisit(){
        return exhibitsToVisit;
    }
}

