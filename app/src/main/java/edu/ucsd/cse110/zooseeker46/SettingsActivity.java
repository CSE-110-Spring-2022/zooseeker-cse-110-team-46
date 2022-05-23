package edu.ucsd.cse110.zooseeker46;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Boolean isOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // initiate the Switch
        Switch detailedSwitch = (Switch) findViewById(R.id.switch1);
        isOn = detailedSwitch.isChecked();
    }
}
