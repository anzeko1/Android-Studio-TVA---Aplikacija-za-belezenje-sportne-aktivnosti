package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tva_projekt.common.TVAapplication;

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

        app = (TVAapplication)getApplication();

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapController = mapView.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(app.selectedItem.PATH_COORDINATES[0]);
        activityName = findViewById(R.id.textName);
        activityDuration = findViewById(R.id.textDuration);
        activityDate = findViewById(R.id.textDate);
        activityDistance = findViewById(R.id.textDistance);




        activityName.setText(app.selectedItem.getActivityName());
        activityDuration.setText(app.selectedItem.getActivityDuration());
        activityDate.setText(app.selectedItem.getActivityDate());
        activityDistance.setText(app.selectedItem.getActivityDistance().toString());


        drawPath();
    }

    private void drawPath() {
        Polyline path = new Polyline();
        path.setWidth(4f);
        path.setColor(Color.BLUE);



        for (GeoPoint point : app.selectedItem.PATH_COORDINATES) {
            path.addPoint(point);
        }

        mapView.getOverlayManager().add(path);
        mapView.invalidate();

    }
}