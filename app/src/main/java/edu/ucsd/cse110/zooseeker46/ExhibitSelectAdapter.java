package edu.ucsd.cse110.zooseeker46;
import android.content.Context;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogRecord;

public class ExhibitSelectAdapter  extends BaseAdapter implements Filterable {
    private Context context;

    // Model Array Lists are what we pass the original animal list. You can initialize this in SearchActivity.java
    public static ArrayList<Exhibit> ModelArrayList;
    public static ArrayList<Exhibit> ModelArrayListFiltered;

    // As checkboxes are selected/deselected, this keeps all the exhibit names stored
    public static Set<String> selectedExhibits;

    // As the name implies.. (string = exhibit name)
    public Map<String, Exhibit> totalExhibits;

    // You can ignore this function. We are still figuring out a way to update listview so checked boxes are filtered to top of listview
    public ArrayList<Exhibit> updateML(Set<String> selectedList){
        ArrayList<Exhibit> unchecked = new ArrayList<>();
        ArrayList<Exhibit> checked = new ArrayList<>();
        for (Exhibit currEx: ModelArrayList){
            String currName = currEx.getName();
            if(selectedList.contains(currName) == false){
                unchecked.add(currEx);
            }
            else{ checked.add(currEx); }
        }
        checked.addAll(unchecked);
        return checked;
    }

    // Constructor
    public ExhibitSelectAdapter(Context context, ArrayList<Exhibit> modelArrayList) {
        this.context = context;
        Collections.sort(modelArrayList, Exhibit.ExhibitNameComparator);
        this.ModelArrayList = modelArrayList;
        this.ModelArrayListFiltered = modelArrayList;
        this.selectedExhibits = new HashSet<>();
        this.totalExhibits = new HashMap<>();
        for (Exhibit exhibit : modelArrayList) {
            totalExhibits.put(exhibit.getName(), exhibit);
        }
    }

    // All these override functions are required for adapter, please DO NOT MODIFY
    // If you need a helper function, make a new one

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return ModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ModelArrayList.get(position);
    }

    // Helper function to run UI test on the listview display
    public Exhibit getExhibit(int position){
        return ModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Helper function to get the selected exhibit count -> Kevin use this!
    public int getSelectedExhibitsCount(){
        return selectedExhibits.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.animal);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }


        //holder.checkBox.setText("Checkbox " + position);

        holder.tvAnimal.setText(ModelArrayList.get(position).getName());

        holder.checkBox.setChecked(ModelArrayList.get(position).getisSelected());
        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.animal);
                Integer pos = (Integer) holder.checkBox.getTag();
                String curr = ModelArrayList.get(pos).getName();
                Toast.makeText(context, "Selected " + curr, Toast.LENGTH_SHORT).show();
                //System.out.println("-------------------------");
                if (ModelArrayList.get(pos).getisSelected()) {
                    ModelArrayList.get(pos).setSelected(false);
                    //System.out.println("curr unchecked: " + curr);
                    selectedExhibits.remove(curr);
                    //Exhibit currExhibit = totalExhibits.get(curr);
                   // currExhibit.setSelected(false);
                   // totalExhibits.put(curr, currExhibit);

                    //SelectedAnimals.remove(modelArrayList.get(pos).getName());
                } else {
                    ModelArrayList.get(pos).setSelected(true);
                    //ModelArrayList.get(pos).setSelected(true);
                   // System.out.println("curr checked: " + curr);
                    selectedExhibits.add(curr);
                    //Exhibit currExhibit = totalExhibits.get(curr);
                    //currExhibit.setSelected(true);
                    //totalExhibits.put(curr, currExhibit);
                    // SelectedAnimals.add(ModelArrayList.get(pos).getName());
                }

                //ModelArrayList = new ArrayList<Exhibit>(totalExhibits.values());
                //ModelArrayList = updateML(selectedExhibits);
                //Collections.sort(ModelArrayList,Exhibit.ExhibitNameComparator);
               // Collections.sort(ModelArrayListFiltered,Exhibit.ExhibitNameComparator);
                //ModelArrayListFiltered = updateML(selectedExhibits);
                //System.out.println("curr count: " + selectedExhibits.size());
                selectedExhibits.forEach(System.out::println);
                //System.out.println("-------------------------");
                //System.out.println("count = " + getCount());

            }
        });

        return convertView;
    }

    // DO NOT TOUCH
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = ModelArrayListFiltered.size();
                    filterResults.values = ModelArrayListFiltered;

                }else{
                    ArrayList<Exhibit> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Exhibit itemsModel:ModelArrayList){
                        String lower = itemsModel.getName().toLowerCase();
                        if(lower.contains(searchStr)){
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }
                //Collections.sort((ArrayList<Exhibit>)filterResults.values ,Exhibit.ExhibitNameComparator);
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               /*ArrayList<Exhibit> unchecked = new ArrayList<>();
                ArrayList<Exhibit> checked = new ArrayList<>();
                for (Exhibit curr: ModelArrayList){
                    if(curr.getisSelected()){
                        checked.add(curr);
                    }
                    else{unchecked.add(curr);}
                }
                checked.addAll(unchecked);
                ModelArrayList = checked;
                //results.values = checked;
               //results.count = checked.size();*/

               ModelArrayList = (ArrayList<Exhibit>) results.values;
                //Collections.sort(ModelArrayList,Exhibit.ExhibitNameComparator);
                notifyDataSetChanged();

            }
        };
        return filter;
    }
//this is a simple class that filtering the ArrayList of strings used in adapter



    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvAnimal;

    }

}