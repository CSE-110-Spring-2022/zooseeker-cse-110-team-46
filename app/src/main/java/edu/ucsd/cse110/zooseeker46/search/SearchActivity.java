package edu.ucsd.cse110.zooseeker46.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.ZooExhibits;
import edu.ucsd.cse110.zooseeker46.database.ExhibitDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.plan.PlanActivity;
import edu.ucsd.cse110.zooseeker46.R;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;

public class SearchActivity extends AppCompatActivity {

    ListView listView;


    Map<String, ZooData.VertexInfo> vertexInfoMap;
    ZooExhibits zoo;

    private ArrayList<Exhibit> modelArrayList;
    public static ExhibitSelectAdapter customAdapter;
    private Button btnnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExhibitDao exhibitDao = ZooDataDatabase.getSingleton(this).exhibitDao();
        List<Exhibit> modelArrayList = exhibitDao.getAll();
        listView = (ListView) findViewById(R.id.lv);
        btnnext = (Button) findViewById(R.id.plan_btn);
        TextView count = findViewById(R.id.selected_exhibit_count);
        vertexInfoMap = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        zoo = new ZooExhibits(vertexInfoMap);
        //modelArrayList = zoo.getExhibits();
        customAdapter = new ExhibitSelectAdapter(this, (ArrayList<Exhibit>) modelArrayList);

        listView.setAdapter(customAdapter);
        listView.setTextFilterEnabled(true);
        listView.setEmptyView(findViewById(R.id.empty)); // no results text); // sets no results text to the list
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, PlanActivity.class);
                intent.putExtra("exhibit_count", count.getText());
                startActivity(intent);
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

}