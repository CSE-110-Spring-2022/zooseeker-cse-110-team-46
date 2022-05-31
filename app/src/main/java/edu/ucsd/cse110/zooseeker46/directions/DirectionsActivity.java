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
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.MockLocation;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.SettingsStaticClass;
import edu.ucsd.cse110.zooseeker46.Utilities;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.ZooExhibits;

import edu.ucsd.cse110.zooseeker46.database.StatusDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Status;
import edu.ucsd.cse110.zooseeker46.plan.PlanActivity;
import edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;

import edu.ucsd.cse110.zooseeker46.tracking.Coord;
import edu.ucsd.cse110.zooseeker46.tracking.MockDirections;
import edu.ucsd.cse110.zooseeker46.tracking.TrackingStatic;

import edu.ucsd.cse110.zooseeker46.SettingsActivity;
import edu.ucsd.cse110.zooseeker46.visitor;


public class DirectionsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    DirectionsAdapter adapter;

    public int getCounter() {
        return counter;
    }

    private int counter = 0;
    List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    Map<String, ZooData.VertexInfo> vertexForNames;
    List<String> exhibitNamesID;
    int numPlaces;

    visitor visitor;
    List<String> visitedExhibits;
    List<String> remainingExhibits;
    Map<String,ZooData.VertexInfo> nextExhibits;
    Map<String,ZooData.VertexInfo> prevExhibits;
    Directions d;
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

        TrackingStatic.zoo = zoo;
        TrackingStatic.places = places;

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
        visitor = new visitor();
        visitor.setCurrentNode(places.get("entrance_exit_gate"));
        TrackingStatic.visitor = visitor;
        //Find shortest path with Directions object
        d = new Directions(placesToVisit, zoo);
        d.setStartID("entrance_exit_gate");
        d.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        d.finalListOfPaths();
        finalPath = d.getFinalPath();
        exhibitNamesID = d.getExhibitsNamesID();
        numPlaces= exhibitNamesID.size();
        Log.d("numPlaces", "onCreate: " + numPlaces);
        TrackingStatic.exhibitNamesIDs = exhibitNamesID;
        TrackingStatic.finalPath = finalPath;

        visitedExhibits = new ArrayList<>();
        remainingExhibits = new ArrayList<>();
        remainingExhibits.addAll(d.getExhibitToVisitWO().keySet());
        TrackingStatic.visitedExhibits = visitedExhibits;
        TrackingStatic.remainingExhibits = remainingExhibits;

        //remainingExhibits.remove(remainingExhibits.size()-1);

        //recycler view
        recyclerView = findViewById(R.id.directions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("Final Path", finalPath.toString());

        //set adapter
        if(vertexForNames.get(exhibitNamesID.get(counter)).group_id != null ){
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
            adapter.setDirectionsType(new DetailedDirections());
        else
            adapter.setDirectionsType(new SimpleDirections());

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
        SharedPreferences sharedPrefActivity = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        int tempCount = sharedPrefActivity.getInt("counter", 0);

        // If we already had the directions open at a previous point
        if(activatedDirectionsPrev && !onExhibitPrevious.equals("")){
            Log.d("In activatedDirectionsPrev, previous was: ", onExhibitPrevious);
            TextView animalTextcurr = findViewById(R.id.animalView);
            String animalCurr = animalTextcurr.getText().toString();

            Button nextButton = findViewById((R.id.next_btn));

            TextView endText = findViewById(R.id.endText);
            String endString = endText.getText().toString();

            //while(!animalCurr.equals(onExhibitPrevious) || !endString.equals(onExhibitPrevious)){
            for (int i = 0; i != tempCount; i++ ) {
                nextButton.performClick();
                animalTextcurr = findViewById(R.id.animalView);
                animalCurr = animalTextcurr.getText().toString();
                endText = (findViewById(R.id.endText));
                endString = endText.getText().toString();
                Log.d("animalTextCurr: ", animalCurr);
                Log.d("endString: ", endString);
            }
        }
        else {

            // First instance of the directions being called

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
            adapter.setDirectionsType(new DetailedDirections());
        else
            adapter.setDirectionsType(new SimpleDirections());

        recyclerView = findViewById(R.id.directions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        visitor = TrackingStatic.visitor;
        finalPath = TrackingStatic.finalPath;
        exhibitNamesID = TrackingStatic.exhibitNamesIDs;
        System.out.println(finalPath);
        adapter.setPath(DijkstraShortestPath.findPathBetween(TrackingStatic.zoo, visitor.getCurrentNode().id, exhibitNamesID.get(counter)));
        recyclerView.setAdapter(adapter);
    }

    public void onNextButtonClicked(View view) {
        String currPage = "";
        SharedPreferences sharedPrefActivity = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        //now get Editor
        SharedPreferences.Editor editor= sharedPrefActivity.edit();
        //update the textViews if we're still in the array
        if(counter < numPlaces-1){

            //load animal text
            TextView animalText = findViewById(R.id.animalView);
            nextExhibits = d.ExhibitsToMap(TrackingStatic.remainingExhibits);
            nextExhibits.remove(exhibitNamesID.get(counter));


            //update counter and the text on screen
            counter++;
            TrackingStatic.counter = counter;
            /*
            d.setStartID(visitor.getCurrentNode().id);
            nextExhibits.remove("entrance_exit_gate");
            d.setExhibitsToVisit(nextExhibits);
            d.finalListOfPaths();
            this.finalPath = d.getFinalPath();
            TrackingStatic.finalPath = finalPath;
             */

            setAdapter();
            animalText.setText(vertexForNames.get(exhibitNamesID.get(counter)).name);
            //your moving through already visited
            if(counter < visitedExhibits.size()){
                adapter.setPath(DijkstraShortestPath.findPathBetween(TrackingStatic.zoo, visitor.getCurrentNode().id, visitedExhibits
                        .get(counter)));
            }
            //your moving through not visited yet
            else {
                this.remainingExhibits = new ArrayList<>();
                this.remainingExhibits.addAll(nextExhibits.keySet());
                if(visitedExhibits.size() != counter) {
                    this.visitedExhibits.add(exhibitNamesID.get(counter-1));
                }
                TrackingStatic.remainingExhibits = remainingExhibits;
                TrackingStatic.visitedExhibits = visitedExhibits;
                Log.d("visited/remaining", "onNextButtonClicked: visitied:"+visitedExhibits +" remaining:"+remainingExhibits);
                //your going back to gate
                    if(counter == numPlaces-1) {
                        adapter.setPath(DijkstraShortestPath.findPathBetween(TrackingStatic.zoo, visitor.getCurrentNode().id, "entrance_exit_gate"));
                    }
                    else {
                        adapter.setPath(DijkstraShortestPath.findPathBetween(TrackingStatic.zoo, visitor.getCurrentNode().id, remainingExhibits
                                .get(0)));
                    }
            }
            recyclerView.setAdapter(adapter);

            currPage = vertexForNames.get(exhibitNamesID.get(counter)).name;
        }

        //show end text if we've reached the end
        else if(counter == numPlaces-1) {

            TextView endText = findViewById(R.id.endText);
            Button nextButton = findViewById((R.id.next_btn));
            TextView animalText = findViewById(R.id.animalView);
            recyclerView = findViewById(R.id.directions_recycler);

            //update counter
            counter++;
            TrackingStatic.counter = counter;

            //Update visibilities
            endText.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
            animalText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            currPage = endText.getText().toString();
            editor.putBoolean("onDir", false);
        }
        Log.d ("count", String.valueOf(counter));

        Log.d("NEXT BTN: onExhibit var changed: ", currPage);
        // Create object of SharedPreferences.
        SharedPreferences sharedPrefExhibitOn = this.getSharedPreferences("onExhibitDir", MODE_PRIVATE);
        //now get Editor
        SharedPreferences.Editor editor2 = sharedPrefExhibitOn.edit();
        //put your value
        editor2.putString("onExhibit", currPage);
        //commits your edits
        editor2.commit();


        editor.putInt("counter", counter);
        //commits your edits
        editor.commit();

    }

    public void onPrevButtonClicked(View view) {
        String currPage = "";
        //show end text if we've reached the end
        if(counter == numPlaces) {

            TextView endText = findViewById(R.id.endText);
            Button nextButton = findViewById((R.id.next_btn));
            TextView animalText = findViewById(R.id.animalView);
            recyclerView = findViewById(R.id.directions_recycler);

            //update counter
            counter--;
            TrackingStatic.counter = counter;
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

            d.setStartID(visitor.getCurrentNode().id);
            prevExhibits = d.ExhibitsToMap(TrackingStatic.visitedExhibits);
            Log.d("prev", "onPrevButtonClicked: " + visitedExhibits.toString());
            /*d.setExhibitsToVisit(prevExhibits);
            d.finalListOfPaths();
            finalPath = d.getFinalPath();
             */

            //update counter and the text on screen
            counter--;
            TrackingStatic.counter = counter;
            setAdapter();
            animalText.setText(vertexForNames.get(visitedExhibits.get(counter)).name);

            adapter.setPath(DijkstraShortestPath.findPathBetween(TrackingStatic.zoo, visitor.getCurrentNode().id, visitedExhibits.get(counter)));
            recyclerView.setAdapter(adapter);
        }
            Log.d ("count", String.valueOf(counter));
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

        SharedPreferences sharedPrefActivity = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        //now get Editor
        SharedPreferences.Editor editor= sharedPrefActivity.edit();
        editor.putInt("counter", counter);
        //commits your edits
        editor.commit();

    }


    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(DirectionsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


    public void setAdapter(){
        //set adapter
        if(vertexForNames.get(exhibitNamesID.get(counter)).group_id != null ){
            adapter = new DirectionsGroupAdapter();
        }
        else{
            adapter = new DirectionsAdapter();
        }

        adapter.setExhibitsGraph(ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json"));
        adapter.setExhibitsEdge(ZooData.loadEdgeInfoJSON(this, "sample_edge_info.json"));
        adapter.setExhibitsVertex(ZooData.loadVertexInfoJSON(this, "sample_node_info.json"));
        adapter.setEnd(d.getExhibitsNamesID().get(0));
        //adapter.setDirectionsType(new DetailedDirections());
        if(SettingsStaticClass.detailed)
            adapter.setDirectionsType(new DetailedDirections());
        else
            adapter.setDirectionsType(new SimpleDirections());

        recyclerView.setAdapter(adapter);
    }

    public List<String> getExhibitNamesID() {
        return exhibitNamesID;
    }

    public void onMockButtonClicked(View view) {
        Intent intent = new Intent(this, MockLocation.class);
        startActivity(intent);
    }
}
