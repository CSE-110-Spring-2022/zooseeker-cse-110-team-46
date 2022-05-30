package edu.ucsd.cse110.zooseeker46.search;
import android.content.Context;
import android.graphics.ColorSpace;
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
import edu.ucsd.cse110.zooseeker46.locations.Location;


public class mockExhibitSelectAdapter {

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
    public mockExhibitSelectAdapter(Context context, ArrayList<Exhibit> modelArrayList) {
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

        this.sra = null;
    }

    public mockExhibitSelectAdapter(Context context, ArrayList<Exhibit> modelArrayList, SelectedRecyclerAdapter sra) {
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

    // Helper function to get the selected exhibit count -> Kevin use this!
    public int getSelectedExhibitsCount(){
        return exhibitDao.getSelectedExhibits().size();
    }

    public Location getExhibit(int i) {
        return ModelArrayList.get(i);
    }


    //this is a simple class that filtering the ArrayList of strings used in adapter
    private class ViewHolder {
        protected CheckBox checkBox;
        private TextView tvAnimal;
    }

    public void forceRepopulate(){
        zb = zb.resetSingleton(context);
    }
}
