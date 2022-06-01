package edu.ucsd.cse110.zooseeker46;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.directions.DirectionsAdapter;


@RunWith(AndroidJUnit4.class)
public class DirectionsAdapterTest {

    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DirectionsAdapter adapter = new DirectionsAdapter();

    @Before
    public void createAdapter(){
        adapter.setPath(DijkstraShortestPath.findPathBetween
                (ZooData.loadZooGraphJSON(context, "zoo_graph.json"),
                        "entrance_exit_gate", "gorilla"));
    }

    @Test
    public void isSize(){
        assertEquals(9,adapter.getItemCount());
    }

    @Test
    public void String(){
        List<IdentifiedWeightedEdge> edges = adapter.path.getEdgeList();
        String paths =edges.get(0).toString();
        assertEquals("(entrance_exit_gate :gate_to_front: intxn_front_treetops)",paths);
    }
}
