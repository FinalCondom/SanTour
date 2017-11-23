package Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kevin on 17.11.2017.
 */

public class Track {
    private int id_track;
    private String name;
    private double timer;
    private double length;
    private ArrayList<Coordinate>coordinates;

    public Track(int id_track, String name, double timer, double length) {
        this.id_track = id_track;
        this.name = name;
        this.timer = timer;
        this.length = length;
        coordinates = new ArrayList<>();
    }

    public int getId_track() {
        return id_track;
    }

    public void setId_track(int id_track) {
        this.id_track = id_track;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTimer() {
        return timer;
    }

    public void setTimer(double timer) {
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
}
