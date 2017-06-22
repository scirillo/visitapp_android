package com.vistapp.visitapp.model;

import java.io.Serializable;

/**
 * Created by Santiago Cirillo on 19/05/2017.
 */

public class Location implements Serializable {
    private String name;
    private double latitude;
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }



}
