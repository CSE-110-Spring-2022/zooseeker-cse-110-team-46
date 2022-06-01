package edu.ucsd.cse110.zooseeker46;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.zooseeker46.search.SearchActivity;

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

    public void onBacktoSearchClicked(View view) {
        SharedPreferences sharedPrefActivity = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        //now get Editor
        SharedPreferences.Editor editor= sharedPrefActivity.edit();
        //put your value
        editor.putBoolean("onDir", false);
        editor.putBoolean("startover", false);
        //commits your edits
        editor.commit();
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
        //editor.putBoolean("startover", false);

    }

    public void onBacktoStartCleanClicked(View view){
        SharedPreferences sharedPrefActivity = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        //now get Editor
        SharedPreferences.Editor editor= sharedPrefActivity.edit();
        //put your value
        editor.putBoolean("onDir", false);
        editor.putBoolean("startover", true);
        editor.putInt("counter", 0);
        //commits your edits
        editor.commit();
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }
}
