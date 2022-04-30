package edu.ucsd.cse110.zooseeker46;

import static edu.ucsd.cse110.zooseeker46.ZooData.loadVertexInfoJSON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jgrapht.Graph;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    String[] name = {"Atest1", "Atest2", "Btest1", "Btest2",};
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, name);
        listView.setAdapter(arrayAdapter);

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
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}