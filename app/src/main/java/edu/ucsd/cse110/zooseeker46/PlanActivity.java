package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    //This exhibit list is temporary
    private ArrayList<String> testExhibitList;
    public PlanExhibitsAdapter visA;
    //public Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
        //temp create setter later
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(this, "sample_edge_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        Log.d("graph", adapter.exhibitsGraph.toString());

        recyclerView = findViewById(R.id.planned_exhibits_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //temporary till actual implementation of ZooExhibits
        testExhibitList = new ArrayList<>();
        testExhibitList.add("elephant_odyssey");
        testExhibitList.add("gators");
        testExhibitList.add("arctic_foxes");
        testExhibitList.add("lions");
        testExhibitList.add("gorillas");

        adapter.setExhibits(testExhibitList);
        //visA = adapter;
    }

    public void onDirectionsButtonClicked(View view) {
        Utilities.showAlert(this, "This button doesn't do anything yet!");
    }
}