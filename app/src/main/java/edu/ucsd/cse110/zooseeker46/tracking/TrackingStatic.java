package edu.ucsd.cse110.zooseeker46.tracking;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.visitor;

public class TrackingStatic {
    public static int counter;
    public static List<String> exhibitNamesIDs;
    public static List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    public static Graph<String, IdentifiedWeightedEdge> zoo;
    public static Map<String, ZooData.VertexInfo> places;
    public static List<String> remainingExhibits;
    public static List<String> visitedExhibits;
    public static visitor visitor;

}
