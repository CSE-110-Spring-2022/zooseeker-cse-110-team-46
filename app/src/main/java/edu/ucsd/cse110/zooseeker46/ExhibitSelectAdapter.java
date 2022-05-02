package edu.ucsd.cse110.zooseeker46;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Filter;

public class ExhibitSelectAdapter  extends BaseAdapter implements Filterable {

    private Context context;
    public static ArrayList<Exhibit> modelArrayList;
    public Map<String, String> selectedExhibits;
    public ArrayList<String> animals;


    public ExhibitSelectAdapter(Context context, ArrayList<Exhibit> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;

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
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.animal);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        holder.checkBox.setText("Checkbox "+position);
        holder.tvAnimal.setText(modelArrayList.get(position).getName());

        holder.checkBox.setChecked(modelArrayList.get(position).getisSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.animal);
                Integer pos = (Integer)  holder.checkBox.getTag();
                Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(modelArrayList.get(pos).getisSelected()){
                    modelArrayList.get(pos).setSelected(false);
                    //selectedExhibits.remove(modelArrayList.get(pos).getName());
                }else {
                    modelArrayList.get(pos).setSelected(true);
                    //selectedExhibits.put(modelArrayList.get(pos).getName(), "true");
                }

            }
        });

        return convertView;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<String> tempList=new ArrayList<String>();
            //constraint is the result from text you want to filter against.
            //objects is your data set you will filter from
            if(constraint != null && animals!=null) {
                int length=animals.size();
                int i=0;
                while(i<length){
                    String item=animals.get(i);
                    //do whatever you wanna do here
                    //adding result set output array

                    tempList.add(item);

                    i++;
                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            animals = (ArrayList<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return myFilter;
    }


    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvAnimal;

    }

}
