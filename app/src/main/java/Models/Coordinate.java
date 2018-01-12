package Models;

/**
 * Created by kevin on 17.11.2017.
 * this class will save a coordinate
 */

public class Coordinate {

    private double latitude;
    private double longitude;
    private double altitude;
    private double gdop;
    private int nbre_sat;
    private long date;

    public Coordinate(double latitude, double longitude, double altitude, double gdop, int nbre_sat, long date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.gdop = gdop;
        this.nbre_sat = nbre_sat;
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getGdop() {
        return gdop;
    }

    public void setGdop(double gdop) {
        this.gdop = gdop;
    }

    public int getNbre_sat() {
        return nbre_sat;
    }

    public void setNbre_sat(int nbre_sat) {
        this.nbre_sat = nbre_sat;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
