package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectionsV2Activity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public DirectionsAdapter adapter = new DirectionsAdapter();

    private int counter = 0;
    List<GraphPath<String,IdentifiedWeightedEdge>> finalPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_v2);

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

        //Find shortest path with Directions object
        Directions d = new Directions(placesToVisit, zoo);
        d.finalListOfPaths();
        finalPath = d.getFinalPath();

        recyclerView = findViewById(R.id.directions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(finalPath);
        adapter.setDirections(finalPath.get(counter));
        recyclerView.setAdapter(adapter);

        //Current animal text
        TextView animalText = findViewById(R.id.animalView);
        animalText.setText(finalPath.get(counter).getEndVertex());
    }

    public void onNextButtonClicked(View view) {

        //update the textViews if we're still in the array
        if(counter < finalPath.size() - 1){

            //load animal text
            TextView animalText = findViewById(R.id.animalView);

            //update counter and the text on screen
            counter++;
            animalText.setText(finalPath.get(counter).getEndVertex());

            adapter.setDirections(finalPath.get(counter));
            recyclerView.setAdapter(adapter);
        }

    }

    public void onPrevButtonClicked(View view) {

        //update the textViews if we're still in the array
        if(counter > 0){

            //load textViews
            TextView animalText = findViewById(R.id.animalView);

            //update counter and the text on screen
            counter--;
            animalText.setText(finalPath.get(counter).getEndVertex());

            adapter.setDirections(finalPath.get(counter));
            recyclerView.setAdapter(adapter);
        }

    }
}