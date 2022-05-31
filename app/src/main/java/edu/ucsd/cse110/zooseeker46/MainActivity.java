package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.ucsd.cse110.zooseeker46.database.StatusDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.directions.DirectionsActivity;
import edu.ucsd.cse110.zooseeker46.plan.PlanActivity;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a = "a]";
        Log.d("Started onCreate for MainActivity", a);

        SettingsStaticClass.detailed = false;


        // Uncomment for debugging:
        //ZooDataDatabase.setShouldForceRepopulate();

        ZooDataDatabase zb = ZooDataDatabase.getSingleton(this);
        StatusDao statusdao = zb.statusDao();

        SharedPreferences sharedPref = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        boolean onDirections = sharedPref.getBoolean("onDir", false);
        Log.d("In Main, Boolean for onDirections: ", String.valueOf(onDirections));
        if(onDirections){
            Intent intent = new Intent(MainActivity.this, DirectionsActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }

    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

//    public class Worker implements Runnable {
//        private List<String> outputScraper;
//        private CountDownLatch countDownLatch;
//
//        public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
//            this.outputScraper = outputScraper;
//            this.countDownLatch = countDownLatch;
//        }

        //@Override
        public void run() {
            ZooDataDatabase.setShouldForceRepopulate();
//            outputScraper.add("Counted down");
//            countDownLatch.countDown();
        }
    //}
}