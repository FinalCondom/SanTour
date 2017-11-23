package Models;

/**
 * Created by kevin on 17.11.2017.
 */

public class POI {

    private int id_POI;
    private String name;
    private String description;
    private String picture;
    private Coordinate coordinate;

    public POI(int id_POI, String name, String description, String picture, Coordinate coordinate) {
        this.id_POI = id_POI;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.coordinate = coordinate;
    }

    public int getId_POI() {
        return id_POI;
    }

    public void setId_POI(int id_POI) {
        this.id_POI = id_POI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
