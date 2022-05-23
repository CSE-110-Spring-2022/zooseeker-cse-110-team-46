package edu.ucsd.cse110.zooseeker46.directions;

import android.content.Context;
import android.util.Log;

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
    public Map<String, ZooData.VertexInfo> exhibitsVertex;
    private String startID = "entrance_exit_gate";
    private List<String> exhibitsInGroups;

    public Directions(Map<String, ZooData.VertexInfo> exhibitsToVisit,
                      Graph<String, IdentifiedWeightedEdge> zooGraph){
        this.zooGraph = zooGraph;
        this.exhibitsToVisit = exhibitsToVisit;
        this.finalPath = new ArrayList<>();
        this.exhibitsInGroups = new ArrayList<>();
    }
    public void findStart(Map<String, ZooData.VertexInfo> places) {
        for (String placeToVisit : places.keySet()) {
            if (places.get(placeToVisit).kind == ZooData.VertexInfo.Kind.GATE) {
                this.startID = placeToVisit;
            }
        }
    }

    public GraphPath<String, IdentifiedWeightedEdge> findNearestNeighbor
            (String begin){
        GraphPath<String,IdentifiedWeightedEdge> shortestPathAtoB = null;
        GraphPath<String, IdentifiedWeightedEdge> newPath = null;
        String visited = "";
        for (String placeToVisit : exhibitsToVisit.keySet()) {
            if(placeToVisit != begin) {
                if(exhibitsVertex.get(placeToVisit).parent_id != null){
                    String groupToVisit = exhibitsVertex.get(placeToVisit).parent_id;
                    newPath = DijkstraShortestPath.findPathBetween(zooGraph, begin, groupToVisit);
                }
                else{
                    newPath = DijkstraShortestPath.findPathBetween(zooGraph, begin, placeToVisit);
                }
            }
            if (shortestPathAtoB == null || newPath.getWeight() < shortestPathAtoB.getWeight()) {
                shortestPathAtoB = newPath;
                visited = placeToVisit;
                if(exhibitsVertex.get(shortestPathAtoB.getEndVertex()).kind ==
                        ZooData.VertexInfo.Kind.EXHIBIT_GROUP ){
                    exhibitsInGroups.add(visited);
                }
            }
        }
        this.exhibitsToVisit.remove(visited);
        return shortestPathAtoB;
    }

    public void finalListOfPaths(){
        String begin = startID;
        while(exhibitsToVisit.size() > 0){
            GraphPath<String, IdentifiedWeightedEdge> toAdd = findNearestNeighbor(begin);
            finalPath.add(toAdd);
            begin = toAdd.getEndVertex();
            Log.d("begin", begin);
        }
        finalPath.add(DijkstraShortestPath.findPathBetween(zooGraph, begin, startID));
        Log.d("finalPath", finalPath.toString());
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

    //adjust exhibits list to account for exhibit groups
    public void adjust_exhibits_to_visit(){

    }
}

