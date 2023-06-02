package com.example.tva_projekt.dataObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.osmdroid.util.GeoPoint;

public class MyGeoPoint extends GeoPoint {
    @SerializedName("lat")
    @Expose
    public double lat;
    @SerializedName("long")
    @Expose
    public double lng;

    public MyGeoPoint(double lat, double lng) {
        super(lat, lng);

        this.lat = lat;
        this.lng = lng;
    }
}
