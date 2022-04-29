package edu.ucsd.cse110.zooseeker46;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/*
    Adapter for recycler view to use an exhibit_item
 */
public class PlanExhibitsAdapter extends RecyclerView.Adapter<PlanExhibitsAdapter.ViewHolder> {
    private List<Exhibit> exhibits = Collections.emptyList();

    public void setExhibits(List<Exhibit> newExhibits) {
        this.exhibits.clear();
        this.exhibits = newExhibits;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibt_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanExhibitsAdapter.ViewHolder holder, int position) {
        holder.setExhibit(exhibits.get(position));
    }

    @Override
    public int getItemCount() {
        return exhibits.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameTextView;
        private final TextView streetTextView;
        private Exhibit exhibit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.exhibit_name_text);
            this.streetTextView = itemView.findViewById(R.id.exhibit_location_text);
        }

        public Exhibit getExhibit(){ return exhibit;}

        public void setExhibit(Exhibit exhibit){
            this.exhibit = exhibit;
            this.nameTextView.setText(exhibit.animal_name);
            //temporary testing distance
            int distance = (int) (Math.random()*100);
            this.streetTextView.setText(exhibit.animal_location + ", " + distance);
        }
    }
}
