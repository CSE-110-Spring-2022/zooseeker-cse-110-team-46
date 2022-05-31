package edu.ucsd.cse110.zooseeker46.tracking;

import android.location.Location;

import androidx.annotation.NonNull;


import com.google.common.base.Objects;

public class Coord {
    public static final double DEG_LAT_IN_FT = 363843.57;
    public static final double DEG_LNG_IN_FT = 307515.50;
    public static final int BASE = 100;
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

    public double compareCoord(Coord c1, Coord c2){
        double latFt1 = c1.lat * DEG_LAT_IN_FT;
        double lngFt1 = c1.lng * DEG_LNG_IN_FT;
        double latFt2 = c2.lat * DEG_LAT_IN_FT;
        double lngFt2 = c2.lng * DEG_LNG_IN_FT;

        double lat1 = Math.abs(latFt1 - latFt2);
        double lng1 = Math.abs(lngFt1 - lngFt2);

        double weight = Math.sqrt(Math.pow(lat1, 2) + Math.pow(lng1, 2));
        double weightFinal = Math.ceil(weight/BASE) * BASE;
        return weightFinal;
    }
}
