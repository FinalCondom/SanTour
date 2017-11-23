package Models;

/**
 * Created by kevin on 17.11.2017.
 */

public class Difficulty {

    private int id_difficulty;
    private String name;
    private int gradient;

    public Difficulty(int id_difficulty, String name, int gradient) {
        this.id_difficulty = id_difficulty;
        this.name = name;
        this.gradient = gradient;
    }

    public int getId_difficulty() {
        return id_difficulty;
    }

    public void setId_difficulty(int id_difficulty) {
        this.id_difficulty = id_difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGradient() {
        return gradient;
    }

    public void setGradient(int gradient) {
        this.gradient = gradient;
    }
}
