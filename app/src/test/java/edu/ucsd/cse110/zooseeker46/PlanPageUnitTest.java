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

import edu.ucsd.cse110.zooseeker46.plan.PlanExhibitsAdapter;

//should update later with a database
@RunWith(AndroidJUnit4.class)
public class PlanPageUnitTest {
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    List<String> testArrayZoo = new ArrayList<>();
    PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
    @Before
    public void createAdapter(){
        testArrayZoo.add("flamingo");
        testArrayZoo.add("capuchin");
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(context, "sample_zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(context, "sample_edge_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        adapter.setExhibits(testArrayZoo);
    }
    @Test
    public void orderIsCorrect() {
        createAdapter();
        assertEquals("flamingo", adapter.getExhibitsPlan().get(0));
        assertEquals("capuchin",adapter.getExhibitsPlan().get(1));
    }

    @Test
    public void DistanceIsCorrect() {
        createAdapter();
        assertEquals((Integer) 90, adapter.exhibitsEntrance.get("flamingo"));
        assertEquals((Integer) 240, adapter.exhibitsEntrance.get("capuchin"));
    }


}
