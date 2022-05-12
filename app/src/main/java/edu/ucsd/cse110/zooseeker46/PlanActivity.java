package edu.ucsd.cse110.zooseeker46;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

public class PlanActivity extends AppCompatActivity {
    private String count;
    private Context context;
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
<<<<<<< HEAD
        Intent intent = new Intent(PlanActivity.this, DirectionsActivity.class);
=======
        Intent intent = new Intent(PlanActivity.this, DirectionsV2Activity.class);
>>>>>>> directionsRecyclerView
        startActivity(intent);
    }
}