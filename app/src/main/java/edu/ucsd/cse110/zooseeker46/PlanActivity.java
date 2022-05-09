package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {
    private String count;
    private Context context;
    public RecyclerView recyclerView;
    //This exhibit list is temporary
    //should be replaced by a list given by the previous Activity of what was accepted by user
    private ArrayList<String> testExhibitList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle extras = getIntent().getExtras();
        this.count = extras.getString("exhibit_count");
        TextView countView = findViewById(R.id.selected_exhibit_count);
        countView.setText(String.valueOf(count));


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

        //temporary till actual implementation of checking exhibit
        testExhibitList = new ArrayList<>();
        testExhibitList.add("elephant_odyssey");
        testExhibitList.add("gators");
        testExhibitList.add("arctic_foxes");

        adapter.setExhibits(testExhibitList);
        /*
        int count = testExhibitList.size();
        TextView countView = findViewById(R.id.exhibit_count);
        countView.setText(String.valueOf(count));
        */
    }

    public void onDirectionsButtonClicked(View view) {
        Utilities.showAlert(this, "This button doesn't do anything yet!");
    }
}