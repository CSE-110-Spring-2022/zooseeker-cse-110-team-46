package edu.ucsd.cse110.zooseeker46;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectionsActivity extends AppCompatActivity {

    //Array for testing -- not actual animal values
    private int counter = 1; //current position in array

    ExhibitSelectAdapter exhibitSelectAdapter;
    private ArrayList<String> selectedList;
    private Set<String> selected;

    //private ArrayList<String> finalAnimalNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);


        //load zoo graph and places
        Graph<String, IdentifiedWeightedEdge> zoo = ZooData.loadZooGraphJSON(this, "sample_zoo_graph.json");
        Map<String,ZooData.VertexInfo> places = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        //Create an ArrayList with the selected animals' names
        exhibitSelectAdapter = SearchActivity.getCustomAdapter();
        selected = exhibitSelectAdapter.selectedExhibits;
        selectedList = new ArrayList<>(selected);

        Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();

        //get the hashmap of animals/location
        for(int i = 0; i < selectedList.size(); i++) {
            placesToVisit.put(selectedList.get(i), places.get(selectedList.get(i)));
        }


        //Find shortest path with Directions object
        Directions d = new Directions(placesToVisit, zoo);
        d.finalListOfPaths();
        List<GraphPath<String,IdentifiedWeightedEdge>> finalPath = d.getFinalPath();

        //Set text to first array value
        TextView testText = findViewById(R.id.testText);
        TextView testDirection = findViewById(R.id.testDirection);
        testText.setText(finalPath.get(counter).getStartVertex());
        testDirection.setText("test");

        for(int i = 0; i < finalPath.size(); i++) {
            //finalAnimalNames.add(finalPath.get(i).getStartVertex());
        }
    }

    public void onNextButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);

        //update the textViews if we're still in the array
        if(counter < selectedList.size()){
            counter++;
            testText.setText(selectedList.get(counter));
        }

    }

    public void onPrevButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);

        //update the textViews if we're still in the array
        if(counter > 1){
            counter--;
            testText.setText(selectedList.get(counter));
        }

    }


}
