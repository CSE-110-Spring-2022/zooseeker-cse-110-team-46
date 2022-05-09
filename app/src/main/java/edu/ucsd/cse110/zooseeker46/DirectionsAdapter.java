package edu.ucsd.cse110.zooseeker46;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.GraphPath;

import java.util.List;

public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.ViewHolder>{
    GraphPath<String, IdentifiedWeightedEdge> path = null;

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
        TextView DirectionsTextView = holder.getDirectionTextView();
        List<IdentifiedWeightedEdge> edges = path.getEdgeList();
        DirectionsTextView.setText(edges.get(position).toString());
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
