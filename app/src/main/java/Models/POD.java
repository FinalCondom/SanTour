package Models;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by kevin on 17.11.2017.
 */

@IgnoreExtraProperties
public class POD{

    private String id_POD;
    private String name;
    private String description;
    @Exclude
    private Bitmap picture;
    private Coordinate coordinate;
    private ArrayList<Difficulty> difficulties;

    public POD(){
        super();
    }

    public POD(String name, String description, Bitmap picture, Coordinate coordinate) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.coordinate = coordinate;
        difficulties = new ArrayList<Difficulty>();
    }

    public POD(String id_POD, String name, String description, Bitmap picture, Coordinate coordinate) {
        this.id_POD = id_POD;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.coordinate = coordinate;
        difficulties = new ArrayList<Difficulty>();
    }

    public String getId_POD() {
        return id_POD;
    }

    public void setId_POD(String id_POD) {
        this.id_POD = id_POD;
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

    public ArrayList<Difficulty> getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(ArrayList<Difficulty> difficulties) {
        this.difficulties = difficulties;
    }
    public void addDifficulty(Difficulty difficulty){
        this.difficulties.add(difficulty);
    }
}
