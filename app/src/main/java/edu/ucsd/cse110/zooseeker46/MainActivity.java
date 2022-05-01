package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SearchActivity.class);
/*
//start of exhibit count updater
        //getting planned/selected exhibit count
        //this probably needs to be changed to get exhibit count from ZooExhibits.class
        TextView exhibitCountView = findViewById(R.id.exhibit_count);
        String exhibitCountStr = exhibitCountView.getText().toString();

        //Check if integer parsed  correctly
        Optional<Integer> maybeExhibitCount = Utilities.parseCount(exhibitCountStr);
        if (!maybeExhibitCount.isPresent()) {
            //if not, show an error and return
            Utilities.showAlert(this, "Didn't parse count correctly");
            return;
        }

        //get the integer
        int exhibitCount = maybeExhibitCount.get();

        //intent
        intent.putExtra("exhibit_count", exhibitCount);

//end of exhibit count updater

*/
        startActivity(intent);
    }



    public void onPlanButtonClicked(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }
}