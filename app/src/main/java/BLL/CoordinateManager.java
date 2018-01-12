package BLL;

import android.location.Location;
import android.os.Bundle;

import Models.Coordinate;

/**
 * Created by lucien on 04.12.2017.
 * This class is creating a Coordinate object with a given location
 */

public class CoordinateManager {
    public Coordinate createCoordonateFromLocation(Location location){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();
        double gdop = location.getAccuracy();
        Bundle extras = location.getExtras();
        int nbre_sat = (extras != null) ? extras.getInt("satellites") : -1;
        long date = location.getTime();
        Coordinate coordinate = new Coordinate(latitude, longitude, altitude, gdop, nbre_sat, date);

        return coordinate;
    }
}
