package edu.ucsd.cse110.zooseeker46;

import static edu.ucsd.cse110.zooseeker46.ZooData.loadVertexInfoJSON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.jgrapht.Graph;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}