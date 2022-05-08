package edu.ucsd.cse110.zooseeker46;

import static edu.ucsd.cse110.zooseeker46.ZooData.loadVertexInfoJSON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import org.jgrapht.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    ListView listView;


    Map<String, ZooData.VertexInfo> vertexInfoMap;
    ZooExhibits zoo;


    private ArrayList<Exhibit> modelArrayList;
    private ExhibitSelectAdapter customAdapter;
    private Button btnnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);
        btnnext = (Button) findViewById(R.id.plan_btn);
        vertexInfoMap = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        zoo = new ZooExhibits(vertexInfoMap);
        modelArrayList = zoo.getExhibits();
        customAdapter = new ExhibitSelectAdapter(this,modelArrayList);
        listView.setAdapter(customAdapter);
        listView.setTextFilterEnabled(true);
        listView.setEmptyView( findViewById(R.id.empty)); // no results text); // sets no results text to the list
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, PlanActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
       // searchView.setOnSearchClickListener();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//changes text when user presses enter...
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//changes text on every key(e.g. called twice if "ch" is typed. once for 'c' and again for 'h'
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}