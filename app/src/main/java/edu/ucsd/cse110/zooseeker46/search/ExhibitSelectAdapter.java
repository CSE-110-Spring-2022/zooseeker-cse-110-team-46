package edu.ucsd.cse110.zooseeker46.search;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


import java.util.List;
import java.util.Locale;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.database.ExhibitDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;


public class ExhibitSelectAdapter extends BaseAdapter implements Filterable {

    private final edu.ucsd.cse110.zooseeker46.search.searchFilter searchFilter = new searchFilter(this);
    private Context context;

    // Model Array Lists are what we pass the original animal list. You can initialize this in SearchActivity.java
    public static ArrayList<Exhibit> ModelArrayList;
    public static ArrayList<Exhibit> ModelArrayListFiltered;

    // As checkboxes are selected/deselected, this keeps all the exhibit names stored
    public static Set<String> selectedExhibits;

    private int SelectedCount;

    // As the name implies.. (string = exhibit name)
    public Map<String, Exhibit> totalExhibits;

    public ZooDataDatabase zb;
    public ExhibitDao exhibitDao;

    SelectedRecyclerAdapter sra;

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
        this.zb = ZooDataDatabase.getSingleton(context);
        exhibitDao = zb.exhibitDao();
        setSelectedCount(exhibitDao.getSelectedExhibits().size());

        this.sra = null;

        Log.d("In constructor of exhibitselectadapter, size of selected: ", String.valueOf(exhibitDao.getSelectedExhibits().size()));
    }

    public ExhibitSelectAdapter(Context context, ArrayList<Exhibit> modelArrayList, SelectedRecyclerAdapter sra) {
        this.context = context;
        Collections.sort(modelArrayList, Exhibit.ExhibitNameComparator);
        this.ModelArrayList = modelArrayList;
        this.ModelArrayListFiltered = modelArrayList;
        this.selectedExhibits = new HashSet<>();
        this.totalExhibits = new HashMap<>();
        for (Exhibit exhibit : modelArrayList) {
            totalExhibits.put(exhibit.getName(), exhibit);
        }
        this.zb = ZooDataDatabase.getSingleton(context);
        exhibitDao = zb.exhibitDao();
        setSelectedCount(exhibitDao.getSelectedExhibits().size());
        this.sra = sra;
        Log.d("In constructor of exhibitselectadapter, size of selected: ", String.valueOf(exhibitDao.getSelectedExhibits().size()));
    }

    public int getSelectedCount() {
        return this.SelectedCount;
    }

    public void setSelectedCount(int i) {
        this.SelectedCount =  i;
    }

    // All these override functions are required for adapter, please DO NOT MODIFY
    // If you need a helper function, make a new one

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return ModelArrayList.size();
    }

    @Override
    public int getViewTypeCount(){
        if(getCount()>0) {
            return getCount();
        }else{
            return super.getViewTypeCount();
    }
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
        return exhibitDao.getSelectedExhibits().size();
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


        holder.tvAnimal.setText(ModelArrayList.get(position).getName());
        holder.checkBox.setChecked(ModelArrayList.get(position).getIsSelected());
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
                if (ModelArrayList.get(pos).getIsSelected()) {
                    Exhibit currExhibit = ModelArrayList.get(pos);
                    currExhibit.setSelected(false);
                    exhibitDao.update(currExhibit);

                    selectedExhibits.remove(curr);

                    setSelectedCount(exhibitDao.getSelectedExhibits().size());
                    TextView foo = (TextView) ((SearchActivity)context).findViewById(R.id.selected_exhibit_count);
                    foo.setText(String.valueOf(getSelectedCount()));

                    if(sra != null){ sra.updateData((ArrayList<Exhibit>) exhibitDao.getSelectedExhibits()); }
                } else {
                    Exhibit currExhibit = ModelArrayList.get(pos);
                    currExhibit.setSelected(true);
                    exhibitDao.update(currExhibit);

                    selectedExhibits.add(curr);
                    setSelectedCount(exhibitDao.getSelectedExhibits().size());
                    //setSelectedCount(selectedExhibits.size());
                    TextView foo = (TextView) ((SearchActivity)context).findViewById(R.id.selected_exhibit_count);
                    foo.setText(String.valueOf(getSelectedCount()));

                    if(sra != null){ sra.updateData((ArrayList<Exhibit>) exhibitDao.getSelectedExhibits()); }
                }
                selectedExhibits.forEach(System.out::println);
            }
        });
        return convertView;
    }

    // DO NOT TOUCH
    @Override
    public Filter getFilter() {
        return searchFilter.getFilter();

    }
    //this is a simple class that filtering the ArrayList of strings used in adapter
    private class ViewHolder {
        protected CheckBox checkBox;
        private TextView tvAnimal;
    }

    public void forceRepopulate(){

        //zb = zb.resetSingleton(context);
        zb = zb.resetSelected();
    }
}