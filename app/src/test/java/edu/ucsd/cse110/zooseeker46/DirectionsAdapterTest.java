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


@RunWith(AndroidJUnit4.class)
public class DirectionsAdapterTest {

    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DirectionsAdapter adapter = new DirectionsAdapter();

    @Before
    public void createAdapter(){
        adapter.setDirections(DijkstraShortestPath.findPathBetween
                (ZooData.loadZooGraphJSON(context, "sample_zoo_graph.json"),
                        "entrance_exit_gate", "gorillas"));
    }

    @Test
    public void isSize(){
        assertEquals(2,adapter.getItemCount());
    }

    @Test
    public void String(){
        List<IdentifiedWeightedEdge> edges = adapter.path.getEdgeList();
        String paths =edges.get(0).toString();
        assertEquals("(entrance_exit_gate :edge-0: entrance_plaza)",paths);
    }
}
