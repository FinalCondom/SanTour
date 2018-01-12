package Models;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by kevin on 17.11.2017.
 * This class will create a POI
 */

@IgnoreExtraProperties
public class POI {

    protected String id_POI;
    protected String name;
    protected String description;
    @Exclude
    protected Bitmap picture;
    protected Coordinate coordinate;

    public POI(){

    }

    public POI(String name, String description, Bitmap picture, Coordinate coordinate) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.coordinate = coordinate;
    }

    public POI(String id_POI, String name, String description, Bitmap picture, Coordinate coordinate) {
        this.id_POI = id_POI;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.coordinate = coordinate;
    }

    public String getId_POI() {
        return id_POI;
    }

    public void setId_POI(String id_POI) {
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

    @Exclude
    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
