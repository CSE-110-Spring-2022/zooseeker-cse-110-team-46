package edu.ucsd.cse110.zooseeker46;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class DirectionsTest {
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    List<String> testArrayZoo = new ArrayList<>();
    Map<String,ZooData.VertexInfo> placestoVist = new HashMap<>();
    Map<String, ZooData.EdgeInfo> streets;
    Map<String,ZooData.VertexInfo> places;
    Graph<String, IdentifiedWeightedEdge> zoo;
    @Before
    public void setGraph(){
        zoo = ZooData.loadZooGraphJSON(context, "sample_zoo_graph.json");
        streets = ZooData.loadEdgeInfoJSON(context, "sample_edge_info.json");
        places = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
    }

    @Test
    public void getsDistance(){
        testArrayZoo.add("lions");
        testArrayZoo.add("gators");
        GraphPath<String,IdentifiedWeightedEdge> path =
                DijkstraShortestPath.findPathBetween(zoo, "lions", "gators");

        double total = (double) path.getWeight();
        assertEquals(200.0, total,0.01 );
    }

    @Test
    public void getNearestNeighbor(){
        placestoVist.put("entrance_exit_gate",places.get("entrance_exit_gate"));
        placestoVist.put("lions",places.get("lions"));
        placestoVist.put("gators",places.get("gators"));
        placestoVist.put("arctic_foxes",places.get("arctic_foxes"));
        Directions d = new Directions(placestoVist,zoo);
        GraphPath f = d.findNearestNeighbor("entrance_exit_gate", placestoVist);
        assertEquals(3, d.getExhibitsToVisit().size());
        assertEquals("gators", f.getEndVertex());
        assertEquals(110, f.getWeight(), 0.01);
        f = d.findNearestNeighbor((String)f.getEndVertex(), placestoVist);
        assertEquals(2, d.getExhibitsToVisit().size());
        assertEquals("lions", f.getEndVertex());
        assertEquals(200, f.getWeight(), 0.01);
    }

    @Test
    public void finalPath(){
        placestoVist.put("entrance_exit_gate",places.get("entrance_exit_gate"));
        placestoVist.put("lions",places.get("lions"));
        placestoVist.put("gators",places.get("gators"));
        placestoVist.put("arctic_foxes",places.get("arctic_foxes"));
        Directions d = new Directions(placestoVist,zoo);
        //d.findStart(places);
        d.finalListOfPaths();
        List<GraphPath<String,IdentifiedWeightedEdge>> f = d.getFinalPath();
        assertEquals("entrance_exit_gate", f.get(0).getStartVertex());
        assertEquals("gators", f.get(1).getStartVertex());
        assertEquals("lions", f.get(2).getStartVertex());
        assertEquals("arctic_foxes", f.get(3).getStartVertex());
        assertEquals("entrance_exit_gate", f.get(0).getStartVertex());


    }

}
