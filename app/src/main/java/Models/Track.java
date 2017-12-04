package Models;

import java.util.ArrayList;

/**
 * Created by kevin on 17.11.2017.
 */



public class Track {
    private String id_track;
    private String name;
    private long timer;
    private double length;
    private int userId;
    private ArrayList<POD> PODs;
    private ArrayList<POI> POIs;
    private ArrayList<Coordinate>coordinates;

    public Track(String id_track, String name, long timer, double length, int userId) {
        this.id_track = id_track;
        this.name = name;
        this.timer = timer;
        this.length = length;
        this.userId = userId;
        POIs = new ArrayList<>();
        PODs = new ArrayList<>();
        coordinates = new ArrayList<>();
    }

    public Track(String name, long timer, double length, int userId) {
        this.name = name;
        this.timer = timer;
        this.length = length;
        POIs = new ArrayList<>();
        PODs = new ArrayList<>();
        coordinates = new ArrayList<>();
    }

    public String getId_track() {
        return id_track;
    }

    public void setId_track(String id_track) {
        this.id_track = id_track;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public void addCoordinate(Coordinate coordinate){
        this.coordinates.add(coordinate);
    }

    public ArrayList<POD> getPODs() {
        return PODs;
    }

    public void setPODs(ArrayList<POD> PODs) {
        this.PODs = PODs;
    }

    public void addPod(POD pod){
        this.PODs.add(pod);
    }

    public ArrayList<POI> getPOIs() {
        return POIs;
    }

    public void setPOIs(ArrayList<POI> POIs) {
        this.POIs = POIs;
    }

    public void addPoi(POI poi){
        this.POIs.add(poi);
    }
}
