package edu.ucsd.cse110.zooseeker46;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
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

        //set switch to on or off
        if(SettingsStaticClass.detailed)
            detailedSwitch.setChecked(true);
        else
            detailedSwitch.setChecked(false);

        //isOn = detailedSwitch.isChecked();

        detailedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SettingsStaticClass.detailed = true;
                } else {
                    SettingsStaticClass.detailed = false;
                }
            }
        });
    }

    public void onBackButtonClicked(View view) {
        finish();
    }
}
