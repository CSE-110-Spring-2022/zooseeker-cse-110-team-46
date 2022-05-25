package edu.ucsd.cse110.zooseeker46.directions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.SettingsStaticClass;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.ZooExhibits;
import edu.ucsd.cse110.zooseeker46.plan.PlanActivity;
import edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;
import edu.ucsd.cse110.zooseeker46.SettingsActivity;

public class DirectionsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    DirectionsAdapter adapter;
    private int counter = 0;
    List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    Map<String, ZooData.VertexInfo> vertexForNames;
    List<String> exhibitNamesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        vertexForNames = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(this, "sample_edge_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        if(SettingsStaticClass.detailed)
            adapter.directions = new DetailedDirections();
        else
            adapter.directions = new SimpleDirections();


        //load zoo graph and places
        Graph<String, IdentifiedWeightedEdge> zoo = ZooData.loadZooGraphJSON(this, "sample_zoo_graph.json");
        Map<String, ZooData.VertexInfo> places = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        //Create an ArrayList with the selected animals' names
        ExhibitSelectAdapter exhibitSelectAdapter = SearchActivity.getCustomAdapter();
        Set<String> selected = exhibitSelectAdapter.selectedExhibits;
        ArrayList<String> selectedList = new ArrayList<>(selected);

        Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();

        ZooExhibits zoo2 = new ZooExhibits(places);
        ArrayList<String> idList = zoo2.getIDList(selectedList);

        //get the hashmap of animals/location
        for(int i = 0; i < selectedList.size(); i++) {
            placesToVisit.put(idList.get(i), places.get(selectedList.get(i)));
        }
        //placesToVisit.put("entrance_exit_gate", places.get("entrance_exit_gate"));

        //Find shortest path with Directions object
        Directions d = new Directions(placesToVisit, zoo);
        d.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        d.finalListOfPaths();
        finalPath = d.getFinalPath();
        exhibitNamesID = d.getExhibitsNamesID();

        //recycler view
        recyclerView = findViewById(R.id.directions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("Final Path", finalPath.toString());

        //set adapter
        if(vertexForNames.get(exhibitNamesID.get(counter)).parent_id != null ){
            adapter = new DirectionsGroupAdapter();
        }
        else{
            adapter = new DirectionsAdapter();
        }

        adapter.setExhibitsGraph(ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json"));
        adapter.setExhibitsEdge(ZooData.loadEdgeInfoJSON(this, "sample_edge_info.json"));
        adapter.setExhibitsVertex(ZooData.loadVertexInfoJSON(this, "sample_node_info.json"));
        adapter.setEnd(exhibitNamesID.get(counter));
        adapter.setDirectionsType(new DetailedDirections());

        adapter.setPath(finalPath.get(counter));
        recyclerView.setAdapter(adapter);

        //Current animal text
        TextView animalText = findViewById(R.id.animalView);
        animalText.setText(vertexForNames.get(exhibitNamesID.get(counter)).name);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(SettingsStaticClass.detailed)
            adapter.directions = new DetailedDirections();
        else
            adapter.directions = new SimpleDirections();

        recyclerView = findViewById(R.id.directions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(finalPath);
        adapter.setDirections(finalPath.get(counter));
        recyclerView.setAdapter(adapter);
    }

    public void onNextButtonClicked(View view) {

        //update the textViews if we're still in the array
        if(counter < finalPath.size() - 1){

            //load animal text
            TextView animalText = findViewById(R.id.animalView);

            //update counter and the text on screen
            counter++;
            setAdapter();
            animalText.setText(vertexForNames.get(exhibitNamesID.get(counter)).name);;
            adapter.setPath(finalPath.get(counter));
            recyclerView.setAdapter(adapter);
        }

        //show end text if we've reached the end
        else if(counter == finalPath.size() - 1) {

            TextView endText = findViewById(R.id.endText);
            Button nextButton = findViewById((R.id.next_btn));
            TextView animalText = findViewById(R.id.animalView);
            recyclerView = findViewById(R.id.directions_recycler);

            //update counter
            counter++;

            //Update visibilities
            endText.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
            animalText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    public void onPrevButtonClicked(View view) {

        //show end text if we've reached the end
        if(counter == finalPath.size()) {

            TextView endText = findViewById(R.id.endText);
            Button nextButton = findViewById((R.id.next_btn));
            TextView animalText = findViewById(R.id.animalView);
            recyclerView = findViewById(R.id.directions_recycler);

            //update counter
            counter--;
            //Update visibilities
            endText.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            animalText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        //update the textViews if we're still in the array
        else if(counter > 0){

            //load textViews
            TextView animalText = findViewById(R.id.animalView);

            //update counter and the text on screen
            counter--;
            setAdapter();
            animalText.setText(vertexForNames.get(exhibitNamesID.get(counter)).name);

            adapter.setPath(finalPath.get(counter));
            recyclerView.setAdapter(adapter);
        }

    }


    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(DirectionsActivity.this, SettingsActivity.class);
        startActivity(intent);
    public void setAdapter(){
        //set adapter
        if(vertexForNames.get(exhibitNamesID.get(counter)).parent_id != null ){
            adapter = new DirectionsGroupAdapter();
        }
        else{
            adapter = new DirectionsAdapter();
        }

        adapter.setExhibitsGraph(ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json"));
        adapter.setExhibitsEdge(ZooData.loadEdgeInfoJSON(this, "sample_edge_info.json"));
        adapter.setExhibitsVertex(ZooData.loadVertexInfoJSON(this, "sample_node_info.json"));
        adapter.setEnd(exhibitNamesID.get(counter));
        adapter.setDirectionsType(new DetailedDirections());

        recyclerView.setAdapter(adapter);
    }
}
