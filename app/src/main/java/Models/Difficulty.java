package Models;

/**
 * Created by kevin on 17.11.2017.
 */

public class Difficulty {

    private String difficulty_id;
    private String name;
    private int gradient;

    public Difficulty() {
    }

    public Difficulty(String name) {
        this.name = name;
    }

    public Difficulty(String name, int gradient) {
        this.name = name;
        this.gradient = gradient;
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
