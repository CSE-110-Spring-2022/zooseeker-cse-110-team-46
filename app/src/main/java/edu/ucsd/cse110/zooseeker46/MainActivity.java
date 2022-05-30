package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SettingsStaticClass.detailed = false;
        // Uncomment for debugging:
        ZooDataDatabase.setShouldForceRepopulate();

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public class Worker implements Runnable {
        private List<String> outputScraper;
        private CountDownLatch countDownLatch;

        public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
            this.outputScraper = outputScraper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            ZooDataDatabase.setShouldForceRepopulate();
            outputScraper.add("Counted down");
            countDownLatch.countDown();
        }
    }
}