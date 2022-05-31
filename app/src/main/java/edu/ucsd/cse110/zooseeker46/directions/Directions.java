package edu.ucsd.cse110.zooseeker46.directions;

import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.tracking.TrackingStatic;

/*
    Calculates the route of the trip from selected exhibits
 */
public class Directions {
    private List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    private Map<String, ZooData.VertexInfo> exhibitsToVisit;
    private Graph<String, IdentifiedWeightedEdge> zooGraph;
    public Map<String, ZooData.VertexInfo> exhibitsVertex;
    private String startID;
    private List<String> exhibitsNamesID;
    Map<String, ZooData.VertexInfo> exhibitToVisitWO;

    public Directions(Map<String, ZooData.VertexInfo> exhibitsToVisit,
                      Graph<String, IdentifiedWeightedEdge> zooGraph){
        this.zooGraph = zooGraph;
        this.exhibitsToVisit = exhibitsToVisit;
        this.finalPath = new ArrayList<>();
        this.exhibitsNamesID = new ArrayList<>();
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
                if(exhibitsVertex.get(placeToVisit).group_id != null){
                    String groupToVisit = exhibitsVertex.get(placeToVisit).group_id;
                    newPath = DijkstraShortestPath.findPathBetween(zooGraph, begin, groupToVisit);
                }
                else{
                    newPath = DijkstraShortestPath.findPathBetween(zooGraph, begin, placeToVisit);
                }
            }
            if (shortestPathAtoB == null || newPath.getWeight() < shortestPathAtoB.getWeight()) {
                shortestPathAtoB = newPath;
                visited = placeToVisit;
            }
        }
        this.exhibitToVisitWO.put(visited, exhibitsVertex.get(visited));
        exhibitsNamesID.add(visited);
        this.exhibitsToVisit.remove(visited);
        return shortestPathAtoB;
    }

    public void finalListOfPaths(){
        String begin = startID;
        this.finalPath = new ArrayList<>();
        this.exhibitsNamesID = new ArrayList<>();
        this.exhibitToVisitWO = new HashMap<>();
        while(exhibitsToVisit.size() > 0){
            GraphPath<String, IdentifiedWeightedEdge> toAdd = findNearestNeighbor(begin);
            finalPath.add(toAdd);
            begin = toAdd.getEndVertex();
            Log.d("begin", begin);
        }
        finalPath.add(DijkstraShortestPath.findPathBetween(zooGraph, begin, "entrance_exit_gate"));
        this.exhibitsNamesID.add("entrance_exit_gate");
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

    public List<String> getExhibitsNamesID() {
        return exhibitsNamesID;
    }

    public Map<String, ZooData.VertexInfo> ExhibitsToMap(List<String> s){
        List<String> remaining = s;
        Map<String, ZooData.VertexInfo> map = new HashMap<>();
        for (int i = 0; i < remaining.size(); i++){
            ZooData.VertexInfo info = TrackingStatic.places.get(remaining.get(i));
            map.put(remaining.get(i), info);
        }
        return map;
    }

    public Map<String, ZooData.VertexInfo> getExhibitToVisitWO(){
       return this.exhibitToVisitWO;
    }

    public Map<String, ZooData.VertexInfo> getExhibitsToVisit(){
        return exhibitsToVisit;
    }


}

