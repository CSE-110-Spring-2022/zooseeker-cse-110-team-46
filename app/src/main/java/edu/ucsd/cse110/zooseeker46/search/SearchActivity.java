package edu.ucsd.cse110.zooseeker46.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.SettingsActivity;
import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.ZooExhibits;
import edu.ucsd.cse110.zooseeker46.directions.DirectionsActivity;
import edu.ucsd.cse110.zooseeker46.database.ExhibitDao;
import edu.ucsd.cse110.zooseeker46.database.ExhibitGroupDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit_Group;
import edu.ucsd.cse110.zooseeker46.plan.PlanActivity;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;

public class SearchActivity extends AppCompatActivity {

    ListView listView;


    Map<String, ZooData.VertexInfo> vertexInfoMap;
    ZooExhibits zoo;

    private ArrayList<Exhibit> modelArrayList;
    public static ExhibitSelectAdapter customAdapter;
    public SelectedRecyclerAdapter selectadapter;
    private Button btnnext;
    public ZooDataDatabase zb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("On create called!", "inside search");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Two lines for accessing db
        zb= ZooDataDatabase.getSingleton(this);
        ExhibitDao exhibitDao = zb.exhibitDao();

        List<Exhibit> allexhibits = exhibitDao.getAll();
        int size = allexhibits.size();
        Log.d("size of exhibit list: ", String.valueOf(size));
        modelArrayList = (ArrayList<Exhibit>) allexhibits;
        Log.d("size of selected exhibit list: ", String.valueOf(exhibitDao.getSelectedExhibits().size()));

        listView = (ListView) findViewById(R.id.lv);
        btnnext = (Button) findViewById(R.id.plan_btn);
        TextView count = findViewById(R.id.selected_exhibit_count);
        count.setText(String.valueOf(exhibitDao.getSelectedExhibits().size()));
        zoo = new ZooExhibits(vertexInfoMap);


        RecyclerView rvSelectedExhibits = (RecyclerView) findViewById(R.id.selected_rv);
        selectadapter = new SelectedRecyclerAdapter((ArrayList<Exhibit>) exhibitDao.getSelectedExhibits());
        rvSelectedExhibits.setLayoutManager(new LinearLayoutManager(this));
        rvSelectedExhibits.setAdapter(selectadapter);
        rvSelectedExhibits.setHasFixedSize(true);
        customAdapter = new ExhibitSelectAdapter(this, (ArrayList<Exhibit>) modelArrayList, selectadapter);

        listView.setAdapter(customAdapter);
        listView.setTextFilterEnabled(true);
        listView.setEmptyView(findViewById(R.id.empty)); // no results text); // sets no results text to the list


        List<Exhibit> selected = exhibitDao.getSelectedExhibits();

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, PlanActivity.class);
                intent.putExtra("exhibit_count", count.getText());
                startActivity(intent);
            }
        });

        Button clearbtn = (Button) findViewById(R.id.clear_btn);

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("being called");
                customAdapter.forceRepopulate();
                customAdapter.notifyDataSetChanged();
                selectadapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//changes text when user presses enter...
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//changes text on every key(e.g. called twice if "ch" is typed. once for 'c' and again for 'h'
                customAdapter.getFilter().filter(newText);
                customAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public static ExhibitSelectAdapter getCustomAdapter(){
        return customAdapter;
    }

    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


}