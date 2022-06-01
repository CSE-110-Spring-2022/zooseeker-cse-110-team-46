package edu.ucsd.cse110.zooseeker46;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.function.Consumer;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import edu.ucsd.cse110.zooseeker46.database.StatusDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.directions.DirectionsActivity;
import edu.ucsd.cse110.zooseeker46.plan.PlanActivity;
import edu.ucsd.cse110.zooseeker46.search.SearchActivity;
import edu.ucsd.cse110.zooseeker46.tracking.Coord;
import edu.ucsd.cse110.zooseeker46.tracking.LocationModel;
import edu.ucsd.cse110.zooseeker46.tracking.LocationPermissionChecker;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FOOBAR";
    public static final String EXTRA_LISTEN_TO_GPS = "listen_to_gps";

    private boolean useLocationService;

    //private ActivityMapsBinding binding;

    private LocationModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String a = "a]";
        //Log.d("Started onCreate for MainActivity", a);
        SettingsStaticClass.detailed = false;

        // Uncomment for debugging:
        //ZooDataDatabase.setShouldForceRepopulate();
        ZooDataDatabase zb = ZooDataDatabase.getSingleton(this);
        StatusDao statusdao = zb.statusDao();
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPref = this.getSharedPreferences("onDirections", MODE_PRIVATE);
        boolean onDirections = sharedPref.getBoolean("onDir", false);
        Log.d("In Main, Boolean for onDirections: ", String.valueOf(onDirections));

        SharedPreferences sharedPref3 = this.getSharedPreferences("onExhibitDir", MODE_PRIVATE);
        String onExhibitPrevious = sharedPref3.getString("onExhibit", "");


        if(onDirections && !onExhibitPrevious.equals("")){
            Intent intent = new Intent(MainActivity.this, DirectionsActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }


    @SuppressLint("MissingPermission")
    private void setupLocationListener(Consumer<Coord> handleNewCoords){
        new LocationPermissionChecker(this).ensurePermissions();

        String provider = LocationManager.GPS_PROVIDER;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener(){
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Coord coords = new Coord(location.getLatitude(), location.getLongitude());
                handleNewCoords.accept(coords);
            }
        };
        locationManager.requestLocationUpdates(provider, 0, 0f, locationListener);
    }


    public void onSettingsButtonClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


        //@Override
        public void run() {
            ZooDataDatabase.setShouldForceRepopulate();
        }
}