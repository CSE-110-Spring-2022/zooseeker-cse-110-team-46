package edu.ucsd.cse110.zooseeker46.directions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import edu.ucsd.cse110.zooseeker46.database.StatusDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Status;
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
    public static String STATE_USER = "user";
    public static String mUser;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_USER, mUser);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SettingsStaticClass.onDirections = true;
//        Log.d("Boolean CHANGED for onDirections: ", String.valueOf(SettingsStaticClass.onDirections));

        setContentView(R.layout.activity_directions);
        vertexForNames = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        //load zoo graph and places
        Graph<String, IdentifiedWeightedEdge> zoo = ZooData.loadZooGraphJSON(this, "sample_zoo_graph.json");
        Map<String, ZooData.VertexInfo> places = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        //Create an ArrayList with the selected animals' names
//        ExhibitSelectAdapter exhibitSelectAdapter = SearchActivity.getCustomAdapter();
//        Set<String> selected = exhibitSelectAdapter.selectedExhibits;
//        ArrayList<String> selectedList = new ArrayList<>(selected);

        Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();

//        ZooExhibits zoo2 = new ZooExhibits(places);
//        ArrayList<String> idList = zoo2.getIDList(selectedList);

        // Replaced with database
        ArrayList<String> selectedList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();

        ZooDataDatabase zb = ZooDataDatabase.getSingleton(this);
        StatusDao statusdao = zb.statusDao();
        Status onDir = new Status();
        onDir.setOnDirections();
        statusdao.insert(onDir);


        ArrayList<Exhibit> exhibitArrayList = (ArrayList<Exhibit>) zb.exhibitDao().getSelectedExhibits();
        for(Exhibit curr: exhibitArrayList){
            selectedList.add(curr.getName());
            idList.add(curr.getId());
        }

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

        if(SettingsStaticClass.detailed)
            adapter.directionsType = new DetailedDirections();
        else
            adapter.directionsType = new SimpleDirections();

        adapter.setPath(finalPath.get(counter));
        recyclerView.setAdapter(adapter);

        //Current animal text
        TextView animalText = findViewById(R.id.animalView);
        animalText.setText(vertexForNames.get(exhibitNamesID.get(counter)).name);

        Log.d("On Create for DirectionsActivity", vertexForNames.get(exhibitNamesID.get(counter)).name);

        SharedPreferences sharedPrefCheckDirActivity = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        boolean activatedDirectionsPrev = sharedPrefCheckDirActivity.getBoolean("onDir", false);


        SharedPreferences sharedPref3 = this.getSharedPreferences("onExhibitDir", MODE_PRIVATE);
        String onExhibitPrevious = sharedPref3.getString("onExhibit", "");

        // If we already had the directions open at a previous point
        if(activatedDirectionsPrev && !onExhibitPrevious.equals("")){
            Log.d("In activatedDirectionsPrev, previous was: ", onExhibitPrevious);
            TextView animalTextcurr = findViewById(R.id.animalView);
            Button nextButton = findViewById((R.id.next_btn));
            TextView endText = findViewById(R.id.endText);
            String endString = endText.getText().toString();
//            while(!animalTextcurr.equals(onExhibitPrevious) || !endString.equals(onExhibitPrevious)){
//                nextButton.performClick();
//                animalTextcurr = findViewById(R.id.animalView);
//                endText = findViewById(R.id.endText);
//            }
        }
        else {

            // First instance of the directions being called
            // Create object of SharedPreferences.
            SharedPreferences sharedPrefActivity = this.getSharedPreferences("onDirections", MODE_PRIVATE);
            //now get Editor
            SharedPreferences.Editor editor= sharedPrefActivity.edit();
            //put your value
            editor.putBoolean("onDir", true);
            //commits your edits
            editor.commit();
            Log.d("Boolean CHANGED for onDirections: ", String.valueOf(true));

            // Create object of SharedPreferences.
            SharedPreferences sharedPrefExhibitOn = this.getSharedPreferences("onExhibitDir", MODE_PRIVATE);
            //now get Editor
            SharedPreferences.Editor editor2 = sharedPrefExhibitOn.edit();
            //put your value
            editor2.putString("onExhibit", vertexForNames.get(exhibitNamesID.get(counter)).name);
            //commits your edits
            editor2.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(SettingsStaticClass.detailed)
            adapter.directionsType = new DetailedDirections();
        else
            adapter.directionsType = new SimpleDirections();

        recyclerView = findViewById(R.id.directions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(finalPath);
        adapter.setPath(finalPath.get(counter));
        recyclerView.setAdapter(adapter);
    }

    public void onNextButtonClicked(View view) {
        String currPage = "";

        //update the textViews if we're still in the array
        if(counter < finalPath.size() - 1){

            //load animal text
            TextView animalText = findViewById(R.id.animalView);
            Log.d("Next button clicked:", vertexForNames.get(exhibitNamesID.get(counter)).name);

            //update counter and the text on screen
            counter++;
            setAdapter();
            animalText.setText(vertexForNames.get(exhibitNamesID.get(counter)).name);;
            adapter.setPath(finalPath.get(counter));
            recyclerView.setAdapter(adapter);
            currPage = vertexForNames.get(exhibitNamesID.get(counter)).name;
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
            currPage = endText.getText().toString();
        }

        Log.d("NEXT BTN: onExhibit var changed: ", currPage);
        // Create object of SharedPreferences.
        SharedPreferences sharedPrefExhibitOn = this.getSharedPreferences("onExhibitDir", MODE_PRIVATE);
        //now get Editor
        SharedPreferences.Editor editor2 = sharedPrefExhibitOn.edit();
        //put your value
        editor2.putString("onExhibit", currPage);
        //commits your edits
        editor2.commit();

    }

    public void onPrevButtonClicked(View view) {
        String currPage = "";
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

            currPage = endText.getText().toString();
        }

        //update the textViews if we're still in the array
        else if(counter > 0){

            //load textViews
            TextView animalText = findViewById(R.id.animalView);

            //update counter and the text on screen
            counter--;
            setAdapter();
            animalText.setText(vertexForNames.get(exhibitNamesID.get(counter)).name);

            currPage = vertexForNames.get(exhibitNamesID.get(counter)).name;

            adapter.setPath(finalPath.get(counter));
            recyclerView.setAdapter(adapter);
        }

        Log.d("PREV BTN: onExhibit var changed: ", currPage);
        // Create object of SharedPreferences.
        SharedPreferences sharedPrefExhibitOn = this.getSharedPreferences("onExhibitDir", MODE_PRIVATE);
        //now get Editor
        SharedPreferences.Editor editor2 = sharedPrefExhibitOn.edit();
        //put your value
        editor2.putString("onExhibit", currPage);
        //commits your edits
        editor2.commit();

    }


    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(DirectionsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
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
