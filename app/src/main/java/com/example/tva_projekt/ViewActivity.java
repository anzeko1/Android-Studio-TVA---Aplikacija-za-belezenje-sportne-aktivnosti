package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tva_projekt.common.TVAapplication;
import com.example.tva_projekt.dataObjects.MyGeoPoint;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;


public class ViewActivity extends AppCompatActivity {

    TextView activityName;
    TextView activityDuration;
    TextView activityDate;
    TextView activityDistance;

    TVAapplication app;
    MapView mapView;
    IMapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("OpenStreetMap", MODE_PRIVATE));
        setContentView(R.layout.view_activity);

        app = (TVAapplication) getApplication();

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapController = mapView.getController();

        if (app.selectedItem.PATH_COORDINATES.length > 0) {
            drawPath();

            mapController.setZoom(15.0);
            mapController.setCenter(new GeoPoint(app.selectedItem.PATH_COORDINATES[0].lat, app.selectedItem.PATH_COORDINATES[0].lng));
        }

        activityName = findViewById(R.id.textName);
        activityDuration = findViewById(R.id.textDuration);
        activityDate = findViewById(R.id.textDate);
        activityDistance = findViewById(R.id.textDistance);

        activityName.setText(app.selectedItem.getActivityName());
        activityDuration.setText(app.selectedItem.getActivityDuration());
        activityDate.setText(app.selectedItem.getActivityDate());
        activityDistance.setText(app.selectedItem.getActivityDistance().toString());
    }

    public void closeEnterActivity(View view) {
        finish();
    }

    private void drawPath() {
        Polyline path = new Polyline();
        path.setWidth(4f);
        path.setColor(Color.BLUE);



        for (MyGeoPoint point : app.selectedItem.PATH_COORDINATES) {
            path.addPoint(new GeoPoint(point.lat, point.lng));
        }

        mapView.getOverlayManager().add(path);
        mapView.invalidate();

    }
}