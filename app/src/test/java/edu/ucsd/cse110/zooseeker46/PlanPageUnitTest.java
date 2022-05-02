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

//should update later with a database
@RunWith(AndroidJUnit4.class)
public class PlanPageUnitTest {
    List<String> testArray = new ArrayList<>();
    Map<String, ZooData.VertexInfo> VertexT = new HashMap<>();
    Map<String, ZooData.EdgeInfo> EdgeT = new HashMap<>();
    Graph<String, IdentifiedWeightedEdge> GraphT =
            new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);
    PlanExhibitsAdapter adapterT = new PlanExhibitsAdapter();
    RecyclerView test;
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Before
    public void createAdapter(){

        testArray.add("B");
        testArray.add("A");

        ZooData.VertexInfo start = new ZooData.VertexInfo();
        start.name = "entrance";
        start.id = "entrance_exit_gate";
        start.kind = ZooData.VertexInfo.Kind.GATE;
        VertexT.put("entrance_exit_gate", start);

        ZooData.VertexInfo av = new ZooData.VertexInfo();
        av.name = "A exhibit";
        av.id = "A";
        av.kind = ZooData.VertexInfo.Kind.EXHIBIT;
        VertexT.put("A",av);

        ZooData.VertexInfo bv = new ZooData.VertexInfo();
        bv.name = "B exhibit";
        bv.id = "B";
        bv.kind = ZooData.VertexInfo.Kind.EXHIBIT;
        VertexT.put("B",bv);


        ZooData.EdgeInfo e0 = new ZooData.EdgeInfo();
        e0.id = "edge-0";
        e0.street = "Entrance Way";
        EdgeT.put("edge-0", e0);

        ZooData.EdgeInfo e1 = new ZooData.EdgeInfo();
        e1.id = "edge-1";
        e1.street = "A and B Street";
        EdgeT.put("edge-1", e1);

        GraphT.addVertex("entrance_exit_gate");
        GraphT.addVertex("A");
        GraphT.addVertex("B");

        IdentifiedWeightedEdge ew0 = new IdentifiedWeightedEdge();
        ew0.setId("edge-0");
        GraphT.addEdge("entrance_exit_gate","A", ew0);
        GraphT.setEdgeWeight(ew0, 10);

        IdentifiedWeightedEdge ew1 = new IdentifiedWeightedEdge();
        ew1.setId("edge-1");
        GraphT.addEdge("A","B", ew1);
        GraphT.setEdgeWeight(ew1, 100);


        adapterT.exhibitsGraph = GraphT;
        adapterT.exhibitsVertex = VertexT;
        adapterT.exhibitsEdge = EdgeT;
        adapterT.setExhibits(testArray);
    }
    @Test
    public void orderIsCorrect() {
        //createAdapter();
        List<String> testArrayZoo = new ArrayList<>();
        testArrayZoo.add("lions");
        testArrayZoo.add("gators");
        PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(context, "sample_zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(context, "example_edge_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(context, "example_node_info.json");
        adapter.setExhibits(testArrayZoo);
        assertEquals("gators", adapter.getExhibitsPlan().get(0));
        assertEquals("lions",adapter.getExhibitsPlan().get(1));
    }

    @Test
    public void DistanceIsCorrect() {
        createAdapter();
        System.out.println(GraphT.toString());
        assertEquals((Integer) 10, adapterT.exhibitsEntrance.get("A"));
        assertEquals((Integer) 110,adapterT.exhibitsEntrance.get("B"));
    }



}
