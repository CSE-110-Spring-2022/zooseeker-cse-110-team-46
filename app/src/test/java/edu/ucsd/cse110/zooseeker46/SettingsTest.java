package edu.ucsd.cse110.zooseeker46;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.directions.DetailedDirections;
import edu.ucsd.cse110.zooseeker46.directions.Directions;
import edu.ucsd.cse110.zooseeker46.directions.DirectionsActivity;
import edu.ucsd.cse110.zooseeker46.directions.DirectionsAdapter;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.plan.PlanExhibitsAdapter;
import edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;
import edu.ucsd.cse110.zooseeker46.search.mockExhibitSelectAdapter;

@RunWith(AndroidJUnit4.class)
public class SettingsTest {

    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    Map<String,ZooData.VertexInfo> testMapZoo = new HashMap<>();
    PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
    Directions d;

    DirectionsAdapter adapter2 = new DirectionsAdapter();

    mockExhibitSelectAdapter customAdapter;
    ZooExhibits ze = new ZooExhibits(ZooData.loadVertexInfoJSON(context, "exhibit_info.json"));
    ArrayList<Exhibit> totalExhibits;

    @Before
    public void createAdapter(){
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(context, "zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(context, "trail_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(context, "exhibit_info.json");
        testMapZoo.put("capuchin", adapter.exhibitsVertex.get("capuchin"));
        d = new Directions(testMapZoo,adapter.exhibitsGraph);
        d.exhibitsVertex = adapter.exhibitsVertex;
        d.setStartID("entrance_exit_gate");
        d.finalListOfPaths();
        adapter.orderPlan(d.getFinalPath(),d.getExhibitsNamesID());

        adapter2.setPath(DijkstraShortestPath.findPathBetween
                (ZooData.loadZooGraphJSON(context, "zoo_graph.json"),
                        "entrance_exit_gate", "capuchin"));

        Map<String, Exhibit> mapExhibits =  ze.nameToVertexMap();
        totalExhibits = new ArrayList<>();

        for(Map.Entry<String, Exhibit> curr: mapExhibits.entrySet()){
            totalExhibits.add(curr.getValue());
        }

        customAdapter = new mockExhibitSelectAdapter(context, totalExhibits);
    }


    @Test
    public void testDetailed() {

        totalExhibits.get(2).setSelected(true);
        boolean test = totalExhibits.get(2).getIsSelected();

        System.out.println("Selected Exhibit: " + totalExhibits.get(2).getName());

        //search user story
        assertEquals(true, test);

        //plan user story
        assertEquals((Integer) 8400, adapter.exhibitsDistFromStart.get("capuchin"));

        List<IdentifiedWeightedEdge> edges = adapter2.path.getEdgeList();

        //simple directions user story
        String simple_paths0 = edges.get(0).toString();
        assertEquals("(entrance_exit_gate :gate_to_front: intxn_front_treetops)", simple_paths0);

        String simple_paths1 = edges.get(1).toString();
        assertEquals("(intxn_front_treetops :front_to_monkey: intxn_front_monkey)", simple_paths1);



        SettingsStaticClass.detailed = true; //set to detailed directions
        adapter2.directionsType = new DetailedDirections();



        List<IdentifiedWeightedEdge> edges2 = adapter2.path.getEdgeList();


        //detailed directions user story
        String detailed_paths0 = edges2.get(0).toString();
        assertEquals("(entrance_exit_gate :gate_to_front: intxn_front_treetops)", detailed_paths0);

        String detailed_paths1 = edges2.get(1).toString();
        assertEquals("(intxn_front_treetops :front_to_monkey: intxn_front_monkey)", detailed_paths1);

    }
}
