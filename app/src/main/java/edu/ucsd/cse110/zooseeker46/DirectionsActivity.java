package edu.ucsd.cse110.zooseeker46;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DirectionsActivity extends AppCompatActivity {

    //Array for testing -- not actual animal values
    private int counter = 1; //current position in array

    ExhibitSelectAdapter exhibitSelectAdapter;
    private ArrayList<String> selectedList;
    private Set<String> selected;

    Graph<String, IdentifiedWeightedEdge> zoo;
    Map<String,ZooData.VertexInfo> places;

    private ArrayList<String> idList;
    ZooExhibits zoo2;

    //private ArrayList<String> finalAnimalNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);


        //load zoo graph and places
        zoo = ZooData.loadZooGraphJSON(this, "sample_zoo_graph.json");
        places = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        //Create an ArrayList with the selected animals' names
        exhibitSelectAdapter = SearchActivity.getCustomAdapter();
        selected = exhibitSelectAdapter.selectedExhibits;
        selectedList = new ArrayList<>(selected);

        Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();

        idList = zoo2.getIDList(selectedList);

        //get the hashmap of animals/location
        for(int i = 0; i < selectedList.size(); i++) {
            placesToVisit.put(idList.get(i), places.get(selectedList.get(i)));
        }
/*
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

*/WA
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
