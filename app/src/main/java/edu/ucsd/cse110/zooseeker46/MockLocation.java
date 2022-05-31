package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.ucsd.cse110.zooseeker46.tracking.Coord;
import edu.ucsd.cse110.zooseeker46.tracking.MockDirections;
import edu.ucsd.cse110.zooseeker46.tracking.TrackingStatic;

public class MockLocation extends AppCompatActivity {
    EditText lat;
    EditText lng;
    Coord currLoc;
    visitor visitor;
    MockDirections mockDirections = new MockDirections();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_location);

        mockDirections.setCounter(TrackingStatic.counter);
        mockDirections.setExhibitNamesID(TrackingStatic.exhibitNamesIDs);
        mockDirections.setExhibitsGraphTracking(TrackingStatic.zoo);
        mockDirections.setFinalPath(TrackingStatic.finalPath);
        mockDirections.setExhibitsVertexTracking(TrackingStatic.places);
        this.visitor = new visitor();
    }

    public void onDoneButtonClicked(View view) {
        lat = (EditText) this.findViewById(R.id.latInput);
        lng = (EditText) this.findViewById(R.id.longInput);

        Coord coord = mockDirections.convertFromInput(lat, lng);

        this.visitor.setCurrentNode(mockDirections.closestNode(coord));
        TrackingStatic.visitor = visitor;

        Coord currExhibit = mockDirections.locationOfExhibit(mockDirections.getExhibitNamesID(), mockDirections.getCounter());
        double latitude = Double.parseDouble(lat.getText().toString());
        boolean offRoute = mockDirections.checkOffRoute(coord);
        if (offRoute == true){
            Utilities.showAlert(this, "Offtrack");
        }
        else{
            finish();
        }
    }

    public void onCancelButtonClicked(View view) {
        finish();
    }


}