package edu.ucsd.cse110.zooseeker46.directions;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;

public interface DirectionTypeInterface {

    public String pathFormat(String startName, String endName, String streetName, int length);
    public String getStartName(Map<String, ZooData.VertexInfo> map, IdentifiedWeightedEdge edge);
    public String getEndName(Map<String, ZooData.VertexInfo> map, IdentifiedWeightedEdge edge);
    public String getStreetName(Map<String, ZooData.EdgeInfo> map, IdentifiedWeightedEdge edge);
    public int getLength(Graph<String, IdentifiedWeightedEdge> graph, IdentifiedWeightedEdge edge);
}
