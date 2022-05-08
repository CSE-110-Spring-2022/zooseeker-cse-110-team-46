package edu.ucsd.cse110.zooseeker46;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DirectionsActivity extends AppCompatActivity {

    //Array for testing -- not actual animal values
    private String[] testList = new String[]{"Alligators", "Arctic Foxes", "Gorillas", "Ur mom", "You have completed your path!"};
    private String[] testDirections = new String[]{"To your left", "to your right", "above you", "lmao", "Congratulations"};
    private int counter = 0; //current position in array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //Set text to first array value
        TextView testText = findViewById(R.id.testText);
        TextView testDirection = findViewById(R.id.testDirection);
        testText.setText(testList[counter]);
        testDirection.setText(testDirections[counter]);

    }

    public void onNextButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);
        TextView testDirection = findViewById(R.id.testDirection);

        //update the textViews if we're still in the array
        if(counter < testList.length){
            counter++;
            testText.setText(testList[counter]);
            testDirection.setText(testDirections[counter]);
        }

    }

    public void onPrevButtonClicked(View view) {

        //load textViews
        TextView testText = findViewById(R.id.testText);
        TextView testDirection = findViewById(R.id.testDirection);

        //update the textViews if we're still in the array
        if(counter > 0){
            counter--;
            testText.setText(testList[counter]);
            testDirection.setText(testDirections[counter]);
        }

    }
}
