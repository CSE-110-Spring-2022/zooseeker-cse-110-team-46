package edu.ucsd.cse110.zooseeker46.plan;

import android.util.Log;
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
    public Map<String, Integer> exhibitsDistFromStart = new HashMap<>();
    public Map<String, String> exhibitsStreet = new HashMap<>();
    List<GraphPath<String, IdentifiedWeightedEdge>> finalPath;
    List<String> exhibitNamesID;

    public void OrderPlan(List<GraphPath<String, IdentifiedWeightedEdge>> finalPath,List<String> exhibitNamesID) {
        this.finalPath = finalPath;
        this.exhibitNamesID = exhibitNamesID;
        this.keyExhibits.clear();
        distanceFromStartMap();

        //sorting map source: https://www.baeldung.com/java-hashmap-sort
        exhibitsDistFromStart = exhibitsDistFromStart.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue,newValue) -> oldValue, LinkedHashMap::new));
        Log.d("exhibit from start", "distanceFromStartMap: " + exhibitsDistFromStart.toString());
        Log.d("exhibit street", "exhibit street: " + exhibitsStreet.toString());
        //exhibits array is now ordered shortest distance from entrance to longest
        this.keyExhibits = new ArrayList<String>(exhibitsDistFromStart.keySet());
        Log.d("exhibit keys", keyExhibits.toString());
    }

    public List<String> getExhibitsPlan(){
        return keyExhibits;
    }

    public void distanceFromStartMap(){
        int initialDist = 0;
        int i = 0;
        String street;
        int indxOfPath;
        Log.d("elements", exhibitNamesID.toString());
        while(i < exhibitNamesID.size()){
            //case of exhibit groups
            if(exhibitsVertex.get(exhibitNamesID.get(i)).parent_id != null){
                initialDist = initialDist + (int)finalPath.get(i).getWeight();
                exhibitsDistFromStart.put(exhibitNamesID.get(i),initialDist);
                indxOfPath = finalPath.get(i).getEdgeList().size()-1;
                //if group was already visited
                if(indxOfPath == -1){
                    street = exhibitsStreet.get(exhibitNamesID.get(i-1));
                }
                //first time visiting group
                else {
                    street = exhibitsEdge.get(finalPath.get(i).getEdgeList().get(indxOfPath).getId()).street;
                }
                exhibitsStreet.put(exhibitNamesID.get(i), street);
                i++;
                continue;
            }
            //case of an exhibit
            initialDist = initialDist + (int)finalPath.get(i).getWeight();
            exhibitsDistFromStart.put(exhibitNamesID.get(i),initialDist);
            indxOfPath = finalPath.get(i).getEdgeList().size() - 1;
            street = exhibitsEdge.get(finalPath.get(i).getEdgeList().get(indxOfPath).getId()).street;
            exhibitsStreet.put(exhibitNamesID.get(i), street);
            i++;
        }
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
        String street = exhibitsStreet.get(keyExhibits.get(position));
        int distance  = exhibitsDistFromStart.get(keyExhibits.get(position));
        TextView nameTextView = holder.getNameTextView();
        TextView streetTextView = holder.getStreetTextView();
        nameTextView.setText(exhibitsVertex.get(keyExhibits.get(position)).name);
        streetTextView.setText
                ( street + ", " +
                        distance + "m");
        //holder.setExhibit(keyExhibits.get(position));
    }

    @Override
    public int getItemCount() {
        //-1 to remove the entrance_exit_gate
        return keyExhibits.size()-1;
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
