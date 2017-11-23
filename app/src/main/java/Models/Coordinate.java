package Models;

import java.util.Date;

/**
 * Created by kevin on 17.11.2017.
 */

public class Coordinate {

    private int id_coordinate;
    private double x;
    private double y;
    private double z;
    private double gdop;
    private int nbre_sat;
    private Date date;


    public Coordinate(int id_coordinate, double x, double y, double z, double gdop, int nbre_sat, Date date) {
        this.id_coordinate = id_coordinate;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gdop = gdop;
        this.nbre_sat = nbre_sat;
        this.date = date;
    }

    public int getId_coordinate() {
        return id_coordinate;
    }

    public void setId_coordinate(int id_coordinate) {
        this.id_coordinate = id_coordinate;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
