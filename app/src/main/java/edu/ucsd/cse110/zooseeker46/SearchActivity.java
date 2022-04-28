package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        List<ExhibitItem> exhibitItemList = ExhibitItem.loadJSON(this, "example_exhibits.json");
        Log.d("SearchActivity", exhibitItemList.toString());
    }
}