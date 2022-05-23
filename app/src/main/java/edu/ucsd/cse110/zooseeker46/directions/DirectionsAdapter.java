package edu.ucsd.cse110.zooseeker46.directions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.IdentifiedWeightedEdge;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.ZooData;

public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.ViewHolder>{
    public GraphPath<String, IdentifiedWeightedEdge> path = null;
    public Graph<String, IdentifiedWeightedEdge> exhibitsGraph;
    public Map<String, ZooData.VertexInfo> exhibitsVertex;
    public Map<String, ZooData.EdgeInfo> exhibitsEdge;
    public DirectionTypeInterface directions = new DetailedDirections();

    public void setDirections(GraphPath<String, IdentifiedWeightedEdge> path){
        this.path = path;
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
        pathLayout = edges.get(position);
        String startName = directions.getStartName(exhibitsVertex, pathLayout);
        String endName = directions.getEndName(exhibitsVertex, pathLayout);
        String streetName = directions.getStreetName(exhibitsEdge, pathLayout);
        //check if in correct order
        //if not switch
        if (position < this.getItemCount()-1 && position+1 < this.getItemCount()) {
            if (startName == exhibitsVertex.get(edges.get(position + 1).getSourceName()).name ||
                    startName == exhibitsVertex.get(edges.get(position + 1).getTargetName()).name) {
                String tempName = startName;
                startName = endName;
                endName = tempName;
            }
        }
        if(position == this.getItemCount()-1 && position-1 > -1){
            if (endName == exhibitsVertex.get(edges.get(position - 1).getSourceName()).name ||
                    endName == exhibitsVertex.get(edges.get(position - 1).getTargetName()).name) {
                String tempName = startName;
                startName = endName;
                endName = tempName;
            }
        }
        int length = directions.getLength(exhibitsGraph, pathLayout);
        DirectionsTextView.setText(directions.pathFormat(startName, endName, streetName, length));
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
}
