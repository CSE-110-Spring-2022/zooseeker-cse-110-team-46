package edu.ucsd.cse110.zooseeker46;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DirectionsActivity extends AppCompatActivity {

    //Array for testing -- not actual animal values
    private String[] testList = new String[]{"Alligators", "Arctic Foxes", "Gorillas", "Ur mom", "You have completed your path!"};
    private String[] testDirections = new String[]{"To your left", "to your right", "above you", "lmao", "Congratulations"};
    private int counter = 0; //current position in array

    //Graph<String, IdentifiedWeightedEdge> zoo = ZooData.loadZooGraphJSON(this, "sample_zoo_graph.json");
    //Map<String,ZooData.VertexInfo> places = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
    Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();

    ExhibitSelectAdapter exhibitSelectAdapter;
    private ArrayList<String> selectedList;
    private Set<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //Set text to first array value
        TextView testText = findViewById(R.id.testText);
        TextView testDirection = findViewById(R.id.testDirection);
        testText.setText(testList[counter]);
        testDirection.setText(testDirections[counter]);

        PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        //Create an ArrayList with the selected animals' names
        exhibitSelectAdapter = SearchActivity.getCustomAdapter();
        selected = exhibitSelectAdapter.selectedExhibits;
        selectedList = new ArrayList<>(selected);

        for(int i = 0; i < selectedList.size(); i++) {
            Log.d("bruh", selectedList.get(i));
        }


        //get the hashmap of animals/location
        for(int i = 0; i < selectedList.size(); i++) {
            placesToVisit.put(selectedList.get(i), adapter.exhibitsVertex.get(selectedList.get(i)));
        }


        //for(String i : placesToVisit.keySet()) {
            //Log.d("bruh", i);
            //Log.d("bruh2", placesToVisit.get(i).toString());
        //}


        //Find shortest path with Directions object
        //Directions d = new Directions(placesToVisit, zoo);
        //d.finalListOfPaths();
        //List<GraphPath<String,IdentifiedWeightedEdge>> finalPath = d.getFinalPath();

        //testText.setText(finalPath.get(counter).getStartVertex());
    }

    public void onNextButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);
        //TextView testDirection = findViewById(R.id.testDirection);

        //update the textViews if we're still in the array
        if(counter < testList.length){
            counter++;
            testText.setText(testList[counter]);
            //testDirection.setText(testDirections[counter]);
        }

    }

    public void onPrevButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);
        //TextView testDirection = findViewById(R.id.testDirection);

        //update the textViews if we're still in the array
        if(counter > 0){
            counter--;
            testText.setText(testList[counter]);
            //testDirection.setText(testDirections[counter]);
        }

    }
}
