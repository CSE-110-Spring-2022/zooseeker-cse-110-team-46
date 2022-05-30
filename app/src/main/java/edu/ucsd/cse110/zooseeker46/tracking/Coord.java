package edu.ucsd.cse110.zooseeker46.tracking;

import android.location.Location;

import androidx.annotation.NonNull;


import com.google.common.base.Objects;

public class Coord {
    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public final double lat;
    public final double lng;

    public static Coord of(double lat, double lng) {
        return new Coord(lat, lng);
    }

    public static Coord fromLocation(Location location) {
        return Coord.of(location.getLatitude(), location.getLongitude());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return Double.compare(coord.lat, lat) == 0 && Double.compare(coord.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lat, lng);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Coord{lat=%s, lng=%s}", lat, lng);
    }

    public boolean compareCoord(Coord c1, Coord c2){
        double lat = c1.lat - c2.lat;
        double lng = c1.lng - c2.lng;
        if (lat > 0.001 || lng > 0.001){
            return true;
        }
        return false;
    }
}
