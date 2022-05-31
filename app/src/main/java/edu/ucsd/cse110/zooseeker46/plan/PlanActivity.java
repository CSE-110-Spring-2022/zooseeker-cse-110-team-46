package edu.ucsd.cse110.zooseeker46.plan;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.SettingsActivity;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.ZooExhibits;
import edu.ucsd.cse110.zooseeker46.database.ExhibitDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.directions.Directions;
import edu.ucsd.cse110.zooseeker46.directions.DirectionsActivity;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;

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
    ZooDataDatabase zb;
    ExhibitDao exhibitDao;
    Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();
    Directions d;


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

        Log.d("graph", adapter.exhibitsGraph.toString());


        recyclerView = findViewById(R.id.planned_exhibits_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        zoo = new ZooExhibits(adapter.exhibitsVertex);
        ZooDataDatabase zb = ZooDataDatabase.getSingleton(this.context);
        exhibitArrayList = (ArrayList<Exhibit>) zb.exhibitDao().getAll();
        //exhibitArrayList = zoo.getExhibits();
        exhibitSelectAdapter = SearchActivity.getCustomAdapter();
//        selected = exhibitSelectAdapter.selectedExhibits;
        selectedList = new ArrayList<>();
        List<Exhibit> selectedExhibits;
        selectedExhibits = zb.exhibitDao().getSelectedExhibits();
        idList = new ArrayList<>();
        //idList = zoo.getIDList(selectedList);
        for (Exhibit curr : selectedExhibits) {
            selectedList.add(curr.getName());
            idList.add(curr.getId());
        }
        Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();

        //get the hashmap of animals/location
        for(int i = 0; i < selectedList.size(); i++) {
            placesToVisit.put(idList.get(i), adapter.exhibitsVertex.get(selectedList.get(i)));
        }
        d = new Directions(placesToVisit, adapter.exhibitsGraph);
        d.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        d.finalListOfPaths();
        if (selectedList.size() != 0) {
            adapter.orderPlan(d.getFinalPath(), d.getExhibitsNamesID());
        }

    }

    public void onDirectionsButtonClicked(View view) {
        //Intent intent = new Intent(PlanActivity.this, DirectionsActivity.class);
        Intent intent = new Intent(PlanActivity.this, DirectionsActivity.class);
        startActivity(intent);
    }

    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(PlanActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}