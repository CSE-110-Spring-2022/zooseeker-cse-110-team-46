package edu.ucsd.cse110.zooseeker46;
import android.content.Context;
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
import java.util.Locale;
import java.util.Map;
import java.util.logging.LogRecord;

public class ExhibitSelectAdapter  extends BaseAdapter implements Filterable {
    public ArrayList<String> SelectedAnimals;
    private Context context;
    public static ArrayList<Exhibit> ModelArrayList;
    public static ArrayList<Exhibit> ModelArrayListFiltered;
    public Map<String, String> selectedExhibits;



    public ExhibitSelectAdapter(Context context, ArrayList<Exhibit> modelArrayList) {
        this.context = context;
        this.ModelArrayList = modelArrayList;
        this.ModelArrayListFiltered = modelArrayList;
    }

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

                if (ModelArrayList.get(pos).getisSelected()) {
                    ModelArrayList.get(pos).setSelected(false);
                    //SelectedAnimals.remove(modelArrayList.get(pos).getName());
                } else {
                    ModelArrayList.get(pos).setSelected(true);
                    // SelectedAnimals.add(ModelArrayList.get(pos).getName());
                }

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