package edu.ucsd.cse110.zooseeker46;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.internal.bytecode.ClassHandler;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.directions.Directions;
import edu.ucsd.cse110.zooseeker46.plan.PlanExhibitsAdapter;

//should update later with a database
@RunWith(AndroidJUnit4.class)
public class PlanPageUnitTest {
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    Map<String,ZooData.VertexInfo> testMapZoo = new HashMap<>();
    PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
    Directions d;
    @Before
    public void createAdapter(){
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(context, "zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(context, "trail_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(context, "exhibit_info.json");
        testMapZoo.put("flamingo", adapter.exhibitsVertex.get("flamingo"));
        testMapZoo.put("capuchin", adapter.exhibitsVertex.get("capuchin"));
        testMapZoo.put("toucan", adapter.exhibitsVertex.get("toucan"));
        testMapZoo.put("motmot", adapter.exhibitsVertex.get("motmot"));
        d = new Directions(testMapZoo,adapter.exhibitsGraph);
        d.exhibitsVertex = adapter.exhibitsVertex;
        d.setStartID("entrance_exit_gate");
        d.finalListOfPaths();
        adapter.orderPlan(d.getFinalPath(),d.getExhibitsNamesID());
    }
    @Test
    public void orderIsCorrect() {
        createAdapter();
        assertEquals("flamingo", adapter.getExhibitsPlan().get(0));
        assertEquals("capuchin",adapter.getExhibitsPlan().get(1));
        assertEquals("motmot",adapter.getExhibitsPlan().get(2));
        assertEquals("toucan",adapter.getExhibitsPlan().get(3));
    }

    @Test
    public void DistanceIsCorrect() {
        createAdapter();
        assertEquals((Integer) 5300, adapter.exhibitsDistFromStart.get("flamingo"));
        assertEquals((Integer) 8400, adapter.exhibitsDistFromStart.get("capuchin"));
        assertEquals((Integer) 17500, adapter.exhibitsDistFromStart.get("toucan"));
        assertEquals((Integer) 17500, adapter.exhibitsDistFromStart.get("motmot"));

    }

    @Test
    public void StreetIsCorrect(){
        createAdapter();
        assertEquals("Monkey Trail", adapter.exhibitsStreet.get("flamingo"));
        assertEquals("Monkey Trail", adapter.exhibitsStreet.get("capuchin"));
        assertEquals("Treetops Way", adapter.exhibitsStreet.get("toucan"));
        assertEquals("Treetops Way", adapter.exhibitsStreet.get("motmot"));
    }


}
