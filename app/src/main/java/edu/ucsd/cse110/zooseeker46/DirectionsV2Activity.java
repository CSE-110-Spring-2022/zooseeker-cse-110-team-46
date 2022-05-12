package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_v2);
        DirectionsAdapter adapter = new DirectionsAdapter();

        adapter.exhibitsGraph = ZooData.loadZooGraphJSON(this,"sample_zoo_graph.json");
        adapter.exhibitsEdge = ZooData.loadEdgeInfoJSON(this, "sample_edge_info.json");
        adapter.exhibitsVertex = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

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
        List<GraphPath<String,IdentifiedWeightedEdge>> finalPath = d.getFinalPath();

        recyclerView = findViewById(R.id.directions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(finalPath);
        adapter.setDirections(finalPath.get(0));
        recyclerView.setAdapter(adapter);
    }
}