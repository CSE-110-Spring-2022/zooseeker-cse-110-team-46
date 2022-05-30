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
import android.os.Bundle;

import java.util.function.Consumer;

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
        setContentView(R.layout.activity_main);

        useLocationService = getIntent().getBooleanExtra(EXTRA_LISTEN_TO_GPS, true);

        // Set up the model.
        model = new ViewModelProvider(this).get(LocationModel.class);

        if (useLocationService) {
            setupLocationListener(model::mockLocation);
        }

        else {
            LocationPermissionChecker permissionChecker = new LocationPermissionChecker(this);
            permissionChecker.ensurePermissions();

            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            String provider = LocationManager.GPS_PROVIDER;
            model.addLocationProviderSource(locationManager, provider);
        }

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
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

}