package edu.ucsd.cse110.zooseeker46;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.ucsd.cse110.zooseeker46.directions.Directions;
import edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;

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
    private String[] testList = new String[]{"Alligators", "Arctic Foxes", "Gorillas", "Ur mom", "You have completed your path!"};
    private String[] testDirections = new String[]{"To your left", "to your right", "above you", "lmao", "Congratulations"};
    private int counter = 0; //current position in array

    Graph<String, IdentifiedWeightedEdge> zoo;
    Map<String,ZooData.VertexInfo> places;
    Map<String,ZooData.VertexInfo> placesToVisit = new HashMap<>();

    ExhibitSelectAdapter exhibitSelectAdapter;
    private ArrayList<String> selectedList;
    private Set<String> selected;

    private ArrayList<String> idList;
    ZooExhibits zooExhibit;

    List<GraphPath<String,IdentifiedWeightedEdge>> finalPath;
    List<IdentifiedWeightedEdge> edgeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);



        zoo = ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json");
        places = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        //Create an ArrayList with the selected animals' names
        exhibitSelectAdapter = SearchActivity.getCustomAdapter();
        selected = exhibitSelectAdapter.selectedExhibits;
        selectedList = new ArrayList<>(selected);

        zooExhibit = new ZooExhibits(places);
        idList = zooExhibit.getIDList(selectedList);

        //get the hashmap of animals/location
        for(int i = 0; i < idList.size(); i++) {
            placesToVisit.put(idList.get(i), places.get(idList.get(i)));
        }


        for(String i : placesToVisit.keySet()) {
            Log.d("bruh", i);
            Log.d("bruh2", placesToVisit.get(i).toString());
        }


        //Find shortest path with Directions object
        Directions d = new Directions(placesToVisit, zoo);
        d.finalListOfPaths();
        finalPath = d.getFinalPath();

        //Set text to first array value
        TextView testText = findViewById(R.id.testText);
        TextView testDirection = findViewById(R.id.testDirection);

        testDirection.setText("Directions");
        testText.setText(finalPath.get(counter).getEndVertex());

        edgeList = finalPath.get(0).getEdgeList();
        testDirection.setText(edgeList.get(counter).toString());

    }

    public void onNextButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);

        //update the textViews if we're still in the array
        if(counter < selectedList.size()){
            counter++;
            //testText.setText(testList[counter]);
            testText.setText(finalPath.get(counter).getEndVertex());
        }

    }

    public void onPrevButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);

        //update the textViews if we're still in the array
        if(counter > 0){
            counter--;
            //testText.setText(testList[counter]);
            testText.setText(finalPath.get(counter).getEndVertex());
        }

    }
}
