package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    //This exhibit list is temporary
    private List<Exhibit> testExhibitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();

        recyclerView = findViewById(R.id.planned_exhibits_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //temporary till actual implementation of ZooExhibits
        testExhibitList = new ArrayList<>();
        testExhibitList.add(new Exhibit("Bears","Honey Walk", false));
        testExhibitList.add(new Exhibit("Seals","Sea Street", false));
        testExhibitList.add(new Exhibit("Lion","Safari Street", false));
        adapter.setExhibits(testExhibitList);
    }

    public void onDirectionsButtonClicked(View view) {
        Utilities.showAlert(this, "This button doesn't do anything yet!");
    }
}