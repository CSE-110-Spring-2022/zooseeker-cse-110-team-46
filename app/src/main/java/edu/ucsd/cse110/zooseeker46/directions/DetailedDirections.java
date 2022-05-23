package edu.ucsd.cse110.zooseeker46.directions;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;

public class DetailedDirections implements DirectionTypeInterface {



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
            return "From junction of " + partsStart[0] + " and " + partsStart[1] + ", go down "
                    + streetName + " " + length + "m towards " + partsEnd[0] + " and " + partsEnd[1];
        }
        else if (startName.contains("/")) {
            String[] partsStart2 = startName.split("/");
            return "From junction of " + partsStart2[0] + " and " + partsStart2[1] + ", go down "
                    + streetName + " " + length + "m towards " + endName;
        }
        else if (endName.contains("/")) {
            String[] partsEnd2 = endName.split("/");
            return "From " + startName + " down " + streetName + " " + length + "m towards junction of " + partsEnd2[0] + partsEnd2[1];
        }
        return "Go from " + startName + " down " + streetName
                + " " + length + "m towards  " + endName;
    }
}
