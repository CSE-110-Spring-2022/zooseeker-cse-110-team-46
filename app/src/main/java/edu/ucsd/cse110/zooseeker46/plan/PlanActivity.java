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
import java.util.Set;
;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.SettingsActivity;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.ZooExhibits;
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
        //Intent intent = new Intent(PlanActivity.this, DirectionsActivity.class);
        Intent intent = new Intent(PlanActivity.this, DirectionsActivity.class);
        startActivity(intent);
    }

    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(PlanActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}