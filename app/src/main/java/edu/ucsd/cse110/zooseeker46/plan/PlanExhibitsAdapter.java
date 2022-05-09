package edu.ucsd.cse110.zooseeker46.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.ZooData;

/*
    Adapter for recycler view to use an exhibit_item
 */
public class PlanExhibitsAdapter extends RecyclerView.Adapter<PlanExhibitsAdapter.ViewHolder> {
    //public now for testing maybe should make it private?
    //the graph, vertex, and edge may be better as a database?
    public Graph<String, IdentifiedWeightedEdge> exhibitsGraph;
    public Map<String, ZooData.VertexInfo> exhibitsVertex;
    public Map<String, ZooData.EdgeInfo> exhibitsEdge;
    private List<String> keyExhibits = new ArrayList<>();
    public Map<String, Integer> exhibitsEntrance = new HashMap<>();
    public Map<String, String> exhibitsStreet = new HashMap<>();

    public void setExhibits(List<String> newExhibits) {
        this.keyExhibits.clear();
        for(String name: newExhibits){
            findDistanceFromEntrance(name);
        }
        //sorting map source: https://www.baeldung.com/java-hashmap-sort
        exhibitsEntrance = exhibitsEntrance.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue,newValue) -> oldValue, LinkedHashMap::new));
        System.out.println(exhibitsGraph);
        //exhibits array is now ordered shortest distance from entrance to longest
        this.keyExhibits = new ArrayList<String>(exhibitsEntrance.keySet());
    }

    public List<String> getExhibitsPlan(){
        return keyExhibits;
    }

    public void findDistanceFromEntrance(String end){
        String start = "entrance_exit_gate";
        //finds shortest path from entrance to exhibit
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween
                (exhibitsGraph, start, end);
        int total = 0;
        IdentifiedWeightedEdge eL = null;
        //finds total length of path
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            total = total + (int)exhibitsGraph.getEdgeWeight(e);
            eL = e;
        }
        //street of exhibit is the last edge in path
        String street = eL.getId();
        this.exhibitsStreet.put(end, street);
        this.exhibitsEntrance.put(end, total);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //default view in a 1d list
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibt_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanExhibitsAdapter.ViewHolder holder, int position) {
        //puts data in the viewHolder
        TextView nameTextView = holder.getNameTextView();
        TextView streetTextView = holder.getStreetTextView();
        nameTextView.setText(exhibitsVertex.get(keyExhibits.get(position)).name);
        streetTextView.setText
                (exhibitsEdge.get(exhibitsStreet.get(keyExhibits.get(position))).street + ", " +
                        exhibitsEntrance.get(keyExhibits.get(position))+ "m");
        //holder.setExhibit(keyExhibits.get(position));
    }

    @Override
    public int getItemCount() {
        return keyExhibits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameTextView;
        private final TextView streetTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.exhibit_name_text);
            this.streetTextView = itemView.findViewById(R.id.exhibit_location_text);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }
        public TextView getStreetTextView() {
            return streetTextView;
        }
    }
}
