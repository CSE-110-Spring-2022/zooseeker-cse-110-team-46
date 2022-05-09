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

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;
import edu.ucsd.cse110.zooseeker46.plan.PlanExhibitsAdapter;

//should update later with a database
@RunWith(AndroidJUnit4.class)
public class PlanPageUnitTest {
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    List<String> testArrayZoo = new ArrayList<>();
    PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
    @Before
    public void createAdapter(){
        testArrayZoo.add("lions");
        testArrayZoo.add("gators");
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(context, "sample_zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(context, "sample_edge_info.json");
        Map<String,ZooData.VertexInfo> vertexMap = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        for(String location: vertexMap.keySet()){
            ZooData.VertexInfo item = vertexMap.get(location);
            if(item.kind == ZooData.VertexInfo.Kind.EXHIBIT){
                adapter.exhibitsVertex.put(item.id, new Exhibit(item.id, item.name, item.tags));
            }
            if(item.kind == ZooData.VertexInfo.Kind.INTERSECTION){
                adapter.exhibitsVertex.put(item.id, new Intersection(item.id, item.name, item.tags));
            }
            if(item.kind == ZooData.VertexInfo.Kind.GATE){
                adapter.exhibitsVertex.put(item.id, new Gate(item.id, item.name, item.tags));
            }
        }
        adapter.setExhibits(testArrayZoo);
    }
    @Test
    public void orderIsCorrect() {
        //createAdapter();
        assertEquals("gators", adapter.getExhibitsPlan().get(0));
        assertEquals("lions",adapter.getExhibitsPlan().get(1));
    }

    @Test
    public void DistanceIsCorrect() {
        createAdapter();
        assertEquals((Integer) 310, adapter.exhibitsEntrance.get("lions"));
        assertEquals((Integer) 110, adapter.exhibitsEntrance.get("gators"));
    }


}
