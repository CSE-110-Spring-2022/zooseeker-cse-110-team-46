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
import java.util.Set;

public class PlanActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    //This exhibit list is temporary
    //should be replaced by a list given by the previous Activity of what was accepted by user
    private ArrayList<String> testExhibitList;
    private ArrayList<Exhibit> exhibitArrayList;
    private ArrayList<String> idList;
    private ArrayList<String> selectedList;
    private Set<String> selected;
    ZooExhibits zoo;
    ExhibitSelectAdapter exhibitSelectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
        //sets the graph, nodes, and edges. May be better as database?
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(this, "sample_edge_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        //testing
        Log.d("graph", adapter.exhibitsGraph.toString());


        recyclerView = findViewById(R.id.planned_exhibits_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        zoo = new ZooExhibits(adapter.exhibitsVertex);
        exhibitArrayList = zoo.getExhibits();
        exhibitSelectAdapter = SearchActivity.getCustomAdapter();
        selected = exhibitSelectAdapter.selectedExhibits;
        selectedList = new ArrayList<>(selected);
        idList = zoo.getIDList(selectedList);
        //temporary till actual implementation of checking exhibit
        testExhibitList = new ArrayList<>();
        testExhibitList.add("elephant_odyssey");
        testExhibitList.add("gators");
        testExhibitList.add("arctic_foxes");

        adapter.setExhibits(idList);
    }

    public void onDirectionsButtonClicked(View view) {
        Utilities.showAlert(this, "This button doesn't do anything yet!");
    }
}