package edu.ucsd.cse110.zooseeker46;
import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogRecord;

public class ExhibitSelectAdapter extends BaseAdapter implements Filterable {
    public ArrayList<String> SelectedAnimals;
    private Context context;
    public static ArrayList<Exhibit> ModelArrayList;
    public static ArrayList<Exhibit> ModelArrayListFiltered;
    public Set<String> selectedExhibits;
    private int SelectedCount  =  0;

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


    public ExhibitSelectAdapter(Context context, ArrayList<Exhibit> modelArrayList) {
        this.context = context;
        Collections.sort(modelArrayList,Exhibit.ExhibitNameComparator);
        this.ModelArrayList = modelArrayList;
        this.ModelArrayListFiltered = modelArrayList;
        this.selectedExhibits = new HashSet<>();
    }

    //kevins buggy code start
    public int getSelectedCount() {
        return this.SelectedCount;
    }

    public void setSelectedCount(int i) {
        this.SelectedCount =  i;
    }

//    //kevin end code

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
        return ModelArrayListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return ModelArrayListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
        holder.tvAnimal.setText(ModelArrayListFiltered.get(position).getName());

        holder.checkBox.setChecked(ModelArrayListFiltered.get(position).getisSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.animal);
                Integer pos = (Integer) holder.checkBox.getTag();
                Toast.makeText(context, "" +"selected" + "", Toast.LENGTH_SHORT).show();
                String curr = ModelArrayListFiltered.get(pos).getName();
                if (ModelArrayListFiltered.get(pos).getisSelected()) {
                    ModelArrayListFiltered.get(pos).setSelected(false);
                    selectedExhibits.remove(curr);

                    //SelectedAnimals.remove(modelArrayList.get(pos).getName());
                } else {
                    ModelArrayListFiltered.get(pos).setSelected(true);
                    selectedExhibits.add(curr);
                    // SelectedAnimals.add(ModelArrayList.get(pos).getName());
                }
                ModelArrayList = updateML(selectedExhibits);

                //kevins buggy code at 1;26am
//                setSelectedCount(selectedExhibits.size());
                SearchActivity tempObj = new SearchActivity();
                tempObj.updateCount(1);
            }

        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = ModelArrayList.size();
                    filterResults.values = ModelArrayList;

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
               ModelArrayListFiltered = (ArrayList<Exhibit>) results.values;
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