package edu.ucsd.cse110.zooseeker46.directions;

import org.jgrapht.Graph;

import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;

public class SimpleDirections implements DirectionTypeInterface{

    public String getStartName(Map<String, ZooData.VertexInfo> map, IdentifiedWeightedEdge edge) {
        return map.get(edge.getSourceName()).name;
    }
    public String getEndName(Map<String, ZooData.VertexInfo> map, IdentifiedWeightedEdge edge){
        return map.get(edge.getTargetName()).name;
    }
    public String getStreetName(Map<String, ZooData.EdgeInfo> map, IdentifiedWeightedEdge edge){
        return map.get(edge.getId()).street;
    }
    public int getLength(Graph<String, IdentifiedWeightedEdge> graph, IdentifiedWeightedEdge edge){
        return (int) graph.getEdgeWeight(edge);
    }

    public String pathFormat(String startName, String endName, String streetName, int length){
        if (startName.contains("/") && endName.contains("/")){
            String[] start = startName.split("/");
            String[] end = endName.split("/");
            if (start[0].contains(streetName)){
                return "Continue on " + streetName;
            }
            if (start[1].contains(streetName)){
                return "Continue on " + streetName;
            }
            if (end[0].contains(streetName)){
                return "Continue on " + streetName + " towards " + end[1];
            }
            if (end[1].contains(streetName)){
                return "Continue on " + streetName + " towards " + end[0];
            }

        }
        return "Proceed on " + streetName + " for " + length + "m.";
    }
}
