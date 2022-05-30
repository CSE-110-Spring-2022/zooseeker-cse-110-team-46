package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.ucsd.cse110.zooseeker46.tracking.Coord;
import edu.ucsd.cse110.zooseeker46.tracking.MockDirections;

public class MockLocation extends AppCompatActivity {
    EditText lat;
    EditText lng;
    Coord currLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_location);
    }

    public void onDoneButtonClicked(View view) {
        lat = (EditText) this.findViewById(R.id.latInput);
        lng = (EditText) this.findViewById(R.id.longInput);
        double latitude = Double.parseDouble(lat.getText().toString());
        double longitude = Double.parseDouble(lng.getText().toString());

        finish();
    }

    public Coord getCurrLoc(){
        return currLoc;
    }

}