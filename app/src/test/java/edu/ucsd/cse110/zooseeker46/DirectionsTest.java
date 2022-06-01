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

import edu.ucsd.cse110.zooseeker46.directions.Directions;

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
        testArrayZoo.add("flamingo");
        testArrayZoo.add("capuchin");
        GraphPath<String,IdentifiedWeightedEdge> path =
                DijkstraShortestPath.findPathBetween(zoo, "flamingo", "capuchin");

        double total = (double) path.getWeight();
        assertEquals(3100.0, total,0.01 );
    }

    @Test
    public void getNearestNeighbor(){
        placestoVist.put("koi",places.get("koi"));
        placestoVist.put("flamingo",places.get("flamingo"));
        Directions d = new Directions(placestoVist,zoo);
        d.exhibitsVertex = places;
        d.setStartID("entrance_exit_gate");
        assertEquals(2, d.getExhibitsToVisit().size());
        GraphPath f = d.findNearestNeighbor("entrance_exit_gate");
        assertEquals(1, d.getExhibitsToVisit().size());
        assertEquals("flamingo", f.getEndVertex());
        assertEquals(5300.0, f.getWeight(), 0.01);
        f = d.findNearestNeighbor((String)f.getEndVertex());
        assertEquals(0, d.getExhibitsToVisit().size());
        assertEquals("koi", f.getEndVertex());
        assertEquals(9600, f.getWeight(), 0.01);
    }

    @Test
    public void finalPath(){
        placestoVist.put("capuchin",places.get("capuchin"));
        placestoVist.put("flamingo",places.get("flamingo"));
        placestoVist.put("koi",places.get("koi"));
        Directions d = new Directions(placestoVist,zoo);
        d.setStartID("entrance_exit_gate");
        d.exhibitsVertex = places;
        d.finalListOfPaths();
        List<GraphPath<String,IdentifiedWeightedEdge>> f = d.getFinalPath();
        assertEquals("entrance_exit_gate", f.get(0).getStartVertex());
        assertEquals("flamingo", f.get(1).getStartVertex());
        assertEquals("capuchin", f.get(2).getStartVertex());
        assertEquals("koi", f.get(3).getStartVertex());
        assertEquals("entrance_exit_gate", f.get(0).getStartVertex());
    }
}
