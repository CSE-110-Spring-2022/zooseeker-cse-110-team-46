package edu.ucsd.cse110.zooseeker46.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.plan.PlanExhibitsAdapter;


public class SelectedRecyclerAdapter extends RecyclerView.Adapter<SelectedRecyclerAdapter.ViewHolder>{
    private ArrayList<Exhibit> items;
    Context context;
//    private OnItemClickListener onItemClickListener;

    public SelectedRecyclerAdapter(ArrayList<Exhibit> items) {
        this.items = items;
    }

//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //View v = LayoutInflater.from(parent.getContext()).inflate(R.id.selected_rv, parent, false);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        //v.setOnClickListener(this);
//        return new ViewHolder(v);
//    }

    @NonNull
    @Override
    public SelectedRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //default view in a 1d list
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibt_item, parent, false);
        return new SelectedRecyclerAdapter.ViewHolder(view);
    }

    public SelectedRecyclerAdapter(Context context, ArrayList<Exhibit> modelArrayList){
        this.context = context;
        this.items = modelArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final SelectedRecyclerAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new SelectedRecyclerAdapter.ViewHolder(convertView);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item, null, true);

            holder.tvAnimal = (TextView) convertView.findViewById(R.id.selectedtext);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (SelectedRecyclerAdapter.ViewHolder) convertView.getTag();
        }

        holder.tvAnimal.setText(items.get(position).getName());
        return convertView;
    }

    public void updateData(ArrayList<Exhibit> viewModels) {
        items.clear();
        items.addAll(viewModels);
    }

    public void addItem(int position, Exhibit viewModel) {
        items.add(items.size(), viewModel);
    }

    public void removeItem(Exhibit removed) {
        int i = 0;
        for(Exhibit curr: items){
            if(curr.getName().equals(removed.getName())){
                items.remove(i);
                return;
            }
            i++;
        }
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView exhibittext = holder.tvAnimal;
        exhibittext.setText(items.get(position).getName());
    }


//
//    @Override
//    public void onClick(final View v) {
//        // Give some time to the ripple to finish the effect
//        if (onItemClickListener != null) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    onItemClickListener.onItemClick(v, (ViewModel) v.getTag());
//                }
//            }, 0);
//        }
//    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAnimal;
        public ViewHolder(View itemView){
            super(itemView);
            this.tvAnimal = itemView.findViewById(R.id.selected_tv);
        }
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, ViewModel viewModel);
//    }

}
