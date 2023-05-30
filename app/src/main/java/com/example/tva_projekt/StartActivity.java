package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.tva_projekt.common.TVAapplication;
import com.example.tva_projekt.dataObjects.ActivityObject;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import android.Manifest;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {


    TextView activityName;
    TextView activityDuration;
    TextView activityDate;
    TextView activityDistance;

    MapView mapView;
    IMapController mapController;

    LocationManager locationManager;

    LocationListener locationListener;

    private TVAapplication app;

    Boolean IsTimerRunning = false;
    Long startTime;
    Long elapsedTime;
    Handler handler;
    Runnable runnable;

    Double totalDistance;

    ActivityObject activityObject;

    private Location lastLocation;

    public static final String ACTIVITY_TYPE = "activityNameExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("OpenStreetMap", MODE_PRIVATE));
        setContentView(R.layout.start_activity);

        app = (TVAapplication)getApplication();


        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapController = mapView.getController();
        mapController.setZoom(15.0);

        activityName = findViewById(R.id.textName);
        activityDuration = findViewById(R.id.textDuration);
        activityDistance = findViewById(R.id.textDistance);

        activityName.setText(getIntent().getStringExtra(ACTIVITY_TYPE));
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {

                    if (location != null) {
                        if (lastLocation != null) {
                            totalDistance += getDistanceFromLatLonInKm(lastLocation.getLatitude(), lastLocation.getLongitude(), location.getLatitude(), location.getLongitude());
                        }
                        activityDistance.setText(String.format("%.2f", totalDistance) + " km");
                        lastLocation = location;
                        activityObject.pathCoordinates.add(new GeoPoint(location.getLatitude(), location.getLongitude()));
                        mapController.setCenter(new GeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude()));
                        Marker startMarker = new Marker(mapView);
                        startMarker.setPosition(new GeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude()));
                        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        mapView.getOverlays().clear();
                        mapView.getOverlays().add(startMarker);
                    }
                } catch (Exception e) {
                    Log.e("Location", "onLocationChanged: " + e.getMessage());
                }
            }
        };

        handler = new Handler();
        elapsedTime = 0L;
        startTime = 0L;

        runnable = new Runnable() {
            @Override
            public void run() {
                updateTimer();
                handler.postDelayed(this, 1000);
            }
        };

    }

    private void updateTimer() {
        long currentTime = System.currentTimeMillis();
        long totalTime = elapsedTime + (currentTime - startTime);
        int seconds = (int) (totalTime / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;

        activityDuration.setText(String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60));
    }

    public void startActivity(android.view.View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        activityObject = new ActivityObject();
        activityObject.setActivityName(activityName.getText().toString());
        // activityObject.setStartActivity(System.currentTimeMillis());

        activityObject.setIdUser(app.user.getIdUser());

        totalDistance = 0.0;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        IsTimerRunning = true;
        startTime = System.currentTimeMillis();
        handler.postDelayed(runnable, 0);

    }

    private void requestMyPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    public void stopActivity(android.view.View view) {
        locationManager.removeUpdates(locationListener);
        IsTimerRunning = false;
        elapsedTime += System.currentTimeMillis() - startTime;
        handler.removeCallbacks(runnable);
        elapsedTime = 0L;
        activityObject.activityDuration = elapsedTime;
        //TODO DA SE VPIŠE V BAZO
    }

    public void pauseActivity(android.view.View view) {
        locationManager.removeUpdates(locationListener);
        IsTimerRunning = false;
        elapsedTime += System.currentTimeMillis() - startTime;
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDetach();
    }

    public Double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        var R = 6371; // Radius of the earth in km
        var dLat = deg2rad(lat2-lat1);  // deg2rad below
        var dLon = deg2rad(lon2-lon1);
        var a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c; // Distance in km
        return d;
    }

    public Double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

}