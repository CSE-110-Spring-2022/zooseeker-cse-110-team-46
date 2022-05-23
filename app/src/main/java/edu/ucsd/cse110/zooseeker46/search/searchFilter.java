package edu.ucsd.cse110.zooseeker46.search;

import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;

public class searchFilter implements Filterable{
    ExhibitSelectAdapter ExhibitSelectAdapter;
    public searchFilter(ExhibitSelectAdapter exhibitSelectAdapter) {
        this.ExhibitSelectAdapter = exhibitSelectAdapter;

    }// DO NOT TOUCH

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter.ModelArrayListFiltered.size();
                    filterResults.values = edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter.ModelArrayListFiltered;

                } else {
                    ArrayList<Exhibit> resultsModel = new ArrayList<Exhibit>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Exhibit itemsModel : edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter.ModelArrayListFiltered) {
                        String lower = itemsModel.getName().toLowerCase();
                        if (lower.contains(searchStr)) {
                            resultsModel.add(itemsModel);
                        }
                        else{
                            for(String tag:itemsModel.tags.getTags()){
                                tag = tag.toLowerCase();
                                if(tag.contains(searchStr)){
                                    resultsModel.add(itemsModel);
                                    break;
                                }
                            }
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ExhibitSelectAdapter.ModelArrayList = (ArrayList<Exhibit>) results.values;
                ExhibitSelectAdapter.notifyDataSetChanged();
            }
        };
        return filter;
    }
}