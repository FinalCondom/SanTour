package Models;

import java.util.ArrayList;

/**
 * Created by kevin on 17.11.2017.
 */

public class POD extends POI{

    private ArrayList<Difficulty> difficulties;

    public POD(){
        super();
    }

    public POD(String name, String description, String picture, Coordinate coordinate) {
        super(name, description, picture, coordinate);
        difficulties = new ArrayList<Difficulty>();
    }

    public POD(int id_pod, String name, String description, String picture, Coordinate coordinate) {
        super(id_pod, name, description, picture, coordinate);
        difficulties = new ArrayList<Difficulty>();
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
