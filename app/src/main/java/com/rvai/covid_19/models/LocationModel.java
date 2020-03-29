package com.rvai.covid_19.models;

import java.io.Serializable;

public class LocationModel implements Serializable {
    double lat,lon;

    public LocationModel() {
    }

    public LocationModel(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
