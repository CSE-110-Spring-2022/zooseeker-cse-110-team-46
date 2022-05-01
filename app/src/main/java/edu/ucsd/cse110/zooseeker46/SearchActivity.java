package edu.ucsd.cse110.zooseeker46;

import static edu.ucsd.cse110.zooseeker46.ZooData.loadVertexInfoJSON;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.jgrapht.Graph;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchActivity extends AppCompatActivity {
    private int exhibitCount;

    /* Not sure if updating with background thread
    private ExecutorService backgroundThreadExecutor = Executors.newSingleThreadExecutor();
    private Future<Void> future;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*
        //get exhibit_count passed from previous activity(wherever its set)
        Bundle extras =  getIntent().getExtras();
        this.exhibitCount = extras.getInt("exhibit_count");

        //find view and set text to number of selected
        TextView exhibitCountView = findViewById(R.id.exhibit_count);
        //unsure if we are updating with background thread or run everytime when an exhibit is selected
        exhibitCountView.setText(String.valueOf(exhibitCount));


         */
    }


    //go to 'your plan' page
    public void onPlanButtonClicked(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }

}