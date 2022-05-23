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
            String[] partsStart = startName.split("/");
            String[] partsEnd = endName.split("/");
            return "Proceed from " + partsStart[0] + " and " + partsStart[1] + " towards " + partsEnd[0] + " and " + partsEnd[1];
        }
        else if (startName.contains("/")) {
            String[] partsStart2 = startName.split("/");
            return "Proceed from " + partsStart2[0] + " and " + partsStart2[1] + " towards " + endName;
        }
        else if (endName.contains("/")) {
            String[] partsEnd2 = endName.split("/");
            return "Proceed from " + startName + " to " + partsEnd2[0] + " and " + partsEnd2[1];
        }
        return "Continue on " + streetName;
    }
}
