package edu.ucsd.cse110.zooseeker46.directions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.ZooData;

public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.ViewHolder>{
    public GraphPath<String, IdentifiedWeightedEdge> path = null;
    public Graph<String, IdentifiedWeightedEdge> exhibitsGraph;
    public Map<String, ZooData.VertexInfo> exhibitsVertex;
    public Map<String, ZooData.EdgeInfo> exhibitsEdge;
    public DirectionTypeInterface directionsType;
    public String end;
    boolean isReverse = false;

    public void setPath(GraphPath<String, IdentifiedWeightedEdge> path){
        this.path = path;
    }

    public void setReversed(boolean isReverse){
        this.isReverse = isReverse;
    }

    @NonNull
    @Override
    public DirectionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.directions_item, parent, false);
        return new DirectionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionsAdapter.ViewHolder holder, int position) {
        IdentifiedWeightedEdge pathLayout;
        TextView DirectionsTextView = holder.getDirectionTextView();
        List<IdentifiedWeightedEdge> edges = path.getEdgeList();
        if (isReverse) {
            position = path.getLength()-1 - position;
        }
        pathLayout = edges.get(position);
        String startName = directionsType.getStartName(exhibitsVertex, pathLayout);
        String endName = directionsType.getEndName(exhibitsVertex, pathLayout);
        String streetName = directionsType.getStreetName(exhibitsEdge, pathLayout);
        //check if in correct order
        //if not switch
        if (position <= path.getLength()-1 && position+1 < path.getLength()) {
            if (startName == exhibitsVertex.get(edges.get(position + 1).getSourceName()).name ||
                    startName == exhibitsVertex.get(edges.get(position + 1).getTargetName()).name) {
                String tempName = startName;
                startName = endName;
                endName = tempName;
            }
            if(isReverse){
                String tempName = startName;
                startName = endName;
                endName = tempName;
            }
        }
        if(position == path.getLength()-1 && position-1 > -1){
            if (endName == exhibitsVertex.get(edges.get(position - 1).getSourceName()).name ||
                    endName == exhibitsVertex.get(edges.get(position - 1).getTargetName()).name) {
                String tempName = startName;
                startName = endName;
                endName = tempName;
            }
            if(isReverse){
                String tempName = startName;
                startName = endName;
                endName = tempName;
            }
        }
        if(position == path.getLength()-1 && position+1 == path.getLength()){
            if(startName == exhibitsVertex.get(end).name){
                String tempName = startName;
                startName = endName;
                endName = tempName;
            }
        }
        int length = directionsType.getLength(exhibitsGraph, pathLayout);
        DirectionsTextView.setText(directionsType.pathFormat(startName, endName, streetName, length));
    }

    @Override
    public int getItemCount() {
        return path.getLength();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView directionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.directionTextView = itemView.findViewById(R.id.dirText);
        }

        public TextView getDirectionTextView() {
            return directionTextView;
        }
    }

    public void setExhibitsGraph(Graph<String, IdentifiedWeightedEdge> exhibitsGraph) {
        this.exhibitsGraph = exhibitsGraph;
    }

    public void setExhibitsVertex(Map<String, ZooData.VertexInfo> exhibitsVertex) {
        this.exhibitsVertex = exhibitsVertex;
    }

    public void setExhibitsEdge(Map<String, ZooData.EdgeInfo> exhibitsEdge) {
        this.exhibitsEdge = exhibitsEdge;
    }

    public void setDirectionsType(DirectionTypeInterface directionsType) {
        this.directionsType = directionsType;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
